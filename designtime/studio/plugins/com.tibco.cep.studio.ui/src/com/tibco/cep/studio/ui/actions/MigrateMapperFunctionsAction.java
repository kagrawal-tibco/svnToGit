package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.core.refactoring.MapperFunctionRefactoring;
import com.tibco.cep.studio.ui.refactoring.MigrateMapperFunctionsRefactoringWizard;

public class MigrateMapperFunctionsAction extends AbstractElementRefactorAction {
	
	public MigrateMapperFunctionsAction() {
		super(IRefactoringContext.RENAME_REFACTORING);
	}

	protected void performRefactoring(Object elementToRefactor) {
		
		if (elementToRefactor instanceof IResource) {
			String projectName = ((IResource) elementToRefactor).getProject().getName();
			MapperFunctionRefactoring refactoring = new MapperFunctionRefactoring(projectName);
			MigrateMapperFunctionsRefactoringWizard refactoringWizard = new MigrateMapperFunctionsRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);
			
			RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
			try {
				op.run(Display.getDefault().getActiveShell(), "Migrate Mapper Function Calls");
			} catch (InterruptedException e) {
			}
		}
	}
	
}
