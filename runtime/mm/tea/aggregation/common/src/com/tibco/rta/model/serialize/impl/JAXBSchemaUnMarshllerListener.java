package com.tibco.rta.model.serialize.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.Unmarshaller;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.IllegalSchemaException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits.Unit;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;
import com.tibco.rta.model.impl.DimensionImpl;
import com.tibco.rta.model.impl.MeasurementImpl;
import com.tibco.rta.model.impl.TimeDimensionImpl;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.mutable.MutableMetadataElement;
import com.tibco.rta.model.mutable.MutableRtaSchema;
import com.tibco.rta.model.serialize.jaxb.adapter.HierarchyDimension;
import com.tibco.rta.model.serialize.jaxb.adapter.HierarchyMeasurement;
import com.tibco.rta.model.serialize.jaxb.adapter.MeasurementMetricFunctionData;
import com.tibco.rta.model.serialize.jaxb.adapter.RtaSchemaCollection;
import com.tibco.rta.model.serialize.jaxb.adapter.SchemaDimension;
import com.tibco.rta.util.DiGraph;
import com.tibco.rta.util.TopologicalSort;

public class JAXBSchemaUnMarshllerListener extends Unmarshaller.Listener {

	private Map<Measurement, String[]> tempMeasurements = new HashMap<Measurement, String[]>();	
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());
	
	private MutableRtaSchema currentSchema;
	private boolean resolved;
	
	@Override
	public void beforeUnmarshal(Object target, Object parent) {		
	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		deserialize(target, parent);
	}

	private void deserialize(Object target, Object parent) {
		try {
			deserializeRetentionPolicies(target, parent);
			deserializeAttributes(target, parent);
			deserializeDimensions(target, parent);
			deserializeMeasurements(target, parent);
			deserializeMeasurementMetricFunctionData(target, parent);
			deserializeCubes(target, parent);
			deserializeDimHierarchies(target, parent);
			deserializeHierarchyDim(target, parent);
			deserializeHierarchyMeasurement(target, parent);
			deserializeSchemas(target, parent);
			setRetentionPoliciesTohierarchies();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "", e);
			throw new RuntimeException(e);
		}
	}

	private void deserializeSchemas(Object target, Object parent) {
		if (target instanceof RtaSchema && parent instanceof RtaSchemaCollection) {
			currentSchema = null;
			resolved = false;
			tempMeasurements.clear();
		}		
	}

	private void deserializeHierarchyMeasurement(Object target, Object parent) throws UndefinedSchemaElementException, DuplicateSchemaElementException {
		if (target instanceof HierarchyMeasurement && parent instanceof DimensionHierarchy) {
			HierarchyMeasurement hMeasurement = (HierarchyMeasurement) target;
			MutableDimensionHierarchy dh = (MutableDimensionHierarchy) parent;
			setHierarchyMeasurement(hMeasurement, dh);
		}
	}

	private void setHierarchyMeasurement(HierarchyMeasurement hMeasurement, MutableDimensionHierarchy dh) throws UndefinedSchemaElementException,
			DuplicateSchemaElementException {
		
		Measurement m = currentSchema.getMeasurement(hMeasurement.getMeasurementName());
		if (m == null) {
			LOGGER.log(Level.ERROR, "Invalid Measurement Name [%s] defined in hierarchy [%s]", hMeasurement.getMeasurementName(), dh.getName());
			Exception e =  new UndefinedSchemaElementException("Invalid Measurement Name " + hMeasurement.getMeasurementName() + " defined in hierarchy "+ dh.getName());
			throw new RuntimeException(e);
		}

		dh.addMeasurement((MutableMeasurement) m);
	}

	private void deserializeHierarchyDim(Object target, Object parent) throws IllegalSchemaException {
		if (target instanceof HierarchyDimension && parent instanceof DimensionHierarchy) {
			HierarchyDimension hDim = (HierarchyDimension) target;
			MutableDimensionHierarchy dh = (MutableDimensionHierarchy) parent;
			setOwnerSchema(parent);
			setHierarchyDim(hDim, dh);
		}
	}

	private void setOwnerSchema(Object parent) {
		if (parent instanceof DimensionHierarchyImpl) {
			((DimensionHierarchyImpl) parent).setOwnerSchema(currentSchema);
		}
	}

	private void setHierarchyDim(HierarchyDimension hDim, MutableDimensionHierarchy dh) throws IllegalSchemaException {
		if (currentSchema.getDimension(hDim.getReferenceDimension()) == null) {
			String message = String.format("Invalid Dimension:[%s] for hierarchy:[%s]\nDefine dimension:[%s] for schema:[%s]", hDim.getReferenceDimension(), dh.getName(), hDim.getReferenceDimension(),
					currentSchema.getName());
			LOGGER.log(Level.ERROR, message);
			throw new RuntimeException(message);
		}

		Dimension d = currentSchema.getDimension(hDim.getReferenceDimension());
		 String compute = hDim.getCompute();
		if (compute != null && compute.equals("false")) {
			dh.setComputeForLevel(d, false);
		} else {
			dh.setComputeForLevel(d, true);
		}
		addExcludeMeasurements(d, hDim, dh);
		dh.addDimensionAfter(null, d);
	}

	private void addExcludeMeasurements(Dimension d, HierarchyDimension hDim, MutableDimensionHierarchy dh) {
		String exclude = hDim.getExcluded();
		if (exclude != null && !exclude.equals("")) {
			String[] exMeasurementArry = exclude.split(",");
			for (String measurementName : exMeasurementArry) {
				Measurement measurement = currentSchema.getMeasurement(measurementName.trim());
				if (measurement == null) {
					LOGGER.log(Level.ERROR, "Invalid excluded measurement [%s] for dimension [%s] in hierarchy [%s] of schema [%s]", measurementName, d.getName(), dh.getName(),
							currentSchema.getName());
				}
				dh.addExcludeMeasurement(d, measurement);
			}
		}
	}

	private void deserializeDimHierarchies(Object target, Object parent) throws DuplicateSchemaElementException, IllegalSchemaException, UndefinedSchemaElementException {
		if (target instanceof DimensionHierarchy && parent instanceof Cube) {
			DimensionHierarchy dh = (DimensionHierarchy) target;
			MutableCube cube = (MutableCube) parent;
			MutableDimensionHierarchy newDh = cube.newDimensionHierarchy(dh.getName());
			setOwnerSchema(newDh);
			addDependingMeasurementesToHierarchy(newDh, dh);
			deserializeProperties(newDh, dh);
			deserializeHierarchyAttributes(newDh, dh);
			deserializeHierarchyDimensions(newDh, dh);
		}
	}

	private void addDependingMeasurementesToHierarchy(MutableDimensionHierarchy newDh, DimensionHierarchy dh) throws UndefinedSchemaElementException,
			DuplicateSchemaElementException {
		DiGraph<String> mGraph = new DiGraph<String>();

		for (Measurement m : dh.getMeasurements()) {
			mGraph.addNode(m.getName());
			addDependencies((MutableMeasurement) m, mGraph);
		}

		List<String> sort = TopologicalSort.sort(mGraph);
		for (String measurement : sort) {
			MutableMeasurement dependant = currentSchema.getMeasurement(measurement);
			if (dependant != null) {
				newDh.addMeasurement(dependant);
			}
		}
	}

	private void deserializeCubes(Object target, Object parent) throws DuplicateSchemaElementException, UndefinedSchemaElementException, IllegalSchemaException {
		if (target instanceof Cube && parent instanceof RtaSchema) {
			Cube cube = (Cube) target;
			MutableCube newCube = currentSchema.newCube(cube.getName());			
			resolveDependingMeasurements();
			mapCubes(newCube, cube);
		}
	}

	private void resolveDependingMeasurements() {
		if (!resolved) {
			for (Measurement m : tempMeasurements.keySet()) {
				String[] depends = tempMeasurements.get(m);
				for (String measurementName : depends) {
					Measurement depedingMeasurement = currentSchema.getMeasurement(measurementName.trim());
					if (depedingMeasurement == null) {
						throw new IllegalArgumentException("No Depending Measurement [" + measurementName + "] present in schema [" + currentSchema.getName() + "]");
					}
					((MutableMeasurement) m).addDependency(depedingMeasurement);
				}
			}
			resolved = true;
		}
	}

	private void mapDimHierarchies(MutableDimensionHierarchy newDh, DimensionHierarchy dh) throws IllegalSchemaException, UndefinedSchemaElementException,
			DuplicateSchemaElementException {
		deserializeProperties(newDh, dh);
		deserializeHierarchyAttributes(newDh, dh);
		deserializeHierarchyDimensions(newDh, dh);
		deserializwHierarchyMeasurement(newDh, dh);
	}

	private void deserializwHierarchyMeasurement(MutableDimensionHierarchy newDh, DimensionHierarchy dh) throws UndefinedSchemaElementException,
			DuplicateSchemaElementException {

		for (Measurement m : dh.getMeasurements()) {
			newDh.addMeasurement((MutableMeasurement) m);
		}
	}
	
	private void addDependencies(MutableMeasurement measurement, DiGraph<String> mGraph) {
		for (Measurement dependencyMeasurement : measurement.getDependencies()) {
			MutableMeasurement mutableMeasurement = currentSchema.getMeasurement(dependencyMeasurement.getName());
			boolean isNewNode = mGraph.addNode(dependencyMeasurement.getName());
			mGraph.addEdge(dependencyMeasurement.getName(), measurement.getName());
			if (isNewNode) {
				addDependencies(mutableMeasurement, mGraph);
			}
		}
	}


	private void deserializeHierarchyDimensions(MutableDimensionHierarchy newDh, DimensionHierarchy dh) throws IllegalSchemaException {
		Dimension prev = null;
		for (Dimension d : dh.getDimensions()) {

			newDh.addDimensionAfter(prev, d);
			for (Measurement m : dh.getExcludedMeasurements(d)) {
				newDh.addExcludeMeasurement(d, m);
			}
			newDh.setComputeForLevel(d, dh.getComputeForLevel(dh.getLevel(d.getName())));
			prev = d;
		}
	}

	private void mapCubes(MutableCube newCube, Cube cube) throws DuplicateSchemaElementException, IllegalSchemaException, UndefinedSchemaElementException {
		for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
			MutableDimensionHierarchy newDh = newCube.newDimensionHierarchy(dh.getName());
			mapDimHierarchies(newDh, dh);
		}
		deserializeProperties(newCube, cube);
	}

	private void deserializeHierarchyAttributes(MutableDimensionHierarchy newDh, DimensionHierarchy dh) {
//		newDh.setAssetValidationEnabled(dh.assetValidationEnabled());
		newDh.setEnabled(dh.isEnabled());
	}
	
	private void setRetentionPoliciesTohierarchies() {
		if (currentSchema != null) {
			for (Cube cube : currentSchema.getCubes()) {
				for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {

					RetentionPolicy rPolicy = getPolicy(dh);
					if (rPolicy != null) {
						((MutableDimensionHierarchy) dh).setRetentionPolicy(rPolicy);
					}
				}
			}
		}
	}
	
	private RetentionPolicy getPolicy(DimensionHierarchy dh) {
		String cubeName = dh.getOwnerCube().getName();
		String type = cubeName + "/" + dh.getName();

		for (RetentionPolicy rp : currentSchema.getRetentionPolicies()) {
			if (rp.getQualifier().equals(Qualifier.HIERARCHY) && rp.getHierarchyName().equalsIgnoreCase(type)) {
				return rp;
			}
		}
		return null;
	}



	private void deserializeMeasurementMetricFunctionData(Object target, Object parent) {
		if (target instanceof MeasurementMetricFunctionData && parent instanceof Measurement) {
			MeasurementMetricFunctionData mf = (MeasurementMetricFunctionData) target;
			MutableMeasurement measurement = (MutableMeasurement) parent;
			String funRef = mf.getMetricFunctionName();
			MetricFunctionDescriptor md = MetricFunctionsRepository.INSTANCE.getFunctionDescriptor(funRef);
			measurement.setMetricFunctionDescriptor(md);
			addFnBindings(measurement, mf);
		}

	}

	private void addFnBindings(MutableMeasurement measurement, MeasurementMetricFunctionData mf) {
		for (Entry<String, String> entry : mf.getFnParams().entrySet()) {
			if (currentSchema.getAttribute(entry.getValue()) == null) {
				String err = String.format("No Such attribute [%s] in schema [%s] for measurement [%s]", entry.getValue(), currentSchema.getName(), measurement.getName());
				throw new RuntimeException(err);
			}
			measurement.addFunctionParamBinding(entry.getKey(), entry.getValue());
		}
	}

	private void deserializeMeasurements(Object target, Object parent) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
		if (target instanceof Measurement && parent instanceof RtaSchema) {
			Measurement m = (Measurement) target;

			MutableMeasurement newM = currentSchema.newMeasurement(m.getName());
			newM.setMetricFunctionDescriptor(m.getMetricFunctionDescriptor());
			newM.setDataType(m.getDataType());
			addFnBindings(newM, m, m.getMetricFunctionDescriptor());
			newM.setUnitOfMeasurement(m.getUnitOfMeasurement());
			deserializeDepdendingMeasurements(newM, m);

			deserializeProperties(newM, m);
		}
	}

	private void deserializeProperties(MutableMetadataElement newM, MetadataElement m) {
		for (String propName : m.getPropertyNames()) {
			newM.setProperty(propName, m.getProperty(propName));
		}

	}

	private void addFnBindings(MutableMeasurement newM, Measurement m, MetricFunctionDescriptor md) {
		for (FunctionParam fp : md.getFunctionParams()) {
			String refAttribute = m.getFunctionParamBinding(fp.getName());
			if (currentSchema.getAttribute(refAttribute) == null) {
				LOGGER.log(Level.ERROR, "Invalid attribute [%s] for measurement [%s] , No such attribute present in schema [%s]", refAttribute, m.getName(), currentSchema.getName());
			}
			newM.addFunctionParamBinding(fp.getName(), refAttribute);
		}
	}

	private void deserializeDepdendingMeasurements(Measurement newM, Measurement measurement) {
		if (measurement instanceof MeasurementImpl) {
			String depends = ((MeasurementImpl) measurement).getDependingMeasurements();
			if (depends != null && !depends.equals("")) {
				String[] exMeasurementArry = depends.split(",");
				tempMeasurements.put(newM, exMeasurementArry);
			}
		}
	}

	private void deserializeRetentionPolicies(Object target, Object parent) {
		if (target instanceof RetentionPolicy && parent instanceof RtaSchema) {
			RetentionPolicy rt = (RetentionPolicy) target;
			currentSchema = ((MutableRtaSchema) parent);
			if (rt.getQualifier().equals(Qualifier.FACT)) {
				currentSchema.addRetentionPolicy("" + Qualifier.FACT, rt.getRetentionUnit(), (int) rt.getRetentionUnitCount(), rt.getPurgeTimeOfDay(),
						rt.getPurgeFrequencyPeriod());
			} else {
				currentSchema.addRetentionPolicy(rt.getHierarchyName(), rt.getRetentionUnit(), (int) rt.getRetentionUnitCount(), rt.getPurgeTimeOfDay(),
						rt.getPurgeFrequencyPeriod());
			}
		}
	}

	private void deserializeAttributes(Object target, Object parent) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
		if (target instanceof Attribute && parent instanceof RtaSchema) {
			Attribute attr = (Attribute) target;
			setSchemaIfNull((RtaSchema) parent);
			if (attr.getName() == null) {
				String err = String.format("Attribute name can not be null for schema [%s]", currentSchema.getName());
				throw new RuntimeException(err);
			}
			Attribute newAttr = currentSchema.newAttribute(attr.getName(), attr.getDataType());
			deserializeProperties((MutableMetadataElement) newAttr, attr);
		}
	}

	private void setSchemaIfNull(RtaSchema schema) {
		if (currentSchema == null) {
			currentSchema = (MutableRtaSchema) schema;
		}
	}

	private void deserializeDimensions(Object target, Object parent) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
		if (target instanceof TimeDimension && parent instanceof SchemaDimension) {
			TimeDimensionImpl d = (TimeDimensionImpl) target;
			String attrRefName = d.getAssociatedAttributeName();
			if (currentSchema.getAttribute(attrRefName) == null) {
				LOGGER.log(Level.ERROR, "Invalid attribute:%s  for dimension:%s", attrRefName, d.getName());
			}
			TimeDimension timeDim = currentSchema.newTimeDimension(d.getName(), currentSchema.getAttribute(attrRefName),
					Unit.valueOf(d.getUnitName()), d.getFrequency());
			deserializeProperties((MutableMetadataElement) timeDim, d);

		} else if (target instanceof DimensionImpl && parent instanceof SchemaDimension) {
			DimensionImpl d = (DimensionImpl) target;
			String attrRefName = d.getAssociatedAttributeName();
			if (currentSchema.getAttribute(attrRefName) == null) {
				LOGGER.log(Level.ERROR, "Invalid attribute:%s  for dimension:%s", attrRefName, d.getName());
			}
			Dimension newDim = currentSchema.newDimension(d.getName(), currentSchema.getAttribute(attrRefName));
			deserializeProperties((MutableMetadataElement) newDim, d);
		}
	}


}
