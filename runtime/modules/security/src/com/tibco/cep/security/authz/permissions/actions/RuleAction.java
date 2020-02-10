package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class RuleAction extends AbstractAuthzAction {
	
	private static final long serialVersionUID = 3456789L;
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof RuleAction)) {
			return false;
		}
		RuleAction co = (RuleAction)action;
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
	public RuleAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RuleAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public RuleAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}



	public static final int READ = 0x2;
	
	public static final int ADD_IMPL = 0x4;
	
	public static final int DEL_IMPL = 0x8;
	
	public static final int CREATE = 0x16;
	
	public static final int DELETE = 0x32;
	
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
		return actionType;
	}
	
	
	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RuleAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RuleAction other = (RuleAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	public int hashCode() {
		return actionType.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public String toString() {
		return "[" + this.getClass().getSimpleName() + ":" + actionType + "]";
	}

}
