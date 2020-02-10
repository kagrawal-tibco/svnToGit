package com.tibco.cep.studio.rms.ui.actions;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.openArtifactEditor;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;

/**
 * 
 * @author sasahoo
 *
 */
public class OpenReadOnlyArtifactEditorAction extends org.eclipse.jface.action.Action {
	
	private Viewer viewer;
	
	public OpenReadOnlyArtifactEditorAction(Viewer viewer) {
		super("Open...");
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		Object object = ((IStructuredSelection)viewer.getSelection()).getFirstElement();
		CommittedArtifactDetails committedArtifactDetails = (CommittedArtifactDetails)object;
		ArtifactReviewTask artifactReviewTask = committedArtifactDetails.getParent();
		String projectName = artifactReviewTask.getProjectName();
		String revisionId = artifactReviewTask.getTaskId();
		String artifactType = committedArtifactDetails.getArtifact().getArtifactExtension();
		String artifactPath = committedArtifactDetails.getArtifact().getArtifactPath();
		String contents = ArtifactsManagerUtils.fetchRevisionContents(RMSUtil.buildRMSURL(), projectName, 
				revisionId, artifactPath, artifactType).getArtifact().getArtifactContent();
		String name = artifactPath.substring(artifactPath.lastIndexOf("/") + 1);
		String fileName = name + "." + artifactType;
		openArtifactEditor(artifactPath, 
						   contents, 
						   projectName, 
						   fileName, 
						   artifactType, 
						   revisionId);
	}
}