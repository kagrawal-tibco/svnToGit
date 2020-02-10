package com.tibco.be.jdbcstore.impl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;

public class StateMachineTimeoutDescription extends TimeEventDescription {

    /**
     *
     * @throws Exception
     */
    public StateMachineTimeoutDescription() throws Exception{
        super(StateMachineConceptImpl.StateTimeoutEvent.class.getName(), StateMachineConceptImpl.StateTimeoutEvent.class);
        //initialize();
    }

    protected void initialize() {

        EventProperty p1= new EventProperty();
        p1.propertyName = "currentTime";
        p1.index = 0;
        p1.type = RDFTypes.LONG_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "nextTime";
        p1.index = 1;
        p1.type = RDFTypes.LONG_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "closure";
        p1.index = 2;
        p1.type = RDFTypes.STRING_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "ttl";
        p1.index = 3;
        p1.type = RDFTypes.LONG_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "fired";
        p1.index = 4;
        p1.type = RDFTypes.BOOLEAN_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "smid";
        p1.index = 5;
        p1.type = RDFTypes.LONG_TYPEID;
        _properties.add(p1);

        p1= new EventProperty();
        p1.propertyName = "propertyName";
        p1.index = 6;
        p1.type = RDFTypes.STRING_TYPEID;
        _properties.add(p1);
    }
}
