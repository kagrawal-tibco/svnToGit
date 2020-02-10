package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * Handle diffs between 2 artifact paths either from same revision
 * or different revisions or different artifacts from same revision
 * or different artifacts from different revisions.
 * @author sasahoo
 *
 */
public class RevisionDetailsDiffAction extends HistoryViewDiffAction {
			
	/**
	 * @param detailsTableViewer
	 * @param rmsHistoryEditor
	 */
	public RevisionDetailsDiffAction(TableViewer detailsTableViewer, RMSHistoryEditor rmsHistoryEditor) {
		super("Show differences", detailsTableViewer, rmsHistoryEditor);
	}
		
	/**
	 * 
	 */
	public boolean isEnabled() {
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			int selectionSize = structuredSelection.size();
			if (selectionSize != 2) {
				setEnabled(false);
				return false;
			}
			if (selectionSize == 2) {
				Object[] selections = structuredSelection.toArray();
				RevisionDetailsItem revDetailsItem1 = (RevisionDetailsItem)selections[0];
				RevisionDetailsItem revDetailsItem2 = (RevisionDetailsItem)selections[1];
				if (revDetailsItem1.getArtifactPath().equals(revDetailsItem2.getArtifactPath())
						&& revDetailsItem1.getArtifactType().equals(revDetailsItem2.getArtifactType())) {
					//TODO
				} else {
					setEnabled(false);
					return false;
				}
				
			}
		}
		setEnabled(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if (!tableViewer.getSelection().isEmpty() && tableViewer.getSelection() instanceof IStructuredSelection) {
			StructuredSelection s = (StructuredSelection)tableViewer.getSelection();
			CompareConfiguration cc = new CompareConfiguration(StudioUIPlugin.getDefault().getPreferenceStore());
			RMSHistoryEditorInput editorInput = (RMSHistoryEditorInput)rmsHistoryEditor.getEditorInput();
			String localProjectName = editorInput.getLocalProjectName();
			String repoProjectName = editorInput.getRepoProjectName();
			String url = editorInput.getHistoryURL();
			RMSHistoryCompareEditorInput input = new RMSHistoryCompareEditorInput(cc, url, localProjectName, repoProjectName);
			input.setSelection(s);
			CompareUI.openCompareDialog(input);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.actions.HistoryViewDiffAction#getLoopIndex()
	 */
	@Override
	protected int getLoopIndex() {
		return 0;
	}
}