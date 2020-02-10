package com.tibco.cep.studio.core.validation;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.SharedElement;

/**
 * Extension interface to IResourceValidator to support validation of SharedElements
 * in project libraries
 * 
 * @author rhollom
 *
 */
public interface IResourceValidatorExtension {
	
	public static final String SHARED_ELEMENT_VALIDATION_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".projectLibraryProblem";
	public static final String SHARED_ELEMENT_PREFIX = "Project Library: ";

	boolean validate(SharedElementValidationContext validationContext);
	
	boolean enablesFor(SharedElement element);
	
}
