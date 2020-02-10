package com.tibco.cep.studio.ui.persistence;


import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * 
 * @author sasahoo
 *
 */
public interface IManageResource {
	
	public void loadResource(ResourceSet resourceSet, IFile file) throws Exception;	
	public void saveResource(ResourceSet resourceSet) throws Exception;

}
