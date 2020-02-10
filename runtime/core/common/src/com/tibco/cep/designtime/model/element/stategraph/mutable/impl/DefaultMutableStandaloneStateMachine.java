package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;

import java.awt.Rectangle;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStandaloneStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateComposite;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 30, 2009
* Time: 4:53:25 PM
*/
public class DefaultMutableStandaloneStateMachine
        extends DefaultMutableStateMachine
        implements MutableStandaloneStateMachine {


    public static final String PERSISTENCE_NAME = "StandaloneStateMachine";
    public static final ExpandedName XNAME_CONCEPT = ExpandedName.makeName("concept");


    public DefaultMutableStandaloneStateMachine(
            DefaultMutableOntology ontology,
            String name,
            DefaultMutableFolder folder,
            Rectangle bounds,
            Concept ownerConcept,
            MutableStateComposite rootState) {
        super(ontology, name, folder, bounds, ownerConcept, rootState);
    }


    public static DefaultMutableStandaloneStateMachine createDefaultStandaloneStateMachineFromNode(
            XiNode root)
            throws ModelException {
        final String name = root.getAttributeStringValue(NAME_NAME);
        final String folderPath = root.getAttributeStringValue(FOLDER_NAME);
        final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null,
                String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultMutableFolder folder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);

        final DefaultMutableStandaloneStateMachine sm = new DefaultMutableStandaloneStateMachine(
                null, name, folder, null, null, null);

        sm.fromXiNode(root);

        return sm;
    }


    public void fromXiNode(
            XiNode root)
            throws ModelException {
        super.fromXiNode(root);

        this.setOwnerConceptPath(root.getAttributeStringValue(XNAME_CONCEPT));

//        final Ontology ontology = this.getOntology();
//        if ((null != ontology) && (null != this.ownerConceptPath)) {
//            final Concept c = ontology.getConcept(this.ownerConceptPath);
//            if (null != c) {
//                this.setOwnerConcept(c);
//            }
//        }
    }


    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }

//    public StateMachineRuleSet getRuleSet() {
//        return this.ruleSet;
//    }
//
//
//    public void setOntology(
//            MutableOntology ontology) {
//
//        final Ontology oldOntology = this.getOntology();
//
//        super.setOntology(ontology);
//        this.ruleSet.setOntology(ontology);
//
//        if ((null == this.getOwnerConcept())
//                && (null != ontology)
//                && ((null == oldOntology) || !oldOntology.equals(ontology))) {
//            final Concept c = ontology.getConcept(this.ownerConceptPath);
//            if (null != c) {
//                super.setOwnerConcept(c);
//            }
//        }
//    }
//
//
//    public void setOwnerConcept(
//            Concept ownerConcept) {
//        this.ruleSet.setConceptPath((null == ownerConcept) ? null : ownerConcept.getFullPath());
//        super.setOwnerConcept(ownerConcept);
//    }
//
//
//    public void setOwnerConceptPath(
//            String ownerConceptPath) {
//        super.setOwnerConceptPath(ownerConceptPath);
//        this.ruleSet.setConceptPath(ownerConceptPath);
//    }


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        final XiNode node = super.toXiNode(factory);

        final Concept c = this.getOwnerConcept();
        final String conceptPath = (null == c) ? this.ownerConceptPath : c.getFullPath();
        node.setAttributeStringValue(XNAME_CONCEPT, (null == conceptPath) ? "" : conceptPath);

        return node;
    }// end toXiNode
}
