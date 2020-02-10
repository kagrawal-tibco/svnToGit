package com.tibco.rta.model.serialize.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTE_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_COMPUTATION_VALUE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DESC_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIM_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FACTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FACT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_KEY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_CATEGORY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_CLASS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_DATATYPE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_DESC;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_MULTIVALUED;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRICFUNCTION_DESCRIPTOR_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRIC_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RESULTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_TUPLENAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_TUPLE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VALUE_NAME;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.tibco.rta.query.QueryResultTuple;
import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.query.QueryDef;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 24/1/13 Time: 11:07 AM To change this template use File | Settings | File Templates.
 */
public class ModelXMLStaxDeserializer implements ModelDeserializer {

	private String currentLocalName;

	/**
	 * Currently dimension we are processing
	 */
	private String currentDimensionName;

	/**
	 * Currently processing tuple
	 */
	private QueryResultTuple currentResultTuple;

	/**
	 * Currently processing metric
	 */
	private Metric<?> currentMetric;

	/**
	 * Currently processing metric key
	 */
	private MetricKeyImpl currentMetricKey;
	private MetricValueDescriptorImpl currentDesc;
	private String currentMetricFunDName;
	private String currentMetricFunDCategory;
	private String currentMetricFunDMulti;
	private String currentMetricFunDClass;
	private String currentMetricFunDDatatype;
	private String currentMetricFunDDesc;
	Source source;
	XMLInputFactory factory;
	XMLStreamReader sReader;

	public ModelXMLStaxDeserializer() {
		try {
			factory = XMLInputFactory.newInstance();
		} catch (Exception e) {
		}
	}

	@Override
	public RtaSchema deserialize(File file) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	@Override
	public RtaSchema deserializeSchema(InputSource inputStream) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	private RtaSchema getSchema(String schemaName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		return schema;
	}

	@Override
	public Fact deserializeFact(InputSource inputSource) throws Exception {		
		source = new StreamSource(inputSource.getByteStream());
		sReader = factory.createXMLStreamReader(source);

		Fact fact = null;
		String attrName = null;
		RtaSchema schema = null;

		while (sReader.hasNext()) {
			int type = sReader.next();
			switch (type) {
				case XMLStreamConstants.START_ELEMENT :
					currentLocalName = sReader.getLocalName();
					if (ELEM_FACT_NAME.equals(currentLocalName)) {
						if (sReader.getAttributeCount() != 0) {
							attrName = sReader.getAttributeLocalName(0);
							if (attrName.equals(ELEM_SCHEMA_NAME)) {
								String schemaName = sReader.getAttributeValue(0);
								schema = getSchema(schemaName);
								// System.out.println("Schema:" + schema);
								fact = new FactImpl(schema);
							}
						}
					} else if (ELEM_ATTRIBUTE_REF_NAME.equals(currentLocalName)) {
						if (sReader.getAttributeCount() != 0) {
							String refName = sReader.getAttributeLocalName(0);
							String valueName = sReader.getAttributeLocalName(1);;
							Attribute attr = null;
							if (refName.equals(ATTR_REF_NAME)) {
								attrName = sReader.getAttributeValue(0);
								attr = schema.getAttribute(attrName);
							}
							if (valueName.equals(ATTR_VALUE_NAME)) {
								String value = sReader.getAttributeValue(1);
								Object val;
								switch (attr.getDataType()) {
									case DOUBLE :
										val = Double.parseDouble(value);
										break;
									case LONG :
										val = Long.parseLong(value);
										break;
									default :
										val = value;
								}
								fact.setAttribute(attrName, val);
							}
						}
					}
					break;
			}
		}

		return fact;

	}

	@Override
	public List<Fact> deserializeFacts(InputSource inputSource) throws Exception {
		source = new StreamSource(inputSource.getByteStream());
		sReader = factory.createXMLStreamReader(source);
		List<Fact> facts = null;
		Fact fact = null;
		String attrName = null;
		RtaSchema schema = null;

		while (sReader.hasNext()) {
			int type = sReader.next();
			switch (type) {
				case XMLStreamConstants.START_ELEMENT :
					currentLocalName = sReader.getLocalName();
					if (ELEM_FACTS_NAME.equals(currentLocalName)) {
						facts = new ArrayList<Fact>();
					}
					if (ELEM_FACT_NAME.equals(currentLocalName)) {
						if (sReader.getAttributeCount() != 0) {
							attrName = sReader.getAttributeLocalName(0);
							if (attrName.equals(ELEM_SCHEMA_NAME)) {
								String schemaName = sReader.getAttributeValue(0);
								schema = getSchema(schemaName);
								fact = new FactImpl(schema);
								facts.add(fact);
								System.out.println("\nAdding fact:" + fact.getKey());
							}
						}
					} else if (ELEM_ATTRIBUTE_REF_NAME.equals(currentLocalName)) {
						if (sReader.getAttributeCount() != 0) {
							String refName = sReader.getAttributeLocalName(0);
							String valueName = sReader.getAttributeLocalName(1);;
							Attribute attr = null;
							if (refName.equals(ATTR_REF_NAME)) {
								attrName = sReader.getAttributeValue(0);
								attr = schema.getAttribute(attrName);
							}
							if (valueName.equals(ELEM_VALUE_NAME)) {
								String value = sReader.getAttributeValue(1);
								Object val;
								switch (attr.getDataType()) {
									case DOUBLE :
										val = Double.parseDouble(value);
										break;
									case LONG :
										val = Long.parseLong(value);
										break;
									default :
										val = value;
								}
								fact.setAttribute(attrName, val);
								facts.add(fact);
							}
						}
					}
					break;
			}
		}

		return facts;
	}

	public List<QueryResultTuple> deserializeTuples(InputSource inputSource) throws Exception {
		source = new StreamSource(inputSource.getByteStream());
		List<QueryResultTuple> queryResultTuples = null;
		while (sReader.hasNext()) {
			int type = sReader.next();
			switch (type) {
				case XMLStreamConstants.START_ELEMENT : {
					currentLocalName = sReader.getLocalName();
					if (ELEM_RESULTS_NAME.equals(currentLocalName)) {
						queryResultTuples = new ArrayList<QueryResultTuple>();
					} else if (ELEM_TUPLE_NAME.equals(currentLocalName)) {
						currentResultTuple = new QueryResultTuple();
						if (queryResultTuples != null) {
							queryResultTuples.add(currentResultTuple);
						}
					} else if (ELEM_METRIC_NAME.equals(currentLocalName)) {
						// TODO instantiate on basis of multiplicity
						currentMetric = new SingleValueMetricImpl<Object>();
						currentResultTuple.setMetric(currentMetric);
					} else if (ELEM_KEY_NAME.equals(currentLocalName)) {
						// TODO instantiate appropriate key
						currentMetricKey = new MetricKeyImpl();
						currentMetric.setKey(currentMetricKey);
						// Deserialize key
					} else if (ELEM_DESC_NAME.equals(currentLocalName)) {
						currentDesc = new MetricValueDescriptorImpl();
						currentMetric.setDescriptor(currentDesc);
						currentDesc.setSchemaName(currentMetricKey.getSchemaName());
						currentDesc.setCubeName(currentMetricKey.getCubeName());
						currentDesc.setDimHierarchyName(currentMetricKey.getDimensionHierarchyName());
						// currentDesc.setMeasurementName(currentMetricKey.getMeasurementName());
					}
					break;
				}
				case XMLStreamConstants.CHARACTERS : {
					String text = sReader.getText();
					if (ELEM_TUPLENAME_NAME.equals(currentLocalName)) {
						if (currentResultTuple != null) {
							currentResultTuple.setQueryName(text);
						}
					}
					if (currentMetricKey == null) {
						continue;
					}
					if (ELEM_SCHEMA_NAME.equals(currentLocalName)) {
						currentMetricKey.setSchemaName(text);
					} else if (ELEM_CUBE_NAME.equals(currentLocalName)) {
						currentMetricKey.setCubeName(text);
					} else if (ELEM_HIERARCHY_NAME.equals(currentLocalName)) {
						currentMetricKey.setDimensionHierarchyName(text);
					} else if (ELEM_MEASUREMENT_NAME.equals(currentLocalName)) {
						// currentMetricKey.setMeasurementName(text);
					} else if (ELEM_DIMENSIONS_NAME.equals(currentLocalName)) {
						currentMetricKey.setDimensionLevelName(text);
					} else if (ELEM_DIM_NAME.equals(currentLocalName)) {
						currentDimensionName = text;
					} else if (ELEM_VALUE_NAME.equals(currentLocalName)) {
						currentMetricKey.addDimensionValueToKey(currentDimensionName, text);
					} else if (ELEM_DIMENSION_NAME.equals(currentLocalName)) {
						currentDesc.setDimensionName(text);
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_NAME.equals(currentLocalName)) {
						currentMetricFunDName = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_CATEGORY.equals(currentLocalName)) {
						currentMetricFunDCategory = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_MULTIVALUED.equals(currentLocalName)) {
						currentMetricFunDMulti = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_CLASS.equals(currentLocalName)) {
						currentMetricFunDClass = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_DATATYPE.equals(currentLocalName)) {
						currentMetricFunDDatatype = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR_DESC.equals(currentLocalName)) {
						currentMetricFunDDesc = text;
					} else if (ELEM_METRICFUNCTION_DESCRIPTOR.equals(currentLocalName)) {
						// currentDesc.setMetricFunctionDescriptor(new MetricFunctionDescriptorImpl(currentMetricFunDName, currentMetricFunDCategory, Boolean.getBoolean(currentMetricFunDMulti), currentMetricFunDClass, DataType.getByLiteral(currentMetricFunDDatatype), currentMetricFunDDesc));
					} else if (ELEM_COMPUTATION_VALUE.equals(currentLocalName)) {
						if (currentMetric instanceof SingleValueMetric) {
							((SingleValueMetricImpl) currentMetric).setValue(text);
						}
					}
					break;
				}
				case XMLStreamConstants.ATTRIBUTE : {
					currentLocalName = sReader.getLocalName();
					System.out.println(currentLocalName);
				}
			}
		}
		sReader.close();
		return queryResultTuples;
	}

	@Override
	public QueryDef deserializeQuery(InputSource inputStream) throws Exception {
		return null;  // To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public RuleDef deserializeRule(InputSource inputStream) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	@Override
    public List<RuleDef> deserializeRules(InputSource inputStream) throws Exception {
		throw new UnsupportedOperationException("NA");
	}
}
