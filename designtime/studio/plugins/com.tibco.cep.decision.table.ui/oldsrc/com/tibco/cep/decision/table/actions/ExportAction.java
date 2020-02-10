/**
 * 
 */
package com.tibco.cep.decision.table.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.ICommandIds;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelExportProvider;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decisionproject.util.DecisionProjectUtil;

/**
 * @author ggrigore
 * 
 */
public class ExportAction extends Action {
	private final IWorkbenchWindow window;

	public ExportAction(String title) {
		this(title, null);
	}

	public ExportAction(String title, IWorkbenchWindow window) {
		super(title);

		this.window = window;
		this.setId(ICommandIds.CMD_EXPORT_EXCEL);
		this.setToolTipText(Messages.getString("DT_ExportExcel_Action_Tooltip"));
		this.setActionDefinitionId(ICommandIds.CMD_EXPORT_EXCEL);
		this.setImageDescriptor(DTImages
				.getImageDescriptor(DTImages.DT_IMAGES_EXCEL_ACTION));
	}

	public void run() {
		DecisionTableUtil.hideAllEditorPopups(window.getActivePage().getActiveEditor(), true);
		FileDialog fd = new FileDialog(this.window.getShell(), SWT.SAVE);

		// String[] extensions = { "*.xls", "*.csv", "*.xlt", "*.xlw", "*.xla",
		// "*.*" };
		String[] extensions = { "*.xls", "*.prr" };
		fd.setText(Messages.getString("DT_ExportExcel_Action_Dialog_Title"));
		fd.setFilterExtensions(extensions);
		if (DecisionProjectUtil.DIRECTORY_FILE_PATH != null)
			fd.setFilterPath(DecisionProjectUtil.DIRECTORY_FILE_PATH);
		final String fullFilename = fd.open();
		if (fullFilename != null) {
			File dfile = new File(fullFilename);
			DecisionProjectUtil.DIRECTORY_FILE_PATH = dfile.getParent();
			String filename = fd.getFileName();

			if (filename == null) {
				return;
			} else {

				if (filename.endsWith(".prr")) {
					// write code to export to PRR format

				} else if (filename.endsWith(".xls")) {

					final ProgressMonitorDialog pdialog = new ProgressMonitorDialog(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell()) {
						@Override
						protected void createDialogAndButtonArea(
								Composite parent) {
							dialogArea = createDialogArea(parent);
							applyDialogFont(parent);
						}

					};
					pdialog.setCancelable(false);
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							try {
							monitor.beginTask("Exporting Decision Table...", 100);
							monitor.worked(10);
							File file = new File(fullFilename);
							if (file.exists()) {
								String[] buttonLabels = { Messages.getString("YES"),
										Messages.getString("NO") };
								MessageDialog md = new MessageDialog(window
										.getShell(), Messages.getString("Warning_Title"),
										null, Messages.getString("Warning_Text1")
												+ fullFilename + "?\n\n",
										MessageDialog.QUESTION, buttonLabels, 0);

								int rc = md.open();

								if (rc == 1) {
									monitor.setTaskName("Export Error");
									monitor.done();
									return;
								}
							}
							monitor.worked(40);
							//ExcelExportProvider exporter = new ExcelExportProvider(
								//	fullFilename, Messages.Sheet_Name, null);

							IWorkbenchPage page = window.getActivePage();

							if (window.getActivePage() != null
									&& window.getActivePage().getActiveEditor() != null) {
								IEditorPart editor = page.getActiveEditor();
								monitor.worked(40);
								if (editor instanceof DecisionTableEditor) {
									DecisionTableModelManager dtModelManager = ((DecisionTableEditor) editor)
											.getDecisionTableModelManager();
									if (dtModelManager != null) {
										Table tableEModel = dtModelManager
												.getTabelEModel();
										if (tableEModel == null) {
//											ExcelExportProvider
//													.showError(
//															Messages.getString("Export_title"),
//															Messages.getString("ExportExcel_message_emptytable"));
										} else {
											ExcelExportProvider exporter = new ExcelExportProvider(null,
												fullFilename, Messages.getString("Sheet_Name"), tableEModel);
											//exporter
												//	.setTableEModel(tableEModel);
											exporter.exportWorkbook();
											monitor.worked(10);
											monitor.setTaskName("Export completed");
											monitor.done();
										}
									}
								} else {
									monitor.setTaskName("Export Error");
									monitor.done();
//									ExcelExportProvider
//											.showError(
//													Messages.getString("Export_title"),
//													Messages.getString("Export_message_notableactive"));
								}
							} else {
								monitor.setTaskName("Export Error");
								monitor.done();
//								ExcelExportProvider.showError(
//										Messages.getString("Export_title"),
//										Messages.getString("Export_message_notableopen"));
							}
							} catch (Exception e){
								//commented so that error is not shown on console
								//DecisionTableUIPlugin.LOGGER.logError(this.getClass().getName(), "Export Failed::",e);
								monitor.setTaskName("Export Error");
								monitor.done();						
							}
						
						}
						
					};
					try {
						pdialog.run(false, true, runnable);

					} catch (InvocationTargetException e) {
						DecisionTableUIPlugin.log("Export Failed::", e);
					} catch (InterruptedException e) {
						DecisionTableUIPlugin.log("Export Failed::", e);
					} catch (Exception e) {
						DecisionTableUIPlugin.log("Export Failed::", e);
					}
					pdialog.close();
				}
			}
		}
	}
}