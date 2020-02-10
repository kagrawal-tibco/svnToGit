/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager;

import org.eclipse.core.resources.IResourceChangeEvent;

/**
 * @author aathalye
 *
 */
public interface IArtifactResourceChangeListener {
	
	public void resourceChanged(IResourceChangeEvent event);
}
