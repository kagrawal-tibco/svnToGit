package com.tibco.cep.decision.table.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.ICommandIds;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;

public class AnalyzeTableAction extends Action {

	private IWorkbenchWindow window;
//	private static final String CLASS_NAME = AnalyzeTableAction.class
//			.getName();

	public AnalyzeTableAction(String title) {
		this(title, null);
	}

	public AnalyzeTableAction(String title, IWorkbenchWindow window) {
		super(title);
		this.window = window;
		this.setId(ICommandIds.CMD_ANALYZE_TABLE);
		this.setImageDescriptor(DTImages.getImageDescriptor(DTImages.DT_IMAGES_ANALYZE_ACTION));
		this.setActionDefinitionId(ICommandIds.CMD_ANALYZE_TABLE);
		this.setToolTipText(Messages.getString("AnalyzeTable_title"));
	}

	@Override
	public void run() {
		DecisionTableUIPlugin.log("Analyzing Table...");
		DecisionTableUtil.hideAllEditorPopups(window.getActivePage().getActiveEditor(), true);
		ProgressMonitorDialog d = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		
		IWorkbenchPage page = window.getActivePage();
		DecisionTableUtil.hideAllEditorPopups(window.getActivePage().getActiveEditor(), true);
		if (window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() != null) {
			IEditorPart editor = page.getActiveEditor();

			if (editor instanceof DecisionTableEditor) {
				DecisionTableModelManager dtModelManager = ((DecisionTableEditor) editor)
						.getDecisionTableModelManager();
				if (dtModelManager != null) {
					Table tableEModel = dtModelManager.getTabelEModel();

					if (tableEModel == null) {
						MessageDialog.openError(window.getShell(),
								Messages.getString("AnalyzeTable_title"),
								Messages.getString("AnalyzeTable_message_emptytable"));
					} else {
						try {
//							DecisionTable decisionTable = null;//DecisionTableEMFFactory.createOptimizedDecisionTable(tableEModel);
//							AnalyzeTableRunnable runnable = null;//new AnalyzeTableRunnable(decisionTable, tableEModel, window.getShell());
							d.run(true, true, null);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				MessageDialog.openError(window.getShell(), Messages.getString("AnalyzeTable_title"),
								Messages.getString("AnalyzeTable_message_notableactive"));
				return;
			}
		} else {
			MessageDialog.openError(window.getShell(), Messages.getString("AnalyzeTable_title"),
					Messages.getString("AnalyzeTable_message_notableopen"));
			return;
		}
		
	}

}
