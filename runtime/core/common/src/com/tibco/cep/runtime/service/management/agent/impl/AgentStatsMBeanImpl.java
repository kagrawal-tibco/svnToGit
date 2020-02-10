package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.runtime.service.management.agent.AgentStatsMBean;
import com.tibco.cep.runtime.service.management.agent.ontology.OntologyMBeanFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/11/12
 * Time: 2:54 PM
 */

public class AgentStatsMBeanImpl implements AgentStatsMBean {
    private OntologyMBeanFactory ombf;


    public AgentStatsMBeanImpl(RuleServiceProvider rsp, RuleSession rs,int agentId) {
        ombf = new OntologyMBeanFactory(rsp, rs, agentId);
    }

    @Override
    public synchronized int register(String uri) throws StatsMBeanException {
        if (ombf == null)
            return 0;

        return ombf.registerMbean(uri);
    }

    @Override
    public synchronized int unregister(String uri) throws StatsMBeanException {
        if (ombf == null)
            return 0;

        return ombf.unRegisterMbean(uri);
    }
}
