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

import java.io.Serializable;

/**
 * LongGenerator interface for generating posts id.
 * Used as a strategy for plugging in different implementations.
 * 
 * @author Costin Leau
 */
public interface PostIdGenerator<T extends Serializable> {

	T generate();
}
