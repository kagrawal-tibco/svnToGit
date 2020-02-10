/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryStateChangeListener;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.manager.IArtifactResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.IArtifactResourceChangeListener;
import com.tibco.cep.studio.rms.artifacts.manager.IRemoteResourceDelta;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;

/**
 * @author aathalye
 * 
 */
public class ArtifactsSummaryChangeListener implements IResourceChangeListener, IArtifactResourceChangeListener {
	
	private IArtifactSummaryStateChangeListener stateChangeListener;
	
	private List<IProject> 						affectedProjects = new ArrayList<IProject>();
	
	private IResourceDeltaVisitor 				resourceDeltaVisitor;
	
	private static final String                 RMS_ARTIFACTS_SUMMARY_FILENAME = "ars";
	
	private boolean blockLocalRefreshEvent, allowLocalRefreshEvent;
	
	public ArtifactsSummaryChangeListener() {
		this.stateChangeListener = new ArtifactSummaryStateManager();
		this.blockLocalRefreshEvent = false;
		this.allowLocalRefreshEvent = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */

	public void resourceChanged(IResourceChangeEvent event) {
		if (!(event instanceof IArtifactResourceChangeEvent)) {
			if (!(event.getType() == IResourceChangeEvent.POST_CHANGE)) {
				return;
			}
		}
		affectedProjects.clear();
		IResourceDelta delta = event.getDelta();
		
		IResourceDeltaVisitor visitor = getResourceDeltaVisitor();
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			RMSCorePlugin.log(e);
		}

		refreshArsFiles(delta);
		
		if (allowLocalRefreshEvent) blockLocalRefreshEvent = allowLocalRefreshEvent = false;
	}
	
	
	private IResourceDeltaVisitor getResourceDeltaVisitor() {
		if (resourceDeltaVisitor == null) {
			resourceDeltaVisitor = new IResourceDeltaVisitor() {

				public boolean visit(IResourceDelta delta) throws CoreException {
					IResource cachedResource = delta.getResource();
					if (delta instanceof IRemoteResourceDelta) {
						visit((IRemoteResourceDelta)delta);
					}
					int type = delta.getKind();
					int flags = delta.getFlags();
					if (type == IResourceDelta.REMOVED) {
						if (cachedResource instanceof IProject) {
							return true;
						}
						if (cachedResource instanceof IFile) {
							IFile file = (IFile)cachedResource;
							if (!RMS_ARTIFACTS_SUMMARY_FILENAME.equals(file.getFileExtension())) {
								fireArtifactLocalRemovalEvent(file);
							}
							return true;
						}
					} else if (type == IResourceDelta.CHANGED) {
						//Also check flag
						if ((flags & IResourceDelta.CONTENT) == flags) {
							if (cachedResource instanceof IFile && !blockLocalRefreshEvent) {
								IFile file = (IFile)cachedResource;
								if (!RMS_ARTIFACTS_SUMMARY_FILENAME.equals(file.getFileExtension())) {
									fireArtifactLocalModificationEvent(file);
								}
								return true;
							}
							if (blockLocalRefreshEvent) allowLocalRefreshEvent = true;
						}
					} else if (type == IResourceDelta.ADDED) {
						if (cachedResource instanceof IProject) {
							return true;
						}
						if (cachedResource instanceof IFile) {
							IFile file = (IFile)cachedResource;
							if (!RMS_ARTIFACTS_SUMMARY_FILENAME.equals(file.getFileExtension())) {
								fireArtifactLocalCreationEvent(file);
							}
							return true;
						}
					}
					return true;
				}
				
				private boolean visit(IRemoteResourceDelta remoteResourceDelta) {
					int type = remoteResourceDelta.getKind();
					if (type == IRemoteResourceDelta.REMOTE_REMOVED) {
						Artifact artifact = remoteResourceDelta.getArtifact();
						String project = remoteResourceDelta.getProject();
						fireArtifactRemoteRemovalEvent(project, artifact);
						return true;
					} else if (type == IRemoteResourceDelta.REMOTE_MODIFIED) {
						Artifact artifact = remoteResourceDelta.getArtifact();
						String project = remoteResourceDelta.getProject();
						fireArtifactRemoteModificationEvent(project, artifact);
						blockLocalRefreshEvent = true;
						return true;
					} else if (type == IRemoteResourceDelta.REMOTE_ADDED) {
						Artifact artifact = remoteResourceDelta.getArtifact();
						String project = remoteResourceDelta.getProject();
						fireArtifactRemoteCreationEvent(project, artifact);
						return true;
					} else if (type == IRemoteResourceDelta.COMMIT) {
						Artifact artifact = remoteResourceDelta.getArtifact();
						String project = remoteResourceDelta.getProject();
						fireArtifactCommitSuccessEvent(project, artifact);
						return true;
					}
					return true;
				}
			};
		}
		return resourceDeltaVisitor;
	}

	private void refreshArsFiles(IResourceDelta delta) {
		if (affectedProjects.size() > 0) {
			for (IProject project : affectedProjects) {
				IPath dtlPath = 
					Path.fromOSString("." + ArtifactsSummaryPersistenceHandler.RMS_ARTIFACTS_SUMMARY_FILENAME);
				final IFile arsFile = project.getFile(dtlPath);	
				if (arsFile.exists()) {
					CommonUtil.refresh(arsFile, 0, true);
				}
			}
		}
	}

	private void addAffectedProject(IFile resourceFile) {
		if (!affectedProjects.contains(resourceFile.getProject())) {
			affectedProjects.add(resourceFile.getProject());
		}
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactLocalCreationEvent(IFile resourceFile) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(resourceFile, SummaryStateChangeEvent.LOCAL_ARTIFACT_CREATE);
		stateChangeListener.stateChanged(stateChangeEvent);
		addAffectedProject(resourceFile);
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactLocalModificationEvent(IFile resourceFile) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(resourceFile, SummaryStateChangeEvent.LOCAL_ARTIFACT_MODIFY);
		stateChangeListener.stateChanged(stateChangeEvent);
		addAffectedProject(resourceFile);
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactLocalRemovalEvent(IFile resourceFile) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(resourceFile, SummaryStateChangeEvent.LOCAL_ARTIFACT_REMOVE);
		stateChangeListener.stateChanged(stateChangeEvent);
		addAffectedProject(resourceFile);
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactRemoteCreationEvent(String project, Artifact artifact) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(artifact, project, SummaryStateChangeEvent.REMOTE_ARTIFACT_CREATE);
		stateChangeListener.stateChanged(stateChangeEvent);
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactRemoteModificationEvent(String project, Artifact artifact) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(artifact, project, SummaryStateChangeEvent.REMOTE_ARTIFACT_MODIFY);
		stateChangeListener.stateChanged(stateChangeEvent);
	}
	
	/**
	 * @param artifact
	 * @param project
	 */
	private void fireArtifactRemoteRemovalEvent(String project, Artifact artifact) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(artifact, project, SummaryStateChangeEvent.REMOTE_ARTIFACT_REMOVE);
		stateChangeListener.stateChanged(stateChangeEvent);
	}
	
	/**
	 * 
	 * @param resourceFile
	 */
	private void fireArtifactCommitSuccessEvent(String project, Artifact artifact) {
		SummaryStateChangeEvent stateChangeEvent = 
			new SummaryStateChangeEvent(artifact, project, SummaryStateChangeEvent.ARTIFACT_COMMIT);
		stateChangeListener.stateChanged(stateChangeEvent);
	}
}
