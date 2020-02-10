/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;

/**
 * @author aathalye
 *
 */
public class ConceptAction extends AbstractAuthzAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1078466603903840520L;
	

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies()
	 */
	public boolean implies(final IAction other) {
		if (!(other instanceof ConceptAction)) {
			return false;
		}
		ConceptAction co = (ConceptAction)other;
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
	 * Example actiontype string could be
	 * CREATE_CONCEPT or READ_CONCEPT
	 * @param actions
	 */
	public ConceptAction(final String actionType) {
		super(actionType);
	}
	
	public ConceptAction(final int mask) {
		super(mask);
	}
	
	public ConceptAction(final String actionType,
			             final Permit permit,
			             final Principal principal) throws RuntimeException {
		super(actionType, permit, principal);
	}
	
	public static final int CREATE = 0x1;
	
	public static final int READ = 0x2;
	
	//Add/remove concept properties/attributes
	public static final int MODIFY = 0x4;
	
	public static final int DELETE = 0x8;
	
	public static final int NONE = 0x0;
	
	public static final int ALL = 0x128;
	
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
		if ((mask & MODIFY) == MODIFY) {
			actionType = "MODIFY";
		}
		if ((mask & DELETE) == DELETE) {
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
		if (!(obj instanceof ConceptAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConceptAction other = (ConceptAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
