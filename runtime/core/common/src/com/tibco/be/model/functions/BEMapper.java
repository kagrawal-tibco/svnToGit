package com.tibco.be.model.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates if the function is enabled for use in a mapper.
 * @since 5.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface BEMapper {
	/**
	 * @return {@link Enabled} annotation, if this annotation is not specified the default value is true
	 */
	Enabled enabled() default @Enabled(value=false);
	
	/**
	 * @return The enumerated {@link MapperElementType} type. If not specified the default value is {@link MapperElementType#UNKNOWN}
	 */
	MapperElementType  type() default MapperElementType.UNKNOWN;
	/**
	 * @return The  string representation schema to be used in the mapper right hand side pane as a default schema until a typed schema is chosen for the function use.
	 */
	String inputelement() default "";

}
