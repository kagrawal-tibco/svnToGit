package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 4:45:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConditionImpl implements Condition {

    final static private String[] EMPTY_DEPENDSON = new String[0];

    Rule m_rule;
    protected Identifier[] m_identifiers;

    public ConditionImpl(Rule rule, Identifier[] identifiers) {
        m_rule = rule;
        m_identifiers = identifiers;
    }

    public ConditionImpl(Rule rule) {
        m_rule = rule;
    }

    public Identifier[] getIdentifiers() {
        return m_identifiers;
    }

    public Rule getRule() {
        return m_rule;
    }

    //The default for this is false, when it is true the subclass will override it.
    public boolean alwaysEvaluate() {
        return false;
    }

    public String[] getDependsOn() {
        return EMPTY_DEPENDSON;
    }
    
    abstract public boolean eval(Object[] objects);
}