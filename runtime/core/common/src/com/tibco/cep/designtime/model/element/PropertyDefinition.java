package com.tibco.cep.designtime.model.element;


import java.util.Collection;
import java.util.Set;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:54:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyDefinition extends Entity {


    int HISTORY_POLICY_CHANGES_ONLY = com.tibco.cep.runtime.model.element.Property.HISTORY_POLICY_CHANGES_ONLY;
    int HISTORY_POLICY_ALL_VALUES = com.tibco.cep.runtime.model.element.Property.HISTORY_POLICY_ALL_VALUES;
    int[] HISTORY_POLICIES = new int[]{HISTORY_POLICY_CHANGES_ONLY, HISTORY_POLICY_ALL_VALUES};
    int MIN_HISTORY_SIZE = 0;
    // int MAX_HISTORY_SIZE = 1024;
    int MAX_HISTORY_SIZE = Short.MAX_VALUE;
    //Todo put in bundle
    String[] HISTORY_POLICY_DESCRIPTIONS = new String[]{
            "Changes Only", "All Values"
    };
    /**
     * Flag indicating a PropertyDefinition of type Concept.
     */
    int PROPERTY_TYPE_CONCEPT = RDFTypes.CONCEPT_TYPEID;
    /**
     * Flag indicating a PropertyDefinition of type String.
     */
    int PROPERTY_TYPE_STRING = RDFTypes.STRING_TYPEID;
    /**
     * Flag indicating a PropertyDefinition of type Real.
     */
    int PROPERTY_TYPE_REAL = RDFTypes.DOUBLE_TYPEID;
    /**
     * Flag indicating a PropertyDefinition of type Integer.
     */
    int PROPERTY_TYPE_INTEGER = RDFTypes.INTEGER_TYPEID;
    /**
     * Flag indicating a PropertyDefinition of type Boolean.
     */
    int PROPERTY_TYPE_BOOLEAN = RDFTypes.BOOLEAN_TYPEID;
    int PROPERTY_TYPE_LONG = RDFTypes.LONG_TYPEID;
    int PROPERTY_TYPE_DATETIME = RDFTypes.DATETIME_TYPEID;
    int PROPERTY_TYPE_CONCEPTREFERENCE = RDFTypes.CONCEPT_REFERENCE_TYPEID;
    //public final static String[] typeDescriptions = {"Concept", "String", "Real", "Integer", "Boolean"};
    String[] typeDescriptions = RDFTypes.typeStrings;


    /**
     * Returns a flag indicating the type of the PropertyDefinition.
     *
     * @return The flag indicating the type of the PropertyDefinition.
     */
    int getType();


    /**
     * @return a Set
     */
    Set getInstances();


    /**
     * If this PropertyDefinition has type Concept, that Concept is returned.
     * Otherwise, null is returned.
     *
     * @return The Concept type of this PropertyDefinition, or null if its type is a primitive.
     */
    Concept getConceptType();


    /**
     * If this PropertyDefinition has type Concept, the path of that Concept is returned, otherwise null is returned.
     *
     * @return The path of the Concept type, or null if it doesn't exist.
     */
    String getConceptTypePath();


    /**
     * Returns whether or not this PropertyDefinition has array semantics.
     *
     * @return True, if this PropertyDefinition has array semantics.
     */
    boolean isArray();


    /**
     * Returns the Concept to which this PropertyDefinition belongs/
     *
     * @return The owning Concept.
     */
    Concept getOwner();


    /**
     * Returns the policy used to keep track of the history of this PropertyDefinition.
     *
     * @return the policy used to keep track of the history of this PropertyDefinition.
     */
    int getHistoryPolicy();


    /**
     * Returns the history size for this PropertyDefinition.
     *
     * @return The history size for this PropertyDefinition.
     */
    int getHistorySize();


    /**
     * Returns the default value associated with this PropertyDefinition.
     *
     * @return The default value associated with this PropertyDefinition.
     */
    String getDefaultValue();


    /**
     * Returns the PropertyDefinition, if any, from which this PropertyDefinition inherits.
     * If this PropertyDefinition is an overriding of a PropertyDefinition belonging to the owning
     * Concept's parent, then the overridden PropertyDefinition is returned.
     *
     * @return The PropertyDefinition, if any, from which this PropertyDefinition inherits.
     */
    PropertyDefinition getParent();


    /**
     * In RDF, properties can be inherited.  This method tests whether or not
     * propertyDefinition is a super-Property of this PropertyDefinition.
     *
     * @param propertyDefinition The PropertyDefinition to test.
     * @return True if this is the same PropertyDefinition, or inherits from it, otherwise false.
     */
    boolean isA(PropertyDefinition propertyDefinition);


    /**
     * In RDF, properties can be transitive.  This method returns true if this
     * PropertyDefinition is transitive.
     *
     * @return true if this PropertyDefinition is transitive, false otherwise.
     */
    boolean isTransitive();


    /**
     * In RDF, properties can be equivalent to each other.  This method returns true
     * if this PropertyDefinition is equivalent to the provided one.
     *
     * @param pd The PropertyDefinition to test equivalence against.
     * @return true, if the two PropertyDefinitions are equivalent, false otherwise.
     */
    boolean isSameAs(PropertyDefinition pd);


    /**
     * Returns all equivalent PropertyDefinitions.
     *
     * @return A Collection containing all equivalent PropertyDefinition objects.
     */
    Collection getEquivalentProperties();


    /**
     * In RDF, properties can be disjoint from one another (mutually exclusive).  This
     * method returns true if the supplied PropertyDefinition is disjoint from the provided one.
     *
     * @param pd The PropertyDefinition to test against.
     * @return true if the two PropertyDefinitions are disjoint.
     */
    boolean isDisjointFrom(PropertyDefinition pd);


    /**
     * Returns the PropertyDefinitions from which this PropertyDefinition is disjoint.
     *
     * @return A collection containing the PropertyDefinitions from which this one is disjoint.
     */
    Collection getDisjointSet();


    Collection getAttributeDefinitions();


    PropertyDefinition getAttributeDefinition(String attributeName);
    
    int getOrder();
    
    boolean isMetricTrackingAuditField();
}
