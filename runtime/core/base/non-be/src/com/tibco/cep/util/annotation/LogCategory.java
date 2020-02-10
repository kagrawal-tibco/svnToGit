package com.tibco.cep.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* Author: Ashwin Jayaprakash / Date: Dec 17, 2009 / Time: 2:57:54 PM
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Inherited
public @interface LogCategory {
    String value();
}
