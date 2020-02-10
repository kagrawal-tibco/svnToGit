package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.impl.MetadataElementImpl;

import javax.xml.bind.annotation.XmlAttribute;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_COMPUTE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_EXCLUDE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;

public class HierarchyDimension extends MetadataElementImpl {

    @XmlAttribute(name = ATTR_REF_NAME)
    private String dimName;

    private String compute;


    private String excluded;

    public HierarchyDimension(String dimName, boolean compute, String excluded) {
        this.dimName = dimName;
        this.compute = "" + compute;
        this.excluded = excluded;
    }

    protected HierarchyDimension() {

    }

    public String getReferenceDimension() {
        return dimName;
    }

    @XmlAttribute(name = ATTR_COMPUTE_NAME)
    public String getCompute() {
        if (compute != null && !Boolean.parseBoolean(compute)) {
            return "false";
        }
        return null;
    }

    private void setCompute(String value) {
        if (value == null) {
            compute = "true";
        } else {
            compute = value;
        }
    }

    @XmlAttribute(name = ATTR_EXCLUDE_NAME)
    public String getExcluded() {
        return excluded;
    }

    private void setExcluded(String name) {
        excluded = name;
    }

}
