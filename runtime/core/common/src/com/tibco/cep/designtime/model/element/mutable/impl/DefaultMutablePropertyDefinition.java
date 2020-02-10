/**
 * User: ishaan
 * Date: Mar 31, 2004
 * Time: 3:48:28 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.UniqueNamer;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutablePropertyDefinition extends AbstractMutableEntity implements MutablePropertyDefinition {


    public static final ExpandedName PROPERTY_DEFINITION_NAME = ExpandedName.makeName("PropertyDefinition");
    public static final ExpandedName INSTANCES_NAME = ExpandedName.makeName("instances");
    public static final ExpandedName INSTANCE = ExpandedName.makeName("instance");
    public static final ExpandedName EQUIVALENTS = ExpandedName.makeName("equivalents");
    public static final ExpandedName EQUIVALENT = ExpandedName.makeName("equivalent");
    public static final ExpandedName DISJOINTS = ExpandedName.makeName("disjoints");
    public static final ExpandedName DISJOINT = ExpandedName.makeName("disjoint");
    public static final ExpandedName TRANSITIVE = ExpandedName.makeName("transitive");
    public static final ExpandedName SUPER = ExpandedName.makeName("super");
    public static final ExpandedName REFERENCE = ExpandedName.makeName("reference");
    public static final ExpandedName HIDDEN = ExpandedName.makeName("hiddenAttributes");

    public static final List m_arrayAttributes = new ArrayList();
    public static final List m_atomAttributes = new ArrayList();

    private static final String LENGTH_ATTRIB_NAME = "length";
    private static final String IS_SET_ATTRIB_NAME = "isSet";


    static {
        m_arrayAttributes.add(new DefaultMutablePropertyDefinition(null, LENGTH_ATTRIB_NAME, null, false, RDFTypes.INTEGER_TYPEID, null, HISTORY_POLICY_CHANGES_ONLY, 0, null, 0));
//        m_arrayAttributes.add(new DefaultPropertyDefinition(null,"historySize",  null, true, RDFTypes.INTEGER_TYPEID, null,0,null,0));

        m_atomAttributes.add(new DefaultMutablePropertyDefinition(null, IS_SET_ATTRIB_NAME, null, false, RDFTypes.BOOLEAN_TYPEID, null, HISTORY_POLICY_CHANGES_ONLY, 0, null, 0));
//        m_atomAttributes.add(new DefaultPropertyDefinition(null,"historySize",  null, true, RDFTypes.INTEGER_TYPEID, null,0,null,0));
    }


    protected static final String INTERNAL_REPRESENTATION_SEPARATOR_STRING = ":";

    protected int m_type;
    protected String m_conceptType;
    protected boolean m_isArray;
    protected DefaultMutableConcept m_owner;
    protected int m_historyPolicy;
    protected int m_historySize;
    protected String m_defValue;
    protected LinkedHashSet m_instances;
    protected int m_order;

    /**
     * RDF specific fields
     */
    protected String m_super;
    protected LinkedHashSet m_equivalenceSet;
    protected boolean m_isTransitive;
    protected LinkedHashSet m_disjointSet;

    protected static final PropertyDefinitionNameValidator DEFAULT_VALIDATOR = new PropertyDefinitionNameValidator(null);


    public DefaultMutablePropertyDefinition(DefaultMutableOntology ontology, String name, DefaultMutableConcept owner, boolean isArray, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, String defValue, int order) {
        this(ontology, name, owner, isArray, typeFlag, conceptTypePath, historyPolicy, historySize, defValue, order, "");
    }


    public DefaultMutablePropertyDefinition(DefaultMutableOntology ontology, String name, DefaultMutableConcept owner, boolean isArray, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, String defValue, int order, String superPath) {
        super(ontology, null, name);
        m_owner = owner;
        m_isArray = isArray;
        m_type = typeFlag;
        m_conceptType = conceptTypePath;
        m_historyPolicy = historyPolicy;
        m_historySize = historySize;
        m_defValue = (defValue == null) ? "" : defValue;
        m_order = order;
        m_instances = new LinkedHashSet();
        m_super = (null == superPath) ? "" : superPath;
        m_isTransitive = false;
        m_equivalenceSet = new LinkedHashSet();
        m_disjointSet = new LinkedHashSet();
    }


    public Set getInstances() {
        return this.m_instances;
    }


    public int getType() {
        return m_type;
    }


    public void setType(int type, String conceptTypePath) throws ModelException {
        modify(type, conceptTypePath, m_historyPolicy, m_historySize, m_isArray, m_defValue);
    }


    /**
     * @return a MutableConcept
     */
    public Concept getConceptType() {
        if ((m_conceptType != null) && (m_conceptType.length() > 0)) {
            return m_ontology.getConcept(m_conceptType);
        } else {
            return null;
        }
    }


    public String getConceptTypePath() {
        return m_conceptType;
    }


    public boolean isArray() {
        return m_isArray;
    }


    public void setIsArray(boolean isArray) {
        if (isArray == m_isArray) {
            return;
        }
        m_isArray = isArray;

        notifyListeners();
        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();
    }


    public Concept getOwner() {
        return m_owner;
    }


    public void setOwner(Concept owner) throws ModelException {
    }


    public int getHistoryPolicy() {
        return m_historyPolicy;
    }


    public void setHistoryPolicy(int policy) {
        if (policy < HISTORY_POLICY_CHANGES_ONLY) {
            return;
        }
        if (policy >= HISTORY_POLICIES.length) {
            return;
        }
        m_historyPolicy = policy;

        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();
        notifyListeners();
    }


    public int getHistorySize() {
        return m_historySize;
    }


    public void setHistorySize(int size) {
        if (size == m_historySize) {
            return;
        }
        if (size < MIN_HISTORY_SIZE) {
            size = MIN_HISTORY_SIZE;
        }
        m_historySize = size;

        notifyListeners();
        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();
    }


    public String getDefaultValue() {
        return m_defValue;
    }


    public void setDefaultValue(String value) {
        if (value == null) {
            value = "";
        }
        if (value.equals(m_defValue)) {
            return;
        }
        m_defValue = value;

        notifyListeners();
        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();
    }


    public void modify(int typeFlag, String conceptTypePath, int historyPolicy, int historySize, boolean isArray, String defaultValue) throws ModelException {
        if (m_ontology == null) {
            return;
        }
        if (historySize < MIN_HISTORY_SIZE) {
            return;
        }
        if (defaultValue == null) {
            defaultValue = "";
        }

        DefaultMutableConcept owner = m_owner;
        String ownerPath = owner.getFullPath();

        /**
         * If the oldConceptType exists, and is different than the newConceptType, we remove the reference.
         * We also add the reference to the new type if applicable.
         */
        DefaultMutableConcept oldConceptType = null;
        DefaultMutableConcept newConceptType = null;

        boolean wasConcept = (getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) || (getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE);
        if (wasConcept) {
            oldConceptType = (DefaultMutableConcept) getConceptType();
        }

        boolean willBeConcept = (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPT) || (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE);
        if (willBeConcept) {
            newConceptType = (DefaultMutableConcept) m_ontology.getConcept(conceptTypePath);
        } else {
            conceptTypePath = "";
        }

        if (conceptTypePath == null) {
            conceptTypePath = "";
        }
        boolean conceptPathChanged = (oldConceptType != newConceptType);

        /** Check to see if the type has changed */
        if ((typeFlag != m_type) || conceptPathChanged) {

            if (conceptPathChanged) {

                /** Make sure containment constraints are not violated */
                if (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {

                    /** Don't let Concepts contain themselves, or form containment circles */
                    if (newConceptType != null) {
                        if (newConceptType.equals(owner) || newConceptType.contains(owner)) {
                            BEModelBundle bundle = BEModelBundle.getBundle();
                            String msg = bundle.formatString(DefaultMutableConcept.CONTAINMENT_CIRCLE, ownerPath, newConceptType.getFullPath());
                            throw new ModelException(msg);
                        }
                    }
                }

                /** Remove referrer from old Concept */
                if (wasConcept) {
                    if (oldConceptType != null) {
                        oldConceptType.removeReferringProperty(ownerPath, getName());
                    }
                }

                /** Add referrer to new Concept */
                if (willBeConcept) {
                    if (newConceptType != null) {
                        newConceptType.addReferringProperty(ownerPath, getName());
                    }
                }
            }

            m_type = typeFlag;
            m_conceptType = conceptTypePath;
        }

        /** Check to see if Cardinality has changed */
        if (isArray != isArray()) {
            m_isArray = isArray;
        }

        /* Check to see if History Policy has changed */
        if (historyPolicy != getHistoryPolicy()) {
            m_historyPolicy = historyPolicy;
        }

        /** Check to see if History Size has changed */
        if (historySize != getHistorySize()) {
            m_historySize = historySize;
        }

        /** Check to see if Default Value has changed */
        if (!defaultValue.equals(getDefaultValue())) {
            m_defValue = defaultValue;
        }

//        m_owner.setReferringRuleCompilationStatus(-1);
        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();
    }


    public PropertyDefinition getParent() {
        return parseInternalRepresentation(m_ontology, m_super);
    }


    public void setParent(MutablePropertyDefinition parent) throws ModelException {
        if (parent == null) {
            m_super = "";
        } else {
            m_super = ((DefaultMutablePropertyDefinition) parent).toInternalRepresentation();
        }
    }

    public void setParent(String parent) {
        m_super = parent;
    }


    public boolean isA(PropertyDefinition propertyDefinition) {
        return false;
    }


    public boolean isTransitive() {
        return m_isTransitive;
    }


    public void setTransitive(boolean isTransitive) {
        m_isTransitive = isTransitive;
    }


    public boolean isSameAs(PropertyDefinition pd) {
        if (pd == null) {
            return false;
        }
        if (pd == this) {
            return true;
        }

        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) pd;
        String internalRep = dpd.toInternalRepresentation();

        return this.m_equivalenceSet.contains(internalRep);
    }


    public void setSameAs(PropertyDefinition pd, boolean isSameAs) {
        if (pd == null) {
            return;
        }
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) pd;

        Set mSet = m_equivalenceSet;
        Set otherSet = dpd.m_equivalenceSet;

        modifySets(mSet, otherSet, dpd, isSameAs);
    }


    public Collection getEquivalentProperties() {
        Iterator it = m_equivalenceSet.iterator();
        return buildPropertyDefinitionList(it);
    }


    public boolean isDisjointFrom(PropertyDefinition pd) {
        if (pd == null || pd == this) {
            return false;
        }

        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) pd;
        String internalRep = dpd.toInternalRepresentation();

        return m_disjointSet.contains(internalRep);
    }


    public void setDisjointFrom(PropertyDefinition pd, boolean isDisjointFrom) {
        if (pd == null) {
            return;
        }
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) pd;

        Set mSet = m_disjointSet;
        Set otherSet = dpd.m_disjointSet;

        modifySets(mSet, otherSet, dpd, isDisjointFrom);
    }


    public Collection getDisjointSet() {
        Iterator it = m_disjointSet.iterator();
        return buildPropertyDefinitionList(it);
    }


    public MutablePropertyInstance instantiate(MutableInstance instance) throws ModelException {
        return instance.createPropertyInstance(getName());
    }


    public static final String NAME_CONFLICT_KEY = "DefaultPropertyDefinition.setName.nameConflict";


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        /** Make sure that a PropertyDefinition with the new name doesn't exist under the owning Concept **/
        if (m_owner.m_properties.containsKey(name)) {
            if (renameOnConflict) {
                DEFAULT_VALIDATOR.setDefinition(this);
                name = UniqueNamer.generateUniqueName(name, DEFAULT_VALIDATOR);
            } else {
                String msg = bundle.formatString(NAME_CONFLICT_KEY, name, m_owner.getFullPath());
                throw new ModelException(msg);
            }
        }

        /** Re-register with owning Concept under the new name **/
        m_owner.m_properties.remove(m_name);
        m_owner.m_properties.put(name, this);
        m_owner.notifyListeners();
        m_owner.notifyOntologyOnChange();

        /** Re-register with m_conceptType **/
        DefaultMutableConcept dcType = (DefaultMutableConcept) m_ontology.getConcept(m_conceptType);

        /** Should not be null */
        if (dcType != null) {
            String ownerPath = m_owner.getFullPath();
            dcType.modifyReferringProperty(ownerPath, m_name, name);
        }

        /** Re-register instances of this definition under the new name **/
        Collection instances = m_owner.getLocalInstancesPaths();
        Iterator instancesIt = instances.iterator();

        while (instancesIt.hasNext()) {
            DefaultMutableInstance instance = (DefaultMutableInstance) instancesIt.next();

            Collection propInstances = (Collection) instance.m_properties.remove(m_name);
            instance.m_properties.put(name, propInstances);
        }

        m_name = name;
        notifyListeners();
    }


    public void serialize(OutputStream out) throws IOException {
    }


    public String getFullPath() {
        return "";
    }


    public static Map<String, Object> getDefaultExtendedProperties() {
        final Map<String, Object> extendedProperties = new LinkedHashMap<String, Object>();
        final Map<String, Object> backingStoreProperties = new LinkedHashMap<String, Object>();
        backingStoreProperties.put("Column Name", "");
        backingStoreProperties.put("Max Length", "");
        backingStoreProperties.put("Nested Table Name", "");        

        extendedProperties.put("Backing Store", backingStoreProperties);
        return extendedProperties;
    }


    public void setExtendedProperties(Map props) {
        if (null == props) {
            this.m_extendedProperties = getDefaultExtendedProperties();
        } else {
            this.m_extendedProperties = props;
        }

        HashMap bs = (HashMap) m_extendedProperties.get(EXTPROP_PROPERTY_BACKINGSTORE);
        if (bs == null) {
            bs = new HashMap();
            m_extendedProperties.put(EXTPROP_PROPERTY_BACKINGSTORE, bs);
        }

        String columnName= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
        if (columnName == null) {
            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, "");
        }

        String maxLength= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
        if (maxLength == null) {
            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH, "");
        }

        String nestedTableName= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME);
        if (nestedTableName == null) {
            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME, "");
        }

//        super.setExtendedProperties(props);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void setFolderPath(String fullPath) throws ModelException {
    }


    public void setFolder(MutableFolder folder) throws ModelException {
    }


    public String toString() {
        /** Append owner path and property name */
        StringBuffer me = new StringBuffer();
        if (m_owner != null) {
            String conceptFullPath = m_owner.getFullPath();
            if (conceptFullPath != null) {
                me.append(conceptFullPath);
                me.append('.');
            }
        }

        /** Append type information */
        me.append(m_name);
        me.append("(");
        me.append(PropertyDefinition.typeDescriptions[m_type]);

        if (!ModelUtils.IsEmptyString(m_conceptType)) {
            me.append(" ");
            me.append(m_conceptType);
        }
        me.append(")");

        /** Append array information */
        if (m_isArray) {
            me.append("[]");
        }
        me.append(" ");

        /** Append history information */
        me.append("H:");
        me.append(m_historySize);

        /** Append default value */
        me.append(" == ");
        if (m_defValue == null) {
            me.append(m_defValue);
        } else {
            me.append("\"");
            me.append(m_defValue);
            me.append("\"");
        }

        return me.toString();
    }


    public static final ExpandedName NAME_NAME = AbstractMutableEntity.NAME_NAME;
    public static final ExpandedName GUID_NAME = AbstractMutableEntity.GUID_NAME;
    public static final ExpandedName TYPE_NAME = ExpandedName.makeName("type");
    public static final ExpandedName CONCEPT_TYPE_NAME = ExpandedName.makeName("conceptType");
    public static final ExpandedName IS_ARRAY_NAME = ExpandedName.makeName("isArray");
    public static final ExpandedName POLICY_NAME = ExpandedName.makeName("policy");
    public static final ExpandedName HISTORY_NAME = ExpandedName.makeName("history");
    public static final ExpandedName VALUE_NAME = ExpandedName.makeName("value");
    public static final ExpandedName TRANSITIVE_NAME = ExpandedName.makeName("transitive");
    public static final ExpandedName SUPER_NAME = ExpandedName.makeName("super");
    public static final ExpandedName ORDER_NAME = ExpandedName.makeName("order");


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = factory.createElement(PROPERTY_DEFINITION_NAME);
        root.setAttributeStringValue(NAME_NAME, m_name);
        root.setAttributeStringValue(GUID_NAME, m_guid);
        root.setAttributeStringValue(TYPE_NAME, String.valueOf(m_type));
        root.setAttributeStringValue(CONCEPT_TYPE_NAME, m_conceptType);
        root.setAttributeStringValue(IS_ARRAY_NAME, String.valueOf(m_isArray));
        root.setAttributeStringValue(POLICY_NAME, String.valueOf(getHistoryPolicy()));
        root.setAttributeStringValue(HISTORY_NAME, String.valueOf(m_historySize));
        root.setAttributeStringValue(VALUE_NAME, m_defValue);
        root.setAttributeStringValue(TRANSITIVE_NAME, String.valueOf(m_isTransitive));
        root.setAttributeStringValue(SUPER_NAME, m_super);
        root.setAttributeStringValue(ORDER_NAME, String.valueOf(getOrder()));

        XiNode hidden = root.appendElement(HIDDEN);
        Map hiddenProperties = this.getHiddenProperties();
        Iterator hiddenKeys = hiddenProperties.keySet().iterator();
        while (hiddenKeys.hasNext()) {
            String hiddenKey = (String) hiddenKeys.next();
            Object hiddenValue = hiddenProperties.get(hiddenKey);
            XiNode hk = hidden.appendElement(ExpandedName.makeName(hiddenKey));
            hk.appendText(hiddenValue.toString());
        }


        XiNode instances = root.appendElement(INSTANCES_NAME);
        Iterator it = m_instances.iterator();
        while (it.hasNext()) {
            String path = (String) it.next();
            XiNode instanceNode = instances.appendElement(INSTANCE);
            instanceNode.setStringValue(path);
        }

        XiNode equivalents = root.appendElement(EQUIVALENTS);
        it = m_equivalenceSet.iterator();
        while (it.hasNext()) {
            String internalRep = (String) it.next();
            XiNode equivalentNode = equivalents.appendElement(EQUIVALENT);
            equivalentNode.setStringValue(internalRep);
        }

        XiNode disjoints = root.appendElement(DISJOINTS);
        it = m_disjointSet.iterator();
        while (it.hasNext()) {
            String internalRep = (String) it.next();
            XiNode disjointNode = disjoints.appendElement(DISJOINT);
            disjointNode.setStringValue(internalRep);
        }

        root.appendChild(AbstractMutableEntity.createXiNodeFromExtendedProperties(this.getExtendedProperties()));

        return root;
    }


    public void delete() {
        m_ontology.deletePropertyDefinition(this);
    }


    protected static DefaultMutablePropertyDefinition parseInternalRepresentation(Ontology ontology, String rep) {
        if (rep == null || rep.equals("")) {
            return null;
        }
        DefaultMutablePropertyDefinition dpd = null;

        String[] parts = rep.split(INTERNAL_REPRESENTATION_SEPARATOR_STRING);
        if (parts == null || parts.length != 2) {
            return null;
        }

        Concept c = ontology.getConcept(parts[0]);
        if (c == null) {
            return null;
        }

        dpd = (DefaultMutablePropertyDefinition) c.getPropertyDefinition(parts[1], true);
        return dpd;
    }


    protected String toInternalRepresentation() {
        return m_owner.getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + m_name;
    }


    protected void modifySets(Set mSet, Set otherSet, DefaultMutablePropertyDefinition dpd, boolean add) {
        String internalRep = dpd.toInternalRepresentation();
        if (add) {
            mSet.add(internalRep);
            otherSet.add(this);
        } else {
            mSet.remove(internalRep);
            otherSet.remove(this);
        }
    }


    /**
     * @return a Collection
     */
    public Collection getAttributeDefinitions() {
        if (m_isArray) {
            if (m_arrayAttributes.contains(getName())) {
                return null;
            }
            return m_arrayAttributes;
        } else {
            if (m_atomAttributes.contains(getName())) {
                return null;
            }
            return m_atomAttributes;
        }
    }


    /**
     * @param attributeName
     * @return a PropertyDefinition
     */
    public PropertyDefinition getAttributeDefinition(String attributeName) {
        return m_isArray ? matchAttribute(attributeName, m_arrayAttributes) : matchAttribute(attributeName, m_atomAttributes);
    }


    /**
     * @param attributeName
     * @param list
     * @return a PropertyDefinition
     */
    private PropertyDefinition matchAttribute(String attributeName, List list) {
        for (int i = 0; i < list.size(); i++) {
            DefaultMutablePropertyDefinition prop = (DefaultMutablePropertyDefinition) list.get(i);
            if (prop.getName().equals(attributeName)) {
                return prop;
            }
        }
        return null;
    }


    protected Collection buildPropertyDefinitionList(Iterator internalFormIterator) {
        ArrayList list = new ArrayList();

        while (internalFormIterator.hasNext()) {
            String internalRep = (String) internalFormIterator.next();
            PropertyDefinition pd = parseInternalRepresentation(m_ontology, internalRep);
            if (pd != null) {
                list.add(pd);
            }
        }

        return list;
    }


    public int getOrder() {
        return m_order;
    }


    public void setOrder(int order) {
        m_order = order;
    }

    public void setHiddenProperties(LinkedHashMap hiddenProperties) {
        m_hiddenProperties = hiddenProperties;
    }

    public boolean isMetricTrackingAuditField() {
    	return false;
    }
    
    static class PropertyDefinitionNameValidator implements UniqueNamer.NameValidator {


        protected DefaultMutablePropertyDefinition m_definition;


        public PropertyDefinitionNameValidator(DefaultMutablePropertyDefinition definition) {
            m_definition = definition;
        }


        public void setDefinition(DefaultMutablePropertyDefinition definition) {
            m_definition = definition;
        }


        public DefaultMutablePropertyDefinition getDefinition() {
            return m_definition;
        }


        public boolean isNameUnique(String name) {
            if (m_definition == null) {
                return true;
            }

            DefaultMutableConcept dc = (DefaultMutableConcept) m_definition.getOwner();
            if (dc == null) {
                return true;
            }

            return (dc.getPropertyDefinitionForName(name) == null);
        }
    }
}
