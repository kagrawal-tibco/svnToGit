package com.tibco.rta.model.serialize.impl;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.AttributeImpl;
import com.tibco.rta.model.impl.BaseMetadadataElementImpl;
import com.tibco.rta.model.impl.CubeImpl;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;
import com.tibco.rta.model.impl.DimensionImpl;
import com.tibco.rta.model.impl.FunctionDescriptorImpl;
import com.tibco.rta.model.impl.FunctionDescriptorImpl.FunctionParamImpl;
import com.tibco.rta.model.impl.MeasurementImpl;
import com.tibco.rta.model.impl.MetricFunctionDescriptorImpl;
import com.tibco.rta.model.impl.RetentionPolicyImpl;
import com.tibco.rta.model.impl.RtaSchemaImpl;
import com.tibco.rta.model.impl.TimeDimensionImpl;
import com.tibco.rta.model.impl.TimeUnitsImpl;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.rta.model.rule.impl.TimeBasedInvokeConstraintImpl;
import com.tibco.rta.model.serialize.jaxb.adapter.HierarchyDimension;
import com.tibco.rta.model.serialize.jaxb.adapter.ParamMapAdapter;
import com.tibco.rta.model.serialize.jaxb.adapter.RtaSchemaCollection;
import com.tibco.rta.model.serialize.jaxb.adapter.RuleDefCollection;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.impl.AndFilterImpl;
import com.tibco.rta.query.filter.impl.BaseRelationalFilterImpl;
import com.tibco.rta.query.filter.impl.EqFilterImpl;
import com.tibco.rta.query.filter.impl.FilterImpl;
import com.tibco.rta.query.filter.impl.GEFilterImpl;
import com.tibco.rta.query.filter.impl.GtFilterImpl;
import com.tibco.rta.query.filter.impl.InFilterImpl;
import com.tibco.rta.query.filter.impl.LEFilterImpl;
import com.tibco.rta.query.filter.impl.LikeFilterImpl;
import com.tibco.rta.query.filter.impl.LtFilterImpl;
import com.tibco.rta.query.filter.impl.NEqFilterImpl;
import com.tibco.rta.query.filter.impl.NotFilterImpl;
import com.tibco.rta.query.filter.impl.OrFilterImpl;
import com.tibco.rta.query.filter.impl.RelationalFilterImpl;
import com.tibco.rta.query.impl.BaseQueryDefImpl;
import com.tibco.rta.query.impl.QueryDefImpl;
import com.tibco.rta.query.impl.QueryFilterDefImpl;
import com.tibco.rta.query.impl.QueryKeyDefImpl;

public class ModelJAXBSerializer {

	protected ModelJAXBSerializer () {
		
	}
	
	public void serialize(RtaSchema schema, Writer writer) throws Exception {
		Marshaller marsheller = getMarshallerForSchema();
		marsheller.marshal(schema, writer);
	}

	public void serialize(RtaSchema schema, File file) throws Exception {
		Marshaller marsheller = getMarshallerForSchema();		
		marsheller.marshal(schema, file);
	}

	public void serialize(QueryDef queryDef, OutputStream outputStream) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(queryDef, outputStream);
	}

	public void serialize(QueryDef queryDef, Writer writer) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(queryDef, writer);
	}

	public void serialize(QueryDef queryDef, File file) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(queryDef, file);
	}

	private Class[] getContextClassesForQueryAndRule() {
		Class[] classes = {QueryDefImpl.class, BaseQueryDefImpl.class, QueryFilterDefImpl.class, 
				AndFilterImpl.class, EqFilterImpl.class, NEqFilterImpl.class, GtFilterImpl.class, FilterImpl.class, RelationalFilterImpl.class
				  , BaseRelationalFilterImpl.class, OrFilterImpl.class, GEFilterImpl.class, NotFilterImpl.class, LEFilterImpl.class,
				  LtFilterImpl.class, InFilterImpl.class, LikeFilterImpl.class, QueryKeyDefImpl.class, RuleDefImpl.class
				  , TimeBasedInvokeConstraintImpl.class, RuleDefCollection.class};
		
		return classes;
	}
	
	private Marshaller getMarshallerForQueryAndRule() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(getContextClassesForQueryAndRule());

		Marshaller marsheller = context.createMarshaller();
		marsheller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marsheller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "true");
		return marsheller;
	}
	
	public void serialize(RuleDef ruleDef, OutputStream outputStream) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(ruleDef, outputStream);
	}


	public void serialize(RuleDef ruleDef, Writer writer) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(ruleDef, writer);
	}

	public void serialize(RuleDef ruleDef, File file) throws Exception {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		marsheller.marshal(ruleDef, file);
	}


	public void serialize(RtaSchema schema, OutputStream outputStream) throws Exception {
		Marshaller marsheller = getMarshallerForSchema();
		marsheller.marshal(schema, outputStream);
	}
	
	private Marshaller getMarshallerForSchema() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(RtaSchemaImpl.class, RetentionPolicyImpl.class, AttributeImpl.class, DimensionImpl.class, TimeDimensionImpl.class,
				TimeUnitsImpl.class, ParamMapAdapter.EntryMap.class, BaseMetadadataElementImpl.class, MeasurementImpl.class, MetricFunctionDescriptorImpl.class,
				FunctionDescriptorImpl.class, FunctionParamImpl.class, CubeImpl.class, DimensionHierarchyImpl.class, HierarchyDimension.class, RtaSchemaCollection.class);

		Marshaller marsheller = context.createMarshaller();
		marsheller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marsheller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "true");
		return marsheller;
	}
	
	public void serializeRules(List<RuleDef> ruleDefs, File file) throws JAXBException {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		RuleDefCollection rules = new RuleDefCollection(ruleDefs);
		marsheller.marshal(rules, file);
	}
	

	public void serializeRules(List<RuleDef> ruleDefs, OutputStream outputStream) throws JAXBException {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		RuleDefCollection rules = new RuleDefCollection(ruleDefs);
		marsheller.marshal(rules, outputStream);
	}

	public void serializeRules(List<RuleDef> ruleDefs, Writer writer) throws JAXBException {
		Marshaller marsheller = getMarshallerForQueryAndRule();
		RuleDefCollection rules = new RuleDefCollection(ruleDefs);
		marsheller.marshal(rules, writer);
	}

	public void serializeSchemas(List<RtaSchema> schema, Writer writer) throws JAXBException {
		Marshaller marsheller = getMarshallerForSchema();
		RtaSchemaCollection schemaCollection = new RtaSchemaCollection(schema);
		marsheller.marshal(schemaCollection, writer);			
	}

}
