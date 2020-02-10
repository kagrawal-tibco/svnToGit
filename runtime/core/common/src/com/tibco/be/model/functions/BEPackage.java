package com.tibco.be.model.functions;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation to a java type enables it to be considered as a BusinessEvents&trade; function catalog
 *
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface BEPackage {
	
	/**
	 * @version 5.2.0
	 * @.category public-api
	 * @return {@link Enabled} annotation, if this annotation is not specified the default value is true
	 */
	Enabled enabled() default @Enabled(property="",value=true);
	
	
	/**
	 * @version 5.2.0
	 * @.category public-api
	 * @return The mandatory catalog name.
	 */
	String catalog();


    /**
     * @version 5.2.0
     * @.category public-api
     * @return The mandatory category name which follows the java package format i.e "x.y.z" package
     */
    String category();


    /**
     * @return The brief description of the catalog which also appears on a catalog tooltip.
     */
    String synopsis() default "<synopsis>";


}