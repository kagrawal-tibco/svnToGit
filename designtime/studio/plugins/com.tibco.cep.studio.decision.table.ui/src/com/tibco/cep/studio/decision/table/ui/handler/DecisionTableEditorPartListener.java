package com.tibco.cep.studio.decision.table.ui.handler;

import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.studio.decision.table.ui.editors.DecisionTableEditor;

/**
 * 
 * @author sasahoo
 * @author aathalye
 * @author rmishra
 * 
 */
public class DecisionTableEditorPartListener implements IPartListener {

	private DecisionTableEditor _editor;

	/**
	 * @param editor
	 */
	public DecisionTableEditorPartListener(DecisionTableEditor editor) {
		this._editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {

		if (part instanceof DecisionTableEditor) {
			DecisionTableEditor editor = (DecisionTableEditor)part;
			if (editor.isInvalidPart()) {
				return;
			}
			// Ensure that only the listener for the attached editor executes
			if (part.getSite().getPage().getActiveEditor() != editor) {
				return;
			}
			IEditorSite site = (IEditorSite) editor.getSite();
			final IWorkbenchPage activePage = site.getPage();
			if (activePage == null) {
				return;
			}
			editor.activateDTEditorContext();
			editor.openEditorPerspective(site);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(final IWorkbenchPart part) {


		if (part instanceof DecisionTableEditor) {
			DecisionTableEditor editor = (DecisionTableEditor)part;
			if (editor.isInvalidPart()) {
				return;
			}
			// Ensure that only the listener for the attached editor executes
			if (part.getSite().getPage().getActiveEditor() != editor) {
				return;
			}
			IEditorSite site = (IEditorSite) editor.getSite();
			final IWorkbenchPage activePage = site.getPage();
			if (activePage == null) {
				return;
			}
			editor.deactivateDTEditorContext();
		}
	
	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

}
