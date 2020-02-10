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
public class AverageMemUsage extends SingleValueMetricFunction<Double> {
	
	Double memUsage;
	Double maxMem;

	public static final String PARAM1 = "PARAM1";
	public static final String PARAM2 = "PARAM2";
	
	
	@Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {
		
		double sum = getSum(context);
        long count = getCount(context);
        
        if(maxMem==0D)
			return 0D;
		Double percent=(memUsage/maxMem)*100;
		percent= (((double)((int)(percent*100)))/100);  
        
 
        
        double newSum = sum + percent;
        long newCount = ++count;

        setSum(context, newSum);
        setCount(context, newCount);

        return (newSum/newCount);
    }

	@Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrMem = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());
        
        String  attrMaxMem= measurement.getFunctionParamBinding(PARAM2);

        memUsage = getAttributeValue(fn.getAttribute(attrMem));
        maxMem = getAttributeValue(fn.getAttribute(attrMaxMem));

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
