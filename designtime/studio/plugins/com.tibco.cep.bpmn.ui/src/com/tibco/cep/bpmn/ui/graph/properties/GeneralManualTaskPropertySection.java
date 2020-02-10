package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;

public class GeneralManualTaskPropertySection extends GeneralNodePropertySection {

	@Override
	protected PropertyNodeTypeGroup getNodeTypeData() {
		return null;
	}


	
	@Override
	protected boolean isResourcePropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
