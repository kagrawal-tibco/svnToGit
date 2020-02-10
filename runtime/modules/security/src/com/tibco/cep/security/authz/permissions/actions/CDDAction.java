package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * 
 * @author yrajput
 *
 */
public class CDDAction extends SharedResourceAction {

	private static final long serialVersionUID = 2995710276488742036L;

	public boolean implies(final IAction other) {
		if (!(other instanceof CDDAction)) {
			return false;
		}
		CDDAction co = (CDDAction)other;
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
	
	public CDDAction(final String actionType) {
		super(actionType);
	}
	
	public CDDAction(final int mask) {
		super(mask);
	}
	
	public CDDAction(final String actionType,
			                     final Permit permit,
			                     final Principal principal) {
		super(actionType, permit, null);
	}
	
	public static final int CREATE = 0x1;
	
	public static final int READ = 0x2;
		
	public static final int NONE = 0x0;
	
	public static final int ALL = 0x128;
	
	/**
	 * Two actions are equal if their type/mask
	 * is same. Use <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CDDAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		CDDAction other = (CDDAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}

}
