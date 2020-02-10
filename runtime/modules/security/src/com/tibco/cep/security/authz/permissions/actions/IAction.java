/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import java.io.Serializable;

import com.tibco.cep.security.authz.core.SerializableObject;

/**
 * @author aathalye
 *
 */
public interface IAction extends Serializable, SerializableObject {
	
	/**
	 * @param action
	 * @return
	 */
	public boolean implies(IAction action);
	
	public String getActionType();
	
	public String getPermitValue();
}