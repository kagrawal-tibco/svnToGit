package com.tibco.cep.dashboard.integration.be;

import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentCapability;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.NewInferenceAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author anpatil
 *
 */
public final class DashboardAgent extends NewInferenceAgent {

	protected MessageGenerator messageGenerator;

    public DashboardAgent(AgentConfiguration agentConfig, RuleServiceProvider rsp, CacheAgent.Type type) throws Exception {
        super(agentConfig, rsp, type);
    }

	@Override
	protected void onInit() throws Exception {
		super.onInit();
		((DashboardSessionImpl)ruleSession).setAgentID(this.getAgentId());
		((DashboardSessionImpl)ruleSession).setAgentName(this.getAgentName());
	}

	@Override
	protected void onSuspend() throws Exception {
		((DashboardSessionImpl)ruleSession).suspend();
		super.onSuspend();
	}

	@Override
	protected void onResume() throws Exception {
		((DashboardSessionImpl)ruleSession).resume();
		super.onResume();
	}


    @Override
    public Type getAgentType() {
        return CacheAgent.Type.DASHBOARD;
    }

    @Override
    public void recover() throws Exception {
    	String recoverOnStartUpKey = "Agent." + getAgentName() + AgentCapability.RECOVERONSTARTUP.getPropSuffix();
    	boolean containsRecoverOnStartUpKey = ruleSession.getRuleServiceProvider().getProperties().containsKey(recoverOnStartUpKey);
    	if (containsRecoverOnStartUpKey == true) {
    		super.recover();
    	}
    }
}