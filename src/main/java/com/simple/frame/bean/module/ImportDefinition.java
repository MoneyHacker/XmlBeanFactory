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

import com.simple.frame.bean.spi.BeanMetadataElement;
import com.simple.frame.resource.spi.Resource;
import com.simple.frame.resource.util.Assert;
import com.sun.istack.internal.Nullable;

/**
 * Representation of an import that has been processed during the parsing process.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */
public class ImportDefinition implements BeanMetadataElement {

	private final String importedResource;

	@Nullable
	private final Resource[] actualResources;

	@Nullable
	private final Object source;


	/**
	 * Create a new ImportDefinition.
	 * @param importedResource the location of the imported resource
	 */
	public ImportDefinition(String importedResource) {
		this(importedResource, null, null);
	}

	/**
	 * Create a new ImportDefinition.
	 * @param importedResource the location of the imported resource
	 * @param source the source object (may be {@code null})
	 */
	public ImportDefinition(String importedResource, @Nullable Object source) {
		this(importedResource, null, source);
	}

	/**
	 * Create a new ImportDefinition.
	 * @param importedResource the location of the imported resource
	 * @param source the source object (may be {@code null})
	 */
	public ImportDefinition(String importedResource, @Nullable Resource[] actualResources, @Nullable Object source) {
		Assert.notNull(importedResource, "Imported resource must not be null");
		this.importedResource = importedResource;
		this.actualResources = actualResources;
		this.source = source;
	}


	/**
	 * Return the location of the imported resource.
	 */
	public final String getImportedResource() {
		return this.importedResource;
	}

	@Nullable
	public final Resource[] getActualResources() {
		return this.actualResources;
	}

	@Override
	@Nullable
	public final Object getSource() {
		return this.source;
	}

}
