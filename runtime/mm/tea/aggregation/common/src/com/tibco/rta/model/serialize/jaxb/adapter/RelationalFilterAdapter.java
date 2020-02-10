package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.query.filter.RelationalFilter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RelationalFilterAdapter extends XmlAdapter<Object, RelationalFilter> {

    @Override
    public RelationalFilter unmarshal(Object v) throws Exception {
        return (RelationalFilter) v;
    }

    @Override
    public Object marshal(RelationalFilter v) throws Exception {
        return v;
    }

}
