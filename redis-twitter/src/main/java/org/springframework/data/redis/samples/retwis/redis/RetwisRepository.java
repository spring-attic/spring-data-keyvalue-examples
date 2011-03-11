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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.keyvalue.redis.core.BoundHashOperations;
import org.springframework.data.keyvalue.redis.core.BulkMapper;
import org.springframework.data.keyvalue.redis.core.StringRedisTemplate;
import org.springframework.data.keyvalue.redis.core.ValueOperations;
import org.springframework.data.keyvalue.redis.core.query.SortQuery;
import org.springframework.data.keyvalue.redis.core.query.SortQueryBuilder;
import org.springframework.data.keyvalue.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.keyvalue.redis.hash.HashMapper;
import org.springframework.data.keyvalue.redis.hash.JacksonHashMapper;
import org.springframework.data.keyvalue.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisList;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisMap;
import org.springframework.data.keyvalue.redis.support.collections.DefaultRedisSet;
import org.springframework.data.keyvalue.redis.support.collections.RedisList;
import org.springframework.data.keyvalue.redis.support.collections.RedisMap;
import org.springframework.data.keyvalue.redis.support.collections.RedisSet;
import org.springframework.data.redis.samples.retwis.Post;
import org.springframework.data.redis.samples.retwis.Range;
import org.springframework.data.redis.samples.retwis.RetwisSecurity;
import org.springframework.data.redis.samples.retwis.web.WebPost;
import org.springframework.util.StringUtils;

/**
 * Twitter-clone on top of Redis.
 * 
 * @author Costin Leau
 */
@Named
public class RetwisRepository {

	private static final Pattern MENTION_REGEX = Pattern.compile("@[\\w]+");

	private final StringRedisTemplate template;
	private final ValueOperations<String, String> valueOps;

	private final LongGenerator postIdGenerator;
	private final RedisAtomicLong userIdCounter;

	// global users
	private RedisList<String> users;
	// global timeline
	private final RedisList<String> timeline;

	private final HashMapper<Post, String, String> postMapper = new DecoratingStringHashMapper<Post>(
			new JacksonHashMapper<Post>(Post.class));

	@Inject
	public RetwisRepository(StringRedisTemplate template) {
		this.template = template;
		valueOps = template.opsForValue();

		users = new DefaultRedisList<String>(KeyUtils.users(), template);
		timeline = new DefaultRedisList<String>(KeyUtils.timeline(), template);
		userIdCounter = new RedisAtomicLong(KeyUtils.globalUid(), template.getConnectionFactory());
		postIdGenerator = new LongGenerator(new RedisAtomicLong(KeyUtils.globalPid(), template.getConnectionFactory()));
	}

	public String addUser(String name, String password) {
		String uid = String.valueOf(userIdCounter.incrementAndGet());

		// save user as hash
		// uid -> user
		BoundHashOperations<String, String, String> userOps = template.boundHashOps(KeyUtils.uid(uid));
		userOps.put("name", name);
		userOps.put("pass", password);
		valueOps.set(KeyUtils.user(name), uid);

		users.addFirst(name);
		return addAuth(name);
	}

	public List<WebPost> getPost(String pid) {
		return Collections.singletonList(convertPost(pid, post(pid)));
	}

	public List<WebPost> getPosts(String uid, Range range) {
		return convertPidsToPosts(KeyUtils.posts(uid), range);
	}

	public List<WebPost> getTimeline(String uid, Range range) {
		return convertPidsToPosts(KeyUtils.timeline(uid), range);
	}

	public Collection<String> getFollowers(String uid) {
		return covertUidsToNames(KeyUtils.followers(uid));
	}

	public Collection<String> getFollowing(String uid) {
		return covertUidsToNames(KeyUtils.followers(uid));
	}

	public List<WebPost> getMentions(String uid, Range range) {
		return convertPidsToPosts(KeyUtils.mentions(uid), range);
	}

	public Collection<WebPost> timeline(Range range) {
		return convertPidsToPosts(KeyUtils.timeline(), range);
	}

	public Collection<String> newUsers(Range range) {
		return users.range(range.being, range.end);
	}

	public void post(String username, WebPost post) {
		Post p = post.asPost();

		String uid = findUid(username);
		p.setUid(uid);

		String pid = postIdGenerator.generate();

		String replyName = post.getReplyTo();
		if (StringUtils.hasText(replyName)) {
			String mentionUid = findUid(replyName);
			p.setReplyUid(mentionUid);
			mentions(mentionUid).addFirst(pid);
			p.setReplyPid(post.getReplyPid());
		}

		// add post
		post(pid).putAll(postMapper.toHash(p));

		// add links
		posts(uid).addFirst(pid);
		timeline(uid).addFirst(pid);

		// update followers
		for (String follower : followers(uid)) {
			timeline(follower).addFirst(pid);
		}

		timeline.addFirst(pid);
		handleMentions(p, pid);
	}

	private void handleMentions(Post post, String pid) {
		// find mentions
		Collection<String> mentions = findMentions(post.getContent());

		for (String mention : mentions) {
			String uid = findUid(mention);
			if (uid != null) {
				mentions(uid).addFirst(pid);
			}
		}
	}

	public String findUid(String name) {
		return valueOps.get(KeyUtils.user(name));
	}

	public boolean isUserValid(String name) {
		return template.hasKey(KeyUtils.user(name));
	}

	public boolean isPostValid(String pid) {
		return template.hasKey(KeyUtils.post(pid));
	}


	private String findName(String uid) {
		if (!StringUtils.hasText(uid)) {
			return "";
		}
		BoundHashOperations<String, String, String> userOps = template.boundHashOps(KeyUtils.uid(uid));
		return userOps.get("name");
	}

	public boolean auth(String user, String pass) {
		// find uid
		String uid = findUid(user);
		if (StringUtils.hasText(uid)) {
			BoundHashOperations<String, String, String> userOps = template.boundHashOps(KeyUtils.uid(uid));
			return userOps.get("pass").equals(pass);
		}

		return false;
	}

	public String findNameForAuth(String value) {
		String uid = valueOps.get(KeyUtils.authKey(value));
		return findName(uid);
	}

	public String addAuth(String name) {
		String uid = findUid(name);
		// add random auth key relation
		String auth = UUID.randomUUID().toString();
		valueOps.set(KeyUtils.auth(uid), auth);
		valueOps.set(KeyUtils.authKey(auth), uid);
		return auth;
	}

	public void deleteAuth(String user) {
		String uid = findUid(user);

		String authKey = KeyUtils.auth(uid);
		String auth = valueOps.get(authKey);

		template.delete(Arrays.asList(authKey, KeyUtils.authKey(auth)));
	}

	public boolean isFollowing(String uid, String targetUid) {
		return following(uid).contains(targetUid);
	}

	public void follow(String targetUser) {
		String targetUid = findUid(targetUser);

		following(RetwisSecurity.getUid()).add(targetUid);
		followers(targetUid).add(RetwisSecurity.getUid());
	}

	public void stopFollowing(String targetUser) {
		String targetUid = findUid(targetUser);

		following(RetwisSecurity.getUid()).remove(targetUid);
		followers(targetUid).remove(RetwisSecurity.getUid());
	}

	public Set<String> alsoFollowed(String uid, String targetUid) {
		return covertUidToNames(following(uid).intersect(followers(targetUid)));
	}

	public Set<String> commonFollowers(String uid, String targetUid) {
		return covertUidToNames(following(uid).intersect(following(targetUid)));
	}

	// collections mapping the core data structures

	private RedisList<String> timeline(String uid) {
		return new DefaultRedisList<String>(KeyUtils.timeline(uid), template);
	}

	private RedisSet<String> following(String uid) {
		return new DefaultRedisSet<String>(KeyUtils.following(uid), template);
	}

	private RedisSet<String> followers(String uid) {
		return new DefaultRedisSet<String>(KeyUtils.followers(uid), template);
	}

	private RedisList<String> mentions(String uid) {
		return new DefaultRedisList<String>(KeyUtils.mentions(uid), template);
	}

	private RedisMap<String, String> post(String pid) {
		return new DefaultRedisMap<String, String>(KeyUtils.post(pid), template);
	}

	private RedisList<String> posts(String uid) {
		return new DefaultRedisList<String>(KeyUtils.posts(uid), template);
	}

	// various util methods

	private String replaceReplies(String content) {
		Matcher regexMatcher = MENTION_REGEX.matcher(content);
		while (regexMatcher.find()) {
			String match = regexMatcher.group();
			int start = regexMatcher.start();
			int stop = regexMatcher.end();

			String uName = match.substring(1);
			if (isUserValid(uName)) {
				content = content.substring(0, start) + "<a href=\"!" + uName + "\">" + match + "</a>"
						+ content.substring(stop);
			}
		}
		return content;
	}

	private List<String> covertUidsToNames(String key) {
		return template.sort(SortQueryBuilder.sort(key).noSort().get("user:*->name").build());
	}

	private Set<String> covertUidToNames(Set<String> uids) {
		Set<String> set = new LinkedHashSet<String>(uids.size());

		for (String uid : uids) {
			set.add(findName(uid));
		}
		return set;
	}

	private List<WebPost> convertPidsToPosts(String key, Range range) {
		String pid = "pid:*->";
		final String pidKey = "#";
		final String uid = "uid";
		final String content = "content";
		final String replyPid = "replyPid";
		final String replyUid = "replyUid";

		SortQuery<String> query = SortQueryBuilder.sort(key).noSort().get(pidKey).get(pid + uid).get(pid + content).get(
				pid + replyPid).get(pid + replyUid).limit(range.being, range.end).build();
		BulkMapper<WebPost, String> hm = new BulkMapper<WebPost, String>() {
			@Override
			public WebPost mapBulk(List<String> bulk) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				Iterator<String> iterator = bulk.iterator();

				String pid = iterator.next();
				map.put(uid, iterator.next());
				map.put(content, iterator.next());
				map.put(replyPid, iterator.next());
				map.put(replyUid, iterator.next());

				return convertPost(pid, map);
			}
		};
		List<WebPost> sort = template.sort(query, hm);

		return sort;
	}

	// FIXME: eliminate this n+1 calls
	// potentially do another sort query to find all the names in one go
	private WebPost convertPost(String pid, Map hash) {
		Post post = postMapper.fromHash(hash);
		WebPost wPost = new WebPost(post);
		wPost.setPid(pid);
		wPost.setName(findName(post.getUid()));
		wPost.setReplyTo(findName(post.getReplyUid()));
		wPost.setContent(replaceReplies(post.getContent()));
		return wPost;
	}

	public static Collection<String> findMentions(String content) {
		Matcher regexMatcher = MENTION_REGEX.matcher(content);
		List<String> mentions = new ArrayList<String>(4);

		while (regexMatcher.find()) {
			mentions.add(regexMatcher.group().substring(1));
		}

		return mentions;
	}
}