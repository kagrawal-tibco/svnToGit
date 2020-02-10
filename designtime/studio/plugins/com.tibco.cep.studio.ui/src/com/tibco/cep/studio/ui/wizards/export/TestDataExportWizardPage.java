/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.decorators.TestDataLabelDecorator;

/**
 * @author mgujrath
 *
 */
public class TestDataExportWizardPage extends ExportExcelWizardPage<TestDataExcelExportWizard,TestDataLabelDecorator> {
	
	/**
	 * @param pageName
	 * @param window
	 * @param wizard
	 * @param treeselection
	 */
	public TestDataExportWizardPage(String pageName,
			IWorkbenchWindow window, 
			IStructuredSelection treeselection) {
		super(pageName, window, treeselection);
		resourceFileExtensionsforTestData.add("concepttestdata");
		resourceFileExtensionsforTestData.add("eventtestdata");
		resourceFileExtensionsforTestData.add("scorecardtestdata");
		resourceType = "Test Data";
	}

}
