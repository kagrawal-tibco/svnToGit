/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: ssubrama
 * Date: Oct 31, 2004
 * Time: 8:58:50 AM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import com.tibco.cep.designtime.model.element.stategraph.InternalStateTransition;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;


public class MutableInternalStateTransition extends DefaultMutableStateTransition implements InternalStateTransition {


    protected State m_ownerState;
    protected MutableRule m_rule;
    protected boolean m_isLambda;


    public MutableInternalStateTransition(String name, State ownerState, MutableRule guardRule) {
        super(null, null, null);
        m_ownerState = ownerState;
        m_rule = guardRule;
        m_name = name;
        m_isLambda = false;
    }// end InternalStateTransition


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
    }// end setFromState


    public void setIsLambda(boolean isLambda) {
        m_isLambda = isLambda;
    }// end setIsLambda


    public void setToState(State toState) {
    }// end setToState
}// end class InternalStateTransition
