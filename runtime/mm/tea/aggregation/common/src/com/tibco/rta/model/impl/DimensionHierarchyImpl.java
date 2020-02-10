package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_HIERARCHY_ENABLED;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALIDATE_ASSET;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_REFS_NAME;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.IllegalSchemaException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.serialize.jaxb.adapter.HierarchyDimension;
import com.tibco.rta.model.serialize.jaxb.adapter.HierarchyMeasurement;

/**
 * Default implementation for the {@link DimensionHierarchy}
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DimensionHierarchyImpl extends MetadataElementImpl implements MutableDimensionHierarchy {

    private static final long serialVersionUID = -8397283973000737108L;

    /**
     * We will not cache dimensions here since they will come from measurement.
     */
    protected List<Dimension> dimensions = new ArrayList<Dimension>();

    protected Map<String, MutableMeasurement> measurementMap = new LinkedHashMap<String, MutableMeasurement>();

    protected Map<String, List<Measurement>> excludeList = new LinkedHashMap<String, List<Measurement>>();
    
    protected Map<String, Set<String>> excludeMeasurements = new HashMap<String, Set<String>>(); 

    protected Map<Integer, List<Measurement>> measurementsForLevel = new LinkedHashMap<Integer, List<Measurement>>();

    protected Map<Integer, Boolean> computeForLevel = new LinkedHashMap<Integer, Boolean>();

    protected RetentionPolicy retentionPolicy;

    protected Cube ownerCube;

    //retain the insert order
    protected Map<String, MetricFunctionDescriptor> metricDescriptors = new LinkedHashMap<String, MetricFunctionDescriptor>();
    
    @XmlAttribute(name=ATTR_VALIDATE_ASSET)
    protected boolean assetValidationEnabled = true;

    @XmlAttribute(name=ATTR_HIERARCHY_ENABLED)
    protected boolean isEnabled = true;
    
    protected List<String> dimensionAttribsList = new ArrayList<String>();

    protected DimensionHierarchyImpl() {
    }

    public DimensionHierarchyImpl(String name) {
        super(name);
    }

    public DimensionHierarchyImpl(String name, RtaSchema ownerSchema) {
        super(name, ownerSchema);
    }

    public DimensionHierarchyImpl(String name, Cube cube) {
        super(name, cube.getOwnerSchema());
        this.ownerCube = cube;
    }

    @Override
    public <T extends Cube> T getOwnerCube() {
        return (T) ownerCube;
    }

    @Override
    public Dimension getDimension(int level) {
        return dimensions.get(level);
    }

    public void setOwnerSchema(RtaSchema schema) {
    	ownerSchema = schema;
    }
    
    @XmlElementWrapper(name=ELEM_DIMENSIONS_NAME)
    @XmlElement(name=ELEM_DIMENSION_NAME)   
    private Collection<HierarchyDimension> getHierarchyDimensions() {
    	List<HierarchyDimension> dims = new ArrayList<HierarchyDimension>();
    	for (Dimension d : dimensions) {
    		dims.add(new HierarchyDimension(d.getName(), getComputeForLevel(getLevel(d.getName())), getExcludedMeasurementsString(d)));
    	}    	
    	return dims;
    }
    
	private String getExcludedMeasurementsString(Dimension d) {
		StringBuffer str = new StringBuffer();
		int count = 0;
		List<Measurement> excluded = excludeList.get(d.getName());
		if (excluded != null && excluded.size() != 0) {

			for (Measurement measurement : excluded) {
				str.append(measurement.getName());
				count++;
				if (count != excluded.size()) {
					str.append(",");
				}
			}
		}
		if (str.length() == 0) {
			return null;
		}
		String exMeasurements = new String(str);
		return exMeasurements;
	}
    
    @Override
    public <T extends Dimension> List<T> getDimensions() {
        return (List<T>) dimensions;
    }

    @Override
    public int getLevel(String dimensionNm) {

        if (dimensionNm == null || (dimensionNm != null && dimensionNm.equals("root"))) {
            return -1;
        }
        int i = 0;
        for (i = 0; i < dimensions.size(); i++) {
            if (dimensionNm.equals(dimensions.get(i).getName())) {
                break;
            }
        }
        return i;
    }

    /**
     * Get the entire length of hierarchy
     *
     * @return
     */
    @Override
    public int getDepth() {
        return dimensions.size();
    }

    @Override
    public <T extends Measurement> T getMeasurement(String name) {
        return (T) measurementMap.get(name);
    }

    @XmlElementWrapper(name=ELEM_MEASUREMENT_REFS_NAME)
    @XmlElement(name=ELEM_MEASUREMENT_NAME)    
    private Collection<HierarchyMeasurement> getMeasurementRefs() {
    	Collection<HierarchyMeasurement> hm = new ArrayList<HierarchyMeasurement>();
    	for (String measurement : measurementMap.keySet()) {
    		hm.add(new HierarchyMeasurement(measurement));
    	}
    	return hm;
    }
    
    @Override
    public <T extends Measurement> List<T> getMeasurements() {
        return new ArrayList(measurementMap.values());
    }


    /**
     * Inserts new dimension after the reference dimension.
     *
     * @param newDimension the new dimension to insert.
     * @param refDimension the dimension to insert new dimension after.
     *                     It can take a null value.
     *                     If null and no dimensions exist, it is same as {@link #setRootDimension(Dimension)}
     *                     else append it to the hierarchy
     * @throws IllegalSchemaException   If the owner schema of this hierarchy and the child dimension are different
     * @throws IllegalArgumentException If the new child and the reference dimension are equal.
     */
    @Override
    public void addDimensionAfter(Dimension refDimension, Dimension newDimension) throws IllegalSchemaException {
        if (ownerSchema != newDimension.getOwnerSchema()) {
            throw new IllegalSchemaException("Schemas of this hierarchy and dimension are different");
        }

        if (refDimension == null) {
            if (dimensions.isEmpty()) {
                //Set root
                setRootDimension(newDimension);
            } else {
                appendChild(newDimension);
            }
        } else {
            if (newDimension.getName().equals(refDimension.getName())) {
                throw new IllegalArgumentException(String.format("Dimension with name '%s' already exists in hierarchy '%s'", newDimension.getName(), name));
            }
            int reqdIndex = dimensions.indexOf(refDimension);
            reqdIndex = (reqdIndex == -1) ? 0 : reqdIndex;
            if (reqdIndex == dimensions.size()) {
                appendChild(newDimension);
            } else {
                appendChild(reqdIndex + 1, newDimension);
                // The following direct addition is commented since duplicate validation check in appendChild
                // was not getting triggered.
                // dimensions.add(reqdIndex + 1, newDimension);
            }
        }
    }

    @Override
    public Dimension removeDimension(String name) throws IllegalSchemaException {
        for (int i = 0; i < dimensions.size(); i++) {
            Dimension d = dimensions.get(i);
            if (d.getName().equals(name)) {
                //TODO: Check reference count before removing from the hierarchy
                dimensions.remove(i);
                return d;
            }
        }
        return null;
    }

    /**
     * Set root dimension for the hierarchy.
     * If a root exists, it will be overwritten with the new one.
     *
     * @param dimension the root dimension.
     */
    @Override
    public void setRootDimension(Dimension dimension) {
        dimensions.add(0, dimension);
    }

    private void appendChild(Dimension child) throws IllegalSchemaException {
        int reqdIndex = dimensions.size();
        reqdIndex = (reqdIndex == -1) ? 0 : reqdIndex;
        appendChild((reqdIndex), child);
    }

    private void appendChild(int index, Dimension child) throws IllegalSchemaException {
        if (ownerSchema != child.getOwnerSchema()) {
            throw new IllegalSchemaException("Schemas of this hierarchy and dimension are different");
        }

        //Check if it is already present in the hierarchy
        if (!dimensions.contains(child)) {
            dimensions.add(index, child);
        } else {
            throw new IllegalArgumentException(String.format("Dimension with name '%s' already exists in hierarchy '%s'", child.getName(), name));
        }
    }

//	@Override
//	public boolean equals(Object obj) {
//		if (super.equals(obj)) {
//			//TODO: Add additional comparisons of member elements, if required
//			DimensionHierarchyImpl cObj = (DimensionHierarchyImpl) obj;
//			return cObj.ownerCube.equals(this.ownerCube);
//		}
//		return false;
//	}

    /**
     * Define the measurement for this cube. Every cube in the schema
     * will have a single measurement. e.g : Sales of car.
     *
     * @param measurement
     * @throws UndefinedSchemaElementException
     *
     * @throws DuplicateSchemaElementException
     *
     */
    @Override
    synchronized public void addMeasurement(MutableMeasurement measurement) throws UndefinedSchemaElementException, DuplicateSchemaElementException {
        // Check if it is already present in the map
        if (!measurementMap.containsKey(measurement.getName())) {
            MonitoredMeasurement monitoredMeasurement = new MonitoredMeasurement(measurement);
            measurementMap.put(measurement.getName(), monitoredMeasurement);
            try {
                registerHierarchyMBean();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            throw new DuplicateSchemaElementException(String.format("Measurement with name '%s' already exists in cube '%s'", measurement.getName(), name));
        }
    }

    @Override
    public void removeMeasurement(Measurement measurement) {
        //TODO: Check reference count before removing from the hierarchy
        measurementMap.remove(measurement.getName());
    }

    @Override
    public <T extends Measurement> Collection<T> getExcludedMeasurements(int level) {
        Dimension d = getDimension(level);
        return getExcludedMeasurements(d);
    }
    
    @Override
    public boolean isExcluded(int level, String measurmentName) {
    	Dimension d = getDimension(level);
    	HashSet<String> excluded = (HashSet<String>) excludeMeasurements.get(d.getName());
    	if (excluded != null) {
    		return excluded.contains(measurmentName);
    	}
    	return false;
    }

    @Override
    public <T extends Measurement> Collection<T> getExcludedMeasurements(
            Dimension dimension) {
        List<T> m = null;
        if (dimension != null) {
            m = (List<T>) excludeList.get(dimension.getName());
        }
        if (m == null) {
            m = new ArrayList<T>();
        }
        return m;
    }

    @Override
    public void addExcludeMeasurement(Dimension dimension,
                                      Measurement measurement) {
        List<Measurement> m = excludeList.get(dimension.getName());
        Set<String> nameList = excludeMeasurements.get(dimension.getName());
        
        if (m == null) {
            m = new ArrayList<Measurement>();            
            excludeList.put(dimension.getName(), m);
            if (nameList == null) {
            	nameList = new HashSet<String>();
            	excludeMeasurements.put(dimension.getName(), nameList);
            }
        }
        m.add(measurement);
        nameList.add(measurement.getName());
    }

    @Override
    public void removeExcludeMeasurement(Dimension dimension,
                                         Measurement measurement) {
        List<Measurement> mList = excludeList.get(dimension.getName());
        if (mList != null) {
            Iterator<Measurement> i = mList.iterator();

            while (i.hasNext()) {
                Measurement m = i.next();
                if (m.getName().equals(measurement.getName())) {
                    i.remove();
                    return;
                }
            }
        }
        Set<String> mStringList = excludeMeasurements.get(dimension.getName());
        if (mStringList != null) {
        	mStringList.remove(measurement.getName());
        }
    }


    @Override
    public Dimension getDimension(String dimName) {
        for (Dimension d : dimensions) {
            if (d.getName().equals(dimName)) {
                return d;
            }
        }
        return null;
    }


    @Override
    public boolean computeRoot() {
        // TODO Auto-generated method stub
        return false;
    }

    
    public static boolean isAssetHierarchy(DimensionHierarchy dh) {
        String assetHr = dh.getProperty("asset-hierarchy");
        return (assetHr != null && assetHr.equals("true"));
    }
    
    public static String getAssetName(DimensionHierarchy dh) {
        return dh.getProperty("asset-name");
    }
        
    
    @Override
    public Collection<Measurement> getMeasurementsForLevel(int level) {
        List<Measurement> measurements = measurementsForLevel.get(level);
        if (measurements == null) {
            measurements = new LinkedList<Measurement>();
            Dimension d = getDimension(level);
            List<Measurement> excludedAtLevel = excludeList.get(d.getName());
            if (excludedAtLevel != null) {
                outer:
                for (Measurement m : measurementMap.values()) {
                    for (Measurement excluded : excludedAtLevel) {
                        if (m.getName().equals(excluded.getName())) {
                            continue outer;
                        }
                    }
                    measurements.add(m);
                }
            } else {
                measurements.addAll(measurementMap.values());
            }
            measurementsForLevel.put(level, measurements);
        }
        return measurements;
    }

    @Override
    public boolean getComputeForLevel(int level) {
        Boolean toCompute = computeForLevel.get(level);
        if (toCompute == null) {
            return true;
        }
        return toCompute;
    }

    public void setComputeForLevel(Dimension dimension, boolean toCompute) {
        int level = getLevel(dimension.getName());
        computeForLevel.put(level, toCompute);
    }

    @Override
    public void setRetentionPolicy(RetentionPolicy retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    @Override
    public RetentionPolicy getRetentionPolicy() {
        return retentionPolicy;
    }

    private void registerHierarchyMBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(BEMMServiceProviderManager.getInstance().getConfiguration());

        for (Measurement mutableMeasurement : getMeasurements()) {
            ObjectName hierarchyMBeanObjectName = ObjectName.getInstance(mbeanPrefix + ".dimension:type=DimensionHierarchy-" + getName() + ", measurement=" + mutableMeasurement.getName());

            if (!mBeanServer.isRegistered(hierarchyMBeanObjectName)) {
                mBeanServer.registerMBean(mutableMeasurement, hierarchyMBeanObjectName);
            }
        }
    }


//	@XmlAttribute(name=ATTR_HIERARCHY_ENABLED)
	@Override
	public boolean isEnabled() {		
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		
	}

	public static boolean checkForAssets(DimensionHierarchy hierarchy) {
		String checkForAssets = hierarchy.getProperty("check-for-assets");
		return checkForAssets != null && checkForAssets.equalsIgnoreCase("true");
	}

//	@Override
//	public void setRetentionCount(int retentionCount) {
//		this.retentionCount = retentionCount;
//	}
//
//	@Override
//	public void setDailyPurgeTime(String dailyPurgeTime) throws Exception {
//		ModelValidations.validateDailyPurgeTime(dailyPurgeTime);
//		this.dailyPurgeTime = dailyPurgeTime;
//	}
	
	@Override
	public Collection<String> getDimensionAttribNames() {
		if (dimensionAttribsList.size() == 0) {
			for (Dimension dim : getDimensions()) {
				String attrName = dim.getAssociatedAttribute().getName();
				dimensionAttribsList.add(attrName);
			}
		}
		return dimensionAttribsList;
	}
}