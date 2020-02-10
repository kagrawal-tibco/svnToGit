/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class ProjectAction extends AbstractAuthzAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357245890031522749L;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof ProjectAction)) {
			return false;
		}
		ProjectAction co = (ProjectAction)action;
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
	public ProjectAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public ProjectAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public ProjectAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}



	public static final int CHECKOUT = 0x2;
	
	public static final int COMMIT = 0x4;
	
	public static final int UPDATE = 0x8;
	
	public static final int NONE = 0x0;
	
	public static final int GEN_DEPLOY = 0x16;
	
	public static final int APPROVAL = 0x32;
	
	public static final int MANAGE_LOCKS = 0x64;
	
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

		if ("checkout".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CHECKOUT;
	    } else if ("commit".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = COMMIT;
	    } else if ("update".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = UPDATE;
	    } else if ("gen_deploy".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = GEN_DEPLOY;
	    } else if ("approval".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = APPROVAL;
	    } else if ("manage_locks".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = MANAGE_LOCKS;
	    } else {
			throw new IllegalArgumentException("Illegal action value");
		}
		return mask;
	}

	protected String getActionType(int mask) {
		String actionType = null;
		if ((mask & CHECKOUT) == CHECKOUT) {
			actionType = "READ";
		}
		if ((mask & COMMIT) == COMMIT) {
			actionType = "COMMIT";
		}
		if ((mask & UPDATE) == UPDATE) {
			actionType = "UPDATE";
		}
		if ((mask & GEN_DEPLOY) == GEN_DEPLOY) {
			actionType = "GEN_DEPLOY";
		}
		if ((mask & APPROVAL) == APPROVAL) {
			actionType = "APPROVAL";
		}
		if ((mask & MANAGE_LOCKS) == MANAGE_LOCKS) {
			actionType = "MANAGE_LOCKS";
		}
		return actionType;
	}
	
	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProjectAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ProjectAction other = (ProjectAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
