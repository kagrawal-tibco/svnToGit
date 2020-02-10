package com.tibco.cep.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* Author: Ashwin Jayaprakash / Date: Oct 29, 2009 / Time: 3:00:55 PM
*/

/**
 * Could also mean concurrent. Values can change because of another thread but the values will be safely published to
 * all threads.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ThreadSafe {
    String value() default "";
}