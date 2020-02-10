/**
 * 
 */
package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * @author ssinghal
 *
 */
public class AvgCpuUsagePercentMetric extends SingleValueMetricFunction<Double> {
	
	Double cpuLoad;

    public static final String PARAM1 = "PARAM1";
    
    @Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {
		
		double sum = getSum(context);
        long count = getCount(context);
        
        double cpuUsagePercentCurrent = (((double)((int)(cpuLoad*100000)))/1000);  
        
        double newSum = sum + cpuUsagePercentCurrent;
        long newCount = ++count;

        setSum(context, newSum);
        setCount(context, newCount);

        return (newSum/newCount);
    }
	
	
	@Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        if (fn.getAttribute(attrName) instanceof Number) {
            Number number = (Number) fn.getAttribute(attrName);
            if (number instanceof Double) {
            	cpuLoad = number.doubleValue();
            } else if (number instanceof Float) {
            	cpuLoad = number.doubleValue();
            } else if (number instanceof Long) {
            	cpuLoad = number.doubleValue();
            } else if (number instanceof Integer) {
            	cpuLoad = number.doubleValue();
            } else if (number instanceof Short) {
            	cpuLoad = number.doubleValue();
            } else if (number instanceof Byte) {
            	cpuLoad = number.doubleValue();
            } else {
            	cpuLoad = 0.0D;
            }
        }else {
        	cpuLoad = 0.0D;
        }
    }
	
	 public static double getSum(RtaNodeContext context) {
	        if (context.getTupleValue("sum") != null) {
	            return (Double) context.getTupleValue("sum");
	        }
	        return 0.0D;
	 }
	
	 public void setSum(RtaNodeContext context, double sum) {
	     context.setTuple("sum", sum);
	 }
	
	
	 public static long getCount(RtaNodeContext context) {
	     if (context.getTupleValue("count") != null) {
	         return (Long) context.getTupleValue("count");
	     }
	     return 0L;
	 }
	
	 public static void setCount(RtaNodeContext context, long count) {
	     context.setTuple("count", count);
	 }

}
