/*
 * Copyright 2002-2012 the original author or authors.
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

package com.simple.frame.xml;

import com.simple.frame.xml.module.Problem;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple {@link ProblemReporter} implementation that exhibits fail-fast
 * behavior when errors are encountered.
 *
 * <p>The first error encountered results in a {@link BeanDefinitionParsingException}
 * being thrown.
 *
 * <p>Warnings are written to
 * {@link #setlog(org.apache.commons.logging.Log) the log} for this class.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Rick Evans
 * @since 2.0
 */
public class FailFastProblemReporter implements ProblemReporter {

	private org.slf4j.Logger  log;



	public void setlog(Logger log) {
		this.log = (log != null ? log : LoggerFactory.getLogger(getClass()));
	}



	@Override
	public void fatal(Problem problem) {
		//throw new BeanDefinitionParsingException(problem);
		System.err.println(problem);
	}


	@Override
	public void error(Problem problem) {
		//throw new BeanDefinitionParsingException(problem);
		System.err.println(problem);

	}


	@Override
	public void warning(Problem problem) {
		//this.log.warn(problem, problem.getRootCause());
		System.err.println(problem);

	}

}
