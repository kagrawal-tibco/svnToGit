/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.validation;

import com.tibco.cep.designtime.core.model.domain.Domain;

/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.rule.Symbol}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SymbolValidator {
	boolean validate();

	boolean validateIdName(String value);
	boolean validateType(String value);
	boolean validateTypeExtension(String value);
	boolean validateDomain(Domain value);
	boolean validateArray(boolean value);
}
