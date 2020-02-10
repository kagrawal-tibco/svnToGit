package com.tibco.cep.designtime.model.element;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.tibco.cep.designtime.model.RuleParticipant;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.xml.schema.SmElement;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:17:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Concept extends RuleParticipant {


    int MAX_LOCAL_PROPERTY_DEFINITIONS = Integer.MAX_VALUE;
    String[] BASE_ATTRIBUTE_NAMES = {"id", "extId"};


    boolean contains(Concept concept);


    /**
     * Returns true if this Concept can exist only within another Concept.
     *
     * @return true if this Concept can exist only within another Concept.
     */
    boolean isContained();


    /**
     * If this Concept is contained, then the containing Concept is returned.  Otherwise, null is returned.
     *
     * @return If this Concept is contained, then the containing Concept is returned.  Otherwise, null is returned.
     */
    Concept getParentConcept();


    PropertyDefinition getParentPropertyDefinition();


    /**
     * Returns the Concept from which this Concept inherits.
     *
     * @return The inherited Concept, or null if this Concept does not inherit from another, or the inheritance Concept doesn't exist.
     */
    Concept getSuperConcept();


    /**
     * Returns the path of the inherited Concept.
     */
    String getSuperConceptPath();


    /**
     * Checks if this Concept inherits from another Concept, directly or indirectly.
     *
     * @param concept the Concept to test
     * @return true, if this Concept does inherit from the specified Concept.
     */
    boolean isA(Concept concept);


    /**
     * Return the paths of all Concepts that inherit from this Concept.
     *
     * @return A Collection of all Concepts that inherit from this Concept.
     */
    Collection getSubConceptPaths();


    /**
     * Tests whether or not this Concept can inherit from another.
     *
     * @param concept The Concept against which to test.
     * @return true, if this Concept can inherit from concept, or if concept is null.  Otherwise, false.
     */
    boolean canInheritFrom(Concept concept);


    /**
     * Returns all Concept paths that are used as types in this Concept's local PropertyDefinitions.
     *
     * @return A Collection of all Concept paths that are used as types in this Concept's local PropertyDefinitions.
     */
    Collection getReferencedConceptPaths();


    /**
     * Returns the paths of all Instances of this Concept, and its sub-Concepts.
     *
     * @return A Set containing the paths of all Instances of this Concept and its sub-Concepts.
     */
    Collection getAllInstancePaths();


    /**
     * Returns the paths of all Instances belonging to this Concept's sub-Concepts.
     *
     * @return A Set containing the paths of all Instances of this Concept's sub-Concepts.
     */
    Collection getSubInstancePaths();


    /**
     * Returns the paths of all direct Instances of this Concept.
     *
     * @return A Set containing the paths of all direct Instances of this Concept.
     */
    Collection getLocalInstancesPaths();


    /**
     * Returns all local PropertyDefinitions of this Concept.
     *
     * @return All local PropertyDefinitions of this Concept.
     */
    List getLocalPropertyDefinitions();


    /**
     * Returns local and inherited PropertyDefinitions of this Concept.
     *
     * @return All local and inherited PropertyDefinitions.
     */
    List getAllPropertyDefinitions();


    /**
     * Returns the PropertyDefinitions of this Concept.
     *
     * @param localOnly whether or not to include inherited PropertyDefintions.
     * @return A List containing the requested PropertyDefinitions.
     */
    List getPropertyDefinitions(boolean localOnly);


    /**
     * @return a Collection
     */
    Collection getAttributeDefinitions();


    /**
     * Returns a PropertyDefinition with the supplied on a derived Concept, if it exists.
     *
     * @param name the name for the sub PropertyDefinition.
     * @return A PropertyDefinition with the supplied on a derived Concept, if it exists.
     */
    PropertyDefinition getSubPropertyDefinition(String name);


    /**
     * Returns a PropertyDefinition with the specified name.
     *
     * @param name The name of the requested PropertyDefinition.
     * @return The PropertyDefinition, if found, else null.
     */
    PropertyDefinition getPropertyDefinition(String name, boolean localOnly);


    /**
     * Returns the paths of the Concepts that reference this Concept.
     *
     * @return A collection of Strings, each of which is the path to a Concept.
     */
    Collection getReferringConceptPaths();


    /**
     * Returns the names of all PropertyDefinitions within a RuleSet that reference this Concept.
     *
     * @param conceptPath The path of the Concept.
     * @return A Collection of Strings, each of which is the name of a PropertyDefinition.
     */
    Collection getReferringPropertyDefinitionNames(String conceptPath);


    /**
     * Returns the paths of all ConceptViews that contain References to this Concept.
     */
    Collection getReferringViewPaths();


    /**
     * Returns the SmElement representation of this Concept.  All inherited PropertyDefinitions
     * are included.
     *
     * @return An SmElement representation of the underlying Concept.
     */
    SmElement toSmElement();


    /**
     * Get the list of StateMachines contained within this Concept.
     *
     * @return The list of StateMachines contained within this Concept.
     */
    List getStateMachines();


    /**
     * Return the "main" StateMachine.  The main state machine is the where the
     * execution should start.
     *
     * @return The "main" StateMachine.
     */
    StateMachine getMainStateMachine();


    /**
     * Returns local and inherited StateMachine's.
     *
     * @return List of local and inherited StateMachine's.
     */
    List getAllStateMachines();


    /**
     * @return a boolean
     */
    boolean isAScorecard();


    Set getInstances();


    PropertyDefinition getAttributeDefinition(String attributeName);

    boolean isTransient();

    boolean isPOJO();

    String getPOJOImplClassName();

    boolean isAutoStartStateMachine();

    boolean isMetric();

    void enableMetricTracking();

    void disableMetricTracking();

    boolean isMetricTrackingEnabled();
}
