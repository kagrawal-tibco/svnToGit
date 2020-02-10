package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;

public abstract class AbstractElementRefactorAction implements IWorkbenchWindowActionDelegate {

	protected ISelection fSelection;
	protected String fProjectName;
	protected int fType;

	public AbstractElementRefactorAction(int type) {
		this.fType = type;
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
	}

	public void run(IAction action) {
		if (!checkDirtyEditors()) {
			return;
		}
		if (!(fSelection instanceof StructuredSelection)) {
			return;
		}
		
		StructuredSelection selection = (StructuredSelection) fSelection;
		if (selection.isEmpty()) {
			return;
		}
		Object elementToRefactor = null;
		Object selectedElement = selection.getFirstElement();
//		if (selectedElement instanceof IProject) {
//			MessageDialog.openError(new Shell(), "Error", com.tibco.cep.studio.core.util.Messages.getString("BE_Project_rename_error"));
//			return;
//		}
		if (selectedElement instanceof IResource) {
			elementToRefactor = selectedElement;
		} /*else if (false) {
			IResource resource = (IResource) selectedElement;
			this.fProjectName = resource.getProject().getName();
			DesignerElement element = IndexUtils.getElement(resource);
			if (element != null) {
				elementToRefactor = element;
			} else {
				// attempt to load the file, and see if it gets an EObject
				elementToRefactor = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
			}
		} */else if (selectedElement instanceof IAdaptable) {
			Object adapter = ((IAdaptable) selectedElement).getAdapter(Entity.class);
			if (adapter instanceof Entity) {
				Entity entity = (Entity) adapter;
				this.fProjectName = entity.getOwnerProjectName();
				elementToRefactor = adapter;
			}
		} else if (selectedElement instanceof Entity) {
			Entity entity = (Entity) selectedElement;
			this.fProjectName = entity.getOwnerProjectName();
			elementToRefactor = selectedElement;
		}
		if (elementToRefactor == null) {
			elementToRefactor = selectedElement;
		}
		performRefactoring(elementToRefactor);
	}

	private boolean checkDirtyEditors() {
		try {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (activePage != null && activePage.getDirtyEditors().length > 0) {
				if (MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Save all resources", "You must save all modified resources before continuing.  Would you like to save now?")) {
					return activePage.saveAllEditors(false);
				}
				return false;
			}
		} catch (NullPointerException e) {
		}
		return true;
	}

	abstract protected void performRefactoring(Object elementToRefactor);

	protected IRefactoringContext createRefactoringContext(
			Object elementToRefactor) {
		StudioRefactoringContext context = new StudioRefactoringContext(elementToRefactor, fType, fProjectName);
		return context;
	}

}
