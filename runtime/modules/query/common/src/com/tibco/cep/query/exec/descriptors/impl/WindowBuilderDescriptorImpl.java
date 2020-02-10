package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.WindowBuilderDescriptor;
import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.stream.partition.WindowInfo;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 7:01:31 PM
*/
public class WindowBuilderDescriptorImpl
        implements WindowBuilderDescriptor {


    protected final EmitType emitType;
    protected final boolean includesAggregates;
    protected final String inputTupleInfoFieldName;
    protected final String outputTupleInfoFieldName;
    protected final Type type;
    protected final WindowInfo windowInfo;
    
    protected String variableName;


    public WindowBuilderDescriptorImpl(
            Type type,
            String inputTupleInfoFieldName,
            String outputTupleInfoFieldName,
            EmitType emitType,
            boolean includesAggregates,
            WindowInfo windowInfo) {

        this.type = type;
        this.inputTupleInfoFieldName = inputTupleInfoFieldName;
        this.outputTupleInfoFieldName = outputTupleInfoFieldName;
        this.emitType = emitType;
        this.includesAggregates = includesAggregates;
        this.variableName = null;
        this.windowInfo = windowInfo;
    }


    public EmitType getEmitType() {
        return this.emitType;
    }


    public boolean getIncludesAggregates() {
        return this.includesAggregates;
    }


    public String getInputTupleInfoFieldName() {
        return this.inputTupleInfoFieldName;
    }


    public String getOutputTupleInfoFieldName() {
        return this.outputTupleInfoFieldName;
    }


    public Type getType() {
        return this.type;
    }


    public String getVariableName() {
        return this.variableName;
    }


    public WindowInfo getWindowInfo() {
        return this.windowInfo;
    }


    public void setVariableName(String name) {
        this.variableName = name;
    }

}
