package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.refactoring.CoherenceFunctionRefactoring;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.ui.refactoring.MigrateCoherenceFunctionsRefactoringWizard;

public class MigrateCoherenceFunctionsAction extends AbstractElementRefactorAction {
	
	public MigrateCoherenceFunctionsAction() {
		super(IRefactoringContext.RENAME_REFACTORING);
	}

	protected void performRefactoring(Object elementToRefactor) {
		
		if (elementToRefactor instanceof IResource) {
			String projectName = ((IResource) elementToRefactor).getProject().getName();
			CoherenceFunctionRefactoring refactoring = new CoherenceFunctionRefactoring(projectName);
			MigrateCoherenceFunctionsRefactoringWizard refactoringWizard = new MigrateCoherenceFunctionsRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);
			
			RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
			try {
				op.run(Display.getDefault().getActiveShell(), "Migrate Coherence Function Calls");
			} catch (InterruptedException e) {
			}
		}
	}
	
}
