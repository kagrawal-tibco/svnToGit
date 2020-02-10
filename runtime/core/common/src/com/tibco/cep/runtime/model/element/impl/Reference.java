package com.tibco.cep.runtime.model.element.impl;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 6:03:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Reference implements ConceptOrReference {
    long m_id;

    public Reference(long id) {
        m_id = id;
    }

    public long getId() {
        return m_id;
    }

    public int hashCode() {
        return EntityImpl.hashCode(m_id);

    }

    public boolean equals(Object obj) {
        if(obj instanceof Reference) {
            return (m_id == ((Reference)obj).m_id);
        }
        return false;
    }
    
    public static boolean idEquals(ConceptOrReference a, ConceptOrReference b) {
        if(a == b) return true;
        return a!=null && b!= null && a.getId() == b.getId();
    }

    public static boolean idEquals(Object a, ConceptOrReference b) {
        if(a == b) return true;
        return a!=null && b!= null && a instanceof ConceptOrReference && ((ConceptOrReference)a).getId() == b.getId();
    }

    public static boolean idEquals(ConceptOrReference a, Object b) {
        return idEquals(b, a);
    }
}