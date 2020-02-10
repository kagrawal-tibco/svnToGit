/**
 * 
 */
package com.tibco.cep.decision.table.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.decision.table.ICommandIds;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;

/**
 * @author ggrigore
 * 
 */
@SuppressWarnings("unused")
public class ImportAction extends Action {

	private final IWorkbenchWindow window;
	private IStructuredSelection selection;

	public ImportAction(String title) {
		this(title, null);
	}

	public ImportAction(String title, IWorkbenchWindow window) {
		super(title);

		this.window = window;
		this.setId(ICommandIds.CMD_IMPORT_EXCEL);
		this.setToolTipText(Messages.getString("DT_ImportExcel_Action_Tooltip"));
		this.setActionDefinitionId(ICommandIds.CMD_IMPORT_EXCEL);
		this.setImageDescriptor(DTImages
				.getImageDescriptor(DTImages.DT_IMAGES_IMPORT_EXCEL));
	}

	public ImportAction(String title, IWorkbenchWindow window,
			IStructuredSelection selection) {
		super(title);

		this.window = window;
		this.selection = selection;
		this.setId(ICommandIds.CMD_IMPORT_EXCEL);
		this.setToolTipText(Messages.getString("DT_ImportExcel_Action_Tooltip"));
		this.setActionDefinitionId(ICommandIds.CMD_IMPORT_EXCEL);
		this.setImageDescriptor(DTImages
				.getImageDescriptor(DTImages.DT_IMAGES_IMPORT_EXCEL));
	}

	public void run() {
//		DecisionTableUtil.hideAllEditorPopups(window.getActivePage().getActiveEditor(), true);
//		Wizard importExcelWizard = new ImportExcelWizard(window, selection);
//		importExcelWizard.setNeedsProgressMonitor(true);
//		WizardDialog wd = new WizardDialog(window.getShell(), importExcelWizard) {
//			@Override
//			protected void createButtonsForButtonBar(Composite parent) {
//				super.createButtonsForButtonBar(parent);
//				Button finishButton = getButton(IDialogConstants.FINISH_ID);
//				finishButton.setText(IDialogConstants.OK_LABEL);
//			}
//		};
//		wd.setMinimumPageSize(320, 10);
//		wd.create();
//		wd.open();

	}

}