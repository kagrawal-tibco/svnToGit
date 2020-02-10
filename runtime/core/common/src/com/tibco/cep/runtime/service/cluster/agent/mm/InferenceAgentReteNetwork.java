/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.mm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * 
 * @author ggrigore
 *
 */
public class InferenceAgentReteNetwork implements InferenceAgentReteNetworkMBean {

	private InferenceAgent agent;
	
	public InferenceAgentReteNetwork(InferenceAgent agent) {
		this.agent = agent;
        registerMBean();
	}
	
    void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:type=Agent,agentId=" + 
            	this.agent.getAgentId() +
            	",subType=ReteWM,service=ReteNetwork-" + Thread.currentThread().getId());
            mbs.registerMBean(this, name);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
	public boolean getConcurrent() {
		if (this.agent != null && this.agent.getAgentConfig() != null) {
			return this.agent.getAgentConfig().isConcurrent();
		}
		else {
			return false;
		}
	}
	
	public long getTotalNumberRulesFired() {
		if (this.agent != null &&
			this.agent.getRuleSession() != null &&
			((RuleSessionImpl) this.agent.getRuleSession()).getWorkingMemory() != null) {
			return ((ReteWM)((RuleSessionImpl)this.agent.getRuleSession()).getWorkingMemory()).getTotalNumberRulesFired();
		}
		else {
			return 0;
		}
	}
	
	public void saveReteNetworkToXML() {
		if (this.agent != null &&
				this.agent.getRuleSession() != null &&
				((RuleSessionImpl) this.agent.getRuleSession()).getWorkingMemory() != null) {
			((ReteWM)((RuleSessionImpl)this.agent.getRuleSession()).getWorkingMemory()).saveReteNetworkToXML();
		}
	}
	
	public void saveReteNetworkToXML(String filename) {
		if (this.agent != null &&
				this.agent.getRuleSession() != null &&
				((RuleSessionImpl) this.agent.getRuleSession()).getWorkingMemory() != null) {
			((ReteWM)((RuleSessionImpl)this.agent.getRuleSession()).getWorkingMemory()).saveReteNetworkToXML(filename);
		}
	}
	
	public void saveReteNetworkToString() {
		if (this.agent != null &&
				this.agent.getRuleSession() != null &&
				((RuleSessionImpl) this.agent.getRuleSession()).getWorkingMemory() != null) {
			((ReteWM)((RuleSessionImpl)this.agent.getRuleSession()).getWorkingMemory()).saveReteNetworkToString();
		}
	}
	
	public void saveReteNetworkToString(String filename) {
		if (this.agent != null &&
				this.agent.getRuleSession() != null &&
				((RuleSessionImpl) this.agent.getRuleSession()).getWorkingMemory() != null) {
			((ReteWM)((RuleSessionImpl)this.agent.getRuleSession()).getWorkingMemory()).saveReteNetworkToString(filename);
		}
	}    

}
