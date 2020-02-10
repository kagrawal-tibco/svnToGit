/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 30, 2004
 * Time: 6:31:15 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateStub;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateSubmachine;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * This class is used to represent a "sub-machine" (a link to another state machine).
 */
public class DefaultMutableStateSubmachine extends DefaultMutableStateComposite implements MutableStateSubmachine {


    public static final String PERSISTENCE_NAME = "StateSubmachine";
    protected static final ExpandedName URI_TAG = ExpandedName.makeName("URI");
    protected static final ExpandedName EXPLICIT_TAG = ExpandedName.makeName("explicit");
    protected static final ExpandedName PRESERVE_FWD_CORRELATION = ExpandedName.makeName("preserveFwdCorr");
    protected static final String SUBMACHINES_MUST_HAVE_A_PATH_SET = "DefaultStateSubmachine.getModelErrors.submachinesMustHaveAPathSet";
    protected static final String SUBMACHINES_CANNOT_BE_CONCURRENT_STATES = "DefaultStateSubmachine.getModelErrors.submachinesCannotBeConcurrentStates";
    protected static final String SUBMACHINES_CAN_ONLY_CONTAIN_STUB_STATES = "DefaultStateSubmachine.getModelErrors.submachinesCanOnlyContainStubStates";
    protected String m_URI = null;
    protected boolean m_callExplicitly;
    protected boolean m_preserveForwardCorrelation = true;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateSubmachine(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateSubmachine


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateSubmachine(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            MutableRule entryAction,
            MutableRule exitAction,
            String URI) {
        super(ontology, name, bounds, ownerStateMachine, entryAction, exitAction, true);
        m_URI = URI;
        m_callExplicitly = false;
    }// end DefaultStateSubmachine


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        m_URI = root.getAttributeStringValue(URI_TAG);

        String callExplicitlyString = root.getAttributeStringValue(EXPLICIT_TAG);
        if (ModelUtils.IsEmptyString(callExplicitlyString)) {
            m_callExplicitly = false;
        } else {
            Boolean callExplicitly = Boolean.valueOf(callExplicitlyString);
            m_callExplicitly = callExplicitly.booleanValue();
        }

        String preserveString = root.getAttributeStringValue(PRESERVE_FWD_CORRELATION);
        if (!ModelUtils.IsEmptyString(preserveString)) {
            Boolean preserve = Boolean.valueOf(preserveString);
            m_preserveForwardCorrelation = preserve.booleanValue();
        }

    }// end fromXiNode


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        ArrayList modelErrors = new ArrayList();
        BEModelBundle bundle = BEModelBundle.getBundle();
        addErrorIfTrue(m_URI == null, modelErrors, bundle.getString(SUBMACHINES_MUST_HAVE_A_PATH_SET));
        addErrorIfTrue(m_isConcurrentState, modelErrors, bundle.getString(SUBMACHINES_CANNOT_BE_CONCURRENT_STATES));
        Iterator entities = getEntities().iterator();
        while (entities.hasNext()) {
            StateEntity entity = (StateEntity) entities.next();
            if (!(entity instanceof StateStub)) {
                modelErrors.add(new ModelError(this, bundle.getString(SUBMACHINES_CAN_ONLY_CONTAIN_STUB_STATES)));
                break;
            }//endif
        }//endwhile
        return modelErrors;
    }// end getModelErrors


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    /**
     * Get the URI of this submachine.
     *
     * @return The URI of this submachine.
     */
    public String getSubmachineURI() {
        return m_URI;
    }// end getSubmachineURI


    /**
     * Set the URI of this submachine.
     *
     * @param URI The new URI of this submachine.
     */
    public void setSubmachineURI(
            String URI) {
        m_URI = URI;
    }// end setSubmachineURI


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        setAttributeStringValueSafe(root, URI_TAG, m_URI);
        setAttributeStringValueSafe(root, EXPLICIT_TAG, String.valueOf(m_callExplicitly));
        setAttributeStringValueSafe(root, PRESERVE_FWD_CORRELATION, String.valueOf(m_preserveForwardCorrelation));
        return root;
    }// end toXiNode


    public StateMachine getReferencedStateMachine() {
        String uri = this.getSubmachineURI();
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash == -1) {
            throw new RuntimeException("StateSubMachine: Incorrect URI format - " + uri);
        }

        final Ontology ontology = this.getOntology();
        if (null != ontology) {
            final int indexOfLastDot = uri.lastIndexOf('.');
            final String smUri = (indexOfLastDot < 0) ? uri : uri.substring(0, indexOfLastDot);
            final Entity sm = ontology.getEntity(smUri);
            if (sm instanceof StateMachine) {
                return (StateMachine) sm;
            }
        }

        String conceptPath = uri.substring(0, lastSlash);
        int lastDot = conceptPath.lastIndexOf('.'); //sometimes it could be abc.concept (So remove the .concept);
        String conceptName = conceptPath;
        if (lastDot != -1) {
            conceptName = conceptPath.substring(0, lastDot);
        }
        String smName = uri.substring(lastSlash + 1);

        Concept c = this.getOntology().getConcept(conceptName);
        if (c == null) {
            throw new RuntimeException("StateSubMachine: Invalid Concept Name - " + conceptName);
        }

        List l = c.getStateMachines();
        if (l == null) {
            throw new RuntimeException("StateSubMachineState: Invalid Machine Name - " + smName);
        }

        Iterator itr = l.iterator();
        while (itr.hasNext()) {
            StateMachine sm = (StateMachine) itr.next();
            if (smName.equals(sm.getName())) {
                return sm;
            }
        }
        throw new RuntimeException("StateSubMachine: Unknown Machine Reference - " + smName);

    }


    public boolean callExplicitly() {
        return m_callExplicitly;
    }


    public void setCallsExplicitly(boolean virtual) {
        m_callExplicitly = virtual;
    }


    public boolean preserveForwardCorrelate() {
        return m_preserveForwardCorrelation;
    }


    public void setPreserveForwardCorrelate(boolean preserve) {
        m_preserveForwardCorrelation = preserve;
    }
}// end class DefaultStateSubmachine
