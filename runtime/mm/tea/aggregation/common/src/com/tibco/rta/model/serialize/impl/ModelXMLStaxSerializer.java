package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MetricValueDescriptor;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryDef;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ATTRIBUTES_NAME;
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
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRIC_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RESULTS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_TUPLENAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_TUPLE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VALUE_NAME;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 24/1/13 Time: 10:34 AM To change this template use File | Settings | File Templates.
 */
public class ModelXMLStaxSerializer implements ModelSerializer<XMLStreamWriter> {

	private XMLStreamWriter xmlStreamWriter;

	public ModelXMLStaxSerializer(OutputStream outputStream) throws Exception {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		xmlStreamWriter = factory.createXMLStreamWriter(outputStream, Charset.forName("UTF-8").name());
	}

	public ModelXMLStaxSerializer(Writer writer) throws Exception {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		xmlStreamWriter = factory.createXMLStreamWriter(writer);
	}

	@Override
	public void serialize(RtaSchema schema) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	@Override
	public void serialize(Fact fact) throws Exception {
		xmlStreamWriter.writeStartElement(ELEM_FACT_NAME);
		RtaSchema schema = fact.getOwnerSchema();
		xmlStreamWriter.writeAttribute(ATTR_SCHEMA_NAME, schema.getName());
		xmlStreamWriter.writeStartElement(ELEM_ATTRIBUTES_NAME);

		for (String attrName: fact.getAttributeNames()) {
			xmlStreamWriter.writeStartElement(ELEM_ATTRIBUTE_REF_NAME);
			xmlStreamWriter.writeAttribute(ATTR_REF_NAME, "" + schema.getAttribute(attrName).getName());
			xmlStreamWriter.writeAttribute(ATTR_VALUE_NAME, "" + fact.getAttribute(attrName));
			xmlStreamWriter.writeEndElement();
		}

		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeEndElement();
	}

	@Override
	public void serialize(List<Fact> facts) throws Exception {

		xmlStreamWriter.writeStartElement(ELEM_FACTS_NAME);
		for (Fact fact: facts) {
			serialize(fact);
		}
		xmlStreamWriter.writeEndElement();
	}

	@Override
	public void serialize(QueryDef queryDef) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	@Override
	public XMLStreamWriter getTransformed() {
		return xmlStreamWriter;
	}

	public void serialize(QueryResultTuple queryResultTuple) throws Exception {
		// Serialize root
		// Serialize metric
		writeTuple(queryResultTuple);
	}

	public void serialize(QueryResultTuple[] queryResultTuples) throws Exception {
		xmlStreamWriter.writeStartElement(ELEM_RESULTS_NAME);

		for (QueryResultTuple queryResultTuple: queryResultTuples) {
			writeTuple(queryResultTuple);
		}

		xmlStreamWriter.writeEndElement();
	}

	private void writeTuple(QueryResultTuple queryResultTuple) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(ELEM_TUPLE_NAME);
		// Write query name as attr
		xmlStreamWriter.writeStartElement(ELEM_TUPLENAME_NAME);
		xmlStreamWriter.writeCharacters(queryResultTuple.getQueryName());
		xmlStreamWriter.writeEndElement();
		// Write metric
		writeMetric(queryResultTuple.getMetric());

		xmlStreamWriter.writeEndElement();
	}

	private void writeMetric(Metric<?> metric) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(ELEM_METRIC_NAME);
		// Write key

		Key key = metric.getKey();
		MetricValueDescriptor desc = metric.getDescriptor();

		writeKey(key);
		writeDesc(desc);
		writeValue(metric);

		xmlStreamWriter.writeEndElement();
	}

	private void writeValue(Metric<?> metric) throws XMLStreamException {

		if (metric instanceof SingleValueMetric) {
			SingleValueMetric<?> singleVMetric = (SingleValueMetric<?>) metric;
			Object computedValue = singleVMetric.getValue();

			xmlStreamWriter.writeStartElement(ELEM_COMPUTATION_VALUE);
			xmlStreamWriter.writeCharacters(computedValue.toString());
			xmlStreamWriter.writeEndElement();
		}
	}

	private void writeDesc(MetricValueDescriptor desc) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(ELEM_DESC_NAME);

		xmlStreamWriter.writeStartElement(ELEM_SCHEMA_NAME);
		xmlStreamWriter.writeCharacters(desc.getSchemaName());
		xmlStreamWriter.writeEndElement();

		xmlStreamWriter.writeStartElement(ELEM_CUBE_NAME);
		xmlStreamWriter.writeCharacters(desc.getCubeName());
		xmlStreamWriter.writeEndElement();

		xmlStreamWriter.writeStartElement(ELEM_HIERARCHY_NAME);
		xmlStreamWriter.writeCharacters(desc.getDimHierarchyName());
		xmlStreamWriter.writeEndElement();

		xmlStreamWriter.writeStartElement(ELEM_DIMENSION_NAME);
		xmlStreamWriter.writeCharacters(desc.getDimensionName());
		xmlStreamWriter.writeEndElement();

		xmlStreamWriter.writeStartElement(ELEM_MEASUREMENT_NAME);
		xmlStreamWriter.writeCharacters(desc.getMeasurementName());
		xmlStreamWriter.writeEndElement();

		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_NAME);
		// xmlStreamWriter.writeCharacters(desc.getMetricDescriptor().getName());
		// xmlStreamWriter.writeEndElement();
		//
		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_CATEGORY);
		// xmlStreamWriter.writeCharacters(desc.getMetricDescriptor().getCategory());
		// xmlStreamWriter.writeEndElement();
		//
		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_MULTIVALUED);
		// xmlStreamWriter.writeCharacters(String.valueOf(desc.getMetricDescriptor().isMultiValued()));
		// xmlStreamWriter.writeEndElement();
		//
		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_CLASS);
		// xmlStreamWriter.writeCharacters(String.valueOf(desc.getMetricDescriptor().getClass()));
		// xmlStreamWriter.writeEndElement();
		//
		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_DATATYPE);
		// xmlStreamWriter.writeCharacters(String.valueOf(desc.getMetricDescriptor().getMetricDataType().toString()));
		// xmlStreamWriter.writeEndElement();

		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR_DESC);
		// xmlStreamWriter.writeCharacters(String.valueOf(desc.getMetricDescriptor().getDescription()));
		// xmlStreamWriter.writeEndElement();
		//
		// xmlStreamWriter.writeStartElement(ELEM_METRICFUNCTION_DESCRIPTOR);
		// xmlStreamWriter.writeCharacters(desc.getMetricDescriptor().toString());
		// xmlStreamWriter.writeEndElement();

		xmlStreamWriter.writeEndElement();
	}

	private void writeKey(Key key) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(ELEM_KEY_NAME);

		if (key instanceof MetricKey) {
			MetricKey metricKey = (MetricKey) key;

			xmlStreamWriter.writeStartElement(ELEM_SCHEMA_NAME);
			xmlStreamWriter.writeCharacters(metricKey.getSchemaName());
			xmlStreamWriter.writeEndElement();

			xmlStreamWriter.writeStartElement(ELEM_CUBE_NAME);
			xmlStreamWriter.writeCharacters(metricKey.getCubeName());
			xmlStreamWriter.writeEndElement();

			xmlStreamWriter.writeStartElement(ELEM_HIERARCHY_NAME);
			xmlStreamWriter.writeCharacters(metricKey.getDimensionHierarchyName());
			xmlStreamWriter.writeEndElement();

			// xmlStreamWriter.writeStartElement(ELEM_MEASUREMENT_NAME);
			// xmlStreamWriter.writeCharacters(metricKey.getMeasurementName());
			// xmlStreamWriter.writeEndElement();

			xmlStreamWriter.writeStartElement(ELEM_DIMENSIONS_NAME);

			for (String dimensionName: metricKey.getDimensionNames()) {
				Object dimensionValue = metricKey.getDimensionValue(dimensionName);

				if (dimensionValue != null) {
					xmlStreamWriter.writeStartElement(ELEM_DIMENSION_NAME);

					xmlStreamWriter.writeStartElement(ELEM_DIM_NAME);
					xmlStreamWriter.writeCharacters(dimensionName);
					xmlStreamWriter.writeEndElement();

					xmlStreamWriter.writeStartElement(ELEM_VALUE_NAME);
					xmlStreamWriter.writeCharacters(dimensionValue.toString());
					xmlStreamWriter.writeEndElement();

					xmlStreamWriter.writeEndElement();
				}
			}
			xmlStreamWriter.writeEndElement();
		}
		xmlStreamWriter.writeEndElement();
	}

	@Override
	public void serialize(RuleDef ruleDef) throws Exception {
		throw new UnsupportedOperationException("NA");
	}

	@Override
    public void serializeRules(List<RuleDef> ruleDefs) throws Exception {
		throw new UnsupportedOperationException("NA");	    
    }

	@Override
    public void serialize(MetricFunctionDescriptor mfd) throws Exception {
		throw new UnsupportedOperationException("NA");	    
    }

	@Override
    public void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
		throw new UnsupportedOperationException("NA");	    
    }

	@Override
    public void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
		throw new UnsupportedOperationException("NA");	    
    }
}
