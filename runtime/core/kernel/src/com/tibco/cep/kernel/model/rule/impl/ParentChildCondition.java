package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 6:20:43 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ParentChildCondition extends ConditionImpl {

    public ParentChildCondition(Rule rule, Identifier[] identifiers) {
        super(rule, identifiers);
    }

    public ParentChildCondition(Rule rule) {
        super(rule);
    }

    abstract public Identifier getParentIdentifier();

    abstract public Identifier getChildIdentifier();

    abstract public Class      getPropertyClass();
}
