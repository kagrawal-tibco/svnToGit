package com.tibco.cep.studio.rms.history.actions;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils.fetchRevisionContents;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils.getArtifactContents;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.ui.compare.EMFCompareEditorInput;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;

public class RMSHistoryCompareEditorInput extends EMFCompareEditorInput {

	private String url;
	
	private String repoProjectName;
	
	private String localProjectName;
	
	private long revision;
	
	private boolean isLeftPane = true;
	
	/**
	 * @param configuration
	 */
	public RMSHistoryCompareEditorInput(CompareConfiguration configuration) {
	    this(configuration, null, null, null);
	}
	
	/**
	 * 
	 * @param configuration
	 * @param url
	 * @param localProjectName
	 * @param repoProjectName
	 */
	public RMSHistoryCompareEditorInput(CompareConfiguration configuration, 
			                            String url, 
			                            String localProjectName, 
			                            String repoProjectName) {
		super(configuration);
		this.url = url;
		this.localProjectName = localProjectName;
		this.repoProjectName = repoProjectName;
	}

	/**
	 * Compare for Any copy with left pane by default
	 * @param configuration
	 * @param url
	 * @param projectName
	 * @param revision
	 */
	public RMSHistoryCompareEditorInput(CompareConfiguration configuration, 
			                            String url, 
			                            String localProjectName, 
			                            String repoProjectName, 
			                            long revision) {
	    this(configuration, url, localProjectName, repoProjectName);
	    this.revision = revision;
	}

	/**
	 * Compare with any revision
	 * @param configuration
	 * @param url
	 * @param localProjectName
	 * @param repoProjectName
	 * @param revision
	 * @param isLeftPane
	 */
	public RMSHistoryCompareEditorInput(CompareConfiguration configuration, 
										String url, 
										String localProjectName, 
			                            String repoProjectName, 
										long revision, 
										boolean isLeftPane) {
		this(configuration, url, localProjectName, repoProjectName, revision);
		this.isLeftPane = isLeftPane;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.ICompareEditorInput#getLeftInput(org.eclipse.compare.CompareConfiguration)
	 */
	@Override
	public AbstractResourceNode getLeftInput(CompareConfiguration configuration) throws Exception {
		return createResourceNode(getSelectedObject(true), true);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.ICompareEditorInput#getRightInput(org.eclipse.compare.CompareConfiguration)
	 */
	@Override
	public AbstractResourceNode getRightInput(CompareConfiguration configuration) throws Exception {
		return createResourceNode(getSelectedObject(false), false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.EMFCompareEditorInput#getSelectedObject(boolean)
	 */
	protected Object getSelectedObject(boolean isLeft) throws Exception {
		CompareConfiguration compareConfiguration = getCompareConfiguration();
		IStructuredSelection structuredSelection = getSelection();
		if (revision != 0 && isLeft == isLeftPane) {
			String artifactType = null;
			String artifactPath = null;
			if ((structuredSelection instanceof StructuredSelection) 
					&& (structuredSelection.size() == 1)) {
				//for Master copy, get Artifact Type and Artifact Path from the compare selection
				if (structuredSelection.getFirstElement() instanceof RevisionDetailsItem) {
					RevisionDetailsItem revDetailsItem = (RevisionDetailsItem)structuredSelection.getFirstElement();
					artifactPath = revDetailsItem.getArtifactPath();
					artifactType = revDetailsItem.getArtifactType();
				}
				if (structuredSelection.getFirstElement() instanceof IFile) {
					IFile file = (IFile)structuredSelection.getFirstElement();
					artifactPath = IndexUtils.getFullPath(file);
					artifactType = file.getFileExtension();
				}
			}
			compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_REVISION_ID_LEFT : PROP_COMPARE_REVISION_ID_RIGHT, Long.toString(revision));
			compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE, true);
			compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT : PROP_COMPARE_EXTN_RIGHT, artifactType);
			String name = artifactPath.substring(artifactPath.lastIndexOf("/") + 1);
			compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT : PROP_COMPARE_NAME_RIGHT , name + "." + artifactType);
			String contents = null;
			final String loggedInUsername = AuthTokenUtils.getLoggedinUser();
			if (revision == -1) {
				contents = getArtifactContents(url, loggedInUsername, repoProjectName, artifactPath, artifactType).getArtifactContent(); //contents for master copy
			} else {
				ArtifactRevisionMetadata metaData = fetchRevisionContents(url, repoProjectName, Long.toString(revision), artifactPath, artifactType);
				contents = metaData.getArtifact().getArtifactContent(); //contents from Revision
			}
			return contents;
		}
		
		
		if (structuredSelection != null) {
			if (structuredSelection.getFirstElement() instanceof RevisionDetailsItem) {
				if (structuredSelection.size() == 1) {
					RevisionDetailsItem revDetailsItem = (RevisionDetailsItem)getSelection().getFirstElement();
					String revId = revDetailsItem.getHistoryRevisionItem().getRevisionId();
					String path = revDetailsItem.getArtifactPath();
					String type = revDetailsItem.getArtifactType();
					if (revision != 0) { //for working copy/any revision compare
						compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_REVISION_ID_LEFT : PROP_COMPARE_REVISION_ID_RIGHT, revId);
						compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE, true);
						compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT : PROP_COMPARE_EXTN_RIGHT, type);
						String name = path.substring(path.lastIndexOf("/") + 1);
						compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT : PROP_COMPARE_NAME_RIGHT , name + "." + type);
						ArtifactRevisionMetadata metaData = fetchRevisionContents(url, repoProjectName, revDetailsItem);
						String contents = null;
						if (metaData != null && metaData.getArtifact() != null) {
							contents = metaData.getArtifact().getArtifactContent(); //contents from Revision
						}
						return contents;
					}
					//Revision with working copy
					if (isLeft) {
						compareConfiguration.setProperty(PROP_COMPARE_REVISION_ID_LEFT, revId);
						compareConfiguration.setProperty(PROP_COMPARE_IS_LEFT_REMOTE, true);
						compareConfiguration.setProperty(PROP_COMPARE_EXTN_LEFT, type);
						String name = revDetailsItem.getArtifactPath().substring(revDetailsItem.getArtifactPath().lastIndexOf("/") + 1);
						compareConfiguration.setProperty(PROP_COMPARE_NAME_LEFT, name + "." + type);
						ArtifactRevisionMetadata metaData = fetchRevisionContents(url, repoProjectName, revDetailsItem);
						String contents = null;
						if (metaData != null && metaData.getArtifact() != null) {
							contents = metaData.getArtifact().getArtifactContent(); //contents from Revision
						}
						return contents;
					} else {
						compareConfiguration.setProperty(PROP_COMPARE_REVISION_ID_RIGHT, null);
						compareConfiguration.setProperty(PROP_COMPARE_IS_RIGHT_REMOTE, false);
						compareConfiguration.setProperty(PROP_COMPARE_EXTN_RIGHT, type);
						IFile file = (IFile)ValidationUtils.resolveResourceReference(path + "." + type, localProjectName);
						compareConfiguration.setProperty(PROP_COMPARE_NAME_RIGHT, file.getName());
						compareConfiguration.setProperty(PROP_COMPARE_ABS_PATH_RIGHT, file.getLocation().toString());
						return file;
					}
				}
				//Two Revision details 
				if (structuredSelection.size() == 2 ) {
					Object[] selections = structuredSelection.toArray();
					RevisionDetailsItem revDetailsItem = isLeft == true ?(RevisionDetailsItem)selections[0] : (RevisionDetailsItem)selections[1];
					String revId = revDetailsItem.getHistoryRevisionItem().getRevisionId();
					String type = revDetailsItem.getArtifactType();
					compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_REVISION_ID_LEFT : PROP_COMPARE_REVISION_ID_RIGHT, revId);
					compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE, true);
					compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT : PROP_COMPARE_EXTN_RIGHT, type);
					String name = revDetailsItem.getArtifactPath().substring(revDetailsItem.getArtifactPath().lastIndexOf("/") + 1);
					compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT : PROP_COMPARE_NAME_RIGHT, name + "." + type);
					ArtifactRevisionMetadata metaData = fetchRevisionContents(url, repoProjectName, revDetailsItem);
					String contents = null;
					if (metaData != null && metaData.getArtifact() != null) {
						contents = metaData.getArtifact().getArtifactContent(); //contents from Revision
					}
					return contents;
				}
			}
			if (structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof IFile) {
				IFile file = (IFile)structuredSelection.getFirstElement();
				compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE, false);
				compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT : PROP_COMPARE_EXTN_RIGHT, file.getFileExtension());
				compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT : PROP_COMPARE_NAME_RIGHT, file.getName());
				compareConfiguration.setProperty(isLeft == true ? PROP_COMPARE_ABS_PATH_LEFT : PROP_COMPARE_ABS_PATH_RIGHT, file.getLocation().toString());
				return file;
			}
		} 
		return super.getSelectedObject(isLeft);
	}
}