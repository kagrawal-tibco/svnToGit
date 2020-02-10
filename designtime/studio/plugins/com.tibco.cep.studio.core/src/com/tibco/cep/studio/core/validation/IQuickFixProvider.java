package com.tibco.cep.studio.core.validation;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;

public interface IQuickFixProvider {
	
	void initializeProvider(boolean updateVersions);
	
	// Perhaps extend this interface for other fixes as well
//	void findAndApplyFixes();

	String findAndApplyMapperFixes(MapperInvocationContext context,
			ResourceSet resourceSet, boolean xpath);

}
