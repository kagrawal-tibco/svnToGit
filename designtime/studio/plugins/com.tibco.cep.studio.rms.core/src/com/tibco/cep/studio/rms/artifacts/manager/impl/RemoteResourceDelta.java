/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager.impl;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.manager.IRemoteResourceDelta;

/**
 * @author aathalye
 *
 */
public class RemoteResourceDelta implements IRemoteResourceDelta {
	
	private Artifact cachedArtifact;
	
	private int deltaKind;
	
	private String project;
	

	/**
	 * @param cachedArtifact
	 */
	public RemoteResourceDelta(Artifact cachedArtifact, 
			                   String project,
			                   int deltaKind) {
		this.cachedArtifact = cachedArtifact;
		this.project = project;
		this.deltaKind = deltaKind;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#accept(org.eclipse.core.resources.IResourceDeltaVisitor, boolean)
	 */
	
	public void accept(IResourceDeltaVisitor visitor, boolean includePhantoms)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#accept(org.eclipse.core.resources.IResourceDeltaVisitor, int)
	 */
	
	public void accept(IResourceDeltaVisitor visitor, int memberFlags)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#accept(org.eclipse.core.resources.IResourceDeltaVisitor)
	 */
	
	public void accept(IResourceDeltaVisitor visitor) throws CoreException {
		visitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#findMember(org.eclipse.core.runtime.IPath)
	 */
	
	public IResourceDelta findMember(IPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getAffectedChildren()
	 */
	
	public IResourceDelta[] getAffectedChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getAffectedChildren(int, int)
	 */
	
	public IResourceDelta[] getAffectedChildren(int kindMask, int memberFlags) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getAffectedChildren(int)
	 */
	
	public IResourceDelta[] getAffectedChildren(int kindMask) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getFlags()
	 */
	
	public int getFlags() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getFullPath()
	 */
	
	public IPath getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getKind()
	 */
	
	public int getKind() {
		return deltaKind;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getMarkerDeltas()
	 */
	
	public IMarkerDelta[] getMarkerDeltas() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getMovedFromPath()
	 */
	
	public IPath getMovedFromPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getMovedToPath()
	 */
	
	public IPath getMovedToPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getProjectRelativePath()
	 */
	
	public IPath getProjectRelativePath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceDelta#getResource()
	 */
	
	public IResource getResource() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.manager.IRemoteResourceDelta#getArtifact()
	 */
	public Artifact getArtifact() {
		return cachedArtifact;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}
	
	
}
