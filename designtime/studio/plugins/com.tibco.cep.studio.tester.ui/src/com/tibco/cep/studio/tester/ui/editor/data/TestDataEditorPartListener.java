package com.tibco.cep.studio.tester.ui.editor.data;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 * 
 */
public class TestDataEditorPartListener implements IPartListener {

	private TestDataEditor testDataEditor;

	public TestDataEditorPartListener(TestDataEditor testDataEditor) {
		this.testDataEditor = testDataEditor;
	}

	public void partActivated(IWorkbenchPart part) {
		if (part == testDataEditor) {
			StudioUIUtils.refreshPaletteAndOverview(testDataEditor.getEditorSite());
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
