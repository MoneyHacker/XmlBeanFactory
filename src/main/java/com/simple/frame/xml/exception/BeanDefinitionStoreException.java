package com.simple.frame.xml.exception;

import com.sun.istack.internal.Nullable;
import org.xml.sax.SAXException;

/**
 * Created by lvxiang@ganji.com 2018/12/6 15:29
 *
 * @author <a href="mailto:lvxiang@ganji.com">simple</a>
 */
public class BeanDefinitionStoreException extends BeansException {
    private static final long serialVersionUID = -1604659732389790896L;

    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }
    /**
     * Create a new BeanDefinitionStoreException.
     * @param msg the detail message (used as exception message as-is)
     * @param cause the root cause (may be {@code null})
     */
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanDefinitionStoreException(String resourceDescription, String msg, Throwable cause) {
        super(resourceDescription + msg, cause);
    }
}
