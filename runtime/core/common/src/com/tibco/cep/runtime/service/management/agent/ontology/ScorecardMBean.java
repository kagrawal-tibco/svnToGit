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
public class ScorecardMBean extends OntologyMBean {
    private Concept scorecard;

    public ScorecardMBean(Concept scorecard, ObjectName on, Logger logger) {
        super(on, logger);
        this.scorecard = scorecard;
        reLoadProps();
    }


    @Override
    protected void reLoadProps() {
        props = scorecard != null ? scorecard.getProperties() : null;
    }

}
