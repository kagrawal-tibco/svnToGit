package com.tibco.rta.runtime.metric.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/1/14
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricFunctionUtils {

    public static final BigDecimal SQRT_DIG = new BigDecimal(10);

    public static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    public static BigDecimal objectToBigDecimal(Object value) {
        BigDecimal val;
        if (value instanceof BigDecimal) {
            val = (BigDecimal) value;
        } else if (value instanceof Number) {
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

    protected static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
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
     */
    public static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }
}
