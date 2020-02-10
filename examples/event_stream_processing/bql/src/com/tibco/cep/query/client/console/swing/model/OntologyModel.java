package com.tibco.cep.query.client.console.swing.model;

import com.tibco.cep.query.client.console.swing.model.integ.BEOntologyModelAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author ksubrama
 */
public class OntologyModel extends DefaultTreeModel {
    private static final long serialVersionUID = 1L;
    public static final String EVENTS = "Events";
    public static final String SIMPLE_EVENTS = "Simple Events";
    public static final String TIME_EVENTS = "Time Events";
    public static final String CONCEPTS = "Concepts";
    public static final String RULES = "Rules";
    public static final String RULE_FUNCTIONS = "Rule Functions";
    private DefaultMutableTreeNode eventNode, conceptNode, ruleNode, ruleFunctionNode;
    
    public OntologyModel() {
        super(new DefaultMutableTreeNode("Ontology"), true);
        addDefaultChildren();
        BEOntologyModelAdapter adapter = new BEOntologyModelAdapter(this);
        adapter.loadOntology();
    }

    private void addDefaultChildren() {
        int index = 0;
        DefaultMutableTreeNode mutableRoot = (DefaultMutableTreeNode)root;
        // Event node.
        eventNode = new DefaultMutableTreeNode(EVENTS);
        insertNodeInto(eventNode, mutableRoot, index++);
        // Concept node
        conceptNode = new DefaultMutableTreeNode(CONCEPTS);
        insertNodeInto(conceptNode, mutableRoot, index++);
        // Rule node
        ruleNode = new DefaultMutableTreeNode(RULES);
        insertNodeInto(ruleNode, mutableRoot, index++);
        // Rule Function node
        ruleFunctionNode = new DefaultMutableTreeNode(RULE_FUNCTIONS);
        insertNodeInto(ruleFunctionNode, mutableRoot, index++);
    }

    public DefaultMutableTreeNode getConceptNode() {
        return conceptNode;
    }

    public DefaultMutableTreeNode getRuleFunctionNode() {
        return ruleFunctionNode;
    }

    public DefaultMutableTreeNode getRuleNode() {
        return ruleNode;
    }

    public DefaultMutableTreeNode getEventNode() {
        return eventNode;
    }
}
