package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.types.ScaleEnum;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;

public final class FormatterUtils {
    
    private static OutputFieldValueProcessor DEFAULT_NULL_CATEGORY_VALUE_HNDLR = new DefaultNullCategoryValueHndlrImpl();
    private static OutputFieldValueProcessor DEFAULT_NULL_CHART_VALUE_HNDLR = new DefaultChartValueHndlrImpl();
    private static OutputFieldValueProcessor DEFAULT_NULL_TEXT_VALUE_HNDLR = new DefaultNullTextValueHndlrImpl();
    
    public static OutputFieldValueProcessor getDefaultNullCategoryImpl(){
    	return DEFAULT_NULL_CATEGORY_VALUE_HNDLR;
    }
    
    public static OutputFieldValueProcessor getDefaultNullValueDataHandler(MALComponent component,MALFieldMetaInfo scaledField,ScaleEnum scale){
    	String chartDefinitionType = component.getDefinitionType();
    	if(chartDefinitionType.equals("ChartComponent") || chartDefinitionType.equals("TrendChartComponent")){
            if (scaledField != null && scale != null){
                return new DefaultChartValueHndlrImpl(scaledField.getName(),scale);
            }
            return DEFAULT_NULL_CHART_VALUE_HNDLR;
    	}else if(chartDefinitionType.equals("TextComponent")){
    		return DEFAULT_NULL_TEXT_VALUE_HNDLR;
    	}
    	return null;
    }

}