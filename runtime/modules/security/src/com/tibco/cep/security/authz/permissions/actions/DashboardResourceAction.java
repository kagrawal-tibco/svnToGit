/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * 
 * @author Vikram Patil
 */
public class DashboardResourceAction extends AbstractAuthzAction {
	
	public static final int CREATE = 0x1;
	public static final int READ = 0x2;
	public static final int DELETE = 0x8;
	public static final int NONE = 0x0;
	public static final int ALL = 0x128;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1669029101148219928L;	
	
	public DashboardResourceAction(final String actionType) {
		super(actionType);
	}
	
	public DashboardResourceAction(final int mask) {
		super(mask);
	}
	
	public DashboardResourceAction(final String actionType,
			                     final Permit permit,
			                     final Principal principal) {
		super(actionType, permit, null);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(final IAction other) {
		if (!(other instanceof DashboardResourceAction)) {
			return false;
		}
		DashboardResourceAction co = (DashboardResourceAction)other;
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.AbstractAuthzAction#getMask(java.lang.String)
	 */
	@Override
	protected int getMask(String actionType) {
		int mask = NONE;
		if (actionType == null || actionType.length() == 0) {
			return mask;
		}
		char[] a = actionType.toCharArray();
		int i = a.length - 1;
		if (i < 0) {
			//Check this condition just to be safe.
			return mask;
		}
		
	    char c;
	    // skip whitespace
	    while ((i!=-1) && ((c = a[i]) == ' ' ||
			       c == '\r' ||
			       c == '\n' ||
			       c == '\f' ||
			       c == '\t'))
		i--;
	    
	    if ("read".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = READ;
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.AbstractAuthzAction#getActionType(int)
	 */
	@Override
	protected String getActionType(int mask) {
		String actionType = null;
		if ((mask & READ) == READ) {
			actionType = "READ";
		}
		if ((mask & CREATE) == CREATE) {
			actionType = "CREATE";
		}
		if ((mask & NONE) == NONE) {
			actionType = "NONE";
		}
		return actionType;
	}
	
	/**
	 * Two actions are equal if their type/mask
	 * is same. Use <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DashboardResourceAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		DashboardResourceAction other = (DashboardResourceAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
