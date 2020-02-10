package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class RolePreferenceWizardAction extends CommonWizardAction implements IWorkbenchWindowActionDelegate {

	private SelectionAwareWizardLauncherAction delegateAction;

	@Override
	public void init(IWorkbenchWindow window) {
		setWorkbenchWindow(window);
		delegateAction = new SelectionAwareWizardLauncherAction(window, "New Role Preference", SWT.NONE, "com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.rolepreference");
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(getStudioProject(selection) != null);
		if (selection instanceof IStructuredSelection) {
			delegateAction.selectionChanged((IStructuredSelection) selection);
		}
	}

	@Override
	public void run(IAction action) {
		delegateAction.run();
	}

	@Override
	public void dispose() {
		delegateAction = null;
	}
}
