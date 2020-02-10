package com.tibco.cep.dashboard.management;

import java.util.HashMap;

import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public final class ServiceContext extends HashMap<String,Object> {

	private static final long serialVersionUID = -3343388919405441440L;
	
//	private static final String BAR_RESOURCE_KEY = "barresource";
	
	private static final String RULE_SESSION_KEY = "rulesession";
	
	private static final String RULE_SERVICE_PROVIDER_KEY = "ruleserviceprovider";
	
//	void setBEArchiveResource(BEArchiveResource beArchiveResource){
//		put(BAR_RESOURCE_KEY,beArchiveResource);
//	}
//	
//	public BEArchiveResource getBEArchiveResource(){
//		return (BEArchiveResource) get(BAR_RESOURCE_KEY);
//	}
	
	void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider){
		put(RULE_SERVICE_PROVIDER_KEY,ruleServiceProvider);
	}
	
	public RuleServiceProvider getRuleServiceProvider(){
		return (RuleServiceProvider) get(RULE_SERVICE_PROVIDER_KEY);
	}
	
	void setRuleSession(DashboardSession session){
		put(RULE_SESSION_KEY,session);
	}
	
	public DashboardSession getRuleSession(){
		return (DashboardSession) get(RULE_SESSION_KEY);
	}	

}
