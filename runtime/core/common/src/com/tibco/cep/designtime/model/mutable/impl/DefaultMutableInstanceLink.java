/**
 * User: ishaan
 * Date: Apr 26, 2004
 * Time: 11:51:34 AM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.util.List;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.InstanceReference;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableInstanceReference;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableMutableInstanceView;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableInstanceLink extends AbstractMutableEntityLink {


    protected String m_propertyName;
    protected int m_index;


    public DefaultMutableInstanceLink(DefaultMutableOntology ontology, DefaultMutableMutableInstanceView view, DefaultMutableInstanceReference from, DefaultMutableInstanceReference to, String propertyName, int index) throws ModelException {
        super(ontology, view, from, to);
        m_propertyName = propertyName;
        m_index = index;
    }


    public void delete() {
        final MutableInstance from = (MutableInstance) ((InstanceReference) getFrom()).getInstance();
        if (isValid(false) && from != null) {
            from.deletePropertyInstance((MutablePropertyInstance) getEntity());
        }
        removeFromOwner();
    }


    public boolean isValid(boolean recurse) {
        boolean isValid = false;

        InstanceReference fromRef = (InstanceReference) getFrom();
        InstanceReference toRef = (InstanceReference) getTo();
        if (fromRef == null || toRef == null) {
            return false;
        }

        Instance fromInstance = fromRef.getInstance();
        Instance toInstance = toRef.getInstance();
        if (fromInstance == null || toInstance == null) {
            return false;
        }

        MutablePropertyInstance pi = (MutablePropertyInstance) getEntity();
        if (pi == null) {
            isValid = false;
        } else {
            String value = pi.getValue();
            if (toInstance.getFullPath().equals(value)) {
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return isValid;
    }


    public String getLabel() {
        StringBuffer label = new StringBuffer(m_propertyName);
        label.append('[').append(m_index).append(']');
        return label.toString();
    }


    public void setPropertyName(String propertyName) {
        m_propertyName = propertyName;
    }


    public String getPropertyName() {
        return m_propertyName;
    }


    public Entity getEntity() {
        String fromPath = m_from.getEntityPath();
        Instance instance = m_ontology.getInstance(fromPath);

        List l = instance.getPropertyInstances(m_propertyName);
        return (Entity) l.get(m_index);
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "instanceLink");
        root.setAttributeStringValue(ExpandedName.makeName("propertyName"), m_propertyName);
        root.setAttributeStringValue(ExpandedName.makeName("index"), String.valueOf(m_index));

        return root;
    }
}
