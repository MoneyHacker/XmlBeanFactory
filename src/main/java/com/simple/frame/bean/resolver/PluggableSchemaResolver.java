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

package com.simple.frame.bean.resolver;


import com.simple.frame.resource.spi.Resource;
import com.simple.frame.resource.support.classpath.ClassPathResource;
import com.simple.frame.resource.util.Assert;
import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link EntityResolver} implementation that attempts to resolve schema URLs into
 * local {@link ClassPathResource classpath resources} using a set of mappings files.
 *
 * <p>By default, this class will look for mapping files in the classpath using the pattern:
 * {@code META-INF/spring.schemas} allowing for multiple files to exist on the
 * classpath at any one time.
 *
 * The format of {@code META-INF/spring.schemas} is a properties
 * file where each line should be of the form {@code systemId=schema-location}
 * where {@code schema-location} should also be a schema file in the classpath.
 * Since systemId is commonly a URL, one must be careful to escape any ':' characters
 * which are treated as delimiters in properties files.
 *
 * <p>The pattern for the mapping files can be overidden using the
 * {@link #PluggableSchemaResolver(ClassLoader, String)} constructor
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
@Slf4j
public class PluggableSchemaResolver implements EntityResolver {

	/**
	 * The location of the file that defines schema mappings.
	 * Can be present in multiple JAR files.
	 */
	public static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "META-INF/spring.schemas";



	
	private final ClassLoader classLoader;

	private final String schemaMappingsLocation;

	/** Stores the mapping of schema URL -> local schema path */

	private volatile Map<String, String> schemaMappings;


	/**
	 * Loads the schema URL -> schema file location mappings using the default
	 * mapping file pattern "META-INF/spring.schemas".
	 * @param classLoader the ClassLoader to use for loading
	 * (can be {@code null}) to use the default ClassLoader)
	 */
	public PluggableSchemaResolver(ClassLoader classLoader) {
		this.classLoader = classLoader;
		this.schemaMappingsLocation = DEFAULT_SCHEMA_MAPPINGS_LOCATION;
	}

	/**
	 * Loads the schema URL -> schema file location mappings using the given
	 * mapping file pattern.
	 * @param classLoader the ClassLoader to use for loading
	 * (can be {@code null}) to use the default ClassLoader)
	 * @param schemaMappingsLocation the location of the file that defines schema mappings
	 * (must not be empty)
	 */
	public PluggableSchemaResolver( ClassLoader classLoader, String schemaMappingsLocation) {
		Assert.hasText(schemaMappingsLocation, "'schemaMappingsLocation' must not be empty");
		this.classLoader = classLoader;
		this.schemaMappingsLocation = schemaMappingsLocation;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException {
		if (log.isTraceEnabled()) {
			log.trace("Trying to resolve XML entity with public id [" + publicId +
					"] and system id [" + systemId + "]");
		}

		if (systemId != null) {
			String resourceLocation = getSchemaMappings().get(systemId);
			if (resourceLocation != null) {
				Resource resource = new ClassPathResource(resourceLocation, this.classLoader);
				try {
					InputSource source = new InputSource(resource.getInputStream());
					source.setPublicId(publicId);
					source.setSystemId(systemId);
					if (log.isDebugEnabled()) {
						log.debug("Found XML schema [" + systemId + "] in classpath: " + resourceLocation);
					}
					return source;
				}
				catch (FileNotFoundException ex) {
					if (log.isDebugEnabled()) {
						log.debug("Couldn't find XML schema [" + systemId + "]: " + resource, ex);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Load the specified schema mappings lazily.
	 */
	private Map<String, String> getSchemaMappings() {
		Map<String, String> schemaMappings = this.schemaMappings;
		if (schemaMappings == null) {
			synchronized (this) {
				schemaMappings = this.schemaMappings;
				if (schemaMappings == null) {
					if (log.isDebugEnabled()) {
						log.debug("Loading schema mappings from [" + this.schemaMappingsLocation + "]");
					}
					try {
						Properties mappings = new Properties();//PropertiesLoaderUtils.loadAllProperties(this.schemaMappingsLocation, this.classLoader);
						if (log.isDebugEnabled()) {
							log.debug("Loaded schema mappings: " + mappings);
						}
						Map<String, String> mappingsToUse = new ConcurrentHashMap<>(mappings.size());
						//CollectionUtils.mergePropertiesIntoMap(mappings, mappingsToUse);
						schemaMappings = mappingsToUse;
						this.schemaMappings = schemaMappings;
					}
					catch (/**IO**/Exception ex) {
						throw new IllegalStateException(
								"Unable to load schema mappings from location [" + this.schemaMappingsLocation + "]", ex);
					}
				}
			}
		}
		return schemaMappings;
	}


	@Override
	public String toString() {
		return "EntityResolver using mappings " + getSchemaMappings();
	}

}
