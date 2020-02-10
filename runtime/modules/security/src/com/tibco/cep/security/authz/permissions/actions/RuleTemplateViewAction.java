package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author vdhumal
 * Action for RuleTemplate resources
 */
public class RuleTemplateViewAction extends RuleTemplateAction {

	private static final long serialVersionUID = -2964946183821638808L;

	/**
	 * @param mask
	 */
	public RuleTemplateViewAction(int mask) {
		super(mask);
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RuleTemplateViewAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
	}

	/**
	 * @param actionType
	 */
	public RuleTemplateViewAction(String actionType) {
		super(actionType);
	}
}
