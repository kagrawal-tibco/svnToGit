package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.query.QueryDef;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class QueryDefAdapter extends XmlAdapter<Object, QueryDef> {

    @Override
    public QueryDef unmarshal(Object v) throws Exception {
        return (QueryDef) v;
    }

    @Override
    public Object marshal(QueryDef v) throws Exception {
        return v;
    }

}
