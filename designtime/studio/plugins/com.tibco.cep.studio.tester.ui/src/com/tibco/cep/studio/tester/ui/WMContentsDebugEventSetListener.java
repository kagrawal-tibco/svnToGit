package com.tibco.cep.studio.tester.ui;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.core.provider.TestResultsDeserializer;
import com.tibco.cep.studio.tester.core.provider.TesterEngine;
import com.tibco.cep.studio.tester.ui.editor.result.WMResultEditor;
import com.tibco.cep.studio.tester.ui.editor.result.WMResultEditorInput;

public class WMContentsDebugEventSetListener implements IDebugEventSetListener {

	private WMResultEditor editor;
	
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
		if (source instanceof IRuleRunTarget) {
			final IRuleRunTarget runTarget = (IRuleRunTarget)source;
			boolean DEBUG_EVENT_CREATE = false;
			if (event.getKind() == DebugEvent.MODEL_SPECIFIC) {
				if(event.getDetail() == IRuleRunTarget.DEBUG_TASK_REPONSE) {
					//Then do further processing
					try {
						Object data = event.getData();
						if (data instanceof WMContentsInputTask) {
							final WMContentsInputTask wmContentsInputTask = (WMContentsInputTask)data;
							if (wmContentsInputTask.hasResponse()) {
								
								final TesterResultsType testerResultsType = processData(wmContentsInputTask.getResponse());
								final String projectName = runTarget.getBEProject().getName();
								if (testerResultsType != null) {
									testerResultsType.setProject(projectName);
								}
								final String ruleSessionName = wmContentsInputTask.getRuleSessionName();
								
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										try {
											IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
											refreshWMResultViewer(workbenchPage, runTarget, testerResultsType, ruleSessionName, false);
											if (testerResultsType != null && testerResultsType.getExecutionObject().size() == 0) {
												if (!wmContentsInputTask.isReset()) {
													MessageDialog.openInformation(workbenchPage.getWorkbenchWindow().getShell(), "Show Working Memory", "No Working Memory Contents has been found.");
												}
												return;
											} 
										} catch (Exception e) {
											StudioTesterUIPlugin.log(e);
										}
									}
								});
							}
						}
					} catch (Exception e) {
						StudioTesterUIPlugin.log(e);
					}
				}
			}
			if (event.getKind() == DebugEvent.CREATE) {
				DEBUG_EVENT_CREATE = true;
				TesterEngine.INSTANCE.addTarget(runTarget);
			}
			if (event.getKind() == DebugEvent.TERMINATE) {
				TesterEngine.INSTANCE.removeTarget(runTarget);
			}
			
			//Fix: BE-14711 : Tester : When tried to open Working memory contents for running engine, sometimes it shows 'No Rule Sessions Available'.
			//if there is no DebugEvent.CREATE found, then execute below.
			if (!DEBUG_EVENT_CREATE && event.getKind() != DebugEvent.TERMINATE) {
				TesterEngine.INSTANCE.addTarget(runTarget);
			}
		}
	}
	
	/**
	 * @param workbenchPage
	 * @param runTarget
	 * @param testerResultsType
	 * @param ruleSessionName
	 * @param reset
	 * @throws Exception
	 */
	private void refreshWMResultViewer(IWorkbenchPage workbenchPage, 
									   IRuleRunTarget runTarget,
									   TesterResultsType testerResultsType, 
									   String ruleSessionName, 
									   boolean reset) throws Exception {
		if (isWMResultEditorOpen(workbenchPage.getWorkbenchWindow(), ruleSessionName)) {
			if (editor != null) {
				workbenchPage.activate(editor);
				editor.doRefresh(testerResultsType);
			}
		} else {
			if (!reset) {
				WMResultEditorInput resultEditorInput = 
						new WMResultEditorInput(runTarget, ruleSessionName, testerResultsType);
				workbenchPage.openEditor(resultEditorInput, WMResultEditor.ID);
			}
		}		
	}
	
	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private TesterResultsType processData(Object data) throws Exception {
		String responseString = (String) data;
		if (responseString != null) {
			TestResultsDeserializer deserializer = new TestResultsDeserializer();
			TesterResultsType testerResultsType = deserializer.deserialize(responseString);
			return testerResultsType;
//			return TestResultMarshaller.deserialize(responseString);
		}
		return null;
	}
	
	/**
	 * @param window
	 * @param sessionName
	 * @return
	 * @throws PartInitException
	 */
	private boolean isWMResultEditorOpen(IWorkbenchWindow window, String sessionName) throws PartInitException {
		for (IEditorReference ref : window.getActivePage().getEditorReferences()) {
			if (ref.getEditorInput() instanceof WMResultEditorInput) {
				WMResultEditorInput input = (WMResultEditorInput)ref.getEditorInput();
				if (input.getSessionName().equals(sessionName)) {
					editor = (WMResultEditor)ref.getEditor(false);
					return true;
				}
			}
		}
		return false;
	}
}