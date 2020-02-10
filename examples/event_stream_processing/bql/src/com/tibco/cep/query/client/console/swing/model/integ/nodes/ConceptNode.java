package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.query.client.console.swing.util.Registry;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author ksubrama
 */
public class ConceptNode extends AbstractNode {
    private static final long serialVersionUID = 1L;
    private final Concept concept;
    private final Map<String, String> parentProps;
    public ConceptNode(Concept concept) {
        super(concept);
        this.concept = concept;
        parentProps = getParentProperties(concept.getParentConcept());
        initProperties();
    }

    @Override
    protected void initProperties() {
        properties.put(NAME, getNonNullValue(concept.getName()));
        properties.put(NAMESPACE, getNonNullValue(concept.getNamespace()));
        properties.put(PATH, getNonNullValue(concept.getFullPath()));
        if(concept.getParentConcept() != null) {
            // Parent Properties
            properties.put(PARENT_PROPERTIES, parentProps);
        }
    }

    private String[][] getConceptProperties() {
        String[][] props = new String[3][2];
        // Name
        props[0][0] = NAME;
        props[0][1] = concept.getName();
        // Namespace
        props[1][0] = NAMESPACE;
        props[1][1] = concept.getNamespace();
        // Path
        props[2][0] = PATH;
        props[2][1] = concept.getFullPath();
        return props;
    }

    private Map<String, String> getParentProperties(Concept parent) {
        if(parent == null) {
            return Collections.emptyMap();
        }
        Map<String, String> parentProperties = new TreeMap<String, String>();
        ConceptNode parentNode = Registry.getRegistry().getNodeCache().getConceptNode(parent);
        String[][] props = parentNode.getConceptProperties();
        for(int i = 0; i < props.length; i++) {
            if(props[i] != null && props[i][0] != null && props[i][1] != null) {
                parentProperties.put(props[i][0], props[i][1]);
            }
        }
        return parentProperties;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
