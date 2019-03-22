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
 * Basic object indicating a range of objects to retrieve. Default is 10 objects (starting at zero).
 * 
 * @author Costin Leau
 */
public class Range {

	private static final int SIZE = 9;
	public int begin = 0;
	public int end = SIZE;

	public Range() {
	};

	public Range(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public Range(int pageNumber) {
		this.begin = 0;
		this.end = pageNumber * SIZE;
	}

	public int getPages() {
		return (int) Math.round(Math.ceil(end / SIZE));
	}
}
