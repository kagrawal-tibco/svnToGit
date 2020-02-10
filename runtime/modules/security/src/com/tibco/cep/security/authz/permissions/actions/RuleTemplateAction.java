package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author vdhumal
 * Action for RuleTemplate resources
 */
public class RuleTemplateAction extends AbstractAuthzAction {

	private static final long serialVersionUID = 5523237653460651897L;

	public static final int NONE = 0x0;
	
	public static final int READ = 0x2;
	
	public static final int ADD_INST = 0x4;
	
	public static final int CREATE = 0x8;
	
	public static final int DELETE = 0x16;	
		
	public static final int MODIFY = 0x32;
	
	public static final int DEL_INST = 0x64;

	/**
	 * @param mask
	 */
	public RuleTemplateAction(int mask) {
		super(mask);
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RuleTemplateAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
	}

	/**
	 * @param actionType
	 */
	public RuleTemplateAction(String actionType) {
		super(actionType);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof RuleTemplateAction)) {
			return false;
		}
		RuleTemplateAction co = (RuleTemplateAction)action;
		String actionType = co.getActionType();
		if (actionType != null) {
			//Get its mask
			int otherMask = co.getMask(actionType);
			if ((this.mask & otherMask) == otherMask) {
				if (Permit.ALLOW.equals(this.permit)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int getMask(String actionType) {
		int mask = NONE;
		if (actionType == null || actionType.trim().length() == 0) {
			return mask;
		}

		if ("read".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = READ;
	    } else if ("create".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CREATE;
	    } else if ("delete".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DELETE;
	    } else if ("modify".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = MODIFY;
	    } else if ("add_inst".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = ADD_INST;
	    } else if ("del_inst".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DEL_INST;
	    } else {
			throw new IllegalArgumentException("Illegal action value");
		}
		return mask;
	}
	
	protected String getActionType(int mask) {
		String actionType = null;
		if ((mask & READ) == READ) {
			actionType = "READ";
		}
		if ((mask & CREATE) == CREATE) {
			actionType = "CREATE";
		}
		if ((mask & DELETE) == DELETE) {
			actionType = "DELETE";
		}
		if ((mask & MODIFY) == MODIFY) {
			actionType = "MODIFY";
		}
		if ((mask & ADD_INST) == ADD_INST) {
			actionType = "ADD_INST";
		}
		if ((mask & DEL_INST) == DEL_INST) {
			actionType = "DEL_INST";
		}
		return actionType;
	}
	
	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RuleTemplateAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RuleTemplateAction other = (RuleTemplateAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}

}
