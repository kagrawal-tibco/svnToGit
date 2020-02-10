/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.security.Principal;


/**
 * @author aathalye
 *
 */
public class RulesetAction extends AbstractAuthzAction {

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction#implies()
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6781969848069643818L;

	public boolean implies(final IAction other) {
		if (!(other instanceof RulesetAction)) {
			return false;
		}
		RulesetAction ra = (RulesetAction)other;
		String actionType = ra.getActionType();
		if (actionType != null) {
			//Get its mask
			int otherMask = ra.getMask(actionType);
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
	public RulesetAction(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 * @param permit
	 * @param principal
	 */
	public RulesetAction(String actionType, Permit permit, Principal principal) {
		super(actionType, permit, principal);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param actionType
	 */
	public RulesetAction(String actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}




	public static final int READ = 0x2;
	
	public static final int CREATE_TABLE = 0x4;
	
	public static final int DELETE_TABLE = 0x8;
	
	public static final int MODIFY = 0x16;
	
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
	    } else if ("create_table".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = CREATE_TABLE;
	    } else if ("delete_table".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = DELETE_TABLE;
	    } else if ("modify".equalsIgnoreCase(actionType.trim().intern())) {
	    	mask = MODIFY;
	    }
	    else {
	    	throw new IllegalArgumentException("Illegal action value");
	    }
		return mask;
	}
	
	protected String getActionType(int mask) {
		String actionType = null;
		if ((mask & READ) == READ) {
			return actionType = "read";
		}
		if ((mask & MODIFY) == MODIFY) {
			return actionType = "modify";
		}
		if ((mask & CREATE_TABLE) == CREATE_TABLE) {
			return actionType = "create_table";
		}
		if ((mask & DELETE_TABLE) == DELETE_TABLE) {
			return actionType = "delete_table";
		}
		if ((mask & NONE) == NONE) {
			return actionType = "none";
		}
		return actionType;
	}
	

	/**
	 * Two actions are equal if their type/mask
	 * is same. Use <code>implies</code> in ACL stuff
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RulesetAction)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RulesetAction other = (RulesetAction)obj;
		int otherMask = other.getMask(other.getActionType());
		if ((this.mask & otherMask) == otherMask) {
			return true;
		}
		return false;
	}
}
