package com.tibco.rta.runtime.metric.functions;

import java.math.BigDecimal;
import java.math.MathContext;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.log.Level;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.util.MetricFunctionUtils;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * @author bgokhale
 *         <p/>
 *         +2-sigma from mean assuming normal distribution.
 */

public class NinetyFifthPercentileMetric extends NthPercentileMetric {

    @Override
    public BigDecimal compute(MetricNode metricNode, SingleValueMetric<BigDecimal> metric, RtaNodeContext context) {

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Computing TP95 for : %s", metric);
        }
        BigDecimal newSum = getSum(context);
        newSum = newSum.add(qty);
        long newCountLong = getCount(context);
        BigDecimal newCount = new BigDecimal(newCountLong);

        // for calulation purposes using BigDecimal and for storing count using long
        newCount = newCount.add(new BigDecimal(1));
        newCountLong = newCountLong + 1;

        BigDecimal newSumSq = getSumOfSquare(context);
        newSumSq = newSumSq.add(qty.pow(2));

        BigDecimal mean = newSum.divide(newCount, MathContext.DECIMAL128);

        BigDecimal temp = newSumSq.subtract((newSum.pow(2).divide(newCount, MathContext.DECIMAL128)));

        BigDecimal stddev = ZERO;

        if (newCount.longValue() >= 2) {
            temp = temp.divide(newCount.subtract(new BigDecimal(1)), MathContext.DECIMAL128);
            stddev = MetricFunctionUtils.bigSqrt(temp);
        } else if (newCount.longValue() >= 1) {
            temp = temp.divide(newCount, MathContext.DECIMAL128);
            stddev = MetricFunctionUtils.bigSqrt(temp);
        }

        setCount(context, newCountLong);
        setSum(context, newSum);
        setSumOfSquare(context, newSumSq);

        return mean.add(stddev.multiply(new BigDecimal(2)));
    }


    @Override
    public void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fact, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        Object value = fact.getAttribute(attrName);
        qty = MetricFunctionUtils.objectToBigDecimal(value);
    }
}
