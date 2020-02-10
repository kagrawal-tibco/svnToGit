package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.impl.MetadataElementImpl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAMS;

public class MeasurementMetricFunctionData extends MetadataElementImpl {

    private Map<String, String> fnBinding = new LinkedHashMap<String, String>();

    @XmlAttribute(name = ATTR_REF_NAME)
    private String fnName;

    public MeasurementMetricFunctionData(String fnName) {
        this.fnName = fnName;
    }

    public MeasurementMetricFunctionData() {

    }


    public String getMetricFunctionName() {
        return fnName;
    }

    public void addFnBinding(String paramName, String reference) {
        fnBinding.put(paramName, reference);
    }

    @XmlElement(name = ELEM_FUNCTION_PARAMS)
    @XmlJavaTypeAdapter(ParamMapAdapter.class)
    public Map<String, String> getFnParams() {
        return fnBinding;
    }

    private void setFnParams(Map<String, String> fnBindings) {
        fnBinding = fnBindings;
    }

}
