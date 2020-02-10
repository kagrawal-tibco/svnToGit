package com.tibco.cep.runtime.service.tester.core;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.debug.AbstractDebugResponseTask;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

public class WorkingMemoryManipulatorResponseTaskImpl extends AbstractDebugResponseTask{
	 private String ruleSessionName;

	    private Long id;
	    private String[] index;
	    private String[] properties;

	    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WorkingMemoryManipulatorResponseTaskImpl.class);

	    /**
	     * @param ruleSessionName
	     * @param id
	     * @param index
	     * @param properties
	     */
	    public WorkingMemoryManipulatorResponseTaskImpl(String ruleSessionName, Long id, String[] index, String[] properties) {
	        this.ruleSessionName = ruleSessionName;
	        this.id = id;
	        this.index = index;
	        this.properties = properties;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Runnable#run()
	     */
	    public void run() {
	        DebuggerService debuggerService = DebuggerService.getInstance();
	        RuleServiceProvider ruleServiceProvider = debuggerService.getRuleServiceProvider();
	        RuleSession ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession(ruleSessionName);
	        WorkingMemoryManipulator workingMemoryManipulator = new WorkingMemoryManipulator(ruleSession);
	       	workingMemoryManipulator.manipulateConceptInstance(id, index, properties);
	    }
}
