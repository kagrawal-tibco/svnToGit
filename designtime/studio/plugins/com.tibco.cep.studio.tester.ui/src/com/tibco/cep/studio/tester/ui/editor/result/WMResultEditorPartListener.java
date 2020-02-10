package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class WMResultEditorPartListener implements IPartListener {

	private WMResultEditor wmResultEditor;

	/**
	 * @param wmResultEditor
	 */
	public WMResultEditorPartListener(WMResultEditor wmResultEditor) {
		this.wmResultEditor = wmResultEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		if (part == wmResultEditor) {
			StudioUIUtils.refreshPaletteAndOverview(wmResultEditor.getEditorSite());
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
