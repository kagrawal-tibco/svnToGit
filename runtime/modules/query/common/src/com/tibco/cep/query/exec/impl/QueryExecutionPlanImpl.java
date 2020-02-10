package com.tibco.cep.query.exec.impl;

import java.util.Map;

import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.query.snapshot.Bridge;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 2:04:17 PM
*/
class QueryExecutionPlanImpl
        implements QueryExecutionPlan {

    private Bridge bridge;
    private boolean isContinuous;
    private String name;
    private String regionName;
    private Sink sink;
    private Map<String,Source> nameToSource;
    private String text;


    QueryExecutionPlanImpl(
            String regionName,
            String name,
            String text,
            Map<String,Source> nameToSource,
            Bridge bridge,
            Sink sink,
            boolean isContinuous)
            throws Exception {

        this.regionName = regionName;
        this.name = name;
        this.text = text;
        this.nameToSource = nameToSource;
        this.bridge = bridge;
        this.sink = sink;
        this.isContinuous = isContinuous;
    }


    /**
     * @return Bridge if there is one, else null.
     */
    public Bridge getBridge() {
        return this.bridge;
    }


    /**
     * @return The name of the agent/region/section in which this Query plan is being used.
     */
    public String getRegionName() {
        return this.regionName;
    }


    /**
     * @return String name.
     */
    public String getName() {
        return this.name;
    }


    /**
     * @return String text of the query.
     */
    public String getQueryText() {
        return this.text;
    }


    /**
     * @return Sink
     */
    public Sink getSink() {
        return this.sink;
    }


    /**
     * @return Set of Source
     */
    public Map<String,Source> getSources() {
        return this.nameToSource;
    }


    public void initializeAsContinuous()
            throws Exception {
    }


    public void initializeAsSnapshot()
            throws Exception {
    }


    public boolean isContinuous() {
        return this.isContinuous;
    }

}
