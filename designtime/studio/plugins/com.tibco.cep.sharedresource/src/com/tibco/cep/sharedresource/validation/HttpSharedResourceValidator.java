package com.tibco.cep.sharedresource.validation;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.httpconfig.HttpConfigModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigHttpModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class HttpSharedResourceValidator extends SharedResourceValidator {

	@Override
	public boolean canContinue() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		
		HttpConfigModelMgr modelmgr = new HttpConfigModelMgr(resource);
		modelmgr.parseModel();

		String host = modelmgr.getStringValue("Host");
		if (host != null && host.trim().equals("")) {
			reportProblem(resource, getMessageString("http.empty_host", host), IMarker.SEVERITY_ERROR);
		}
		// Added this for BE-15955	Error validating HTTP shared resources
		else {
			String variable = GvUtil.getGvVariable(host);
				if(host.startsWith("%%") && host.endsWith("%%")) {
				GlobalVariableDescriptor desc = glbVarsDesc.get(variable);
				if (desc==null || !desc.isDeploymentSettable()) {		
					reportProblem(resource, getMessageString("http.invalid_host", host), IMarker.SEVERITY_ERROR);
				}
			}
		
		}/* else if (!GvUtil.isGlobalVar(host) && 
				!EntityNameHelper.isValidSharedResourceIdentifier(host)) {
			reportProblem(resource, getMessageString("http.invalid_host", host), IMarker.SEVERITY_ERROR);
		}*/
		 
		String val = modelmgr.getStringValue("Port");
		
		//validateNumericField(resource, glbVars, val, "http.empty_port", "http.invalid_port", IMarker.SEVERITY_ERROR, true, true);
		// CR BE-12096: if the GV is deployment settable, the default value should not be checked.
		boolean valid = validateStringField(resource, glbVars, val, "http.empty_port", "http.invalid_port", IMarker.SEVERITY_ERROR);
		if (valid){
			String variable = GvUtil.getGvVariable(val);
			GlobalVariableDescriptor desc = glbVarsDesc.get(variable);
			if (desc==null || !desc.isDeploymentSettable()) {		
				valid = validateNumericField(val, glbVars, true, true);
				if (!valid)
					reportProblem(resource, getMessageString("http.invalid_port", val), IMarker.SEVERITY_ERROR);
			}
		}
		
		boolean useSsl = modelmgr.getBooleanValue("useSsl");
		if (useSsl) {
			SslConfigHttpModel sslConfigModel = modelmgr.getModel().getSslConfigHttpModel();
			if (sslConfigModel != null) {
				String clientAuth = GvUtil.getGvDefinedValue(resource.getProject(), sslConfigModel.clientAuth);
				if (Boolean.parseBoolean(clientAuth)) {
					String trustCertificatesFolder = sslConfigModel.getCert();
					if (trustCertificatesFolder.equals("")) {
						reportSSLProblem(resource, trustCertificatesFolder, "ssl.empty.trusted.certificates.folder", "ssl.invalid.trusted.certificates.folder", true);
					}else{
					reportTrustCertifactFolderProblem(trustCertificatesFolder, resource, glbVars, glbVarsDesc, "ssl.empty.trusted.certificates.folder", "ssl.invalid.trusted.certificates.folder");
					}
				}
				reportSSLProblem(resource, sslConfigModel.getIdentity(), "ssl.empty.identity", "ssl.invalid.identity", true);
			}
		}
		
		/*
		for (LinkedHashMap<String, String> map: modelmgr.getModel().getConnectionProps()) {
			String propertyName = map.get("PropertyName");
			String propVal = map.get("PropertyValue");
			
			if (propertyName != null && propVal != null){
				if(propertyName.equals("maxPostSize (bytes)")
						|| propertyName.equals("maxSavePostSize (bytes)")
						|| propertyName.equals("acceptCount")
						|| propertyName.equals("connectionTimeOut (ms)"))
				{
					
					validateNumericConfigField(resource, glbVars, propertyName, propVal, "http.property.invalid.config", IMarker.SEVERITY_ERROR, true, true);
				}
				if(propertyName.equals("compression")){
					if(propVal.equals("on") || 
							propVal.equals("off") ||
							propVal.equals("force")){
						//DO Nothing
					}else{
						validateNumericConfigField(resource, glbVars, propertyName, propVal, "http.config.invalid.config", IMarker.SEVERITY_ERROR, true, true);
					}
				}
			}
		}
		*/
		
		return true;
	}	
}
