/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

/**
 * @author aathalye
 *
 */
public enum Permit {
	ALLOW,
	INDETERMINATE,
	DENY;
	
	public Permit and(final Permit permit) {
		if (permit == null) {
			//cant decide whether to allow or deny
			return Permit.INDETERMINATE;
		}
		if (this.equals(permit)) {
			return permit;
		} else {
			return Permit.INDETERMINATE;
		}
	}
	
	/*public Permit or(final Permit permit) {
		if (permit == null) {
			//cant decide whether to allow or deny
			return Permit.INDETERMINATE;
		}
		if (this.equals(permit)) {
			return permit;
		} else {
			
		}
	}*/
}
