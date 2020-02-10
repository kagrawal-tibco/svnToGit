package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.model.Ontology;

public class MachineRootAdapter extends StateCompositeAdapter<StateComposite> {

	public MachineRootAdapter(StateComposite adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	public String getName() {
		return "RootState";
	}

}
