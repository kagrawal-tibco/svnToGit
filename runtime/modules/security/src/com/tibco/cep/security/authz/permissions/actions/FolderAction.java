package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class FolderAction extends AbstractAuthzAction {
	
	private static final long serialVersionUID = 3456784645L;
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof FolderAction)) {
			return false;
		}
		FolderAction co = (FolderAction)action;
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
	
	public FolderAction(final String actionType) {
		super(actionType);
	}
	
	public FolderAction(final int mask) {
		super(mask);
	}
	
	public FolderAction(final String actionType,
			            final Permit permit,
			            final Principal principal) throws RuntimeException {
		
		super(actionType, permit, principal);
	}
	
	public static final int READ = 0x2;
	
	public static final int ADD_RESOURCE = 0x4;
	
	public static final int DEL_RESOURCE = 0x8;
	
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
	    } else if ("add_resource".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = ADD_RESOURCE;
	    } else if ("del_resource".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DEL_RESOURCE;
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
		if ((mask & ADD_RESOURCE) == ADD_RESOURCE) {
			actionType = "ADD_RESOURCE";
		}
		if ((mask & DELETE) == DELETE) {
			actionType = "DELETE";
		}
		if ((mask & DEL_RESOURCE) == DEL_RESOURCE) {
			actionType = "DEL_RESOURCE";
		}
		return actionType;
	}
	
	public String getActionType() {
		return this.actionType;
	}

	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FolderAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FolderAction other = (FolderAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}

