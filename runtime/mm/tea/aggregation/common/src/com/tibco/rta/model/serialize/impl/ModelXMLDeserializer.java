package com.tibco.rta.model.serialize.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.model.TimeUnits.Unit;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.DimensionImpl;
import com.tibco.rta.model.impl.RtaSchemaImpl;
import com.tibco.rta.model.impl.TimeDimensionImpl;
import com.tibco.rta.model.impl.TimeUnitsImpl;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.mutable.MutableMetadataElement;
import com.tibco.rta.model.mutable.MutableRtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.util.DiGraph;
import com.tibco.rta.util.TopologicalSort;

public class ModelXMLDeserializer implements ModelDeserializer {

	MutableRtaSchema schema;

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

//	private QueryValidator validator = null;

	private DocumentBuilder documentBuilder;

	private Map<Measurement, String[]> tempMeasurements = new HashMap<Measurement, String[]>();

	private ModelResolver resolver;

	protected ModelXMLDeserializer() {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public RtaSchema deserialize(File file) throws Exception {
		RtaSchema schema;
		InputSource in = new InputSource(new FileInputStream(file));
		schema = deserializeSchema(in);
		return schema;
	}

	@Override
	public RtaSchema deserializeSchema(InputSource in) throws Exception {
		Document rootDocument = documentBuilder.parse(in);
		Element schemaElem = rootDocument.getDocumentElement();
		schema = new RtaSchemaImpl();
		if (ELEM_SCHEMA_NAME.equals(schemaElem.getNodeName())) {
			deserializeSchemaAttributes(schema, schemaElem);
			deserializeMData(schemaElem, schema);

			NamedNodeMap np = schemaElem.getAttributes();
			deserializeProperty(schema, np);
			deserializePropertyFromTag(schema, schemaElem);
			
			deserializeAttributes(schemaElem);			
			deserializeMeasurements(schemaElem);
			deserializeDimensions(schemaElem);
			deserializeCubes(schemaElem);
			deserializeRetentionPolicies(schemaElem);
			setRetentionPoliciesTohierarchies();
		}
		return schema;
	}

	private void deserializeSchemaAttributes(MutableRtaSchema schema2, Element schemaElem) {
		schema.setName(schemaElem.getAttribute(ATTR_NAME_NAME));
		String isSystemSchema = schemaElem.getAttribute(ATTR_SYSTEM_SCHEMA); 
		if (isSystemSchema != null) {
			((RtaSchemaImpl)schema).setSystemSchema(Boolean.parseBoolean(isSystemSchema));
		}		
	}

	private void setRetentionPoliciesTohierarchies() {
		for (Cube cube : schema.getCubes()) {
			for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {

				RetentionPolicy rPolicy = getPolicy(dh);
				if (rPolicy != null) {
					((MutableDimensionHierarchy) dh).setRetentionPolicy(rPolicy);
				}
			}
		}
	}

	private void deserializeRetentionPolicies(Element schemaElem) throws Exception {
		NodeList policiesNList = schemaElem.getElementsByTagName(ELEM_RETENTION_POLICIES);
		if (policiesNList.getLength() > 0) {
			NodeList policyNList = ((Element) policiesNList.item(0)).getElementsByTagName(ELEM_RETENTION_POLICY);
			for (int j = 0; j < policyNList.getLength(); j++) {
				String type = ((Element) policyNList.item(j)).getAttribute(ATTR_TYPE_NAME);
				String period = ((Element) policyNList.item(j)).getAttribute(ATTR_PERIOD_NAME);
				String unit = ((Element) policyNList.item(j)).getAttribute(ATTR_UNIT_NAME);
				String purgeTime = ((Element) policyNList.item(j)).getAttribute(ATTR_PURGE_TIME_DAY);
				String purgeFrequencyPeriod = ((Element) policyNList.item(j)).getAttribute(ATTR_PURGE_FREQUENCY_PERIOD);
				if (period != null && !period.equals("") && purgeFrequencyPeriod != null && !purgeFrequencyPeriod.equals("")) {
					if (type.equalsIgnoreCase(Qualifier.FACT.name())) {
						schema.addRetentionPolicy("" + Qualifier.FACT, Unit.valueOf(unit), Integer.parseInt(period), purgeTime, Long.parseLong(purgeFrequencyPeriod));
					} else {
						if (validateRetentionPolicyType(type)) {
							schema.addRetentionPolicy(type, Unit.valueOf(unit), Integer.parseInt(period), purgeTime, Long.parseLong(purgeFrequencyPeriod));
						}
					}
				}
			}
		}
	}

	private Boolean validateRetentionPolicyType(String type) throws Exception {
		String[] val = type.split("/");
		if (val.length >= 2) {
			Cube cube = schema.getCube(val[0]);
			if (cube == null) {
				LOGGER.log(Level.ERROR, "Invalid cube:%s in RetentionPolicy", val[0]);
				throw new Exception("Invalid cube:" + val[0] + " in RetentionPolicy of schema: " + schema.getName());
			}

			DimensionHierarchy dh = cube.getDimensionHierarchy(val[1]);
			if (dh == null) {
				LOGGER.log(Level.ERROR, "Invalid hierarchy:%s in RetentionPolicy", val[1]);
				throw new Exception("Invalid hierarchy:" + val[1] + " in RetentionPolicy of schema: " + schema.getName());
			}
			return true;
		} else {
			LOGGER.log(Level.ERROR, "Invalid type:%s for RetentionPolicy\tIt should be <cube-name>/<hierarchy-name>", type);
			throw new Exception("Invalid type:" + type + " for RetentionPolicy\tIt should be <cube-name>/<hierarchy-name>");
		}
	}

	private void deserializeAttributes(Element schemaElem) throws Exception {
		NodeList attributesNodeList = schemaElem.getElementsByTagName(ELEM_ATTRIBUTES_NAME);

		NodeList attributeNodeList = ((Element) attributesNodeList.item(0)).getElementsByTagName(ELEM_ATTRIBUTE_NAME);
		for (int j = 0; j < attributeNodeList.getLength(); j++) {
			Element attrbuteElem = ((Element) attributeNodeList.item(j));
			String attributeName = attrbuteElem.getAttribute(ATTR_NAME_NAME);
			String dataTypeValue = attrbuteElem.getAttribute(ATTR_DATATYPE_NAME);
			if (attributeName == null) {
				LOGGER.log(Level.ERROR, "Attribute Name can not be null");
				// throw new Exception("Attribute Name can not be null");
			}
			Attribute attr = schema.newAttribute(attributeName.trim(), DataType.valueOf(dataTypeValue));
			NamedNodeMap np = attrbuteElem.getAttributes();

			deserializeProperty((MutableMetadataElement) attr, np);
			deserializePropertyFromTag((MutableMetadataElement) attr, attrbuteElem);
		}
	}

	private Attribute validateSchemaAttribute(String attrName) {
		if (schema != null) {
			for (Attribute attr : schema.getAttributes()) {
				if (attr.getName().equals(attrName)) {
					return attr;
				}
			}
		}
		return null;
	}

	private void deserializeDimensions(Element schemaElem) throws Exception {
		NodeList dimensionsElem = schemaElem.getElementsByTagName(ELEM_DIMENSIONS_NAME);
		NodeList dimensionElem = ((Element) dimensionsElem.item(0)).getElementsByTagName(ELEM_DIMENSION_NAME);
		NodeList timeDimensionElem = ((Element) dimensionsElem.item(0)).getElementsByTagName(ELEM_TIME_DIMENSION_NAME);
		for (int j = 0; j < dimensionElem.getLength(); j++) {
			String dimensionName = ((Element) dimensionElem.item(j)).getAttribute(ATTR_NAME_NAME);
			String attributeRefName = ((Element) dimensionElem.item(j)).getAttribute(ATTR_ATTR_REF_NAME);
			String assetRef = ((Element) dimensionElem.item(j)).getAttribute(ATTR_ASSET_REF);

			Attribute attr = null;

			if ((attr = validateSchemaAttribute(attributeRefName)) == null) {
				LOGGER.log(Level.ERROR, "Invalid attribute:%s  for dimension:%s", attributeRefName, dimensionName);
				// throw new Exception("Invalid attribute:" + attributeRefName +
				// " for dimension:" + dimensionName);
			}

			String dimType = ((Element) dimensionElem.item(j)).getAttribute(ATTR_DIMENSION_TYPE);
			if (dimType != null && !dimType.equals("")) {
				if (dimType.equals("time")) {
					String timeUnit = ((Element) dimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_UNIT);
					String freq = ((Element) dimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_FREQUENCY);
					String qtroffsetStr = ((Element) dimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_QTR_OFFSET);
					TimeDimensionImpl tdI = (TimeDimensionImpl) schema.newTimeDimension(dimensionName, schema.getAttribute(attributeRefName),
							TimeUnits.Unit.valueOf(timeUnit), Integer.parseInt(freq));
					TimeUnitsImpl tUI = (TimeUnitsImpl) tdI.getTimeUnit();
					if (timeUnit.equalsIgnoreCase(TimeUnits.Unit.QUARTER.name())) {
						tUI.setStartMonth(Integer.parseInt(qtroffsetStr));
					} else {
						tUI.setStartMonth(1);
					}
				} else {
					LOGGER.log(Level.ERROR, "DimensionType not recognized %s", dimType);
					// throw new
					// Exception(String.format("DimensionType not recognized %s",
					// dimType));
				}
			} else {
				DimensionImpl d = (DimensionImpl) schema.newDimension(dimensionName, schema.getAttribute(attributeRefName));
				if (assetRef != null && !assetRef.equals("")) {
//					if (validateAssetRef(assetRef) == null) {
//						LOGGER.log(Level.ERROR, "Invalid asset reference:%s  for dimension:%s", assetRef, dimensionName);
//					}
					d.setAssociatedAssetName(assetRef);
				}
			}
		}
		
		for (int j = 0; j < timeDimensionElem.getLength(); j++) {
			String dimensionName = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_NAME_NAME);
			String attributeRefName = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_ATTR_REF_NAME);
			String assetRef = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_ASSET_REF);

			Attribute attr = null;

			if ((attr = validateSchemaAttribute(attributeRefName)) == null) {
				LOGGER.log(Level.ERROR, "Invalid attribute:%s  for dimension:%s", attributeRefName, dimensionName);
				// throw new Exception("Invalid attribute:" + attributeRefName +
				// " for dimension:" + dimensionName);
			}
			
			String timeUnit = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_UNIT);
			String freq = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_FREQUENCY);
			String qtroffsetStr = ((Element) timeDimensionElem.item(j)).getAttribute(ATTR_TIME_DIMENSION_QTR_OFFSET);
			TimeDimensionImpl tdI = (TimeDimensionImpl) schema.newTimeDimension(dimensionName, schema.getAttribute(attributeRefName),
					TimeUnits.Unit.valueOf(timeUnit), Integer.parseInt(freq));
			TimeUnitsImpl tUI = (TimeUnitsImpl) tdI.getTimeUnit();
			if (timeUnit.equalsIgnoreCase(TimeUnits.Unit.QUARTER.name())) {
				tUI.setStartMonth(Integer.parseInt(qtroffsetStr));
			} else {
				tUI.setStartMonth(1);
			}

		}	
	}

	private void deserializeMeasurements(Element schemaElem) throws Exception {
		NodeList measurementsElem = schemaElem.getElementsByTagName(ELEM_MEASUREMENTS_NAME);
		NodeList measurementElemL = ((Element) measurementsElem.item(0)).getElementsByTagName(ELEM_MEASUREMENT_NAME);
		for (int i = 0; i < measurementElemL.getLength(); i++) {
			Element measurementElem = (Element) measurementElemL.item(i);
			String measurementName = measurementElem.getAttribute(ATTR_NAME_NAME);
			String unitName = measurementElem.getAttribute(ATTR_UNIT_NAME);
			String dataTypeName = measurementElem.getAttribute(ATTR_DATATYPE_NAME);
			MutableMeasurement measurement = schema.newMeasurement(measurementName);

			if (unitName != null) {
				measurement.setUnitOfMeasurement(unitName);
			}

			deserializeDepends(measurementElem, measurement);
			deserializeMData(measurementElem, measurement);
			NamedNodeMap np = measurementElem.getAttributes();
			deserializeProperty(measurement, np);
			deserializePropertyFromTag(measurement, measurementElem);
			
			Element metricFunctionElem = (Element) measurementElem.getElementsByTagName(ELEM_METRIC_FN_NAME).item(0);
			String fnName = metricFunctionElem.getAttribute(ATTR_REF_NAME);
			MetricFunctionDescriptor mfd;
//			if (validator != null) {
				mfd = MetricFunctionsRepository.INSTANCE.getFunctionDescriptor(fnName);

				if (mfd == null) {
					String errorMessage = String.format("Metric Function:%s not found in schema:%s", fnName, schema.getName());
					LOGGER.log(Level.ERROR, errorMessage);
					throw new Exception(errorMessage);
				}
//			} else {
//				MetricFunctionsRepository.addMeasurementFunctionBinding(measurementName, fnName);
//				mfd = MetricFunctionsRepository.getClientFunctionDescriptorForMeasurement(measurementName);
//			}
			measurement.setMetricFunctionDescriptor(mfd);

			NodeList fnParamL = metricFunctionElem.getElementsByTagName(ELEM_FUNCTION_PARAM);
			for (int j = 0; j < fnParamL.getLength(); j++) {
				Element fnParamElem = (Element) fnParamL.item(j);
				String paramName = fnParamElem.getAttribute(ATTR_NAME_NAME);
				String attrName = fnParamElem.getAttribute(ATTR_ATTR_REF_NAME);
				measurement.addFunctionParamBinding(paramName, attrName);
			}
		}

		resolveDependencies();
	}

	private void resolveDependencies() {
		for (Entry<Measurement, String[]> entry : tempMeasurements.entrySet()) {
			MutableMeasurement measurement = (MutableMeasurement) entry.getKey();
			String[] depends = entry.getValue();

			for (String measurementName : depends) {
				Measurement depedingMeasurement = schema.getMeasurement(measurementName.trim());
				if (depedingMeasurement == null) {
					throw new IllegalArgumentException("No Depending Measurement [" + measurementName + "] present in schema [" + schema.getName() + "]");
				}
				measurement.addDependency(depedingMeasurement);
			}
		}
		tempMeasurements.clear();
	}

	private void deserializeDepends(Element measurementElem, MutableMeasurement measurement) throws UndefinedSchemaElementException {
		String depends = measurementElem.getAttribute(ATTR_DEPENDS_NAME);
		if (!depends.equals("")) {
			String[] exMeasurementArry = depends.split(",");
			tempMeasurements.put(measurement, exMeasurementArry);
		}
	}

	private void deserializeCubes(Element schemaElem) throws Exception {
		NodeList cubesElem = schemaElem.getElementsByTagName(ELEM_CUBES_NAME);
		NodeList cubeElems = ((Element) cubesElem.item(0)).getElementsByTagName(ELEM_CUBE_NAME);
		for (int j = 0; j < cubeElems.getLength(); j++) {
			Element cubeElem = (Element) cubeElems.item(j);
			String cubeName = cubeElem.getAttribute(ATTR_NAME_NAME);
			if (schema.getCube(cubeName) != null) {
				LOGGER.log(Level.ERROR, "Duplicate cube:%s for schema:%s", cubeName, schema.getName());
				throw new Exception("Duplicate cube:" + cubeName + " for schema:" + schema.getName());
			}
			MutableCube cube = schema.newCube(cubeName);
			deserializeMData(cubeElem, cube);
			NamedNodeMap np = cubeElem.getAttributes();
			deserializeProperty(cube, np);
			deserializePropertyFromTag(cube, cubeElem);
			
			deserializeHierarchies(cube, cubeElem);
		}
	}

	private void deserializeMData(Element elem, MutableMetadataElement mData) {
		String displayName = elem.getAttribute(ATTR_DISPLAY_NAME);
		String desc = elem.getAttribute(ATTR_DESCRIPTION);
		if (displayName != null && !displayName.equals("")) {
			mData.setDisplayName(displayName);
		}

		if (desc != null && !desc.equals("")) {
			mData.setDescription(desc);
		}
	}

	private void deserializePropertyFromTag(MutableMetadataElement mData, Element mElement) {
		NodeList propertiesElemnt = mElement.getElementsByTagName(ELEM_PROPERTIES_NAME);
		if (propertiesElemnt.getLength() > 0) {
			NodeList propertyElem = ((Element)propertiesElemnt.item(0)).getElementsByTagName(ELEM_PROPERTY_NAME);
			for (int i = 0; i < propertyElem.getLength(); i++) {
				Element property = (Element) propertyElem.item(i);
				mData.setProperty(property.getAttribute(ATTR_NAME_NAME), property.getAttribute(ATTR_VALUE_NAME));
			}
		}
	}
	
	
	private void deserializeProperty(MutableMetadataElement mData, NamedNodeMap np) {
		for (int i = 0; i < np.getLength(); i++) {
			Node property = np.item(i);
			if (!property.getNodeName().equalsIgnoreCase("name") && !property.getNodeName().equalsIgnoreCase("description")
					&& !property.getNodeName().equalsIgnoreCase("display-name") && !property.getNodeName().equals("")) {
				mData.setProperty(property.getNodeName(), property.getNodeValue());
			}
		}
	}

	private void deserializeHierarchies(MutableCube cube, Element cubeElem) throws Exception {
		NodeList hierarchiesElem = cubeElem.getElementsByTagName(ELEM_HIERARCHYS_NAME);
		NodeList hierarchyElems = ((Element) hierarchiesElem.item(0)).getElementsByTagName(ELEM_HIERARCHY_NAME);

		for (int j = 0; j < hierarchyElems.getLength(); j++) {
			Element hierarchyElem = (Element) hierarchyElems.item(j);
			String hierarchyName = hierarchyElem.getAttribute(ATTR_NAME_NAME);
			MutableDimensionHierarchy dh = cube.newDimensionHierarchy(hierarchyName);
			String validateAsset = hierarchyElem.getAttribute(ATTR_VALIDATE_ASSET);
			String isEnabled = hierarchyElem.getAttribute(ATTR_HIERARCHY_ENABLED);

//			if (validateAsset != null && !validateAsset.equals("")) {
//				Boolean validate = Boolean.valueOf(validateAsset);
//				dh.setAssetValidationEnabled(validate);
//			} else {
//				dh.setAssetValidationEnabled(true);
//			}

			if (isEnabled != null && !isEnabled.equals("")) {
				Boolean enable = Boolean.parseBoolean(isEnabled);
				dh.setEnabled(enable);
			} else {
				dh.setEnabled(true);
			}

			deserializeMData(hierarchyElem, dh);
			NamedNodeMap np = hierarchyElem.getAttributes();
			deserializeProperty(dh, np);
			deserializePropertyFromTag(dh, hierarchyElem);
			
			DiGraph<MutableMeasurement> mGraph = deserializeHierarchyMeasurementRefs(dh, hierarchyElem);
			List<MutableMeasurement> sort = TopologicalSort.sort(mGraph);
			for (MutableMeasurement measurement : sort) {
				dh.addMeasurement(measurement);
			}
			deserializeHierarchyDimensionRefs(dh, hierarchyElem);
		}
	}

	private RetentionPolicy getPolicy(DimensionHierarchy dh) {
		String cubeName = dh.getOwnerCube().getName();
		String type = cubeName + "/" + dh.getName();

		for (RetentionPolicy rp : schema.getRetentionPolicies()) {
			if (rp.getQualifier().equals(Qualifier.HIERARCHY) && rp.getHierarchyName().equalsIgnoreCase(type)) {
				return rp;
			}
		}
		return null;
	}

	private MutableMeasurement validateMeasurement(String measurementName) throws UndefinedSchemaElementException {
		MutableMeasurement measurement = schema.getMeasurement(measurementName);
		if (measurement == null) {
			LOGGER.log(Level.ERROR, "Invalid Measurement Name %s defined in hierarchy", measurementName);
			throw new UndefinedSchemaElementException("Invalid Measurement Name " + measurementName + " defined in hierarchy ");
		}
		return measurement;
	}

	private DiGraph<MutableMeasurement> deserializeHierarchyMeasurementRefs(MutableDimensionHierarchy dh, Element hierarchyElem) throws UndefinedSchemaElementException,
			DuplicateSchemaElementException {
		DiGraph<MutableMeasurement> mGraph = new DiGraph<MutableMeasurement>();
		Element measurementRefsElem = (Element) hierarchyElem.getElementsByTagName(ELEM_MEASUREMENT_REFS_NAME).item(0);
		NodeList measurementRefL = measurementRefsElem.getElementsByTagName(ELEM_MEASUREMENT_NAME);
		for (int i = 0; i < measurementRefL.getLength(); i++) {
			Element measurementRefElem = (Element) measurementRefL.item(i);
			String measurementRefName = measurementRefElem.getAttribute(ATTR_REF_NAME);
			MutableMeasurement measurement = validateMeasurement(measurementRefName);
			mGraph.addNode(measurement);
			addDependencies(measurement, mGraph);
			// dh.addMeasurement(measurement);
			// mGraph.addNode(measurement);
		}
		return mGraph;
	}

	private void addDependencies(MutableMeasurement measurement, DiGraph<MutableMeasurement> mGraph) {
		for (Measurement dependencyMeasurement : measurement.getDependencies()) {
			MutableMeasurement mutableMeasurement = schema.getMeasurement(dependencyMeasurement.getName());
			boolean isNewNode = mGraph.addNode(mutableMeasurement);
			mGraph.addEdge(mutableMeasurement, measurement);
			if (isNewNode) {
				addDependencies(mutableMeasurement, mGraph);
			}
		}
	}

	private void deserializeHierarchyDimensionRefs(MutableDimensionHierarchy dh, Element hierarchyElem) throws Exception {
		NodeList dimensionsElem = hierarchyElem.getElementsByTagName(ELEM_DIMENSIONS_NAME);
		NodeList dimensionElems = ((Element) dimensionsElem.item(0)).getElementsByTagName(ELEM_DIMENSION_NAME);
		Dimension prev = null;
		for (int j = 0; j < dimensionElems.getLength(); j++) {
			Element dimensionElem = (Element) dimensionElems.item(j);
			String dimensionName = dimensionElem.getAttribute(ATTR_REF_NAME);
			String exMeasurements = dimensionElem.getAttribute(ATTR_EXCLUDE_NAME);
			if (schema.getDimension(dimensionName) == null) {
				String message = String.format("Invalid Dimension:%s for hierarchy:%s\nDefine dimension:%s for schema:%s", dimensionName, dh.getName(), dimensionName,
						schema.getName());
				LOGGER.log(Level.ERROR, message);
				throw new Exception(message);
			}

			Dimension d = schema.getDimension(dimensionName);
			if (!exMeasurements.equals("")) {
				String[] exMeasurementArry = exMeasurements.split(",");
				for (String measurementName : exMeasurementArry) {
					Measurement measurement = dh.getMeasurement(measurementName.trim());
					dh.addExcludeMeasurement(d, measurement);
				}
			}
			String compute = dimensionElem.getAttribute(ATTR_COMPUTE_NAME);
			if (compute != null && compute.equals("false")) {
				dh.setComputeForLevel(d, false);
			} else {
				dh.setComputeForLevel(d, true);
			}
			dh.addDimensionAfter(prev, d);
			prev = d;
		}
	}

	@Override
	public List<Fact> deserializeFacts(InputSource inputStream) throws Exception {
		List<Fact> facts = new ArrayList<Fact>();
		Document rootDocument = documentBuilder.parse(inputStream);
		Element rootElement = rootDocument.getDocumentElement();

		if (ELEM_FACTS_NAME.equals(rootElement.getNodeName())) {

			NodeList factNodeList = rootElement.getElementsByTagName(ELEM_FACT_NAME);
			Fact fact;

			for (int i = 0; i < factNodeList.getLength(); i++) {
				if (factNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					fact = deserializeFact((Element) factNodeList.item(i));
					if (fact != null) {
						facts.add(fact);
					}
				}
			}
		}
		return facts;
	}

	private Fact deserializeFact(Element parentElement) throws UndefinedSchemaElementException, DataTypeMismatchException {
		Fact fact = null;
		if (ELEM_FACT_NAME.equals(parentElement.getNodeName())) {
			String schemaName = parentElement.getAttribute(ATTR_SCHEMA_NAME);
			RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
			fact = new FactImpl(schema);
			NodeList attributesNodeList = parentElement.getElementsByTagName(ELEM_ATTRIBUTES_NAME);
			for (int i = 0; i < attributesNodeList.getLength(); i++) {
				NodeList attributeNodeList = ((Element) attributesNodeList.item(i)).getElementsByTagName(ELEM_ATTRIBUTE_REF_NAME);
				for (int j = 0; j < attributeNodeList.getLength(); j++) {
					fact.setAttribute(((Element) attributeNodeList.item(j)).getAttribute(ATTR_REF_NAME), ((Element) attributeNodeList.item(j)).getAttribute(ELEM_VALUE_NAME));
				}
			}
		}
		return fact;

	}

	@Override
	public Fact deserializeFact(InputSource in) throws Exception {
		Fact fact;
		Document rootDocument = documentBuilder.parse(in);
		Element rootElement = rootDocument.getDocumentElement();

		fact = deserializeFact(rootElement);
		return fact;
	}

	@Override
	public QueryDef deserializeQuery(InputSource in) throws Exception {
		return QueryDeserializer.INSTANCE.deserializeQuery(in, null, documentBuilder);
	}

	@Override
	public RuleDef deserializeRule(InputSource in) throws Exception {
		return QueryDeserializer.INSTANCE.deserializeRule(in, documentBuilder, null);
	}

	@Override
	public List<RuleDef> deserializeRules(InputSource inputStream) throws Exception {
		List<RuleDef> ruleDefs = new ArrayList<RuleDef>();
		Document rootDocument = documentBuilder.parse(inputStream);
		Element rootElement = rootDocument.getDocumentElement();

		if (ELEM_RULES_NAME.equals(rootElement.getNodeName())) {
			NodeList ruleNodeList = rootElement.getElementsByTagName(ELEM_RULE_NAME);
			RuleDef ruleDef;

			for (int i = 0; i < ruleNodeList.getLength(); i++) {
				if (ruleNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					ruleDef = QueryDeserializer.INSTANCE.deserializeRule(inputStream, documentBuilder, (Element) ruleNodeList.item(i));
					if (ruleDef != null) {
						ruleDefs.add(ruleDef);
					}
				}
			}
		}
		return ruleDefs;
	}

}
