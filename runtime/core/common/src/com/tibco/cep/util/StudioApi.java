package com.tibco.cep.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Jul 22, 2010
 * Time: 11:28:09 AM
 *
 * Tag methods with this annotation to be exposed as APIs,
 * so only these methods could be javadocked.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface StudioApi {
}
