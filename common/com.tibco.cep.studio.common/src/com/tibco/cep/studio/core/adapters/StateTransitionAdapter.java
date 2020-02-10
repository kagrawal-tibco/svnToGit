/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.awt.BasicStroke;
import java.awt.Color;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.rule.Rule;

/**
 * @author aathalye
 *
 */
public class StateTransitionAdapter<S extends StateTransition> extends StateEntityAdapter<S> 
				implements com.tibco.cep.designtime.model.element.stategraph.StateTransition {

	/**
	 * @param adapted
	 * @param emfOntology
	 */
	public StateTransitionAdapter(S adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getLabel()
	 */
	
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getLineColor()
	 */
	
	public Color getLineColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getLinePattern()
	 */
	
	public BasicStroke getLinePattern() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getPrepasteFromGuid()
	 */
	
	public String getPrepasteFromGuid() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getPrepasteToGuid()
	 */
	
	public String getPrepasteToGuid() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#forwardCorrelates()
	 */
	
	public boolean forwardCorrelates() {
		throw new UnsupportedOperationException("To Be Done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#getFromState()
	 */
	
	public State getFromState() {
		com.tibco.cep.designtime.core.model.states.State fromState = adapted.getFromState();
		
		if (fromState != null) {
			//TODO From factory
			return CoreAdapterFactory.INSTANCE.createAdapter(fromState, emfOntology);
//			return new StateAdapter<com.tibco.cep.designtime.core.model.states.State>(fromState, emfOntology);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#getGuardRule(boolean)
	 */
	
	public Rule getGuardRule(boolean create) {
		com.tibco.cep.designtime.core.model.rule.Rule guardRule = adapted.getGuardRule();
		
		if (guardRule != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(guardRule, emfOntology);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#getPriority()
	 */
	
	public int getPriority() {
		Rule guardRule = getGuardRule(true);
		if (guardRule != null) {
			return guardRule.getPriority();
		}
		return 5;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#getToState()
	 */
	
	public State getToState() {
		com.tibco.cep.designtime.core.model.states.State toState = adapted.getToState();
		
		if (toState != null) {
			//TODO From factory
			return CoreAdapterFactory.INSTANCE.createAdapter(toState, emfOntology);
//			return new StateAdapter<com.tibco.cep.designtime.core.model.states.State>(toState, emfOntology);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#isLambda()
	 */
	
	public boolean isLambda() {
		return adapted.isLambda();
	}

	@Override
	public StateEntity getParent() {
		return getOwnerStateMachine();
//		StateEntity parentEntity = adapted.getParent();
//		
//		//TODO This cast is Bad work. Can help though
//		StateComposite parentComposite = (StateComposite)parentEntity;
//		if (parentEntity != null) {
//			//TODO Should come from AdapterFactory
//			return AdapterFactory.createAdapter(parentComposite, emfOntology);
//		} 
//		return null;
		
	}
	
	
	
}
