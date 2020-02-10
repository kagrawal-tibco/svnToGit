/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateVertex;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;



/**
 * @author aathalye
 *
 */
public class StateCompositeAdapter<S extends StateComposite>  extends StateAdapter<S> implements com.tibco.cep.designtime.model.element.stategraph.StateComposite {
	
	public StateCompositeAdapter(S adapted, 
                                 Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	
	
	public List<StateEntity> getEntities() {
		List<com.tibco.cep.designtime.core.model.states.StateEntity> adaptedEntities = 
			adapted.getStateEntities();
		List<StateEntity> stateEntities = new ArrayList<StateEntity>(adaptedEntities.size());
		
		for (com.tibco.cep.designtime.core.model.states.StateEntity adaptedEntity : adaptedEntities) {
			//TODO Should come from AdapterFactory
			StateAdapter sa = CoreAdapterFactory.INSTANCE.createAdapter(adaptedEntity, emfOntology);
			stateEntities.add(sa);
//			if (adaptedEntity instanceof com.tibco.cep.designtime.core.model.states.StateComposite) {
//				StateCompositeAdapter adapter = 
//					new StateCompositeAdapter((com.tibco.cep.designtime.core.model.states.StateComposite) adaptedEntity, emfOntology);
//				stateEntities.add(adapter);
//			} else if (adaptedEntity instanceof State) {
//				StateAdapter sa = AdapterFactory.createAdapter(adaptedEntity, emfOntology);
//				stateEntities.add(sa);
//				if (adaptedEntity instanceof StateStart) {
//					StateAdapter<StateStart> adapter = 
//						new StateStartAdapter((StateStart) adaptedEntity, emfOntology);
//					stateEntities.add(adapter);
//				} else if (adaptedEntity instanceof StateEnd) {
//					StateAdapter<StateEnd> adapter = 
//						new StateEndAdapter((StateEnd) adaptedEntity, emfOntology);
//					stateEntities.add(adapter);
//				} else {
//					StateAdapter<State> adapter = new StateAdapter<State>((State) adaptedEntity, emfOntology);
//					stateEntities.add(adapter);
//				}
//			} else {
//				StateEntityAdapter<com.tibco.cep.designtime.core.model.states.StateEntity> adapter = 
//					new StateEntityAdapter<com.tibco.cep.designtime.core.model.states.StateEntity>(adaptedEntity, emfOntology);
//				stateEntities.add(adapter);
//			}
		}
		return stateEntities;
	}

	
	public com.tibco.cep.designtime.model.element.stategraph.StateComposite getRegion(int arg0) throws ModelException {
		throw new UnsupportedOperationException("To Be Done");
	}

	
	public List<com.tibco.cep.designtime.model.element.stategraph.StateComposite> getRegions() {
		EList<com.tibco.cep.designtime.core.model.states.StateComposite> regions = getAdapted().getRegions();
		List<com.tibco.cep.designtime.model.element.stategraph.StateComposite> adaptedRegions = new ArrayList<com.tibco.cep.designtime.model.element.stategraph.StateComposite>();
		for (com.tibco.cep.designtime.core.model.states.StateComposite stateComposite : regions) {
			com.tibco.cep.designtime.model.element.stategraph.StateComposite comp = CoreAdapterFactory.INSTANCE.createAdapter(stateComposite, emfOntology);
			adaptedRegions.add(comp);
//			adaptedRegions.add(new StateCompositeAdapter(stateComposite, getOntology()));
		}
		return adaptedRegions;
	}

	
	public StateVertex getState(String arg0) {
		throw new UnsupportedOperationException("To Be Done");
	}

	
	public int getTimeout() {
		return adapted.getTimeout();
	}

	
	public boolean isConcurrentState() {
		return adapted.isConcurrentState();
	}

	
	public boolean isRegion() {
		return adapted.isRegion();
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.adapters.StateAdapter#getTimeoutCodeBlock()
	 */
	@Override
	public CodeBlock getTimeoutCodeBlock() {
		return CommonRulesParserManager.calculateOffset( RulesParser.THEN_BLOCK, ModelUtils.getRuleAsSource(adapted.getTimeoutAction()));
	}

	@Override
	public String getName() {
		if (adapted instanceof StateMachine) {
			return "RootState";
		}
		return super.getName();
	}
	
}
