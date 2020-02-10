package com.tibco.cep.kernel.model.rule;


import java.util.Map;


/**
 * Runtime rule function.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface RuleFunction {


    /**
     * Invokes the rule function with an array of objects.
     * The objects in the array have to follow the same order as the input parameters.
     * Primitive types have to be boxed.
     * Also, the return value will be boxed if it is primitive type.
     *
     * @param objects the input parameters for the rule function.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * @.category public-api
     * @since 2.0.0
     */
    Object invoke(Object[] objects);


    /**
     * Invokes the rule function with a map.
     * The input map should map the parameter name to its value.
     * Input parameters of primitive type have to be boxed.
     * The return value will be boxed if it is a primitive type.
     *
     * @param maps the input parameters for the rule function,
     *             maps the parameter name to the corresponding value.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * @.category public-api
     * @since 2.0.0
     */
    Object invoke(Map maps);


    /**
     * Returns the original signature of the <code>RuleFunction</code> in string format.
     *
     * @return the original signature of the <code>RuleFunction</code> in string format
     * @.category public-api
     * @since 2.0.0
     */
    String getSignature();


    /**
     * Gets all the parameter descriptors for this rule function.
     * Each <code>ParameterDescriptor</code> describe a parameter of this <code>RuleFunction</code>,
     * except the last one, which has the name <code>"returnValue"</code> and describes the return type.
     *
     * @return a <code>ParameterDescriptor[]</code> for this <code>RuleFunction</code>.
     * @.category public-api
     * @since 2.0.0
     */
    ParameterDescriptor[] getParameterDescriptors();


    /**
     * Describes a {@link RuleFunction RuleFunction} parameter.
     *
     * @.category public-api
     * @since 2.0.0
     */
    interface ParameterDescriptor {


        /**
         * Gets the name of this parameter.
         *
         * @return name of a parameter.
         * @.category public-api
         * @since 2.0.0
         */
        String getName();


        /**
         * Gets the type or the boxed type (if primitive) of this parameter.
         *
         * @return type of a parameter.
         * @.category public-api
         * @since 2.0.0
         */
        Class getType();


        /**
         * Returns <code>true</code> if this is an input parameter.
         *
         * @return <code>true</code> for input parameter, <code>false</code> for output parameter.
         * @.category public-api
         * @since 2.0.0
         */
        boolean isInput();
    }
}
