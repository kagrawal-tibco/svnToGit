package com.tibco.cep.studio.core.converter;

import java.util.HashMap;
import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class ConceptConverter extends EntityConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		Concept newEntity = null;
		if (((com.tibco.cep.designtime.model.element.Concept)entity).isAScorecard()) {
			newEntity = ElementFactory.eINSTANCE.createScorecard();
		} else {
			newEntity = ElementFactory.eINSTANCE.createConcept();
		}
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.Concept.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		com.tibco.cep.designtime.model.element.Concept concept = (com.tibco.cep.designtime.model.element.Concept) entity;
		Concept newConcept = (Concept) newEntity;
		super.populateEntity(newEntity, entity, projectName);
		
		populateConceptProperties(newConcept, concept, projectName);
		
		newConcept.setScorecard(concept.isAScorecard());
		// added for contained concepts
		if (concept.isContained()){
			newConcept.setParentConceptPath(concept.getParentConcept().getFullPath());
		}
		newConcept.setPOJO(concept.isPOJO());
		newConcept.setTransient(concept.isTransient());
		newConcept.setImplClass(concept.getPOJOImplClassName());
		String superConceptPath = concept.getSuperConceptPath();
		newConcept.setSuperConceptPath(superConceptPath);
		newConcept.setAutoStartStateMachine(concept.isAutoStartStateMachine());
		// addded inside EntityConverter
		//newConcept.setOwnerProjectName(projectName);
		

		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			List<com.tibco.cep.designtime.model.element.stategraph.StateMachine> stateMachines = 
				concept.getStateMachines();
			if (stateMachines != null) {
				StateMachineConverter stateMachineConverter = new StateMachineConverter(new HashMap<String, StateEntity>());
				for (Object object : stateMachines) {
					if(object instanceof com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine){
						com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine oldStandAloneSM = 
							(com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine)object;
						newConcept.getStateMachinePaths().add(oldStandAloneSM.getFullPath());
					}else{
						com.tibco.cep.designtime.model.element.stategraph.StateMachine oldSM = 
							(com.tibco.cep.designtime.model.element.stategraph.StateMachine)object;
						StateMachine stateMachine = 
							(StateMachine) stateMachineConverter.convertEntity(oldSM, projectName);
						newConcept.getStateMachinePaths().add(stateMachine.getFullPath());
						if (fConvertedEntities != null) {
							fConvertedEntities.add(stateMachine);
						}
					}
				}
			}
		}
		
//		Collection ruleSetPaths = concept.getRuleSetPaths(); // TODO : conversion work
//		if (ruleSetPaths != null) {
//			RuleSetConverter ruleSetConverter = new RuleSetConverter();
//			for (Object object : ruleSetPaths) {
////			RuleSet ruleSet = (RuleSet) ruleSetConverter.convertEntity((com.tibco.cep.designtime.model.Entity) object);
////			newConcept.setRuleSet(ruleSet);
//			}
//		}
	}

	private void populateConceptProperties(Concept newEntity,
			com.tibco.cep.designtime.model.element.Concept entity,
			String projectName) {
		PropertyDefinitionConverter converter = new PropertyDefinitionConverter();
		List<PropertyDefinition> propertyDefinitions = entity.getPropertyDefinitions(true);
		for (Object object : propertyDefinitions) {
			PropertyDefinition propertyDef = (PropertyDefinition) object;
			
			com.tibco.cep.designtime.core.model.element.PropertyDefinition newPropertyDefinition = 
				(com.tibco.cep.designtime.core.model.element.PropertyDefinition) converter.convertEntity(propertyDef, projectName);
			newEntity.getProperties().add(newPropertyDefinition);
		}
	}
}
