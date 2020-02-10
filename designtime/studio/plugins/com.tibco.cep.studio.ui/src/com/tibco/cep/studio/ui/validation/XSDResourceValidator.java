package com.tibco.cep.studio.ui.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;


public class XSDResourceValidator extends DefaultResourceValidator {

	@Override
	public boolean canContinue() {
		// can continue if any xsd resource got any validation problem
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		deleteMarkers(resource);
		if (!(resource instanceof IFile)) {
			return true;
		}
		IFile file = (IFile) resource;
		InputStream contents = null;
		try{
			EMFTnsCache cache = StudioCorePlugin.getCache(resource.getProject().getName());
			XSDProblemHandler problemHandler = new XSDProblemHandler();
			contents = file.getContents();
			cache.alreadyRegistered(file.getLocation().toString(), contents, problemHandler);
			List<String> errors = problemHandler.getErrors();
			for (String message : errors) {
				reportProblem(resource, message, IResourceValidator.SEVERITY_WARNING);
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				contents.close();
			} catch (IOException e) {
				// ignore
			}
		}
		return true;
	}	
}
