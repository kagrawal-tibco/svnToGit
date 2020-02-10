/**
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.validation;

import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.NamePrefix;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface BpmnProcessSettingsValidator {
	boolean validate();

	boolean validateBuildFolder(String value);
	boolean validatePalettePathEntries(EList<BpmnPalettePathEntry> value);
	boolean validateSelectedProcessPaths(EList<BpmnProcessPathEntry> value);
	boolean validateProcessPrefix(String value);
	boolean validateRulePrefix(String value);
	boolean validateRuleFunctionPrefix(String value);
	boolean validateConceptPrefix(String value);
	boolean validateEventPrefix(String value);
	boolean validateTimeEventPrefix(String value);
	boolean validateScorecardPrefix(String value);
	boolean validateNamePrefixes(EList<NamePrefix> value);
}
