/**
 * 
 */
package com.tibco.cep.bpmn.runtime.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * This annotation identifies a Java task function
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JavaTaskMethod {

}
