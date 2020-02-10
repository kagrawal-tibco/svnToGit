package com.tibco.cep.studio.core.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioJavaCustomFunctionSourceValidator extends StudioJavaSourceValidator {

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
		
		if (!StudioJavaUtil.isAnnotatedJavaTaskSource((IFile)resource, CommonIndexUtils.ANNOTATION_JAVA_CUSTOM_FUNCTION_CLASS)) {
			return true;
		}

		return true;
	}
	
	@Override
	protected void deleteMarkers(IResource file) {
		super.deleteMarkers(file);
	}
	
}