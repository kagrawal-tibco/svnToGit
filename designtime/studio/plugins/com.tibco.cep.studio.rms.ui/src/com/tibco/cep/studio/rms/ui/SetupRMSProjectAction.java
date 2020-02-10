package com.tibco.cep.studio.rms.ui;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.ui.actions.AbstractRMSAction;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.wizards.RMSProjectSetupWizard;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author smarathe
 *
 */
public class SetupRMSProjectAction extends AbstractRMSAction implements IWorkbenchWindowActionDelegate {

	private IStructuredSelection strSelection;
	private IProject project;
	private IEditorPart active;
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub

	}


	@Override
	public void init(IAction action) {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		StudioUIManager.getInstance().addAction(ActionConstants.SETUP_RMS_PROJECT_ACTION, action);
		action.setEnabled(false);
		
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		RMSProjectSetupWizard wizard = new RMSProjectSetupWizard();
		Shell shell = this.window.getShell();
		wizard.setNeedsProgressMonitor(true);
		wizard.setProject(project);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.setMinimumPageSize(320, 5);
		dialog.create();
		dialog.open();
	
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		getResourceFromEditor();
		if (_selection != null && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection) _selection;
			if ((strSelection.getFirstElement() instanceof IProject)
					|| (strSelection.getFirstElement() instanceof IFolder
					|| active!=null)) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}
		}
	}
	
	@Override
	public void getResourceFromEditor() {
		if (!PlatformUI.isWorkbenchRunning() || PlatformUI.getWorkbench().isClosing()) {
			return;
		}
		if(_selection instanceof IStructuredSelection) {
			project = StudioResourceUtils.getCurrentProject((IStructuredSelection)_selection);
		}
		/*window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			active = page.getActiveEditor();
			if (active != null) {
				project = StudioResourceUtils.getProjectForInput(active.getEditorInput());
				if (project != null) {
					m_SelectedResource = project;
				}
			}
		}*/
	}

}
