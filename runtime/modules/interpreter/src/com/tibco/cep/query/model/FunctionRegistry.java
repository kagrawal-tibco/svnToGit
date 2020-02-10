package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Resolvable;


public interface FunctionRegistry
        extends NamedContext, RegistryContext, Resolvable {

    /**
     * Returns the array of AggregateFunctions
     * @return Function[]
     */
    Function[] getAggregateFunctions();

    /**
     * Returns the array of CatalogFunctions
     * @return Function[]
     */
    Function[] getCatalogFunctions();

    /**
     * Returns the array of RuleFunctions
     * @return Function[]
     */
    Function[] getRuleFunctions();


    /**
     * Looks up a function by path.
     * @param path String path of the Function
     * @return Function found at the path, or null if not found.
     */
    Function getFunctionByPath(String path);


    boolean resolveContext(
            boolean isQueryContext)
            throws Exception;

}
