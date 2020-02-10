package com.tibco.cep.bpmn.ui.refactoring;

import org.eclipse.ltk.core.refactoring.Refactoring;

import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringWizard;

public class ProcessVarRenameElementRefactoringWizard extends RenameElementRefactoringWizard{

	public ProcessVarRenameElementRefactoringWizard(
			Refactoring refactoring, int flags) {
		super(refactoring, flags);
	}
	@Override
	protected void addUserInputPages() {
		fRenameEntityPage = new ProcessVarRenameElementRefactoringPage("Rename element", this.getRefactoring());
		addPage(fRenameEntityPage);
	}
}