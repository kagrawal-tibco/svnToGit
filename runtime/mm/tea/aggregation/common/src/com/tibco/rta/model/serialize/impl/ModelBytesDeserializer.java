package com.tibco.rta.model.serialize.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.util.IOUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 2/1/13
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelBytesDeserializer implements ModelDeserializer {

    @Override
    public RtaSchema deserialize(File file) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RtaSchema deserializeSchema(InputSource inputSource) throws Exception {    	
        return IOUtils.deserialize(inputSource.getByteStream());
    }

    @Override
    public Fact deserializeFact(InputSource inputSource) throws Exception {
        return IOUtils.deserialize(inputSource.getByteStream());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Fact> deserializeFacts(InputSource inputSource) throws Exception {
        return (List<Fact>)IOUtils.deserialize(inputSource.getByteStream());
    }

    @Override
    public QueryDef deserializeQuery(InputSource inputSource) throws Exception {
    	return IOUtils.deserialize(inputSource.getByteStream()); 
    }

	@Override
	public RuleDef deserializeRule(InputSource inputSource) throws Exception {		
		return IOUtils.deserialize(inputSource.getByteStream());
	}

	@Override
    public ArrayList<RuleDef> deserializeRules(InputSource inputSource) throws Exception {
		return IOUtils.deserialize(inputSource.getByteStream());
    }
}
