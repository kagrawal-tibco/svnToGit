package com.tibco.cep.sharedresource.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.sharedresource.httpconfig.HttpConfigModelMgr;
import com.tibco.cep.sharedresource.identity.IdentityConfigModelMgr;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.jms.JmsConModelMgr;
import com.tibco.cep.sharedresource.rvtransport.RvTransportModelMgr;

public class DefaultSharedResourcePropertyProvider implements ISharedResourcePropertyProvider{

	public DefaultSharedResourcePropertyProvider(){
		
	}
	@Override
	public boolean supportsResource(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedhttp")) {
			return true;
		} else if (extension.equals("sharedjmscon")) {
			return true;
		} else if (extension.equals("rvtransport")) {
			return true;
		} else if (extension.equals("sharedjdbc")) {
			return true;
		} else if (extension.equals("id")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Map<String, String> getResourceProperties(IResource resource) {
		String extension = resource.getFileExtension();
		if (extension.equals("sharedhttp")) {
			return new HttpConfigModelMgr(resource).getProperties();
		} else if (extension.equals("sharedjmscon")) {
			return new JmsConModelMgr(resource).getProperties();
		} else if (extension.equals("rvtransport")) {
			return new RvTransportModelMgr(resource).getProperties();
		} else if (extension.equals("sharedjdbc")) {
			return new JdbcConfigModelMgr(resource).getProperties();
		} else if (extension.equals("id")) {
			return new IdentityConfigModelMgr(resource).getProperties();
		}
		return new LinkedHashMap<String, String>();
	}
	
}