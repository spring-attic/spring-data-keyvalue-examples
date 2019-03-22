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
package org.springframework.data.redis.samples.retwisj;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

/**
 * Simple security class that saves the local user info across each request.
 * 
 * @author Costin Leau
 */
public abstract class RetwisSecurity {

	private static class UserInfo {
		String name;
		String uid;
	}

	private static final ThreadLocal<UserInfo> user = new NamedThreadLocal<UserInfo>("Retwis-id");

	public static String getName() {
		UserInfo userInfo = user.get();
		return (userInfo != null ? userInfo.name : null);
	}

	public static String getUid() {
		UserInfo userInfo = user.get();
		return (userInfo != null ? userInfo.uid : null);
	}

	public static void setUser(String name, String uid) {
		UserInfo userInfo = new UserInfo();
		userInfo.name = name;
		userInfo.uid = uid;
		user.set(userInfo);
	}

	public static boolean isUserSignedIn(String name) {
		UserInfo userInfo = user.get();
		return userInfo != null && userInfo.name.equals(name);
	}

	public static boolean isSignedIn() {
		return StringUtils.hasText(getName());
	}

	public static void clean() {
		user.set(null);
	}
}
