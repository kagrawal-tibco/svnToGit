package com.tibco.rta.runtime.metric.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.log.Level;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * 
 * @author bgokhale
 * 
 *         This function is saving sum and sum of sqaures as context,
 * 
 *         these both can be used to calculate standard deviation without
 *         accessing previous values
 *         
 *         using newton raphson method for square rooting a big decimal, 
 *         as converting to double and square rooting is not an option, 
 *         BigDecimal.doubleValue is heavily synchronized API, causing
 *         performance issues.
 * 		   (although this method is also iterative but not creating any
 * 			thread blocks)
 * 	
 */

public class StdDevMetric extends SingleValueMetricFunction<BigDecimal> {

	BigDecimal qty;
	public static final String PARAM1 = "PARAM1";

	String attrName;

	private static final BigDecimal SQRT_DIG = new BigDecimal(10);
	private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());
	private static final BigDecimal ZERO = new BigDecimal(0);
	
	@Override
	public BigDecimal compute(MetricNode metricNode, SingleValueMetric<BigDecimal> metric, RtaNodeContext context) {

		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Computing STDDEV for : %s", metric);
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

		BigDecimal temp = newSumSq.subtract((newSum.pow(2).divide(newCount, MathContext.DECIMAL128)));

		BigDecimal stddev = ZERO;

		if (newCount.longValue() >= 2) {
			temp = temp.divide(newCount.subtract(new BigDecimal(1)), MathContext.DECIMAL128);
			stddev = bigSqrt(temp);
			// stddev = Math.sqrt(temp.doubleValue());
		} else if (newCount.longValue() >= 1) {
			temp = temp.divide(newCount, MathContext.DECIMAL128);
			stddev = bigSqrt(temp);
			// stddev = Math.sqrt(temp.doubleValue());
		}

		setCount(context, newCountLong);
		setSum(context, newSum);
		setSumOfSquare(context, newSumSq);

//		if (Double.isNaN(stddev)) {
//			if (LOGGER.isEnabledFor(Level.WARN)) {
//				LOGGER.log(Level.WARN, "NAN Error while computing STDDEV, returning old value ");
//			}
//
//			if (metric != null && !metric.isMultiValued()) {
//				SingleValueMetric svm = (SingleValueMetric) metric;
//				stddev = (Double) svm.getValue();
//			} else {
//				stddev = 0D;
//			}
//		}

		return stddev;
	}

	private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
		BigDecimal fx = xn.pow(2).add(c.negate());
		BigDecimal fpx = xn.multiply(new BigDecimal(2));
		BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
		xn1 = xn.add(xn1.negate());
		BigDecimal currentSquare = xn1.pow(2);
		BigDecimal currentPrecision = currentSquare.subtract(c);
		currentPrecision = currentPrecision.abs();
		if (currentPrecision.compareTo(precision) <= -1) {
			return xn1;
		}
		return sqrtNewtonRaphson(c, xn1, precision);
	}

	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 * 
	 * 
	 */
	public static BigDecimal bigSqrt(BigDecimal c) {
		return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
	}

	@Override
	public void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fact, measurement, startNode, dh);

		String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
				.getFunctionParams().iterator().next().getName());

		Object value = fact.getAttribute(attrName);
		qty= objectToBigDecimal(value);
	}

	private BigDecimal objectToBigDecimal(Object value) {
		BigDecimal val = null;
		if(value instanceof BigDecimal){
			val = (BigDecimal)value;
		}
		else if (value instanceof Number) {
			Number number = (Number) value;
			if (number instanceof Double) {
				val = new BigDecimal(number.doubleValue());
			} else if (number instanceof Float) {
				val = new BigDecimal(number.floatValue());
			} else if (number instanceof Long) {
				val = new BigDecimal(number.longValue());
			} else if (number instanceof Integer) {
				val = new BigDecimal(number.intValue());
			} else if (number instanceof Short) {
				val = new BigDecimal(number.shortValue());
			} else if (number instanceof Byte) {
				val = new BigDecimal(number.byteValue());
			} else {
				val = new BigDecimal(0D);
			}
		} else {
			val = new BigDecimal(0D);
		}
		return val;
	}

	public BigDecimal getSum(RtaNodeContext context) {
		if (context.getTupleValue("sum") != null) {
			Object value = context.getTupleValue("sum");
			return objectToBigDecimal(value);
		}
		return new BigDecimal(0D);
	}

	public void setSum(RtaNodeContext context, BigDecimal sum) {
		context.setTuple("sum", sum);
	}

	public long getCount(RtaNodeContext context) {
		if (context.getTupleValue("count") != null) {
			Object value = context.getTupleValue("count");
			return (Long)value;
		}
		return 0L;
	}

	public void setCount(RtaNodeContext context, long count) {
		context.setTuple("count", count);
	}

	public BigDecimal getSumOfSquare(RtaNodeContext context) {
		if (context.getTupleValue("sumofsquare") != null) {
			Object value = context.getTupleValue("sumofsquare");
			return objectToBigDecimal(value);
		}
		return new BigDecimal(0D);
	}

	public void setSumOfSquare(RtaNodeContext context, BigDecimal sumOfSquare) {
		context.setTuple("sumofsquare", sumOfSquare);
	}
}
