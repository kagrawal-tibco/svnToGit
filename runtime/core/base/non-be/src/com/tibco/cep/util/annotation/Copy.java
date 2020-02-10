package com.tibco.cep.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 6:01:23 PM
*/

/**
 * This means that the annotated target is a copy of the one being queried.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Copy {
    String value() default "";
}