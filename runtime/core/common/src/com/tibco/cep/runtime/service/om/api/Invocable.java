/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import java.io.Serializable;
import java.util.Map;

import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * 
 * @author bgokhale
 * 
 * Interface for remote invocations
 * 
 * @see Invoker
 */

public interface Invocable extends Serializable {

	/**
	 * Invocation result is wrapped in a Result object
	 * 
	 */
    interface Result extends SerializableLite {
    	/**
    	 * 
    	 * @return Serializable/SerializableLite object returned by the invocation
    	 */
    	Object getResult();
    	
    	/**
    	 * 
    	 * @return Invocation status
    	 */
        Status getStatus();
        
        /**
         * 
         * @return Exception if any, during invoking the invoke method on the remote side
         */
        Exception getException();
    }

    /**
     * Client side can query for result status
     *
     */
    enum Status {
        SUCCESS,
        ERROR,
    }
    
    /**
     * 
     * @param entry The matching entry that caused this invocation, due to an entry set matching a filter
     * as defined by Invoker.invoke(Filter filter, Invocable invocable) or by a matching key as specified by 
     * Invoker.invokeWithKey(Object key, Invocable invocable)
     * @return An Object that is Serializable or SerializableLite
     * @throws Exception
     */
	Object invoke(Map.Entry entry) throws Exception;
}
