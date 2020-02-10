package com.tibco.cep.query.model;

public interface FunctionArg extends RegistryContext, TypedContext {

    /**
     * Return the argument name
     * @return String
     */
    public String getName();

    /**
     * Set the argument name
     * @param name
     */
    public void setName(String name);

}
