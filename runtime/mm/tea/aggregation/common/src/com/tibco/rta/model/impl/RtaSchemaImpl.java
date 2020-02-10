package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_SYSTEM_SCHEMA;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RETENTION_POLICIES;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RETENTION_POLICY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.tibco.rta.Fact;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits.Unit;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.mutable.MutableRtaSchema;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.SerializationTarget;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.model.serialize.jaxb.adapter.SchemaDimension;

@XmlRootElement(name = ELEM_SCHEMA_NAME)
@XmlType(propOrder={ "retentionPolicies", "attributes", "schemaDimensions", "measurements", "cubes"})
@XmlAccessorType(XmlAccessType.NONE)
public class RtaSchemaImpl extends BaseMetadadataElementImpl implements MutableRtaSchema {

    private static final long serialVersionUID = -2773585779133409370L;

    protected Map<String, Attribute> attributeMap = new LinkedHashMap<String, Attribute>();

    protected Map<String, Dimension> dimensionMap = new LinkedHashMap<String, Dimension>();

    protected Map<String, MutableMeasurement> measurementMap = new LinkedHashMap<String, MutableMeasurement>();

    protected Map<String, MutableCube> cubesMap = new LinkedHashMap<String, MutableCube>();

    protected Map<String, RetentionPolicy> retentionPolicies = new LinkedHashMap<String, RetentionPolicy>();

    protected long version;

    protected String display_name;
    
    @XmlAttribute(name = ATTR_SYSTEM_SCHEMA)
    protected boolean isSystemSchema;
    
    public RtaSchemaImpl() {
        super(null);
    }

    public RtaSchemaImpl(String name) {
        super(name);
    }

    @Override
    public RtaSchema getOwnerSchema() {
        return this;
    }

    
    @XmlElementWrapper(name=ELEM_ATTRIBUTES_NAME)
    @XmlElement(name = ELEM_ATTRIBUTE_NAME, type = AttributeImpl.class)
    @Override
    public List<Attribute> getAttributes() {
        return new ArrayList<Attribute>(attributeMap.values());
    }

    @Override
    public Attribute getAttribute(String attributeName) {
        return attributeMap.get(attributeName);
    }

        
    @Override
    public List<Dimension> getDimensions() {
        return new ArrayList<Dimension>(dimensionMap.values());
    }
    
    @XmlElement(name=ELEM_DIMENSIONS_NAME)
    private SchemaDimension getSchemaDimensions() {
    	return new SchemaDimension(dimensionMap.values());
    }
    
    @Override
    public Dimension getDimension(String name) {
        return dimensionMap.get(name);
    }

    @XmlElementWrapper(name=ELEM_MEASUREMENTS_NAME)
    @XmlElement(name = ELEM_MEASUREMENT_NAME, type=MeasurementImpl.class)
    @Override
    public <T extends Measurement> List<T> getMeasurements() {
        return new ArrayList(measurementMap.values());
    }

    @Override
    public <T extends Measurement> T getMeasurement(String name) {
        return (T) measurementMap.get(name);
    }

    @Override
    public <T extends Cube> T getCube(String name) {
        return (T) cubesMap.get(name);
    }

    @XmlElementWrapper(name=ELEM_CUBES_NAME)
    @XmlElement(name=ELEM_CUBE_NAME, type=CubeImpl.class)
    @Override
    public <T extends Cube> List<T> getCubes() {
        return new ArrayList(cubesMap.values());
    }


    @Override
    public long getVersion() {
        return version;
    }

    /**
     * Create a new attribute on this measurement.
     *
     * @param name
     * @param dataType
     * @return
     * @throws com.tibco.rta.model.DuplicateSchemaElementException
     *
     * @throws UndefinedSchemaElementException
     *
     */
    @Override
    public Attribute newAttribute(String name, DataType dataType) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        // null check
        if (null == name) {
            throw new UndefinedSchemaElementException("Attribute is not specified while defining the dimension");
        }
        // Check if it is already present in the map
        if (null != attributeMap.get(name)) {
            throw new DuplicateSchemaElementException(String.format("Attribute %s is already defined", name));
        }
        Attribute attribute = new AttributeImpl(name, dataType, this);
        attributeMap.put(name, attribute);
        return attribute;
    }
    
    @Override
    public Attribute removeAttribute(String name) {
        //TODO: Check reference count before removing from the schema
        return attributeMap.remove(name);
    }

    @Override
    public Dimension newDimension(String name, Attribute attribute) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        // null check
        if (null == attribute) {
            throw new UndefinedSchemaElementException(String.format("Attribute is not specified while defining the dimension [%s]", name));
        }
        name = (name == null) ? attribute.getName() : name;
        // Check if it is already present in the map
        if (null != dimensionMap.get(name)) {
            throw new DuplicateSchemaElementException(String.format("Dimension [%s] is already defined", name));
        }
        Dimension dimension = new DimensionImpl(name, attribute);
        dimensionMap.put(name, dimension);
        return dimension;
    }
    
    
    @Override
    public Dimension newDimension(Attribute attribute) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        if (null == attribute) {
            throw new UndefinedSchemaElementException("Attribute is not specified while defining the dimension");
        }
        String name = attribute.getName();
        return newDimension(name, attribute);
    }

    @Override
    public TimeDimension newTimeDimension(String name, Attribute attribute, Unit unit, int frequency) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        if ((null == attribute) || (null == unit)) {
            throw new UndefinedSchemaElementException("Attribute is not specified while defining the dimension");
        }
        name = (name == null) ? attribute.getName() : name;
        // Check if it is already present in the map
        if (null != dimensionMap.get(name)) {
            throw new DuplicateSchemaElementException(String.format("Time Dimension %s is already defined", name));
        }
        TimeDimension dimension = new TimeDimensionImpl(name, attribute, unit, frequency);
        dimensionMap.put(name, dimension);
        return dimension;
    }

    @Override
    public Dimension removeDimension(String name) {
        //TODO: Check reference count before removing from the schema
        return dimensionMap.remove(name);
    }

    @Override
    public MutableCube newCube(String name) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        if (null == name) {
            throw new UndefinedSchemaElementException("Cube name is not specified while defining the cube");
        }
        MutableCube cube = cubesMap.get(name);
        // Check if it is already present in the map
        if (null != cube) {
            throw new DuplicateSchemaElementException(String.format("Cube %s is already defined", name));
        }
        cube = new CubeImpl(name, this);
        cubesMap.put(name, cube);
        return cube;
    }

    @Override
    public MutableCube removeCube(String name) {
        //TODO: Check reference count before removing from the schema
        return cubesMap.remove(name);
    }

    @Override
    public MutableMeasurement newMeasurement(String name) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        MutableMeasurement m;
        if (null == name) {
            throw new UndefinedSchemaElementException("Measurement name is not specified while defining new measurement");
        }
        // Check if it is already present in the map
        if (null != (m = measurementMap.get(name))) {
            throw new DuplicateSchemaElementException(String.format("Measurement %s is already added", name));
        }
        m = new MeasurementImpl(name, this);
        measurementMap.put(name, m);
        return m;
    }

    @Override
    public MutableMeasurement removeMeasurement(String name) {
        //TODO: Check reference count before removing from the schema
        return measurementMap.remove(name);

    }

    @Override
    public Fact createFact() {
        return new FactImpl(this);
    }

    @Override
    public Fact createFact(String uid) {
        return new FactImpl(this, uid);
    }

    @Override
    public <T extends SerializationTarget, S extends ModelSerializer<T>> void serialize(S serializer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @XmlElementWrapper(name=ELEM_RETENTION_POLICIES)
    @XmlElement(name = ELEM_RETENTION_POLICY, type=RetentionPolicyImpl.class)    
    @Override
    public Collection<RetentionPolicy> getRetentionPolicies() {
        return new ArrayList<RetentionPolicy>(retentionPolicies.values());
    }
    

	@Override
	public void addRetentionPolicy(String qualifier, Unit timeUnit, int multiplier, String purgeTimeOfDay,
			long purgeFrequencyPeriod) {
		RetentionPolicyImpl rPolicy;
		if ("FACT".equalsIgnoreCase(qualifier)) {
			rPolicy = new RetentionPolicyImpl(Qualifier.FACT, timeUnit, multiplier, purgeTimeOfDay,
					purgeFrequencyPeriod);
			retentionPolicies.put(qualifier, rPolicy);
		} else {
			rPolicy = new RetentionPolicyImpl(Qualifier.HIERARCHY, timeUnit, multiplier, purgeTimeOfDay,
					purgeFrequencyPeriod);
			rPolicy.setHierarchyName(qualifier);
			retentionPolicies.put(qualifier, rPolicy);
		}
	}

    @Override
    public void removeRetentionPolicy(String qualifier) {
        retentionPolicies.remove(qualifier);
    }

	@Override
	public String getDisplayName() {
		return display_name;
	}

	@Override
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

    @Override
    public String toXML() throws Exception {
        return SerializationUtils.serializeSchema(this);
    }
		
	@Override
	public boolean isSystemSchema() {
		return isSystemSchema;
	}
	
		
	public void setSystemSchema(boolean systemSchema) {
		isSystemSchema = systemSchema;		
	}
}
