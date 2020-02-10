/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 29, 2004
 * Time: 7:46:50 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateComposite;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateEntity;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStatePseudo;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateTransition;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * The complete state machine which contains all states, links and decorations.
 */
public class DefaultMutableStateMachine extends DefaultMutableStateEntity implements MutableStateMachine {


    public static final ExpandedName XNAME_ISMAIN = ExpandedName.makeName("isMain");
    public static final String PERSISTENCE_NAME = "StateMachine";
    protected static final ExpandedName ROOT_STATE_TAG = ExpandedName.makeName("rootState");
    protected static final ExpandedName OWNER_CONCEPT_GUID_TAG = ExpandedName.makeName("ownerConceptGUID");
    protected static final ExpandedName STATE_TRANSITIONS_TAG = ExpandedName.makeName("stateTransitions");
    protected static final ExpandedName STATE_ANNOTATION_LINKS_TAG = ExpandedName.makeName("annotationLinks");
    protected static final ExpandedName GUARD_RULES_TAG = ExpandedName.makeName("guardRules");
    protected static final ExpandedName FWD_CORRELATES_TAG = ExpandedName.makeName("fwdCorrelate");
    protected static final String END_STATES_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateMachine.canAddOne.endStatesCannotHaveExitingTransitions";
    protected static final String PSEUDO_STATES_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateMachine.canAddOne.pseudoStatesCannotHaveExitingTransitions";
    protected static final String START_STATES_CANNOT_HAVE_ENTERING_TRANSITIONS = "DefaultStateMachine.canAddOne.startStatesCannotHaveEnteringTransitions";
    protected static final String ROOT_STATE_CANNOT_HAVE_ENTERING_OR_EXITING_TRANSITIONS = "DefaultStateMachine.canAddOne.rootStateCannotHaveEnteringOrExitingTransitions";
    protected static final String CANT_CREATE_TRANSITION_THAT_EXITS_REGION = "DefaultStateMachine.canAddOne.cantCreateTransitionThatExitsRegion";
    protected static final String CANT_CREATE_TRANSITION_THAT_ENTERS_REGION = "DefaultStateMachine.canAddOne.cantCreateTransitionThatEntersRegion";
    protected static final String CANT_DELETE_START_STATE_FROM_ROOT = "DefaultStateMachine.canDeleteOne.cantDeleteStartStateFromRoot";
    protected static final String ROOT_MUST_CONTAIN_ONE_END_STATE = "DefaultStateMachine.canDeleteOne.rootMustContainOneEndState";
    protected static final String REGIONS_MUST_CONTAIN_ONE_END_STATE = "DefaultStateMachine.canDeleteOne.regionsMustContainOneEndState";
    protected static final String REGIONS_CANNOT_BE_DELETED_WHEN_THEY_CONTAIN_STATES_OTHER_THAN_START_AND_END = "DefaultStateMachine.canDeleteOne.regionsCannotBeDeletedWhenTheyContainStatesOtherThanStartAndEnd";
    protected static final String CONCURRENT_STATES_MUST_ALWAYS_CONTAIN_TWO_REGIONS = "DefaultStateMachine.canDeleteOne.concurrentStatesMustAlwaysContainTwoRegions";
    protected static final String STATE_MACHINE_OWNER_CONCEPT_MUST_BE_SET = "DefaultStateMachine.getModelErrors.stateMachineOwnerConceptMustBeSet";
    protected static final String STATE_MACHINE_MUST_HAVE_A_ROOT_STATE = "DefaultStateMachine.getModelErrors.stateMachineMustHaveARootState";
    protected static final String ROOT_MUST_NOT_HAVE_PARENT = "DefaultStateMachine.getModelErrors.rootMustNotHaveParent";
    protected static final String END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateMachine.validateTransitionOrigin.endStateCannotHaveExitingTransitions";
    protected static final String CANT_CREATE_A_TRANSITION_STARTING_FROM_A_REGION = "DefaultStateMachine.validateTransitionOrigin.cantCreateATransitionStartingFromARegion";
    protected StateComposite m_rootState = null;
    protected String ownerConceptPath = null;
    protected List m_stateTransitions = new ArrayList();
    protected List m_annotationLinks = null;
    protected boolean m_fwdCorrelates = false;

//protected List  m_guardRules = null;
    /**
     * A cache of state entities present within this StateMachine, only kept accurate when reading
     * from persistent store.
     */
    protected HashMap m_stateEntityCache = new HashMap();
    protected boolean isMain = false;
    protected DefaultMutableStateMachineRuleSet ruleSet;


    /**
     * Constructs this object within the passed ontology and gives it the name passed.
     *
     * @param ontology     The ontology (BE model) to add this entity.
     * @param name         The name of this element.
     * @param bounds       The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerConcept The path to the Concept that contains this state machine.
     */
    public DefaultMutableStateMachine(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            Concept ownerConcept) {
        this(ontology, name, null, bounds, ownerConcept, null);
    }


    /**
     * Constructs this object within the passed ontology and gives it the name passed.
     *
     * @param ontology     The ontology (BE model) to add this entity.
     * @param name         The name of this element.
     * @param bounds       The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerConcept The path to the Concept that contains this state machine.
     * @param rootState    The state that is the root (starting state) of this state machine.
     */
    public DefaultMutableStateMachine(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            Concept ownerConcept,
            MutableStateComposite rootState) {
        this(ontology, name, null, bounds, ownerConcept, rootState);
    }


    /**
     * Constructs this object within the passed ontology and gives it the name passed.
     *
     * @param ontology     The ontology (BE model) to add this entity.
     * @param name         The name of this element.
     * @param folder       DefaultMutableFolder
     * @param bounds       The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerConcept The path to the Concept that contains this state machine.
     * @param rootState    The state that is the root (starting state) of this state machine.
     */
    protected DefaultMutableStateMachine(
            DefaultMutableOntology ontology,
            String name,
            DefaultMutableFolder folder,
            Rectangle bounds,
            Concept ownerConcept,
            MutableStateComposite rootState) {
        super(ontology, name, folder, bounds, null);
        m_rootState = rootState;
        this.ownerConceptPath = (null == ownerConcept) ? null : ownerConcept.getFullPath();
        this.ruleSet = new DefaultMutableStateMachineRuleSet(this, this.ownerConceptPath, ontology, folder, name);
    }


    /**
     * Add the annotation link passed to this state machine at the index passed.  If the
     * index is greater that the number of annotation links in the state machine it is
     * added to the end.
     *
     * @param index             Where to add the new annotation link.
     * @param newAnnotationLink An annotation link to add to this state machine.
     */
    public void addAnnotationLink(int index, StateAnnotationLink newAnnotationLink) {
        if (m_annotationLinks == null) {
            m_annotationLinks = new ArrayList();
        }//endif
        m_annotationLinks.add(index, newAnnotationLink);
    }// end addAnnotationLink


    /**
     * Add the state passed to the state cache.
     *
     * @param stateToCache A state that is contained within this state machine.
     */
    public void addStateToCache(StateEntity stateToCache) {
        m_stateEntityCache.put(stateToCache.getGUID(), stateToCache);
    }// end addStateToCache


    /**
     * Add the transition passed to this state machine at the index passed.  If the
     * index is greater that the number of transitions in the state machine it is
     * added to the end.
     *
     * @param index              Where to add the new transition.
     * @param newStateTransition A transition to add to this state machine.
     */
    public void addTransition(int index, StateTransition newStateTransition) {
        if (m_stateTransitions == null) {
            m_stateTransitions = new ArrayList();
        }//endif
        if (index >= m_stateTransitions.size()) {
            m_stateTransitions.add(newStateTransition);
        } else {
            m_stateTransitions.add(index, newStateTransition);
        }//endif
        ((MutableStateTransition) newStateTransition).setOwnerStateMachine(this);
    }// end addTransition


    /**
     * Can the single object passed be added to this entity.
     *
     * @param addObject The Object to add to this entity.  The Object can only be a
     *                  single model object (not a List).
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    protected String canAddOne(StateEntity addObject) {
        String canAdd = null;
        BEModelBundle bundle = BEModelBundle.getBundle();
        if (addObject instanceof StateTransition) {
            StateTransition transition = (StateTransition) addObject;
            State fromState = transition.getFromState();
            State toState = transition.getToState();
            // Can't have a transition that exits from an End MutableState or Pseudo State
            if (fromState instanceof StateEnd) {
                return bundle.getString(END_STATES_CANNOT_HAVE_EXITING_TRANSITIONS);
            }//endif
            if (fromState instanceof MutableStatePseudo) {
                return bundle.getString(PSEUDO_STATES_CANNOT_HAVE_EXITING_TRANSITIONS);
            }//endif
            // Can't have a transition that enters a Start State
            if (toState instanceof StateStart) {
                return bundle.getString(START_STATES_CANNOT_HAVE_ENTERING_TRANSITIONS);
            }//endif
            // The root state can't be the start or end of a transition
            StateComposite rootState = this.getMachineRoot();
            if (toState == rootState || fromState == rootState) {
                return bundle.getString(ROOT_STATE_CANNOT_HAVE_ENTERING_OR_EXITING_TRANSITIONS);
            }//endif
            // Check if transition attempts to enter or exit a Concurrent State
            if (toState.getParent() != fromState.getParent()) {
                // States don't have the same parent, this might be an illegal transition
                StateComposite commonParent = getLeastCommonAncester(fromState, toState);
                // If any parent on either branch, up to the common parent is a
                // Region then this transition is illegal
                StateEntity parentState = fromState.getParent();
                while (parentState != commonParent) {
                    if (parentState instanceof StateComposite && ((StateComposite) parentState).isRegion()) {
                        return bundle.getString(CANT_CREATE_TRANSITION_THAT_EXITS_REGION);
                    }//endif
                    parentState = parentState.getParent();
                }//endwhile
                // Now check the "to" State parents up to the LCA
                parentState = toState.getParent();
                while (parentState != commonParent) {
                    if (parentState instanceof StateComposite && ((StateComposite) parentState).isConcurrentState()) {
                        return bundle.getString(CANT_CREATE_TRANSITION_THAT_ENTERS_REGION);
                    }//endif
                    parentState = parentState.getParent();
                }//endwhile
            }//endif
        }//endif
        return canAdd;
    }// end canAddOne


    /**
     * Can the single object passed be deleted from this entity.
     *
     * @param deleteObject The Object to delete from this entity.  The Object can only
     *                     be a single model object (not a List).
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    protected String canDeleteOne(StateEntity deleteObject) {
        BEModelBundle bundle = BEModelBundle.getBundle();
        String errorMessage = null;
        StateEntity parent = null;
        if (deleteObject != null) {
            parent = deleteObject.getParent();
        }//endif
        StateComposite compositeStateParent = null;
        if (parent instanceof StateComposite) {
            compositeStateParent = (StateComposite) parent;
        }//endif
        if (deleteObject instanceof StateStart) {
            errorMessage = bundle.getString(CANT_DELETE_START_STATE_FROM_ROOT);
        } else {
            if (deleteObject instanceof StateEnd) {
                if (parent instanceof StateComposite) {
                    // Some composites must have one end state, but if there are other
                    // end states this one can be selected
                    List entities = compositeStateParent.getEntities();
                    int endCount = 0;
                    Iterator iter = entities.iterator();
                    while (iter.hasNext()) {
                        if (iter.next() instanceof StateEnd) {
                            endCount++;
                            if (endCount > 1) {
                                break;
                            }//endif
                        }//endif
                    }//endwhile
                    if (endCount <= 1) {
                        Object grandParent = compositeStateParent.getParent();
                        if (grandParent == null) {
                            // Root state's parent is null
                            // Root must have at least one end state
                            errorMessage = bundle.getString(ROOT_MUST_CONTAIN_ONE_END_STATE);
                        } else {
                            boolean inRegion = (grandParent instanceof StateComposite) && (((StateComposite) grandParent).isConcurrentState());
                            if (inRegion) {
                                // Regions must have at least one end state
                                errorMessage = bundle.getString(REGIONS_MUST_CONTAIN_ONE_END_STATE);
                            }//endif
                        }//endif
                    }//endif
                }//endif
            } else {
                if (deleteObject instanceof StateComposite) {
                    if (parent instanceof StateComposite) {
                        if (compositeStateParent.isConcurrentState()) {
                            // The deleteObject is a Region
                            // todo rkt Kurt wrote this and I think it's wrong, but need to ask him why he thinks it's needed
                            if (((StateComposite) deleteObject).getEntities().size() > 2) {
                                // Regions with anything besides start and stop nodes cannot be deleted
                                errorMessage = bundle.getString(REGIONS_CANNOT_BE_DELETED_WHEN_THEY_CONTAIN_STATES_OTHER_THAN_START_AND_END);
                            } else {
                                if (compositeStateParent.getRegions().size() <= 2) {
                                    // Must always have at least two Regions in parent
                                    errorMessage = bundle.getString(CONCURRENT_STATES_MUST_ALWAYS_CONTAIN_TWO_REGIONS);
                                }//endif
                            }//endif
                        }//endif
                    }//endif
                }//endif
            }//endif
        }//endif
        return errorMessage;
    }// end canDeleteOne


    /**
     * Clear the state cache.
     */
    public void clearStateCache() {
        m_stateEntityCache.clear();
    }// end clearStateCache


    /**
     * Delete the annotation link at the index passed from this state machine.  If the
     * index is greater that the number of annotation link in the state machine the
     * delete is ignored.
     *
     * @param index Which annotation link to delete from this state machine.
     */
    public void deleteAnnotationLink(int index) {
        if (m_annotationLinks != null && index < m_annotationLinks.size()) {
            m_annotationLinks.remove(index);
        }//endif
    }// end deleteAnnotationLink


    /**
     * Delete the annotation link passed from this state machine.
     *
     * @param annotationLinkToDelete Which annotation link to delete from this state machine.
     */
    public void deleteAnnotationLink(StateAnnotationLink annotationLinkToDelete) {
        if (m_annotationLinks != null) {
            int index = 0;
            // Find the annotation link passed so it can be deleted
            Iterator annotationLinks = m_annotationLinks.iterator();
            while (annotationLinks.hasNext()) {
                StateAnnotationLink annotationLink = (StateAnnotationLink) annotationLinks.next();
                if (annotationLink.getGUID().equals(annotationLinkToDelete.getGUID())) {
                    m_annotationLinks.remove(index);
                    break;
                }//endif
                index++;
            }//endwhile
        }//endif
    }// end deleteAnnotationLink


    /**
     * Delete all transitions that the passed state is linked to.
     *
     * @param stateToUnlink The state to unlink.
     */
    public void deleteLinkedTransitions(State stateToUnlink) {
        // Make a list of transitions to delete and delete them later to avoid a concurrent modification exception
        ArrayList transitionsToDelete = new ArrayList();
        // ++SS fixed, getTransitions can return null list.
        List l = getTransitions();
        if (l == null) {
            return;
        }
        Iterator transitions = l.iterator();
        while (transitions.hasNext()) {
            StateTransition transition = (StateTransition) transitions.next();
            if (transition.getFromState() == stateToUnlink || transition.getToState() == stateToUnlink) {
                transitionsToDelete.add(transition);
            }//endif
        }//endwhile
        Iterator transitionsToDeleteIterator = transitionsToDelete.iterator();
        while (transitionsToDeleteIterator.hasNext()) {
            StateTransition transition = (StateTransition) transitionsToDeleteIterator.next();
            deleteTransition(transition);
        }//endwhile
    }// end deleteLinkedTransitions


    /**
     * Delete the transition at the index passed from this state machine.  If the
     * index is greater that the number of transitions in the state machine the
     * delete is ignored.
     *
     * @param index Which transition to delete from this state machine.
     */
    public void deleteTransition(int index) {
        if (m_stateTransitions != null && index < m_stateTransitions.size()) {
            m_stateTransitions.remove(index);
        }//endif
    }// end deleteTransition


    /**
     * Delete the transition passed from this state machine.
     *
     * @param stateTransitionToDelete Which transition to delete from this state machine.
     */
    public void deleteTransition(StateTransition stateTransitionToDelete) {
        if (m_stateTransitions != null) {
            int index = 0;
            Iterator transitions = m_stateTransitions.iterator();
            while (transitions.hasNext()) {
                StateTransition transition = (StateTransition) transitions.next();
                if (transition.getGUID().equals(stateTransitionToDelete.getGUID())) {
                    //SS ++ Added to delete the rule from ruleset
                    final MutableRuleSet rs = (MutableRuleSet) this.getRuleSet();
                    Rule r = transition.getGuardRule(false);
                    if (r != null) {
                        rs.deleteRule(r.getName());
                    }
                    m_stateTransitions.remove(index);
                    //SS -- end of changes
                    return;
                }//endif
                index++;
            }//endwhile
        }//endif
    }// end deleteTransition

    public void setExtendedProperties(Map props) {
        if (null == props) {
            props=this.m_extendedProperties = new LinkedHashMap();
        }
        MutableOntology mo = (MutableOntology) this.getOntology();
        if (mo != null) { //mo can be null during deserialization.
            mo.removeAlias(this);
        }
        this.m_extendedProperties = props;
        if (mo != null) {
            mo.addAlias(this.getAlias(), this);
        }

        // Backing Store Properties
        Map bs= (Map) props.get(EXTPROP_ENTITY_BACKINGSTORE);
        if (bs == null) {
            bs = new LinkedHashMap();
            props.put(EXTPROP_ENTITY_BACKINGSTORE, bs);
        }
        // Get the table name
        String tableName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME);
        if (tableName == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME, "");
        }

        String typeName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME);
        if (typeName == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME, "");
        }

        String hasBackingStore= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE);
        if (hasBackingStore == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE, "true");
        }

        Map cs= (Map) props.get(EXTPROP_ENTITY_CACHE);
        if (cs == null) {
            cs = new LinkedHashMap();
            props.put(EXTPROP_ENTITY_CACHE, cs);
        }

        String constant= (String) cs.get(EXTPROP_ENTITY_CACHE_CONSTANT);
        if (constant == null) {
            cs.put(EXTPROP_ENTITY_CACHE_CONSTANT, "false");
        }

        String preloadAll= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_ALL);
        if (preloadAll == null) {
            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_ALL, "false");
        }


        String fetchSize= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE);

        if (fetchSize == null) {
            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE, "0");
        }

        String requiresVersionCheck= (String) cs.get(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK);
        if (requiresVersionCheck == null) {
            cs.put(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK, "true");
        }

        String isCacheLimited= (String) cs.get(EXTPROP_ENTITY_CACHE_ISCACHELIMITED);
        if (isCacheLimited == null) {
            cs.put(EXTPROP_ENTITY_CACHE_ISCACHELIMITED, "true");
        }

        String evictOnUpdate= (String) cs.get(EXTPROP_ENTITY_CACHE_EVICTONUPDATE);
        if (evictOnUpdate == null) {
            cs.put(EXTPROP_ENTITY_CACHE_EVICTONUPDATE, "true");
        }

//        super.setExtendedProperties(props);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(XiNode root) throws ModelException {
        // The m_ownerConcept is persisted in the Concept, not here
        super.fromXiNode(root);
        XiNode rootStateXiNode = XiChild.getChild(root, ROOT_STATE_TAG);
        if (rootStateXiNode != null) {
            // According to UML spec this can only be a composite state (should be enforced at creation time)
            m_rootState = (StateComposite) DefaultMutableOntology.createEntityFromNode(rootStateXiNode.getFirstChild());
        }//endif

        this.setAsMain(Boolean.valueOf(root.getAttributeStringValue(XNAME_ISMAIN)));

        // Get the state transitions and if present, iterate through each and add them to this
        m_stateTransitions = createListFromXiNodes(root, STATE_TRANSITIONS_TAG);
        m_annotationLinks = createListFromXiNodes(root, STATE_ANNOTATION_LINKS_TAG);
        String fwdCorrStr = root.getAttributeStringValue(FWD_CORRELATES_TAG);
        if (!ModelUtils.IsEmptyString(fwdCorrStr)) {
            m_fwdCorrelates = Boolean.valueOf(fwdCorrStr).booleanValue();
        }
        XiNode extPropsNode = XiChild.getChild(root, EXTENDED_PROPERTIES_NAME);
        setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));

        //m_guardRules = createListFromXiNodes (root, GUARD_RULES_TAG);
    }// end fromXiNode


    /**
     * Get a List containing all the StateAnnotationLinks in this state machine.
     */
    public List getAnnotationLinks() {
        return m_annotationLinks;
    }// end getAnnotationLinks


    /**
     * Get the root state of this state machine.
     *
     * @return The root state of this state machine.
     */
    public StateComposite getMachineRoot() {
        return m_rootState;
    }// end getMachineRoot


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        BEModelBundle bundle = BEModelBundle.getBundle();
        ArrayList modelErrors = new ArrayList();
        addErrorIfTrue((this.getOwnerConcept() == null),
                modelErrors, bundle.getString(STATE_MACHINE_OWNER_CONCEPT_MUST_BE_SET));
        // Root state validations
        addErrorIfTrue(m_rootState == null, modelErrors, bundle.getString(STATE_MACHINE_MUST_HAVE_A_ROOT_STATE));
        addErrorIfTrue(m_rootState.getParent() != null, modelErrors, bundle.getString(ROOT_MUST_NOT_HAVE_PARENT));
        addErrorIfTrue(((DefaultMutableStateComposite) m_rootState).getNumberOfEndStates() == 0, modelErrors, bundle.getString(ROOT_MUST_CONTAIN_ONE_END_STATE));
        modelErrors.addAll(getModelErrorsForList(m_stateTransitions));
        modelErrors.addAll(getModelErrorsForList(m_annotationLinks));
        //modelErrors.addAll (getModelErrorsForList (m_guardRules));
        return modelErrors;
    }// end getModelErrors


    public void notifyListeners() {
        super.notifyListeners();
//        m_ownerConcept.notifyListeners ();
    }


    /**
     * Get the Concept that "owns" this StateModel.
     */
    public Concept getOwnerConcept() {
        final Ontology o = this.getOntology();
        return (Concept) ((null == o) ? null : o.getEntity(this.ownerConceptPath));
    }// end getOwnerConcept


    public String getOwnerConceptPath() {
        return this.ownerConceptPath;
    }


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName

//    public int getTimeout() {
//        return m_rootState.getTimeout();
//    }// end getTimeout

//    public void setTimeout(int timeout) {
//        super.setTimeout(timeout);
//        m_rootState.setTimeout(timeout);
//    }// end setTimeout


//    public StateMachineRuleSet getRuleSet() {
//        final DefaultMutableConcept c = (DefaultMutableConcept) this.getOwnerConcept();
//        return (null == c) ? null : c.getStateMachineRuleSet();
//    }


    public StateMachineRuleSet getRuleSet() {
        return this.ruleSet;
    }


    public void setOntology(
            MutableOntology ontology) {

        final Ontology oldOntology = this.getOntology();

        super.setOntology(ontology);
        this.ruleSet.setOntology(ontology);

        Concept c = this.getOwnerConcept();
        if ((null != ontology)
                && ((null == oldOntology) || !oldOntology.equals(ontology))) {
            c = ontology.getConcept(this.ownerConceptPath);
            if (null != c) {
                this.setOwnerConcept(c);
            }
        }

        if (null != c) {
            ((MutableConcept) c).addStateMachine(this);
        }
    }


    /**
     * Get the state entity with the GUID passed from the state entity cache.
     *
     * @param stateEntityGUID The GUID of a state entity that is contained within this state machine.
     * @return The state entity with the GUID passed if present, otherwise null.
     */
    public StateEntity getStateEntityFromCache(String stateEntityGUID) {
        return (StateEntity) m_stateEntityCache.get(stateEntityGUID);
    }// end getStateFromCache


    /**
     * Get a List containing all the StateTransitions in this state machine.
     *
     * @return A list of all the StateTransition objects contained in this StateMachine.
     */
    public List getTransitions() {
        return m_stateTransitions;
    }// end getTransitions


    /**
     * Get whether this state machine as the "main" state machine.  This means that this state machine
     * is the entry point and no other state machine can be.
     *
     * @return Is this StateMachine the main entry state machine?
     */
    public boolean isMain() {
        return this.isMain;
//        boolean isMain = false;
//        final DefaultMutableConcept c = (DefaultMutableConcept) this.getOwnerConcept();
//        if (null != c) {
//            isMain = (c.getMainStateMachine() == this);
//        }//endif
//        return isMain;
    }// end isMain


    /**
     * Set the collection containing all the StateAnnotationLinks in this state machine.
     */
    public void setAnnotationLinks(List annotationLinks) {
        m_annotationLinks = new ArrayList(annotationLinks);
    }// end setAnnotationLinks


    /**
     * Set this state machine as the "main" state machine.  This means that this state machine will
     * be the entry point and no other state machine can be (so all other local state machines
     * will be marked as "non-main").
     *
     * @param main
     */
    public void setAsMain(boolean main) {
        if (this.isMain != main) {
            final DefaultMutableConcept c = (DefaultMutableConcept) this.getOwnerConcept();
            if (null != c) {
                final MutableStateMachine oldMain = (MutableStateMachine) c.getMainStateMachine();
                if ((null != oldMain) && (this != oldMain) && c.getStateMachines().contains(oldMain)) {
                    oldMain.setAsMain(false);
                }
                this.isMain = main;
                if (main) {
                    c.setMainStateMachine(this);
                }
            } else {
                this.isMain = main;
            }
            this.notifyOntologyOnChange();
        }
    }// end setAsMain


    public void delete() {
        if (m_stateTransitions != null) {
            Iterator transitionIt = m_stateTransitions.iterator();
            while (transitionIt.hasNext()) {
                final MutableStateTransition st = (MutableStateTransition) transitionIt.next();
                st.delete();
            }
        }

        final MutableStateComposite root = (MutableStateComposite) getMachineRoot();
        List entities = root.getEntities();
        if (entities != null) {
            Iterator rootIt = entities.iterator();
            while (rootIt.hasNext()) {
                final MutableStateEntity se = (MutableStateEntity) rootIt.next();
                se.delete();
            }
        }
        root.delete();

        final DefaultMutableConcept c = (DefaultMutableConcept) this.getOwnerConcept();
        if (null != c) {
            c.deleteStateMachine(this);
        }
        super.delete();
    }


    /**
     * Override setName so rule naming issues can be handled.
     */
    public void setName(String name, boolean renameOnConflict) throws ModelException {
        super.setName(name, renameOnConflict);

        String newName = getName();
        List transitions = getTransitions();
        if (transitions != null) {
            Iterator transitionsIterator = transitions.iterator();
            while (transitionsIterator.hasNext()) {
                StateTransition transition = (StateTransition) transitionsIterator.next();
                final MutableRule guardRule = (MutableRule) transition.getGuardRule(false);
                if (guardRule != null) {
                    guardRule.setName(newName + "_" + transition.getName(), false);
                }//endif
            }//endwhile
        }//endif

        StateComposite root = getMachineRoot();
        if (root != null) {
            updateStateCompositeRuleNames((DefaultMutableStateComposite) root);
        }

    }// end setName


    private void updateStateCompositeRuleNames(DefaultMutableStateComposite sc) throws ModelException {
        updateStateRuleNames(sc);

        List l = sc.getEntities();
        if (l == null) {
            return;
        }
        Iterator stateIt = l.iterator();
        while (stateIt.hasNext()) {
            Object o = stateIt.next();
            if (!(o instanceof DefaultMutableState)) {
                continue;
            }

            DefaultMutableState ds = (DefaultMutableState) o;
            updateStateRuleNames(ds);
            if (ds instanceof StateComposite) {
                updateStateCompositeRuleNames((DefaultMutableStateComposite) ds);
            }

        }
    }


    private void updateStateRuleNames(DefaultMutableState ds) throws ModelException {
        final MutableRule entry = (MutableRule) ds.getEntryAction(false);
        if (entry != null) {
            String ruleName = ds.getRuleName(DefaultMutableState.ENTRY_ACTION_RULE_TAG);
            entry.setName(ruleName, false);
        }

        final MutableRule exit = (MutableRule) ds.getExitAction(false);
        if (exit != null) {
            String ruleName = ds.getRuleName(DefaultMutableState.EXIT_ACTION_RULE_TAG);
            exit.setName(ruleName, false);
        }

        final MutableRule timeout = (MutableRule) ds.getTimeoutAction(false);
        if (timeout != null) {
            String ruleName = ds.getRuleName(DefaultMutableState.TIMEOUT_ACTION_RULE_TAG);
            timeout.setName(ruleName, false);
//            if (null != ruleName) {
//                timeout.setName(ruleName, false);
//            }
        }

        if (timeout != null) {
            final MutableRuleFunction timeoutExpr = (MutableRuleFunction) ds.getTimeoutExpression();
            if (null == timeoutExpr) { return; }
            final String ruleName = ds.getRuleName(DefaultMutableState.TIMEOUT_EXPRESSION_RULE_TAG);
            timeoutExpr.setName(ruleName, false);
        }

        final MutableRule internal = (MutableRule) ds.getInternalTransition(false);
        if (internal != null) {
            String ruleName = ds.getRuleName(DefaultMutableState.INTERNAL_TRANSITION_RULE_TAG);
            internal.setName(ruleName, false);
        }
    }


    /**
     * Sets the Concept that "owns" this StateMachine.
     *
     * @param newConcept Concept that must "owns" this StateMachine.
     */
    public void setOwnerConcept(Concept newConcept) {
        this.ruleSet.setConceptPath((null == newConcept) ? null : newConcept.getFullPath());
        final MutableConcept oldConcept = (MutableConcept) this.getOwnerConcept();
//        if (newConcept != oldConcept) {
            if (null != oldConcept) {
                oldConcept.deleteStateMachine(this);
            }
            if (null != newConcept) {
                this.ownerConceptPath = newConcept.getFullPath();
                ((MutableConcept) newConcept).addStateMachine(this);
            } else {
                this.ownerConceptPath = null;
            }
//        }
    }// end setOwnerConcept


    public void setOwnerConceptPath(String newOwnerConceptPath) {
        if (((null == this.ownerConceptPath) && (null != newOwnerConceptPath))
                || ((null != this.ownerConceptPath) && !this.ownerConceptPath.equals(newOwnerConceptPath))) {

            this.ruleSet.setConceptPath(newOwnerConceptPath);

            final Ontology o = this.getOntology();
            final MutableConcept newConcept = (MutableConcept) ((null == o) ? null : o.getEntity(newOwnerConceptPath));
            final MutableConcept oldConcept = (MutableConcept) this.getOwnerConcept();
            if (newConcept != oldConcept) {
                if (null != oldConcept) {
                    oldConcept.deleteStateMachine(this);
                }
                if (null != newConcept) {
                    newConcept.addStateMachine(this);
                }
            }//if
            this.ownerConceptPath = newOwnerConceptPath;
        }//if
    }



    /**
     * Set the root state of this state machine.
     */
    public void setMachineRoot(StateComposite rootState) {
        m_rootState = rootState;
    }// end setRoot


    /**
     * Set the list containing all the StateTransitions in this state machine.
     *
     * @param stateTransitions A list of all the StateTransition objects to add to this StateMachine.
     */
    public void setTransitions(List stateTransitions) {
        m_stateTransitions = stateTransitions;
        Iterator transitions = m_stateTransitions.iterator();
        while (transitions.hasNext()) {
            final MutableStateTransition transition = (MutableStateTransition) transitions.next();
            transition.setOwnerStateMachine(this);
        }//endwhile
    }// end setTransitions


    public boolean forwardCorrelates() {
        return m_fwdCorrelates;
    }


    public void setForwardCorrelates(boolean fwdCorrelate) {
        m_fwdCorrelates = fwdCorrelate;
    }


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(XiFactory factory) {
        // The m_ownerConcept is persisted in the Concept, not here
        XiNode root = super.toXiNode(factory);
        if (m_rootState != null) {
            XiNode rootXiNode = factory.createElement(ROOT_STATE_TAG);
            rootXiNode.appendChild(((DefaultMutableState) m_rootState).toXiNode(factory));
            root.appendChild(rootXiNode);
        }//endif
        addXiNodesOfList(factory, root, m_stateTransitions, STATE_TRANSITIONS_TAG);
        addXiNodesOfList(factory, root, m_annotationLinks, STATE_ANNOTATION_LINKS_TAG);
        root.setAttributeStringValue(FWD_CORRELATES_TAG, String.valueOf(m_fwdCorrelates));
        root.setAttributeStringValue(XNAME_ISMAIN, "" + this.isMain());        
        return root;
    }// end toXiNode

// todo rkt This method is needed, need to expand design of validation interface to encompass this type of use


    public String validateTransitionOrigin(State from) {
        BEModelBundle bundle = BEModelBundle.getBundle();
        String result = null;
        if (from instanceof StateEnd) {
            result = bundle.getString(END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS);
        } else {
            // Can't drop from regions
            if (from instanceof DefaultMutableStateComposite) {
                if (((DefaultMutableStateComposite) from).isRegion()) {
                    // todo rkt This test seems wrong, doesn't check hierarchy for ANY concurrent, not sure, need to ask Kurt
                    result = bundle.getString(CANT_CREATE_A_TRANSITION_STARTING_FROM_A_REGION);
                }//endif
            }//endif
        }//endif
        return result;
    }// end validateTransitionOrigin

// todo rkt This method is not needed, canAdd does this (and much more)
// Well Formed-ness Rules


    public String validateTransitionConnection(State from, State to) {
        String ret = null;
        if (from != null && to != null) {
            StateTransition transition = new DefaultMutableStateTransition(null, "temp", this, to, from);
            ret = canAdd(transition);
        }//endif
        return ret;
    }// end validateTransitionConnection

// todo rkt This method is not needed, canAdd does this (and much more)

// not quite equivalent to canAdd() since the oldtransition will be deleted before the
// new transition is added. This may change the validation result.


    public String validateTransitionReconnection(StateTransition oldtransition, State newFrom, State newTo, boolean isReconnectingOrigin) {
        String ret = null;
        if (newFrom != null && newTo != null) {
            StateTransition transition = new DefaultMutableStateTransition(null, "temp", this, newTo, newFrom);
            ret = canAdd(transition);
        }//endif
        return ret;
    }// end validateTransitionReconnection


}// end class DefaultStateMachine
