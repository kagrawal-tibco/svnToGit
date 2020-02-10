/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.tools;

import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSToolRuleActionDefinition;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleAction;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleActionImplementer;

/**
 * @author dijadhav
 * 
 */
public class NewFlowElementRuleActionDefinition extends
		TSToolRuleActionDefinition implements TSWebToolRuleActionImplementer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4432159291161447885L;

	@Override
	public TSWebToolRuleAction newWebImplementation() {
		return new NewFlowElementToolRuleAction();
	}

	@Override
	public TSToolRuleActionDefinition getCopy() {
		return new NewFlowElementRuleActionDefinition();
	}

}
