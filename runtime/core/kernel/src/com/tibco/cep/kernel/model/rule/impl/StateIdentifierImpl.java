package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.rule.StateIdentifier;
import com.tibco.cep.kernel.model.rule.StateRule;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 8, 2009
 * Time: 11:12:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateIdentifierImpl extends IdentifierImpl implements StateIdentifier {
    int[]  m_dependencyBitArray_Child;
    StateRule rule;

    public StateIdentifierImpl(Class type, String name) {
        super(type,name);
    }

    public void setRule(StateRule rule) {
        this.rule=rule;
    }

    public void setDependencyBitArray_Child(int [] dependencyBitArray_Child) {
        this.m_dependencyBitArray_Child=dependencyBitArray_Child;
    }

    public boolean hasDependency(Mutable mutable, int[] overrideDirtyBitMap) {
//        System.err.println(rule.getDescription() + ": hasDependency: ");
        boolean hasDependency=super.hasDependency(mutable, overrideDirtyBitMap);
//        System.err.println(rule.getDescription() + ": parent-dependency: " + hasDependency);
        if (!hasDependency ) {
            if (m_dependencyBitArray_Child == null) {
                return true;
            }
            int[] checkthis;
            if(overrideDirtyBitMap == null)
                checkthis = mutable.getDirtyBitArray();
            else
                checkthis = overrideDirtyBitMap;
            for(int i = 0; i < m_dependencyBitArray_Child.length && i < checkthis.length ; i++ ) {
                if((m_dependencyBitArray_Child[i] & checkthis[i]) != 0) {
//                    System.err.println(rule.getDescription() + ": check-child-dependency: true" );
                    if (overrideDirtyBitMap != null)
                        hasDependency=rule.checkDependency(mutable, overrideDirtyBitMap);
                    else
                        hasDependency=true;
                }
            }
        }
        return hasDependency;
//        return hasDependency;
    }
}
