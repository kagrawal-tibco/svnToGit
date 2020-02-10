package com.tibco.cep.bpmn.ui.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.validation.BpmnPaletteValidator;

/**
 * 
 * @author majha
 *
 */
public class PaletteMissingResourceResolutionGenerator implements
		IMarkerResolutionGenerator {

	
	public PaletteMissingResourceResolutionGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (BpmnPaletteValidator.MISSING_RESOURCE_MARKER_TYPE.equals(marker.getType())) {
				return new IMarkerResolution[] { new MissingResourceInBpmnPaletteMarkerResolution() };
			}
			
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		}
		return new IMarkerResolution[0];
	}

}
