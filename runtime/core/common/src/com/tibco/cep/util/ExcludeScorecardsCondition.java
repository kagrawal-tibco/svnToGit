package com.tibco.cep.util;

import java.util.ArrayList;

import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ConditionImpl;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 27, 2006
 * Time: 5:18:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcludeScorecardsCondition extends ConditionImpl {
    public ExcludeScorecardsCondition(Rule rule) {
        super(rule, makeIdentifierArray(rule));
    }

    private static Identifier[] makeIdentifierArray(Rule rule) {
        Identifier[] ruleIds = rule.getIdentifiers();
        ArrayList conditionIds = new ArrayList();
        for(int ii = 0; ii < ruleIds.length; ii++) {
            Class cls = ruleIds[ii].getClass();
            if(NamedInstance.class.isAssignableFrom(cls)) {
                conditionIds.add(new IdentifierImpl(cls, ruleIds[ii].getName()));
            }
        }
        Identifier[] idArr = new Identifier[conditionIds.size()];
        return (Identifier[])conditionIds.toArray(idArr);
    }

    public boolean eval(Object[] objects) {
        Object concept = null;
        for(int ii = 0; ii < objects.length; ii++) {
            concept = objects[ii];
            assert(concept instanceof ConceptImpl);
            if(concept instanceof NamedInstance) return false;
        }
        return true;
    }
}
