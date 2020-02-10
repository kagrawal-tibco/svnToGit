/**
 * 
 */
package com.tibco.be.model.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation element identifies if its associated method is deprecated or not.
 *
 * @version 5.2.0
 * @since 5.2.0
 * @.category public-api
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface BEDeprecated {
	
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The suggested alternative option
	 */
	String alternative() default "";
	
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return <code>true</code> if deprecated or else <code>false</code> 
	 */
	boolean value() default false;
}
