package com.tibco.cep.bpmn.ui.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.validation.BpmnProcessValidator;

public class ProcessOwnerResolutionGenerator implements
		IMarkerResolutionGenerator {

	
	public ProcessOwnerResolutionGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (BpmnProcessValidator.PROCESS_OWNER_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new ProcessOwnerMarkerResolution() };
			}
			if (BpmnProcessValidator.INDEX_FORMAT_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new IndexFormatMarkerResolution() };
			}
			if (BpmnProcessValidator.MISSING_EXTDEF_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new MissingExtDefMarkerResolution() };
			}
			if (BpmnProcessValidator.WRONG_FOLDER_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new ProcessFolderMarkerResolution() };
			}
			
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		}
		return new IMarkerResolution[0];
	}

}
