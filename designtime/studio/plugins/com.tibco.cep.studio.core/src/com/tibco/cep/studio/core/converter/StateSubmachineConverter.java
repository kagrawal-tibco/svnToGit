package com.tibco.cep.studio.core.converter;

import java.util.HashMap;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StateSubmachineConverter extends StateCompositeConverter {

	public StateSubmachineConverter(HashMap<String, StateEntity> allStateEntities, StateMachine ownerStateMachine) {
		super(allStateEntities, ownerStateMachine);
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateSubmachine newEntity = StatesFactory.eINSTANCE.createStateSubmachine();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateMachine.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateSubmachine newSubmachine = (StateSubmachine) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateSubMachine machine = (com.tibco.cep.designtime.model.element.stategraph.StateSubMachine) entity;
		// remove the second segment which is concept
		String uri = machine.getSubmachineURI();
		if(uri != null){
			int index = uri.lastIndexOf('/');
			if (index != -1){
				String subStatemachineName = uri.substring(index +1);
				int sasmIndex = subStatemachineName.indexOf(".standalonestatemachine");
				if (sasmIndex != -1) {
					// these get converted to .statemachine files
					subStatemachineName = subStatemachineName.substring(0, sasmIndex) + ".statemachine";
				}
				String root = uri.substring(0,index);
				// remove concept part 
				index = root.lastIndexOf('/');
				String conceptName = "";
				if (index != -1){
					conceptName = root.substring(index + 1);
					root = root.substring(0, index);
				} else {
					conceptName = root;
					root = "";
				}
				String conceptExt = IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT);
				if (conceptName.endsWith("."+conceptExt)){
					index = conceptName.lastIndexOf('.');
					if (index != -1){
						conceptName = conceptName.substring(0,index);
					}
				}
				if (conceptName == null || conceptName.length() == 0) {
					uri = root + "/" + subStatemachineName;
				} else {
					uri = root + "/" + conceptName + "/" + subStatemachineName;
				}

				newSubmachine.setURI(uri);
			} else {
				String subStatemachineName = uri;
				int sasmIndex = subStatemachineName.indexOf(".standalonestatemachine");
				if (sasmIndex != -1) {
					// these get converted to .statemachine files
					subStatemachineName = subStatemachineName.substring(0, sasmIndex) + ".statemachine";
				}
				newSubmachine.setURI(uri);
			}
		}
		
		//newSubmachine.setURI(machine.getSubmachineURI());
		
		
		newSubmachine.setCallExplicitly(machine.callExplicitly());
		newSubmachine.setPreserveForwardCorrelation(machine.preserveForwardCorrelate());
		
	}
}
