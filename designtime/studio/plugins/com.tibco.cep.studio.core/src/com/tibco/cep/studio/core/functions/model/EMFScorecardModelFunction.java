package com.tibco.cep.studio.core.functions.model;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFScorecardModelFunction extends EMFConceptModelFunction {

    String getDescription() {
        return "Accesses the Scorecard of type <" + getModel().getFullPath() + ">.";
    }
    
    public EMFScorecardModelFunction(Concept model, ExpandedName functionName) {
        super(model, functionName);
    }

    public EMFScorecardModelFunction(Concept model, ExpandedName functionName, String javaFunctionName) {
        super(model, functionName, javaFunctionName);
    }

    protected void putArgs(boolean refresh) {
        if(fParameterTypes == null) fParameterTypes = new Class[0] ;
        if(fParameterNames == null) fParameterNames = new String[0];
        if(fEntityArguments == null) fEntityArguments = new Entity[0];
    }


}
