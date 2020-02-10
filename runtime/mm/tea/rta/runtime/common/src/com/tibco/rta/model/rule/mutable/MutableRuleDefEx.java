package com.tibco.rta.model.rule.mutable;

import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;

/**
 * @author vdhumal
 *
 */
public interface MutableRuleDefEx extends MutableRuleDef {

	/**
	 * @param commandListener
	 */
	void setCommandListener(RtaCommandListener commandListener);

	/**
	 * @return
	 */
	RtaCommandListener getCommandListener();

}
