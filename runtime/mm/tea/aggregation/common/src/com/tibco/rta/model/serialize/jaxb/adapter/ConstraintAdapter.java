package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.rule.InvokeConstraint;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ConstraintAdapter extends XmlAdapter<Object, InvokeConstraint> {

    @Override
    public InvokeConstraint unmarshal(Object v) throws Exception {
        return (InvokeConstraint) v;
    }

    @Override
    public Object marshal(InvokeConstraint v) throws Exception {
        return v;
    }

}
