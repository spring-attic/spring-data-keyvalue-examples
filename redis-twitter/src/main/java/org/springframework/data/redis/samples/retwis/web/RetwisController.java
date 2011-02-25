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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.samples.retwis.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Annotation-driven controller for Retwis.
 * 
 * @author Costin Leau
 */
@Controller
public class RetwisController {

	@Autowired
	private final Twitter twitter;

	@Autowired
	public RetwisController(Twitter twitter) {
		this.twitter = twitter;
	}

	@RequestMapping("/")
	public String homeHandler() {
		return "home";
	}

	@RequestMapping(value = "/{username}")
	public ModelAndView posts(@PathVariable String username) {
		ModelAndView mav = new ModelAndView("posts");
		mav.addObject("posts", twitter.mentions(username));
		return mav;
	}

	@RequestMapping(value = "/{username}/mentions")
	public ModelAndView mentions(@PathVariable String username) {
		ModelAndView mav = new ModelAndView("mentions");
		mav.addObject("mentions", twitter.mentions(username));
		return mav;
	}

	@RequestMapping("/timeline")
	public ModelMap timeline() {
		return new ModelMap(twitter.timeline());
	}
}