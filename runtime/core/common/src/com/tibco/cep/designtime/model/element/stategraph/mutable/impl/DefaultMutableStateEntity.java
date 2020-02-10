/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 3:52:43 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Validatable;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;
import com.tibco.cep.designtime.model.element.stategraph.Validator;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateEntity;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * The root of all entities that can be placed on a state machine.
 * If this class becomes concrete (no longer abstract) it must be added to
 * createEntityFromNode as a new object to be constructed from persistence.
 */
public abstract class DefaultMutableStateEntity extends AbstractMutableEntity implements MutableStateEntity, Validator, Validatable {


    public static final String PERSISTENCE_NAME = "StateEntity";
    protected static final ExpandedName FOLDER_TAG = ExpandedName.makeName("folder");
    protected static final ExpandedName NAME_TAG = ExpandedName.makeName("name");
    protected static final ExpandedName GUID_TAG = ExpandedName.makeName("guid");
    protected static final ExpandedName BOUNDS_X_TAG = ExpandedName.makeName("x");
    protected static final ExpandedName BOUNDS_Y_TAG = ExpandedName.makeName("y");
    protected static final ExpandedName BOUNDS_WIDTH_TAG = ExpandedName.makeName("width");
    protected static final ExpandedName BOUNDS_HEIGHT_TAG = ExpandedName.makeName("height");
    protected static final ExpandedName TIMEOUT_TAG = ExpandedName.makeName("timeout");
    protected static final ExpandedName TIMEOUT_UNITS_TAG = ExpandedName.makeName("timeoutUnits");
    protected static final String UNKNOWN_ERROR = "DefaultStateEntity.unknownError";
    protected static final String END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateEntity.canStart.endStateCannotHaveExitingTransitions";
    // All actions appropriate for canStart are listed here
    protected static final Integer CREATE_TRANSITION_ACTION = new Integer(1);
    protected Rectangle m_bounds = new Rectangle(0, 0, 0, 0);
    protected MutableStateMachine m_ownerStateMachine = null;
    protected StateComposite m_parent = null;
    protected int m_timeout = 0;
    protected int m_timeoutUnits = com.tibco.cep.designtime.model.event.Event.SECONDS_UNITS;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology The ontology (BE model) to add this entity.
     * @param name     The name of this element.
     * @param bounds   The location of the entity on the main view (only top-left is used for iconic entities).
     */
    public DefaultMutableStateEntity(DefaultMutableOntology ontology, String name, Rectangle bounds, MutableStateMachine ownerStateMachine) {
        this(ontology, name, null, bounds, ownerStateMachine);
    }// end DefaultStateEntity


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology The ontology (BE model) to add this entity.
     * @param name     The name of this element.
     * @param folder   MutableFolder
     * @param bounds   The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine MutableStateMachine
     */
    protected DefaultMutableStateEntity(
            DefaultMutableOntology ontology,
            String name,
            DefaultMutableFolder folder,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, folder, name);
        if (bounds == null) {
            m_bounds = new Rectangle(0, 0, 0, 0);
        } else {
            m_bounds = bounds;
        }//endif
        m_ownerStateMachine = ownerStateMachine;
    }// end DefaultStateEntity


    /**
     * Can the object passed be added to this entity.
     *
     * @param addObject The Object to add to this entity.  The Object can be a List, StateEntity or
     *                  certain subclasses based on the implementing object.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canAdd(Object addObject) {
        String canAdd = null;
        if (addObject instanceof List) {
            // Trying to add several objects to this entity
            List objects = (List) addObject;
            Iterator objectIterator = objects.iterator();
            while (objectIterator.hasNext() && canAdd == null) {
                canAdd = canAddOne((StateEntity) objectIterator.next());
            }//endwhile
        } else {
            canAdd = canAddOne((StateEntity) addObject);
        }//endif
        return canAdd;
    }// end canAdd


    /**
     * Can the single object passed be added to this entity.
     *
     * @param addObject The Object to add to this entity.  The Object can only be a
     *                  single model object (not a List).
     * @return true if the Object can be added to this object, otherwise return false.
     */
    protected String canAddOne(StateEntity addObject) {
        return BEModelBundle.getBundle().getString(UNKNOWN_ERROR);
    }// end canAddOne


    /**
     * Can the object passed be deleted from this entity.
     *
     * @param deleteObject The Object to delete from this entity.  The Object can be a List,
     *                     StateEntity or certain subclasses based on the implementing object.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canDelete(Object deleteObject) {
        String canDelete = null;
        if (deleteObject instanceof List) {
            // Trying to delete several objects from this entity
            List objects = (List) deleteObject;
            Iterator objectIterator = objects.iterator();
            while (objectIterator.hasNext() && canDelete == null) {
                canDelete = canDeleteOne((StateEntity) objectIterator.next());
            }//endwhile
        } else {
            canDelete = canDeleteOne((StateEntity) deleteObject);
        }//endif
        return canDelete;
    }// end canDelete


    /**
     * Can the single object passed be deleted from this entity.
     *
     * @param deleteObject The Object to delete from this entity.  The Object can only
     *                     be a single model object (not a List).
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    protected String canDeleteOne(StateEntity deleteObject) {
        return BEModelBundle.getBundle().getString(UNKNOWN_ERROR);
    }// end canDeleteOne


    /**
     * Can the object passed act as a start for an action.
     *
     * @param startObject The Object to test if it can start the requested action.
     *                    The Object can be a List, StateEntity or certain subclasses based on the implementing object.
     * @param actionType  The type of action to start.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canStart(Object startObject, Object actionType) {
        String result = null;
        if (actionType.equals(CREATE_TRANSITION_ACTION)) {
            if (startObject instanceof StateEnd) {
                result = BEModelBundle.getBundle().getString(END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS);
            }//endif
        }//endif
        return result;
    }// end canStart


    /**
     * Load this object from the XiNode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(XiNode root) throws ModelException {
        String name = root.getAttributeStringValue(NAME_TAG);
        setName(name, false);
        String guid = root.getAttributeStringValue(GUID_TAG);
        setGUID(guid);
        String desc = root.getAttributeStringValue(DESCRIPTION_NAME);
        setDescription(desc);
        int boundsX = getIntAttributeValue(root, BOUNDS_X_TAG);
        int boundsY = getIntAttributeValue(root, BOUNDS_Y_TAG);
        int boundsWidth = getIntAttributeValue(root, BOUNDS_WIDTH_TAG);
        int boundsHeight = getIntAttributeValue(root, BOUNDS_HEIGHT_TAG);
        m_bounds = new Rectangle(boundsX, boundsY, boundsWidth, boundsHeight);
        // Add this state entity to the cache so transitions and annotation links can be restored
        if (m_ownerStateMachine != null) {
            ((DefaultMutableStateMachine) m_ownerStateMachine).addStateToCache(this);
        }//endif
        m_timeout = getIntAttributeValue(root, TIMEOUT_TAG);
        m_timeoutUnits = getIntAttributeValue(root, TIMEOUT_UNITS_TAG);
    }// end fromXiNode


    /**
     * Load the Color attribute passed as attributeName from the XiNode passed.
     *
     * @param attributeName  The name of the attribute to load the color from.
     * @param nodeToLoadFrom The XiNode where the color should be loaded from.
     */
    public Color fromXiNodeOfColor(ExpandedName attributeName, XiNode nodeToLoadFrom) {
        int colorInt = getIntAttributeValue(nodeToLoadFrom, attributeName);
        Color resultColor = new Color(colorInt / 65536, colorInt / 256 % 256, colorInt % 256);
        return resultColor;
    }// end fromXiNodeOfColor


    /**
     * Get the bounding rectangle for this decoration element.
     *
     * @return The bounding rectangle of this decoration element.
     */
    public Rectangle getBounds() {
        // Copy to make a safe return value (can't change internal bounds)
        return new Rectangle(m_bounds);
    }// end getBounds


    /**
     * Get an int attribute value from the XiNode passed.
     *
     * @param node The XiNode to get the attribute from.
     * @param tag  The tag of the attribute to get.
     * @return The int value of the attribute in "node" with the tag passed as "tag".
     */
    protected int getIntAttributeValue(XiNode node, ExpandedName tag) {
        int result = 0;
        try {
            result = Integer.valueOf(node.getAttributeStringValue(tag)).intValue();
        } catch (Exception exception) {
            // Ignore
        }//endtry
        return result;
    }// end getIntAttributeValue


    /**
     * Get the Least Common Ancester State (the state which encloses both input States
     * while enclosing the smallest scope).
     *
     * @param state1 A state to find the common parent of.
     * @param state2 A second state to find the common parent of.
     */
    public StateComposite getLeastCommonAncester(StateEntity state1, StateEntity state2) {
        ArrayList state1Parents = getParentList(state1);
        ArrayList state2Parents = getParentList(state2);
        int index = 0;
        while (index < state1Parents.size() && index < state2Parents.size() && state1Parents.get(index) == state2Parents.get((index)))
        {
            index++;
        }//endwhile
        return (StateComposite) state1Parents.get(index - 1);
    }// end getLeastCommonAncester


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        ArrayList modelErrors = new ArrayList();
        return modelErrors;
    }// end getModelErrors


    /**
     * Checks if this portion of the data model is valid.
     *
     * @param entities A List of entities to check for validity.
     * @return true if all entities in List are valid, false otherwise.
     */
    public List getModelErrorsForList(List entities) {
        ArrayList modelErrors = new ArrayList();
        Iterator entitiesIterator = entities.iterator();
        while (entitiesIterator.hasNext()) {
            Validatable entity = (Validatable) entitiesIterator.next();
            modelErrors.addAll(entity.getModelErrors());
        }//endwhile
        return modelErrors;
    }// end getModelErrorsForList


    /**
     * Get the state machine that this state belongs to.
     *
     * @return The state machine that this state belongs to.
     */
    public StateMachine getOwnerStateMachine() {
        return m_ownerStateMachine;
    }// end getOwnerStateMachine


    /**
     * Get the parent of this state entity.  Certain entities have no useful parent (e.g.
     * StateMachine has no parent).
     *
     * @return The parent of this state entity.
     */
    public StateEntity getParent() {
        return m_parent;
    }// end getParent


    /**
     * Get a List containing the parent hierarchy for the state entity passed.
     *
     * @return A List containing the parent hierarchy for the state entity passed.
     */
    protected ArrayList getParentList(StateEntity state) {
        ArrayList stateParents = new ArrayList();
        StateEntity stateParent = state.getParent();
        while (stateParent != null) {
            stateParents.add(0, stateParent);
            stateParent = stateParent.getParent();
        }//endwhile
        return stateParents;
    }// end getParentList


    /**
     * Get the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName



    public StateMachineRuleSet getRuleSet() {
        final StateMachine sm = this.getOwnerStateMachine();
        if (null == sm) {
            return null;
        } else {
            return sm.getRuleSet();
        }
    }


    /**
     * Generate a rule name for this state entity.
     */
    public String getRuleName(String qualifier) {
        return ModelNameUtil.getStateRuleName(this, qualifier);
    }// end getRuleName

//    /** Get the timeout (time before this transition is traversed automatically) for this transition.
//     * @return The timeout (time before this transition is traversed automatically) for this transition. */
//    public int getTimeout () {
//        return m_timeout;
//    }// end getTimeout


    /**
     * Set the timeout units (the time unit e.g. minute, for the timeout value).
     * The value is interpreted as 0 - milliseconds, 1 - seconds, 2 - minutes, 3 - hours, 4 - days
     *
     * @return The timeout units (the time unit e.g. minute, for the timeout value).
     */
    public int getTimeoutUnits() {
        return m_timeoutUnits;
    }// end getTimeoutUnits


    /**
     * Checks if this portion of the data model is valid.
     *
     * @param recurse If true, isValid() is called on all sub-Validatables, and returns false if any are invalid.
     * @return true if valid, false otherwise.
     */
    public boolean isValid(boolean recurse) {
        return getModelErrors().size() == 0;
    }// end isValid


    /**
     * A utility function to set an attribute string value while checking for null.
     */
    public void setAttributeStringValueSafe(XiNode node, ExpandedName tag, String value) {
        if (value != null) {
            node.setAttributeStringValue(tag, value);
        }//endif
    }// end setAttributeStringValueSafe


    /**
     * Set the new bounding rectangle for this decoration element.
     *
     * @param bounds The new bounding rectangle of this decoration element.
     */
    public void setBounds(Rectangle bounds) {
        m_bounds = new Rectangle(bounds);
    }// end setBounds


    /**
     * Set the state machine that this state belongs to.
     *
     * @param ownerStateMachine The state machine that this state belongs to.
     */
    public void setOwnerStateMachine(MutableStateMachine ownerStateMachine) {
        m_ownerStateMachine = ownerStateMachine;
    }// end setOwnerStateMachine


    /**
     * NOTE: This method is not for public consumption, only the State Model classes should
     * call this method, other classes should call the appropriate add method in the parent
     * object, which in turn will call this method.
     * Set the parent of this state entity.  This method is a very simple set, it does NOT
     * perform any consistency updates to related structures.  It doesn't update a previous
     * parent (if any) to notify that this entity is no longer a member and does not add this
     * entity to the new parent.
     *
     * @param parent The new parent of this state entity.
     */
    protected void setParent(StateComposite parent) {
        m_parent = parent;
    }// end setParent

//    /** Set the timeout (time before this transition is traversed automatically) for this transition.
//     * @param timeout The timeout (time before this transition is traversed automatically) for this transition. */
//    public void setTimeout (int timeout) {
//        m_timeout = timeout;
//    }// end setTimeout


    /**
     * Set the timeout units (the time unit e.g. minute, for the timeout value).
     * The value is interpreted as 0 - milliseconds, 1 - seconds, 2 - minutes, 3 - hours, 4 - days
     *
     * @param timeoutUnits The timeout units (the time unit e.g. minute, for the timeout value).
     */
    public void setTimeoutUnits(int timeoutUnits) {
        m_timeoutUnits = timeoutUnits;
    }// end setTimeoutUnits


    public void setExtendedProperties(Map props) {
        if (null == props) {
            this.m_extendedProperties = new LinkedHashMap();
        } else {
            this.m_extendedProperties = props;
        }

        String index= (String) m_extendedProperties.get("index");
        if (index == null) {
            m_extendedProperties.put("index", "false");
        }

        HashMap _bs = new HashMap();
        HashMap bs = (HashMap) m_extendedProperties.get(EXTPROP_PROPERTY_BACKINGSTORE);
        if (bs == null) {
            bs = new LinkedHashMap();
        }
        m_extendedProperties.put(EXTPROP_PROPERTY_BACKINGSTORE, _bs);
        String columnName= (String) bs.get(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
        if (columnName == null) {
            _bs.put(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, "");
        } else {
            _bs.put(EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, columnName);
        }

        m_extendedProperties.remove(EXTPROP_ENTITY_CACHE);
//        super.setExtendedProperties(props);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(XiFactory factory) {
        // The parent is not persisted, instead it is determined and set while streaming in
        XiNode root = super.toXiNode(factory, getPersistenceName());
        Folder folder = getFolder();
        if (folder != null) {
            root.setAttributeStringValue(FOLDER_TAG, folder.toString());
        }//endif
        root.setAttributeStringValue(BOUNDS_X_TAG, Integer.toString((int) m_bounds.getX()));
        root.setAttributeStringValue(BOUNDS_Y_TAG, Integer.toString((int) m_bounds.getY()));
        root.setAttributeStringValue(BOUNDS_WIDTH_TAG, Integer.toString((int) m_bounds.getWidth()));
        root.setAttributeStringValue(BOUNDS_HEIGHT_TAG, Integer.toString((int) m_bounds.getHeight()));
//        root.setAttributeStringValue (TIMEOUT_TAG, Long.toString (m_timeout));
        root.setAttributeStringValue(TIMEOUT_UNITS_TAG, Integer.toString(m_timeoutUnits));
        return root;
    }// end toXiNode


    /**
     * Convert the data in the color passed into an XiNode attribute on the nodeToSaveIn
     * with the name passed as attributeName.
     *
     * @param color         The color to save.
     * @param attributeName The name of the attribute to save the color in.
     * @param nodeToSaveIn  The XiNode where the color should be saved.
     */
    public void toXiNodeOfColor(Color color, ExpandedName attributeName, XiNode nodeToSaveIn) {
        String colorString = "0";
        if (color != null) {
            colorString = Integer.toString(color.getRed() * 65536 + color.getGreen() * 256 + color.getBlue());
        }//endif
        nodeToSaveIn.setAttributeStringValue(attributeName, colorString);
    }// end toXiNodeOfColor
}// end class DefaultStateEntity
