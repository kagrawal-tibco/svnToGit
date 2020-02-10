package com.tibco.cep.bpmn.ui.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.validation.ValidationURIHandler;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class ProcessUniqueIdNotGeneratedResolution implements
		IMarkerResolution2 {

	@Override
	public String getDescription() {
		return BpmnMessages.getString("processIndexGenRes_description_label");
	}

	@Override
	public Image getImage() {
		return BpmnUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
	}

	@Override
	public String getLabel() {
		return BpmnMessages.getString("processIndexGenRes_description_label");
	}
	@Override
	public void run(IMarker marker) {
		final IResource resource = marker.getResource();
		if (!StudioUIUtils.saveDirtyEditor(resource)) {
			return;
		}
		
		final EObject element = BpmnIndexUtils.getElement(resource);
		try {
			boolean flag=BpmnModelUtils.checkForUniqueId(EObjectWrapper.wrap(element));
			if(flag){
				ECoreHelper.serializeModelXMI(resource,element);
			}
		}catch(Exception e){
			
		}
		
	}

	
	

}
