package com.tibco.cep.addon;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

/**
 * <code>IExternalResouceInfoProvider</code> provides mechanism for additional 
 * add on features to register their EMF registries. The 
 * <code>IExternalResouceInfoProvider</code> provides the core framework the 
 * ability to handle objects which are outside it's scope.
 *  
 * As of now a constant set of <code>IExternalResouceInfoProvider</code> implementation
 * are searched for at run time. 
 *  
 * @author anpatil
 * @see ExternalResourceInfoProviderFinder
 */
public interface IExternalResourceInfoProvider {
	
	public Map<String,EPackage> getPackageRegistry();
	
//	public boolean canHandle(String fileExtension);
//	
//	public String getExtension(EObject eObject);
//	
//	public ELEMENT_TYPES getType(String fileExtension);
//	
//	public ELEMENT_TYPES getType(EObject eObject);

}
