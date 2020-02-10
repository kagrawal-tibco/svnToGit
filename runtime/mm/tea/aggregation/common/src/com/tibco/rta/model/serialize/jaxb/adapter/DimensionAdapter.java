package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.impl.DimensionImpl;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DimensionAdapter extends XmlAdapter<DimensionImpl, Dimension> {

    @Override
    public Dimension unmarshal(DimensionImpl v) throws Exception {
        return v;
    }

    @Override
    public DimensionImpl marshal(Dimension v) throws Exception {
        return (DimensionImpl) v;
    }


}
