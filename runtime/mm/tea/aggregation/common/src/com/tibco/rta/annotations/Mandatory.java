package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/5/13
 * Time: 10:12 AM
 * Applies to properties which should not have null values.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Mandatory {
}
