package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.TimeDimensionImpl;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class TimeDimensionAdapter extends XmlAdapter<TimeDimensionImpl, TimeDimension> {

    @Override
    public TimeDimension unmarshal(TimeDimensionImpl v) throws Exception {
        return v;
    }

    @Override
    public TimeDimensionImpl marshal(TimeDimension v) throws Exception {
        return (TimeDimensionImpl) v;
    }

}
