/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager.impl;

import java.util.EventObject;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.manager.IArtifactResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.IRemoteResourceDelta;

/**
 * @author aathalye
 *
 */
public class RemoteResourceChangeEvent extends EventObject implements IArtifactResourceChangeEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2238976873799165747L;
	
	private IRemoteResourceDelta delta;
	
	/**
	 * @param arg0
	 */
	public RemoteResourceChangeEvent(Object source, IRemoteResourceDelta delta) {
		super(source);
		this.delta = delta;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#findMarkerDeltas(java.lang.String, boolean)
	 */
	public IMarkerDelta[] findMarkerDeltas(String type, boolean includeSubtypes) {
		throw new UnsupportedOperationException("Not supported");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#getBuildKind()
	 */
	
	public int getBuildKind() {
		throw new UnsupportedOperationException("Not supported");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#getDelta()
	 */
	
	public IResourceDelta getDelta() {
		return delta;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#getResource()
	 */
	
	public IResource getResource() {
		throw new UnsupportedOperationException("Not supported");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#getSource()
	 */
	
	public Object getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeEvent#getType()
	 */
	
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.manager.IRemoteResourceChangeEvent#getCachedArtifact()
	 */
	
	public Artifact getCachedArtifact() {
		return delta.getArtifact();
	}
}
