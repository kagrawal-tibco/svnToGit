package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.jndi.JndiConfigModelMgr;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class JNDIConfigSharedResourceValidator extends SharedResourceValidator{

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
		JndiConfigModelMgr modelmgr = new JndiConfigModelMgr(resource);
		modelmgr.parseModel();
		
		String jndiContextFactory = modelmgr.getStringValue("ContextFactory");
		validateStringField(resource, glbVars, jndiContextFactory, "jndi.context.empty.factory", "jndi.context.invalid.factory", IMarker.SEVERITY_ERROR);
		
		String providerUrl = modelmgr.getStringValue("ProviderUrl");
		validateStringField(resource, glbVars, providerUrl, "jndi.context.empty.url", "jndi.context.invalid.url", IMarker.SEVERITY_ERROR);
		return true;
	}	
}
