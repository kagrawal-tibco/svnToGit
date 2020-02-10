/**
 * User: ishaan
 * Date: Apr 2, 2004
 * Time: 3:07:52 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.io.IOException;
import java.io.OutputStream;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;


public class DefaultMutablePropertyInstance extends AbstractMutableEntity implements MutablePropertyInstance {


    protected DefaultMutableInstance m_instance;
    protected String m_definitionName;
    protected String m_value;
    protected boolean m_hasBeenSet;


    public void serialize(OutputStream out) throws IOException {
    }


    public DefaultMutablePropertyInstance(DefaultMutableOntology ontology, DefaultMutableInstance instance, String definitionName, boolean hasBeenSet) {
        super(ontology, null, null);
        m_instance = instance;
        m_definitionName = definitionName;
        m_value = "";
        m_hasBeenSet = hasBeenSet;
    }


    public boolean hasBeenSet() {
        return m_hasBeenSet;
    }


    /**
     * NOOP *
     */
    public void setName(String name, boolean renameOnConflict) throws ModelException {
    }


    /**
     * NOOP *
     */
    public String getFullPath() {
        return "";
    }


    /**
     * NOOP *
     */
    public void setFolderPath(String fullPath) throws ModelException {
    }


    /**
     * NOOP *
     */
    public void setFolder(MutableFolder folder) throws ModelException {
    }


    public Instance getInstance() {
        return m_instance;
    }


    public String getValue() {
        if (m_hasBeenSet) {
            return m_value;
        }

        Concept concept = m_instance.getConcept();
        if (concept == null) {
            return "";
        }

        PropertyDefinition pd = concept.getPropertyDefinition(m_definitionName, false);
        if (pd == null) {
            return "";
        }

        String defVal = pd.getDefaultValue();
        return defVal;
    }


    public void setValue(String value) throws ModelException {
        if (value == null) {
            this.m_value = "";
        } else {
            this.m_value = value;
        }
        this.m_hasBeenSet = true;
        this.notifyListeners();
    }


    public PropertyDefinition getPropertyDefinition() {
        Concept c = m_instance.getConcept();
        if (c == null) {
            return null;
        }

        /* The PropertyDefinition should be local to the Concept of our Instance */
        return c.getPropertyDefinition(m_definitionName, true);
    }


    public String getPropertyDefinitionName() {
        return m_definitionName;
    }


    public void delete() {
        m_ontology.deletePropertyInstance(this);
    }


    public String toString() {
        StringBuffer me = new StringBuffer("\tDefaultPropertyInstance: " + m_guid);
        me.append("\n\tInstance: " + m_instance.getFolder() + m_instance.getName() + " (" + m_instance.getGUID() + ")");
        me.append("\n\tDefinition: " + m_definitionName);
        me.append("\n\tValue: " + getValue());

        return me.toString();
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = factory.createElement(ExpandedName.makeName("propertyInstance"));
        XiAttribute.setStringValue(root, "instance", m_instance.getFullPath());
        XiAttribute.setStringValue(root, "definition", m_definitionName);
        XiAttribute.setStringValue(root, "value", getValue());
        XiAttribute.setStringValue(root, "hasBeenSet", String.valueOf(m_hasBeenSet));
        return root;
    }
}