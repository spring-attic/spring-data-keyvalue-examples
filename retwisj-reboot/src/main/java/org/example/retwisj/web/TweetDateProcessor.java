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
package org.example.retwisj.web;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.AttributeNameProcessorMatcher;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * @author Christoph Strobl
 */
public class TweetDateProcessor extends AbstractTextChildModifierAttrProcessor {

	public TweetDateProcessor() {
		this(new AttributeNameProcessorMatcher("date"));
	}

	public TweetDateProcessor(IAttributeNameProcessorMatcher matcher) {
		super(matcher);
	}

	@Override
	protected String getText(Arguments arguments, Element element, String attributeName) {

		Configuration configuration = arguments.getConfiguration();

		IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
		String attributeValue = element.getAttributeValue(attributeName);
		IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);

		WebPost post = (WebPost) expression.execute(configuration, arguments);

		return getMessage(arguments, post.getTime(), new Object[] { post.getTimeArg() });
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

}
