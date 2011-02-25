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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.samples.retwis.Range;
import org.springframework.data.redis.samples.retwis.redis.RedisTwitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView signUp(@RequestParam String name, @RequestParam String pass, @RequestParam String pass2) {
		// FIXME: add pass check
		twitter.addUser(name, pass);
		return home(name, null);
	}

	@RequestMapping("/signIn")
	public ModelAndView signIn(@RequestParam String name, @RequestParam String pass) {
		if (twitter.auth(name, pass)) {
			return posts(name);
		}

		ModelAndView mav = new ModelAndView("signin");
		return mav;
	}

	@RequestMapping("/home")
	public ModelAndView home(String name, @RequestParam String content) {

		if (StringUtils.hasText(content)) {
			// get username
			twitter.post(name, content);
		}

		ModelAndView mav = new ModelAndView("home");
		mav.addObject("name", name);
		mav.addObject("posts", twitter.getPosts(name, new Range()));
		return mav;
	}

	@RequestMapping("/#{username}")
	public ModelAndView posts(@PathVariable String username) {
		ModelAndView mav = new ModelAndView("posts");
		return mav;
	}

	@RequestMapping("/#{username}/mentions")
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