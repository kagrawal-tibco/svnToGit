package com.tibco.cep.runtime.util;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/
public class CustomBEManagedThread extends BEManagedThread {
    protected Runnable job;

    public CustomBEManagedThread(ThreadGroup group, Runnable target, String name,
                                 RuleServiceProvider rsp) {
        super(group, target, name, rsp);

        this.job = target;
    }

    @Override
    public void run() {
        try {
            job.run();
        }
        catch (Throwable t){
            logger.log(Level.ERROR, t, t.getMessage());
        }
        finally {
            ((RuleServiceProviderImpl) RSP).unregisterExecutableResource(this);
        }
    }
}
