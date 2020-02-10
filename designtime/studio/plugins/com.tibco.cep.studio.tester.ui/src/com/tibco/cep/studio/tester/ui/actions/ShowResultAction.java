package com.tibco.cep.studio.tester.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowResultAction extends Action {

	private String testerRunPath;
	@SuppressWarnings("unused")
	private IWorkbenchWindow window; 
	@SuppressWarnings("unused")
	private IEditorPart editor;
	
	/**
	 * @param window
	 * @param testerRun
	 * @param tooltip
	 */
	public ShowResultAction(IWorkbenchWindow window, String testerRun, String tooltip) {
		super(testerRun, AS_PUSH_BUTTON);
		if(!testerRun.equalsIgnoreCase(Messages.getString("tester.others")) 
				&& !testerRun.equalsIgnoreCase(Messages.getString("tester.no.sessions"))){
			setImageDescriptor(StudioTesterUIPlugin.getImageDescriptor("icons/tester_run.png"));
		}
		this.testerRunPath = testerRun;
		this.window = window;
		this.setToolTipText(tooltip);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if(testerRunPath.equalsIgnoreCase(Messages.getString("tester.others"))){
			//TODO: 
		} else {
//			TesterPlugin.debug("Show results for test run session:{0}", testerRunPath);
//			Map<String, TesterRun> map = new HashMap<String, TesterRun>();
//			traverseTesterRuns(map);
//			TesterRun run = map.get(testerRunPath);
//			TesterPlugin.debug("Test Run Session:{0}",  run.getRunName());
//			try {
//				editor = null;
//				if (isCurrentRunEditorOpen(window, testerRunPath)) {
//					if (editor != null) {
//						window.getActivePage().activate(editor);
//					}
//				} else {
//					TestResultEditorInput resultEditorInput = new TestResultEditorInput(testerRunPath, run);
//					window.getActivePage().openEditor(resultEditorInput, TestResultEditor.ID);
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * @param window
	 * @param uri
	 * @return
	 * @throws PartInitException
	 */
//	private boolean isCurrentRunEditorOpen(IWorkbenchWindow window, String uri) throws PartInitException {
//		for (IEditorReference ref : window.getActivePage().getEditorReferences()) {
//			if (ref.getEditorInput() instanceof TestResultEditorInput) {
//				TestResultEditorInput input = (TestResultEditorInput)ref.getEditorInput();
//				if (input.getFullPath().equals(uri)) {
//					editor = ref.getEditor(false);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}