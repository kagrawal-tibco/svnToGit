package com.tibco.cep.query.client.console.swing.model.integ;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleFunction.Validity;
import com.tibco.cep.query.client.console.swing.model.OntologyModel;
import com.tibco.cep.query.client.console.swing.model.integ.nodes.ConceptNode;
import com.tibco.cep.query.client.console.swing.model.integ.nodes.EventNode;
import com.tibco.cep.query.client.console.swing.model.integ.nodes.RuleFunctionNode;
import com.tibco.cep.query.client.console.swing.model.integ.nodes.RuleNode;
import com.tibco.cep.query.client.console.swing.util.Registry;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

/**
 *
 * @author ksubrama
 */
@SuppressWarnings("unchecked")
public class BEOntologyModelAdapter {

    private final OntologyModel model;
    private final RuleServiceProvider rsProvider;
    private Ontology ontology;

    public BEOntologyModelAdapter(OntologyModel model) {
        this.model = model;
        rsProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
        initOntologyProvider();
    }

    private void initOntologyProvider() {
        if (rsProvider != null) {
            ontology = rsProvider.getProject().getOntology();
        }
    }

    public void loadOntology() {
        addNodes(TypeManager.TYPE_SIMPLEEVENT, model.getEventNode(), sortEntities(getEvents()));
        addNodes(TypeManager.TYPE_CONCEPT, model.getConceptNode(), sortEntities(getConcepts()));
        addNodes(TypeManager.TYPE_RULE, model.getRuleNode(), sortEntities(getRules()));
        addNodes(TypeManager.TYPE_RULEFUNCTION, model.getRuleFunctionNode(), 
                sortEntities(getRuleFunctions()));
    }

    private Collection<? extends Entity> getRules() {
        // This null check is required for Netbeans swing editor.
        // Do not remove this.
        if(ontology == null) {
            return Collections.emptyList();
        }
        return ontology.getRules();
    }

    private Collection<? extends Entity> getRuleFunctions() {
        // This null check is required for Netbeans swing editor.
        // Do not remove this.
        if(ontology == null) {
            return Collections.emptyList();
        }
        return ontology.getRuleFunctions();
    }

    private Collection<? extends Entity> getConcepts() {
        // This null check is required for Netbeans swing editor.
        // Do not remove this.
        if(ontology == null) {
            return Collections.emptyList();
        }
        return ontology.getConcepts();
    }

    private Collection<? extends Entity> getEvents() {
        // This null check is required for Netbeans swing editor.
        // Do not remove this.
        if(ontology == null) {
            return Collections.emptyList();
        }
        return ontology.getEvents();
    }

    private void addNodes(int type, final DefaultMutableTreeNode node,
            final Collection<? extends Entity> entities) {
        int index = 0;
        switch(type) {
            case TypeManager.TYPE_CONCEPT:
                for (Entity entity : entities) {
                    model.insertNodeInto(Registry.getRegistry().getNodeCache().getConceptNode((Concept)entity),
                            node, index++);
                }
                break;
            case TypeManager.TYPE_SIMPLEEVENT:
                for (Entity entity : entities) {
                    model.insertNodeInto(new EventNode((Event)entity),
                            node, index++);
                }
                break;
            case TypeManager.TYPE_RULE:
                for (Entity entity : entities) {
                    model.insertNodeInto(new RuleNode((Rule)entity),
                            node, index++);
                }
                break;
            case TypeManager.TYPE_RULEFUNCTION:
                for (Entity entity : entities) {
                    RuleFunction ruleFunction = (RuleFunction)entity;
                    if(ruleFunction.isVirtual() == true ||
                            ruleFunction.getValidity() != Validity.QUERY) {
                        continue;
                    }
                    model.insertNodeInto(new RuleFunctionNode(ruleFunction),
                            node, index++);
                }
                break;
        }
    }

    private List<Entity> sortEntities(Collection<? extends Entity> entities) {
        List<Entity> result = new LinkedList<Entity>();
        Map<String, Entity> sortedMap = new TreeMap<String, Entity>();
        for(Entity entity : entities) {
            sortedMap.put(entity.getFullPath(), entity);
        }
        for(String key : sortedMap.keySet()) {
            result.add(sortedMap.get(key));
        }
        return result;
    }
}
