package com.tibco.rta;

import java.util.List;


/**
 * A metric computation with multiple values. For example, computing the top five car sales by
 * region or certain number of outliers in a statistical computation.
 * 
 * @param <N> The data type of the metric computation.
 * 
 */

public interface MultiValueMetric<N> extends Metric<N> {

	/**
	 * Return a list of values associated with this metric.
	 * @return list
	 */
	List<N> getValues();
}
