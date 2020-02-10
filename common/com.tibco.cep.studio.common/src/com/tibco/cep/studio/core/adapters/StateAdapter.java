/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * @author aathalye
 *
 */
public class StateAdapter<S extends State> extends StateEntityAdapter<S> implements 
	com.tibco.cep.designtime.model.element.stategraph.State {
	
	public StateAdapter(S adapted, 
                        Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.adapters.EntityAdapter#getAdapted()
	 */
	
	protected S getAdapted() {
		return adapted;
	}

	
	public Rule getEntryAction(boolean create) throws ModelException {
		com.tibco.cep.designtime.core.model.rule.Rule entryAction = adapted.getEntryAction();
		if (entryAction != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(entryAction, emfOntology);
		}
		return null;
	}

	
	public Rule getExitAction(boolean create) throws ModelException {
		com.tibco.cep.designtime.core.model.rule.Rule exitAction = adapted.getExitAction();
		if (exitAction != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(exitAction, emfOntology);
		}
		return null;
	}

	
	public Rule getInternalTransition(boolean create) throws ModelException {
		com.tibco.cep.designtime.core.model.rule.Rule internalTransAction = adapted.getInternalTransitionRule();
		if (internalTransAction != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(internalTransAction, emfOntology);
		}
		return null;
	}

	
	public Rule getTimeoutAction(boolean create) {
		com.tibco.cep.designtime.core.model.rule.Rule timeOutAction = adapted.getTimeoutAction();
		if (timeOutAction != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(timeOutAction, emfOntology);
		}
		return null;
	}

	
	public RuleFunction getTimeoutExpression() {
		com.tibco.cep.designtime.core.model.rule.RuleFunction adaptedRF = adapted.getTimeoutExpression();
		if (adaptedRF != null) {
			try {
				return CoreAdapterFactory.INSTANCE.createAdapter(adaptedRF, emfOntology);
			} catch (Exception e) {
				return null;
			}
		}
		//Shouldnt get to this stage
		return null;
	}
	

	public int getTimeoutPolicy() {
		return adapted.getTimeoutPolicy();
	}

	
	public com.tibco.cep.designtime.model.element.stategraph.State getTimeoutState() {
		State timeoutState = adapted.getTimeoutState();
		if (timeoutState != null) {
			return CoreAdapterFactory.INSTANCE.createAdapter(timeoutState, emfOntology);
//			return new StateAdapter<State>(timeoutState, emfOntology); 
		}
		return null;
	}

	
	public boolean isInternalTransitionEnabled() {
		return adapted.isInternalTransitionEnabled();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getEntryCodeBlock()
	 */
	@Override
	public CodeBlock getEntryCodeBlock() {
		return CommonRulesParserManager.calculateOffset( RulesParser.THEN_BLOCK, ModelUtils.getRuleAsSource(adapted.getEntryAction()));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getExitCodeBlock()
	 */
	@Override
	public CodeBlock getExitCodeBlock() {
		return CommonRulesParserManager.calculateOffset( RulesParser.THEN_BLOCK, ModelUtils.getRuleAsSource(adapted.getExitAction()));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getTimeoutCodeBlock()
	 */
	@Override
	public CodeBlock getTimeoutCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.THEN_BLOCK, ModelUtils.getRuleAsSource(adapted.getTimeoutAction()));
	}
	
	

}
