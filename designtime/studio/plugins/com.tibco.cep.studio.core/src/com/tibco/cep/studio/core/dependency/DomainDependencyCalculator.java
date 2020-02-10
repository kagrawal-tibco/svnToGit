package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.index.model.EntityElement;

public class DomainDependencyCalculator extends EntityDependencyCalculator {

	
	@Override
	protected void processEntityElement(File projectDir, String projectName,
			EntityElement element, List<Object> dependencies) {
		Domain domain = (Domain) element.getEntity();
		Domain superDomain = domain.getSuperDomain();
		if (superDomain != null) {
			processAndAddDependency(dependencies, projectName, superDomain);
//			IFile file = IndexUtils.getFile(projectName, superDomain);
//			addDependency(dependencies, file);
		}
	}

}
