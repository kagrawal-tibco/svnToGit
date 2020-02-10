package com.tibco.cep.bpmn.ui.refactoring;

import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.ui.actions.RenameElementAction;
import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringWizard;

public class BpmnProcessRenameElementAction extends RenameElementAction{
	

	public BpmnProcessRenameElementAction() {
		super();
	}

	protected void performRefactoring(Object elementToRefactor) {
		IRefactoringContext context = createRefactoringContext(elementToRefactor);
		Object element = context.getElement();
		BpmnProcessRenameProcessor processor = new BpmnProcessRenameProcessor(context);
		EntityElementRefactoring refactoring = new EntityElementRefactoring(processor);
		RenameElementRefactoringWizard refactoringWizard = invokeRefactoringWizard(refactoring);
		refactoringWizard.setForcePreviewReview(true);
		RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
		try {
			op.run(Display.getDefault().getActiveShell(), "Rename Resource");
		} catch (InterruptedException e) {
		}
	}
	
	protected RenameElementRefactoringWizard invokeRefactoringWizard(EntityElementRefactoring refactoring) {
		return new ProcessVarRenameElementRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);
	}
	
	
	

}

