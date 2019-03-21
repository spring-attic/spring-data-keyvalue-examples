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
package org.springframework.data.redis.samples.retwisj.web;

/**
 * Exception thrown when the controller is invoked with invalid data.
 * 
 * @author Costin Leau
 */
public class NoSuchDataException extends RuntimeException {

	private final String data;
	private final boolean userRelated;

	public NoSuchDataException(String data, boolean userRelated) {

		super("Invalid data " + data);
		this.data = data;
		this.userRelated = userRelated;
	}

	/**
	 * Returns the name.
	 *
	 * @return Returns the name
	 */
	public String getData() {
		return data;
	}

	public boolean isPost() {
		return !userRelated;
	}
}
