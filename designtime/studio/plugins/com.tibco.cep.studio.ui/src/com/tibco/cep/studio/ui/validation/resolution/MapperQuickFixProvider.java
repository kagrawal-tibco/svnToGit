package com.tibco.cep.studio.ui.validation.resolution;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.core.validation.IQuickFixProvider;
import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;

public class MapperQuickFixProvider implements IQuickFixProvider {

	private boolean updateVersions;

	@Override
	public void initializeProvider(boolean updateVersions) {
        SWTMapperUtils.initCustomFunctions();
        this.updateVersions = updateVersions;
	}

	@Override
	public String findAndApplyMapperFixes(
			MapperInvocationContext context, ResourceSet resourceSet, boolean xpath) {
		
		XslMapperModelWrapper wrapper = new XslMapperModelWrapper(context, MapperXSDUtils.getSourceElements(context), MapperXSDUtils.getTargetEntity(context), xpath, updateVersions);
		String fixedStr = wrapper.fixErrors(false, false, false);
		return fixedStr;
	}

}
