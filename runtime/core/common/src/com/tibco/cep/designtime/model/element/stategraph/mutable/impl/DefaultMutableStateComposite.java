/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 9:02:27 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.element.stategraph.StateVertex;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableState;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateComposite;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateEntity;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateVertex;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableStateComposite extends DefaultMutableState implements MutableStateComposite {


    public static final String PERSISTENCE_NAME = "StateComposite";
    protected static final ExpandedName TIMEOUT_MILLISECONDS_TAG = ExpandedName.makeName("timeoutMilliseconds");
    protected static final ExpandedName IS_CONCURRENT_STATE_TAG = ExpandedName.makeName("isConcurrentState");
    protected static final ExpandedName REGIONS_TAG = ExpandedName.makeName("regions");
    protected static final ExpandedName ENTITIES_TAG = ExpandedName.makeName("elements");
    protected static final String COMPOSITES_MUST_HAVE_ONE_START_STATE = "DefaultStateComposite.getModelErrors.compositesMustHaveOneStartState";
    protected static final String CONCURRENT_STATE_MUST_CONTAIN_TWO_REGIONS = "DefaultStateComposite.getModelErrors.concurrentStateMustContainTwoRegions";
    protected static final String ALL_SUBSTATES_OF_CONCURRENT_MUST_BE_COMPOSITES = "DefaultStateComposite.getModelErrors.allSubstatesOfConcurrentMustBeComposites";
    protected static final String REGIONS_MUST_HAVE_ONE_END_STATE = "DefaultStateComposite.getModelErrors.regionsMustHaveOneEndState";
    protected int m_timeoutComposite = 0;
    protected boolean m_isConcurrentState = false;
    protected List m_regions = null;
    protected List m_entities = null;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateComposite(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateComposite


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     * @param entryAction       The action to perform on entry to this state.
     * @param exitAction        The action to perform on exit from this state.
     * @param isConcurrentState A flag indicating whether this state is concurrent (parallel), or just composite.
     */
    public DefaultMutableStateComposite(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            MutableRule entryAction,
            MutableRule exitAction,
            boolean isConcurrentState) {
        super(ontology, name, bounds, ownerStateMachine, entryAction, exitAction, null);
        m_isConcurrentState = isConcurrentState;
    }// end DefaultStateComposite


    /**
     * Add the entity passed to this composite state.
     *
     * @param index    Where to add the new entity.
     * @param newState The new entity to add to this composite state.
     */
    public void addEntity(
            int index,
            StateEntity newState) {
        if (m_entities == null) {
            m_entities = new ArrayList();
        }//endif
        if (index < m_entities.size()) {
            m_entities.add(index, newState);
        } else {
            m_entities.add(newState);
        }//endif
        ((DefaultMutableStateEntity) newState).setParent(this);
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end addElement


    /**
     * Add the entity passed to this composite state.
     *
     * @param index      Where to add the new entity.
     * @param movedState The entity to move from its old composite state to this one.
     */
    public void moveEntity(
            int index,
            StateEntity movedState) {
        StateEntity oldParent = movedState.getParent();

        if (oldParent != this) {
            if (oldParent instanceof StateComposite) {
                ((MutableStateComposite) oldParent).removeEntity(movedState);
            }

            this.addEntity(index, movedState);

        }
    }// end addElement


    /**
     * Add a concurrent machine into the list of concurrent machines in this state.
     *
     * @param index Where to insert the new concurrent machine.  If index > size, the
     *              new machine is added at the end of the list.
     */
    public void addRegion(
            int index,
            StateComposite region) throws ModelException {
        if (!m_isConcurrentState) {
            throw new ModelException("Can't addRegion on StateComposite if not set as concurrent");
        }//endif
        if (m_regions == null) {
            m_regions = new ArrayList();
        }//endif
        if (index >= m_regions.size()) {
            m_regions.add(region);
        } else {
            m_regions.add(index, region);
        }//endif
        ((DefaultMutableStateComposite) region).setParent(this);
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }

        notifyListeners();
    }// end addRegion


    /**
     * Delete the entity at the index passed from this composite state.  No action
     * is taken if index is beyond end of List.
     *
     * @param index Which entity to delete.
     */
    public void deleteEntity(
            int index) {
        if (m_entities != null) {
            if (index < m_entities.size()) {
                StateEntity entityToDelete = (StateEntity) m_entities.get(index);
                if (entityToDelete instanceof State) {
                    final MutableState state = (MutableState) entityToDelete;
                    ((MutableStateMachine) entityToDelete.getOwnerStateMachine()).deleteLinkedTransitions(state);
                    state.delete();
                }//endif
                m_entities.remove(index);
            }//endif
        }//endif
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end deleteEntity


    /**
     * Delete the entity passed from this composite state.  No action is taken if entity is not present.
     *
     * @param entity Which entity to delete.
     */
    public void deleteEntity(
            StateEntity entity
    ) {
        if (m_entities != null && entity != null) {
            if (entity instanceof State) {
                final MutableState state = (MutableState) entity;
                ((MutableStateMachine) entity.getOwnerStateMachine()).deleteLinkedTransitions(state);
                state.delete();
            }//endif
            m_entities.remove(entity);
        }//endif
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end deleteEntity


    /**
     * Delete the entity passed from this composite state.  No action is taken if entity is not present.
     *
     * @param entity Which entity to delete.
     */
    public void removeEntity(
            StateEntity entity
    ) {
        if (m_entities != null && entity != null) {
            m_entities.remove(entity);
        }//endif
    }// end deleteEntity


    /**
     * Delete a region (set of concurrent sub-states) from the list of regions in this state.
     *
     * @param index The index of the region to delete.  If index > size, no action is taken.
     */
    public void deleteRegion(int index) throws ModelException {
        if (!m_isConcurrentState) {
            throw new ModelException("Can't deleteRegion on StateComposite if not set as concurrent");
        }//endif
        if (m_regions != null && index >= 0 && index < m_regions.size()) {
            final MutableStateComposite region = (MutableStateComposite) m_regions.get(index);
            region.delete();
            m_regions.remove(index);
        }//endif

        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end deleteRegion


    /**
     * Delete the region (set of concurrent sub-states) passed from the list of regions in this state.
     *
     * @param region The region to delete.
     */
    public void deleteRegion(MutableStateComposite region) throws ModelException {
        if (!m_isConcurrentState) {
            throw new ModelException("Can't deleteRegion on StateComposite if not set as concurrent");
        }//endif
        if (m_regions != null) {
            region.delete();
            m_regions.remove(region);
        }//endif

        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end deleteRegion


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        m_timeoutComposite = Integer.parseInt(root.getAttributeStringValue(TIMEOUT_MILLISECONDS_TAG));
        m_isConcurrentState = Boolean.valueOf(root.getAttributeStringValue(IS_CONCURRENT_STATE_TAG)).booleanValue();
        m_regions = createListFromXiNodes(root, REGIONS_TAG);
        if (m_regions != null) {
            Iterator regionsIterator = m_regions.iterator();
            while (regionsIterator.hasNext()) {
                DefaultMutableStateComposite region = (DefaultMutableStateComposite) regionsIterator.next();
                region.setParent(this);
            }//endwhile
        }//endif
        m_entities = createListFromXiNodes(root, ENTITIES_TAG);
        if (m_entities != null) {
            Iterator entitiesIterator = m_entities.iterator();
            while (entitiesIterator.hasNext()) {
                DefaultMutableStateEntity entity = (DefaultMutableStateEntity) entitiesIterator.next();
                entity.setParent(this);
            }//endwhile
        }//endif
    }// end fromXiNode


    /**
     * Return a List of all the entities contained in this composite state.
     *
     * @return A List of all the entities contained in this composite state.
     */
    public List getEntities() {
        return m_entities;
    }// end getEntities


    public void delete() {
        if (m_entities != null) {
            Iterator it = m_entities.iterator();
            while (it.hasNext()) {
                final MutableStateEntity se = (MutableStateEntity) it.next();
                se.delete();
            }
        }

        if (m_regions != null) {
            Iterator it = m_regions.iterator();
            while (it.hasNext()) {
                DefaultMutableStateComposite dsc = (DefaultMutableStateComposite) it.next();
                dsc.delete();
            }
        }

        super.delete();
    }


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        ArrayList modelErrors = new ArrayList();
        BEModelBundle bundle = BEModelBundle.getBundle();
        addErrorIfTrue(getNumberOfStartStates() != 1 && !isConcurrentState(), modelErrors, bundle.getString(COMPOSITES_MUST_HAVE_ONE_START_STATE));
        addErrorIfTrue(m_isConcurrentState && m_regions == null, modelErrors,
                bundle.getString(CONCURRENT_STATE_MUST_CONTAIN_TWO_REGIONS));
        if (m_isConcurrentState && m_regions != null) {
            addErrorIfTrue(m_regions.size() < 2, modelErrors,
                    bundle.getString(CONCURRENT_STATE_MUST_CONTAIN_TWO_REGIONS));
            Iterator regions = m_regions.iterator();
            while (regions.hasNext()) {
                if (!(regions.next() instanceof StateComposite)) {
                    modelErrors.add(new ModelError(this, bundle.getString(ALL_SUBSTATES_OF_CONCURRENT_MUST_BE_COMPOSITES)));
                    break;
                }//endif
            }//endwhile
        }//endif
        StateEntity parent = getParent();
        if (parent != null && parent instanceof DefaultMutableStateComposite) {
            if (((DefaultMutableStateComposite) parent).isConcurrentState()) {
                // This composite state is a region (since it's parent is a concurrent state)
                addErrorIfTrue(getNumberOfEndStates() == 0, modelErrors, bundle.getString(REGIONS_MUST_HAVE_ONE_END_STATE));
            }//endif
        }//endif
//        if(m_regions != null) modelErrors.addAll (getModelErrorsForList (m_regions));
//        if(m_entities != null) modelErrors.addAll (getModelErrorsForList (m_entities));

        // check super stuff if we're not a root state
        if (parent != null) {
            modelErrors.addAll(super.getModelErrors());
        }

        return modelErrors;
    }// end getModelErrors


    /**
     * Get the number of End States in this StateComposite.
     *
     * @return The number of End States in this StateComposite.
     */
    public int getNumberOfEndStates() {
        int numberOfEndStates = 0;
        Iterator entities = getEntities().iterator();
        while (entities.hasNext()) {
            StateEntity entity = (StateEntity) entities.next();
            if (entity instanceof StateEnd) {
                numberOfEndStates++;
            }//endif
        }//endwhile
        return numberOfEndStates;
    }// end getNumberOfEndStates


    /**
     * Get the number of Start States in this StateComposite.
     *
     * @return The number of Start States in this StateComposite.
     */
    public int getNumberOfStartStates() {
        int numberOfStartStates = 0;
        List entityList = getEntities();
        if (entityList == null) {
            return 0;
        }

        Iterator entities = entityList.iterator();
        while (entities.hasNext()) {
            StateEntity entity = (StateEntity) entities.next();
            if (entity instanceof StateStart) {
                numberOfStartStates++;
            }//endif
        }//endwhile
        return numberOfStartStates;
    }// end getNumberOfStartStates


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    /**
     * Get the region (a StateComposite) at the index passed or null if region index not present.
     *
     * @return Get the region (a StateComposite) at the index passed or null if region index not present.
     */
    public StateComposite getRegion(
            int index) throws ModelException {
        if (!m_isConcurrentState) {
            throw new ModelException("Can't getRegion on StateComposite if not set as concurrent");
        }//endif
        if (m_regions != null && index < m_regions.size()) {
            return (StateComposite) m_regions.get(index);
        } else {
            return null;
        }//endif
    }// end getRegion


    /**
     * Get the list of regions contained within this StateConcurrent.  Each element
     * of the List returned will be a StateComposite.
     *
     * @return A list of regions contained within this concurrent state.
     */
    public List getRegions() {
        return m_regions;
    }// end getRegions


    /**
     * Get the timeout (in milliseconds) for this concurrent set of states.
     *
     * @return The new number of milliseconds before this concurrent set of states
     *         will automatically exit.
     */
    public int getTimeout() {
        return m_timeoutComposite;
    }// end getTimeout


    /**
     * Return true if this composite state is a concurrent state, otherwise return false.
     *
     * @return true if this composite state is a concurrent state, otherwise return false.
     */
    public boolean isConcurrentState() {
        return m_isConcurrentState;
    }// end isConcurrentState


    /**
     * Is this Composite State a Region?
     *
     * @return true if this Composite State a Region, otherwise false.
     */
    public boolean isRegion() {
        return (getParent() instanceof StateComposite &&
                ((StateComposite) getParent()).isConcurrentState());
    }// end isRegion


    /**
     * Set whether this composite state is a concurrent state.
     *
     * @param isConcurrentState Should this composite state be a concurrent state.
     */
    public void setConcurrentState(
            boolean isConcurrentState) {
        // If was not previously concurrent but now is, then initialize concurrent model variable
        if (isConcurrentState && !m_isConcurrentState) {
            m_regions = new ArrayList();
        }//endif
        m_isConcurrentState = isConcurrentState;
        // If new setting is "not concurrent" then null out concurrent model list
        if (!isConcurrentState) {
            m_regions = null;
        }//endif

        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end setAsConcurrentState


    /**
     * Set the list of regions (concurrent states).  Each element of the List passed in should
     * be a StateComposite.
     *
     * @param regions The new list of regions in this concurrent state (where each element of the
     *                List is a StateComposite).
     */
    public void setRegions(
            List regions) throws ModelException {
        if (!m_isConcurrentState) {
            throw new ModelException("Can't setRegions on StateComposite if not set as concurrent");
        }//endif
        m_regions = regions;
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end setRegions


    public void setTimeout(int timeoutMilliseconds) {
        m_timeoutComposite = timeoutMilliseconds;
        m_ownerStateMachine.notifyListeners();
        DefaultMutableConcept dc = (DefaultMutableConcept) m_ownerStateMachine.getOwnerConcept();
        if (dc != null) {
            dc.notifyOntologyOnChange();
        }
        notifyListeners();
    }// end setTimeout


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        root.setAttributeStringValue(TIMEOUT_MILLISECONDS_TAG, String.valueOf(m_timeoutComposite));
        root.setAttributeStringValue(IS_CONCURRENT_STATE_TAG, String.valueOf(m_isConcurrentState));
        addXiNodesOfList(factory, root, m_regions, REGIONS_TAG);
        addXiNodesOfList(factory, root, m_entities, ENTITIES_TAG);
        return root;
    }// end toXiNode


    /**
     * Return the StateVertex within this Composite State
     *
     * @param name
     * @return
     */
    public StateVertex getState(String name) {
        Iterator itr = this.getEntities().iterator();
        while (itr.hasNext()) {
            MutableStateVertex sv = (MutableStateVertex) itr.next();
            if (sv.getName().equals(name)) {
                return sv;
            }
        }
        return null;

    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.StateComposite#getTimeoutCodeBlock()
	 */
	@Override
	public CodeBlock getTimeoutCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getEntryCodeBlock()
	 */
	@Override
	public CodeBlock getEntryCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.stategraph.State#getExitCodeBlock()
	 */
	@Override
	public CodeBlock getExitCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}// end class DefaultStateComposite
