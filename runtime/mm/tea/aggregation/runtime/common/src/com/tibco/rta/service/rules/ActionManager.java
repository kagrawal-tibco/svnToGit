package com.tibco.rta.service.rules;


import java.util.Properties;

import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public interface ActionManager {
	
	void scheduleAction(RuleMetricNodeState actionState); 
	
	void removeActionSchedule(RuleMetricNodeState actionState);
	
	void init(Properties configuration) throws Exception;
	
	void start() throws Exception;
	
	void stop() throws Exception;

}