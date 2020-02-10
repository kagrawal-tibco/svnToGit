/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class RuleFunctionAction extends AbstractAuthzAction {
	
	private static final long serialVersionUID = 3456789L;
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof RuleFunctionAction)) {
			return false;
		}
		RuleFunctionAction co = (RuleFunctionAction)action;
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
	
	
	
	/**
	 * @param mask
	 */
	public RuleFunctionAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RuleFunctionAction(String actionType, Permit permit,
			Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public RuleFunctionAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}



	public static final int READ = 0x2;
	
	public static final int ADD_IMPL = 0x4;
	
	public static final int DEL_IMPL = 0x8;
	
	public static final int CREATE = 0x16;
	
	public static final int DELETE = 0x32;
	
	public static final int INVOKE = 0x64;
	
	public static final int NONE = 0x0;
	
	//TODO Need to add constants for actions like modify declaration, modify body etc.	
	public int getMask(String actionType) {
		int mask = NONE;
		if (actionType == null || actionType.length() == 0) {
			return mask;
		}
		char[] a = actionType.toCharArray();
		int i = a.length - 1;
		if (i < 0) {
			// Check this condition just to be safe.
			return mask;
		}

		char c;

		// skip whitespace
		while ((i != -1)
				&& ((c = a[i]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
			i--;

		if ("read".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = READ;
	    } else if ("add_impl".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = ADD_IMPL;
	    } else if ("del_impl".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DEL_IMPL;
	    } else if ("create".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CREATE;
	    } else if ("delete".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DELETE;
	    } else if ("invoke".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = INVOKE;
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
		if ((mask & ADD_IMPL) == ADD_IMPL) {
			actionType = "ADD_IMPL";
		}
		if ((mask & DELETE) == DELETE) {
			actionType = "DELETE";
		}
		if ((mask & DEL_IMPL) == DEL_IMPL) {
			actionType = "ASSERT";
		}
		if ((mask & INVOKE) == INVOKE) {
			actionType = "INVOKE";
		}
		return actionType;
	}
	
	
	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RuleFunctionAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RuleFunctionAction other = (RuleFunctionAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
