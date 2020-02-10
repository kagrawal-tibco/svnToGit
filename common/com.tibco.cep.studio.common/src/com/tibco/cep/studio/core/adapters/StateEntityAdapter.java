/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.awt.Rectangle;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;

/**
 * @author aathalye
 *
 */
public class StateEntityAdapter<S extends StateEntity> extends EntityAdapter<S> implements com.tibco.cep.designtime.model.element.stategraph.StateEntity {
	
	public StateEntityAdapter(S adapted, 
                              Ontology emfOntology) {
		super(adapted, emfOntology);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.adapters.EntityAdapter#getAdapted()
	 */
	
	protected S getAdapted() {
		return adapted;
	}
	
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public com.tibco.cep.designtime.model.element.stategraph.StateMachine getOwnerStateMachine() {
		com.tibco.cep.designtime.core.model.states.StateMachine adaptedOwnerSM = adapted.getOwnerStateMachine();
		if (adaptedOwnerSM != null) {
			try {
				return CoreAdapterFactory.INSTANCE.createAdapter(adaptedOwnerSM, emfOntology);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	public com.tibco.cep.designtime.model.element.stategraph.StateEntity getParent() {
		StateEntity parentEntity = adapted.getParent();
//		if (parentEntity instanceof StateMachine) {
		if (parentEntity != null) {
			return new StateCompositeAdapter<StateComposite>((StateComposite) parentEntity, emfOntology);
		}
//		}
//		//TODO This cast is Bad work. Can help though
//		StateComposite parentComposite = (StateComposite)parentEntity;
//		if (parentEntity != null) {
//			//TODO Should come from AdapterFactory
//			return AdapterFactory.createAdapter(parentComposite, emfOntology);
//		} 
		return null;
		
	}
	
	
	
	public StateMachineRuleSet getRuleSet() {
		final StateMachine sm = adapted.getOwnerStateMachine();
        if (null == sm) {
            return null;
        } else {
        	com.tibco.cep.designtime.model.element.stategraph.StateMachine _sm = CoreAdapterFactory.INSTANCE.createAdapter(sm, emfOntology);
            return _sm.getRuleSet();
        }        
//		throw new UnsupportedOperationException("To Be Done");
	}
	
	public int getTimeoutUnits() {
		return adapted.getTimeoutUnits().getValue();
	}
}
