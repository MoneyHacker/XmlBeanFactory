package com.simple.frame.bean.exception;

/**
 * Created by lvxiang@ganji.com 2018/12/6 15:28
 *
 * @author <a href="mailto:lvxiang@ganji.com">simple</a>
 */
public class NoSuchBeanDefinitionException extends BeansException {
    private static final long serialVersionUID = 3552728381289912837L;

    public NoSuchBeanDefinitionException(String msg) {
        super(msg);
    }
}
