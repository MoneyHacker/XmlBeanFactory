package com.simple.frame.xml;

import com.simple.frame.resource.spi.Resource;
import com.simple.frame.resource.support.classpath.ClassPathResource;
import com.simple.frame.xml.bean.DefaultBeanFactory;

/**
 * Created by lvxiang@ganji.com 2018/12/8 10:00
 *
 * @author <a href="mailto:lvxiang@ganji.com">simple</a>
 */
public class Tester {

    public static void  main(String[] args) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new DefaultBeanFactory());
        Resource resource = new ClassPathResource("spring_other_dubbo.xml");
        int count =  xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        System.out.println("count=" + count);
    }
}
