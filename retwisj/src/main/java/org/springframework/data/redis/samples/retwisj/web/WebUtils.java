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


/**
 * Utility converting the date (stored as long) into human-friendly message.
 * 
 * @author Costin Leau
 */
public class WebUtils {

	public static String timeInWords(long time) {
		long elapsed = System.currentTimeMillis() - time;

		// seconds
		elapsed /= 1000;

		if (elapsed < 10) {
			return "time.now";
		}
		if (elapsed < 60) {
			return "time.minute.less";
		}

		// minutes
		elapsed /= 60;

		if (elapsed < 2) {
			return "time.minute";
		}

		if (elapsed < 45) {
			return "time.minutes#" + elapsed;
		}

		if (elapsed < 90) {
			return "time.hour";
		}

		if (elapsed < 1440) {
			return "time.hours#" + elapsed / 60;
		}

		if (elapsed < 2880) {
			return "time.day";
		}

		if (elapsed < 43200) {
			return "time.days#" + (elapsed / 1440);
		}

		if (elapsed < 86400) {
			return "time.month";
		}

		if (elapsed < 525600) {
			return "time.months#" + (elapsed / 43200);
		}

		if (elapsed < 1051199) {
			return "time.year";
		}

		return "time.years#" + (elapsed / 525600);
	}
}