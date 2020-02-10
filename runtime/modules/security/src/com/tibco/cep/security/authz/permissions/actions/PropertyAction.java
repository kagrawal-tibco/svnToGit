/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */

public class PropertyAction extends AbstractAuthzAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8627328290654715778L;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies()
	 */
	
	public boolean implies(final IAction other) {
		if (!(other instanceof PropertyAction)) {
			return false;
		}
		PropertyAction co = (PropertyAction)other;
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
	public PropertyAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public PropertyAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public PropertyAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}


	public static final int READ = 0x2;
	
	public static final int MODIFY = 0x4;
	
	public static final int CREATE = 0x8;
	
	public static final int DELETE = 0x16;
	
	public static final int NONE = 0x0;
	
	public static final int WRITE_DOMAIN_MODEL = 0x32;
	
	public static final int DOMAIN_OVERRIDE = 0x64;
	
		
	public int getMask(String actionType) {
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
	    } else if ("modify".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = MODIFY;
	    } else if ("create".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CREATE;
	    } else if ("delete".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DELETE;
	    } else if ("override".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DOMAIN_OVERRIDE;
	    } else if ("write_domain_model".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = WRITE_DOMAIN_MODEL;
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
		if ((mask & MODIFY) == MODIFY) {
			actionType = "MODIFY";
		}
		if ((mask & CREATE) == CREATE) {
			actionType = "CREATE";
		}
		if ((mask & WRITE_DOMAIN_MODEL) == WRITE_DOMAIN_MODEL) {
			actionType = "WRITE_DOMAIN_MODEL";
		}
		return actionType;
	}
	
	/**
	 * Two actions are equal if their type/mask
	 * is same. Use <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PropertyAction other = (PropertyAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
