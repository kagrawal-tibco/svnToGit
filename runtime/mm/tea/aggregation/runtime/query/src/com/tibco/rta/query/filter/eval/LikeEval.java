package com.tibco.rta.query.filter.eval;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.LikeFilter;
import com.tibco.rta.runtime.model.MetricNode;

public class LikeEval extends RelationalEval {

	public LikeEval(LikeFilter filter) {
		super(filter);
	}

	@Override
	public boolean eval(MetricNode node) throws Exception {
		String regEx = (String) filter.getValue();
		FilterKeyQualifier keyQualifier = filter.getKeyQualifier();
		MetricQualifier metricQualifier = filter.getMetricQualifier();
		MetricKey metricKey = (MetricKey) node.getKey();

		if (metricQualifier != null) {
			switch (metricQualifier) {
			case DIMENSION_LEVEL:
				return isPatternMatch(regEx, metricKey.getDimensionLevelName());
			default:
				return false;
			}
		} else if (keyQualifier != null) {
			switch (keyQualifier) {

			case MEASUREMENT_NAME:

				Metric metric = node.getMetric(filter.getKey());
				if (metric instanceof SingleValueMetric) {
					SingleValueMetric svm = (SingleValueMetric) metric;
					Object value = svm.getValue();
					return isPatternMatch(regEx, String.valueOf(value));
				}
				break;
			case DIMENSION_NAME:
				Object value = metricKey.getDimensionValue(filter.getKey());
				return isPatternMatch(regEx, String.valueOf(value));
			default:
				return false;
			}
		}
		return false;
	}
	
}
