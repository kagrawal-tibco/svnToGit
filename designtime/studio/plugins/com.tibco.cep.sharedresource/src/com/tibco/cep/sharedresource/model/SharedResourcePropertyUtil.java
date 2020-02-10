package com.tibco.cep.sharedresource.model;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.core.StudioCorePlugin;


/*
@author ssailapp
@date Mar 9, 2011
 */

public class SharedResourcePropertyUtil {
	
	private static final String SHARED_RESOURCE_PROPERTY_PROVIDER = "com.tibco.cep.sharedresource.sharedResourcePropertyProvider" ;
	private static final String SHARED_RESOURCE_PROPERTY_ATTRIBUTE = "sharedResourcePropertyProvider";
	
	public static Map<String, String> getProperties(IResource resource) {
		try{
			  IExtensionRegistry reg = Platform.getExtensionRegistry();
			  if (reg != null) {
	      		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(SHARED_RESOURCE_PROPERTY_PROVIDER);
	      		for (int i = 0; i < extensions.length; i++) {
	      			IConfigurationElement element = extensions[i];
	    			final Object o = element.createExecutableExtension(SHARED_RESOURCE_PROPERTY_ATTRIBUTE);
	    			if (o != null && o instanceof ISharedResourcePropertyProvider) {
	    				if(((ISharedResourcePropertyProvider)o).supportsResource(resource)){
	    					return ((ISharedResourcePropertyProvider)o).getResourceProperties(resource);
	    				}
	    			}
	      		}
			  }
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
		return null;
	}
	
}
