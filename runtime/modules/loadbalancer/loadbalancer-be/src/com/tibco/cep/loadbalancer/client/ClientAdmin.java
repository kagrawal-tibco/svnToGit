package com.tibco.cep.loadbalancer.client;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.service.Service;

/*
* Author: Ashwin Jayaprakash / Date: Jun 25, 2010 / Time: 3:25:07 PM
*/

public interface ClientAdmin extends Service {
    /**
     * Starts the client when called the first time.
     *
     * @param ruleSession
     * @return
     * @throws LifecycleException
     */
    Client getClientFor(RuleSession ruleSession) throws LifecycleException;
}
