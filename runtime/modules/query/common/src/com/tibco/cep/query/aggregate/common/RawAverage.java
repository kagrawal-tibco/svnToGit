package com.tibco.cep.query.aggregate.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/30/12
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class RawAverage<T> implements Serializable {
    T sum;
    double count;

    public RawAverage(T sum, double count) {
        this.sum = sum;
        this.count = count;
    }

    public T getSum() {
        return sum;
    }

    public double getCount() {
        return count;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getAvg() {
        Class classType = sum.getClass();
        if (classType == Integer.class) {
            return ((Integer) sum / count);
        } else if (classType == Float.class) {
            return ((Float) sum / count);
        } else if (classType == Long.class) {
            return ((Long) sum / count);
        } else {
            return ((Double) sum / count);
        }
    }
}
