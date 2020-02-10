/**
 *
 */
package com.tibco.cep.studio.tester.ui;

import static com.tibco.cep.studio.tester.core.utils.TesterCoreUtils.processTestResult;
import static com.tibco.cep.studio.tester.core.utils.TesterCoreUtils.saveTestResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.AbstractVmResponseTask;
import com.tibco.cep.studio.debug.input.DebugTestDataInputTask;
import com.tibco.cep.studio.debug.input.TesterInputTask;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.ui.editor.result.TestResultEditor;
import com.tibco.cep.studio.tester.ui.editor.result.TestResultEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.widgets.StudioErrorDialog;

/**
 * @author aathalye
 *
 */
public class TesterDebugEventsSetListener implements IDebugEventSetListener {

	private AbstractVmResponseTask task;
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.eclipse.debug.core.DebugEvent[])
	 */
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		if (events == null || events.length == 0) {
			return;
		}
		DebugEvent event = events[0];
		Object source = event.getSource();
		task = null;
		if (source instanceof IRuleRunTarget) {
			IRuleRunTarget runTarget = (IRuleRunTarget)source;
			if (event.getKind() == DebugEvent.MODEL_SPECIFIC) {
				if (event.getDetail() == IRuleRunTarget.DEBUG_TASK_REPONSE) {

					//Then do further processing
					try {
						Object data = event.getData();
						if (data instanceof TesterInputTask || data instanceof DebugTestDataInputTask) {

							if (data instanceof TesterInputTask) {
								task = (TesterInputTask) data;
							}
							if (data instanceof DebugTestDataInputTask) {
								task = (DebugTestDataInputTask) data;
							}
							// check if the task has response inside it.
							if (task.hasResponse()) {
								final Object response = task.getResponse();
								final TesterResultsType testerResultsType = processTestResult(response, TesterDebugEventsSetListener.class.getName());
								final String projectName = runTarget.getBEProject().getName();
								Display.getDefault().asyncExec(new Runnable() {

									/* (non-Javadoc)
									 * @see java.lang.Runnable#run()
									 */

									public void run() {
										if (testerResultsType != null) {
											testerResultsType.setProject(projectName);
											IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
											if (testerResultsType.getExecutionObject().size() == 0) {
												MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Show Test Result", "No test results have been found.");
												return;
											}

											try {
												String outputPath = StudioUIPlugin.getDefault().getPreferenceStore().getString(StudioUIPreferenceConstants.TEST_DATA_OUTPUT_PATH);
												IFile file = saveTestResult(testerResultsType.getRunName(), projectName, testerResultsType, outputPath, false);
												//file Save
//												IFile file = save(workbenchPage, testerResultsType.getRunName(), projectName, testerResultsType);
												TestResultEditorInput resultEditorInput = new TestResultEditorInput(file, testerResultsType/*, runTarget.getRuleSessions()*/);
												workbenchPage.openEditor(resultEditorInput, TestResultEditor.ID);
											} catch (Exception e) {
												StudioTesterUIPlugin.log(e);
											}
										}
									}
								});
							}
						}
					} catch (final Exception e) {
						Display.getDefault().asyncExec(new Runnable() {
							/* (non-Javadoc)
							 * @see java.lang.Runnable#run()
							 */
							@Override
							public void run() {
								StudioErrorDialog.openError(Display.getDefault().getActiveShell(), "Tester", "Test Data can't be asserted.", "Runtime Exception occurred. Please check the console view for details.");
							}});
						StudioTesterUIPlugin.log(e);
					}
				}
			}
		}
	}

}