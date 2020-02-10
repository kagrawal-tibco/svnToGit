package com.tibco.cep.studio.dashboard.core.model;

import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;

/**
 * @ *
 */
public interface ISynValidationProvider {

	/**
	 * Return whether the current state of the element is valid
	 *
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isValid() throws Exception;

	/**
	 * Returns whether the state of the given Object is valid; within the context of this element
	 *
	 * @param value
	 * @return
	 */
	public abstract boolean isValid(Object value) throws Exception;

	public abstract SynValidationMessage getValidationMessage() throws Exception;

	public abstract void setValidationMessage(SynValidationMessage validationMessage);

}
