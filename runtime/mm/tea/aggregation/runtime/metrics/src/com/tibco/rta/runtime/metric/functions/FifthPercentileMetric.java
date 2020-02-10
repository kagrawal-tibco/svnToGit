package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.log.Level;
import com.tibco.rta.runtime.metric.util.MetricFunctionUtils;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 
 * 
 * -2-sigma from mean assuming normal distribution.
 * 
 * fifth percentile goes -ve for small and random (not normal) distribution of
 * numbers, so making it zero when -ve, considering spm data to be +ve always
 * 
 */

public class FifthPercentileMetric extends NthPercentileMetric {


	@Override
	public BigDecimal compute(MetricNode metricNode, SingleValueMetric<BigDecimal> metric, RtaNodeContext context) {

		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Computing TP5 for : %s", metric);
		}

		BigDecimal newSum = getSum(context);
		newSum = newSum.add(qty);
		
		long newCountLong = getCount(context);
		BigDecimal newCount = new BigDecimal(newCountLong);

		// for calulation purposes using BigDecimal and for storing count using long
		newCount = newCount.add(new BigDecimal(1));				
		newCountLong = newCountLong+1;
		
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

		BigDecimal fifthPercentileValue = mean.subtract(stddev.multiply(new BigDecimal(2)));

		if (fifthPercentileValue.compareTo(ZERO) == -1) {
			fifthPercentileValue = ZERO;
		}
		return fifthPercentileValue;
	}
}