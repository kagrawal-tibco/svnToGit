package com.tibco.cep.designtime.model;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 6:34:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Interval {


    /**
     * @param interval an Interval not null
     * @return true iif interval is empty or contained in this Interval.
     */
    boolean contains(Interval interval);


    /**
     * @param value a double
     * @return true iif value is contained in this interval.
     */
    boolean contains(double value);


    /**
     * @param interval an Interval
     * @return true iif interval is not null, and either interval and this are both empty,
     *         or interval and this share the same bounds and bounds exclusion.
     */
    boolean equals(Interval interval);


    double getLowerBound();


    boolean getLowerBoundExcluded();


    double getUpperBound();


    boolean getUpperBoundExcluded();


    /**
     * @param interval an Interval not null
     * @return if interval intersects with this Interval, the intersection, else an empty interval.
     */
    Interval getIntersection(Interval interval);


    /**
     * @param interval an Interval not null
     * @return true iif the intersection of interval with this Interval is not empty.
     */
    boolean intersects(Interval interval);


    /**
     * @return true iif the lower bound and the lower bound are equal and one of them is excluded.
     */
    boolean isEmpty();
}
