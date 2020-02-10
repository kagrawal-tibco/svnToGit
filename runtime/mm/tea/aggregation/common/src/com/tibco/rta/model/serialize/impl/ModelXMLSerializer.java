package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.FunctionDescriptor.FunctionParamValue;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.query.QueryDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collection;
import java.util.List;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ASSET_REF;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_CATEGORY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_COMPUTE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DEFAULT_VALUE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DEPENDS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DESCRIPTION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DIMENSION_TYPE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DISPLAY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_EXCLUDE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_HIERARCHY_ENABLED;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ID_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_IMPL_CLASS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ORDINAL;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PERIOD_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PURGE_FREQUENCY_PERIOD;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PURGE_TIME_DAY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_SYSTEM_SCHEMA;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_FREQUENCY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_QTR_OFFSET;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_UNIT;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_UNIT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALIDATE_ASSET;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_DESCRIPTOR;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_DESCRIPTORS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_PARAM;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_PARAMS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTE_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FACTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FACT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_CONTEXT;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_DESCRIPTOR;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_DESCRIPTORS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAM;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAMS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHYS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_REFS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRIC_FN_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RETENTION_POLICIES;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RETENTION_POLICY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.IS_MULTI_VALUED;

public class ModelXMLSerializer implements ModelSerializer<Document> {

	private Document rootDocument;

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

	private RtaSchema schema;

	protected ModelXMLSerializer() {
		try {
			rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "", e);
		}
	}

	@Override
	public void serialize(RtaSchema schema) throws Exception {
		if (schema != null) {
			this.schema = schema;
			Element schemaElement = rootDocument.createElement(ELEM_SCHEMA_NAME);

			serializeMetaDataElement(schemaElement, schema);
			serializeSchemaAttributes(schemaElement, schema);
			serializeProperty(schemaElement, schema);
			rootDocument.appendChild(schemaElement);

			serializeRetentionPolicies(schemaElement);
			serializeAttributes(schemaElement);
			serializeAssets(schemaElement);
			serializeDimensions(schemaElement);
			serializeMeasurements(schemaElement);
			serializeCubes(schemaElement);
		} else {
			LOGGER.log(Level.ERROR, "Schema is null");
		}
	}

	private void serializeSchemaAttributes(Element schemaElement, RtaSchema schema2) {
		if (schema2.isSystemSchema()) {
			schemaElement.setAttribute(ATTR_SYSTEM_SCHEMA, "" + schema2.isSystemSchema());
		}
	}

	private void serializeAssets(Element schemaElement) {
//		Collection<Asset> assets = schema.getAssets();
//		if (!assets.isEmpty()) {
//			Element assetsElem = rootDocument.createElement(ELEM_ASSETS_NAME);
//
//			for (Asset asset : assets) {
//				Element assetElem = rootDocument.createElement(ELEM_ASSET_NAME);
//				assetElem.setAttribute(ATTR_NAME_NAME, asset.getName());
//				serializeProperty(assetElem, asset);
//				Collection<AssetAttribute> attrs = asset.getAttributes();
//				if (!attrs.isEmpty()) {
//					for (AssetAttribute attr : attrs) {
//						Element propertyElem = rootDocument.createElement(ELEM_PROPERTY_NAME);
//						propertyElem.setAttribute(ATTR_NAME_NAME, attr.getName());
//						propertyElem.setAttribute(ATTR_TYPE_NAME, attr.getDataType() + "");
//						propertyElem.setAttribute(ATTR_IS_MANDATORY, attr.isMandatory() + "");
//						assetElem.appendChild(propertyElem);
//					}
//				}
//				assetsElem.appendChild(assetElem);
//			}
//			schemaElement.appendChild(assetsElem);
//		}
//
	}

	private void serializeRetentionPolicies(Element schemaElement) {
		Element policiesElem = rootDocument.createElement(ELEM_RETENTION_POLICIES);
		Collection<RetentionPolicy> policies = schema.getRetentionPolicies();
		for (RetentionPolicy policy : policies) {
			Element policyElem = rootDocument.createElement(ELEM_RETENTION_POLICY);
			if (policy.getQualifier().equals(Qualifier.FACT)) {
				policyElem.setAttribute(ATTR_TYPE_NAME, policy.getQualifier().name());
			} else {
				policyElem.setAttribute(ATTR_TYPE_NAME, policy.getHierarchyName());
			}

			policyElem.setAttribute(ATTR_PERIOD_NAME, "" + policy.getRetentionUnitCount());
			policyElem.setAttribute(ATTR_UNIT_NAME, "" + policy.getRetentionUnit());
			policyElem.setAttribute(ATTR_PURGE_TIME_DAY, "" + policy.getPurgeTimeOfDay());
			policyElem.setAttribute(ATTR_PURGE_FREQUENCY_PERIOD, "" + policy.getPurgeFrequencyPeriod());
			policiesElem.appendChild(policyElem);
		}
		schemaElement.appendChild(policiesElem);
	}

	private void serializeProperty(Element elem, MetadataElement mData) {
		for (String property : mData.getPropertyNames()) {
			if (property != null && !property.equals("")) {
				String value = mData.getProperty(property);
				elem.setAttribute(property, value);
			}
		}
	}

	private void serializeAttributes(Element schemaElement) {
		Element attributesElem = rootDocument.createElement(ELEM_ATTRIBUTES_NAME);
		schemaElement.appendChild(attributesElem);

		for (Attribute attrib : schema.getAttributes()) {
			Element attributeElem = rootDocument.createElement(ELEM_ATTRIBUTE_NAME);
			serializeMetaDataElement(attributeElem, attrib);
			
			attributeElem.setAttribute(ATTR_DATATYPE_NAME, attrib.getDataType().toString());
			// TODO: Validate if the attribute is already present in the list
			serializeProperty(attributeElem, attrib);
			attributesElem.appendChild(attributeElem);
		}
	}

	private void serializeDimensions(Element schemaElement) {
		Element dimensionsElem = rootDocument.createElement(ELEM_DIMENSIONS_NAME);
		schemaElement.appendChild(dimensionsElem);

		for (Dimension dimension : schema.getDimensions()) {
			Element dimensionElem = rootDocument.createElement(ELEM_DIMENSION_NAME);
			dimensionElem = rootDocument.createElement(ELEM_DIMENSION_NAME);
			dimensionElem.setAttribute(ATTR_NAME_NAME, dimension.getName());
			dimensionElem.setAttribute(ATTR_ATTR_REF_NAME, dimension.getAssociatedAttribute().getName());
			if (dimension.getAssociatedAssetName() != null && !dimension.getAssociatedAssetName().equals("")) {
				dimensionElem.setAttribute(ATTR_ASSET_REF, dimension.getAssociatedAssetName());
			}

			// TODO: Validate if the associated attribute is already part of the
			// schema before adding it
			dimensionsElem.appendChild(dimensionElem);

			if (dimension instanceof TimeDimension) {
				// Need additional attributes for each element in the schema
				TimeDimension td = (TimeDimension) dimension;
				TimeUnits tu = td.getTimeUnit();

				int qtrOffset = tu.getFirstQtrStartMonth();
				int mx = tu.getMultiplier();
				TimeUnits.Unit unit = tu.getTimeUnit();

				dimensionElem.setAttribute(ATTR_DIMENSION_TYPE, "time");
				dimensionElem.setAttribute(ATTR_TIME_DIMENSION_UNIT, unit.toString());
				dimensionElem.setAttribute(ATTR_TIME_DIMENSION_FREQUENCY, Integer.toString(mx));
				if (unit.toString().equalsIgnoreCase(TimeUnits.Unit.QUARTER.name())) {
					dimensionElem.setAttribute(ATTR_TIME_DIMENSION_QTR_OFFSET, Integer.toString(qtrOffset));
				}
			}
		}
	}

	@Override
	public void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
		Element actionsElement = rootDocument.createElement(ELEM_ACTION_DESCRIPTORS);

		for (ActionFunctionDescriptor ad : ads) {
			Element actionElement = rootDocument.createElement(ELEM_ACTION_DESCRIPTOR);

			serializeActionAttributes(ad, actionElement);
			serializeActionParams(ad, actionElement);

			actionsElement.appendChild(actionElement);
		}

		rootDocument.appendChild(actionsElement);

	}

	private void serializeActionParams(ActionFunctionDescriptor ad, Element actionElement) {
		actionElement.setAttribute(ATTR_NAME_NAME, ad.getName());
		actionElement.setAttribute(ATTR_CATEGORY, ad.getCategory());

		// Data Type not seriallized as there is no getDataType Method
		// actionElement.setAttribute(ATTR_DATATYPE_NAME, "" + ad);

		actionElement.setAttribute(ATTR_IMPL_CLASS, ad.getImplClass());
		actionElement.setAttribute(ATTR_DESCRIPTION, ad.getDescription());
	}

	private void serializeActionAttributes(ActionFunctionDescriptor ad, Element actionElement) {
		Element actionParamsElement = rootDocument.createElement(ELEM_ACTION_PARAMS);

		for (FunctionParamValue param : ad.getFunctionParamValues()) {
			Element actionParamElement = rootDocument.createElement(ELEM_ACTION_PARAM);

			actionParamElement.setAttribute(ATTR_ID_NAME, param.getName());
			actionParamElement.setAttribute(ATTR_DATATYPE_NAME, param.getDataType().name());
			actionParamElement.setAttribute(ATTR_ORDINAL, param.getIndex() + "");
			actionParamElement.setAttribute(ATTR_DESCRIPTION, param.getDescription());
			actionParamElement.setAttribute(ATTR_DEFAULT_VALUE, "" + param.getValue());

			actionParamsElement.appendChild(actionParamElement);
		}

		actionElement.appendChild(actionParamsElement);
	}

	@Override
	public void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
		Element functionsElement = rootDocument.createElement(ELEM_FUNCTION_DESCRIPTORS);

		for (MetricFunctionDescriptor mfd : mfds) {
			Element functionElement = rootDocument.createElement(ELEM_FUNCTION_DESCRIPTOR);

			serializeFunctionAttributes(mfd, functionElement);
			serializeFunctionParams(mfd, functionElement);
			serializeFunctionContext(mfd, functionElement);

			functionsElement.appendChild(functionElement);
		}

		rootDocument.appendChild(functionsElement);
	}

	@Override
	public void serialize(MetricFunctionDescriptor mfd) throws Exception {

		Element functionElement = rootDocument.createElement(ELEM_FUNCTION_DESCRIPTOR);

		serializeFunctionAttributes(mfd, functionElement);
		serializeFunctionParams(mfd, functionElement);
		serializeFunctionContext(mfd, functionElement);

		rootDocument.appendChild(functionElement);
	}

	private void serializeFunctionContext(MetricFunctionDescriptor mfd, Element functionElement) {
		Element functionContextElement = rootDocument.createElement(ELEM_FUNCTION_CONTEXT);

		for (FunctionParam param : mfd.getFunctionContexts()) {
			Element functionParamElement = rootDocument.createElement(ELEM_FUNCTION_PARAM);

			functionParamElement.setAttribute(ATTR_ID_NAME, param.getName());
			functionParamElement.setAttribute(ATTR_DATATYPE_NAME, param.getDataType().name());
			functionParamElement.setAttribute(ATTR_ORDINAL, param.getIndex() + "");
			functionParamElement.setAttribute(ATTR_DESCRIPTION, param.getDescription());

			functionContextElement.appendChild(functionParamElement);
		}

		functionElement.appendChild(functionContextElement);
	}

	private void serializeFunctionParams(MetricFunctionDescriptor mfd, Element functionElement) {
		Element functionParamsElement = rootDocument.createElement(ELEM_FUNCTION_PARAMS);

		for (FunctionParam param : mfd.getFunctionParams()) {
			Element functionParamElement = rootDocument.createElement(ELEM_FUNCTION_PARAM);

			functionParamElement.setAttribute(ATTR_ID_NAME, param.getName());
			functionParamElement.setAttribute(ATTR_DATATYPE_NAME, param.getDataType().name());
			functionParamElement.setAttribute(ATTR_ORDINAL, param.getIndex() + "");
			functionParamElement.setAttribute(ATTR_DESCRIPTION, param.getDescription());

			functionParamsElement.appendChild(functionParamElement);
		}

		functionElement.appendChild(functionParamsElement);
	}

	private void serializeFunctionAttributes(MetricFunctionDescriptor mfd, Element functionElement) {

		functionElement.setAttribute(ATTR_NAME_NAME, mfd.getName());
		functionElement.setAttribute(ATTR_CATEGORY, mfd.getCategory());
		functionElement.setAttribute(IS_MULTI_VALUED, "" + mfd.isMultiValued());
		functionElement.setAttribute(ATTR_IMPL_CLASS, mfd.getImplClass());
		functionElement.setAttribute(ATTR_DATATYPE_NAME, mfd.getMetricDataType().name());
		functionElement.setAttribute(ATTR_DESCRIPTION, mfd.getDescription());
	}

	private void serializeMeasurements(Element schemaElement) throws Exception {
		Element measurementsElem = rootDocument.createElement(ELEM_MEASUREMENTS_NAME);
		schemaElement.appendChild(measurementsElem);

		for (Measurement measurement : schema.getMeasurements()) {
			Element measurementElem = rootDocument.createElement(ELEM_MEASUREMENT_NAME);
			serializeMetaDataElement(measurementElem, measurement);
			serializeProperty(measurementElem, measurement);
			serializeMeasurementAttributes(measurementElem, measurement);

			measurementsElem.appendChild(measurementElem);

			MetricFunctionDescriptor mfd = measurement.getMetricFunctionDescriptor();

			if (mfd == null) {
				LOGGER.log(Level.ERROR, "Metric Function Decriptor not found for measurement:%s", measurement.getName());
				throw new Exception("Metric Function Decriptor not found for measurement:" + measurement.getName());
			}
			Element metricFunctionElem = rootDocument.createElement(ELEM_METRIC_FN_NAME);
			metricFunctionElem.setAttribute(ATTR_REF_NAME, mfd.getName());
			measurementElem.appendChild(metricFunctionElem);

			for (FunctionParam fp : mfd.getFunctionParams()) {
				Element fnParam = rootDocument.createElement(ELEM_FUNCTION_PARAM);
				metricFunctionElem.appendChild(fnParam);
				String fpName = fp.getName();
				String attribName = measurement.getFunctionParamBinding(fpName);
				fnParam.setAttribute(ATTR_NAME_NAME, fpName);
				fnParam.setAttribute(ATTR_ATTR_REF_NAME, attribName);
			}
		}
	}

	private void serializeMeasurementAttributes(Element measurementElem, Measurement measurement) {
		if (measurement.getUnitOfMeasurement() != null && !measurement.getUnitOfMeasurement().equals("")) {
			measurementElem.setAttribute(ATTR_UNIT_NAME, measurement.getUnitOfMeasurement());
		}

		serializeDependingMeasurements(measurementElem, measurement);
	}

	private void serializeDependingMeasurements(Element measurementElem, Measurement currentMeasurement) {
		Collection<Measurement> measurements = currentMeasurement.getDependencies();
		StringBuffer str = new StringBuffer();
		int count = 0;
		if (measurements.size() != 0) {
			for (Measurement measurement : measurements) {
				str.append(measurement.getName());
				count++;
				if (count != measurements.size()) {
					str.append(",");
				}
			}
			String exMeasurements = new String(str);
			measurementElem.setAttribute(ATTR_DEPENDS_NAME, exMeasurements);
		}
	}

	private void serializeMetaDataElement(Element elem, MetadataElement mDataElem) {
		elem.setAttribute(ATTR_NAME_NAME, mDataElem.getName());
		if (mDataElem.getDisplayName() != null && !mDataElem.getDisplayName().equals("")) {
			elem.setAttribute(ATTR_DISPLAY_NAME, mDataElem.getDisplayName());
		}

		if (mDataElem.getDescription() != null && !mDataElem.getDescription().equals("")) {
			elem.setAttribute(ATTR_DESCRIPTION, mDataElem.getDescription());
		}
	}

	private void serializeCubes(Element schemaElement) {
		Element cubesElement = rootDocument.createElement(ELEM_CUBES_NAME);
		schemaElement.appendChild(cubesElement);

		for (Cube cube : schema.getCubes()) {
			Element cubeElement = rootDocument.createElement(ELEM_CUBE_NAME);
			serializeMetaDataElement(cubeElement, cube);
			serializeProperty(cubeElement, cube);
			cubesElement.appendChild(cubeElement);
			serializeDimensionHierarchies(cube, cubeElement);
		}
	}

	private void serializeDimensionHierarchies(Cube cube, Element cubeElement) {
		Element hierarchysElem = rootDocument.createElement(ELEM_HIERARCHYS_NAME);
		cubeElement.appendChild(hierarchysElem);

		for (DimensionHierarchy hierarchy : cube.getDimensionHierarchies()) {
			Element hierarchyElem = rootDocument.createElement(ELEM_HIERARCHY_NAME);
			serializeMetaDataElement(hierarchyElem, hierarchy);
			serializeProperty(hierarchyElem, hierarchy);

//			if (!hierarchy.assetValidationEnabled()) {
//				hierarchyElem.setAttribute(ATTR_VALIDATE_ASSET, "" + hierarchy.assetValidationEnabled());
//			}

			if (!hierarchy.isEnabled()) {
				hierarchyElem.setAttribute(ATTR_HIERARCHY_ENABLED, "" + hierarchy.isEnabled());
			}

			// TODO: Validate if the dimension hierarchy is already part of the
			// schema before adding it
			hierarchysElem.appendChild(hierarchyElem);

			Element dimensionsRefElem = rootDocument.createElement(ELEM_DIMENSIONS_NAME);
			// TODO: Debug
			for (Dimension dimension : hierarchy.getDimensions()) {
				serializeDimensionRef(dimension, dimensionsRefElem, hierarchy);
			}

			// TODO: Validate if the dimension is already part of the schema
			// before adding it
			hierarchyElem.appendChild(dimensionsRefElem);

			Element measurementRefsElem = rootDocument.createElement(ELEM_MEASUREMENT_REFS_NAME);
			hierarchyElem.appendChild(measurementRefsElem);

			for (Measurement m : hierarchy.getMeasurements()) {
				Element measurementRefElem = rootDocument.createElement(ELEM_MEASUREMENT_NAME);
				measurementRefsElem.appendChild(measurementRefElem);

				String measurementName = m.getName();
				measurementRefElem.setAttribute(ATTR_REF_NAME, measurementName);
			}
		}
	}

	private void serializeDimensionRef(Dimension dimension, Element dimensionsRefElem, DimensionHierarchy hierarchy) {
		Element dimensionRef = rootDocument.createElement(ELEM_DIMENSION_NAME);
		dimensionRef.setAttribute(ATTR_REF_NAME, dimension.getName());
		if (!hierarchy.getComputeForLevel(hierarchy.getLevel(dimension.getName()))) {
			dimensionRef.setAttribute(ATTR_COMPUTE_NAME, "false");
		}

		Collection<Measurement> measurements = hierarchy.getExcludedMeasurements(dimension);
		StringBuffer str = new StringBuffer();
		int count = 0;
		if (measurements.size() != 0) {
			for (Measurement measurement : measurements) {
				str.append(measurement.getName());
				count++;
				if (count != measurements.size()) {
					str.append(",");
				}
			}
		}
		String exMeasurements = new String(str);
		dimensionRef.setAttribute(ATTR_EXCLUDE_NAME, exMeasurements);
		dimensionsRefElem.appendChild(dimensionRef);
	}

	/**
	 * @param attribute
	 * @param value
	 * @param rootDocument
	 */
	private void serializeAttribute(Attribute attribute, Object value, Document rootDocument, Element parentElement, boolean serializeAll) {
		Element attributeElement;

		if (serializeAll) {
			attributeElement = rootDocument.createElement(ELEM_ATTRIBUTE_NAME);
			attributeElement.setAttribute(ATTR_NAME_NAME, attribute.getName());
			attributeElement.setAttribute(ATTR_DATATYPE_NAME, attribute.getDataType().name());
		} else {
			attributeElement = rootDocument.createElement(ELEM_ATTRIBUTE_REF_NAME);
			attributeElement.setAttribute(ATTR_REF_NAME, attribute.getName());

			if (value != null) {
				attributeElement.setAttribute(ELEM_VALUE_NAME, value.toString());
			}
		}
		parentElement.appendChild(attributeElement);
	}

	// public static Map<Node> getChildren(Element parentElement) {
	// NodeList nodes = parentElement.getChildNodes();
	//
	// NodeList elements = new NodeList();
	// for (int i=0; i<nodes.getLength(); i++) {
	// Element element = (Element) nodes.item(i);
	// if(!(element.getNodeType() == Element.TEXT_NODE)) {
	// elements.addElement(element);
	// } else {
	// //if the node is a text node
	// String value = element.getNodeValue().trim();
	// if (!(value.length() == 0))
	// elements.addElement(element);
	// }
	// }
	// return elements;
	// } // getChildren(Element parentElement)

	@Override
	public Document getTransformed() {
		return rootDocument;
	}

	@Override
	public void serialize(List<Fact> facts) throws Exception {
		Element factsElement = rootDocument.createElement(ELEM_FACTS_NAME);

		for (Fact fact : facts) {
			serialize(fact, rootDocument, factsElement);
		}
		rootDocument.appendChild(factsElement);
	}

	@Override
	public void serializeRules(List<RuleDef> ruleDefs) throws Exception {
		Element rulesElement = rootDocument.createElement(ELEM_RULES_NAME);

		for (RuleDef ruleDef : ruleDefs) {
			QuerySerializer.INSTANCE.serialize(ruleDef, rootDocument, rulesElement);
		}
		rootDocument.appendChild(rulesElement);
	}

	public void serialize(Fact fact, Document rootDocument, Element parentElement) {
		Element factElement = rootDocument.createElement(ELEM_FACT_NAME);
		RtaSchema schema = fact.getOwnerSchema();

		factElement.setAttribute(ATTR_SCHEMA_NAME, schema.getName());
		parentElement.appendChild(factElement);

		// Serialize dimensions
		Element attributesElement = rootDocument.createElement(ELEM_ATTRIBUTES_NAME);
		factElement.appendChild(attributesElement);

		Collection<String> attributeNames = fact.getAttributeNames();

		for (String attributeName : attributeNames) {
			serializeAttribute(schema.getAttribute(attributeName), fact.getAttribute(attributeName), rootDocument, attributesElement, false);
		}

	}

	/**
	 * @param fact
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void serialize(Fact fact) throws Exception {
		Element factElement = rootDocument.createElement(ELEM_FACT_NAME);
		RtaSchema schema = fact.getOwnerSchema();

		factElement.setAttribute(ATTR_SCHEMA_NAME, schema.getName());
		rootDocument.appendChild(factElement);

		// Serialize dimensions
		Element attributesElement = rootDocument.createElement(ELEM_ATTRIBUTES_NAME);
		factElement.appendChild(attributesElement);
		Collection<String> attributeNames = fact.getAttributeNames();

		for (String attributeName : attributeNames) {
			serializeAttribute(schema.getAttribute(attributeName), fact.getAttribute(attributeName), rootDocument, attributesElement, false);
		}
	}

	/**
	 * @param queryDef
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void serialize(QueryDef queryDef) throws Exception {
		QuerySerializer.INSTANCE.serializeQueryDef(queryDef, rootDocument, null);
	}

	/**
	 * @param ruleDef
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void serialize(RuleDef ruleDef) throws Exception {
		QuerySerializer.INSTANCE.serialize(ruleDef, rootDocument, null);
	}

}
