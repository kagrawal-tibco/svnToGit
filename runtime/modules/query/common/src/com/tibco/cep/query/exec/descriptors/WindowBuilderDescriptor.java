package com.tibco.cep.query.exec.descriptors;

import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.stream.partition.WindowInfo;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 7:00:10 PM
*/
public interface WindowBuilderDescriptor {
    
    EmitType getEmitType();

    boolean getIncludesAggregates();

    String getInputTupleInfoFieldName();

    String getOutputTupleInfoFieldName();

    Type getType();

    void setVariableName(String name);

    WindowInfo getWindowInfo();


    public static enum Type {
        SLIDING,
        TIME,
        TUMBLING,
    }
}
