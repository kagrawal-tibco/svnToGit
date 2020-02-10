package com.tibco.rta.query.filter.eval;

import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.filter.RelationalFilter;
import com.tibco.rta.runtime.model.MetricNode;

abstract public class RelationalEval extends BaseRelationalEval implements FilterEvaluator {
	
	protected RelationalFilter filter;
	
	public RelationalEval(RelationalFilter filter) {
		this.filter = filter;
	}

	protected boolean evaluate(MetricNode node, RelationalOperator operator,
			FilterKeyQualifier keyQualifier, String key, Object filterValue)
			throws Exception {
		MetricKey metricKey = (MetricKey) node.getKey();

		if (keyQualifier != null) {
			switch (keyQualifier) {

			case MEASUREMENT_NAME:

				Metric metric = node.getMetric(key);
				if (metric instanceof SingleValueMetric) {
					SingleValueMetric svm = (SingleValueMetric) metric;
					Object value = svm.getValue();
					return evalComparables(operator, value, filterValue);
				}
				return false;
			case DIMENSION_NAME:
				Object value = metricKey.getDimensionValue(key);
				return evalComparables(operator, value, filterValue);
			default:
				return false;
			}
		}

		return false;

	}

}
