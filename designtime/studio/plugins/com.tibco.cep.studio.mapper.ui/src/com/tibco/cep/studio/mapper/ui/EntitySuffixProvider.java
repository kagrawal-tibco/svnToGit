package com.tibco.cep.studio.mapper.ui;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;
import com.tibco.xml.xmodel.IPickerSuffixProvider;

public class EntitySuffixProvider implements IPickerSuffixProvider {

	private static final String[] entitySuffixes = new String[] { "concept", "event", "xsd", "wsdl", "process" };

	@Override
	public IContainer getContainer() {
		if (SWTMapperUtils.currentContext != null) {
			return ResourcesPlugin.getWorkspace().getRoot(); // temp code to workaround BW6 limitation that looks only 1 level deep for schemas
//			return ResourcesPlugin.getWorkspace().getRoot().getProject(SWTMapperUtils.currentContext.getProjectName());
		}
		return null;
	}

	@Override
	public String[] getSuffixes() {
		return entitySuffixes;
	}

}
