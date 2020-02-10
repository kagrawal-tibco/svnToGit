package com.tibco.cep.bpmn.ui;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.properties.filter.BpmnProcessTreeViewerFilter;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;

public class BpmnProcessSelectionDialog extends
		StudioFilteredResourceSelectionDialog {

	public BpmnProcessSelectionDialog(Shell parent, String currentProjectName,
			String existingPath, EObjectWrapper<EClass, EObject> process) {
		super(parent, currentProjectName, existingPath,  new ELEMENT_TYPES[0] ,
				false, false, false);
		extensions.add(BpmnIndexUtils.BPMN_PROCESS_EXTENSION);
		addFilter(new BpmnProcessTreeViewerFilter(process));
	}

}
