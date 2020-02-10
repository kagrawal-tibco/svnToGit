package com.tibco.rta;


/**
 * 
 * A metric computation is single valued. For example, averages, counts, standard deviation, etc.
 * This represents such a single valued computation. There can be multi-valued computations as well
 * @param <N> The datatype of the metric.
 * @see MultiValueMetric<N>
 * 
 */

public interface SingleValueMetric<N> extends Metric<N> {
	
	/**
	 * The value of the metric
	 * @return the value
	 */
	N getValue();
}