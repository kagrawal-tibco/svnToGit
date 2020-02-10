package com.tibco.cep.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* Author: Ashwin Jayaprakash / Date: Jun 11, 2010 / Time: 5:43:14 PM
*/


/**
 * This means that the annotated target should handle repetitive calls gracefully by ignoring subsequent invocations.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface Idempotent {
    String value() default "";
}
