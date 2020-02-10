package com.tibco.cep.studio.core.adapters;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;

public class StateRuleAdapter<R extends com.tibco.cep.designtime.core.model.rule.Rule> extends RuleAdapter<R> {

	public StateRuleAdapter(R adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	public RuleSet getRuleSet() {

		if (emfOntology instanceof CoreOntologyAdapter) {
			ElementContainer folder = getContainer();
			EObject container = null;
			if (this instanceof StateMachine && adapted.eContainer() == null) {
				container = (StateMachine) this;
			} else {
				container = adapted.eContainer();
				while (container.eContainer() != null && !(container instanceof SharedStateMachineElement)) {
					container = container.eContainer();
				}
			}
			if (container instanceof SharedStateMachineElement) {
				container = ((SharedStateMachineElement) container).getSharedEntity();
			}
//			EObject rootContainer = CommonIndexUtils.getRootContainer(adapted);
			StateMachine machine = null;
			if (container instanceof StateMachine) {
				machine = (StateMachine) container;
			}
			StateMachineRuleSetAdapter adapter = new StateMachineRuleSetAdapter(emfOntology, folder, machine);
			
			return adapter;
		}
		return null;
	}

}
