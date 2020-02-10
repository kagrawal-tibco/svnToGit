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
 * This means that the annotated target must not be modified by the receiver/caller.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ReadOnly {
    String value() default "";
}