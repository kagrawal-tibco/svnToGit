/**
 * 
 */
package com.jidesoft.decision.validation;

import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.FieldSettingsPanel;

/**
 * Implementations of this interface will be notified
 * when a field in the {@link FieldSettingsPanel}
 * associated with a {@link DecisionField} is changed.
 * <p>
 * e.g : If name is changed, an implementation can perform
 * validation on it before committing the change.
 * </p>
 * @author aathalye
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
