package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.as3.AS3ModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigFtlModel;
import com.tibco.cep.studio.core.validation.ValidationContext;

public class AS3SharedResourceValidator extends SharedResourceValidator {

	public boolean canContinue() {
		return true;
	}

	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		
		AS3ModelMgr modelmgr = new AS3ModelMgr(resource);
		modelmgr.parseModel();
		
		String realmServer = modelmgr.getStringValue("RealmServer");
		if (realmServer == null || realmServer.isEmpty()) reportProblem(resource, "Empty Realm Server url", IMarker.SEVERITY_ERROR);
		
		String useSsl = modelmgr.getStringValue("useSsl");
		if ("true".equalsIgnoreCase(useSsl)) {
			SslConfigFtlModel sslConfigModel = modelmgr.getModel().getSslConfigFtlModel();
			if (sslConfigModel != null) {
				if (sslConfigModel.getTrust_type().equalsIgnoreCase(sslConfigModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE))
					reportSSLProblem(resource,sslConfigModel.getTrust_file(), "ssl.empty.identity", "ssl.invalid.identity", true);
				if (sslConfigModel.getTrust_type().equalsIgnoreCase(sslConfigModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING) &&  sslConfigModel.getTrust_string().equalsIgnoreCase(""))
					reportProblem(resource, getMessageString("ftl.ssl.empty.trust.string", sslConfigModel.getTrust_string()), IMarker.SEVERITY_ERROR);
			}
		}
		
		return true;
	}
	
}