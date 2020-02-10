package com.tibco.be.model.functions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation describes a BusinessEvents&trade; catalog function argument or return type.
 *
 * @version 5.2.0
 * @since 5.2.0
 * @.category public-api
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionParamDescriptor {
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The name of the argument or its identifier. If not specified the default value "<name>" is used.
	 */
	String name() default "<name>";
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The type of the argument or its identifier. If not specified the default value "<type>" is used.
	 * BusinessEvents&trade; recognizes a limited set of primitive types and domain specific data types which are different
	 * from Java types i.e. int ,long,double,DateTime,Concept,Event,Scorecard,Object are some of the accepted datatypes. To specifiy a 
	 * unknown data type , the opaque Object type should be used. If not specified the default value "<type>" is used.
	 */
	String type() default "<type>";
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return A brief description of the parameter/return value.If not specified the default value "<description>" is used.
	 */
	String desc() default "<description>";	
}
