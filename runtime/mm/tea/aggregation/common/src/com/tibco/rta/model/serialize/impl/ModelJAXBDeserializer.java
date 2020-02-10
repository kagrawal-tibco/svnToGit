package com.tibco.rta.model.serialize.impl;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
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
import com.tibco.rta.model.serialize.ModelDeserializer;
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

public class ModelJAXBDeserializer implements ModelDeserializer{
	
	protected ModelJAXBDeserializer () {
		
	}
		
	@Override
	public RtaSchema deserializeSchema(InputSource inputStream) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForSchema();
		unMarsheller.setListener(new JAXBSchemaUnMarshllerListener());
		return (RtaSchema) unMarsheller.unmarshal(inputStream);
	}

	private Unmarshaller getUnMarshalleForSchema() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(RtaSchemaImpl.class, RetentionPolicyImpl.class, AttributeImpl.class, DimensionImpl.class, TimeDimensionImpl.class,
				TimeUnitsImpl.class, ParamMapAdapter.EntryMap.class, BaseMetadadataElementImpl.class, MeasurementImpl.class, MetricFunctionDescriptorImpl.class,
				FunctionDescriptorImpl.class, FunctionParamImpl.class, CubeImpl.class, DimensionHierarchyImpl.class, HierarchyDimension.class, RtaSchemaCollection.class);
		return context.createUnmarshaller();
	}

	private Unmarshaller getUnMarshalleForQueryAndRule() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(QueryDefImpl.class, BaseQueryDefImpl.class, QueryFilterDefImpl.class, 
				AndFilterImpl.class, EqFilterImpl.class, NEqFilterImpl.class, GtFilterImpl.class, FilterImpl.class, RelationalFilterImpl.class
			  , BaseRelationalFilterImpl.class, OrFilterImpl.class, GEFilterImpl.class, NotFilterImpl.class, LEFilterImpl.class,
			  LtFilterImpl.class, InFilterImpl.class, LikeFilterImpl.class, QueryKeyDefImpl.class, RuleDefImpl.class,
			  TimeBasedInvokeConstraintImpl.class, RuleDefCollection.class);

		return context.createUnmarshaller();
	}

	
	@Override
	public RtaSchema deserialize(File file) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForSchema();
		unMarsheller.setListener(new JAXBSchemaUnMarshllerListener());
		return (RtaSchema) unMarsheller.unmarshal(file);
	}

	@Override
	public Fact deserializeFact(InputSource inputSource) throws Exception {		
		throw new Exception("NA");
	}

	@Override
	public List<Fact> deserializeFacts(InputSource inputSource) throws Exception {
		throw new Exception("NA");
	}

	@Override
	public QueryDef deserializeQuery(InputSource inputSource) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForQueryAndRule();
		unMarsheller.setListener(new JAXBQueryUnMarshllerListener());
		return (QueryDef) unMarsheller.unmarshal(inputSource);

	}

	public QueryDef deserializeQuery(File file) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForQueryAndRule();
		unMarsheller.setListener(new JAXBQueryUnMarshllerListener());
		return (QueryDef) unMarsheller.unmarshal(file);
	}

	@Override
	public RuleDef deserializeRule(InputSource inputSource) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForQueryAndRule();
		unMarsheller.setListener(new JAXBQueryUnMarshllerListener());
		return (RuleDef) unMarsheller.unmarshal(inputSource);

	}

	public RuleDef deserializeRule(File file) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForQueryAndRule();
		unMarsheller.setListener(new JAXBQueryUnMarshllerListener());
		return (RuleDef) unMarsheller.unmarshal(file);
	}

	
	@Override
	public List<RuleDef> deserializeRules(InputSource inputSource) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForQueryAndRule();
		unMarsheller.setListener(new JAXBQueryUnMarshllerListener());
		RuleDefCollection ruleDefs = (RuleDefCollection) unMarsheller.unmarshal(inputSource);
		return ruleDefs.getRuleDefs();
	}
	
	public Collection<RtaSchema> deserializeSchemas(InputSource inputStream) throws Exception {
		Unmarshaller unMarsheller = getUnMarshalleForSchema();
		unMarsheller.setListener(new JAXBSchemaUnMarshllerListener());
		RtaSchemaCollection schemas = (RtaSchemaCollection) unMarsheller.unmarshal(inputStream);
		return schemas.getSchemas();
	}


}
