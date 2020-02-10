/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 5:31:10 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.util.ArrayList;
import java.util.List;

import com.tibco.be.util.shared.RuleConstants;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStatePseudo;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateTransition;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * A link between any two states on a state machine.  This gives a direction and a rule
 * that determines when the link can be traversed (and the rule can have an action).
 */
public class DefaultMutableStateTransition extends DefaultMutableStateLink implements MutableStateTransition {


    public static final String PERSISTENCE_NAME = "StateTransition";
    protected static final ExpandedName TO_STATE_TAG = ExpandedName.makeName("toState");
    protected static final ExpandedName FROM_STATE_TAG = ExpandedName.makeName("fromState");
    protected static final ExpandedName GUARD_RULE_GUID_TAG = ExpandedName.makeName("guardRuleGUID");
    protected static final ExpandedName IS_LAMBDA_TAG = ExpandedName.makeName("isLambda");
    protected static final ExpandedName PRIORITY_TAG = ExpandedName.makeName("priority");


    protected static final String TRANSITION_MUST_HAVE_FROM_STATE = "DefaultStateTransition.getModelErrors.transitionMustHaveFromState";
    protected static final String TRANSITION_MUST_HAVE_TO_STATE = "DefaultStateTransition.getModelErrors.transitionMustHaveToState";
    protected static final String END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateTransition.getModelErrors.endStateCannotHaveExitingTransitions";
    protected static final String START_STATE_CANNOT_HAVE_ENTERING_TRANSITIONS = "DefaultStateTransition.getModelErrors.startStateCannotHaveEnteringTransitions";
    protected static final String ROOT_STATE_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateTransition.getModelErrors.rootStateCannotHaveExitingTransitions";
    protected static final String ROOT_STATE_CANNOT_HAVE_ENTERING_TRANSITIONS = "DefaultStateTransition.getModelErrors.rootStateCannotHaveEnteringTransitions";
    protected static final String TRANSITION_EXITING_PSEUDO_STATE_CANNOT_HAVE_GUARD_RULE = "DefaultStateTransition.getModelErrors.transitionExitingPseudoStateCannotHaveGuardRule";
    protected static final String LAMDBA_SELF_TRANSITION = "DefaultStateTransition.getModelErrors.lambdaSelfTransition";

    protected State m_toState = null;
    protected State m_fromState = null;
    protected MutableRule m_guardRule = null;
    protected boolean m_isLambda = false;
    protected boolean m_fwdCorrelates = true;

//    protected transient String m_toStateGUID = null;
//    protected transient String m_fromStateGUID = null;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateTransition(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, ownerStateMachine);
        m_ownerStateMachine = ownerStateMachine;
    }// end DefaultStateTransition


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param ownerStateMachine The state machine that owns this state.
     * @param toState           The State where the transition starts.
     * @param fromState         The State where the transition ends.
     */
    public DefaultMutableStateTransition(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine,
            State toState,
            State fromState) {
        // Bounds are meaningless for transitions (they just follow the states) so just give a dummy bounds
        super(ontology, name, ownerStateMachine);
        m_toState = toState;
        m_fromState = fromState;
    }// end DefaultStateTransition


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        String toStateGUID = root.getAttributeStringValue(TO_STATE_TAG);
        String fromStateGUID = root.getAttributeStringValue(FROM_STATE_TAG);
//        m_toStateGUID = root.getAttributeStringValue (TO_STATE_TAG);
//        m_fromStateGUID = root.getAttributeStringValue (FROM_STATE_TAG);


        m_toState = (State) ((DefaultMutableStateMachine) m_ownerStateMachine).getStateEntityFromCache(toStateGUID);
        m_fromState = (State) ((DefaultMutableStateMachine) m_ownerStateMachine).getStateEntityFromCache(fromStateGUID);

//        if(m_ownerStateMachine != null) {
//            m_toState = (State) ((DefaultStateMachine) m_ownerStateMachine).getStateEntityFromCache (m_toStateGUID);
//            m_fromState = (State) ((DefaultStateMachine) m_ownerStateMachine).getStateEntityFromCache (m_fromStateGUID);
//        }

        m_isLambda = Boolean.valueOf(root.getAttributeStringValue(IS_LAMBDA_TAG)).booleanValue();
        String fwdCorrStr = root.getAttributeStringValue(DefaultMutableStateMachine.FWD_CORRELATES_TAG);
        if (!ModelUtils.IsEmptyString(fwdCorrStr)) {
            m_fwdCorrelates = Boolean.valueOf(fwdCorrStr).booleanValue();
        }

        StateMachine sm = getOwnerStateMachine();
        DefaultMutableConcept dc = null;
        DefaultMutableRuleSet rs = null;

        if (sm != null) {
            rs = (DefaultMutableRuleSet) sm.getRuleSet();
//            dc = (DefaultMutableConcept) sm.getOwnerConcept();
//        }
//        if (dc != null) {
//            rs = dc.getStateMachineRuleSet();
        }

        XiNode ruleNode = XiChild.getChild(root, GUARD_RULE_GUID_TAG);
        if (ruleNode != null) {
            m_guardRule = DefaultMutableRule.createDefaultRuleFromNode(ruleNode.getFirstChild(), rs);
        }
    }// end fromXiNode


    /**
     * Get the "from" State (where the link originates) for this link.
     *
     * @return The "from" State (where the link originates) for this link.
     */
    public State getFromState() {
        return m_fromState;
    }// end getFromState


    /**
     * Get the guard (a Rule) for this link.  This is the rule that determines when
     * this link will be followed.
     *
     * @param createIfNeeded Should this method create a new Rule (and return it) if none currently set.
     * @return The guard (a Rule) for this link.
     */
    public Rule getGuardRule(
            boolean createIfNeeded) {
        try {
            if (m_guardRule == null) {
                // todo Is this a valid name?
                String ruleName = getOwnerStateMachine().getName() + "_" + getName();
                final MutableRuleSet conceptRuleSet = (MutableRuleSet) this.getRuleSet();
                m_guardRule = (MutableRule) conceptRuleSet.getRule(ruleName);
                if (m_guardRule == null && createIfNeeded) {
                    m_guardRule = conceptRuleSet.createRule(ruleName, false, false);
                }//endif
            }//endif
            return m_guardRule;
        } catch (Exception exception) {
            return null;
        }//endtry
    }// end getGuardRule


    public void delete() {
        if (m_guardRule != null) {
            m_guardRule.delete();
        }
        super.delete();
    }


    public void setOntology(MutableOntology ontology) {
        super.setOntology(ontology);
        if (m_guardRule != null) {
            m_guardRule.setOntology(ontology);
        }
    }


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        ArrayList modelErrors = new ArrayList();
        BEModelBundle bundle = BEModelBundle.getBundle();
//        addErrorIfTrue (m_fromState == null, modelErrors, bundle.getString (TRANSITION_MUST_HAVE_FROM_STATE));
//        addErrorIfTrue (m_toState == null, modelErrors, bundle.getString (TRANSITION_MUST_HAVE_TO_STATE));
        // todo rkt Should check the Guard Rule here
        if (m_fromState != null && m_toState != null) {
//            addErrorIfTrue (m_fromState instanceof StateEnd, modelErrors, bundle.getString (END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS));
//            addErrorIfTrue (m_toState instanceof StateStart, modelErrors, bundle.getString (START_STATE_CANNOT_HAVE_ENTERING_TRANSITIONS));
//            addErrorIfTrue (m_fromState == getOwnerStateMachine ().getMachineRoot (), modelErrors, bundle.getString (ROOT_STATE_CANNOT_HAVE_EXITING_TRANSITIONS));
//            addErrorIfTrue (m_toState == getOwnerStateMachine ().getMachineRoot (), modelErrors, bundle.getString (ROOT_STATE_CANNOT_HAVE_ENTERING_TRANSITIONS));

            Rule guardRule = getGuardRule(false);
            addErrorIfTrue(m_toState.equals(m_fromState) && (guardRule == null || ModelUtils.IsEmptyString(guardRule.getConditionText()) || isLambda()), modelErrors, bundle.getString(LAMDBA_SELF_TRANSITION));

            if (m_fromState instanceof MutableStatePseudo) {
//                addErrorIfTrue (getGuardRule (false) != null, modelErrors, bundle.getString (TRANSITION_EXITING_PSEUDO_STATE_CANNOT_HAVE_GUARD_RULE));
            }//endif
        }//endif
        return modelErrors;
    }// end getModelErrors


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
        if (m_ownerStateMachine != null) {

        }

    }// end setOwnerStateMachine


    /**
     * Get the "to" State (where the link terminates) for this link.
     *
     * @return The "to" State (where the link terminates) for this link.
     */
    public State getToState() {
        return m_toState;
    }// end getToState


    /**
     * Is this transition a "lambda" transition (has no condition and is taken immediately).
     *
     * @return true if this transition is a "lambda" transition, otherwise false.
     */
    public boolean isLambda() {
        return m_isLambda;
    }// end isLambda


    /**
     * Set the new "from" State (where the link originates) for this link.
     *
     * @param fromState The new "from" State (where the link originates) for this link.
     */
    public void setFromState(
            State fromState) {
        m_fromState = fromState;
//        m_fromStateGUID = (fromState != null) ? fromState.getGUID() : null;
    }// end setFromState


    /**
     * Set whether this transition is a "lambda" transition (has no condition and is taken immediately).
     *
     * @param isLambda Is this transition a "lambda" transition.
     */
    public void setIsLambda(
            boolean isLambda) {
        m_isLambda = isLambda;
    }// end setIsLambda


    /**
     * Set the new "to" State (where the link terminates) for this link.
     *
     * @param toState The new "to" State (where the link terminates) for this link.
     */
    public void setToState(
            State toState) {
        m_toState = toState;
//        m_toStateGUID = (toState != null) ? toState.getGUID() : null;

    }// end setToState


    public int getPriority() {
        Rule guardRule = getGuardRule(false);
        if (guardRule == null) {
            return RuleConstants.DEFAULT_PRIORITY;
        }
        return guardRule.getPriority();
    }


    public boolean forwardCorrelates() {
        return m_fwdCorrelates;
    }


    public void setForwardCorrelate(boolean fwdCorrelate) {
        m_fwdCorrelates = fwdCorrelate;
    }


    public void setPriority(int priority) {
        final MutableRule guardRule = (MutableRule) getGuardRule(true);
        guardRule.setPriority(priority);
    }


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        if (m_toState != null) {
            setAttributeStringValueSafe(root, TO_STATE_TAG, m_toState.getGUID());
        }//endif
        if (m_fromState != null) {
            setAttributeStringValueSafe(root, FROM_STATE_TAG, m_fromState.getGUID());
        }//endif
        if (m_guardRule != null) {
            //setAttributeStringValueSafe (root, GUARD_RULE_GUID_TAG, m_guardRule.getGUID ());
            XiNode ruleNode = root.appendElement(GUARD_RULE_GUID_TAG);
            ruleNode.appendChild(((DefaultMutableRule) m_guardRule).toXiNode(factory));
        }//endif
        root.setAttributeStringValue(IS_LAMBDA_TAG, String.valueOf(m_isLambda));
        root.setAttributeStringValue(DefaultMutableStateMachine.FWD_CORRELATES_TAG, String.valueOf(m_fwdCorrelates));

        return root;
    }// end toXiNode
}// end class DefaultStateTransition
