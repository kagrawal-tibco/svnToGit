package com.tibco.cep.runtime.service.om.api.invm;

import com.tibco.cep.runtime.service.Service;

/*
* Author: Ashwin Jayaprakash Date: Nov 13, 2008 Time: 1:38:53 PM
*/
public interface InProgressTxnKeeper extends Service {
    /**
     * @param id
     */
    void acquire(Long id);

    /**
     * @param id
     */
    void release(Long id);
}
