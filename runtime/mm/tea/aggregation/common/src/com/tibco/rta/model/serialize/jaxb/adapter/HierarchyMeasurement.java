package com.tibco.rta.model.serialize.jaxb.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;

public class HierarchyMeasurement {


    private String measurementName;

    public HierarchyMeasurement(String name) {
        measurementName = name;
    }

    public HierarchyMeasurement() {
    }

    @XmlAttribute(name = ATTR_REF_NAME)
    public String getMeasurementName() {
        return measurementName;
    }

    private void setMeasurementName(String name) {
        measurementName = name;
    }

}

