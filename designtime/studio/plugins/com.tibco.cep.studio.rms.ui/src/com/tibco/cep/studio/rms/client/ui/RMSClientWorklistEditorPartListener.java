package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class RMSClientWorklistEditorPartListener implements IPartListener {

	private RMSClientWorklistEditor worklistEditor;

	/**
	 * @param worklistEditor
	 */
	public RMSClientWorklistEditorPartListener(RMSClientWorklistEditor worklistEditor) {
		this.worklistEditor = worklistEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		if (part == worklistEditor) {
			StudioUIUtils.refreshPaletteAndOverview(worklistEditor.getEditorSite());
			worklistEditor.refresh();
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
