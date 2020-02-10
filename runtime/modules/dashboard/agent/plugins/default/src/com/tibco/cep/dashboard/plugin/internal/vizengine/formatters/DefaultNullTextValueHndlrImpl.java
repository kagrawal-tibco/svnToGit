package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;


public class DefaultNullTextValueHndlrImpl implements OutputFieldValueProcessor {

    private Map<KEY,Object> valueForms;
    
    DefaultNullTextValueHndlrImpl() {
        valueForms = new HashMap<KEY,Object>();
        valueForms.put(KEY.DISPLAY,"-");
        valueForms.put(KEY.TOOLTIP,"[no data]");
    }
    
    public Map<KEY, Object> process(String fieldName, FieldValue value) {
        if (value == null || value.isNull() == true){
            return valueForms;
        }
        return Collections.emptyMap();
    }    
}
