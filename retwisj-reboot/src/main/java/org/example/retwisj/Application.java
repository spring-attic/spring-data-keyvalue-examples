package org.example.retwisj;

import java.util.Collection;
import java.util.Collections;

import org.example.retwisj.web.CookieInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Collection<IDialect> dialects() {
		// TODO: add spring security dialect
		return Collections.emptyList();
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
}
