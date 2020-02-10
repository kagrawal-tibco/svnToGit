package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author vdhumal
 * Action for RuleTemplate resources
 */
public class RuleTemplateInstanceAction extends RuleTemplateAction {

	private static final long serialVersionUID = 1826436952743896107L;

	/**
	 * @param mask
	 */
	public RuleTemplateInstanceAction(int mask) {
		super(mask);
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RuleTemplateInstanceAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
	}

	/**
	 * @param actionType
	 */
	public RuleTemplateInstanceAction(String actionType) {
		super(actionType);
	}
	
}
