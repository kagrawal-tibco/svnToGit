package com.tibco.rta.model.serialize;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.QueryDef;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/10/12
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ModelDeserializer {

    /**
     * @param file
     * @return
     * @throws Exception
     */
    public RtaSchema deserialize(File file) throws Exception;

    /**
     * @param inputSource
     * @return
     * @throws Exception
     */
    public RtaSchema deserializeSchema(InputSource inputSource) throws Exception;

    /**
     * @param inputSource
     * @return
     * @throws Exception
     */
    public Fact deserializeFact(InputSource inputSource) throws Exception;

    /**
     * @param inputSource
     * @return
     * @throws Exception
     */
    public List<Fact> deserializeFacts(InputSource inputSource) throws Exception;


    /**
     * @param inputSource
     * @return
     * @throws Exception
     */
    public QueryDef deserializeQuery(InputSource inputSource) throws Exception;
    
    /**
     * @param inputSource
     * @return
     * @throws Exception
     */
    public RuleDef deserializeRule(InputSource inputSource) throws Exception;
    
    public List<RuleDef> deserializeRules(InputSource inputSource) throws Exception;

}
