package com.tibco.cep.runtime.model.element;


import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 3:04:47 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*
*/

/**
 * Base for all runtime concepts.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO improve description.
public interface Concept extends com.tibco.cep.kernel.model.entity.Element, SerializableLite {

    /**
     * Get the version of the Concept instance
     * @.category public-api
     * @return int
     */
    int getVersion();

    /**
     * Gets a <code>Property</code> by name.
     *
     * @param name of the <code>Property</code>.
     * @return the <code>Property</code> or null if there is no <code>Property</code> with that
     *         name.
     * @.category public-api
     * @since 2.0.0
     */
    Property getProperty(String name);


    /**
     * Gets all the <code>Property</code> in this <code>Concept</code>, including all the inherited
     * ones.
     *
     * @return an array of all the <code>Property</code> of this <code>Concept</code>, including the
     *         inherited ones.
     * @.category public-api
     * @since 2.0.0
     */
    Property[] getProperties();


    /**
     * Gets all the local properties of this <code>Concept</code>. Properties are local when defined
     * in this <code>Concept</code>. Properties inherited from parent concepts are not local.
     *
     * @return <code>Property[]</code> that contains all the local properties.
     * @.category public-api
     * @since 2.0.0
     */
    Property[] getLocalProperties();


    /**
     * Gets a <code>PropertyAtom</code> by name.
     *
     * @param name of the PropertyAtom.
     * @return the <code>PropertyAtom</code> if it was found, else null.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom getPropertyAtom(String name);


    /**
     * Gets a <code>PropertyArray</code> by name.
     *
     * @param name of the PropertyArray.
     * @return the <code>PropertyArray</code> if it was found, else null.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyArray getPropertyArray(String name);


    /**
     * Serializes the instance and its contained instance(s) into <code>XiNode</code>.
     *
     * @param node XiNode to be serialized to
     */
    void toXiNode(XiNode node);

    /**
     * Serializes the instance and its contained instance(s) into <code>XiNode</code>.
     *
     * @param filter
     * @param node   XiNode to be serialized to
     */
    void toXiNode(ConceptToXiNodeFilter filter, XiNode node);


    /**
     * Serializes the instance and its contained instance(s) into <code>XiNode</code>.
     *
     * @param node       XiNode to be serialized to.
     * @param changeOnly serialize the instance and its contained instances that been changed in the
     *                   current conflict resolution cycle.
     */
    void toXiNode(XiNode node, boolean changeOnly);


    /**
     * Sets the external Id of this <code>Concept</code>. This must be done before the
     * <code>Concept</code> gets asserted to the WorkingMemory. Setting the external Id of a
     * <code>Concept</code> after it is asserted is illegal, and will be ignored.
     *
     * @param extId a <code>String</code>.
     * @throws ExtIdAlreadyBoundException if the Id provided already exists.
     */
    void setExtId(String extId) throws ExtIdAlreadyBoundException;


    /**
     * Returns the expandedName of the Concept's Implementation.
     *
     * @return ExpandedName
     * @.category public-api
     * @since 2.0.0
     */
    //TODO why/how is this used?
    ExpandedName getExpandedName();

    /**
     * This method returns true if the <code> concept </code> has a main state machine defined in
     * the concept hierarchy.
     *
     * @return boolean
     */
    boolean hasMainStateMachine();

    /**
     * This method returns the main state machine associated with the <code> concept </concept>
     * hierarchy
     *
     * @return the main state machine
     */

    public StateMachineConcept getMainStateMachine();

    public boolean isAutoStartupStateMachine();

    /**
     * Starts the associated main state machine
     *
     * @return the started state machine
     */
    public StateMachineConcept startMainStateMachine();


    /**
     * This method returns the property objects associated with defined state machines contained in
     * this concept
     *
     * @return Property.PropertyStateMachine[]
     */
    public Property.PropertyStateMachine[] getStateMachineProperties();

    /**
     * @param stateMachineName
     * @return Property.PropertyStateMachine
     */
    public Property.PropertyStateMachine getStateMachineProperty(String stateMachineName);

    /**
     * @param serializer
     */
    void serialize(ConceptSerializer serializer);


    /**
     * @param deserializer
     */
    void deserialize(ConceptDeserializer deserializer);


    long[] getReverseReferences();

    /**
     * Create a copy of this instance, the new copy of the instance doesn't contain any direct java
     * reference to other concept. Java references will be replaced by some reference objects that
     * store the representation of the java reference objects.
     *
     * @return a copy of this instance
     */
    Concept duplicateThis();

    /**
     * Gets the property by its Id
     *
     * @param id propertyId of the property
     * @return Property
     */
    <T extends Property> T getProperty(int id);

    Concept getParent();
}



