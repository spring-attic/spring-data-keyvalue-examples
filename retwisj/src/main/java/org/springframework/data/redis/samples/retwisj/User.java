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


/**
 * Class describing a user. 
 * 
 * @author Costin Leau
 */
public class User {

	private Long id;
	private String name;
	private String pass;
	private String authKey;

	public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}

	/**
	 * Returns the id.
	 *
	 * @return Returns the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the pass.
	 *
	 * @return Returns the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass The pass to set.
	 */
	public void setPass(String password) {
		this.pass = password;
	}

	/**
	 * Returns the authKey.
	 *
	 * @return Returns the authKey
	 */
	public String getAuthKey() {
		return authKey;
	}

	/**
	 * @param authKey The authKey to set.
	 */
	public void setAuthKey(String loginKey) {
		this.authKey = loginKey;
	}
}