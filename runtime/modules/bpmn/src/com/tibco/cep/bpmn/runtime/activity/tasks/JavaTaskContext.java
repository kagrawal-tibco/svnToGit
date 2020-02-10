package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




/**
 * 
 * This annotation identifies a task context field of a given {@link TaskContextType}
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JavaTaskContext {
	
	TaskContextType value();
	
}
