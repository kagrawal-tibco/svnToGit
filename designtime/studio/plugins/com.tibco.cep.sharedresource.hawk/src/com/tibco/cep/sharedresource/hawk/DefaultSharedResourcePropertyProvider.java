package com.tibco.cep.sharedresource.hawk;

import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVDAEMON;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVNETWORK;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVSERVICE;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_SERVER_URL;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_TRANSPORT;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_EMS;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_RV;

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
		if (extension.equals("hawk")) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("hawk")) {
			HawkModelMgr modelMgr = new HawkModelMgr(resource);
			LinkedHashMap<String, String> props = modelMgr.getProperties();
			if (TRANSPORT_TYPE_RV.equals(modelMgr.getProperties().get(CHANNEL_PROPERTY_TRANSPORT))) {
				props.remove(CHANNEL_PROPERTY_SERVER_URL);
				props.remove(CHANNEL_PROPERTY_USERNAME);
				props.remove(CHANNEL_PROPERTY_PASSWORD);
			} else if (TRANSPORT_TYPE_EMS.equals(modelMgr.getProperties().get(CHANNEL_PROPERTY_TRANSPORT))) {
				props.remove(CHANNEL_PROPERTY_RVSERVICE);
				props.remove(CHANNEL_PROPERTY_RVNETWORK);
				props.remove(CHANNEL_PROPERTY_RVDAEMON);
			}
			return props;
		} 
		return new LinkedHashMap<String, String>();
	}
	
}