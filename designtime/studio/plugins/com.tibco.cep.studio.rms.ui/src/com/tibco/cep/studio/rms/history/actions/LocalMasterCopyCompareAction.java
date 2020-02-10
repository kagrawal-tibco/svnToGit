package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.history.RMSHistoryEditor;

public class LocalMasterCopyCompareAction extends HistoryViewDiffAction {
	
	/**
	 * 
	 * @param detailsTableViewer
	 * @param rmsHistoryEditor
	 */
	public LocalMasterCopyCompareAction(TableViewer detailsTableViewer,
			                            RMSHistoryEditor rmsHistoryEditor) {
		super("Compare Working Copy With Master", detailsTableViewer, rmsHistoryEditor);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.actions.HistoryViewDiffAction#getLoopIndex()
	 */
	@Override
	protected int getLoopIndex() {
		return -1;
	}

	/**
	 * 
	 */
	public boolean isEnabled() {
		//Only single selection allowed
		ISelection selection = tableViewer.getSelection();
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
