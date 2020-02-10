/**
 * User: ishaan
 * Date: Apr 26, 2004
 * Time: 9:14:06 AM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.Collection;
import java.util.Iterator;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConceptReference;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntityReference;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableConceptReference extends AbstractMutableEntityReference implements MutableConceptReference {


    public DefaultMutableConceptReference(DefaultMutableOntology ontology, DefaultMutableConceptView view, String entityPath) {
        super(ontology, view, entityPath);
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = factory.createElement(ExpandedName.makeName("conceptReference"));
        super.toXiNode(root);
        return root;
    }


    public Concept getConcept() {
        if (m_ontology == null) {
            return null;
        }
        return m_ontology.getConcept(m_entityPath);
    }


    public Collection refreshLinks() throws ModelException {
        super.refreshLinks();

        Concept c = getConcept();
        if (c == null) {
            return m_links.values();
        }

        /* Get Inheritance links */
        addInheritanceLink(c);

        /* Get Property links */
        addPropertyLinks(c);

        return m_links.values();
    }


    protected void addInheritanceLink(Concept c) throws ModelException {
        DefaultMutableConceptLink link = (DefaultMutableConceptLink) m_links.remove(DefaultMutableConceptLink.INHERITANCE_LINK_LABEL);

        Concept superConcept = c.getSuperConcept();
        if (superConcept == null) {
            return;
        }

        DefaultMutableConceptReference superReference = (DefaultMutableConceptReference) m_view.getReference(superConcept.getFullPath());
        if (superReference == null) {
            return;
        }

        /* If the link doesn't exist, or is not valid, we create a new one for Inheritance */
        if (link == null || !link.isValid(false)) {
            link = new DefaultMutableConceptLink(m_ontology, (DefaultMutableConceptView) m_view, this, superReference, null);
        }

        m_links.put(link.getLabel(), link);
    }


    protected void addPropertyLinks(Concept c) throws ModelException {
        Collection defs = c.getLocalPropertyDefinitions();
        Iterator defsIt = defs.iterator();
        while (defsIt.hasNext()) {
            PropertyDefinition def = (PropertyDefinition) defsIt.next();
            if ((def.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPT) && (def.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE))
            {
                continue;
            }

            DefaultMutableConceptLink link = (DefaultMutableConceptLink) m_links.remove(def.getName());

            /* See if the Concept for this PropertyDefinition's type exists */
            Concept cType = def.getConceptType();
            if (cType == null) {
                continue;
            }

            /* See if there is a reference on this Reference's View for that Concept type */
            DefaultMutableConceptReference cTypeReference = (DefaultMutableConceptReference) m_view.getReference(cType.getFullPath());

            /* If the link doesn't exist, it exists on the view, then create the link */
            if ((link == null) && (cTypeReference != null)) {
                link = new DefaultMutableConceptLink(m_ontology, (DefaultMutableConceptView) m_view, this, cTypeReference, def.getName());
            }

            /* If the link does not exist, or it exists and is not valid, we forget about it */
            else if ((link == null) || !link.isValid(false)) {
                continue;
            }

            /* Now, we've either created a link, or it was already valid, so we add it*/
            m_links.put(link.getLabel(), link);
        }
    }
}
