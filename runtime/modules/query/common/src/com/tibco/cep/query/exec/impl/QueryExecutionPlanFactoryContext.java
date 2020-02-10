package com.tibco.cep.query.exec.impl;

import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.query.snapshot.Bridge;

class QueryExecutionPlanFactoryContext {

    
    public class BridgeInfo {
        Bridge bridge;
        Stream inputStream;
        Stream setupStream;
    }


    private final BridgeInfo bridgeInfo;
    private final LinkedHashMap<String, Stream> idToStream;
    private final boolean isContinuous;
    private final String name;
    private final LinkedHashMap<String, Source> nameToSource;

    private Sink sink;


    public QueryExecutionPlanFactoryContext(
            String name,
            boolean isContinuous
    ) {
        this.isContinuous = isContinuous;
        this.name = name;

        this.bridgeInfo = new BridgeInfo();
        this.idToStream = new LinkedHashMap<String, Stream>();
        this.nameToSource = new LinkedHashMap<String, Source>();
    }


    public BridgeInfo getBridgeInfo() {
        return bridgeInfo;
    }


    public LinkedHashMap<String, Stream> getIdToStream() {
        return idToStream;
    }


    public String getName() {
        return name;
    }


    public LinkedHashMap<String, Source> getNameToSource() {
        return nameToSource;
    }


    public Sink getSink() {
        return sink;
    }


    public boolean isContinuous() {
        return isContinuous;
    }


    public void setSink(Sink sink) {
        this.sink = sink;
    }


}
