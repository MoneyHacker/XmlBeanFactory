/*
 * Copyright 2002-2018 the original author or authors.
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

package com.simple.frame.bean;


import com.simple.frame.bean.spi.BeanDefinitionRegistry;
import com.simple.frame.bean.config.BeanDefinition;
import com.simple.frame.bean.config.BeanDefinitionHolder;
import com.simple.frame.bean.exception.BeanDefinitionStoreException;
import com.simple.frame.bean.module.XmlReaderContext;
import com.simple.frame.bean.spi.ComponentDefinition;
import com.sun.istack.internal.Nullable;


import java.util.ArrayDeque;
import java.util.Deque;


/**
 * Context that gets passed along a bean definition parsing process,
 * encapsulating all relevant configuration as well as state.
 * Nested inside an {@link XmlReaderContext}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @see XmlReaderContext
 * @see BeanDefinitionParserDelegate
 * @since 2.0
 */
public final class ParserContext {

    private final XmlReaderContext readerContext;

    private final BeanDefinitionParserDelegate delegate;

    /**
     * @Nullable
     **/
    private BeanDefinition containingBeanDefinition;

    private final Deque<CompositeComponentDefinition> containingComponents = new ArrayDeque<>();


    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate) {
        this.readerContext = readerContext;
        this.delegate = delegate;
    }

    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate,
                         BeanDefinition containingBeanDefinition) {

        this.readerContext = readerContext;
        this.delegate = delegate;
        this.containingBeanDefinition = containingBeanDefinition;
    }


    public final XmlReaderContext getReaderContext() {
        return this.readerContext;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.readerContext.getRegistry();
    }

    public final BeanDefinitionParserDelegate getDelegate() {
        return this.delegate;
    }

    @Nullable
    public final BeanDefinition getContainingBeanDefinition() {
        return this.containingBeanDefinition;
    }

    public final boolean isNested() {
        return (this.containingBeanDefinition != null);
    }

    public boolean isDefaultLazyInit() {
        return BeanDefinitionParserDelegate.TRUE_VALUE.equals(this.delegate.getDefaults().getLazyInit());
    }

    @Nullable
    public Object extractSource(Object sourceCandidate) {
        return this.readerContext.extractSource(sourceCandidate);
    }

    @Nullable
    public CompositeComponentDefinition getContainingComponent() {
        return this.containingComponents.peek();
    }

    public void pushContainingComponent(CompositeComponentDefinition containingComponent) {
        this.containingComponents.push(containingComponent);
    }

    public CompositeComponentDefinition popContainingComponent() {
        return this.containingComponents.pop();
    }

    public void popAndRegisterContainingComponent() {
        registerComponent(popContainingComponent());
    }

    public void registerComponent(ComponentDefinition component) {
        CompositeComponentDefinition containingComponent = getContainingComponent();
        if (containingComponent != null) {
            containingComponent.addNestedComponent(component);
        } else {
            this.readerContext.fireComponentRegistered(component);
        }
    }

    public void registerBeanComponent(BeanComponentDefinition component) {
        registerBeanDefinition(component, getRegistry());
        registerComponent(component);
    }

    public static void registerBeanDefinition(
            BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry)
            throws BeanDefinitionStoreException {
        // Register bean definition under primary name.
        String beanName = definitionHolder.getBeanName();
        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());

        // Register aliases for bean name, if any.
        String[] aliases = definitionHolder.getAliases();
        if (aliases != null) {
            for (String alias : aliases) {
                registry.registerAlias(beanName, alias);
            }
        }
    }

}
