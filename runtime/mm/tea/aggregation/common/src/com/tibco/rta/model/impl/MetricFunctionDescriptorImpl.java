package com.tibco.rta.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.mutable.MutableMetricFunctionDescriptor;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

@XmlAccessorType(XmlAccessType.NONE)
public class MetricFunctionDescriptorImpl extends
		FunctionDescriptorImpl implements MutableMetricFunctionDescriptor {

	private static final long serialVersionUID = -6859575486096978052L;

	protected boolean multiValued;
	
	protected DataType dataType;

	public MetricFunctionDescriptorImpl(String name, String category, boolean multiValued, 
			String implClassName, DataType dataType, String desc) {
		super(name, implClassName, category);
		this.description = desc;
		this.dataType = dataType;
		this.multiValued = multiValued;
	}
	
	public MetricFunctionDescriptorImpl() {
		
	}
	
	@XmlAttribute(name=IS_MULTI_VALUED)
	@Override
	public boolean isMultiValued() {
		return multiValued;
	}

	@XmlAttribute(name=ATTR_DATATYPE_NAME)
	@Override
	public DataType getMetricDataType() {
		return dataType;
	}

	@Override
	public void setMultiValued(boolean multiValued) {
		this.multiValued = multiValued;
	}
	
	@Override
	public void setMetricDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
