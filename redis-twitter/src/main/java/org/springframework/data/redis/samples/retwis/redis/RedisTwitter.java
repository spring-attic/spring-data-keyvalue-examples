/*
 * Copyright 2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.samples.retwis.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.keyvalue.redis.core.BoundHashOperations;
import org.springframework.data.keyvalue.redis.core.StringRedisTemplate;
import org.springframework.data.keyvalue.redis.core.ValueOperations;
import org.springframework.data.keyvalue.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisList;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisMap;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisSet;
import org.springframework.data.keyvalue.redis.support.collections.RedisList;
import org.springframework.data.redis.samples.Post;
import org.springframework.data.redis.samples.retwis.PostIdGenerator;
import org.springframework.data.redis.samples.retwis.Range;
import org.springframework.util.StringUtils;

/**
 * Twitter-clone on top of Redis.
 * 
 * @author Costin Leau
 */
@Named
public class RedisTwitter {

	private final StringRedisTemplate template;
	private final ValueOperations<String, String> valueOps;
	// post id generator
	private final PostIdGenerator postIdGenerator;
	// user id generator
	private final RedisAtomicLong userIdCounter;

	// track users
	private RedisList<String> users;
	// time-line
	private final RedisList<String> timeline;

	@Inject
	public RedisTwitter(StringRedisTemplate template) {
		this.template = template;
		valueOps = template.opsForValue();

		users = new DefaultRedisList<String>("users", template);
		timeline = new DefaultRedisList<String>("timeline", template);
		userIdCounter = new RedisAtomicLong("global:uid", template.getConnectionFactory());
		postIdGenerator = new LongGenerator(new RedisAtomicLong("global:pid", template.getConnectionFactory()));
	}

	/**
	 * Adds the given user into the system and fills up the missing links. 
	 *  
	 * @param user
	 * @return
	 */
	public String addUser(String name, String password) {
		String uid = String.valueOf(userIdCounter.incrementAndGet());

		// FIXME: add functionality into the template
		// save user as hash
		// uid -> user
		BoundHashOperations<String, String, String> userOps = template.boundHashOps("uid:" + uid);
		userOps.put("name", name);
		userOps.put("pass", password);

		// link username -> uid
		valueOps.set("user:" + name + ":uid", uid);

		// track username
		users.add(name);

		return addAuth(name);
	}

	public List<Post> getPosts(String username, Range range) {
		String uid = findUid(username);
		List<String> pids = new DefaultRedisList<String>("posts:" + uid, template).range(range.start, range.end);
		return loadPosts(pids);
	}

	private List<Post> loadPosts(Collection<String> pids) {
		List<Post> posts = new ArrayList<Post>(pids.size());

		//FIXME: add basic mapping mechanism
		//FIXME: optimize this N+1
		for (String pid : pids) {
			posts.add(loadPost(pid));
		}

		return posts;
	}

	private Post loadPost(String pid) {
		Post post = new Post().fromMap(new DefaultRedisMap<String, String>("pid:" + pid, template));
		post.setName(findName(post.getUid()));
		post.setReplyName(findName(post.getReplyUid()));
		post.setPid(pid);
		return post;
	}

	public Set<String> getFollowers(String username) {
		String uid = findUid(username);
		return new DefaultRedisSet<String>("uid:" + uid + ":followers", template);
	}

	public Set<String> getFollowing(String username) {
		String uid = findUid(username);
		return new DefaultRedisSet<String>("uid:" + uid + ":following", template);
	}

	public List<String> getMentions(String username, Range range) {
		String uid = findUid(username);
		return new DefaultRedisList<String>("uid:" + uid + ":mentions", template).range(range.start, range.end);
	}

	public Collection<Post> timeline(Range range) {
		Collection<String> pids = timeline.range(range.start, range.end);
		return loadPosts(pids);
	}

	public Collection<String> newUsers(Range range) {
		return users.range(range.start, range.end);
	}


	public void post(String username, Post post) {
		String uid = findUid(username);
		post.setUid(uid);
		String pid = postIdGenerator.generate();
		// add post
		new DefaultRedisMap<String, Object>("pid:" + pid, template).putAll(post.asMap());

		// add links
		new DefaultRedisList<String>("posts:" + uid, template).addFirst(pid);
		timeline.addFirst(pid);
	}

	private String findUid(String name) {
		return valueOps.get("user:" + name + ":uid");
	}


	public boolean isUserValid(String name) {
		return template.hasKey("user:" + name + ":uid");
	}

	private String findName(String uid) {
		BoundHashOperations<String, String, String> userOps = template.boundHashOps("uid:" + uid);
		return userOps.get("name");
	}

	public boolean isAuthValid(String value) {
		String uid = valueOps.get("auth:" + value);
		return (uid != null);
	}

	public boolean auth(String user, String pass) {
		// find uid
		String uid = findUid(user);
		if (StringUtils.hasText(uid)) {
			BoundHashOperations<String, String, String> userOps = template.boundHashOps("uid:" + uid);
			return userOps.get("pass").equals(pass);
		}

		return false;
	}


	public String getUserNameForAuth(String value) {
		String uid = valueOps.get("auth:" + value);
		return findName(uid);
	}

	/**
	 * Adds auth key to Redis for the given username.
	 * 
	 * @param uid
	 * @return
	 */
	public String addAuth(String name) {
		String uid = findUid(name);
		// add random auth key relation
		String auth = UUID.randomUUID().toString();
		valueOps.set("uid:" + uid + ":auth", auth);
		valueOps.set("auth:" + auth, uid);
		return auth;
	}

	public void deleteAuth(String user) {
		String uid = findUid(user);

		String authKey = "uid:"+uid+":auth";
		String auth = valueOps.get(authKey);
		
		template.delete(Arrays.asList(authKey, "auth:" + auth));
	}
}