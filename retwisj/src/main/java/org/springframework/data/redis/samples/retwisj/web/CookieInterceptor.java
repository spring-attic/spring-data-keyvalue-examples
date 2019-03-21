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

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.samples.retwisj.RetwisSecurity;
import org.springframework.data.redis.samples.retwisj.redis.RetwisRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Basic interceptor that checks that each request has been authenticated against Redis.
 * 
 * @author Costin Leau
 */
public class CookieInterceptor extends HandlerInterceptorAdapter {

	public static final String RETWIS_COOKIE = "retwisauth";

	@Inject
	private RetwisRepository twitter;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// all non-root requests get analyzed
		Cookie[] cookies = request.getCookies();

		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (RETWIS_COOKIE.equals(cookie.getName())) {
					String auth = cookie.getValue();
					String name = twitter.findNameForAuth(auth);
					if (name != null) {
						String uid = twitter.findUid(name);
						RetwisSecurity.setUser(name, uid);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RetwisSecurity.clean();
	}
}