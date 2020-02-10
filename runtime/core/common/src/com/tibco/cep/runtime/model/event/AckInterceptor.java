package com.tibco.cep.runtime.model.event;

/*
* Author: Ashwin Jayaprakash / Date: Jul 26, 2010 / Time: 11:26:10 AM
*/

public interface AckInterceptor {
    /**
     * @return Return <code>true</code> to allow further processing in the chain. <code>false</code> to prevent/stop
     *         further processing.
     */
    boolean acknowledge();

    boolean rollBack();

}
