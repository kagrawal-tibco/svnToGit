package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.core.refactoring.EntityMoveProcessor;

/**
 * see {@link EntityElementRefactoring} for more info
 * @author rhollom
 *
 */
public class MoveElementRefactoringWizard extends RefactoringWizard {

	private IWizardPage fMoveEntityPage;
	private String fProjectName;

	public MoveElementRefactoringWizard(Refactoring refactoring, int flags, String projectName) {
		super(refactoring, flags);
		this.fProjectName = projectName;
		setDefaultPageTitle("Move element");
	}

	@Override
	protected void addUserInputPages() {
		EntityMoveProcessor processor = (EntityMoveProcessor) ((ProcessorBasedRefactoring)getRefactoring()).getProcessor();
		if (processor.requiresSelection()) {
			fMoveEntityPage = new MoveElementRefactoringPage("Move element", fProjectName);
			addPage(fMoveEntityPage);
		} else {
			// the target location has already been set, no need to add the page
		}
	}

	@Override
	public String getWindowTitle() {
		return "Move element";
	}

}
