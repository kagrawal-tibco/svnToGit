/**
 * 
 */
package com.tibco.cep.decision.table.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.wizards.export.ExportExcelWizardPage;

/**
 * @author aathalye
 *
 */
public class DecisiontableExcelExportWizardPage extends
		ExportExcelWizardPage<DecisionTableExcelExportWizard, DecisionTableLabelDecorator> {

	/**
	 * @param pageName
	 * @param window
	 * @param wizard
	 * @param treeselection
	 */
	public DecisiontableExcelExportWizardPage(String pageName,
			IWorkbenchWindow window, 
			IStructuredSelection treeselection) {
		super(pageName, window, treeselection);
		resourceFileExtension = "rulefunctionimpl";
		resourceType = "Decision Table";
	}
}
