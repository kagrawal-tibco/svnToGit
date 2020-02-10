package com.tibco.cep.webstudio.server.ui.tools;

import com.tomsawyer.util.shared.TSUserAgent;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSDesktopToolRuleAction;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSDesktopToolRuleActionImplementer;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSToolRuleActionDefinition;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleAction;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleActionImplementer;

/**
 * 
 * @author dijadhav
 * 
 */
public class NewBetweenToolRuleActionDefinition extends
		TSToolRuleActionDefinition implements
		TSDesktopToolRuleActionImplementer, TSWebToolRuleActionImplementer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6564157211630604321L;

	public TSDesktopToolRuleAction newDesktopImplementation(
			TSUserAgent userAgent) {
		return null;
	}

	public TSWebToolRuleAction newWebImplementation() {
		return new NewBetweenToolRuleAction();
	}

	public TSToolRuleActionDefinition getCopy() {
		return new NewBetweenToolRuleActionDefinition();
	}
}
