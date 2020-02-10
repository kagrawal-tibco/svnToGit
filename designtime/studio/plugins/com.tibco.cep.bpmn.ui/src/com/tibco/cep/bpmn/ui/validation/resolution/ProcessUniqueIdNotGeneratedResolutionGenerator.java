package com.tibco.cep.bpmn.ui.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import com.tibco.cep.bpmn.ui.validation.BpmnProcessValidator;

public class ProcessUniqueIdNotGeneratedResolutionGenerator implements
		IMarkerResolutionGenerator {

	public ProcessUniqueIdNotGeneratedResolutionGenerator(){
		super();
	}
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (BpmnProcessValidator.PROCESS_UNIQUEID_NOTGENERATED.equals(marker.getType())) {
				return new IMarkerResolution[] { new ProcessUniqueIdNotGeneratedResolution() };
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return new IMarkerResolution[0];
}
}
