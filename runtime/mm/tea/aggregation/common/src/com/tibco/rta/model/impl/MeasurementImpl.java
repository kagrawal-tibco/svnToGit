package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.serialize.jaxb.adapter.MeasurementMetricFunctionData;

@XmlAccessorType(XmlAccessType.NONE)
public class MeasurementImpl extends MetadataElementImpl implements MutableMeasurement {

	private static final long serialVersionUID = 8519044449381051320L;
	
	protected MetricFunctionDescriptor mfd;
	
	protected String unitofMeasurement;
	
	protected DataType dataType;
	
	Map<String, String> fnBinding = new LinkedHashMap<String, String>();

    protected Collection<Measurement> dependencies = new ArrayList<Measurement>(0);
	
    private MeasurementMetricFunctionData mData;
    
    private String depMeasurementString;
	
	public MeasurementImpl(String name) {
		super(name);		
	}

	public MeasurementImpl (String name, RtaSchema ownerSchema) {
		super(name, ownerSchema);		
	}
	
	public MeasurementImpl() {
		
	}

	@Override	
    @SuppressWarnings("unchecked")
	public <T extends MetricFunctionDescriptor> T getMetricFunctionDescriptor() {
		return (T) mfd;
	}

	@XmlElement(name=ELEM_METRIC_FN_NAME)
	private MeasurementMetricFunctionData getMetricFunctionData() {		
		return mData;
	}
	
	
	@Override
	public String getFunctionParamBinding(String paramName) {
		return fnBinding.get(paramName);
	}

	@Override
	public void addFunctionParamBinding(String paramName, String attributeName) {
		//TODO: Validate both param name and attrib name.
		fnBinding.put(paramName, attributeName);
		mData.addFnBinding(paramName, attributeName);
		
	}

	@Override
	public void setMetricFunctionDescriptor(MetricFunctionDescriptor mfd) {
		this.mfd = mfd;
		mData = new MeasurementMetricFunctionData(mfd.getName());
	}

	@XmlAttribute(name=ATTR_UNIT_NAME)
	@Override
    public String getUnitOfMeasurement() {
	    return unitofMeasurement;
    }

	@Override
    public void setUnitOfMeasurement(String unitofMeasurement) {
	    this.unitofMeasurement = unitofMeasurement;	    
    }

    public void addDependency(Measurement dependency) {                        
        dependencies.add(dependency);
    }
    
	@XmlAttribute(name = ATTR_DEPENDS_NAME)
	public String getDependingMeasurements() {

		StringBuffer str = new StringBuffer();
		int count = 0;
		if (dependencies.size() != 0) {
			for (Measurement measurement : dependencies) {
				str.append(measurement.getName());
				count++;
				if (count != dependencies.size()) {
					str.append(",");
				}
			}

			if (str.length() == 0) {
				return null;
			}
			depMeasurementString = new String(str);
		}

		return depMeasurementString;
	}
	
    private void setDependingMeasurements(String value) {
    	depMeasurementString = value;
    }
    
    
	@Override
    public Collection<Measurement> getDependencies() {
        return Collections.unmodifiableCollection(dependencies);
    }

	@XmlAttribute(name=ATTR_DATATYPE_NAME)
	@Override
	public DataType getDataType() {
		return dataType;
	}

	@Override
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
		if (dataType == null) {
			this.dataType = mfd.getMetricDataType();
		}
	}

}
