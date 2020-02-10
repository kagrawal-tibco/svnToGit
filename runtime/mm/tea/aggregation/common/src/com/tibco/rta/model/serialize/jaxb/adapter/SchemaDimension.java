package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.DimensionImpl;
import com.tibco.rta.model.impl.TimeDimensionImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_TIME_DIMENSION_NAME;

public class SchemaDimension {

    private List<Dimension> nonTimeDims = new ArrayList<Dimension>();
    private List<TimeDimension> timeDims = new ArrayList<TimeDimension>();

    public SchemaDimension(Collection<Dimension> dimensions) {
        for (Dimension d : dimensions) {
            if (d instanceof TimeDimension) {
                timeDims.add((TimeDimensionImpl) d);
            } else {
                nonTimeDims.add(d);
            }
        }
    }

    public SchemaDimension() {
    }


    private List<TimeDimension> getTimeDimension() {
        return timeDims;
    }


    private List<Dimension> getNonTimeDimension() {
        return nonTimeDims;
    }

    @XmlElement(name = ELEM_TIME_DIMENSION_NAME, type = TimeDimensionImpl.class)
    @XmlJavaTypeAdapter(value = TimeDimensionAdapter.class, type = TimeDimensionImpl.class)
    private void setTimeDimension(List<TimeDimension> timeDim) {
        timeDims = timeDim;
    }


    @XmlElement(name = ELEM_DIMENSION_NAME, type = DimensionImpl.class)
    @XmlJavaTypeAdapter(value = DimensionAdapter.class, type = DimensionImpl.class)
    private void setNonTimeDimension(List<Dimension> nonTime) {
        nonTimeDims = nonTime;
    }

}
