package com.tibco.be.model.functions.impl;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * @author ishaan
 * @version Mar 15, 2005, 3:17:09 PM
 */
public class ScorecardModelFunction extends ConceptModelFunctionImpl {


    String getDescription() {
        return "Accesses the Scorecard of type <" + model.getFullPath() + ">.";
    }

    /**
     * @param model
     */
    public ScorecardModelFunction(Concept model, ExpandedName functionName) {
        super(model, functionName);
    }

    /**
     * @param model
     * @param functionName
     * @param javaFunctionName
     */
    public ScorecardModelFunction(Concept model, ExpandedName functionName, String javaFunctionName) {
        super(model, functionName, javaFunctionName);
    }

    protected void putArgs(boolean refresh) {
        if(parameterTypes == null) parameterTypes = new Class[0] ;
        if(parameterNames == null) parameterNames = new String[0];
        if(entityArguments == null) entityArguments = new Entity[0];
    }
}
