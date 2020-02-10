/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager;

import org.eclipse.core.resources.IResourceDelta;

import com.tibco.cep.studio.rms.artifacts.Artifact;

/**
 * @author aathalye
 *
 */
public interface IRemoteResourceDelta extends IResourceDelta {
	
	public static final int REMOTE_ADDED = 0x400000;
	
	public static final int REMOTE_MODIFIED = 0x800000;
	
	public static final int REMOTE_REMOVED = 0x1600000;
	
	public static final int COMMIT = 0x3200000;
	
	Artifact getArtifact();
	
	String getProject();
}
