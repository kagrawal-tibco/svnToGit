package com.tibco.rta.runtime.metric.functions;

import java.math.BigDecimal;

import com.tibco.rta.Fact;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.metric.util.MetricFunctionUtils;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/1/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class NthPercentileMetric extends SingleValueMetricFunction<BigDecimal> {

    protected BigDecimal qty;

    public static final String PARAM1 = "PARAM1";

    protected static final BigDecimal ZERO = new BigDecimal(0);

    @Override
    public void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fact, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        Object value = fact.getAttribute(attrName);
        qty = MetricFunctionUtils.objectToBigDecimal(value);
    }

    public BigDecimal getSum(RtaNodeContext context) {
        if (context.getTupleValue("sum") != null) {
            Object value = context.getTupleValue("sum");
            return MetricFunctionUtils.objectToBigDecimal(value);
        }
        return new BigDecimal(0D);
    }

    public void setSum(RtaNodeContext context, BigDecimal sum) {
        context.setTuple("sum", sum);
    }

    public long getCount(RtaNodeContext context) {
        if (context.getTupleValue("count") != null) {
            Object value = context.getTupleValue("count");
            return (Long) value;
        }
        return 0L;
    }

    public void setCount(RtaNodeContext context, long count) {
        context.setTuple("count", count);
    }

    public BigDecimal getSumOfSquare(RtaNodeContext context) {
        if (context.getTupleValue("sumofsquare") != null) {
            Object value = context.getTupleValue("sumofsquare");
            return MetricFunctionUtils.objectToBigDecimal(value);
        }
        return new BigDecimal(0D);
    }

    public void setSumOfSquare(RtaNodeContext context, BigDecimal sumOfSquare) {
        context.setTuple("sumofsquare", sumOfSquare);
    }

}
