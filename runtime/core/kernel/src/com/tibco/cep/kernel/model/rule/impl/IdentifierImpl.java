package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 4:58:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdentifierImpl implements Identifier {
    final Class  m_type;
    final String m_name;
    int[]  m_dependencyBitArray;

    public IdentifierImpl(Class type, String name) {
        m_type = type;
        m_name = name;
        m_dependencyBitArray = null;
    }

    public boolean equals(Object that) {
        if (that instanceof Identifier) {
            return (m_type.equals(((IdentifierImpl)that).m_type) &&
                    m_name.equals(((IdentifierImpl)that).m_name));
        }
        return false;
    }

    public int hashCode() {
        return m_type.hashCode() + m_name.hashCode();
    }

    public Class getType() {
        return m_type;
    }

    public String getName() {
        return m_name;
    }

    public int[] getDependencyBitArray() {
        return m_dependencyBitArray;
    }

    public void setDependencyBitArray(int[] dependencyBitArray) {
        m_dependencyBitArray = dependencyBitArray;
    }

    public String toString() {
        return "[" + m_type.getName() + " " + m_name + "]";
    }

    public boolean hasDependency(Mutable mutable, int[] overrideDirtyBitMap) {
        if( m_dependencyBitArray == null) return true;
        int[] checkthis;
        if(overrideDirtyBitMap == null)
            checkthis = mutable.getDirtyBitArray();
        else
            checkthis = overrideDirtyBitMap;
        for(int i = 0; i < m_dependencyBitArray.length && i < checkthis.length ; i++ ) {
            if((m_dependencyBitArray[i] & checkthis[i]) != 0) {
                return true;
            }
        }
        return false;
    }
}
