/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.decorators.DomainLabelDecorator;

/**
 * @author aathalye
 *
 */
public class DomainModelExcelExportWizardPage extends
		ExportExcelWizardPage<DomainModelExcelExportWizard, DomainLabelDecorator> {
	
	/**
	 * @param pageName
	 * @param window
	 * @param wizard
	 * @param treeselection
	 */
	public DomainModelExcelExportWizardPage(String pageName,
			IWorkbenchWindow window, 
			IStructuredSelection treeselection) {
		super(pageName, window, treeselection);
		resourceFileExtension = "domain";
		resourceType = "Domain";
	}
}
