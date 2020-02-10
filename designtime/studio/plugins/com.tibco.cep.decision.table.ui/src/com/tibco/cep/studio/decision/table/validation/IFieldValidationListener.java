/**
 * 
 */
package com.tibco.cep.studio.decision.table.validation;



/**
 * Implementations of this interface will be notified
 * when a field in the {@link FieldSettingsDialog}
 * is changed.
 * <p>
 * e.g : If name is changed, an implementation can perform
 * validation on it before committing the change.
 * </p>
 *
 */
public interface IFieldValidationListener<T> {
	
	/**
	 * 
	 * @param fieldValidationContext
	 * @return
	 */
	FieldValidationResult validate(FieldValidationContext<T> fieldValidationContext);
}
