package com.tibco.cep.query.exec;

import java.util.Map;

import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.query.snapshot.Bridge;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 5:11:21 PM
 */

/**
 * Implemented by all generated QueryExecutionPlan's.
 */
public interface QueryExecutionPlan {

    /**
     * @return Bridge if there is one, else null.
     */
    Bridge getBridge();

    /**
     * @return The name of the agent/region/section in which this Query plan is being used.
     */
    String getRegionName();

    /**
     * @return String name.
     */
    String getName();


    /**
     * @return String text of the query.
     */
    String getQueryText();


    /**
     * @return Map of String name to Source
     */
    Map<String, Source> getSources();


    /**
     * @return Sink
     */
    Sink getSink();


    void initializeAsContinuous() throws Exception; //todo remove initializeAsContinuous
    void initializeAsSnapshot() throws Exception; //todo remove initializeAsSnapshot


}
