package com.tibco.cep.runtime.service.tester.core;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * 
 * @author smarathe
 *
 */
public class ClearWorkingMemory {
	
	private RuleSession ruleSession;

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ClearWorkingMemory.class);


	public ClearWorkingMemory(RuleSession ruleSession) {
		this.ruleSession = ruleSession;
	}

	public void clearWorkingMemoryContents() {
		try {
			ruleSession.reset();
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error in resetting Working Memory Contents");
		}
	}
}
