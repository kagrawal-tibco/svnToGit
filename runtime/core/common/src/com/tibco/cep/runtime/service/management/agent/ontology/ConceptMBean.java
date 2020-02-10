package com.tibco.cep.runtime.service.management.agent.ontology;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;

import javax.management.ObjectName;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/15/12
 * Time: 6:59 PM
 */
public class ConceptMBean extends OntologyMBean {
    private Concept concept;

    public ConceptMBean(Concept concept, ObjectName on, Logger logger) {
        super(on, logger);
        this.concept = concept;
        reLoadProps();
    }


    @Override
    protected void reLoadProps() {
        props = concept != null ? concept.getProperties() : null;
    }

}
