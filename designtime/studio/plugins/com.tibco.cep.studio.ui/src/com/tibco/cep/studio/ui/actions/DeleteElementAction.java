package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.DeleteResourcesWizard;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.refactoring.IRefactoringContext;

/**
 * Handles Resource delete refactor other than Project Navigate explorer 
 * @author sasahoo
 *
 */
public class DeleteElementAction extends AbstractElementRefactorAction {
	
	public DeleteElementAction() {
		super(IRefactoringContext.DELETE_REFACTORING);
	}
	public static int result = 0;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.actions.AbstractElementRefactorAction#performRefactoring(java.lang.Object)
	 */
	protected void performRefactoring(Object elementToRefactor) {
		IRefactoringContext context = createRefactoringContext(elementToRefactor);
		Object element = context.getElement();

		//This is one way, similar to ElementRename action 
		//EntityDeleteProcessor processor = new EntityDeleteProcessor(context);
		//EntityElementRefactoring refactoring = new EntityElementRefactoring(processor);

		//This is another way, efficient one
		if (element instanceof IFile) {
			IFile file = (IFile)element;
			
			/**
			 * invoke DeleteResource Wizard for the resource delete
			 * calls all relevant participants 
			 *  see {@link DeleteResourcesWizard} for more info
			 */
			DeleteResourcesWizard refactoringWizard = new DeleteResourcesWizard(new IResource[] {file});
			RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
			try {
//				Shell[] shells = Display.getDefault().getShells();
				result = op.run(Display.getDefault().getShells()[0], "Delete Resource");
			} catch (InterruptedException e) {
			}
		}

	}
}
