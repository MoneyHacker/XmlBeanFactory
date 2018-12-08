package com.simple.frame.bean.exception;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by lvxiang@ganji.com 2018/12/6 16:40
 *
 * @author <a href="mailto:lvxiang@ganji.com">simple</a>
 */
public class BeanInstantiationException extends BeansException {
    private static final long serialVersionUID = 4798530712509670751L;

    public BeanInstantiationException(String msg) {
        super(msg);
    }
    private Class<?> beanClass;

    @Nullable
    private Constructor<?> constructor;

    @Nullable
    private Method constructingMethod;


    /**
     * Create a new BeanInstantiationException.
     * @param beanClass the offending bean class
     * @param msg the detail message
     */
    public BeanInstantiationException(Class<?> beanClass, String msg) {
        this(beanClass, msg, null);
    }

    /**
     * Create a new BeanInstantiationException.
     * @param beanClass the offending bean class
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeanInstantiationException(Class<?> beanClass, String msg, Throwable cause) {
        super("Failed to instantiate [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    /**
     * Create a new BeanInstantiationException.
     * @param constructor the offending constructor
     * @param msg the detail message
     * @param cause the root cause
     * @since 4.3
     */
    public BeanInstantiationException(Constructor<?> constructor, String msg,  Throwable cause) {
        super("Failed to instantiate [" + constructor.getDeclaringClass().getName() + "]: " + msg, cause);
        this.beanClass = constructor.getDeclaringClass();
        this.constructor = constructor;
    }

    /**
     * Create a new BeanInstantiationException.
     * @param constructingMethod the delegate for bean construction purposes
     * (typically, but not necessarily, a static factory method)
     * @param msg the detail message
     * @param cause the root cause
     * @since 4.3
     */
    public BeanInstantiationException(Method constructingMethod, String msg,  Throwable cause) {
        super("Failed to instantiate [" + constructingMethod.getReturnType().getName() + "]: " + msg, cause);
        this.beanClass = constructingMethod.getReturnType();
        this.constructingMethod = constructingMethod;
    }
}
