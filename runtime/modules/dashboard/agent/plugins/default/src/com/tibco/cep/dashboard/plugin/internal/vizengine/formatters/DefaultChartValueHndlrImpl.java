package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.psvr.mal.model.types.ScaleEnum;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;

public class DefaultChartValueHndlrImpl implements OutputFieldValueProcessor {

    private static final double THOUSAND = 1000;
    
    private static final double MILLION = THOUSAND * THOUSAND;
    
    private static final double BILLION = THOUSAND * MILLION;
    
    private static final double TRILLION = THOUSAND * BILLION;
    
    @SuppressWarnings("unused")
	private static final double QUADRILLION = THOUSAND * TRILLION;
    
    private Map<KEY,Object> nullValueForms;
    private String fieldNameToBeScaled;
    private ScaleEnum scale;
    
    DefaultChartValueHndlrImpl() {
        nullValueForms = new HashMap<KEY, Object>();
        nullValueForms.put(KEY.DISPLAY,"-");
        nullValueForms.put(KEY.TOOLTIP,"[no data]");
    }
    
    DefaultChartValueHndlrImpl(String fieldName,ScaleEnum scale) {
        this();
        this.fieldNameToBeScaled = fieldName;
        this.scale = scale;
    }    
    
    public Map<KEY, Object> process(String fieldName,FieldValue value) {
        if (value == null || value.isNull() == true){
            return nullValueForms;
        }

        if (fieldName.equals(fieldNameToBeScaled) == true){
            Comparable<?> actualValue = value.getValue();
			if ((actualValue instanceof Number) == false){
                //PATCH throw new IllegalArgumentException("cannot scale a non numerical value for "+fieldNameToBeScaled+". Expected: Number, got: "+value.getDataType());
				return Collections.emptyMap();
            }
            HashMap<KEY,Object> newValues = new HashMap<KEY,Object>();
            Object scaledValue = getScaledValue((Number) actualValue);
            newValues.put(KEY.DISPLAY, scaledValue);
            newValues.put(KEY.TOOLTIP, scaledValue);
            return newValues;
        }
        return Collections.emptyMap();
    }

    private Number getScaledValue(Number value) {
        switch (scale.getType()) {
            case ScaleEnum.NORMAL_TYPE:
            case ScaleEnum.NONE_TYPE:
                return value;
            case ScaleEnum.THOUSANDS_TYPE:
                return new Double(value.doubleValue()/THOUSAND);
            case ScaleEnum.MILLIONS_TYPE:
                return new Double(value.doubleValue()/MILLION); 
            case ScaleEnum.BILLIONS_TYPE:
                return new Double(value.doubleValue()/BILLION); 
            case ScaleEnum.TRILLIONS_TYPE:
                default:
                return new Double(value.doubleValue()/TRILLION); 
        }
    }
   
}
