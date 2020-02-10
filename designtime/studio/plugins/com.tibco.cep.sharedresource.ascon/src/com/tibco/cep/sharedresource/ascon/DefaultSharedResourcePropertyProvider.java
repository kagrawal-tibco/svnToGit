package com.tibco.cep.sharedresource.ascon;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.sharedresource.model.ISharedResourcePropertyProvider;


public class DefaultSharedResourcePropertyProvider implements ISharedResourcePropertyProvider{

	public DefaultSharedResourcePropertyProvider(){
		
	}
	@Override
	public boolean supportsResource(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedascon")) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedascon")) {
			return new ASConnectionModelMgr(resource).getProperties();
		} 
		return new LinkedHashMap<String, String>();
	}
	
}