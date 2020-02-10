package com.tibco.cep.loadbalancer.impl.message;

import java.io.Serializable;

import com.tibco.cep.loadbalancer.message.DistributionKey;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 3:36:24 PM
*/
public class DefaultDistributionKey<T extends Comparable> implements DistributionKey, Serializable {
    protected T actual;

    public DefaultDistributionKey() {
    }

    public DefaultDistributionKey(T actual) {
        this.actual = actual;
    }

    public T getActual() {
        return actual;
    }

    //---------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultDistributionKey)) {
            return false;
        }

        DefaultDistributionKey that = (DefaultDistributionKey) o;

        if (!actual.equals(that.actual)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return actual.hashCode();
    }

    @Override
    public int compareTo(DistributionKey o) {
        if (o instanceof DefaultDistributionKey) {
            DefaultDistributionKey that = (DefaultDistributionKey) o;

            return actual.compareTo(that.actual);
        }

        throw new IllegalArgumentException(
                "Comparison only works with [" + DefaultDistributionKey.class.getName() + "] type and not for [" +
                        o.getClass().getName() + "]");
    }

    @Override
    public String toString() {
        return actual.toString();
    }
}
