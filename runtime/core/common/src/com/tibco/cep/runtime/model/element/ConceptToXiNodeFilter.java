package com.tibco.cep.runtime.model.element;

/*
* Author: Ashwin Jayaprakash Date: Aug 21, 2008 Time: 10:56:32 PM
*/
public interface ConceptToXiNodeFilter<S> {
    S beginSession();

    boolean allow(S session, Property property);

    void endSession(S session);
}
