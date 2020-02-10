package com.tibco.cep.studio.core.adapters.mutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.adapters.PropertyDefinitionAdapter;

public class MutablePropertyDefinitionAdapter extends MutableEntityAdapter<com.tibco.cep.designtime.core.model.element.PropertyDefinition> 
	implements	PropertyDefinition {
	
	protected static final String INTERNAL_REPRESENTATION_SEPARATOR_STRING = ":";

//    protected int m_type;
//    protected String m_conceptType;
//    protected boolean m_isArray;
//    protected Concept m_owner;
//    protected int m_historyPolicy;
//    protected int m_historySize;
//    protected String m_defValue;
//    protected LinkedHashSet m_instances;
//    protected int m_order;

    /**
     * RDF specific fields
     */
//    protected String m_super;
//    protected LinkedHashSet m_equivalenceSet;
//    protected boolean m_isTransitive;
//    protected LinkedHashSet m_disjointSet;
//    
    public static final List<PropertyDefinition> m_arrayAttributes = new ArrayList<PropertyDefinition>();
    public static final List<PropertyDefinition> m_atomAttributes = new ArrayList<PropertyDefinition>();

    private static final String LENGTH_ATTRIB_NAME = "length";
    private static final String IS_SET_ATTRIB_NAME = "isSet";

//    com.tibco.cep.designtime.core.model.element.PropertyDefinition mutableObj;
    

//    static {
//        m_arrayAttributes.add(new CGMutablePropertyDefinitionAdapter(null, LENGTH_ATTRIB_NAME, null, false, RDFTypes.INTEGER_TYPEID, null, HISTORY_POLICY_CHANGES_ONLY, 0, null, 0));
//
//        m_atomAttributes.add(new CGMutablePropertyDefinitionAdapter(null, IS_SET_ATTRIB_NAME, null, false, RDFTypes.BOOLEAN_TYPEID, null, HISTORY_POLICY_CHANGES_ONLY, 0, null, 0));
//    }
    
    public MutablePropertyDefinitionAdapter(Ontology ontology, String name, Concept owner, boolean isArray, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, String defValue, int order) {    	 
        this(ontology, name, owner, isArray, typeFlag, conceptTypePath, historyPolicy, historySize, defValue, order, "");
    }


    public MutablePropertyDefinitionAdapter(Ontology ontology, String name, Concept owner, boolean isArray, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, String defValue, int order, String superPath) {
//        super(ontology, null, name);
//        m_owner = owner;
//        m_isArray = isArray;
//        m_type = typeFlag;
//        m_conceptType = conceptTypePath;
//        m_historyPolicy = historyPolicy;
//        m_historySize = historySize;
//        m_defValue = (defValue == null) ? "" : defValue;
//        m_order = order;
//        m_instances = new LinkedHashSet();
//        m_super = (null == superPath) ? "" : superPath;
//        m_isTransitive = false;
//        m_equivalenceSet = new LinkedHashSet();
//        m_disjointSet = new LinkedHashSet();
    	  this(ElementFactory.eINSTANCE.createPropertyDefinition(),ontology);
    	  adapted.setName(name);
    	  if(owner != null) {
    		  adapted.setOwnerPath(owner.getFullPath());
    	  }
    	  adapted.setArray(isArray);
    	  adapted.setType(PROPERTY_TYPES.get(typeFlag));
    	  adapted.setConceptTypePath(conceptTypePath);
    	  adapted.setHistoryPolicy(historyPolicy);
    	  adapted.setHistorySize(historySize);
    	  adapted.setDefaultValue((defValue == null) ? "" : defValue);
    	  adapted.setOrder(order);
    	  adapted.setSuper((null == superPath) ? "" : superPath);
    	  adapted.setTransitive(false); 
    	  emfOntology = ontology;
    }
    
    public MutablePropertyDefinitionAdapter(com.tibco.cep.designtime.core.model.element.PropertyDefinition adapted,Ontology o) {
    	super(adapted,o);
    }
    
    public MutablePropertyDefinitionAdapter(PropertyDefinition pd) {
//      super(ontology, null, name);
//      m_owner = owner;
//      m_isArray = isArray;
//      m_type = typeFlag;
//      m_conceptType = conceptTypePath;
//      m_historyPolicy = historyPolicy;
//      m_historySize = historySize;
//      m_defValue = (defValue == null) ? "" : defValue;
//      m_order = order;
//      m_instances = new LinkedHashSet();
//      m_super = (null == superPath) ? "" : superPath;
//      m_isTransitive = false;
//      m_equivalenceSet = new LinkedHashSet();
//      m_disjointSet = new LinkedHashSet();
      this(null,pd.getOntology());
  	  adapted = ElementFactory.eINSTANCE.createPropertyDefinition();
  	  adapted.setOwnerPath(pd.getFullPath());
  	  adapted.setArray(pd.isArray());
  	  adapted.setType(PROPERTY_TYPES.get(pd.getType()));
  	  adapted.setConceptTypePath(pd.getConceptTypePath());
  	  adapted.setHistoryPolicy(pd.getHistoryPolicy());
  	  adapted.setHistorySize(pd.getHistorySize());
  	  adapted.setDefaultValue(pd.getDefaultValue());
  	  adapted.setOrder(pd.getOrder());
//  	  mutableObj.setSuper(pd.get(null == superPath) ? "" : superPath);
  	  adapted.setTransitive(pd.isTransitive()); 
//  	  emfOntology = pd.getOntology();
  }
    
    

	@Override
	public PropertyDefinition getAttributeDefinition(String attributeName) {
//		return m_isArray ? matchAttribute(attributeName, m_arrayAttributes) : matchAttribute(attributeName, m_atomAttributes);
		for(com.tibco.cep.designtime.core.model.element.PropertyDefinition prop:adapted.getAttributeDefinitions()) {
			if(prop.getName().equals(attributeName)) {
				try {
					return CoreAdapterFactory.INSTANCE.createAdapter(prop, emfOntology);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
//	/**
//     * @param attributeName
//     * @param list
//     * @return a PropertyDefinition
//     */
//    private PropertyDefinition matchAttribute(String attributeName, List<PropertyDefinition> list) {
//        for (int i = 0; i < list.size(); i++) {
//            PropertyDefinition prop = list.get(i);
//            if (prop.getName().equals(attributeName)) {
//                return prop;
//            }
//        }
//        return null;
//    }

	@Override
	public Collection getAttributeDefinitions() {
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> defs = adapted.getAttributeDefinitions();
		ArrayList list = new ArrayList<PropertyDefinition>();
		for(com.tibco.cep.designtime.core.model.element.PropertyDefinition prop:adapted.getAttributeDefinitions()) {
			try {
				list.add(CoreAdapterFactory.INSTANCE.createAdapter(prop, emfOntology));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
//		if (m_isArray) {
//            if (m_arrayAttributes.contains(getName())) {
//                return null;
//            }
//            return m_arrayAttributes;
//        } else {
//            if (m_atomAttributes.contains(getName())) {
//                return null;
//            }
//            return m_atomAttributes;
//        }
	}

	@Override
	public Concept getConceptType() {
//		 if ((m_conceptType != null) && (m_conceptType.length() > 0)) {
//	            return m_ontology.getConcept(m_conceptType);
//	        } else {
//	            return null;
//	        }
		 String path = adapted.getConceptTypePath();
		 return getOntology().getConcept(path);
	}

	@Override
	public String getConceptTypePath() {
//		return m_conceptType;
		return adapted.getConceptTypePath();
	}

	@Override
	public String getDefaultValue() {
//		return m_defValue;
		return adapted.getDefaultValue();
	}

	@Override
	public Collection getDisjointSet() {
//		Iterator it = m_disjointSet.iterator();
//        return buildPropertyDefinitionList(it);
		return adapted.getDisjointSet();
	}
	
//	protected Collection buildPropertyDefinitionList(Iterator internalFormIterator) {
//        ArrayList list = new ArrayList();
//
//        while (internalFormIterator.hasNext()) {
//            String internalRep = (String) internalFormIterator.next();
//            PropertyDefinition pd = parseInternalRepresentation(m_ontology, internalRep);
//            if (pd != null) {
//                list.add(pd);
//            }
//        }
//
//        return list;
//    }
	
//	protected static PropertyDefinition parseInternalRepresentation(Ontology ontology, String rep) {
//        if (rep == null || rep.equals("")) {
//            return null;
//        }
//        PropertyDefinition dpd = null;
//
//        String[] parts = rep.split(INTERNAL_REPRESENTATION_SEPARATOR_STRING);
//        if (parts == null || parts.length != 2) {
//            return null;
//        }
//
//        Concept c = ontology.getConcept(parts[0]);
//        if (c == null) {
//            return null;
//        }
//
//        dpd = (PropertyDefinition) c.getPropertyDefinition(parts[1], true);
//        return dpd;
//    }

//	@Override
	public Collection getEquivalentProperties() {
//		Iterator it = m_equivalenceSet.iterator();
//        return buildPropertyDefinitionList(it);
		return adapted.getEquivalentProperties();
	}
	
	public String getFullPath() {
//        return "";
		return adapted.getFullPath();
    }

	@Override
	public int getHistoryPolicy() {
//		 return m_historyPolicy;
		return adapted.getHistoryPolicy();
	}

	@Override
	public int getHistorySize() {
//		return m_historySize;
		return adapted.getHistorySize();
	}

	@Override
	public Set getInstances() {
//		return this.m_instances;
//		return new LinkedHashSet(adapted.getInstances());
		return Collections.emptySet();
	}

	@Override
	public Concept getOwner() {
//		return m_owner;
		String path = adapted.getOwnerPath();
		return getOntology().getConcept(path);
	}

	@Override
	public PropertyDefinition getParent() {
//		return parseInternalRepresentation(m_ontology, m_super);
		return new PropertyDefinitionAdapter(adapted.getParent(),getOntology());
	}

	@Override
	public int getType() {
//		return m_type;
		return adapted.getType().getValue();
	}

	@Override
	public boolean isA(PropertyDefinition propertyDefinition) {
		return false;
	}

	@Override
	public boolean isArray() {
//		return m_isArray;
		return adapted.isArray();
	}

	@Override
	public boolean isDisjointFrom(PropertyDefinition pd) {
//		if (pd == null || pd == this) {
//            return false;
//        }
//
//        String internalRep = toInternalRepresentation(pd);
//
//        return m_disjointSet.contains(internalRep);
		if (pd == null || pd == this) {
            return false;
        }

        String internalRep = toInternalRepresentation(pd);

        return adapted.getDisjointSet().contains(internalRep);
	}
	
	protected String toInternalRepresentation(PropertyDefinition pd) {
        return pd.getOwner().getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + pd.getName();
    }
	
	protected String toInternalRepresentation() {
//		return m_owner.getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + m_name;
        return adapted.getOwner().getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + adapted.getName();
    }

	@Override
	public boolean isSameAs(PropertyDefinition pd) {
//		if (pd == null) {
//            return false;
//        }
//        if (pd == this) {
//            return true;
//        }
//
//        String internalRep = toInternalRepresentation(pd);
//
//        return this.m_equivalenceSet.contains(internalRep);
		if (pd == null) {
            return false;
        }
        if (pd == this) {
            return true;
        }
        String internalRep = toInternalRepresentation(pd);
		return adapted.getEquivalentProperties().contains(internalRep);
	}

	@Override
	public boolean isTransitive() {
//		return m_isTransitive;
		return adapted.isTransitive();
	}
	
	public void setDefaultValue(String value) {
//        if (value == null) {
//            value = "";
//        }
//        if (value.equals(m_defValue)) {
//            return;
//        }
//        m_defValue = value;
		adapted.setDefaultValue(value);

    }
	
//	protected void modifySets(Set mSet, Set otherSet, PropertyDefinition dpd, boolean add) {
//        String internalRep = toInternalRepresentation(dpd);
//        if (add) {
//            mSet.add(internalRep);
//            otherSet.add(this);
//        } else {
//            mSet.remove(internalRep);
//            otherSet.remove(this);
//        }
//    }
	
//	public void setDisjointFrom(PropertyDefinition pd, boolean isDisjointFrom) {
//        if (pd == null) {
//            return;
//        }
//
//        Set mSet = m_disjointSet;
//        Set otherSet = ((CGMutablePropertyDefinitionAdapter)pd).m_disjointSet;
//
//        modifySets(mSet, otherSet, pd, isDisjointFrom);
//    }
	
//	public static Map<String, Object> getDefaultExtendedProperties() {
//        final Map<String, Object> extendedProperties = new LinkedHashMap<String, Object>();
//        final Map<String, Object> backingStoreProperties = new LinkedHashMap<String, Object>();
//        backingStoreProperties.put("Column Name", "");
//        backingStoreProperties.put("Max Length", "");
//        backingStoreProperties.put("Nested Table Name", "");        
//
//        extendedProperties.put("Backing Store", backingStoreProperties);
//        return extendedProperties;
//    }
	
	public void setExtendedProperties(Map props) {
//        if (null == props) {
//            this.m_extendedProperties = getDefaultExtendedProperties();
//        } else {
//            this.m_extendedProperties = props;
//        }
//
//        HashMap bs = (HashMap) m_extendedProperties.get(EXTPROP_PROPERTY_BACKINGSTORE);
//        if (bs == null) {
//            bs = new HashMap();
//            m_extendedProperties.put(EXTPROP_PROPERTY_BACKINGSTORE, bs);
//        }
//
//        String columnName= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
//        if (columnName == null) {
//            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, "");
//        }
//
//        String maxLength= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
//        if (maxLength == null) {
//            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH, "");
//        }
//
//        String nestedTableName= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME);
//        if (nestedTableName == null) {
//            bs.put(EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME, "");
//        }

//        super.setExtendedProperties(props);    //To change body of overridden methods use File | Settings | File Templates.
    }
	
	public void setFolder(Folder folder) {
		setFolder(folder);
    }
	
	public void setFolderPath(String fullPath) throws ModelException {
		setFolderPath(fullPath);
	}
	
	public void setHiddenProperties(LinkedHashMap hiddenProperties) {
//        m_hiddenProperties = hiddenProperties;
    }
	
	public void setHistoryPolicy(int policy) {
//        if (policy < HISTORY_POLICY_CHANGES_ONLY) {
//            return;
//        }
//        if (policy >= HISTORY_POLICIES.length) {
//            return;
//        }
//        m_historyPolicy = policy;
		adapted.setHistoryPolicy(policy);

    }
	
	public void setHistorySize(int size) {
//        if (size == m_historySize) {
//            return;
//        }
//        if (size < MIN_HISTORY_SIZE) {
//            size = MIN_HISTORY_SIZE;
//        }
//        m_historySize = size;
		adapted.setHistorySize(size);

    }
	
	public void setIsArray(boolean isArray) {
//        if (isArray == m_isArray) {
//            return;
//        }
//        m_isArray = isArray;
		adapted.setArray(isArray);

    }
	
	public void setName(String name, boolean renameOnConflict) {
		adapted.setName(name);
////        BEModelBundle bundle = BEModelBundle.getBundle();
//
////        if (name == null || name.length() == 0) {
////            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
////            throw new ModelException(msg);
////        }
//        if (name.equals(m_name)) {
//            return;
//        }
//
//        /** Make sure that a PropertyDefinition with the new name doesn't exist under the owning Concept **/
////        if (m_owner.m_properties.containsKey(name)) {
////            if (renameOnConflict) {
////                DEFAULT_VALIDATOR.setDefinition(this);
////                name = UniqueNamer.generateUniqueName(name, DEFAULT_VALIDATOR);
////            } else {
//////                String msg = bundle.formatString(NAME_CONFLICT_KEY, name, m_owner.getFullPath());
//////                throw new ModelException(msg);
////            }
////        }
//
//        /** Re-register with owning Concept under the new name **/
////        m_owner.m_properties.remove(m_name);
////        m_owner.m_properties.put(name, this);
////        m_owner.notifyListeners();
////        m_owner.notifyOntologyOnChange();
//
//        /** Re-register with m_conceptType **/
////        DefaultMutableConcept dcType = (DefaultMutableConcept) m_ontology.getConcept(m_conceptType);
////
////        /** Should not be null */
////        if (dcType != null) {
////            String ownerPath = m_owner.getFullPath();
////            dcType.modifyReferringProperty(ownerPath, m_name, name);
////        }
////
////        /** Re-register instances of this definition under the new name **/
////        Collection instances = m_owner.getLocalInstancesPaths();
////        Iterator instancesIt = instances.iterator();
////
////        while (instancesIt.hasNext()) {
////            DefaultMutableInstance instance = (DefaultMutableInstance) instancesIt.next();
////
////            Collection propInstances = (Collection) instance.m_properties.remove(m_name);
////            instance.m_properties.put(name, propInstances);
////        }
//
//        m_name = name;
    }
	
	public void setOwner(Concept owner) throws ModelException {
		adapted.setOwnerPath(owner.getFullPath());
    }
	
	public void setParent(PropertyDefinition parent) throws ModelException {
//        if (parent == null) {
//            m_super = "";
//        } else {
//            m_super = toInternalRepresentation(parent);
//        }
//		mutableObj.setParent(parent);
		throw new UnsupportedOperationException();
		
    }
	
	public void setParent(String parent) {
//        m_super = parent;
		throw new UnsupportedOperationException();
    }
	
	public void setSameAs(PropertyDefinition pd, boolean isSameAs) {
//        if (pd == null) {
//            return;
//        }
//
//        Set mSet = m_equivalenceSet;
//        Set otherSet = ((CGMutablePropertyDefinitionAdapter)pd).m_equivalenceSet;
//
//        modifySets(mSet, otherSet, pd, isSameAs);
		throw new UnsupportedOperationException();
    }
	
	public void setTransitive(boolean isTransitive) {
//        m_isTransitive = isTransitive;
		adapted.setTransitive(isTransitive);
    }
	
	public void setType(int type, String conceptTypePath) throws ModelException {
//        modify(type, conceptTypePath, m_historyPolicy, m_historySize, m_isArray, m_defValue);
		adapted.setType(PROPERTY_TYPES.get(type));
		adapted.setConceptTypePath(conceptTypePath);
    }
	
//	public void modify(int typeFlag, String conceptTypePath, int historyPolicy, int historySize, boolean isArray, String defaultValue) throws ModelException {
//        if (m_ontology == null) {
//            return;
//        }
//        if (historySize < MIN_HISTORY_SIZE) {
//            return;
//        }
//        if (defaultValue == null) {
//            defaultValue = "";
//        }
//
//        Concept owner = m_owner;
//        String ownerPath = owner.getFullPath();
//
//        /**
//         * If the oldConceptType exists, and is different than the newConceptType, we remove the reference.
//         * We also add the reference to the new type if applicable.
//         */
//        Concept oldConceptType = null;
//        Concept newConceptType = null;
//
//        boolean wasConcept = (getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) || (getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE);
//        if (wasConcept) {
//            oldConceptType = getConceptType();
//        }
//
//        boolean willBeConcept = (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPT) || (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE);
//        if (willBeConcept) {
//            newConceptType = m_ontology.getConcept(conceptTypePath);
//        } else {
//            conceptTypePath = "";
//        }
//
//        if (conceptTypePath == null) {
//            conceptTypePath = "";
//        }
//        boolean conceptPathChanged = (oldConceptType != newConceptType);
//
//        /** Check to see if the type has changed */
//        if ((typeFlag != m_type) || conceptPathChanged) {
//
//            if (conceptPathChanged) {
//
//                /** Make sure containment constraints are not violated */
//                if (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
//
//                    /** Don't let Concepts contain themselves, or form containment circles */
//                    if (newConceptType != null) {
//                        if (newConceptType.equals(owner) || newConceptType.contains(owner)) {
////                            BEModelBundle bundle = BEModelBundle.getBundle();
////                            String msg = bundle.formatString(DefaultMutableConcept.CONTAINMENT_CIRCLE, ownerPath, newConceptType.getFullPath());
////                            throw new ModelException(msg);
//                        }
//                    }
//                }
//
//                /** Remove referrer from old Concept */
////                if (wasConcept) {
////                    if (oldConceptType != null) {
////                        oldConceptType.removeReferringProperty(ownerPath, getName());
////                    }
////                }
////
////                /** Add referrer to new Concept */
////                if (willBeConcept) {
////                    if (newConceptType != null) {
////                        newConceptType.addReferringProperty(ownerPath, getName());
////                    }
////                }
//            }
//
//            m_type = typeFlag;
//            m_conceptType = conceptTypePath;
//        }
//
//        /** Check to see if Cardinality has changed */
//        if (isArray != isArray()) {
//            m_isArray = isArray;
//        }
//
//        /* Check to see if History Policy has changed */
//        if (historyPolicy != getHistoryPolicy()) {
//            m_historyPolicy = historyPolicy;
//        }
//
//        /** Check to see if History Size has changed */
//        if (historySize != getHistorySize()) {
//            m_historySize = historySize;
//        }
//
//        /** Check to see if Default Value has changed */
//        if (!defaultValue.equals(getDefaultValue())) {
//            m_defValue = defaultValue;
//        }
//
////        m_owner.setReferringRuleCompilationStatus(-1);
//    }
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		//return m_order;
		return adapted.getOrder();
	}	

	public boolean isMetricTrackingAuditField() {
		return false;
	}
}
