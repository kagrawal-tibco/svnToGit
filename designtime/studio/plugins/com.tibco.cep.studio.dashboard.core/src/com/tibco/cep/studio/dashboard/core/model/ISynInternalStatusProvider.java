package com.tibco.cep.studio.dashboard.core.model;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;

/**
 * The status provider is mainly used within Architect to aid in the in-memory manipulation of virtual objects and in the translation from virtual to real objects or in synchronization between local and remote repository
 *
 * Implementation of this interface is not meant to be persisted by imlementors because of the transient nature of the internal status
 *
 * Implementors should take care to resolve any association/confict with the actual status of the associated data object if the object has its own status.
 */
public interface ISynInternalStatusProvider {

	/**
	 * Returns the current internal status
	 *
	 * @return String the status
	 */
	public InternalStatusEnum getInternalStatus();

	/**
	 * Sets the current internal status to the status given
	 *
	 * @param status
	 * @throws Exception
	 */
	public void setInternalStatus(InternalStatusEnum status) throws Exception;

	/**
	 * Sets the current internal status to the status given and also propagate it down to all the children
	 *
	 * @param status
	 * @throws Exception
	 */
	public void setInternalStatus(InternalStatusEnum status, boolean synchAllChildren) throws Exception;

	public boolean isSystemStatus();

}
