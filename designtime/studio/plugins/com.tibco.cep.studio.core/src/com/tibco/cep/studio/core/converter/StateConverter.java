package com.tibco.cep.studio.core.converter;

import java.util.HashMap;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateSimple;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.element.stategraph.StateSubMachine;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class StateConverter extends StateVertexConverter {

	protected HashMap<String, StateEntity> fAllStateEntities;

	public StateConverter(HashMap<String, StateEntity> allStateEntities, StateMachine ownerStateMachine) {
		super(ownerStateMachine);
		this.fAllStateEntities = allStateEntities;
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		State newEntity = null;
		if (entity instanceof StateStart) {
			newEntity = StatesFactory.eINSTANCE.createStateStart();
		} else if (entity instanceof StateSimple) {
			newEntity = StatesFactory.eINSTANCE.createStateSimple();
		} else if (entity instanceof StateEnd) {
			newEntity = StatesFactory.eINSTANCE.createStateEnd();
		} else if (entity instanceof StateSubMachine) {
			newEntity = (State) new StateSubmachineConverter(fAllStateEntities, ownerStateMachine).convertEntity(entity, projectName);
		} else if (entity instanceof StateComposite) {
			newEntity = (State) new StateCompositeConverter(fAllStateEntities, ownerStateMachine).convertEntity(entity, projectName);
		}
		populateEntity(newEntity, entity, projectName);
		if (fAllStateEntities != null) {
			fAllStateEntities.put(newEntity.getGUID(), newEntity);
		}
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.State.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		State newState = (State) newEntity;
		if (!(entity instanceof com.tibco.cep.designtime.model.element.stategraph.State)) {
			return;
		}
		com.tibco.cep.designtime.model.element.stategraph.State state = 
			(com.tibco.cep.designtime.model.element.stategraph.State) entity;
		
		
		
		RuleConverter ruleConverter = new RuleConverter();
//		newState.setTimeoutStateGUID(state.getTimeoutState().getGUID()); // can be calculated
//		newState.setTimeoutTransitionGUID(state.getTimeoutState().getGUID()); // can be calculated
		newState.setTimeoutPolicy(state.getTimeoutPolicy());
		
		try {
			if (state.getEntryAction(true) != null) {
				Rule entryAction = (Rule) ruleConverter.convertEntity(state.getEntryAction(false), projectName);
				newState.setEntryAction(entryAction);
			}
			if (state.getExitAction(true) != null) {
				Rule exitAction = (Rule) ruleConverter.convertEntity(state.getExitAction(false), projectName);
				newState.setExitAction(exitAction);
			}
			if (state.getTimeoutAction(true) != null) {
				Rule timeoutAction = (Rule) ruleConverter.convertEntity(state.getTimeoutAction(false), projectName);
				newState.setTimeoutAction(timeoutAction);
			}
			if (state.getInternalTransition(false) != null) {
				Rule internalTransRule = (Rule) ruleConverter.convertEntity(state.getInternalTransition(false), projectName);
				newState.setInternalTransitionRule(internalTransRule);
			}
			if (state.getTimeoutExpression() != null) {
				com.tibco.cep.designtime.model.rule.RuleFunction timeOutExpr = state.getTimeoutExpression();
				RuleFunction timeoutExp = 
					(RuleFunction) new RuleFunctionConverter().convertEntity(timeOutExpr, projectName);
				//Generate defaults for start/end
				if (newState instanceof StateStart || newState instanceof StateEnd) {
					timeoutExp = ModelUtils.generateDefaultTimeoutExpression(newState);
				}
				//force type to Long since it was incorrectly stored as int in many designer projects
				timeoutExp.setReturnType(RDFTypes.LONG.getName());
				newState.setTimeoutExpression(timeoutExp);
			}
			if (state.getTimeoutState() != null ) {
				newState.setTimeoutStateGUID(state.getTimeoutState().getGUID());
			}
			
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
	}
	
}
