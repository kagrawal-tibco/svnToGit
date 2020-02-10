package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class RevisionMasterCopyCompareAction extends HistoryViewDiffAction {

	public RevisionMasterCopyCompareAction(TableViewer tableViewer, 
			                               RMSHistoryEditor rmsHistoryEditor) {
		super("Compare with master copy", tableViewer, rmsHistoryEditor);
	}

	@Override
	protected int getLoopIndex() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.actions.HistoryViewDiffAction#isEnabled()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.actions.HistoryViewDiffAction#run()
	 */
	public void run() {
		if (!tableViewer.getSelection().isEmpty() && tableViewer.getSelection() instanceof IStructuredSelection) {
			StructuredSelection s = (StructuredSelection)tableViewer.getSelection();
			CompareConfiguration cc = new CompareConfiguration(StudioUIPlugin.getDefault().getPreferenceStore());
			RMSHistoryEditorInput editorInput = (RMSHistoryEditorInput)rmsHistoryEditor.getEditorInput();
			String localProjectName = editorInput.getLocalProjectName();
			String repoProjectName = editorInput.getRepoProjectName();
			String url = editorInput.getHistoryURL();
			RMSHistoryCompareEditorInput input = new RMSHistoryCompareEditorInput(cc, url, localProjectName, repoProjectName, -1);
			input.setSelection(s);
			CompareUI.openCompareDialog(input);
		}
	}
}