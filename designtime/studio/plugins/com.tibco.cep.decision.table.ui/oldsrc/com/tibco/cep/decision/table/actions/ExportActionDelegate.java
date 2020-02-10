package com.tibco.cep.decision.table.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelExportProvider;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.util.DecisionProjectUtil;

public class ExportActionDelegate implements IObjectActionDelegate {

	private final IWorkbenchWindow window;
	private ISelection currentSelection;
	private Table tableEModel;

	public ExportActionDelegate() {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		FileDialog fd = new FileDialog(window.getShell(), SWT.SAVE);
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
								monitor.beginTask(
										"Exporting Decision Table...", 100);
								monitor.worked(10);
								File file = new File(fullFilename);
								if (file.exists()) {
									String[] buttonLabels = {Messages.getString("YES"),
											Messages.getString("NO")};
									MessageDialog md = new MessageDialog(window
											.getShell(),
											Messages.getString("Warning_Title"), null,
											Messages.getString("Warning_Text1")
													+ fullFilename + "?\n\n",
											MessageDialog.QUESTION,
											buttonLabels, 0);

									int rc = md.open();

									if (rc == 1) {
										monitor.setTaskName("Export Error");
										monitor.done();
										return;
									}
								}

								//ExcelExportProvider exporter = new ExcelExportProvider(
									//	fullFilename, Messages.Sheet_Name, null);
								monitor.worked(40);
								if (!(currentSelection instanceof IStructuredSelection)) {
									monitor.setTaskName("Export Error");
									monitor.done();
									return;
								}
								Object selectedObject = ((IStructuredSelection) currentSelection)
										.getFirstElement();
								if (!(selectedObject instanceof Implementation)) {
									monitor.setTaskName("Export Error");
									monitor.done();
									return;
								}
								if (!(selectedObject instanceof Table)) {
									monitor.setTaskName("Export Error");
									monitor.done();
									return;
								}
								tableEModel = (Table) selectedObject;
								monitor.worked(40);
								if (tableEModel == null) {
//									ExcelExportProvider.showError(
//											Messages.getString("Export_title"),
//											Messages.getString("ExportExcel_message_emptytable"));
									monitor.setTaskName("Export Error");
									monitor.done();
								} else {
									ExcelExportProvider exporter = new ExcelExportProvider(null,
										fullFilename, Messages.getString("Sheet_Name"), tableEModel);
									//exporter.setTableEModel(tableEModel);
									exporter.exportWorkbook();
									monitor.worked(10);
									monitor.setTaskName("Export completed");
									monitor.done();
								}

							} catch (Exception e) {
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

	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection;
	}
}
