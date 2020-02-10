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
public class TestDataExcelExportWizardPage extends ExportExcelWizardPage<TestDataExcelExportWizard, TestDataLabelDecorator>{

	
	/**
	 * @param pageName
	 * @param window
	 * @param wizard
	 * @param treeselection
	 */
	public TestDataExcelExportWizardPage(String pageName,
			IWorkbenchWindow window, 
			IStructuredSelection treeselection) {
		
		super(pageName, window, treeselection);
		
		resourceFileExtension="";
		resourceType = "";
		isTestData=true;
		resourceFileExtensionsforTestData.add("concepttestdata");
		resourceFileExtensionsforTestData.add("eventtestdata");
	}
}
