package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * @author rhollom
 *
 */
public class MigrateCoherenceFunctionsRefactoringWizard extends RefactoringWizard {

	private IWizardPage fMigrateCoherenceFunctionsPage;

	public MigrateCoherenceFunctionsRefactoringWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
		setDefaultPageTitle("Migrate Coherence Function Calls");
	}

	@Override
	protected void addUserInputPages() {
		fMigrateCoherenceFunctionsPage = new MigrateCoherenceFunctionsRefactoringPage("Migrate Coherence Function Calls");
		addPage(fMigrateCoherenceFunctionsPage);
	}

	@Override
	public String getWindowTitle() {
		return "Migrate Coherence Function Calls";
	}

}
