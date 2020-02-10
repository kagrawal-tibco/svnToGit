package com.tibco.cep.studio.rms.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsLockDialog;

/**
 * @author hitesh
 * 
 */

public class LockAction implements IObjectActionDelegate {

	private ISelection _selection;
	
	private IStructuredSelection strSelection;
	
	private IWorkbenchPart currentPart;
	
	private Shell shell;
	
	public LockAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.currentPart = targetPart;
		shell = currentPart.getSite().getShell();
		
	}

	@Override
	public void run(IAction action) {
		if (!(_selection instanceof IStructuredSelection)) {
			return;
		}
		int size = ((IStructuredSelection) _selection).size();
		if (size != 1) {
			return;
		}
		Object selectedObject = ((IStructuredSelection) _selection).getFirstElement();
		if (selectedObject instanceof IFile) {
			IFile selectedFile = (IFile)selectedObject;
			IProject project = selectedFile.getProject();
			String projectName = project.getName();
			String user = AuthTokenUtils.getLoggedinUser();
			//Get Artifact Path
			String artifactPath = IndexUtils.getFullPath(selectedFile);
			try {
				RMSArtifactsLockDialog dialog = new RMSArtifactsLockDialog(shell, user, projectName, artifactPath);
				dialog.open();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this._selection = selection;
		if (_selection != null && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection) _selection;
			if ((strSelection.getFirstElement() instanceof IFile)
					&& (RMSUIUtils.islogged())) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}
		}
	}	
}
