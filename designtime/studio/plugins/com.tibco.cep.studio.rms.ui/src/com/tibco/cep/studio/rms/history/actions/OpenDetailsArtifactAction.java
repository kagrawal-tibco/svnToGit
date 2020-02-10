package com.tibco.cep.studio.rms.history.actions;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.openArtifactEditor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;

/**
 * 
 * @author sasahoo
 *
 */
public class OpenDetailsArtifactAction extends Action {
	
	private TableViewer detailsTableiewer;
	private RMSHistoryEditor rmsHistoryEditor;
	
	/**
	 * @param detailsTableiewer
	 * @param rmsHistoryEditor
	 */
	public OpenDetailsArtifactAction(TableViewer detailsTableiewer, RMSHistoryEditor rmsHistoryEditor) {
		super("Open...");
		this.detailsTableiewer = detailsTableiewer;
		this.rmsHistoryEditor = rmsHistoryEditor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = detailsTableiewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof RevisionDetailsItem) {
				RevisionDetailsItem revisionDetailsItem = (RevisionDetailsItem)firstElement;
				RMSHistoryEditorInput editorInput = (RMSHistoryEditorInput)rmsHistoryEditor.getEditorInput();
				String projectName = editorInput.getRepoProjectName();
				String url = editorInput.getHistoryURL();
				String artifactPath = revisionDetailsItem.getArtifactPath();
				String name = artifactPath.substring(artifactPath.lastIndexOf("/") + 1);
				String fileName = name + "." + revisionDetailsItem.getArtifactType();
				String contents = ArtifactsManagerUtils.fetchRevisionContents(url, projectName, revisionDetailsItem).getArtifact().getArtifactContent();
				openArtifactEditor(artifactPath, 
								   contents, 
								   projectName, 
								   fileName, 
								   revisionDetailsItem.getArtifactType(), 
								   revisionDetailsItem.getHistoryRevisionItem().getRevisionId());
			}
		}
	}
	
	public boolean isEnabled() {
		ISelection selection = detailsTableiewer.getSelection();
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