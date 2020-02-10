package com.tibco.cep.studio.rms.history;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author sasahoo
 *
 */
public class RMSHistoryEditorPartListener implements IPartListener {

	private RMSHistoryEditor historyEditor;

	public RMSHistoryEditorPartListener(RMSHistoryEditor historyEditor) {
		this.historyEditor = historyEditor;
	}

	public void partActivated(IWorkbenchPart part) {
		if (part == historyEditor) {
			StudioUIUtils.refreshPaletteAndOverview(historyEditor.getEditorSite());
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
