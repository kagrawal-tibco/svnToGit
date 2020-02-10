/**
 * 
 */
package com.tibco.cep.rms.artifacts.state;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.rms.artifacts.Artifact;

/**
 * @author aathalye
 *
 */
public class SummaryStateChangeEvent {
	
	/**
	 * The project in which the artifact exists
	 */
	private String project;
	
	public static final int LOCAL_ARTIFACT_CREATE = 0x1;
	
	public static final int LOCAL_ARTIFACT_MODIFY = 0x2;
	
	public static final int LOCAL_ARTIFACT_REMOVE = 0x4;
	
	public static final int REMOTE_ARTIFACT_CREATE = 0x8;
	
	public static final int REMOTE_ARTIFACT_MODIFY = 0x16;
	
	public static final int REMOTE_ARTIFACT_REMOVE = 0x32;
	
	public static final int ARTIFACT_COMMIT = 0x364;
	
	private int eventType;
	
	/**
	 * Either {@link IFile} or {@link Artifact} depending
	 * upon local or remote change
	 */
	private IFile file;
	
	private Artifact artifact;

	/**
	 * @param project
	 */
	public SummaryStateChangeEvent(IFile file, 
			                       int eventType) {
		this.file = file;
		this.project = file.getProject().getName();
		this.eventType = eventType;
	}
	
	

	/**
	 * @param eventType
	 * @param artifact
	 */
	public SummaryStateChangeEvent(Artifact artifact, 
			                       String project,
			                       int eventType) {
		this.eventType = eventType;
		this.project = project;
		this.artifact = artifact;
	}



	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @return the eventType
	 */
	public final int getEventType() {
		return eventType;
	}



	/**
	 * @return the file
	 */
	public final IFile getResourceFile() {
		return file;
	}


	/**
	 * @return the artifact
	 */
	public final Artifact getArtifact() {
		return artifact;
	}
	
	public final String getResourcePath() {
		String path = 
			(file != null) ? "/" + file.getFullPath().removeFirstSegments(1).removeFileExtension().toString() : artifact.getArtifactPath();
		return path;	
	}
	
	public final String getExtension() {
		String extension = 
			(file != null) ? file.getFileExtension() : artifact.getArtifactExtension();
		return extension;
	}
	
	public String getContainerPath() {
		String containerPath = null;
		if (file != null) {
			IResource container = file.getParent();
			if (container instanceof IProject) {
				containerPath = "/";
			} else {
				containerPath = "/" + container.
									     getFullPath().
									     	removeFirstSegments(1).
									     		removeFileExtension().toString();
			}
		} else {
			containerPath = artifact.getContainerPath();
		}
		return containerPath;
	}
}
