package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeGroupDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUse;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDPropertyProvider;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.CurrentObjectCounter;

/**
 * @
 *
 */
public class SynXSDPropertyProvider implements ISynXSDPropertyProvider {

	public static final Logger LOGGER = Logger.getLogger(SynXSDPropertyProvider.class.getName());

    private List<ISynXSDAttributeUse> attributeUses;

    /**
     * A Map to hold both attributes and attribute groups
     */
    protected Map<String, Object> attributeMap;

    public SynXSDPropertyProvider() {
        super();
    }

    /**
     * @return Returns the attributeUses.
     */
    public List<ISynXSDAttributeUse> getPropertyUses() {
        return attributeUses;
    }

    /**
     * @param attributeUses
     *            The attributeUses to set.
     */
    @SuppressWarnings("unused")
    private void setAttributeUses(List<ISynXSDAttributeUse> attributeUses) {
        this.attributeUses = attributeUses;
    }

    public void addPropertyUse(ISynXSDAttributeUse attributeUse) {
        if (null == attributeUses) {
            attributeUses = new ArrayList<ISynXSDAttributeUse>();
        }
        attributeUses.add(attributeUse);
    }

    public void removePropertyUse(ISynXSDAttributeUse attributeUse) {
        if (null != attributeUses) {
            attributeUses.remove(attributeUse);
        }
    }

    public List<ISynXSDAttributeDeclaration> getProperties() {
        List<ISynXSDAttributeDeclaration> attrList = new ArrayList<ISynXSDAttributeDeclaration>();
        if (null != attributeMap) {

            /*
             * Cycle through the map
             */
            for (Iterator<Object> iter = attributeMap.values().iterator(); iter.hasNext();) {
                Object obj = iter.next();

                /*
                 * If it's an attribute declaration add it's concrete root
                 */
                if (obj instanceof ISynXSDAttributeDeclaration) {
                    ISynXSDAttributeDeclaration attr = (ISynXSDAttributeDeclaration) obj;
                    attrList.add(getConcreteAttribute(attr));
                }

                /*
                 * If it's a group then add the concrete root for each of the
                 * attribute in the group
                 */
                else if (obj instanceof ISynXSDAttributeGroupDefinition) {
                    ISynXSDAttributeGroupDefinition group = (ISynXSDAttributeGroupDefinition) obj;
                    for (Iterator<ISynXSDAttributeDeclaration> iterator = group.getProperties().iterator(); iterator.hasNext();) {
                        ISynXSDAttributeDeclaration attr = iterator.next();
                        attrList.add(getConcreteAttribute(attr));
                    }
                }
            }
        }
        return attrList;
    }

    public List<String> getPropertyNames() {
        List<String> propertyNames = new ArrayList<String>();
        try {
            for (Iterator<ISynXSDAttributeDeclaration> iter = getProperties().iterator(); iter.hasNext();) {
                ISynXSDAttributeDeclaration property = (ISynXSDAttributeDeclaration) iter.next();
                propertyNames.add(property.getName());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return propertyNames;
    }

    /**
     * Public convenience method for retrieving concrete attribute groups in
     * case of ref usage
     *
     * @param attribute
     * @return
     */
    public ISynXSDAttributeDeclaration getConcreteAttribute(ISynXSDAttributeDeclaration attribute) {
        if (null == attribute) {
            throw new IllegalArgumentException("attribute can not be null");
        }
        /*
         * If it has no ref then it is a concrete attribute already
         */
        if (null == attribute.getRef()) {
            return attribute;
        }

        /*
         * If the ref points to an attribute that is also a ref then traverse
         * through until a concrete attribute is found
         */
        ISynXSDAttributeDeclaration ref = attribute.getRef();
        while (null != ref.getRef()) {
            ref = ref.getRef();
        }

        return ref;
    }

    public ISynXSDAttributeDeclaration getProperty(String attributeName) {
        return (ISynXSDAttributeDeclaration) attributeMap.get(attributeName);

        //KHAI: cut out the iteration because the properties are already stored
        // in the map with their names as keys
        // This works only if we do not implement the use of property groups;
        // with property groups we would have to revert back to the original
        // code because it uses getProperties() which flattens out the groups.

        //        List attList = getProperties();
        //        if (null != attList && false == attList.isEmpty()) {
        //            for (Iterator iter = attList.iterator(); iter.hasNext();) {
        //                ISynXSDAttributeDeclaration attr = (ISynXSDAttributeDeclaration)
        // iter.next();
        //                if (true == attr.getName().equals(attributeName)) {
        //                    return attr;
        //                }
        //            }
        //        }
        //        return null;
    }

    public void addProperty(ISynXSDAttributeDeclaration attribute) {
        if (null == attributeMap) {
            attributeMap = new HashMap<String, Object>();
        }
        attributeMap.put(attribute.getName(), attribute);
    }

    public void addProperty(LocalElement parent, ISynXSDAttributeDeclaration attribute) {
        if (null == attributeMap) {
            attributeMap = new TreeMap<String, Object>();
        }

        if (attribute instanceof SynProperty) {
            ((SynProperty) attribute).setParent(parent);
        }
        attributeMap.put(attribute.getName(), attribute);
        CurrentObjectCounter.increment("Property");
    }

    public void removeProperty(String attributeName) {
        if (attributeMap.containsKey(attributeName)) {
            attributeMap.remove(attributeName);
            CurrentObjectCounter.decrement("Property");
        }
    }

    public List<ISynXSDAttributeGroupDefinition> getAttributeGroups() {
        List<ISynXSDAttributeGroupDefinition> groupList = new ArrayList<ISynXSDAttributeGroupDefinition>();
//        if (null != attributeMap) {
//
//            /*
//             * Cycle through the map
//             */
//            for (Iterator iter = attributeMap.values().iterator(); iter.hasNext();) {
//                Object obj = iter.next();
//
//                /*
//                 * Only if it's a group then add the concrete root for each of
//                 * the group
//                 */
//                if (obj instanceof ISynXSDAttributeGroupDefinition) {
//                    ISynXSDAttributeGroupDefinition group = (ISynXSDAttributeGroupDefinition) obj;
//                    for (Iterator iterator = group.getProperties().iterator(); iterator.hasNext();) {
//                        ISynXSDAttributeDeclaration attr = (ISynXSDAttributeDeclaration) iterator.next();
//                        groupList.add(getConcreteAttribute(attr));
//                    }
//                }
//            }
//        }
        return groupList;
    }

    public ISynXSDAttributeGroupDefinition getAttributeGroup(String attributeGroupName) {
        if (attributeMap.containsKey(attributeGroupName)) {
            return (ISynXSDAttributeGroupDefinition) attributeMap.get(attributeGroupName);
        }
        return null;
    }

    public void addAttributeGroup(ISynXSDAttributeGroupDefinition attributeGroup) {
        if (null == attributeMap) {
            attributeMap = new HashMap<String, Object>();
        }
        attributeMap.put(attributeGroup.getName(), attributeGroup);
    }

    public void removeAttributeGroup(String attributeGroupName) {
        if (attributeMap.containsKey(attributeGroupName)) {
            attributeMap.remove(attributeGroupName);
        }
    }

}
