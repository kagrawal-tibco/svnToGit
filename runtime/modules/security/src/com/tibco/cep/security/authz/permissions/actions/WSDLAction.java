package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

public class WSDLAction extends SharedResourceAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2192835422614841884L;

	public boolean implies(final IAction other) {
		if (!(other instanceof WSDLAction)) {
			return false;
		}
		WSDLAction co = (WSDLAction)other;
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
	
	
	
	public WSDLAction(final String actionType) {
		super(actionType);
	}
	
	public WSDLAction(final int mask) {
		super(mask);
	}
	
	public WSDLAction(final String actionType,
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
		if (!(obj instanceof WSDLAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		WSDLAction other = (WSDLAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
