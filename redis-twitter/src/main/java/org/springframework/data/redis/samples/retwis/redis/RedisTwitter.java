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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.keyvalue.redis.core.BoundHashOperations;
import org.springframework.data.keyvalue.redis.core.StringRedisTemplate;
import org.springframework.data.keyvalue.redis.core.ValueOperations;
import org.springframework.data.keyvalue.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisList;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisSet;
import org.springframework.data.keyvalue.redis.support.collections.RedisList;
import org.springframework.data.redis.samples.User;
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
	// FIXME: unwrap the stored entries as uid and post
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
	public User addUser(String name, String password) {
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

		return new User(name, password);
	}

	public List<String> getPosts(String username, Range range) {
		String uid = findUid(username);
		return new DefaultRedisList<String>("posts:" + uid, template).range(range.start, range.end);
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

	public Collection<String> timeline(Range range) {
		return timeline.range(range.start, range.end);
	}

	public Collection<String> newUsers(int start, int end) {
		return users.range(start, end);
	}

	private String findUid(String name) {
		return valueOps.get("user:" + name + ":uid");
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

	public void post(String username, String content) {
		String uid = findUid(username);
		new DefaultRedisList<String>("posts:" + uid, template).add(content);
	}
}