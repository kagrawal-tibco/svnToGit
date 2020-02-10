package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.tibco.cep.studio.core.refactoring.MapperFunctionRefactoring;

/**
 * @author rhollom
 *
 */
public class MigrateMapperFunctionsRefactoringWizard extends RefactoringWizard {

	private MigrateMapperFunctionsRefactoringPage fMigrateMapperFunctionsPage;

	public MigrateMapperFunctionsRefactoringWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
		setDefaultPageTitle("Migrate Mapper Function Calls to XPath 2.0");
	}

	@Override
	protected void addUserInputPages() {
		fMigrateMapperFunctionsPage = new MigrateMapperFunctionsRefactoringPage("Migrate Mapper Function Calls to XPath 2.0");
		addPage(fMigrateMapperFunctionsPage);
	}

	@Override
	public String getWindowTitle() {
		return "Migrate Mapper Function Calls to XPath 2.0";
	}

	public void setUpdateVersions(boolean update) {
		MapperFunctionRefactoring refactoring = (MapperFunctionRefactoring) getRefactoring();
		refactoring.setUpdateVersion(update);
	}
	
}
