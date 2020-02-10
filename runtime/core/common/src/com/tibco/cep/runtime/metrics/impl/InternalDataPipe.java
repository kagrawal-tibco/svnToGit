package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataPipe;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 11:38:55 AM
*/
/**
 * Internal interface.
 */
public interface InternalDataPipe extends DataPipe {
    /**
     * @param data
     * @return <code>true</code> if the element was added to this queue, else <code>false</code>.
     */
    boolean offer(Data data);
}
