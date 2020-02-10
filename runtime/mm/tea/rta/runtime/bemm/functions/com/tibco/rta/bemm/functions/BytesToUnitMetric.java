package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;


/**
 * This metric will return the Memory Usage converting using the base unit.
 * 
 * @author vasharma
 *
 */
public class BytesToUnitMetric extends SingleValueMetricFunction<Double> {
	
	private static String UNIT_MB="MB";
	private static String UNIT_KB="KB";
	private static String UNIT_GB="GB";
	private static String UNIT_BYTES="BYTES";
	
	//SET UNIT's value to the following variable
	private static int ONE_UNIT = 1048576; // DEFAULTS TO MB
	private String unitType="";
	
	Double memUsage;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {
    	
    	ONE_UNIT=getOneUnitValue();
    	//Two precision points
    	return (((double)((int)((memUsage/ONE_UNIT)*100)))/100);
    }


    private int getOneUnitValue() {
		
    	if(unitType!=null&&!unitType.isEmpty())
    	{	
    		if(UNIT_BYTES.equals(unitType))
    			ONE_UNIT=1;
    		if(UNIT_KB.equals(unitType))
    			ONE_UNIT=1024;
    		if(UNIT_MB.equals(unitType))
    			ONE_UNIT=1048576;
    		if(UNIT_GB.equals(unitType))
    			ONE_UNIT=1073741824;
    	}
		return ONE_UNIT;
	}


	@Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);
        
        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        memUsage = getAttributeValue(fn.getAttribute(attrName));
        unitType=measurement.getUnitOfMeasurement();
    }
    
    private Double getAttributeValue(Object attribute) {
		if (attribute instanceof Long) {
			return ((Long) attribute).doubleValue();
		} else if (attribute instanceof Double) {
			return ((Double) attribute).doubleValue();
		} else if (attribute instanceof Integer) {
			return ((Integer) attribute).doubleValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).doubleValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).doubleValue();
		} else {
			return 0D;
		}
	}
	
	
}
