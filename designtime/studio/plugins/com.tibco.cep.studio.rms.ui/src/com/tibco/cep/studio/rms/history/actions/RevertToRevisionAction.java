package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

public class RevertToRevisionAction extends Action {
	
	private TableViewer viewer;
	
	public RevertToRevisionAction(TableViewer viewer) {
		super("Revert to this revision");
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		//viewer.getSelection()
	}
	
	/**
	 * 
	 */
	public boolean isEnabled() {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			int selectionSize = structuredSelection.size();
			if (selectionSize != 1) {
				setEnabled(false);
				return false;
			}
		}
		setEnabled(true);
		return true;
	}
}