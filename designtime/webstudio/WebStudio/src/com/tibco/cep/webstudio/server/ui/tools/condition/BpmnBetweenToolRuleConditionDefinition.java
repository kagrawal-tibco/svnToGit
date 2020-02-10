package com.tibco.cep.webstudio.server.ui.tools.condition;

import com.tomsawyer.util.shared.TSUserAgent;
import com.tomsawyer.view.behavior.toolcustomization.rule.condition.TSDesktopToolRuleCondition;
import com.tomsawyer.view.behavior.toolcustomization.rule.condition.TSDesktopToolRuleConditionImplementer;
import com.tomsawyer.view.behavior.toolcustomization.rule.condition.TSToolRuleConditionDefinition;
import com.tomsawyer.view.behavior.toolcustomization.rule.condition.TSWebToolRuleCondition;
import com.tomsawyer.view.behavior.toolcustomization.rule.condition.TSWebToolRuleConditionImplementer;

/**
 * This class is used to define the condition definition.
 * 
 * @author dijadhav
 *
 */
public class BpmnBetweenToolRuleConditionDefinition extends
		TSToolRuleConditionDefinition implements
		TSDesktopToolRuleConditionImplementer,
		TSWebToolRuleConditionImplementer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tomsawyer.view.behavior.toolcustomization.rule.condition.
	 * TSWebToolRuleConditionImplementer#newWebImplementation()
	 */
	@Override
	public TSWebToolRuleCondition newWebImplementation() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tomsawyer.view.behavior.toolcustomization.rule.condition.
	 * TSDesktopToolRuleConditionImplementer
	 * #newDesktopImplementation(com.tomsawyer.util.shared.TSUserAgent)
	 */
	@Override
	public TSDesktopToolRuleCondition newDesktopImplementation(TSUserAgent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tomsawyer.view.behavior.toolcustomization.rule.condition.
	 * TSToolRuleConditionDefinition#getCopy()
	 */
	@Override
	public TSToolRuleConditionDefinition getCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
