package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.mqtt.MqttModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigMqttModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * @author ssinghal
 *
 */
public class MqttSharedResourceValidator extends SharedResourceValidator {
	
	
	public boolean canContinue() {
		return true;
	}
	
	@Override
	public boolean validate(ValidationContext validationContext) {
		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		
		boolean baseValidation = super.validate(validationContext);
		
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		
		/*if(!baseValidation){
			return false;
		}*/
		
		MqttModelMgr modelmgr = new MqttModelMgr(resource);
		modelmgr.parseModel();
		
		String brokerUrls = modelmgr.getStringValue("mqtt.broker.urls");
		validateStringField(resource, glbVars, brokerUrls, 
				"mqtt.con.empty.broker.url", "mqtt.invalid.broker.url", IMarker.SEVERITY_ERROR);
		
		String useSsl = modelmgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelmgr.getProject(), useSsl);
		
		if("true".equalsIgnoreCase(useSsl)) {
			SslConfigMqttModel sslConfigModel = modelmgr.getModel().getSslConfigMqttModel();
			if(sslConfigModel!=null){
				reportTrustCertifactFolderProblem(sslConfigModel.getCert(), resource, glbVars, glbVarsDesc, "ssl.empty.trusted.certificates.folder", "ssl.invalid.trusted.certificates.folder");
				
				String clientAuth = GvUtil.getGvDefinedValue(resource.getProject(), sslConfigModel.getClientAuth());
				if(Boolean.parseBoolean(clientAuth)) {
					reportSSLProblem(resource, sslConfigModel.getIdentity(), "ssl.empty.identity", "ssl.invalid.identity", true);
				}
				
			}
		}
		
		return true;
	}

}
