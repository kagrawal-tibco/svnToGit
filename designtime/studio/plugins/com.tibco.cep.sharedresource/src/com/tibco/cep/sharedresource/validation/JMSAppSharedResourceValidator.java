package com.tibco.cep.sharedresource.validation;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class JMSAppSharedResourceValidator extends SharedResourceValidator{

	@Override
	public boolean canContinue() {
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
//		int modificationType = validationContext.getModificationType();
//		int buildType = validationContext.getBuildType();
		super.validate(validationContext);

		//NA for JMS Application Properties
//		JmsAppModelMgr modelmgr = new JmsAppModelMgr(resource.getProject(),resource.getLocation().toString(), null);
//		modelmgr.parseModel();
		
		
		return true;
		
	}	
}