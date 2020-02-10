/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;


/**
 * @author aathalye
 *
 */
public class AbstractImplementationAction extends AbstractAuthzAction implements IAction {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = -7578190921824758744L;

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies()
	 */
	
	public boolean implies(final IAction other) {
		if (!(other instanceof AbstractImplementationAction)) {
			return false;
		}
		AbstractImplementationAction da = (AbstractImplementationAction)other;
		String actionType = da.getActionType();
		if (actionType != null) {
			//Get its mask
			int otherMask = da.getMask(actionType);
			if ((this.mask & otherMask) == otherMask) {
				if (Permit.ALLOW.equals(this.permit)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * Example actiontype string could be
	 * MODIFY or READ
	 * @param actions
	 */
	public AbstractImplementationAction(final String actionType) {
		super(actionType);
	}
	
	public AbstractImplementationAction(final int mask) {
		super(mask);
	}
	
	public AbstractImplementationAction(final String actionType,
			                            final Permit permit,
			                            final Principal principal) {
		super(actionType, permit, principal);
	}
	
		
	public static final int READ = 0x2;
	
	public static final int MODIFY = 0x4;
	
	//Convert to rule function, and execute
	public static final int EXECUTE = 0x8;
	
	public static final int COMMIT = 0x10;
	
	public static final int NONE = 0x0;	
	
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
	    } else if ("execute".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = EXECUTE;
	    } else if ("commit".
	    		equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = COMMIT;
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
		if ((mask & EXECUTE) == EXECUTE) {
			actionType = "DELETE";
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
		if (!(obj instanceof AbstractImplementationAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AbstractImplementationAction other = (AbstractImplementationAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}

}
