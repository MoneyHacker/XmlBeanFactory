package com.simple.frame.bean.dubbo;

import com.alibaba.dubbo.config.*;
import com.simple.frame.bean.NamespaceHandlerSupport;

public class DubboNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("application",  new DubboBeanDefinitionParser(ApplicationConfig.class, true));
        registerBeanDefinitionParser("module",  new DubboBeanDefinitionParser(ModuleConfig.class, true));
        registerBeanDefinitionParser("registry", new DubboBeanDefinitionParser(RegistryConfig.class, true));
        registerBeanDefinitionParser("monitor", new DubboBeanDefinitionParser(MonitorConfig.class, true));
        registerBeanDefinitionParser("provider", new DubboBeanDefinitionParser(ProviderConfig.class, true));
        registerBeanDefinitionParser("consumer", new DubboBeanDefinitionParser(ConsumerConfig.class, true));
        registerBeanDefinitionParser("protocol", new DubboBeanDefinitionParser(ProtocolConfig.class, true));
       // registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
        //registerBeanDefinitionParser("reference", new DubboBeanDefinitionParser(ReferenceBean.class, false));
    }

}