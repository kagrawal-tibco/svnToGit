package com.tibco.cep.sharedresource.mqtt;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.sharedresource.model.ISharedResourcePropertyProvider;

/**
 * @author ssinghal
 *
 */

public class DefaultSharedResourcePropertyProvider implements ISharedResourcePropertyProvider{

	public DefaultSharedResourcePropertyProvider(){
		
	}
	@Override
	public boolean supportsResource(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedmqttcon")) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedmqttcon")) {
			MqttModelMgr modelMgr = new MqttModelMgr(resource);
			LinkedHashMap<String, String> props = modelMgr.getProperties();
			return props;
		} 
		return new LinkedHashMap<String, String>();
	}
	
}