/**
 * 
 */
package com.tibco.cep.sharedresource.model;

import java.util.Map;

import org.eclipse.core.resources.IResource;

/**
 * @author pdhar
 *
 */
public interface ISharedResourcePropertyProvider {
	
	/**
	 * checks whether the resource extension is handled by this provider
	 * @param resource
	 * @return
	 */
	boolean supportsResource(IResource resource);
	
	/**
	 * Returns the resource properties Map
	 * @param resource
	 * @return
	 */
	Map<String,String> getResourceProperties(IResource resource);

}
