/**
 * User: ishaan
 * Date: Apr 23, 2004
 * Time: 6:14:15 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.Collection;
import java.util.Iterator;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.mutable.MutableInstanceReference;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntityReference;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableInstanceLink;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableInstanceReference extends AbstractMutableEntityReference implements MutableInstanceReference {


    public DefaultMutableInstanceReference(DefaultMutableOntology ontology, DefaultMutableMutableInstanceView view, String entityPath) {
        super(ontology, view, entityPath);
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = factory.createElement(ExpandedName.makeName("instanceReference"));
        super.toXiNode(root);
        return root;
    }


    public Instance getInstance() {
        if (m_ontology == null) {
            return null;
        }
        return m_ontology.getInstance(m_entityPath);
    }


    public Collection refreshLinks() throws ModelException {
        super.refreshLinks();

        Instance instance = getInstance();
        if (instance == null) {
            return m_links.values();
        }

        /** Add PropertyInstance links */
        addPropertyInstanceLinks(instance);

        return m_links.values();
    }


    protected void addPropertyInstanceLinks(Instance instance) throws ModelException {
        /** Iterate through all PropertyInstance sets */
        Collection propInstanceSets = instance.getAllPropertyInstances();
        Iterator setsIt = propInstanceSets.iterator();
        while (setsIt.hasNext()) {
            /** Within each set, iterate over the PropertyInstances */
            Collection instanceSet = (Collection) setsIt.next();
            Iterator setIt = instanceSet.iterator();
            for (int i = 0; setIt.hasNext(); i++) {
                MutablePropertyInstance pi = (MutablePropertyInstance) setIt.next();
                String name = pi.getPropertyDefinitionName();
                String val = pi.getValue();

                String key = name + '[' + i + ']';
                DefaultMutableInstanceLink link = (DefaultMutableInstanceLink) m_links.get(key);

                if (link != null && link.isValid(false)) {
                    continue;
                }

                /** See if the PropertyInstance's value is for an InstanceReference within this view */
                DefaultMutableInstanceReference dir = (DefaultMutableInstanceReference) m_view.getReference(val);
                if (dir == null) {
                    continue;
                }

                /** Add the link to the sets */
                link = new DefaultMutableInstanceLink(m_ontology, (DefaultMutableMutableInstanceView) m_view, this, dir, name, i);
                m_links.put(link.getLabel(), link);
            }
        }
    }
}
