/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.validation.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.validation.ModelError}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ModelErrorValidator {
	boolean validate();

	boolean validateMessage(String value);
	boolean validateWarning(boolean value);
	boolean validateSource(EObject value);
}
