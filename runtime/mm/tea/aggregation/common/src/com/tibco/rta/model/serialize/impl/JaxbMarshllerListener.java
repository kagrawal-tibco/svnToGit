package com.tibco.rta.model.serialize.impl;
import javax.xml.bind.Marshaller;

import com.tibco.rta.model.serialize.jaxb.adapter.MeasurementMetricFunctionData;

public class JaxbMarshllerListener extends Marshaller.Listener {
	int i =0;
	
	@Override
    public void beforeMarshal(Object source) {
		if (source instanceof MeasurementMetricFunctionData) {
    	}
    }
		

    @Override
    public void afterMarshal(Object source) {
    	
        
    }
}
