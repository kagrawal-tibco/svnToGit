package com.tibco.cep.runtime.service.tester.core;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * 
 * @author smarathe
 *
 */
public class WorkingMemoryManipulator {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WorkingMemoryManipulator.class);
	private RuleSession session;

	/**
	 * @param session
	 */
	public WorkingMemoryManipulator(RuleSession session) {
		this.session = session;
	}

	/**
	 * @param id
	 * @param columns
	 * @param properties
	 */
	public void manipulateConceptInstance( long id, String[] columns, String[] properties) {
		final ManipulateEntityRuleFunction manipulateRF = new ManipulateEntityRuleFunction();
		final Object[] args = new Object[2];
		Map<String, String> changedPropertiesMap = new HashMap<String, String>();
		for(int cnt = 0; cnt<columns.length; cnt++) {
			changedPropertiesMap.put(columns[cnt], properties[cnt]);
		}
		ConceptImpl concept = (ConceptImpl)session.getObjectManager().getElement(id);
		args[0] = concept;
		args[1] = changedPropertiesMap;
		RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
		if (threadSession == session) {
			LOGGER.log(Level.DEBUG, "------Already Inside the Rule Session----" + session.getName());
			((RuleSessionImpl)session).getWorkingMemory().invoke(manipulateRF, args);
		} else {
			((RuleSessionImpl)session).invokeFunction(manipulateRF, args, true);
		}
	}
	
	/**
	 * @param modifiedScorecardMap
	 */
	public void updateScorecardInstance(Map<Entity, Map<String, Object>> modifiedScorecardMap) {
		final UpdateScorecardRuleFunction updateRF = new UpdateScorecardRuleFunction();
		RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
		final Object[] args = new Object[1];
		args[0] = modifiedScorecardMap;
		if (threadSession == session) {
			LOGGER.log(Level.DEBUG, "------Already Inside the Rule Session----" + session.getName());
			((RuleSessionImpl)session).getWorkingMemory().invoke(updateRF, modifiedScorecardMap);
		} else {
			((RuleSessionImpl)session).invokeFunction(updateRF, args, true);
		}
	}
}
