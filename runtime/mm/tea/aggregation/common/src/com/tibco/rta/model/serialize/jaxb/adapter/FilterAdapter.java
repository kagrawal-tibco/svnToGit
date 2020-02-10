package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.query.filter.Filter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FilterAdapter extends XmlAdapter<Object, Filter> {

    @Override
    public Filter unmarshal(Object v) throws Exception {
        return (Filter) v;
    }

    @Override
    public Object marshal(Filter v) throws Exception {
        return v;
    }

}
