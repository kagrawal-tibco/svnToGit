package com.tibco.cep.runtime.service.decision.impl;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 13, 2009
 * Time: 8:48:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface VRFImpl {
    static final String INVOKE_METHOD_NAME = "invoke";
    static final String ISVOID_METHOD_NAME = "isVoid";
    
    Object invoke(Object[] args);
    boolean isVoid();
}
