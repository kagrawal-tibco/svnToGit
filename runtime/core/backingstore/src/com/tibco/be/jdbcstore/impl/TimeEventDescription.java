package com.tibco.be.jdbcstore.impl;

import com.tibco.be.model.rdf.RDFTypes;

/**
 *
 */
public class TimeEventDescription extends SimpleEventDescription {

    public TimeEventDescription(String timeEventClassName, Class timeEventClass) throws Exception {
        super(timeEventClassName, timeEventClass);
        //initialize();
    }

    protected void initialize() throws Exception{
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
    }
}
