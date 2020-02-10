/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.validation;


/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.rule.Compilable}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CompilableValidator {
	boolean validate();

	boolean validateCompilationStatus(int value);
	boolean validateReturnType(String value);
	boolean validateFullSourceText(String value);
	boolean validateActionText(String value);
	boolean validateConditionText(String value);
	boolean validateRank(String value);
}
