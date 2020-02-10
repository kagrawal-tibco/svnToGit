package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.util.MetricFunctionUtils;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

import java.math.BigDecimal;

public class DerivedNinetyFifthPercentileMetric extends NinetyFifthPercentileMetric {

    @Override
    public BigDecimal compute(MetricNode metricNode,
                              SingleValueMetric<BigDecimal> metric, RtaNodeContext context) {

        SingleValueMetric mean = null;
        SingleValueMetric stdDev = null;
        BigDecimal ninetyFifthPercentileValue;

        for (Measurement dependencyMeasurement : measurement.getDependencies()) {
            if (dependencyMeasurement.getMetricFunctionDescriptor().getName().equals("System.AVG")) {
                mean = (SingleValueMetric) metricNode.getMetric(dependencyMeasurement.getName());
            } else if (dependencyMeasurement.getMetricFunctionDescriptor().getName().equals("System.STDDEV")) {
                stdDev = (SingleValueMetric) metricNode.getMetric(dependencyMeasurement.getName());
            }
        }
        if (mean != null && stdDev != null) {
            BigDecimal stdDevValue = MetricFunctionUtils.objectToBigDecimal(mean.getValue());
            BigDecimal meanValue = MetricFunctionUtils.objectToBigDecimal(stdDev.getValue());
            ninetyFifthPercentileValue = meanValue.add(stdDevValue.multiply(new BigDecimal(2)));
        } else {
            ninetyFifthPercentileValue = super.compute(metricNode, metric, context);
        }
        return ninetyFifthPercentileValue;
    }

}
