/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author aathalye
 *
 */
public class StateMachineAdapter extends StateCompositeAdapter<com.tibco.cep.designtime.core.model.states.StateMachine> implements StateMachine,StateComposite, ICacheableAdapter {
	
	public StateMachineAdapter(com.tibco.cep.designtime.core.model.states.StateMachine template, 
			                   Ontology emfOntology) {
		super(template, emfOntology);
	}

	@Override
	protected com.tibco.cep.designtime.core.model.states.StateMachine getAdapted() {
		return adapted;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#forwardCorrelates()
	 */
	
	public boolean forwardCorrelates() {
		return adapted.isFwdCorrelates();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#getAnnotationLinks()
	 */
	
	public List<?> getAnnotationLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#getMachineRoot()
	 */
	
	public StateComposite getMachineRoot() {
		return new MachineRootAdapter(adapted, emfOntology);
//		return this;
//		return AdapterFactory.createAdapter(adapted, emfOntology);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#getOwnerConcept()
	 */
	
	public Concept getOwnerConcept() {
		String ownerConceptPath = adapted.getOwnerConceptPath();
		if (ownerConceptPath != null) {
			com.tibco.cep.designtime.core.model.element.Concept ownerConcept = 
				CommonIndexUtils.getConcept(adapted.getOwnerProjectName(), ownerConceptPath);
			if (ownerConcept != null) {
				try {
					return CoreAdapterFactory.INSTANCE.createAdapter(ownerConcept, emfOntology);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#getTransitions()
	 */
	
	public List<StateTransition> getTransitions() {
		List<com.tibco.cep.designtime.core.model.states.StateTransition> adaptedTransitions = adapted.getStateTransitions();
		
		List<StateTransition> stateTransitions = 
				new ArrayList<StateTransition>(adaptedTransitions.size());
		
		for (com.tibco.cep.designtime.core.model.states.StateTransition adaptedTransition : adaptedTransitions) {
//			StateTransitionAdapter adapter = new StateTransitionAdapter(adaptedTransition, emfOntology);
			StateTransitionAdapter adapter = 
				CoreAdapterFactory.INSTANCE.createAdapter(adaptedTransition, emfOntology);
			stateTransitions.add(adapter);
		}
		return stateTransitions;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#isMain()
	 */
	
	public boolean isMain() {
		return adapted.isMain();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateMachine#getOwnerConceptPath()
	 */
	public String getOwnerConceptPath() {
		return adapted.getOwnerConceptPath();
	}	
	
	public String getName() {
		return adapted.getName();
	}

}
