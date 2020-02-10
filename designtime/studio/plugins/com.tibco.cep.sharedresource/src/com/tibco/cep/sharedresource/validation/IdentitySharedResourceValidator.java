package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.identity.IdentityConfigModelMgr;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class IdentitySharedResourceValidator extends SharedResourceValidator {

	private static String ID_USERNAME = "usernamePassword";
	private static String ID_KEY = "certPlusKeyURL";
	private static String ID_FILE = "url";
	
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

		IdentityConfigModelMgr modelmgr = new IdentityConfigModelMgr(resource);
		modelmgr.parseModel();
		String type = modelmgr.getStringValue("objectType");

		if (type.equalsIgnoreCase(ID_USERNAME)) {
			String userName =  modelmgr.getStringValue("username");
			String password =  modelmgr.getStringValue("password");
			validateStringField(resource, glbVars, userName, 
					"id.username.empty.config", "id.username.invalid.config", IMarker.SEVERITY_ERROR);
			validateGvField(resource, glbVars, password, "id.password.empty.config", "id.password.invalid.config", IMarker.SEVERITY_ERROR);		
		} else if(type.equalsIgnoreCase(ID_KEY)) {
			String certUrl =  modelmgr.getStringValue("certUrl");
			String privateKeyUrl =  modelmgr.getStringValue("privateKeyUrl");
			String passPhrase =  modelmgr.getStringValue("passPhrase");
			validateStringField(resource, glbVars, certUrl, 
					"id.certurl.empty.config", "id.certurl.invalid.config", IMarker.SEVERITY_ERROR);
			validateStringField(resource, glbVars, privateKeyUrl, 
					"id.privatekeyurl.empty.config", "id.privatekeyurl.invalid.config", IMarker.SEVERITY_ERROR);
			validateGvField(resource, glbVars, passPhrase, 
					"id.passphrase.empty.config", "id.passphrase.invalid.config", IMarker.SEVERITY_ERROR);
		} else if(type.equalsIgnoreCase(ID_FILE)) {
			String url =  modelmgr.getStringValue("url");
			String passPhrase =  modelmgr.getStringValue("passPhrase");
			validateGvField(resource, glbVars, url, 
					"id.url.empty.config", "id.url.invalid.config", IMarker.SEVERITY_ERROR);
			validateGvField(resource, glbVars, passPhrase, 
					"id.password.empty.config", "id.password.invalid.config", IMarker.SEVERITY_ERROR);
		}
		return true;
	}	
}
