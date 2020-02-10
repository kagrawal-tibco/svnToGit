package com.tibco.cep.studio.decision.table.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.wizards.export.ExportExcelWizardPage;

public class DTExcelExportWizardPage extends
		ExportExcelWizardPage<DTExcelExportWizard, DTLabelDecorator> {

	/**
	 * @param pageName
	 * @param window
	 * @param wizard
	 * @param treeselection
	 */
	public DTExcelExportWizardPage(String pageName,
			IWorkbenchWindow window, 
			IStructuredSelection treeselection) {
		super(pageName, window, treeselection);
		resourceFileExtension = "rulefunctionimpl";
		resourceType = "Decision Table";
	}
}
