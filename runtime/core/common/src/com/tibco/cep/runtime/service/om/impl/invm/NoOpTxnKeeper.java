package com.tibco.cep.runtime.service.om.impl.invm;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.om.api.invm.InProgressTxnKeeper;

/*
* Author: Ashwin Jayaprakash Date: Nov 17, 2008 Time: 4:19:26 PM
*/
public class NoOpTxnKeeper implements InProgressTxnKeeper {
    public NoOpTxnKeeper() {
    }

    public String getId() {
        return getClass().getSimpleName() + ":" + System.identityHashCode(this);
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    //------------

    public void acquire(Long id) {
        //Do nothing.
    }

    public void release(Long id) {
        //Do nothing.
    }
}
