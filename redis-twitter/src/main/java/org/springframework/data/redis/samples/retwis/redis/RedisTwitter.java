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

import javax.inject.Inject;

import org.springframework.data.keyvalue.redis.core.StringRedisTemplate;
import org.springframework.data.redis.samples.retwis.PostIdGenerator;
import org.springframework.data.redis.samples.retwis.Twitter;

/**
 * Twitter-clone on top of Redis.
 * 
 * @author Costin Leau
 */
public class RedisTwitter implements Twitter {

	private final StringRedisTemplate template;
	private final PostIdGenerator<?> counter;

	@Inject
	public RedisTwitter(StringRedisTemplate template, PostIdGenerator<?> counter) {
		this.template = template;
		this.counter = counter;
	}

	@Override
	public Object mentions(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> timeline() {
		throw new UnsupportedOperationException();
	}
}
