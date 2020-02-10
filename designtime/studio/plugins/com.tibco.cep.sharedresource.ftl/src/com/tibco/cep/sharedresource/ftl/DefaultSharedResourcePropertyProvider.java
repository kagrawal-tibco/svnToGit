package com.tibco.cep.sharedresource.ftl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.sharedresource.ftl.FTLModelMgr;
import com.tibco.cep.sharedresource.model.ISharedResourcePropertyProvider;


public class DefaultSharedResourcePropertyProvider implements ISharedResourcePropertyProvider{

	public DefaultSharedResourcePropertyProvider(){
		
	}
	@Override
	public boolean supportsResource(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("ftl")) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("ftl")) {
			FTLModelMgr modelMgr = new FTLModelMgr(resource);
			LinkedHashMap<String, String> props = modelMgr.getProperties();
			return props;
		} 
		return new LinkedHashMap<String, String>();
	}
	
}