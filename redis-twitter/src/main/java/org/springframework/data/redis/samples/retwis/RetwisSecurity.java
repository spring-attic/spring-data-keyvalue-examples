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
package org.springframework.data.redis.samples.retwis;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

/**
 * Simple security class that saves the local user info. 
 * 
 * @author Costin Leau
 */
public abstract class RetwisSecurity {

	private static final ThreadLocal<String> user = new NamedThreadLocal<String>("Retwis-id");

	public static String getName(){
		return user.get();
	}

	public static void setName(String name) {
		user.set(name);
	}

	public static boolean isSignedIn() {
		return StringUtils.hasText(getName());
	}

	public static void clean() {
		setName(null);
	}
}
