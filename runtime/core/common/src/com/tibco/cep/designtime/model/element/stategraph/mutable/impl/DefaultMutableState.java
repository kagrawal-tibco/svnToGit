/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 23, 2004
 * Time: 6:43:58 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableState;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableSymbol;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableSymbols;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * If this class becomes concrete (no longer abstract) it must be added to
 * createEntityFromNode as a new object to be constructed from persistence.
 */
public abstract class DefaultMutableState extends DefaultMutableStateVertex implements MutableState {


    public static final String PERSISTENCE_NAME = "State";
    protected static final String TIMEOUT_STATE_GUID_TAG = "timeoutStateGUID";
    protected static final String TIMEOUT_STATE_TAG = "timeoutState";
    protected static final String TIMEOUT_POLICY_TAG = "timeoutPolicy";

    public static final String ENTRY_ACTION_RULE_TAG = "entryAction";
    public static final String EXIT_ACTION_RULE_TAG = "exitAction";
    public static final String TIMEOUT_ACTION_RULE_TAG = "timeoutAction";
    public static final String TIMEOUT_EXPRESSION_RULE_TAG = "timeoutExpression";

    protected static final String IS_INTERNAL_TRANSITION_ENABLED_TAG = "isInternalTransitionEnabled";
    public static final String INTERNAL_TRANSITION_RULE_TAG = "internalTransition";

    protected static final ExpandedName ENTRY_ACTION_NAME_TAG = ExpandedName.makeName("entryActionName");
    protected static final ExpandedName EXIT_ACTION_NAME_TAG = ExpandedName.makeName("exitActionName");
    protected static final ExpandedName TIMEOUT_ACTION_NAME_TAG = ExpandedName.makeName("timeoutActionName");
    protected static final ExpandedName TIMEOUT_EXPRESSION_NAME_TAG = ExpandedName.makeName("timeoutExpressionName");

    protected static final ExpandedName INTERNAL_TRANSITION_RULE_NAME_TAG = ExpandedName.makeName("internalTransitionRuleName");
    protected static final ExpandedName IS_INTERNAL_TRANSITION_ENABLED_NAME = ExpandedName.makeName(IS_INTERNAL_TRANSITION_ENABLED_TAG);
    protected static final ExpandedName TIMEOUT_STATE_GUID_NAME = ExpandedName.makeName(TIMEOUT_STATE_GUID_TAG);
    protected static final ExpandedName TIMEOUT_STATE_NAME = ExpandedName.makeName(TIMEOUT_STATE_TAG);
    protected static final ExpandedName TIMEOUT_POLICY_NAME = ExpandedName.makeName(TIMEOUT_POLICY_TAG);

    protected boolean m_isInternalTransitionEnabled = false;
    protected MutableRule m_entryAction = null;
    protected MutableRule m_exitAction = null;
    protected MutableRule m_timeoutAction = null;
    protected MutableRule m_internalTransitionRule = null;
    protected MutableRuleFunction m_timeoutExpression = null;

    protected String m_timeoutStateGUID = "";

    // kept for reverse compatibility
    protected String m_timeoutTransitionGUID = null;

    protected int m_timeoutPolicy = NO_ACTION_TIMEOUT_POLICY;


    public static final String MULTIPLE_LAMBDAS_ERROR = "DefaultState.getModelErrors.multipleLambdas";
    public static final String TIMEOUT_STATE_MISSING = "DefaultState.getModelErrors.timeoutStateMissing";


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableState(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultState


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology               The ontology (BE model) to add this entity.
     * @param name                   The name of this element.
     * @param bounds                 The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine      The state machine that owns this state.
     * @param entryAction            The action to perform on entry to this state.
     * @param exitAction             The action to perform on exit from this state.
     * @param internalTransitionRule The internal transition (if any) for this state.
     */
    public DefaultMutableState(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            MutableRule entryAction,
            MutableRule exitAction,
            MutableRule internalTransitionRule) {
        super(ontology, name, bounds, ownerStateMachine);
        m_entryAction = entryAction;
        m_exitAction = exitAction;
        m_internalTransitionRule = internalTransitionRule;
    }// end DefaultState


    public int getTimeoutPolicy() {
        return m_timeoutPolicy;
    }// end getTimeoutPolicy


    public void setTimeoutPolicy(
            int flag) {
        if (flag < NO_ACTION_TIMEOUT_POLICY || flag > DETERMINISTIC_STATE_POLICY) {
            flag = NO_ACTION_TIMEOUT_POLICY;
        }//endif
        m_timeoutPolicy = flag;
    }// end setTimeoutPolicy


    /**
     * Get the MutableState to transition to on timeout from the current State.
     *
     * @return The MutableState to transition to on timeout from the current State.
     */
    public State getTimeoutState() {
        // TODO expand for other state machines
        List states = m_ownerStateMachine.getMachineRoot().getEntities();
        final State found = getStateByGUID(states, this.m_timeoutStateGUID);
        if (null != found) {
            return found;
        }

        // reverse compatibility
        if (ModelUtils.IsEmptyString(m_timeoutTransitionGUID)) {
            return null;
        }

        List transitions = m_ownerStateMachine.getTransitions();
        if (transitions == null) {
            return null;
        }//endif
        Iterator transitionIterator = transitions.iterator();
        while (transitionIterator.hasNext()) {
            StateTransition transition = (StateTransition) transitionIterator.next();
            if (transition.getGUID().equals(m_timeoutTransitionGUID)) {
                State toState = transition.getToState();
                m_timeoutStateGUID = toState.getGUID();
                notifyListeners();
                notifyOntologyOnChange();
                return toState;
            }//endif
        }//endwhile
        return null;
    }// end getTimeoutState



    private static State getStateByGUID(
            List statesToVisit,
            String guid) {
        return getStateByGUID(statesToVisit, new LinkedList(), guid);
    }


    private static State getStateByGUID(
            List statesToVisit,
            List statesVisited,
            String guid) {

        if (null != statesToVisit) {
            for (Object stateObj : statesToVisit) {
                State state = (State) stateObj;
                if (null != state) {
                    if (!statesVisited.contains(state)) {
                        statesVisited.add(state);
                        if (state.getGUID().equals(guid)) {
                            return state;
                        }
                        if (state instanceof StateComposite) {
                            state = getStateByGUID(((StateComposite) state).getEntities(), statesVisited, guid);
                            if (null != state) {
                                return state;
                            }
                        }//else
                    }//if
                }//if
            }//for
        }//if
        return null;
    }


    /**
     * Set the MutableState to transition to on timeout from the current State.
     *
     * @param state The new MutableState to transition to on timeout from the current State.
     */
    public void setTimeoutState(State state) {
        if (state == null) {
            m_timeoutStateGUID = null;
        } else {
            m_timeoutStateGUID = state.getGUID();
        }

//        List  transitions = m_ownerStateMachine.getTransitions ();
//        if (transitions == null) {
//            return;
//        }//endif
//        Iterator  transitionIterator = transitions.iterator ();
//        while (transitionIterator.hasNext ()) {
//            MutableStateTransition  transition = (StateTransition) transitionIterator.next ();
//            if (!transition.getFromState ().equals (this)) {
//                continue;
//            }//endif
//            if (transition.getToState ().equals (state)) {
//                m_timeoutTransitionGUID = transition.getGUID ();
//                return;
//            }//endif
//        }//endwhile
//        // Not found
//        m_timeoutTransitionGUID = null;
    }// end setTimeoutState


    /**
     * Delete the internal transition on this state (if any).
     */
    public void deleteInternalTransition() throws ModelException {
        deleteRule(INTERNAL_TRANSITION_RULE_TAG); // todo what value?
    }// end deleteInternalTransition


    /**
     * Delete the Rule identified by "qualifier" from the RuleSet contained in the Concept.
     *
     * @param qualifier String describing which Rule to delete from the Concept's RuleSet.
     */
    public void deleteRule(String qualifier) throws ModelException {
        String ruleName = getRuleName(qualifier);
        final MutableRuleSet conceptRuleSet = (MutableRuleSet) this.getRuleSet();
        conceptRuleSet.deleteRule(ruleName);
    }// end deleteRuleFromConcept


    public void enableInternalTransition(boolean enable) {
        m_isInternalTransitionEnabled = enable;
    }// end enableInternalTransition


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(XiNode root) throws ModelException {
        super.fromXiNode(root);

        StateMachine sm = getOwnerStateMachine();
//        DefaultMutableConcept concept = null;
        DefaultMutableRuleSet ruleSet = null;

        if (sm != null) {
//            concept = (DefaultMutableConcept) sm.getOwnerConcept();
//        }
//        if (concept != null) {
            ruleSet = (DefaultMutableRuleSet) sm.getRuleSet();
        }

        XiNode entryActionNode = XiChild.getChild(root, ENTRY_ACTION_NAME_TAG);
        if (entryActionNode != null) {
            m_entryAction = DefaultMutableRule.createDefaultRuleFromNode(entryActionNode.getFirstChild(), ruleSet);
        }
//        else {
//            // The m_ownerStateMachine is set by the DefaultOntology.createEntityFromNode method
//            m_entryAction = conceptRuleSet.getRule (root.getAttributeStringValue (ENTRY_ACTION_NAME_TAG));
//        }

        XiNode exitActionNode = XiChild.getChild(root, EXIT_ACTION_NAME_TAG);
        if (exitActionNode != null) {
            m_exitAction = DefaultMutableRule.createDefaultRuleFromNode(exitActionNode.getFirstChild(), ruleSet);
        }
//        else {
//            m_exitAction = conceptRuleSet.getRule (root.getAttributeStringValue (EXIT_ACTION_NAME_TAG));
//        }

        XiNode timeoutActionNode = XiChild.getChild(root, TIMEOUT_ACTION_NAME_TAG);
        if (timeoutActionNode != null) {
            m_timeoutAction = DefaultMutableRule.createDefaultRuleFromNode(timeoutActionNode.getFirstChild(), ruleSet);
        }
//        else {
//            m_timeoutAction = conceptRuleSet.getRule(root.getAttributeStringValue(TIMEOUT_ACTION_NAME_TAG));
//        }

        XiNode timeoutExprNode = XiChild.getChild(root, TIMEOUT_EXPRESSION_NAME_TAG);
        if (timeoutExprNode != null) {
//            m_timeoutExpression = DefaultRule.createDefaultRuleFromNode(timeoutExprNode.getFirstChild(), conceptRuleSet);
            MutableRuleFunction rf = DefaultMutableRuleFunction.createDefaultRuleFunctionFromNode(timeoutExprNode.getFirstChild());
            m_timeoutExpression = new MutableStateRuleFunction(rf);
        }

        String intEnabled = root.getAttributeStringValue(INTERNAL_TRANSITION_RULE_NAME_TAG);
        m_isInternalTransitionEnabled = !Boolean.FALSE.toString().equals(intEnabled);

        XiNode internalTransitionNode = XiChild.getChild(root, INTERNAL_TRANSITION_RULE_NAME_TAG);
        if (internalTransitionNode != null) {
            m_internalTransitionRule = DefaultMutableRule.createDefaultRuleFromNode(root, null);
        }
//        else {
//            m_internalTransitionRule = conceptRuleSet.getRule (root.getAttributeStringValue (INTERNAL_TRANSITION_RULE_NAME_TAG));
//        }

        m_timeoutStateGUID = root.getAttributeStringValue(TIMEOUT_STATE_GUID_NAME);
        if (m_timeoutStateGUID == null) {
            m_timeoutStateGUID = "";
        }

        m_timeoutTransitionGUID = root.getAttributeStringValue(TIMEOUT_STATE_NAME);

        String policyStr = root.getAttributeStringValue(TIMEOUT_POLICY_NAME);
        m_timeoutPolicy = (policyStr == null) ? 0 : Integer.parseInt(policyStr);

        XiNode extPropsNode = XiChild.getChild(root, EXTENDED_PROPERTIES_NAME);
        setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));
    }// end fromXiNode


    /**
     * Get the action to perform on entry to this state.
     *
     * @return The action to perform on entry to this state.
     */
    public Rule getEntryAction(boolean create) throws ModelException {
        if (m_entryAction == null) {
            m_entryAction = getRule(ENTRY_ACTION_RULE_TAG, create);
        }

        return m_entryAction;
    }// end getEntryAction


    /**
     * Get the action to perform on exit from this state.
     *
     * @ return The action to perform on exit from this state.
     */
    public Rule getExitAction(boolean create) throws ModelException {
        if (m_exitAction == null) {
            m_exitAction = getRule(EXIT_ACTION_RULE_TAG, create);
        }

        return m_exitAction;
    }// end getExitAction


    public Rule getTimeoutAction(boolean create) throws ModelException {
        if (m_timeoutAction == null) {
            m_timeoutAction = getRule(TIMEOUT_ACTION_RULE_TAG, create);
        }

        return m_timeoutAction;
    }


    public RuleFunction getTimeoutExpression() {
        if (m_timeoutExpression == null) {
            StateMachine sm = getOwnerStateMachine();
            if (sm == null) {
                return null;
            }

            Concept concept = sm.getOwnerConcept();
            if (concept == null) {
                return null;
            }

            String conceptName = concept.getName();

            m_timeoutExpression = new MutableStateRuleFunction((DefaultMutableOntology) concept.getOntology(), (DefaultMutableFolder) concept.getFolder(), concept.getName());
            m_timeoutExpression.setArgumentType(conceptName.toLowerCase(), concept.getFullPath());
            m_timeoutExpression.setReturnType(RDFTypes.INTEGER.getName());
            m_timeoutExpression.setValidity(RuleFunction.Validity.CONDITION);

            String body = "return " + m_timeout + ";"; // for backwards compatibility
            m_timeoutExpression.setBody(body);
        }

        return m_timeoutExpression;
    }


    /**
     * Get the internal transition on this state (if any).
     *
     * @return The internal transition on this state (if any).
     */
    public Rule getInternalTransition(boolean create) throws ModelException {
        if (m_internalTransitionRule == null) {
            m_internalTransitionRule = getRule(INTERNAL_TRANSITION_RULE_TAG, create); // todo what value?
        }

        return m_internalTransitionRule;
    }// end getInternalTransition


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
//        BEModelBundle bundle = BEModelBundle.getBundle();
//
//        ArrayList modelErrors = new ArrayList();
//        // todo rkt Need to add code to check Rule actions for validity
//        //modelErrors.addAll (getModelErrorsForRuleAction (m_entryAction));
//        //modelErrors.addAll (getModelErrorsForRuleAction (m_exitAction));
//        if (isInternalTransitionEnabled() && m_internalTransitionRule != null) {
//            modelErrors.addAll(m_internalTransitionRule.getModelErrors());
//        }
//        addWarningIfTrue(hasMultipleLambdas(), modelErrors, bundle.getString(MULTIPLE_LAMBDAS_ERROR));
//        addErrorIfTrue(timeoutStateMissing(), modelErrors, bundle.getString(TIMEOUT_STATE_MISSING));
//        return modelErrors;
    	return null;
    }// end getModelErrors


    public boolean timeoutStateMissing() {
        int timeoutPolicy = getTimeoutPolicy();
        if (timeoutPolicy == State.DETERMINISTIC_STATE_POLICY) {
            State timeoutState = getTimeoutState();
            if (timeoutState == null) {
                return true;
            }
        }

        return false;
    }


    public boolean hasMultipleLambdas() {
        StateMachine sm = this.getOwnerStateMachine();
        List l = sm.getTransitions();
        if (l == null) {
            return false;
        }

        boolean foundOne = false;
        for (Iterator it = l.iterator(); it.hasNext();) {
            StateTransition st = (StateTransition) it.next();
            if (this != st.getFromState()) {
                continue;
            }
            boolean isLambda = isLambda(st);

            if (isLambda) {
                if (foundOne) {
                    return true;
                } else {
                    foundOne = true;
                }
            }
        }

        return false;
    }


    protected boolean isLambda(StateTransition st) {
        if (st.isLambda()) {
            return true;
        }

        Rule r = st.getGuardRule(false);
        if (r == null) {
            return true;
        }

        String c = r.getConditionText();
        return ModelUtils.IsEmptyString(c);
    }


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    public void setOwnerStateMachine(MutableStateMachine ownerStateMachine) {
        super.setOwnerStateMachine(ownerStateMachine);
        if (ownerStateMachine == null) {
            return;
        }

        try {
//            DefaultMutableConcept dc = (DefaultMutableConcept) ownerStateMachine.getOwnerConcept();
//            if (null == dc) { return ; }
            final RuleSet rs = ownerStateMachine.getRuleSet();
            if (m_entryAction != null) {
                String name = getRuleName(ENTRY_ACTION_RULE_TAG);
                m_entryAction.setName(name, false);
                m_entryAction.setRuleSet(rs);
            }

            if (m_exitAction != null) {
                String name = getRuleName(EXIT_ACTION_RULE_TAG);
                m_exitAction.setName(name, false);
                m_exitAction.setRuleSet(rs);
            }

            if (m_timeoutAction != null) {
                String name = getRuleName(TIMEOUT_ACTION_RULE_TAG);
                m_timeoutAction.setName(name, false);
                m_timeoutAction.setRuleSet(rs);
            }

            if (m_timeoutExpression != null) {
                String name = getRuleName(TIMEOUT_EXPRESSION_RULE_TAG);
                m_timeoutExpression.setName(name, false);
//                m_timeoutExpression.setRuleSet(rs);
            }


            if (m_internalTransitionRule != null) {
                String name = getRuleName(INTERNAL_TRANSITION_RULE_TAG);
                m_internalTransitionRule.setName(name, false);
                m_internalTransitionRule.setRuleSet(rs);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }// end setOwnerStateMachine


    /**
     * Get the Rule identified by "qualifier" from the RuleSet contained in the Concept.
     *
     * @param qualifier String describing which Rule to get from the Concept's RuleSet.
     * @param create    If true, create if not yet present.
     */
    public MutableRule getRule(
            String qualifier,
            boolean create) throws ModelException {
        StateMachine sm = getOwnerStateMachine();
        if (sm == null) {
            return null;
        }

        String ruleName = getRuleName(qualifier);
        final MutableRuleSet ruleSet = (MutableRuleSet) this.getRuleSet();
        if (ruleSet == null) {
            return null;
        }

        MutableRule rule = (MutableRule) ruleSet.getRule(ruleName);

        if (rule == null && create) {
            rule = ruleSet.createRule(ruleName, false, false);
        }//endif
        return rule;
    }// end getRuleFromConcept


    public boolean isInternalTransitionEnabled() {
        return false; // ISS-- Diabled for Version 1.0
//        return m_isInternalTransitionEnabled;
    }// end isInternalTransitionEnabled


    public void delete() {
        m_ownerStateMachine.deleteLinkedTransitions(this);
        try {
            MutableRule r = (MutableRule) getEntryAction(false);
            if (r != null) {
                deleteRule(ENTRY_ACTION_RULE_TAG);
                r.delete();
            }

            r = (MutableRule) getExitAction(false);
            if (r != null) {
                deleteRule(EXIT_ACTION_RULE_TAG);
                r.delete();
            }

            r = (MutableRule) getTimeoutAction(false);
            if (r != null) {
                deleteRule(TIMEOUT_ACTION_RULE_TAG);
                r.delete();
            }

            r = (MutableRule) getInternalTransition(false);
            if (r != null) {
                deleteRule(INTERNAL_TRANSITION_RULE_TAG);
                r.delete();
            }

            final MutableRuleFunction rf = (MutableRuleFunction) getTimeoutExpression();
            if (rf != null) {
//                deleteRuleFromConcept(TIMEOUT_EXPRESSION_RULE_TAG);
                rf.delete();
            }

        } catch (ModelException e) {
        }

        super.delete();
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        MutableRule internalTransitionRule = null;
        MutableRule timeoutAction = null;
        MutableRule entryAction = null;
        MutableRule exitAction = null;
//        RuleFunction timeoutExpression = null;

        internalTransitionRule = (MutableRule) getInternalTransition(false);
        timeoutAction = (MutableRule) getTimeoutAction(false);
        entryAction = (MutableRule) getEntryAction(false);
        exitAction = (MutableRule) getExitAction(false);
//        timeoutExpression = getTimeoutExpression();

        super.setName(name, renameOnConflict);

        StateMachine sm = getOwnerStateMachine();
        if (sm == null) {
            return;
        }

        if (internalTransitionRule != null) {
            String ruleName = getRuleName(INTERNAL_TRANSITION_RULE_TAG);
            internalTransitionRule.setName(ruleName, false);
        }

        if (timeoutAction != null) {
            String ruleName = getRuleName(TIMEOUT_ACTION_RULE_TAG);
            timeoutAction.setName(ruleName, false);
        }

        if (entryAction != null) {
            String ruleName = getRuleName(ENTRY_ACTION_RULE_TAG);
            entryAction.setName(ruleName, false);
        }

        if (exitAction != null) {
            String ruleName = getRuleName(EXIT_ACTION_RULE_TAG);
            exitAction.setName(ruleName, false);
        }

//        if(timeoutExpression != null) {
//            String ruleName = getRuleName(TIMEOUT_EXPRESSION_RULE_TAG);
//            timeoutExpression.setName(ruleName, false);
//        }
    }


    public void setOntology(MutableOntology ontology) {
        super.setOntology(ontology);
        if (m_timeoutExpression != null) {
            m_timeoutExpression.setOntology(ontology);
        }
    }


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        // Don't need to persist m_ownerStateMachine since it will be known implicitly from order and is set by owning object
        if (m_entryAction != null) {
//            setAttributeStringValueSafe (root, ENTRY_ACTION_NAME_TAG, m_entryAction.getName ());
            XiNode actionNode = root.appendElement(ENTRY_ACTION_NAME_TAG);
            actionNode.appendChild(((DefaultMutableRule) m_entryAction).toXiNode(factory));
        }//endif
        if (m_exitAction != null) {
//            setAttributeStringValueSafe (root, EXIT_ACTION_NAME_TAG, m_exitAction.getName ());
            XiNode actionNode = root.appendElement(EXIT_ACTION_NAME_TAG);
            actionNode.appendChild(((DefaultMutableRule) m_exitAction).toXiNode(factory));

        }//endif

        if (m_timeoutAction != null) {
//            setAttributeStringValueSafe(root, TIMEOUT_ACTION_NAME_TAG, m_timeoutAction.getName());
            XiNode actionNode = root.appendElement(TIMEOUT_ACTION_NAME_TAG);
            actionNode.appendChild(((DefaultMutableRule) m_timeoutAction).toXiNode(factory));
        }//endif

        if (m_timeoutExpression != null) {
            XiNode exprNode = root.appendElement(TIMEOUT_EXPRESSION_NAME_TAG);
            exprNode.appendChild(((DefaultMutableRuleFunction) m_timeoutExpression).toXiNode(factory));
        }

        setAttributeStringValueSafe(root, INTERNAL_TRANSITION_RULE_NAME_TAG, String.valueOf(m_isInternalTransitionEnabled));
        if (m_internalTransitionRule != null) {
//            setAttributeStringValueSafe (root, INTERNAL_TRANSITION_RULE_NAME_TAG, m_internalTransitionRule.getName ());
            XiNode actionNode = root.appendElement(INTERNAL_TRANSITION_RULE_NAME_TAG);
            actionNode.appendChild(((DefaultMutableRule) m_internalTransitionRule).toXiNode(factory));

        }//endif

        setAttributeStringValueSafe(root, TIMEOUT_POLICY_NAME, String.valueOf(m_timeoutPolicy));

        if (!ModelUtils.IsEmptyString(m_timeoutStateGUID)) {
            setAttributeStringValueSafe(root, TIMEOUT_STATE_GUID_NAME, m_timeoutStateGUID);
        } else if (m_timeoutTransitionGUID != null) {
            setAttributeStringValueSafe(root, TIMEOUT_STATE_NAME, m_timeoutTransitionGUID);
        }//endif
        return root;
    }// end toXiNode


    private class MutableStateRuleFunction extends DefaultMutableRuleFunction {


        public MutableStateRuleFunction(MutableRuleFunction rf) {
            super((DefaultMutableOntology) rf.getOntology(), (DefaultMutableFolder) rf.getFolder(), rf.getName());
            setReturnType(rf.getReturnType());
            setBody(rf.getBody());
            setValidity(rf.getValidity());
            setScopeInternal(rf.getScope());
        }


        public MutableStateRuleFunction(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
            super(ontology, folder, name);
        }

        public void delete() {
//            removeFromParticipant(m_returnType);
//
//            for (Iterator it = getArguments().values().iterator(); it.hasNext();) {
//                final Symbol symbol = (Symbol) it.next();
//                removeFromParticipant(symbol.getType());
//            }

            DefaultMutableConcept owner = (DefaultMutableConcept) getOwnerConcept();
            if(owner != null) {
                owner.notifyListeners();
            }
        }//delete

        private Concept getOwnerConcept() {
            StateMachine sm = DefaultMutableState.this.getOwnerStateMachine();
            if (sm == null) {
                return null;
            }

            return sm.getOwnerConcept();
        }


        public String getFolderPath() {
            Concept concept = getOwnerConcept();
            if (concept != null) {
                return concept.getFolderPath();
            }

            return super.getFolderPath();
        }


        public String getName() {
            Concept concept = getOwnerConcept();
            if (concept != null) {
                return concept.getName();
            }

            return super.getName();
        }


        public void setName(String name, boolean renameOnConflict) throws ModelException {
            m_name = name;
        }


        public void setOntology(MutableOntology ontology) {
            m_ontology = (DefaultMutableOntology) ontology;
        }


        public Symbols getArguments() {
            Concept concept = getOwnerConcept();
            final DefaultMutableSymbols args = (DefaultMutableSymbols) super.getArguments();
            if (concept == null) {
                return args;
            }

            String path = concept.getFullPath();

            if (args.size() == 0) {
                final String name = concept.getName();
                args.put(new DefaultMutableSymbol(args, name, path));
                return args;
            }

            // update the path
            Object[] objs = args.entrySet().toArray();
            Map.Entry entry = (Map.Entry) objs[0];

            MutableSymbol symbol = (MutableSymbol) entry.getValue();
            if (null == symbol) {
                symbol = new DefaultMutableSymbol(args, (String) entry.getKey(), path);
                entry.setValue(symbol);
            } else {
                symbol.setType(path);
            }

            return args;
        }
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getEntryCodeBlock()
	 */
	@Override
	public CodeBlock getEntryCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getExitCodeBlock()
	 */
	@Override
	public CodeBlock getExitCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getTimeoutCodeBlock()
	 */
	@Override
	public CodeBlock getTimeoutCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}// end class DefaultState
