/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 * 
 */
public class EventAction extends AbstractAuthzAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7261356395113423519L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction)
	 */
	public boolean implies(IAction other) {
		if (!(other instanceof EventAction)) {
			return false;
		}
		EventAction co = (EventAction) other;
		String actionType = co.getActionType();
		if (actionType != null) {
			// Get its mask
			int otherMask = co.getMask(actionType);
			//Note : experimental logic
			if (((this.mask & otherMask) == otherMask)
					|| (this.mask & otherMask) == READ) {
				if (Permit.ALLOW.equals(this.permit)) {
					return true;
				}
			}
		}
		return false;
	}

	public EventAction(final String actionType) {
		super(actionType);
	}

	public EventAction(final int mask) {
		super(mask);
	}

	public EventAction(final String actionType, 
			           final Permit permit,
			           final Principal principal) throws RuntimeException {
		super(actionType, permit, principal);
	}

	public static final int CREATE = 0x1;

	public static final int READ = 0x2;

	// Add/remove concept properties/attributes
	public static final int MODIFY = 0x16;

	public static final int DELETE = 0x32;

	public static final int SEND = 0x256;

	public static final int ASSERT = 0x512;
	
	public static final int REPLY = 0x1024;

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
	    } else if ("modify".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = MODIFY;
	    } else if ("create".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CREATE;
	    } else if ("delete".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DELETE;
	    } else if ("send".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = SEND;
	    } else if ("assert".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = ASSERT;
	    } else if ("reply".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = REPLY;
	    }
		else {
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
		if ((mask & MODIFY) == MODIFY) {
			actionType = "MODIFY";
		}
		if ((mask & DELETE) == DELETE) {
			actionType = "DELETE";
		}
		if ((mask & ASSERT) == ASSERT) {
			actionType = "ASSERT";
		}
		if ((mask & SEND) == SEND) {
			actionType = "SEND";
		}
		if ((mask & REPLY) == REPLY) {
			actionType = "REPLY";
		}
		return actionType;
	}

	

	/**
	 * Two actions are equal if their type/mask is same. Use
	 * <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		EventAction other = (EventAction) obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
