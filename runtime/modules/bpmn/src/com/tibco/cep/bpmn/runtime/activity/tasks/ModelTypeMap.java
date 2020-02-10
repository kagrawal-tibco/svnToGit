package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This ModelTypeMap annotation identifies a type mapping between Java Type and BE Model Type
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.METHOD})
public @interface ModelTypeMap {
	/**
	 * @.category public-api
	 * Returns {@link ModelType}, default {@link ModelType#VOID}
	 * @return
	 */
	ModelType type() default ModelType.VOID;
	/**
	 * @.category public-api
	 * Returns {@link String} uri, default ""
	 * @return
	 */
	String uri() default "";
}
