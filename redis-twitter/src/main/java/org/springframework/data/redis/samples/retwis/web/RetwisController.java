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
package org.springframework.data.redis.samples.retwis.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.samples.Post;
import org.springframework.data.redis.samples.retwis.Range;
import org.springframework.data.redis.samples.retwis.redis.RedisTwitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Annotation-driven controller for Retwis.
 * 
 * @author Costin Leau
 */
@Controller
public class RetwisController {

	@Autowired
	private final RedisTwitter twitter;

	@Autowired
	public RetwisController(RedisTwitter twitter) {
		this.twitter = twitter;
	}

	@RequestMapping("/")
	public String root(HttpServletRequest request) {
		// no cookie means signup page
		return "signin";
	}

	@RequestMapping("/signUp")
	public String signUp(@RequestParam String name, @RequestParam String pass, @RequestParam String pass2) {
		// FIXME: add pass check
		twitter.addUser(name, pass);
		return "redirect:/!" + name;
	}

	@RequestMapping("/signIn")
	public String signIn(@RequestParam String name, @RequestParam String pass, HttpServletResponse response) {
		// add tracing cookie
		if (twitter.auth(name, pass)) {
			Cookie cookie = new Cookie(CookieInterceptor.RETWIS_COOKIE, twitter.addAuth(name));
			response.addCookie(cookie);
			return "redirect:/!" + name;
		}

		// go back to sign in screen
		return "signin";
	}

	@RequestMapping(value = "/!{username}", method = RequestMethod.GET)
	public ModelAndView posts(@PathVariable String username) {
		System.out.println("displaying posts for user " + username);

		ModelAndView mav = new ModelAndView("home");
		mav.addObject("post", new Post());
		mav.addObject("name", username);
		mav.addObject("posts", twitter.getPosts(username, new Range()));
		return mav;
	}

	@RequestMapping(value = "/!{username}", method = RequestMethod.POST)
	public ModelAndView posts(@PathVariable String username, @ModelAttribute Post post) {
		twitter.post(username, post);
		return posts(username);
	}

	@RequestMapping("/!{username}/mentions")
	public ModelAndView mentions(@PathVariable String username) {
		ModelAndView mav = new ModelAndView("mentions");
		mav.addObject("mentions", twitter.getMentions(username, new Range()));
		return mav;
	}

	@RequestMapping("/timeline")
	public ModelMap timeline() {
		return new ModelMap(twitter.timeline(new Range()));
	}
}