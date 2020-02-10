package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_CONCEPT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_DATA_TYPE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_IS_SCORECARD;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_MODIFIED_PROPERTY;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_MULTIPLE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY_INITIAL_VALUE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY_NAME;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY_NEW_VALUE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY_OLD_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.service.tester.model.InvocationObject;
import com.tibco.cep.runtime.service.tester.model.ReteChangeType;
import com.tibco.cep.runtime.service.tester.model.ReteObject;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.datamodel.XiNode;



/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 4, 2010
 * Time: 6:50:57 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class CustomConceptSerializer extends EntitySerializer<Concept> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CustomConceptSerializer.class);

    protected ReteChangeType changeType;

    /**
     * Used in modifiation case only
     */
    protected List<Property> modifiedProperties;

    private Comparator<Property> propertyComparator = new PropertyComparator();

	private XiNode conceptElement;

    public CustomConceptSerializer(Concept entity, ReteChangeType changeType) {
        super(entity);

        if (changeType == ReteChangeType.MODIFY) {
            throw new IllegalArgumentException("Illegal Constructor for this change type");
        }

        this.changeType = changeType;
    }

    public CustomConceptSerializer(Concept entity,
                                   ReteChangeType changeType,
                                   List<Property> modifiedProperties) {
        super(entity);

        this.changeType = changeType;

        this.modifiedProperties = modifiedProperties;
        
    }

    @Override
    public void serialize(XiNode documentNode) throws Exception {
        //Create a concept element
        conceptElement = factory.createElement(EX_CONCEPT);

        //Serialize base attrs
        serializeBaseAttributes(conceptElement);

        //Set name and namespace
        String namespace = entity.getExpandedName().getNamespaceURI();
        String name = entity.getExpandedName().getLocalName();

        serializeNS(conceptElement, name, namespace);

        Class<?> clazz = entity.getClass();
        try {
            clazz.getMethod(CGConstants.scorecardInstanceGetter);
            conceptElement.setAttributeStringValue(EX_IS_SCORECARD, "true");
        } catch (NoSuchMethodException nme) {
            //Do not handle.
        }

        Property[] properties = entity.getProperties();
        serializeProperties(properties, conceptElement);

        documentNode.appendChild(conceptElement);
    }

    protected void serializeProperties(Property[] properties, XiNode conceptElement) throws Exception {
        //TODO Handle refs
        LOGGER.log(Level.TRACE, "Serializing properties %s", properties.length);

        if (changeType == ReteChangeType.MODIFY) {
        	LOGGER.log(Level.TRACE, "Modification request");
            //get all modified properties
            for (Property modifiedProperty : modifiedProperties) {
            	LOGGER.log(Level.TRACE, "Dirty Property >>> %s", modifiedProperty.getName());
                serializeModifiedProperty(conceptElement, modifiedProperty, modifiedProperty instanceof PropertyArray);
            }
            //Now serialize unmodified ones
            properties = getUnmodifiedProperties(properties, modifiedProperties);
        }
        for (Property property : properties) {
            serializePropertyRegular(conceptElement, property);
        }
    }

    /**
     * @param conceptElement
     * @param property
     * @param isMultiple
     */
    private void serializeModifiedProperty(XiNode conceptElement,
                                           Property property,
                                           boolean isMultiple) {

        XiNode propertyElement = serializeBasePropertyAttrs(property, true, isMultiple);
        if (property instanceof PropertyAtom) {
            PropertyAtom propertyAtom = (PropertyAtom)property;

            if (propertyAtom.getHistorySize() > 1) {
            	XiNode oldValueElement = factory.createElement(EX_PROPERTY_OLD_VALUE);
            	Object oldValue = propertyAtom.getPreviousValue();
                if (oldValue != null) {
                	LOGGER.log(Level.TRACE, "Dirty Property Old Value >>> %s", oldValue);
                    oldValueElement.setStringValue(oldValue.toString());
                }
                propertyElement.appendChild(oldValueElement);
            }

            XiNode newValueElement = factory.createElement(EX_PROPERTY_NEW_VALUE);
            Object newValue = propertyAtom.getValue();
            LOGGER.log(Level.TRACE, "Dirty Property New Value >>> %s", propertyAtom.getValue());
            if (newValue != null) {
                newValueElement.setStringValue(newValue.toString());
            }
            propertyElement.appendChild(newValueElement);
            conceptElement.appendChild(propertyElement);
        } else if (property instanceof PropertyArray) {
            PropertyAtom[] propertyAtoms = ((PropertyArray) property).toArray();

            StringBuilder stringBuilder = new StringBuilder();

            for (PropertyAtom propertyAtom : propertyAtoms) {
                if (propertyAtom instanceof PropertyAtomContainedConcept) {
                    PropertyAtomContainedConcept pac = (PropertyAtomContainedConcept) propertyAtom;
                    //Only get the ids
                    stringBuilder.append(pac.getContainedConceptId());
                } else if (propertyAtom instanceof PropertyAtomConceptReference) {
                    PropertyAtomConceptReference par = (PropertyAtomConceptReference) propertyAtom;
                    stringBuilder.append(par.getConceptId());
                } else {
                    stringBuilder.append(propertyAtom.getValue());
                }
                stringBuilder.append(";");
            }
            String newValue = stringBuilder.toString();
//            //Set the value
//            if (propertyValue != null) {
//                propertyElement.setStringValue(propertyValue.toString());
//            }
            XiNode newValueElement = factory.createElement(EX_PROPERTY_NEW_VALUE);
            LOGGER.log(Level.TRACE, "Dirty Property New Value >>> %s", newValue);
            if (newValue != null) {
                newValueElement.setStringValue(newValue.toString());
            }
            propertyElement.appendChild(newValueElement);
            conceptElement.appendChild(propertyElement);
        }
    }

	/**
     *
     * @param conceptElement
     * @param property
     */
    private void serializePropertyRegular(XiNode conceptElement, Property property) {
        Object propertyValue = null;
        boolean multiple = false;
        if (property instanceof PropertyAtom) {

            if (property instanceof PropertyAtomDateTime) {
                PropertyAtomDateTime padt = (PropertyAtomDateTime) property;
                //Chop off the timestamp part if system property set to true
                String dateValue = padt.getString();
                //Date could be null
                if (dateValue != null) {
//                    int index = dateValue.indexOf('T');
//                    dateValue = dateValue.substring(0, index);
                    propertyValue = dateValue;
                }
            } else {
                PropertyAtom propertyAtom = (PropertyAtom) property;
                propertyValue = propertyAtom.getValue();
                LOGGER.log(Level.TRACE, "Property Name %s has value %s", property.getName(), propertyValue);
            }
        } else if (property instanceof PropertyArray) {
            multiple = true;
            PropertyAtom[] propertyAtoms = ((PropertyArray) property).toArray();

            StringBuilder stringBuilder = new StringBuilder();

            for (PropertyAtom propertyAtom : propertyAtoms) {
                if (propertyAtom instanceof PropertyAtomContainedConcept) {
                    PropertyAtomContainedConcept pac = (PropertyAtomContainedConcept) propertyAtom;
                    //Only get the ids
                    stringBuilder.append(pac.getContainedConceptId());
                } else if (propertyAtom instanceof PropertyAtomConceptReference) {
                    PropertyAtomConceptReference par = (PropertyAtomConceptReference) propertyAtom;
                    stringBuilder.append(par.getConceptId());
                } else {
                    stringBuilder.append(propertyAtom.getValue());
                }
                stringBuilder.append(";");
            }
            propertyValue = stringBuilder.toString();
        }
        XiNode propertyElement = serializeBasePropertyAttrs(property, false, multiple);
        //Set the value
        if (propertyValue != null) {
            propertyElement.setStringValue(propertyValue.toString());
        }
        conceptElement.appendChild(propertyElement);
    }

    /**
     *
     * @param property
     * @param isDirty -> Whether property is dirty or not.
     * @param isMultiple
     * @return
     */
    private XiNode serializeBasePropertyAttrs(Property property,
                                              boolean isDirty,
                                              boolean isMultiple) {
        String propertyName = property.getName();
        XiNode propertyElement =
                (isDirty) ? factory.createElement(EX_MODIFIED_PROPERTY) : factory.createElement(EX_PROPERTY);
        propertyElement.setAttributeStringValue(EX_PROPERTY_NAME, propertyName);

        if (isMultiple) {
        	LOGGER.log(Level.TRACE, "Property Name %s is of type array", propertyName);
            propertyElement.setAttributeStringValue(EX_MULTIPLE, Boolean.toString(isMultiple));
        }
        //Set data type
        String propertyDataType = getStringDataType(property);
        LOGGER.log(Level.TRACE, "Data Type for Property Name %s is %s", propertyName, propertyDataType);
        propertyElement.setAttributeStringValue(EX_DATA_TYPE, propertyDataType);

        return propertyElement;
    }


    /**
     *
     * @param allProperties
     * @param modifiedProperties
     * @return
     */
    private Property[] getUnmodifiedProperties(Property[] allProperties, List<Property> modifiedProperties) {
        Collection<Property> unmodifiedProperties = new LinkedHashSet<Property>();
        Collections.sort(modifiedProperties, propertyComparator);
        for (Property eachProperty : allProperties) {
            int index = Collections.binarySearch(modifiedProperties, eachProperty, propertyComparator);
            if (index < 0) {
                //Match not found add it
                unmodifiedProperties.add(eachProperty);
            }
        }
        return unmodifiedProperties.toArray(new Property[unmodifiedProperties.size()]);
    }

    class PropertyComparator implements Comparator<Property> {
        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p/>
         * In the foregoing description, the notation
         * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
         * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
         * <tt>0</tt>, or <tt>1</tt> according to whether the value of
         * <i>expression</i> is negative, zero or positive.<p>
         * <p/>
         * The implementor must ensure that <tt>sgn(compare(x, y)) ==
         * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>compare(x, y)</tt> must throw an exception if and only
         * if <tt>compare(y, x)</tt> throws an exception.)<p>
         * <p/>
         * The implementor must also ensure that the relation is transitive:
         * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
         * <tt>compare(x, z)&gt;0</tt>.<p>
         * <p/>
         * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
         * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
         * <tt>z</tt>.<p>
         * <p/>
         * It is generally the case, but <i>not</i> strictly required that
         * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         *
         * @param property1 the first object to be compared.
         * @param property2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         *         first argument is less than, equal to, or greater than the
         *         second.
         * @throws ClassCastException if the arguments' types prevent them from
         *                            being compared by this comparator.
         */
        public int compare(Property property1, Property property2) {
            String name1 = property1.getName();
            String name2 = property2.getName();

            if (name1.intern() == name2.intern()) {
                return 0;
            }
            //Some random logic in this
            if (name2.hashCode() > name1.hashCode()) {
                return 1;
            }
            return -1;
        }
    }

    public void updateInitialValues(ReteObject initialObject) {
    	for (Property modifiedProperty : modifiedProperties) {
    		String initVal = getInitialValue(modifiedProperty, initialObject.getInvocationObject());
    		if (initVal != null) {
    			XiNode propertyElement = getPropertyElement(modifiedProperty);
    			XiNode initValueElement = factory.createElement(EX_PROPERTY_INITIAL_VALUE);
    			LOGGER.log(Level.TRACE, "Dirty Property Initial Value >>> %s", initVal);
    			initValueElement.setStringValue(initVal);
    			propertyElement.appendChild(initValueElement);
    		}
    	}
    }
	
    private XiNode getPropertyElement(Property modifiedProperty) {
    	String propName = modifiedProperty.getName();
    	XiNode[] childNodes = XiNodeUtilities.getChildNodes(conceptElement, EX_MODIFIED_PROPERTY.getNamespaceURI(), EX_MODIFIED_PROPERTY.getLocalName());
    	for (XiNode xiNode : childNodes) {
			if (propName.equals(xiNode.getAttributeStringValue(TesterConstants.EX_NAME))) {
				return xiNode;
			}
		}
    	return null;
	}

	/**
     * Attempt to look up the initial value of this modified property to display to the user
     * @param property
     * @return
     */
    private String getInitialValue(Property property, InvocationObject invocationObject) {
    	XiNode root = invocationObject.getStartStateSnapshotNode();
    	XiNode[] conceptNodes = XiNodeUtilities.getChildNodes(root, TesterConstants.TESTER_RESULTS_NS, TesterConstants.EX_CONCEPT.localName);
    	for (XiNode xiNode : conceptNodes) {
			XiNode attribute = xiNode.getAttribute(TesterConstants.EX_NAMESPACE);
			if (super.entity.getExpandedName().namespaceURI.equals(attribute.getStringValue())) {
				// found the concept, now lookup the property value
				XiNode[] children = XiNodeUtilities.getChildren(xiNode);
				for (XiNode propNode : children) {
					String propName = propNode.getAttributeStringValue(TesterConstants.EX_NAME);
					if (property.getName().equals(propName)) {
						return propNode.getFirstChild() == null ? "" : propNode.getFirstChild().getStringValue();
					}
				}
			}
		}
		return null;
	}
}
