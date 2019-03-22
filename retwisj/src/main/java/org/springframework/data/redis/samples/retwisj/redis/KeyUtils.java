/*
 * Copyright 2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.samples.retwisj.redis;

/**
 * Simple class keeping all the key patterns to avoid the proliferation of
 * Strings through the code.
 * 
 * @author Costin Leau
 */
abstract class KeyUtils {
	static final String UID = "uid:";

	static String following(String uid) {
		return UID + uid + ":following";
	}

	static String followers(String uid) {
		return UID + uid + ":followers";
	}

	static String timeline(String uid) {
		return UID + uid + ":timeline";
	}

	static String mentions(String uid) {
		return UID + uid + ":mentions";
	}

	static String posts(String uid) {
		return UID + uid + ":posts";
	}

	static String auth(String uid) {
		return UID + uid + ":auth";
	}

	static String uid(String uid) {
		return UID + uid;
	}

	static String post(String pid) {
		return "pid:" + pid;
	}

	static String authKey(String auth) {
		return "auth:" + auth;
	}

	public static String user(String name) {
		return "user:" + name + ":uid";
	}

	static String users() {
		return "users";
	}

	static String timeline() {
		return "timeline";
	}

	static String globalUid() {
		return "global:uid";
	}

	static String globalPid() {
		return "global:pid";
	}

	static String alsoFollowed(String uid, String targetUid) {
		return UID + uid + ":also:uid:" + targetUid;
	}

	static String commonFollowers(String uid, String targetUid) {
		return UID + uid + ":common:uid:" + targetUid;
	}
}