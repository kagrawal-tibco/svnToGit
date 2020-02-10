package com.tibco.cep.studio.debug.core.model;

import java.util.Map;

import org.eclipse.debug.core.DebugException;

import com.tibco.cep.runtime.service.cluster.CacheAgent.Type;

/**
 * @author pdhar
 *
 */
public interface IStepHandler {

	boolean handlesSession(String currentRuleSessionName, Map<String, Type> targetAgentMap);

	void stepOver(final RuleDebugThread ruleDebugThread, final RuleDebugStackFrame frame) throws DebugException;

	void stepInto(final RuleDebugThread ruleDebugThread,final RuleDebugStackFrame frame) throws DebugException;
	
	void stepReturn(final RuleDebugThread ruleDebugThread,final RuleDebugStackFrame frame) throws DebugException;

}
