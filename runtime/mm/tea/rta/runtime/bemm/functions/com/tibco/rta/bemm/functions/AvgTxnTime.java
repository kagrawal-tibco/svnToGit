package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * @author vasharma
 *
 */
public class AvgTxnTime extends SingleValueMetricFunction<Double> {

	Long totalTxn;
	Double avgTxnTime;

	public static final String PARAM1 = "PARAM1";
	public static final String PARAM2 = "PARAM2";

	@Override
	public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {

		if((context.getTupleValue("totalTxn")!=null) && (context.getTupleValue("avgTxnTime")!=null)){
			Double prevAvgTxnTime=(Double) context.getTupleValue("avgTxnTime");
			Long prevTotalTxn=(Long) context.getTupleValue("totalTxn");

			Double total=(avgTxnTime*totalTxn)- (prevAvgTxnTime*prevTotalTxn);
			Long diff=totalTxn-prevTotalTxn;
			
			context.setTuple("avgTxnTime", avgTxnTime);
			context.setTuple("totalTxn", totalTxn);
			
			if(diff>0 && total >0){
				context.setTuple("prvAvgTime", total/diff);
				return total/diff;
			}
			else{
				if(context.getTupleValue("prvAvgTime")!=null){
					return (Double) context.getTupleValue("prvAvgTime");
				}
			}
		}
		else{
			context.setTuple("avgTxnTime", avgTxnTime);
			context.setTuple("totalTxn", totalTxn);
			context.setTuple("prvAvgTime", avgTxnTime);
		}
		return avgTxnTime;
		
	}


	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);
		String attrTotalTxn = measurement.getFunctionParamBinding(PARAM1);
		totalTxn = getAttributeLongValue(fn.getAttribute(attrTotalTxn));
		String attrAvgTxnTime = measurement.getFunctionParamBinding(PARAM2);
		avgTxnTime = getAttributeDoubleValue(fn.getAttribute(attrAvgTxnTime));
	}

	private Double getAttributeDoubleValue(Object attribute) {
		if(attribute instanceof Number){
			Number number = (Number) attribute;
			if (number instanceof Double) {
				return number.doubleValue();
			} else if (number instanceof Float) {
				return number.doubleValue();
			} else if (number instanceof Long) {
				return number.doubleValue();
			} else if (number instanceof Integer) {
				return number.doubleValue();
			} else if (number instanceof Short) {
				return number.doubleValue();
			} else if (number instanceof Byte) {
				return number.doubleValue();
			} else {
				return 0.0D;
			}
		} else {
			return 0.0D;
		}
	}

	private Long getAttributeLongValue(Object attribute) {
		if (attribute instanceof Long) {
			return ((Long) attribute).longValue();
		} else if (attribute instanceof Integer) {
			return ((Integer) attribute).longValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).longValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).longValue();
		} else {
			return 0L;
		}
	}

}
