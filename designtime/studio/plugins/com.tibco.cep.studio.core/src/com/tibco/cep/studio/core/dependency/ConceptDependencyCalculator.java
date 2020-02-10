package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class ConceptDependencyCalculator extends EntityDependencyCalculator {

	@Override
	protected void processEntityElement(File projectDir, String projectName, EntityElement element,
			List<Object> dependencies) {
		if (!(element.getEntity() instanceof Concept)) {
			return;
		}
		Concept concept = (Concept) element.getEntity();
		Concept superConcept = concept.getSuperConcept();
		if (superConcept != null) {
			processAndAddDependency(dependencies, projectName, superConcept);
		}
		
		Concept parentConcept = concept.getParentConcept();
		if (parentConcept != null) {
			processAndAddDependency(dependencies, projectName, parentConcept);
		}
		
		EList<StateMachine> stateMachines = concept.getStateMachines();
		for (StateMachine stateMachine : stateMachines) {
			processAndAddDependency(dependencies, projectName, stateMachine);
		}
		
		EList<PropertyDefinition> properties = concept.getProperties();
		for (int i = 0; i < properties.size(); i++) {
			PropertyDefinition propDefinition = properties.get(i);
			if (propDefinition.getType() == PROPERTY_TYPES.CONCEPT 
					|| propDefinition.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				String path = propDefinition.getConceptTypePath();
				if (path != null) {
					Concept c = IndexUtils.getConcept(projectName, path);
					processAndAddDependency(dependencies, projectName, c);
				}
			}
			EList<DomainInstance> domains = propDefinition.getDomainInstances();
			if (domains.size() > 0) {
				for (int j = 0; j < domains.size(); j++) {
					DomainInstance domainInstance = domains.get(j);
					String path = domainInstance.getResourcePath();
					Path p = new Path(path+"."+IndexUtils.DOMAIN_EXTENSION);
					processAndAddDependency(projectDir, dependencies, projectName, p);
				}
			}
		}
		
	}

}
