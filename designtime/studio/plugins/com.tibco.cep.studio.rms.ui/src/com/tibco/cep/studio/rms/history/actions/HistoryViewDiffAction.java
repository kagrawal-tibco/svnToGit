package com.tibco.cep.studio.rms.history.actions;

import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;

public abstract class HistoryViewDiffAction extends Action {
	
	protected static final int DIFF_ELEMENTS_COUNT = 2;
	
	protected TableViewer tableViewer;
	
	protected RMSHistoryEditor rmsHistoryEditor;

	public HistoryViewDiffAction(String actionName,
			                     TableViewer tableViewer,
			                     RMSHistoryEditor rmsHistoryEditor) {
		super(actionName);
		this.tableViewer = tableViewer;
		this.rmsHistoryEditor = rmsHistoryEditor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if (!tableViewer.getSelection().isEmpty()) {
			ISelection selection = tableViewer.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection)selection;
				Object[] selectedObjects = structuredSelection.toArray();
				String[] selectedArtifactPaths = new String[DIFF_ELEMENTS_COUNT];
				String[] selectedArtifactTypes = new String[DIFF_ELEMENTS_COUNT];
				String[] selectedRevisionIds = new String[DIFF_ELEMENTS_COUNT];
				
				fillArraysWithNull(selectedArtifactPaths, selectedArtifactTypes, selectedRevisionIds);
				
				for (int loop = 0; loop < selectedObjects.length; loop++) {
					Object selectedObject = selectedObjects[loop];
					if (selectedObject instanceof RevisionDetailsItem) {
						RevisionDetailsItem revisionDetailsItem = (RevisionDetailsItem)selectedObject;
						selectedArtifactPaths[loop] = revisionDetailsItem.getArtifactPath();
						selectedArtifactTypes[loop] = revisionDetailsItem.getArtifactType();
						
						loop = loop + getLoopIndex();
						//Get revision id
						HistoryRevisionItem historyRevisionItem = revisionDetailsItem.getHistoryRevisionItem();
						if (loop < 0) {
							break;
						}
						selectedRevisionIds[loop] = historyRevisionItem.getRevisionId();
					}
				}
				try {
					showDiffs(selectedRevisionIds, selectedArtifactPaths, selectedArtifactTypes);
				} catch (Exception e) {
					RMSUIPlugin.log(e);
				}
			}
		}
	}
	
	protected void fillArraysWithNull(String[]...arrays) {
		for (String[] array : arrays) {
			Arrays.fill(array, null);
		}
	}
	
	/**
	 * An integer to tell how to fill various arrays.
	 * @return
	 */
	protected abstract int getLoopIndex();
	
	//TODO Many things
	protected Object showDiffs(String[] diffRevisionIds, 
			                   String[] diffArtifactPaths,
			                   String[] diffArtifactExtensions) throws Exception {
		String diffRevId1 = diffRevisionIds[0];
		String diffRevId2 = diffRevisionIds[1];
		String diffArtifactPath1 = diffArtifactPaths[0];
		String diffArtifactPath2 = diffArtifactPaths[1];
		String diffArtifactExtension1 = diffArtifactExtensions[0];
		String diffArtifactExtension2 = diffArtifactExtensions[1];
		
		long diffRevisionId1 = (diffRevId1 == null) ? -1 : Long.parseLong(diffRevId1);
		long diffRevisionId2 = (diffRevId2 == null) ? -1 : Long.parseLong(diffRevId2);
		
		RMSHistoryEditorInput historyEditorInput = (RMSHistoryEditorInput)rmsHistoryEditor.getEditorInput();
		return ArtifactsManagerClient.compareRevisions(historyEditorInput.getHistoryURL(),
				                                       historyEditorInput.getRepoProjectName(),
				                                       diffArtifactPath1, 
				                                       diffArtifactExtension1, 
				                                       diffArtifactPath2,
				                                       diffArtifactExtension2,
				                                       diffRevisionId1, 
				                                       diffRevisionId2);
	}
	
	public abstract boolean isEnabled();
}
