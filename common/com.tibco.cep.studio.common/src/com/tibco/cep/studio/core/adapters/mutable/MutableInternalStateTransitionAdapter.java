package com.tibco.cep.studio.core.adapters.mutable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import com.tibco.be.util.shared.RuleConstants;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.InternalStateTransition;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.rule.Rule;

public class MutableInternalStateTransitionAdapter extends
		MutableStateEntityAdapter<StateTransition> implements
		InternalStateTransition {

	// // All actions appropriate for canStart are listed here
	// protected static final Integer CREATE_TRANSITION_ACTION = new Integer(1);
	// protected Rectangle m_bounds = new Rectangle(0, 0, 0, 0);
	// protected StateMachine m_ownerStateMachine = null;
	// protected StateComposite m_parent = null;
	// protected int m_timeout = 0;
	// protected int m_timeoutUnits =
	// com.tibco.cep.designtime.model.event.Event.SECONDS_UNITS;
	// 
	// protected Color m_lineColor = null;
	// protected BasicStroke m_linePattern = null;
	// private String m_prepasteToGUID = null;
	// private String m_prepasteFromGUID = null;
	// private String m_label = null;
	//    
	// public static final String PERSISTENCE_NAME = "StateTransition";
	//
	protected State m_toState = null;
	protected State m_fromState = null;
	protected Rule m_guardRule = null;
	protected boolean m_isLambda = false;
	protected boolean m_fwdCorrelates = true;
	protected State m_ownerState;
	protected Rule m_rule;

	public MutableInternalStateTransitionAdapter(Ontology ontology,
			String name, State ownerState, Rule guardRule) {
		this((Ontology) ontology, null, null);
		adapted.setName(name);
		m_ownerState = ownerState;
		m_rule = guardRule;
		// m_name = name;
		m_isLambda = false;
	}// end InternalStateTransition

	public MutableInternalStateTransitionAdapter(StateTransition adapted,
			Ontology o) {
		super(adapted, o);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param ownerStateMachine
	 *            The state machine that owns this state.
	 */
	public MutableInternalStateTransitionAdapter(Ontology ontology,
			String name, StateMachine ownerStateMachine) {
		this(ontology, name, new Rectangle(0, 0, 0, 0), ownerStateMachine);
	}// end DefaultStateTransition
	//    
	//    
	//    
	// /**
	// * Construct this object within the passed ontology and give it the name
	// passed.
	// *
	// * @param ontology The ontology (BE model) to add this entity.
	// * @param name The name of this element.
	// * @param ownerStateMachine The state machine that owns this state.
	// * @param toState The State where the transition starts.
	// * @param fromState The State where the transition ends.
	// */
	// public CGMutableInternalStateTransitionAdapter(
	// Ontology ontology,
	// String name,
	// StateMachine ownerStateMachine,
	// State toState,
	// State fromState) {
	// // Bounds are meaningless for transitions (they just follow the states)
	// so just give a dummy bounds
	// this(ontology, name, ownerStateMachine);
	// m_toState = toState;
	// m_fromState = fromState;
	// }// end DefaultStateTransition
	//    

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param bounds
	 *            The location of the entity on the main view (only top-left is
	 *            used for iconic entities).
	 */
	public MutableInternalStateTransitionAdapter(Ontology ontology,
			String name, Rectangle bounds, StateMachine ownerStateMachine) {
		this(ontology, name, null, bounds, ownerStateMachine);
	}// end DefaultStateEntity

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param folder
	 *            MutableFolder
	 * @param bounds
	 *            The location of the entity on the main view (only top-left is
	 *            used for iconic entities).
	 * @param ownerStateMachine
	 *            MutableStateMachine
	 */
	protected MutableInternalStateTransitionAdapter(Ontology ontology,
			String name, String folder, Rectangle bounds,
			StateMachine ownerStateMachine) {
		this(StatesFactory.eINSTANCE.createStateTransition(), ontology);
		adapted.setFolder(folder);
		adapted.setName(name);
		setOwnerStateMachine(ownerStateMachine);
	}// end DefaultStateEntity

	public State getFromState() {
		return m_ownerState;
	}// end getFromState

	public Rule getGuardRule(boolean createIfNeeded) {
		return m_rule;
	}// end getGuardRule

	public State getToState() {
		return m_ownerState;
	}// end getToState

	public boolean isLambda() {
		return m_isLambda;
	}// end isLambda

	public void setFromState(State fromState) {
		// m_fromState = fromState;
	}// end setFromState

	public void setIsLambda(boolean isLambda) {
		m_isLambda = isLambda;
	}// end setIsLambda

	public void setToState(State toState) {
		// m_toState = toState;
	}// end setToState

	@Override
	public int getPriority() {
		Rule guardRule = getGuardRule(false);
		if (guardRule == null) {
			return RuleConstants.DEFAULT_PRIORITY;
		}
		return guardRule.getPriority();
	}

	public void setOntology(Ontology ontology) {

	}

	/**
	 * Used during cut/paste.
	 * 
	 * @return return guid of this object at time of cut.
	 */
	public String getPrepasteFromGuid() {
		return null;
	}// end getFromGuid

	/**
	 * Get the label (what's displayed in the main view) for this link.
	 * 
	 * @return The label (what's displayed in the main view) for this link.
	 */
	public String getLabel() {
		return null;
	}// end getLabel

	/**
	 * Used during cut/paste.
	 * 
	 * @return return guid of this object at time of cut.
	 */
	public String getPrepasteToGuid() {
		return null;
	}// end getToGuid

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getLineColor()
	 */
	@Override
	public Color getLineColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateLink#getLinePattern()
	 */
	@Override
	public BasicStroke getLinePattern() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateTransition#forwardCorrelates()
	 */
	@Override
	public boolean forwardCorrelates() {
		// TODO Auto-generated method stub
		return m_fwdCorrelates;
	}


}
