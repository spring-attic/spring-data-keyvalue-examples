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

import javax.inject.Inject;

import org.springframework.data.keyvalue.redis.core.RedisOperations;
import org.springframework.data.keyvalue.redis.support.atomic.RedisAtomicLong;

/**
 * Generator using a Redis counter underneath.
 * 
 * @author Costin Leau
 */
public class LongGenerator {
	private final RedisAtomicLong counter;

	public LongGenerator(String counterName, RedisOperations<String, Long> operations) {
		counter = new RedisAtomicLong(counterName, operations);
	}

	@Inject
	public LongGenerator(RedisAtomicLong counter) {
		this.counter = counter;
	}

	public String generate() {
		return String.valueOf(counter.incrementAndGet());
	}
}