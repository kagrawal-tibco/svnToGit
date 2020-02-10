package com.tibco.cep.bpmn.ui.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.validation.StudioJavaSourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class JavaTaskResourceValidator extends StudioJavaSourceValidator {

	@Override
	public boolean canContinue() {
		// can continue if any java resource got any validation problem
		return true;
	}
	
	@Override
	public boolean validate(ValidationContext validationContext) {		
		
		final IResource resource = validationContext.getResource();	
		
		if (resource == null) return true;
		
		if (!StudioJavaUtil.isJavaSource((IFile)resource)) {
			return true;
		}
		
		String uri = ((IFile)resource).getFullPath().toString();
		if (!StudioJavaUtil.isInsideJavaSourceFolder(uri, resource.getProject().getName())) {
			return true;
		}
		
		if (!StudioJavaUtil.isAnnotatedJavaTaskSource((IFile)resource, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK)) {
			return true;
		}

		return true;
	}
	
	@Override
	protected void deleteMarkers(IResource file) {
		super.deleteMarkers(file);
	}
	
}
