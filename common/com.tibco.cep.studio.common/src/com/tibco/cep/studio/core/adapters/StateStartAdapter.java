/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.model.Ontology;

/**
 * @author aathalye
 *
 */
public class StateStartAdapter extends StateAdapter<StateStart> implements com.tibco.cep.designtime.model.element.stategraph.StateStart {

	/**
	 * @param adapted
	 * @param emfOntology
	 */
	public StateStartAdapter(StateStart adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}
	
}
