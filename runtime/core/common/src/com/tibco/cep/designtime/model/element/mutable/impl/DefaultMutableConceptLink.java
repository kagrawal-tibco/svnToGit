/**
 * User: ishaan
 * Date: Apr 26, 2004
 * Time: 11:32:10 AM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityLink;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptReference;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntityLink;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableConceptLink extends AbstractMutableEntityLink {


    public static final String INHERITANCE_LINK_LABEL = "Inherits From";

    protected String m_propertyName;
    protected boolean m_isContainment;


    public DefaultMutableConceptLink(DefaultMutableOntology ontology, DefaultMutableConceptView view, DefaultMutableConceptReference from, DefaultMutableConceptReference to, String propertyName) throws ModelException {
        super(ontology, view, from, to);
        if (propertyName == null) {
            m_type = EntityLink.INHERITANCE_TYPE;
        } else {
            m_type = EntityLink.PROPERTY_TYPE;
        }
        m_from = from;
        m_to = to;
        m_propertyName = propertyName;
    }


    public String getLabel() {
        if (m_type == EntityLink.INHERITANCE_TYPE) {
            return INHERITANCE_LINK_LABEL;
        }
        return m_propertyName;
    }


    public void setPropertyName(String propertyName) {
        m_propertyName = propertyName;
    }


    public String getPropertyName() {
        return m_propertyName;
    }


    public Entity getEntity() {
        PropertyDefinition pd = null;

        if (m_propertyName != null) {
            String conceptPath = m_from.getEntityPath();
            Concept fromConcept = m_ontology.getConcept(conceptPath);
            pd = fromConcept.getPropertyDefinition(m_propertyName, true);
        }

        return pd;
    }


    public void delete() {
        try {
            final MutableConcept from = (MutableConcept) ((ConceptReference) getFrom()).getConcept();

            if (m_type == EntityLink.INHERITANCE_TYPE) {
                if (isValid(false) && from != null) {
                    from.setSuperConcept(null);
                }
            } else if (m_type == EntityLink.PROPERTY_TYPE) {
                if (isValid(false) && from != null) {
                    from.deletePropertyDefinition(m_propertyName);
                }
            }

            removeFromOwner();
        } catch (ModelException e) {
            e.printStackTrace();
        }

    }


    public boolean isValid(boolean recurse) {
        if (!super.isValid(recurse)) {
            return false;
        }

        Concept fromConcept = (Concept) m_from.getEntity();
        if (!m_view.containsReferenceTo(fromConcept)) {
            return false;
        }

        Concept toConcept = (Concept) m_to.getEntity();
        if (!m_view.containsReferenceTo(toConcept)) {
            return false;
        }

        /** If this is a Property Link, check to see if the type of the PropertyDefinition meeting matches */
        if (EntityLink.PROPERTY_TYPE == m_type) {
            PropertyDefinition pd = fromConcept.getPropertyDefinition(m_propertyName, true);
            if (pd == null) {
                return false;
            }

            Concept conceptType = pd.getConceptType();
            if (!toConcept.equals(conceptType)) {
                return false;
            }
        }

        /** Otherwise, if this is an Inheritance Link, check to see if toConcept.getSuperConcept() matches */
        else if (EntityLink.INHERITANCE_TYPE == m_type) {
            Concept superConcept = fromConcept.getSuperConcept();
            if (!toConcept.equals(superConcept)) {
                return false;
            }
        }

        return true;
    }


    public static final ExpandedName PROPERTY_NAME = ExpandedName.makeName("propertyName");
    public static final ExpandedName PROPERTY_TYPE_NAME = ExpandedName.makeName("propertyType");


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "conceptLink");
        if (m_type == EntityLink.PROPERTY_TYPE) {
            root.setAttributeStringValue(PROPERTY_NAME, m_propertyName);

            PropertyDefinition pd = (PropertyDefinition) getEntity();
            if (pd != null) {
                m_isContainment = (pd.getType() == RDFTypes.CONCEPT_TYPEID);
            }
            root.setAttributeStringValue(PROPERTY_TYPE_NAME, String.valueOf(m_isContainment));
        }

        return root;
    }
}
