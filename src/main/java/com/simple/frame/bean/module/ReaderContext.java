/*
 * Copyright 2002-2017 the original author or authors.
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

package com.simple.frame.bean.module;

import com.simple.frame.resource.spi.Resource;
import com.simple.frame.bean.spi.DefaultDefinition;
import com.simple.frame.bean.spi.ProblemReporter;
import com.simple.frame.bean.spi.ReaderEventListener;
import com.simple.frame.bean.spi.SourceExtractor;
import com.simple.frame.bean.spi.ComponentDefinition;
import com.sun.istack.internal.Nullable;

/**
 * Context that gets passed along a bean definition reading process,
 * encapsulating all relevant configuration as well as state.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
public class ReaderContext {

	private final Resource resource;

	private final ProblemReporter problemReporter;

	private final ReaderEventListener eventListener;

	private final SourceExtractor sourceExtractor;


	/**
	 * Construct a new {@code ReaderContext}.
	 * @param resource the XML bean definition resource
	 * @param problemReporter the problem reporter in use
	 * @param eventListener the event listener in use
	 * @param sourceExtractor the source extractor in use
	 */
	public ReaderContext(Resource resource, ProblemReporter problemReporter,
						 ReaderEventListener eventListener, SourceExtractor sourceExtractor) {

		this.resource = resource;
		this.problemReporter = problemReporter;
		this.eventListener = eventListener;
		this.sourceExtractor = sourceExtractor;
	}

	public final Resource getResource() {
		return this.resource;
	}


	// Errors and warnings

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, Object source) {
		fatal(message, source, null, null);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, Object source, Throwable cause) {
		fatal(message, source, null, cause);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, Object source, ParseState parseState) {
		fatal(message, source, parseState, null);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, Object source, ParseState parseState, Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.fatal(new Problem(message, location, parseState, cause));
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, Object source) {
		error(message, source, null, null);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, Object source, Throwable cause) {
		error(message, source, null, cause);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, Object source, ParseState parseState) {
		error(message, source, parseState, null);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, Object source, ParseState parseState, Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.error(new Problem(message, location, parseState, cause));
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, Object source) {
		warning(message, source, null, null);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, Object source, Throwable cause) {
		warning(message, source, null, cause);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, Object source, ParseState parseState) {
		warning(message, source, parseState, null);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, Object source, ParseState parseState, Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.warning(new Problem(message, location, parseState, cause));
	}


	// Explicit parse events

	/**
	 * Fire an defaults-registered event.
	 */
	public void fireDefaultsRegistered(DefaultDefinition defaultsDefinition) {
		this.eventListener.defaultsRegistered(defaultsDefinition);
	}

	/**
	 * Fire an component-registered event.
	 */
	public void fireComponentRegistered(ComponentDefinition componentDefinition) {
		this.eventListener.componentRegistered(componentDefinition);
	}

	/**
	 * Fire an alias-registered event.
	 */
	public void fireAliasRegistered(String beanName, String alias, Object source) {
		this.eventListener.aliasRegistered(new AliasDefinition(beanName, alias, source));
	}

	/**
	 * Fire an import-processed event.
	 */
	public void fireImportProcessed(String importedResource, Object source) {
		this.eventListener.importProcessed(new ImportDefinition(importedResource, source));
	}

	/**
	 * Fire an import-processed event.
	 */
	public void fireImportProcessed(String importedResource, Resource[] actualResources, Object source) {
		this.eventListener.importProcessed(new ImportDefinition(importedResource, actualResources, source));
	}


	// Source extraction

	/**
	 * Return the source extractor in use.
	 */
	public SourceExtractor getSourceExtractor() {
		return this.sourceExtractor;
	}

	/**
	 * Call the source extractor for the given source object.
	 * @param sourceCandidate the original source object
	 * @return the source object to store, or {@code null} for none.
	 * @see #getSourceExtractor()
	 * @see SourceExtractor#extractSource
	 */
	@Nullable
	public Object extractSource(Object sourceCandidate) {
		return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
	}

}
