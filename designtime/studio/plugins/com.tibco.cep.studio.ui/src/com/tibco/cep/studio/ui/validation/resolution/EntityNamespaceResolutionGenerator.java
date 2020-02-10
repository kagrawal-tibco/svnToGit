package com.tibco.cep.studio.ui.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import com.tibco.cep.studio.core.validation.IResourceValidator;

public class EntityNamespaceResolutionGenerator implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (IResourceValidator.VALIDATION_NS_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new EntityMarkerNamespaceResolution() };
			}
			if (IResourceValidator.VALIDATION_PROP_OWNER_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new PropertyOwnerResolution() };
			}
			if (IResourceValidator.VALIDATION_SM_OWNER_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new StateMachineOwnerResolution() };
			}
		} catch (CoreException e) {
			// TODO: handle exception
		}
		return new IMarkerResolution[0];
	}

}
