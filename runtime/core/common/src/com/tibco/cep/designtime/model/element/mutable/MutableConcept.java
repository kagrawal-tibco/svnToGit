package com.tibco.cep.designtime.model.element.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableRuleParticipant;


/**
 * A Concept is an Entity which can have Instances, Properties, and Rules.
 * It is defined uniquely by its Folder name followed by a folder separator,
 * followed by its name.  A Concepts can inherit from another Concept.
 *
 * @author ishaan
 * @version Mar 16, 2004 7:33:23 PM
 */
public interface MutableConcept extends Concept, MutableRuleParticipant {


    /**
     * Sets the Concept from which this inherits.
     *
     * @param superConceptPath The path of the new super Concept, or null to remove inheritance.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          If isA(superConcept) returns true.
     */
    public void setSuperConcept(String superConceptPath) throws ModelException;


    /**
     * Creates an Instance of this Concept.
     *
     * @param domain The primary domain for the new Instance.
     * @param name   The name for the new Instance.
     * @return
     * @throws com.tibco.cep.designtime.model.ModelException
     *          If an Instance with fully specified name already exists, or if the specified name is invalid.
     */
    public MutableInstance instantiate(MutableFolder domain, String name) throws ModelException;


    /**
     * Deletes the Instance.
     *
     * @param instance The local Instance to be deleted.
     */
    public void removeInstance(MutableInstance instance) throws ModelException;


    /**
     * Creates a PropertyDefinition.
     *
     * @param name            The name for the PropertyDefinition.
     * @param typeFlag        A flag (see PropertyDefinition) indicating the type of Property.
     * @param conceptTypePath If the type is Concept, then this parameter is used to determine which path is used as the type for this Defintion.  Must not be null.
     * @param historyPolicy   The policy for maintaining history.
     * @param historySize     The 'ring buffer size'.  Must be a positive number.
     * @param isArray         Whether or not the PropertyDefinition should have array semantics.
     * @param defaultValue    The default value for all instances of this definition.
     * @return The new PropertyDefinition.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          Thrown when a definition of the same name already exists in a super or derived Concept, this Concept already has more than MAX_LOCAL_PROPERTY_DEFINITIONS PropertyDefinitions or any other parameter is invalid.
     */
    public MutablePropertyDefinition createPropertyDefinition(String name, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, boolean isArray, String defaultValue) throws ModelException;


    /**
     * Deletes a PropertyDefinition.
     *
     * @param name The name of the Property Definition
     */
    public void deletePropertyDefinition(String name);


    /**
     * Deletes a PropertyDefinition
     *
     * @param propertyDefinition The PropertyDefinition to delete
     */
    public void deletePropertyDefinition(MutablePropertyDefinition propertyDefinition);


    /**
     * Add the state model passed to this Concept.
     *
     * @param newStateMachine A state model to add to this Concept.
     */
    public void addStateMachine(MutableStateMachine newStateMachine);


    /**
     * Delete the state machine at the index passed from this Concept.
     * If the index is greater than the size of the array then nothing is deleted.
     *
     * @param index The index of a StateMachine to delete from this Concept.
     */
    public void deleteStateMachine(int index);


    /**
     * Delete the state machine passed from this Concept.
     *
     * @param stateMachineToDelete The StateMachine to delete from this Concept.
     */
    public void deleteStateMachine(MutableStateMachine stateMachineToDelete);


    /**
     * Set the "main" StateMachine.  The main state model is the where the
     * execution should start.
     *
     * @param mainStateMachine The new "main" StateMachine.
     */
    public void setMainStateMachine(MutableStateMachine mainStateMachine);


    void setTransient(boolean isTransient);

    void setPOJO(boolean isPOJO);

    void setPOJOImplClassName(String clazzName);

    void setIsAutoStartStateMachine(boolean isAutoStartStateMachine);
}// end interface Concept
