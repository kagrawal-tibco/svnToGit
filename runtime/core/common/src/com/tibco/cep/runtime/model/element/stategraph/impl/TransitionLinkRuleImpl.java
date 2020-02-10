package com.tibco.cep.runtime.model.element.stategraph.impl;


import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLinkAction;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Nov 10, 2004
 * Time: 6:35:06 PM
 * To change this template use File | Settings | File Templates.
 * Every generated class implements the action method of the TransitionLinkAction.
 */
public abstract class TransitionLinkRuleImpl extends RuleImpl implements Action, TransitionLinkAction{


    private int transitionIdx;
    private int argIndex;
    public TransitionLinkRuleImpl(
            String name,
            String uri,
            int priority,
            int idx,
            int ai) {
        super(name, uri, priority);
        this.transitionIdx = idx;
        this.argIndex = ai;
    }

    public TransitionLinkRuleImpl(
            String name,
            String uri,
            int idx,
            int ai) {
        super(name, uri);
        m_actions = new Action[] {this};
        this.transitionIdx = idx;
        this.argIndex = ai;
    }

    public Rule getRule() {
        return this;
    }



    public void execute(Object[] args) {
        try {
            ConceptImpl aci = (ConceptImpl) args[argIndex];
            aci.sm.fire(this, transitionIdx, args);
        }
        catch (ClassCastException ce) {
            ce.printStackTrace();
        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
