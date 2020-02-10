package com.tibco.cep.runtime.service.decision.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Apr 14, 2009
 * Time: 7:24:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DTImpl extends VRFImpl
{
    static final String PRIORITY_METHOD_NAME = "priority";
    static final String ONE_ROW_ONLY_METHOD_NAME = "isOneRowOnly";
    static final String INVOKE_EXPLICIT_RETURN_METHOD_NAME = "invoke_explicitReturn";
    
    int priority();
    boolean isOneRowOnly();
    //Return value is true if the table explicity returned
    //instead of completing normally.
    //Tables with the one-row-only setting will explicity return when a row matches.
    boolean invoke_explicitReturn(Object[] args);
}
