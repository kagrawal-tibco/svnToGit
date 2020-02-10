package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * @author rhollom
 *
 */
public class RenameElementRefactoringWizard extends RefactoringWizard {

	protected IWizardPage fRenameEntityPage;

	public RenameElementRefactoringWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
		setDefaultPageTitle("Rename element");
	}

	@Override
	protected void addUserInputPages() {
		fRenameEntityPage = new RenameElementRefactoringPage("Rename element");
		addPage(fRenameEntityPage);
	}

	@Override
	public String getWindowTitle() {
		return "Rename element";
	}

	@Override
	public boolean canFinish() {
		boolean modified = ((RenameElementRefactoringPage) fRenameEntityPage).getModified();
		return modified;
	}
}
