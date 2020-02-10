package com.tibco.rta.model.serialize.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MetricValueDescriptor;
import com.tibco.rta.RtaCommand;
import com.tibco.rta.impl.BaseMetricImpl;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.query.ResultTuple;
import com.tibco.rta.query.impl.MetricResultTupleImpl;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/3/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelJSONDeserializer extends ModelDeserializerAdapter {

    private final ReentrantLock lock = new ReentrantLock();

    public static final ModelJSONDeserializer INSTANCE = new ModelJSONDeserializer();

    private ObjectMapper objectMapper = new ObjectMapper();

    public RtaCommand deserializeCommand(byte[] serializedCommand) throws Exception {
   		ReentrantLock mainLock = this.lock;
   		mainLock.lock();
   		try {
            return objectMapper.readValue(serializedCommand, RtaCommand.class);
   		} finally {
   			if (mainLock.isHeldByCurrentThread()) {
   				mainLock.unlock();
   			}
   		}
    }

    public ServerConfigurationCollection deserializeServerConfig(byte[] serializedConfig) throws Exception {
   		ReentrantLock mainLock = this.lock;
   		mainLock.lock();
   		try {
            return objectMapper.readValue(serializedConfig, ServerConfigurationCollection.class);
   		} finally {
   			if (mainLock.isHeldByCurrentThread()) {
   				mainLock.unlock();
   			}
   		}
    }


    @SuppressWarnings("unchecked")
    public QueryResultTupleCollection<?> deserializeQueryResults(byte[] serializedTuples) throws Exception {
   		ReentrantLock mainLock = this.lock;
   		mainLock.lock();

   		try {
   			QueryResultTupleCollection<?> queryResultTupleCollection = objectMapper.readValue(serializedTuples, QueryResultTupleCollection.class);

            for (ResultTuple queryResultTuple : queryResultTupleCollection.getQueryResultTuples()) {
                processQueryTuple(queryResultTuple);
            }
            return queryResultTupleCollection;
   		} finally {
   			if (mainLock.isHeldByCurrentThread()) {
   				mainLock.unlock();
   			}
   		}
    }

    /**
     * Massaging done to every query tuple post deserialize.
     */
    private void processQueryTuple(ResultTuple queryResultTuple) {
        if (queryResultTuple instanceof QueryResultTuple) {
            QueryResultTuple metricQueryResultTuple = (QueryResultTuple) queryResultTuple;
            MetricResultTupleImpl metricResultTuple = (MetricResultTupleImpl) metricQueryResultTuple.getMetricResultTuple();
            Map<String, Metric> metrics = metricResultTuple.getMetrics();

            for (Map.Entry<String, Metric> metricEntry : metrics.entrySet()) {
                processMetric(metricEntry.getKey(),
                        metricEntry.getValue(),
                        metricResultTuple.getMetricKey(),
                        metricResultTuple.getMetricValueDescriptor(),
                        metricResultTuple.getCreatedTime(),
                        metricResultTuple.getUpdatedTime());
            }
        }
    }

    /**
     * Massaging done to every metric post deserialize.
     */
    private void processMetric(String metricName,
                               Metric metric,
                               MetricKey metricKey,
                               MetricValueDescriptor commonMetricValueDescriptor,
                               long createdTime,
                               long lastModifiedTime) {
        metric.setKey(metricKey);
        if (metric instanceof BaseMetricImpl) {
            BaseMetricImpl baseMetric = (BaseMetricImpl) metric;
            baseMetric.setCreatedTime(createdTime);
            baseMetric.setLastModifiedTime(lastModifiedTime);
        }
        MetricValueDescriptorImpl metricValueDescriptor = new MetricValueDescriptorImpl();
        metricValueDescriptor.setMeasurementName(metricName);
        metricValueDescriptor.setSchemaName(commonMetricValueDescriptor.getSchemaName());
        metricValueDescriptor.setCubeName(commonMetricValueDescriptor.getCubeName());
        metricValueDescriptor.setDimensionName(commonMetricValueDescriptor.getDimensionName());
        metricValueDescriptor.setDimHierarchyName(commonMetricValueDescriptor.getDimHierarchyName());
        metric.setDescriptor(metricValueDescriptor);
    }
}
