package com.tibco.cep.bpmn.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.bpmn.ui.wizards.BpmnOpenProjectWizard;


public class BpmnOpenAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		Wizard openProjectWizard = new BpmnOpenProjectWizard(window);
		openProjectWizard.setNeedsProgressMonitor(true);
		WizardDialog wd = new WizardDialog(window.getShell(), openProjectWizard) {
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				Button finishButton = getButton(IDialogConstants.FINISH_ID);
				finishButton.setText(IDialogConstants.OK_LABEL);
			}
		};
		wd.setMinimumPageSize(320, 10);
		wd.create();
		wd.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
