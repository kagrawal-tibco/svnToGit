package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 4:42:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ActionImpl implements Action {

    Rule m_rule;

    public ActionImpl(Rule rule) {
        m_rule = (RuleImpl) rule;
    }

    public Identifier[] getIdentifiers() {
        return getRule().getIdentifiers();
    }    

    public Rule getRule() {
        return m_rule;
    }

    abstract public void execute(Object[] objects);

}