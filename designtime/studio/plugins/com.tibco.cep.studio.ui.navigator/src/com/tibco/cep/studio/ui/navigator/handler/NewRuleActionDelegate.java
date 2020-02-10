package com.tibco.cep.studio.ui.navigator.handler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class NewRuleActionDelegate implements IObjectActionDelegate {

	private ISelection currentSelection;
	@SuppressWarnings("unused")
	private IWorkbenchPart currentPart;

	/**
	 * 
	 */
	public NewRuleActionDelegate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.currentPart = targetPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (!(currentSelection instanceof IStructuredSelection)) {
			return;
		}
		int size = ((IStructuredSelection) currentSelection).size();
		if (size != 1) {
			return;
		}
		Object selectedObject = ((IStructuredSelection) currentSelection)
				.getFirstElement();
		if (!(selectedObject instanceof IFolder)) {
			return;
		}
		
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection;
		Object selectedObject = ((IStructuredSelection) currentSelection)
				.getFirstElement();
		int size = ((IStructuredSelection) selection).size();
		if (size == 1 && selectedObject instanceof IFolder) {
			Object[] object = CommonUtil.getResources((IFolder)selectedObject);
			int count = 0;
			for(Object obj:object){
				if(obj instanceof IFolder){
					break;
				}
				if(obj instanceof IFile){
					if(((IFile)obj).getFileExtension().equals("rule")){
						count++;
					}
				}
			}
			if(object.length == count)
				action.setEnabled(true);
			else
				action.setEnabled(false);
		}
	}
	
}
