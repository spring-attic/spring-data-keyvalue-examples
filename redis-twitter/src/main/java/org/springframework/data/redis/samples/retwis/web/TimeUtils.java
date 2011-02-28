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

/**
 * Utility converting the date (stored as long) into human-friendly message.
 * 
 * @author Costin Leau
 */
public class TimeUtils {

	public static String inWords(long time){
		long elapsed = System.currentTimeMillis() - time;
		
		// seconds
		elapsed /= 1000;

		if (elapsed <10){
			return "just now";
		}
		if (elapsed <60){
			return "less than a minute ago";
		}
		
		// minutes
		elapsed /= 60;

		if (elapsed <2){
			return "a minute ago";	
		}
		
		if (elapsed <45){
			return elapsed+" minutes ago";
		}
		
		if (elapsed <90){
			return "about an hour ago";
		}
		
		if (elapsed <1440){
			return elapsed/60 + " hours ago";
		}
		
		if (elapsed < 2880){
			return "about a day ago";
		}
      
		if (elapsed < 43200){
		      return (elapsed / 1440) + " days ago";			
		}
		
		if (elapsed < 86400){
			return "about a month ago";	
		}
		
		if (elapsed < 525600){
			return (elapsed / 43200) + " months ago";			
		}
		
		if (elapsed < 1051199)
		{
			return "about a year ago";
		}
		
		return "over " + (elapsed / 525600) + " years ago";
	}
}