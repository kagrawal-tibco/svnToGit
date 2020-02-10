/**
 * User: ishaan
 * Date: Mar 29, 2004
 * Time: 6:45:39 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateMachineRuleSet;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableProcess extends DefaultMutableConcept implements MutableConcept, MutableRuleSet,ProcessModel {
    


    public DefaultMutableProcess(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name, String superConceptPath) {
        this(ontology, folder, name, superConceptPath, false);
    }


    public DefaultMutableProcess(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name, String superConceptPath, boolean isAScorecard) {
        super(ontology, folder, name,null);
        m_super = superConceptPath;
        m_concepts = new LinkedHashSet();
        m_instances = new LinkedHashSet();
        m_properties = new LinkedHashMap();
        m_views = new LinkedHashSet();
        m_referringPropsMap = new LinkedHashMap();
        m_isAScorecard = isAScorecard;
        m_ruleSet = new DefaultMutableStateMachineRuleSet(this, this.getFullPath(), ontology, folder, name);
    }


   


    public static DefaultMutableProcess createDefaultProcessFromNode(XiNode root) throws ModelException {
        DefaultMutableProcess dc = null;
        String folder = root.getAttributeStringValue(ExpandedName.makeName("folder"));
        String name = root.getAttributeStringValue(ExpandedName.makeName("name"));
        String description = root.getAttributeStringValue(AbstractMutableEntity.DESCRIPTION_NAME);
        String superPath = root.getAttributeStringValue(ExpandedName.makeName("super"));
        String guid = root.getAttributeStringValue(ExpandedName.makeName("guid"));
//        String iconRef = root.getAttributeStringValue(ExpandedName.makeName("iconRef"));
//        String isSC = root.getAttributeStringValue(ExpandedName.makeName("isAScorecard"));
//        String isTransient= root.getAttributeStringValue(ExpandedName.makeName("isTransient"));
//        String isAutoStartStateMachine= root.getAttributeStringValue(ExpandedName.makeName("isAutoStartStateMachine"));
//        String isPOJO= root.getAttributeStringValue(ExpandedName.makeName("isPOJO"));
//        String pojoClassName = root.getAttributeStringValue(ExpandedName.makeName("implClass"));

        /* We pass null for superPath since the super Concept may not yet exist */
        DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        DefaultMutableFolder conceptFolder = DefaultMutableOntology.createFolder(rootFolder, folder, false);

        dc = new DefaultMutableProcess(null, conceptFolder, name, "");

       
        



        // This sort of sucks, but I must know the most recently loaded Concept immediately so
        // the RuleSet can be accessed during loading of other objects
        DefaultMutableOntology.setMostRecentConcept(dc);
        dc.m_super = superPath;
        dc.setGUID(guid);
        dc.m_description = description;

        

       

        /* Add the PropertyDefinitions */
        XiNode propertyDefs = XiChild.getChild(root, PROPERTY_DEFINITIONS_NAME);
        if (propertyDefs != null) {
            Iterator it = propertyDefs.getChildren();
            while (it.hasNext()) {
                XiNode pdNode = (XiNode) it.next();
                String defName = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.NAME_NAME);
                String defGuid = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.GUID_NAME);
                String type = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.TYPE_NAME);
                String conceptType = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.CONCEPT_TYPE_NAME);
                String isArrayStr = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.IS_ARRAY_NAME);
                String policyStr = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.POLICY_NAME);
                String history = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.HISTORY_NAME);
                String value = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.VALUE_NAME);
                String orderString = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.ORDER_NAME);


                int policy = (policyStr == null) ? PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY : Integer.parseInt(policyStr);

                int order = (orderString == null) ? -1 : Integer.parseInt(orderString);
                DefaultMutablePropertyDefinition dpd = new DefaultMutablePropertyDefinition(null, defName, dc, Boolean.valueOf(isArrayStr).booleanValue(), Integer.parseInt(type), conceptType, policy, Integer.parseInt(history), value, order);

                

               


                dc.m_properties.put(defName, dpd);

                dpd.setGUID(defGuid);

               

                
            }

            /* Resolve the orders of any missing numbers. */
            int largest = -1;
            Collection props = dc.getLocalPropertyDefinitions();
            Collection reset = new ArrayList();
            it = props.iterator();
            while (it.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
                if (dpd.getOrder() > largest) {
                    largest = dpd.getOrder();
                }
                if (dpd.getOrder() == -1) {
                    reset.add(dpd);
                }
            }

            largest++;

            it = reset.iterator();
            while (it.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
                dpd.setOrder(largest);
                largest++;
            }
        }
        return dc;
    }


    public void clear() {
        m_ruleSet.clear();
    }


    public Map getAllExtendedProperties() {
        final Map props = new HashMap();
        final DefaultMutableProcess parent = (DefaultMutableProcess) this.getSuperConcept();
        if (null != parent) {
            props.putAll(parent.getAllExtendedProperties());
        }
        props.putAll(this.getExtendedProperties());
        return props;
    }


    public void setExtendedProperties(Map props) {
        super.setExtendedProperties(props);
//        if (null == props) {
//            this.m_extendedProperties = new LinkedHashMap();
//            final Map<String, Object> bsProps = new LinkedHashMap<String, Object>();
//            bsProps.put("hasBackingStore", "true");
//            bsProps.put("Table Name", "");
//            this.m_extendedProperties.put("Backing Store", bsProps);
//        } else {
//            super.setExtendedProperties(props);
//        }
    }


    /**
     * Rule name is always the full path ie StateMachine + State + <TypeofRule>
     *
     * @param name
     * @param renameOnConflict
     * @return
     * @throws ModelException
     */
    public MutableRule createRule(String name, boolean renameOnConflict, boolean isARuleFunction) throws ModelException {
        return this.m_ruleSet.createRule(name, renameOnConflict, isARuleFunction);
    }


    public void deleteRule(String name) {
        m_ruleSet.deleteRule(name);
    }


    public RulesetEntry getRule(String name) {
        return m_ruleSet.getRule(name);
    }


    public List getRules() {
        return m_ruleSet.getRules();
    }


    public StateMachineRuleSet getStateMachineRuleSet() {
        return m_ruleSet;
    }


    /* ******************** End methods used by default implementation ************ */


    public boolean isPOJO() {
        return m_isPOJO;
    }

    public void setPOJO(boolean b) {
        m_isPOJO = b;
    }

    public void setPOJOImplClassName(String clazzName) {
        m_pojoImplClass = clazzName;
    }

    public String getPOJOImplClassName() {
        return m_pojoImplClass;
    }
    
    public boolean isMetric() {
    	return false;
    }
    
    public void enableMetricTracking() {
    }
    
    public void disableMetricTracking() {
    }
    
    @Override
    public <T> T cast(Class<T> typeOf) {
    	// TODO Auto-generated method stub
    	return null;
    }


	@Override
	public int getRevision() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getOriginalAuthor() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getLastModifiedAuthor() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getDeployedDate() {
		return null;
	}


	@Override
	public <T> T getTaskElement(String name, Class<T> typeOf) {
		if(Concept.class.isAssignableFrom(typeOf)){
			return (T) this;
		}
		return null;
	}


	@Override
	public Collection<PropertyDefinition> getPropertyDefinitions() {
		// TODO Auto-generated method stub
		return m_properties.values();
	}
	
	
	public Collection getAttributeDefinitions() {
        ArrayList retAttributes = new ArrayList();
        if (this.getSuperConcept() == null) {
            fillStaticAttributes(retAttributes);
        } else {
            retAttributes.addAll(getSuperConcept().getAttributeDefinitions());
        }

        if (this.isContained()) {
            retAttributes.add(new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), "parent", this, false, RDFTypes.CONCEPT_REFERENCE_TYPEID, this.getParentConcept().getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1));
        }
        return retAttributes;
    }


    public PropertyDefinition getAttributeDefinition(String attributeName) {
    	BASE_ATTRIBUTES attr = BASE_ATTRIBUTES.valueOf(attributeName);
    	if(attr != null) {
    		return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), attr.name(), this, false, attr.getRdfTypeId(), attr == BASE_ATTRIBUTES.parent ? getFullPath() : null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
    	}
        return null;
    }
    
		@Override
	public Collection<SubProcessModel> getSubProcesses() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}
		
	@Override
	public SubProcessModel getSubProcessById(String id) {
		return null;
	}
    
}
