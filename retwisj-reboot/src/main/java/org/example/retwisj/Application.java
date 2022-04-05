/*
 * Copyright 2014 the original author or authors.
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
package org.example.retwisj;

import java.util.Collections;
import java.util.Set;

import org.example.retwisj.web.CookieInterceptor;
import org.example.retwisj.web.TweetDateProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				super.addInterceptors(registry);
				registry.addInterceptor(cookieInterceptor());
			}
		};

		return adapter;
	}

	@Bean
	CookieInterceptor cookieInterceptor() {
		return new CookieInterceptor();
	}

	@Bean
	public IDialect tweetDialect() {

		return new AbstractDialect() {

			@Override
			public Set<IProcessor> getProcessors() {
				return Collections.<IProcessor> singleton(tweetDateProcessor());
			}

			@Override
			public String getPrefix() {
				return "tweet";
			}

		};

	}

	@Bean
	public TweetDateProcessor tweetDateProcessor() {
		return new TweetDateProcessor();
	}
}
