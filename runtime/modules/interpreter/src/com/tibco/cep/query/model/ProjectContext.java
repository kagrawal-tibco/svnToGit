package com.tibco.cep.query.model;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Aug 29, 2007 Time: 5:09:03 PM To
 * change this template use File | Settings | File Templates.
 */
public interface ProjectContext extends RootContext {

    /**
     * @return RuleServiceProvider the associated ruleserviceprovider
     */
    RuleServiceProvider getRuleServiceProvider();

    /**
     * @return EntityRegistry that stores all the Entity objects.
     */
    EntityRegistry getEntityRegistry();

    /**
     * Returns the function Registry
     * 
     * @return FunctionRegistry
     */
    FunctionRegistry getFunctionRegistry();

    /**
     * @return Ontology the BE project ontology
     */
    Ontology getOntology();

    /**
     * Returns the logger
     * 
     * @return Logger
     */
    Logger getLogger();

}
