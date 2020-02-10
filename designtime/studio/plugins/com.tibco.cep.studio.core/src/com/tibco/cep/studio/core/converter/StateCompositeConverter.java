package com.tibco.cep.studio.core.converter;

import java.util.HashMap;
import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.element.stategraph.State;

public class StateCompositeConverter extends StateConverter {
	
	
	
	public StateCompositeConverter(HashMap<String, StateEntity> allStateEntities,
			                       com.tibco.cep.designtime.core.model.states.StateMachine ownerSM) {
		super(allStateEntities, ownerSM);
		
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateComposite newEntity = StatesFactory.eINSTANCE.createStateComposite();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateComposite.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateComposite newComposite = (StateComposite) newEntity;
		if (!(entity instanceof com.tibco.cep.designtime.model.element.stategraph.StateComposite)) {
			return;
		}
		com.tibco.cep.designtime.model.element.stategraph.StateComposite composite = (com.tibco.cep.designtime.model.element.stategraph.StateComposite) entity;
		
		newComposite.setTimeoutComposite(composite.getTimeout());
		newComposite.setConcurrentState(composite.isConcurrentState());
		
		/*
		newComposite.setOwnerStateMachine(ownerStateMachine);*/
		
		List regions = composite.getRegions();
		
		List entities = composite.getEntities(); // A list of State objects
		if (entities == null) {
			entities = composite.getRegions();
		} else if (composite.getRegions() != null) {
			entities.addAll(composite.getRegions());
		}
		if (entities != null) {
			StateConverter stateConverter = 
				new StateConverter(fAllStateEntities, ownerStateMachine);
			for (Object object : entities) {
				State state = (State) object;
				Entity convertEntity = stateConverter.convertEntity(state, projectName);
				StateEntity stateEntity = (StateEntity) convertEntity;
				newComposite.getStateEntities().add(stateEntity);
				if(convertEntity instanceof StateComposite) {
					StateComposite sc = (StateComposite) convertEntity;
					if(sc.isRegion()){
						newComposite.getRegions().add((StateComposite)convertEntity);
					}
				}
				// Parent can be determined from eContainer()
//				stateEntity.setParent(newComposite);
			}
		}
	}
}
