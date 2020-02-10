/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.model.Ontology;

/**
 * @author pdhar
 *
 */
public class InternalStateTransitionAdapter extends
		StateTransitionAdapter<StateTransition> implements
		com.tibco.cep.designtime.model.element.stategraph.InternalStateTransition {

	/**
	 * @param adapted
	 * @param emfOntology
	 */
	public InternalStateTransitionAdapter(StateTransition adapted,
			Ontology emfOntology) {
		super(adapted, emfOntology);
	}

}
