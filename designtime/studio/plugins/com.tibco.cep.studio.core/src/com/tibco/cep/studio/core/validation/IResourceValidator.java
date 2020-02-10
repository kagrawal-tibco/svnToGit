package com.tibco.cep.studio.core.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.StudioCorePlugin;

public interface IResourceValidator {
	
	public static final String VALIDATION_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".validationProblem";
	public static final String VALIDATION_NS_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".namespaceProblem";
	public static final String VALIDATION_PROP_OWNER_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".propertyOwnerProblem";
	public static final String VALIDATION_SM_OWNER_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".ownerStateMachineProblem";
	public static final String BUILD_PATH_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".buildPathProblem";

	public static final String EVENT_DEFAULT_DESTINATION_VALIDATION_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID + ".defaultDestinationValidationProblem";
	
	public static final String MARKER_STATE_GRAPH_PATH_ATTRIBUTE = "com.tibco.cep.statemodel.stategraph.path";
	public static final String MARKER_IS_TRANSITION_STATE_ATTRIBUTE = "com.tibco.cep.statemodel.isTransitionState";
	
	public static final String DESTINATION_NAME_ATTRIBUTE = "com.tibco.cep.studio.channel.destination.path";
	
	public static final int SEVERITY_ERROR 	= IMarker.SEVERITY_ERROR;
	public static final int SEVERITY_WARNING = IMarker.SEVERITY_WARNING;
	public static final int SEVERITY_INFO 	= IMarker.SEVERITY_INFO;

	boolean validate(ValidationContext validationContext);
	
	boolean canContinue();

	boolean enablesFor(IResource resource);
	
}
