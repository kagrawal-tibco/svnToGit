package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.query.QueryDef;
import org.xml.sax.InputSource;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/2/14
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ModelDeserializerAdapter implements ModelDeserializer {

    @Override
    public RtaSchema deserialize(File file) throws Exception {
        return null;
    }

    @Override
    public RtaSchema deserializeSchema(InputSource inputSource) throws Exception {
        return null;
    }

    @Override
    public Fact deserializeFact(InputSource inputSource) throws Exception {
        return null;
    }

    @Override
    public List<Fact> deserializeFacts(InputSource inputSource) throws Exception {
        return null;
    }

    @Override
    public QueryDef deserializeQuery(InputSource inputSource) throws Exception {
        return null;
    }

    @Override
    public RuleDef deserializeRule(InputSource inputSource) throws Exception {
        return null;
    }

    @Override
    public List<RuleDef> deserializeRules(InputSource inputSource) throws Exception {
        return null;
    }
}
