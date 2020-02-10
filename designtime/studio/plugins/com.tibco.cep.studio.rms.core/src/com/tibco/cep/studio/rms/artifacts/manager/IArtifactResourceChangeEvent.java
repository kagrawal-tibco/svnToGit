/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager;

import org.eclipse.core.resources.IResourceChangeEvent;

import com.tibco.cep.studio.rms.artifacts.Artifact;

/**
 * @author aathalye
 *
 */
public interface IArtifactResourceChangeEvent extends IResourceChangeEvent {
	
	Artifact getCachedArtifact();
}
