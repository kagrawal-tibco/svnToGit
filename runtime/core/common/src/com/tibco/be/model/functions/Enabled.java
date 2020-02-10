package com.tibco.be.model.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation allows the user to specify if an associated annotation is enabled or disabled for use
 *
 * @version 5.2.0
 * @since 5.2.0
 * @.category public-api
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Enabled {
	
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The system property override that is used to enable or disable the associated {@link BEFunction} or
	 * {@link BEPackage} annotation. 
	 */
	String property() default "";
	
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The value of the specified annotation, if not specified the default value is true.
	 */
	boolean value() default true;

}
