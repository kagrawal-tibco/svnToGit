package com.tibco.cep.runtime.service.tester.core;

import com.tibco.cep.runtime.service.debug.AbstractDebugResponseTask;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * 
 * @author smarathe
 *
 */
public class ClearWorkingMemoryResponseTaskImpl extends AbstractDebugResponseTask{

	private String ruleSessionName;
	
	 public ClearWorkingMemoryResponseTaskImpl(String ruleSessionName) {
		super();
		this.ruleSessionName = ruleSessionName;
	}

	public void run() {
		 DebuggerService debuggerService = DebuggerService.getInstance();
	     RuleServiceProvider ruleServiceProvider = debuggerService.getRuleServiceProvider();
	     RuleSession ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession(ruleSessionName);
	     ClearWorkingMemory cwm = new ClearWorkingMemory(ruleSession);
	     cwm.clearWorkingMemoryContents();
	 }
}
