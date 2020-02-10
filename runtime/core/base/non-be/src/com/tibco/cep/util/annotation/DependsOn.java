package com.tibco.cep.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
* Author: Ashwin Jayaprakash / Date: Oct 12, 2010 / Time: 2:34:45 PM
*/

/**
 * Used to mark init and start methods - to indicate that the listed classes must already by initialized before calling
 * the annotated resource.
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DependsOn {
    Class[] value();
}
