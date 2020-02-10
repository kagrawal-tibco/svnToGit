/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class FunctionsCatalogAction extends AbstractAuthzAction {
	
	private static final long serialVersionUID = 8498429874982L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction other) {
		if (!(other instanceof FunctionsCatalogAction)) {
			return false;
		}
		FunctionsCatalogAction co = (FunctionsCatalogAction) other;
		String actionType = co.getActionType();
		if (actionType != null) {
			// Get its mask
			int otherMask = co.getMask(actionType);
			//Note : experimental logic
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
	public FunctionsCatalogAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public FunctionsCatalogAction(String actionType, Permit permit,
			Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public FunctionsCatalogAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}



	public static final int READ = 0x2;

	// Add/remove concept properties/attributes
	public static final int INVOKE = 0x16;

	public static final int NONE = 0x0;

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
	    } else if ("invoke".equalsIgnoreCase(actionType.trim().intern())) {
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
		if (!(obj instanceof FunctionsCatalogAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FunctionsCatalogAction other = (FunctionsCatalogAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
