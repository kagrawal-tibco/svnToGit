/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * Action for BE Process
 * @author vpatil
 */
public class BEProcessAction extends AbstractAuthzAction {

	public static final int NONE = 0x0;
	public static final int READ = 0x2;
	public static final int CREATE = 0x8;
	public static final int DELETE = 0x16;	
	public static final int MODIFY = 0x32;

	/**
	 * @param mask
	 */
	public BEProcessAction(int mask) {
		super(mask);
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public BEProcessAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
	}

	/**
	 * @param actionType
	 */
	public BEProcessAction(String actionType) {
		super(actionType);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.actions.IAction#implies(com.tibco.cep.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction action) {
		if (!(action instanceof BEProcessAction)) {
			return false;
		}
		BEProcessAction co = (BEProcessAction)action;
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
		return actionType;
	}

	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BEProcessAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		BEProcessAction other = (BEProcessAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}

}
