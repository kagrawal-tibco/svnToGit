package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Computes the average value at each node
 */

public class AverageMetric extends SingleValueMetricFunction<Double> {

    double qty;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {

        double sum = getSum(context);
        long count = getCount(context);

        double newSum = sum + qty;
        long newCount = ++count;

        setSum(context, newSum);
        setCount(context, newCount);

        return newSum / newCount;
    }

    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        if (fn.getAttribute(attrName) instanceof Number) {
            Number number = (Number) fn.getAttribute(attrName);
            if (number instanceof Double) {
                qty = number.doubleValue();
            } else if (number instanceof Float) {
                qty = number.doubleValue();
            } else if (number instanceof Long) {
                qty = number.doubleValue();
            } else if (number instanceof Integer) {
                qty = number.doubleValue();
            } else if (number instanceof Short) {
                qty = number.doubleValue();
            } else if (number instanceof Byte) {
                qty = number.doubleValue();
            } else {
                qty = 0.0D;
            }
        } else {
            qty = 0.0D;
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
