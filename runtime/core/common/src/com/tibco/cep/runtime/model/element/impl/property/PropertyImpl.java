package com.tibco.cep.runtime.model.element.impl.property;

import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 8:47:26 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyImpl implements Property {
    protected byte m_status = 0;

    protected static final byte IS_SET = (byte)0x80;
    protected static final byte PROP_IDX_MASK = ~IS_SET;

    public void setPropertyIndex(int idx) {
        if(idx < 0 || idx > 127) {
            throw new IllegalArgumentException("PropertyImpl: prop index " + idx + " not between 0 and 127");
        }
        m_status |= idx;
    }

    public int getPropertyIndex() {
        return (m_status & PROP_IDX_MASK);
    }

    //for rete network re-evaluation
    public int dirtyIndex() {
        return getPropertyIndex();
    }

    //this is just here as a reminder that generated rules need to get the dirty index without a property instance
    public static String DIRTY_INDEX_STATIC = PropertyImpl.class.getName()+".dirtyIndex_static";
    public static int dirtyIndex_static(int propertyIndex) {
        return propertyIndex;
    }

    //for persistence -- overridden by contained concept props
    public int modifiedIndex() {
        return getPropertyIndex();
    }

    public String getName() {
        return getMetaProperty().getName();
    }
    public int getHistoryPolicy() {
        return getMetaProperty().getHistoryPolicy();
    }

    public MetaProperty getMetaProperty() {
        return getParentConceptImpl().getMetaProperty(getPropertyIndex());
    }

    abstract public void fillXiNode(XiNode node, boolean changeOnly);

    abstract protected Object getOwner();

    abstract public PropertyImpl copy(Object newOwner);

    protected ConceptImpl getParentConceptImpl() {
        return (ConceptImpl)getParent();
    }

    protected boolean setConceptModified() {
        return (getParentConceptImpl()).modifyConcept(this);
    }

    protected void nullParent(ConceptImpl instance) {
        instance.nullParent();  //re-direct this method for in herit property, can't access nullParent from simple impl
    }

    public ConceptImpl resolveConceptPointer(ConceptOrReference conceptOrReference, Class refClass) {
        if(conceptOrReference instanceof Reference)
            return (getParentConceptImpl()).
                    deReference((Reference) conceptOrReference, refClass);
        else
            return (ConceptImpl) conceptOrReference;
    }
}
