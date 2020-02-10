/**
 * 
 */
package com.tibco.cep.studio.rms.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;

/**
 * @author hitesh
 *
 */
public class ReleaseLockAction implements IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
//	private IWorkbenchPart currentPart;
//	private Shell shell;
	
	/**
	 * 
	 */
	public ReleaseLockAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		this.currentPart = targetPart;
//		shell = currentPart.getSite().getShell();
		
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
//		Object selectedObject = ((IStructuredSelection) _selection).getFirstElement();
//		if(selectedObject instanceof IFile){
//			IProject project = ((IFile)(selectedObject)).getProject();
//			String projectName = project.getName();
//			String user = AuthTokenUtils.getLoggedinUser();
//		}
		RMSUIUtils.setunlocked(true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this._selection = selection;
		if (_selection != null && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection) _selection;
			if ((strSelection.getFirstElement() instanceof IFile)
					&& (RMSUIUtils.islocked())) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}
		}
	}
	
	

}
