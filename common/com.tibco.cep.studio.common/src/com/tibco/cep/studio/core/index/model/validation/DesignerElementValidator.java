/**
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.validation;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;

import java.util.Date;

/**
 * A sample validator interface for {@link com.tibco.cep.studio.core.index.model.DesignerElement}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DesignerElementValidator {
	boolean validate();

	boolean validateName(String value);
	boolean validateElementType(ELEMENT_TYPES value);
	boolean validateLazilyCreated(boolean value);
	boolean validateLastModified(Date value);
	boolean validateCreationDate(Date value);
}
