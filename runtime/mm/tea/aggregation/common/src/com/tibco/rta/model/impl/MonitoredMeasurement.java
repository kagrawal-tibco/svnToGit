package com.tibco.rta.model.impl;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.SerializationTarget;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/6/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitoredMeasurement implements MutableMeasurement, MonitoredMeasurementMBean {

    private MutableMeasurement adaptedMeasurement;

    private AtomicLong processedFacts = new AtomicLong(0);

    private AtomicLong rejectedFacts = new AtomicLong(0);

    public MonitoredMeasurement(MutableMeasurement adaptedMeasurement) {
        this.adaptedMeasurement = adaptedMeasurement;
    }

    @Override
    public String getOwnerSchemaName() {
        return adaptedMeasurement.getOwnerSchema().getName();
    }


    public long incrementProcessedFacts() {
        return processedFacts.incrementAndGet();
    }

    public long incrementRejectedFacts() {
        return rejectedFacts.incrementAndGet();
    }

    @Override
    public long getTotalFacts() {
        return getProcessedFacts() + getRejectedFacts();
    }

    @Override
    public long getProcessedFacts() {
        return processedFacts.get();
    }

    @Override
    public long getRejectedFacts() {
        return rejectedFacts.get();
    }

    @Override
    public void setMetricFunctionDescriptor(MetricFunctionDescriptor functionDescriptor) {
        adaptedMeasurement.setMetricFunctionDescriptor(functionDescriptor);
    }

    @Override
    public void addFunctionParamBinding(String paramName, String attributeName) {
        adaptedMeasurement.addFunctionParamBinding(paramName, attributeName);
    }

    @Override
    public <T extends MetricFunctionDescriptor> T getMetricFunctionDescriptor() {
        return adaptedMeasurement.getMetricFunctionDescriptor();
    }

    @Override
    public String getFunctionParamBinding(String paramName) {
        return adaptedMeasurement.getFunctionParamBinding(paramName);
    }

    @Override
    public void setName(String name) {
        adaptedMeasurement.setName(name);
    }

    @Override
    public void setDescription(String description) {
        adaptedMeasurement.setDescription(description);
    }

    @Override
    public void setProperty(String name, String value) {
        adaptedMeasurement.setProperty(name, value);
    }

    @Override
    public RtaSchema getOwnerSchema() {
        return adaptedMeasurement.getOwnerSchema();
    }

    @Override
    public String getName() {
        return adaptedMeasurement.getName();
    }

    @Override
    public String getDescription() {
        return adaptedMeasurement.getDescription();
    }

    @Override
    public <T extends SerializationTarget, S extends ModelSerializer<T>> void serialize(S serializer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getProperty(String name) {
        return adaptedMeasurement.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return adaptedMeasurement.getPropertyNames();
    }

	@Override
    public String getUnitOfMeasurement() {
		return adaptedMeasurement.getUnitOfMeasurement();
	}

	@Override
    public void setUnitOfMeasurement(String unitOfMeasurement) {
		adaptedMeasurement.setUnitOfMeasurement(unitOfMeasurement);	    
    }

	@Override
    public void setDisplayName(String displayName) {
		adaptedMeasurement.setDisplayName(displayName);
    }

	@Override
    public String getDisplayName() {
		return adaptedMeasurement.getDisplayName();	    
    }

    @Override
    public Collection<Measurement> getDependencies() {
        return adaptedMeasurement.getDependencies();
    }

    @Override
    public void addDependency(Measurement dependency) {
        adaptedMeasurement.addDependency(dependency);
    }
   
	@Override
	public DataType getDataType() {		
		return adaptedMeasurement.getDataType();
	}

	@Override
	public void setDataType(DataType dataType) {		
		adaptedMeasurement.setDataType(dataType);
	}

}
