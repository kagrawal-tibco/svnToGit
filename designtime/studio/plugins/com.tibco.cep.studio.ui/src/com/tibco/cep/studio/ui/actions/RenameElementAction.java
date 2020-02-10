package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.core.refactoring.EntityRenameProcessor;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringWizard;

public class RenameElementAction extends AbstractElementRefactorAction {
	
	public RenameElementAction() {
		super(IRefactoringContext.RENAME_REFACTORING);
	}

	protected void performRefactoring(Object elementToRefactor) {
		IRefactoringContext context = createRefactoringContext(elementToRefactor);
		Object element = context.getElement();
		EntityRenameProcessor processor = new EntityRenameProcessor(context);
		EntityElementRefactoring refactoring = new EntityElementRefactoring(processor);
		RenameElementRefactoringWizard refactoringWizard = invokeRefactoringWizard(refactoring);
		
		if (element instanceof IFile) {
			IFile file = (IFile)element;
			if (file.getFileExtension().equals("rulefunctionimpl")) {
				refactoringWizard.setForcePreviewReview(false);
			}else {
				refactoringWizard.setForcePreviewReview(true);
			}
		}else if(element instanceof PropertyDefinition || 
				 element instanceof Destination){
			refactoringWizard.setForcePreviewReview(true);
		}
		
		RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
		try {
			op.run(Display.getDefault().getActiveShell(), "Rename Resource");
		} catch (InterruptedException e) {
		}
	}
	
	
	protected RenameElementRefactoringWizard invokeRefactoringWizard(EntityElementRefactoring refactoring) {
		return new RenameElementRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);
	}
}
