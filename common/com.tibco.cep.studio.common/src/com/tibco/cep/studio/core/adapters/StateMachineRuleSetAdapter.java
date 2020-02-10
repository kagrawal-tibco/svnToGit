package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.studio.core.index.model.ElementContainer;

public class StateMachineRuleSetAdapter extends FolderAdapter implements
		StateMachineRuleSet {

	private StateMachine fStateMachine;

	public StateMachineRuleSetAdapter(Ontology emfOntology,
			ElementContainer container, StateMachine machine) {
		super(emfOntology, container);
		this.fStateMachine = machine;
	}

	@Override
	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<Rule>();
		for(com.tibco.cep.designtime.core.model.rule.Rule r: _getRules().values()) {
			Rule rl = CoreAdapterFactory.INSTANCE.createAdapter(r, emfOntology);
			if(rl != null) {
				rules.add(rl);
			}
		}
		return rules;
	}

	@Override
	public RulesetEntry getRule(String ruleName) {
		com.tibco.cep.designtime.core.model.rule.Rule rule = _getRules().get(ruleName);
		if(rule != null) {
			Rule r =  CoreAdapterFactory.INSTANCE.createAdapter(rule, emfOntology);
			return r;
		}
		return null;
	}
	
	private Map<String,com.tibco.cep.designtime.core.model.rule.Rule> _getRules() {
		Map<String,com.tibco.cep.designtime.core.model.rule.Rule> stateRules = new LinkedHashMap<String,com.tibco.cep.designtime.core.model.rule.Rule>();
		if(fStateMachine.getTimeoutAction() != null) {
			stateRules.put(fStateMachine.getTimeoutAction().getName(),fStateMachine.getTimeoutAction());
		}
		for(StateEntity se:fStateMachine.getStateEntities()) {
			if(se instanceof State) {
				State st = (State) se;
				if(st.getEntryAction() != null) {
					stateRules.put(st.getEntryAction().getName(),st.getEntryAction());
				}
				if(st.getExitAction() != null) {
					stateRules.put(st.getExitAction().getName(),st.getExitAction());
				}
				if(st.getTimeoutAction() != null) {
					stateRules.put(st.getTimeoutAction().getName(),st.getTimeoutAction());
				}
			}
		}
		for(StateTransition st: fStateMachine.getStateTransitions())  {
			if(st.getGuardRule() != null) {
				stateRules.put(st.getGuardRule().getName(),st.getGuardRule());
			}
		}
		
		return stateRules;
	}
	
	public Folder getFolder() {
        return CoreAdapterFactory.INSTANCE.createAdapter(fStateMachine, emfOntology).getFolder();
    }


    public String getFullPath() {
        return fStateMachine.getFullPath();
    }


    public String getName() {
        return fStateMachine.getName();
    }
    
    public String getConceptPath() {
        return fStateMachine.getOwnerConceptPath();
    }

}
