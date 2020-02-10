/**
 * 
 */
package com.tibco.cep.studio.core.validation;

/**
 * @author rmishra
 *
 */
public interface IValidationError {
	/**
	 * returns the source for error
	 * @return
	 */
	Object getSource();
	/**
	 * returns the error message
	 * @return
	 */
	String getMessage();
	/**
	 * set the source for error
	 * @param source
	 */
	void setSource(Object source);
	/**
	 * set error message
	 * @param msg
	 */
	void setMessage(String msg);
	
	/**
	 * whether error is a warning
	 * @return
	 */
	boolean isWarning();
	
	/**
	 * 
	 * @param isWarning
	 */
	
	void setWarning(boolean isWarning);

}
