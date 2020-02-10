package com.tibco.cep.studio.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;
import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ValidateResourceAction implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	protected ISelection _selection;
	private IStructuredSelection structuredSelection;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (!(_selection instanceof StructuredSelection)) {
			return;
		}
		StructuredSelection selection = (StructuredSelection) _selection;
		if (selection.isEmpty()) {
			return;
		}
		List<IProject> projectsToValidate = new ArrayList<IProject>();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof IResource) {
				IProject project = ((IResource)next).getProject();
				if (!projectsToValidate.contains(project)) {
					projectsToValidate.add(project);
				}
			}
		}
		ValidatorInfo[] projectResourceValidators = ValidationUtils.getProjectResourceValidators();
		for (IProject project : projectsToValidate) {
			for (ValidatorInfo info : projectResourceValidators) {
				if (info.enablesFor(project)) {
					ValidationContext vldContext = new ValidationContext(project , IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD);
					info.validate(vldContext);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this._selection = selection;
		IProject project = null;
		if (!(_selection.isEmpty()) && _selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection)_selection;
			if(structuredSelection.size() == 1){
				action.setEnabled(true);
				if(structuredSelection.getFirstElement() instanceof AbstractNavigatorNode){
					action.setEnabled(false);
					return;
				}
				if (!(structuredSelection.getFirstElement() instanceof IProject)) {
					project = StudioResourceUtils.getCurrentProject(structuredSelection);
				} else {
					project = (IProject)structuredSelection.getFirstElement();
				}
				
				if(project != null && project.isOpen()){
					action.setEnabled(true);
				}else{
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if(_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

	public void set_selection(ISelection _selection) {
		this._selection = _selection;
	}

}
