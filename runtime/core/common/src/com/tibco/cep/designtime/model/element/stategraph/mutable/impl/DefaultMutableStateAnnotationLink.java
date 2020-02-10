/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 1, 2004
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateTextLabel;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateAnnotationLink;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableStateAnnotationLink extends DefaultMutableStateLink implements MutableStateAnnotationLink {


    public static final String PERSISTENCE_NAME = "StateAnnotationLink";
    protected static final ExpandedName FROM_ANNOTATION_TAG = ExpandedName.makeName("fromAnnotation");
    protected static final ExpandedName TO_STATE_ENTITY_TAG = ExpandedName.makeName("toStateEntity");
    protected static final ExpandedName TO_STATE_ENTITY_GUID_TAG = ExpandedName.makeName("toStateEntityGUID");
    protected StateTextLabel m_fromAnnotation = null;
    protected StateEntity m_toStateEntity = null;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateAnnotationLink(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine) {
        // Bounds are meaningless for links (they just follow the states) so just give a dummy bounds
        super(ontology, name, ownerStateMachine);
    }// end DefaultStateAnnotationLink


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param ownerStateMachine The state machine that owns this state.
     * @param fromAnnotation    The State where the transition starts.
     * @param toStateEntity     The State where the transition ends.
     */
    public DefaultMutableStateAnnotationLink(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine,
            StateTextLabel fromAnnotation,
            StateEntity toStateEntity) {
        // Bounds are meaningless for links (they just follow the states) so just give a dummy bounds
        super(ontology, name, ownerStateMachine);
        m_fromAnnotation = fromAnnotation;
        m_toStateEntity = toStateEntity;
    }// end DefaultStateAnnotationLink


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        String fromAnnotationGUID = root.getAttributeStringValue(FROM_ANNOTATION_TAG);
        String toStateEntityGUID = root.getAttributeStringValue(TO_STATE_ENTITY_TAG);
        m_fromAnnotation = (StateTextLabel) ((DefaultMutableStateMachine) m_ownerStateMachine).getStateEntityFromCache(fromAnnotationGUID);
        m_toStateEntity = ((DefaultMutableStateMachine) m_ownerStateMachine).getStateEntityFromCache(toStateEntityGUID);
    }// end fromXiNode


    /**
     * Get the "from" Annotation (where the link originates) for this link.
     *
     * @return The "from" Annotation (where the link originates) for this link.
     */
    public StateTextLabel getFromAnnotation() {
        return m_fromAnnotation;
    }// end getFromAnnotation


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    /**
     * Get the "to" State (where the link terminates) for this link.
     *
     * @return The "to" State (where the link terminates) for this link.
     */
    public StateEntity getToStateEntity() {
        return m_toStateEntity;
    }// end getToStateEntity


    /**
     * Set the new "from" State (where the link originates) for this link.
     *
     * @param fromAnnotation The new "from" State (where the link originates) for this link.
     */
    public void setFromAnnotation(
            StateTextLabel fromAnnotation) {
        m_fromAnnotation = fromAnnotation;
    }// end setFromAnnotation


    /**
     * Set the new "to" State (where the link terminates) for this link.
     *
     * @param toStateEntity The new "to" State (where the link terminates) for this link.
     */
    public void setToStateEntity(
            StateEntity toStateEntity) {
        m_toStateEntity = toStateEntity;
    }// end setToStateEntity


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        if (m_fromAnnotation != null) {
            setAttributeStringValueSafe(root, FROM_ANNOTATION_TAG, m_fromAnnotation.getGUID());
        }//endif
        if (m_toStateEntity != null) {
            setAttributeStringValueSafe(root, TO_STATE_ENTITY_TAG, m_toStateEntity.getGUID());
        }//endif
        return root;
    }// end toXiNode
}// end class DefaultStateAnnotationLink
