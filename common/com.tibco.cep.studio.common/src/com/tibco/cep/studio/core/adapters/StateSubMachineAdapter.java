package com.tibco.cep.studio.core.adapters;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;


public class StateSubMachineAdapter extends StateCompositeAdapter<StateSubmachine> implements
	com.tibco.cep.designtime.model.element.stategraph.StateSubMachine {

	public StateSubMachineAdapter(StateSubmachine adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	public boolean callExplicitly() {
		return adapted.isCallExplicitly();
	}

	@Override
	public StateMachine getReferencedStateMachine() {
		String uri = getSubmachineURI();
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash == -1) {
            throw new RuntimeException("StateSubMachine: Incorrect URI format - " + uri);
        }

        final Ontology ontology = getOntology();
        if (null != ontology) {
            final int indexOfLastDot = uri.lastIndexOf('.');
            final String smUri = (indexOfLastDot < 0) ? uri : uri.substring(0, indexOfLastDot);
            final Entity sm = ontology.getEntity(smUri);
            if (sm instanceof StateMachine) {
                return (StateMachine) sm;
            }
        }

        String conceptPath = uri.substring(0, lastSlash);
        int lastDot = conceptPath.lastIndexOf('.'); //sometimes it could be abc.concept (So remove the .concept);
        String conceptName = conceptPath;
        if (lastDot != -1) {
            conceptName = conceptPath.substring(0, lastDot);
        }
        String smName = uri.substring(lastSlash + 1);

        Concept c = getOntology().getConcept(conceptName);
        if (c == null) {
            throw new RuntimeException("StateSubMachine: Invalid Concept Name - " + conceptName);
        }

        List l = c.getStateMachines();
        if (l == null) {
            throw new RuntimeException("StateSubMachineState: Invalid Machine Name - " + smName);
        }

        Iterator itr = l.iterator();
        while (itr.hasNext()) {
            StateMachine sm = (StateMachine) itr.next();
            if (smName.equals(sm.getName())) {
                return sm;
            }
        }
        throw new RuntimeException("StateSubMachine: Unknown Machine Reference - " + smName);
	}

	@Override
	public String getSubmachineURI() {
		return adapted.getURI();
	}

	@Override
	public boolean preserveForwardCorrelate() {
		return adapted.isPreserveForwardCorrelation();
	}
	
}
