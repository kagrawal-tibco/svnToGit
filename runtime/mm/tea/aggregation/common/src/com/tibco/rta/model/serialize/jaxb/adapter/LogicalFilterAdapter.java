package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.query.filter.LogicalFilter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LogicalFilterAdapter extends XmlAdapter<Object, LogicalFilter> {

    @Override
    public LogicalFilter unmarshal(Object v) throws Exception {
        return (LogicalFilter) v;
    }

    @Override
    public Object marshal(LogicalFilter v) throws Exception {
        return v;
    }

}
