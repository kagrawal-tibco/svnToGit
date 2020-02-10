package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.refactoring.EntityMoveProcessor;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.ui.refactoring.MoveElementRefactoringWizard;

public class MoveResourceAction extends AbstractElementRefactorAction {

	private IContainer fTarget;

	public MoveResourceAction() {
		super(IRefactoringContext.MOVE_REFACTORING);
	}

	public MoveResourceAction(IContainer target) {
		super(IRefactoringContext.MOVE_REFACTORING);
		this.fTarget = target;
	}
	
	protected void performRefactoring(Object elementToRefactor) {
		IRefactoringContext context = createRefactoringContext(elementToRefactor);
		EntityMoveProcessor processor = new EntityMoveProcessor(context);
		if (fTarget != null) {
			IPath path = fTarget.getFullPath();
			path = path.removeFirstSegments(1); // remove project
			processor.setNewPath(path.toString());
			processor.setRequiresSelection(false);
		}
		ProcessorBasedRefactoring refactoring = new ProcessorBasedRefactoring(processor);
		if (elementToRefactor instanceof IResource)	 {
			IResource resource = (IResource) elementToRefactor;
			if (fTarget != null && fTarget.getFile(new Path(resource.getName())).exists()) {
    				// prompt for overwrite
				if (!MessageDialog.openQuestion(new Shell(), "Resource exists", "Resource '"+resource.getName()+"' already exists in this folder.  Do you want to overwrite?")) {
					return;
				}
			}
			MoveElementRefactoringWizard refactoringWizard = new MoveElementRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE, ((IResource) elementToRefactor).getProject().getName());
			refactoringWizard.setForcePreviewReview(true);
			RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
			try {
				op.run(Display.getDefault().getActiveShell(), "Move Resource");
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void run(IAction action) {
		if (!(fSelection instanceof StructuredSelection)) {
			return;
		}
		
		StructuredSelection selection = (StructuredSelection) fSelection;
		if (selection.isEmpty()) {
			return;
		}
		if (selection.getFirstElement() instanceof IProject) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Unable to move", "Moving projects is not a supported operation");
			return;
		}
		super.run(action);
	}

}
