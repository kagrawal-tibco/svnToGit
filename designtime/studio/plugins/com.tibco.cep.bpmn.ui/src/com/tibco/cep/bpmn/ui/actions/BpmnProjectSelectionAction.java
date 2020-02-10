package com.tibco.cep.bpmn.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.studio.ui.actions.ProjectSelection;

/*
@author ssailapp
@date Dec 8, 2011
 */

public class BpmnProjectSelectionAction extends ProjectSelection {

	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		try {
			boolean validBpmnProject = validProject && BpmnProjectNatureManager.getInstance().isBpmnProject(fProject);
			action.setEnabled(validBpmnProject);
		} catch (Exception e) {
			BpmnUIPlugin.log(Messages.getString("bpmn.build.codegen.failed"),e); //$NON-NLS-1$
		}
	}
}
