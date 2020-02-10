/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.domain.exportHandler.DomainModelExcelExportHandler;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.decorators.DomainLabelDecorator;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public class DomainModelExcelExportWizard extends ExportExcelWizard<DomainModelExcelExportWizard, DomainLabelDecorator> {
	
	@Override
	public void addPages() {
		if (selection.isEmpty()) {
			MessageDialog.openError(workbenchWindow.getShell(), "Export", "Cannot Export without selecting a resource");
			dispose();
			return;
		} else {
			addPage(page = new DomainModelExcelExportWizardPage(Messages.getString("Export.title"), workbenchWindow, selection));
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.wizard.ExportExcelWizard#performFinish(com.tibco.cep.studio.core.index.model.ELEMENT_TYPES, java.lang.String)
	 */
	@Override
	public boolean performFinish() {
		String filePath = page.getExportFilePath();
		if(new File(filePath).exists()){
			if(!MessageDialog.openConfirm(workbenchWindow.getShell(),"Export", "The file already exists. Do you want to overwrite?")){
				return false;
			}
		}
				
		String resourcePath = page.getResourcePath();
		String project = StudioResourceUtils.getCurrentProject(selection).getName();
		
		if(resourcePath.contains(".projlib")){
			resourcePath = resourcePath.split(".projlib")[1];
		}
		Domain domain = IndexUtils.getDomain(project, resourcePath);
		if (domain == null) {
			MessageDialog.openError(workbenchWindow.getShell(), "Export", "No Domain found for the path");
		}
		DomainModelExcelExportHandler exportHandler = 
			new DomainModelExcelExportHandler(project, filePath, null, null, domain);
		try {
			exportHandler.exportDomain(true);
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					"Export", Messages.getString("Export.Excel.Success", "Domain Model"));
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				MessageDialog.openError(workbenchWindow.getShell(), 
						"Export", Messages.getString("Export.Excel.Failure.File.Open", filePath));
			}
		}
		return true;
	}
}
