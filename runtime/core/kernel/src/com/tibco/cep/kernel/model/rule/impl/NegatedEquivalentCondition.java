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
abstract public class NegatedEquivalentCondition extends ConditionImpl {

    public NegatedEquivalentCondition(Rule rule, Identifier[] identifiers) {
        super(rule, identifiers);
    }

    public NegatedEquivalentCondition(Rule rule) {
        super(rule);
    }

    abstract public Identifier[] getLeftIdentifiers();

    abstract public Identifier[] getRightIdentifiers();

    abstract public int leftExpHashcode(Object[] objs);

    abstract public int rightExpHashcode(Object[] objs);

    abstract public Object leftExp(Object[] objs);

    abstract public Object rightExp(Object[] objs);
}
