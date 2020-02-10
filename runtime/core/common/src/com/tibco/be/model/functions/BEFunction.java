package com.tibco.be.model.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This method annotation enables a static java function to become eligible to be considered as a catalog function in BusinessEvents&trade;
 *
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})

public @interface BEFunction {

	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return {@link Enabled} annotation value, if not specified the default {@link Enabled} 
	 * annotation is used with an empty property string with a <code>true</code> value. It also
	 * indicates if the associated {@link BEFunction} is enabled for use.
	 */
	Enabled enabled() default @Enabled(property="",value=true);
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return {@link BEDeprecated} annotation value, if not specified the default {@link BEDeprecated} 
	 * annotation is used to indicate if the function has been deprecated in the BusinessEvents&trade; catalog.
	 */
	BEDeprecated deprecated() default @BEDeprecated();
	/**
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return The name of the function which matches the name of the function on which the {@link BEFunction} appears.
	 * If not specified the default "<name>" value is used for showing tooltips.
	 */
	String name() default "<name>";
    /**
     * @deprecated Replaced by {@link BEFunction#description()}
     * @return A brief synopsis of the function. If not specified the default value "<synopsis>" is used for showing tooltips.
     */
    String synopsis() default "<synopsis>";
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return The usage signature of the function. If not specified the default value "<signature>" is used for showing tooltips.
     */
    String signature() default "<signature>";
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return {@link FunctionParamDescriptor} annotation list identifying the function arguments in order, if not specified empty {@link FunctionParamDescriptor} 
	 * list is used.
	 */
    FunctionParamDescriptor[] params() default { };
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 * @return {@link FunctionParamDescriptor}  identifying the function return type, if not specified empty {@link FunctionParamDescriptor} 
	 * is used.
	 */
    FunctionParamDescriptor freturn() default @FunctionParamDescriptor();
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return The version of BusinessEvents&trade; from which the function was release as public API. If not specified the default value "<version>" is used for showing tooltips.
     */
    String version() default "<version>";
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return A string description of any related information of the specified function.
     */
    String see() default "<see>";
    /**
	 * @return {@link BEMapper} annotation value, if not specified the default {@link BEMapper} 
	 * annotation is used to indicate if the function used in a mapper. If not specified a default value of {@link BEMapper} is used.
	 */
    BEMapper mapper() default @com.tibco.be.model.functions.BEMapper(enabled = @Enabled(value=false), inputelement = "", type = MapperElementType.UNKNOWN);

    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return a brief description of the function. if not specified a default value "<description>" is returned.
     */
    String description() default "<description>";
    
    /**
     * @return A boolean value indicating if the function is used asynchronously during rule execution. The default value is <code>false</code>.
     */
    boolean async() default false;
    /**
     * @return A boolean value indicating if the use of this function will cause the rules to be evaluated again. The default value is <code>false</code>.
     */
    boolean reevaluate() default false;
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return A brief caution regarding the use of the function. If not specified the default value "<cautions>" is used.
     */
    String cautions() default "<cautions>";
    /**
     * @deprecated Replaced by {@link BEFunction#fndomain()}
     * @return A domain identifier to distinguish where the function can be used.
     */
    String[] domain() default { };
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return A domain identifier to distinguish where the function can be used. If not specified default {@link FunctionDomain#ACTION} is used.
     */
    FunctionDomain[] fndomain() default { FunctionDomain.ACTION };
    
    /**
     * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
     * @return A short example of how the function should be used. If not specified the default value "<example>" is used.
     */
    String example() default "<example>";
}
