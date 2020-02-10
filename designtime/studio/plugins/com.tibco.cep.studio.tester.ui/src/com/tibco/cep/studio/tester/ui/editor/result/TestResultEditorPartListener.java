package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultEditorPartListener implements IPartListener {

	private TestResultEditor testResultEditor;

	/**
	 * @param testResultEditor
	 */
	public TestResultEditorPartListener(TestResultEditor testResultEditor) {
		this.testResultEditor = testResultEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		if (part == testResultEditor) {
			StudioUIUtils.refreshPaletteAndOverview(testResultEditor.getEditorSite());
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
}