package com.tibco.cep.repo;


import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;


/**
 * Manages the global variables available in a RuleServiceProvider.
 * See the TIBCO Designer documentation for details on creating global variables.
 *
 * @version 2.0.0
 * @.category public-api
 * @see com.tibco.cep.runtime.session.RuleServiceProvider
 * @since 2.0.0
 */
public interface GlobalVariables extends ResourceProvider {


    /**
     * Gets all information about a <code>GlobalVariable</code>.
     *
     * @param name of the variable.
     * @return a <code>GlobalVariable</code>.
     * @.category public-api
     * @since 2.0.0
     */
    GlobalVariableDescriptor getVariable(String name);


    /**
     * Gets all the global variables available in this <code>GlobalVariables</code>.
     *
     * @return a Collection of <code>GlobalVariable</code>.
     * @.category public-api
     * @since 2.0.0
     */
    Collection<GlobalVariableDescriptor> getVariables();


    void merge(GlobalVariables gvar);


    /**
     * Gets the <code>int</code> value of a global variable.
     *
     * @param varName      name of the global variable.
     * @param defaultValue value returned if the global variable was not set.
     * @return an <code>in</code>.
     * @.category public-api
     * @since 2.0.0
     */
    int getVariableAsInt(String varName, int defaultValue);


    /**
     * Gets the <code>long</code> value of a global variable.
     *
     * @param varName      name of the global variable.
     * @param defaultValue value returned if the global variable was not set.
     * @return a <code>long</code>.
     * @.category public-api
     * @since 2.0.0
     */
    long getVariableAsLong(String varName, long defaultValue);


    /**
     * Gets the <code>double</code> value of a global variable.
     *
     * @param varName      name of the global variable.
     * @param defaultValue value returned if the global variable was not set.
     * @return a <code>double</code>.
     * @.category public-api
     * @since 2.0.0
     */
    double getVariableAsDouble(String varName, double defaultValue);


    /**
     * Gets the <code>String</code> value of a global variable.
     *
     * @param varName      name of the global variable.
     * @param defaultValue value returned if the global variable was not set.
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0.0
     */
    String getVariableAsString(String varName, String defaultValue);


    /**
     * Gets the <code>boolean</code> value of a global variable.
     *
     * @param varName      name of the global variable.
     * @param defaultValue value returned if the global variable was not set.
     * @return a <code>boolean</code>.
     * @.category public-api
     * @since 2.0.0
     */
    boolean getVariableAsBoolean(String varName, boolean defaultValue);


    XiNode toXiNode() throws Exception;


    /**
     * Returns the result of substituting all global variables contained in the given <code>CharSequence</code>
     * with their value. Undefined global variables will not be substituted.
     *
     * @param text a <code>CharSequence</code> that may contain global variables.
     * @return another <code>CharSequence</code> that contains the result of substituting all global variables
     *         contained in the given <code>CharSequence</code> with their value.
     * @.category public-api
     * @since 2.0.0
     */
    CharSequence substituteVariables(CharSequence text);

    SmElement toSmElement();
    
    SmElement toSmElement(boolean includeDebuggerVars);  

    void overwriteGlobalVariables(Properties props);
    
    void validateGlobalVariables();

    void setVariables(Map<String,GlobalVariableDescriptor> vars);
}
