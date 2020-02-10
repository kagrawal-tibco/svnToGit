package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 6:16:41 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class EquivalentCondition extends ConditionImpl {
    Identifier [] m_leftIdentifiers;
    Identifier [] m_rightIdentifiers;

    public EquivalentCondition(Rule rule, Identifier[] identifiers) {
        super(rule, identifiers);
    }

    public EquivalentCondition(Rule rule) {
        super(rule);
    }

    public Identifier[] getLeftIdentifiers() {
        if (m_leftIdentifiers == null) {
            m_leftIdentifiers=_getLeftIdentifiers();
        }
        return m_leftIdentifiers;
    }

    public Identifier[] getRightIdentifiers() {
        if (m_rightIdentifiers == null) {
            m_rightIdentifiers=_getRightIdentifiers();
        }
        return m_rightIdentifiers;
    }

    abstract public Identifier[] _getLeftIdentifiers();

    abstract public Identifier[] _getRightIdentifiers();

    abstract public int leftExpHashcode(Object[] objs);

    abstract public int rightExpHashcode(Object[] objs);
}
