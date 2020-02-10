package com.tibco.cep.studio.decision.table.ui.wizard;

import java.io.File;
import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelExportException;
import com.tibco.cep.decision.table.provider.excel.ExcelExportProvider;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.export.ExportExcelWizard;

public class DTExcelExportWizard extends ExportExcelWizard<DTExcelExportWizard, DTLabelDecorator> {
	
	@Override
	public void addPages() {
		if (selection.isEmpty()) {
			MessageDialog.openError(workbenchWindow.getShell(), "Export", "Cannot Export without selecting a resource");
			dispose();
			return;
		} else {
			addPage(page = new DTExcelExportWizardPage(Messages.getString("Export.title"), workbenchWindow, selection));
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
		if(resourcePath.contains(".projlib")){
			resourcePath = resourcePath.split(".projlib")[1];
		}
		String project = 
			StudioResourceUtils.getCurrentProject(selection).getName();
		DecisionTableElement decisiontableElement = 
			 (DecisionTableElement)IndexUtils.getElement(project, resourcePath, ELEMENT_TYPES.DECISION_TABLE);
		Table table = (Table)decisiontableElement.getImplementation();
		
		ExcelExportProvider provider = new ExcelExportProvider(project, 
				                                               filePath, 
				                                               "DecisionTable", 
				                                               table);
		Collection<ValidationError> excelVldErrorCollection = provider.getExcelVldErrorCollection();
		
		
		final IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		//TODO add proper support for preference store
		boolean useColumnAlias = prefStore.getBoolean(PreferenceConstants.USE_ALIAS_IN_EXPORT);
		try {
			if (useColumnAlias) {
				provider.setUseColumnAlias(true);
			}
			provider.exportWorkbook();
			if(excelVldErrorCollection.size() >0 ){
				StringBuffer buf = new StringBuffer();
				buf.append("Invalid Excel file (");
				int size = excelVldErrorCollection.size();
				int ctr = 1;
				buf.append(size);
				buf.append(" errors): ");
				for (ValidationError validationError : provider.getExcelVldErrorCollection()) {
					buf.append(validationError.getErrorMessage());
					if (ctr < size) {
						buf.append(", ");
					} else {
						buf.append('.');
					}
					ctr++;
				}
				MessageDialog.openError(workbenchWindow.getShell(),
						"Export", "The Decision Table is not valid:\n"+buf);
					return false;
			}
				
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					"Export", Messages.getString("Export.Excel.Success", "Decision table"));
		} catch (ExcelExportException e) {
			DecisionTableUIPlugin.log(e);
			MessageDialog.openError(workbenchWindow.getShell(), 
						"Export", Messages.getString("Export.Excel.Failure", e.getMessage()));
		}
		return true;
	}
}

