package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class ConceptSearchParticipant extends EntitySearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<Entity> allEntities = IndexUtils.getAllEntities(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD });
		for (Entity entity : allEntities) {
			Concept concept = (Concept) entity;
			if (resolvedElement instanceof EntityElement && ((EntityElement) resolvedElement).getEntity() instanceof Concept) {
				Concept resolvedConcept = (Concept) ((EntityElement) resolvedElement).getEntity();
				if (concept.getSuperConceptPath() != null && concept.getSuperConceptPath().equalsIgnoreCase(resolvedConcept.getFullPath())) {
					result.addExactMatch(createElementMatch(ElementPackage.CONCEPT__SUPER_CONCEPT_PATH, concept.eClass(), concept));
				}
				if (concept.getParentConceptPath() != null && concept.getParentConceptPath().equalsIgnoreCase(resolvedConcept.getFullPath())) {
					result.addExactMatch(createElementMatch(ElementPackage.CONCEPT__PARENT_CONCEPT_PATH, concept.eClass(), concept));
				}
			}
			if (isEqual(concept, resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, concept.eClass(), resolvedElement));
			}
			EList<PropertyDefinition> allProperties = concept.getProperties();
			for (PropertyDefinition propertyDefinition : allProperties) {
				if (isEqual(propertyDefinition, resolvedElement)) {
					result.addExactMatch(createElementMatch(DEFINITION_FEATURE, concept.eClass(), propertyDefinition));
				}
				Object obj = resolvedElement;
				if (obj instanceof EntityElement) {
					obj = ((EntityElement) resolvedElement).getEntity();
				}
				if (obj instanceof Concept) {
					String conceptPath = propertyDefinition.getConceptTypePath();
					if (conceptPath != null && conceptPath.equals(((Concept) obj).getFullPath())) {
//						result.addExactMatch(propertyDefinition);
						result.addExactMatch(createElementMatch(ElementPackage.CONCEPT__PROPERTIES, concept.eClass(), propertyDefinition));
					}
				}
				if (resolvedElement instanceof EntityElement && ((EntityElement)resolvedElement).getElementType() == ELEMENT_TYPES.DOMAIN) {
					EList<DomainInstance> domainInstances = propertyDefinition.getDomainInstances();
					for (int i = 0; i < domainInstances.size(); i++) {
						DomainInstance domainInstance = domainInstances.get(i);
						if (domainInstance.getResourcePath().equals(((EntityElement) resolvedElement).getEntity().getFullPath())) {
							result.addExactMatch(createElementMatch(ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES, propertyDefinition.eClass(), propertyDefinition));
						}
					}
				}

			}
			if (resolvedElement instanceof StateMachineElement) {
				StateMachine machine = (StateMachine) ((StateMachineElement)resolvedElement).getEntity();
				EList<String> stateMachinePaths = concept.getStateMachinePaths();
				for (String path : stateMachinePaths) {
					if (path.equals(machine.getFullPath())) {
						result.addExactMatch(createElementMatch(ElementPackage.CONCEPT__STATE_MACHINE_PATHS, concept.eClass(), concept));
					}
				}
			}
		}
	}

}
