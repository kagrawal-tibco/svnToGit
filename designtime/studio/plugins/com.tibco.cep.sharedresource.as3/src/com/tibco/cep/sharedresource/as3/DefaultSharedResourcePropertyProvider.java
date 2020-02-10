package com.tibco.cep.sharedresource.as3;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.sharedresource.as3.AS3ModelMgr;
import com.tibco.cep.sharedresource.model.ISharedResourcePropertyProvider;


public class DefaultSharedResourcePropertyProvider implements ISharedResourcePropertyProvider{

	public DefaultSharedResourcePropertyProvider(){
		
	}
	@Override
	public boolean supportsResource(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("as3")) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("as3")) {
			AS3ModelMgr modelMgr = new AS3ModelMgr(resource);
			LinkedHashMap<String, String> props = modelMgr.getProperties();
			return props;
		} 
		return new LinkedHashMap<String, String>();
	}
	
}