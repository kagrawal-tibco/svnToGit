package com.tibco.cep.query.exec.descriptors.impl;

import java.util.LinkedHashMap;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.stream.impl.expression.TupleExtractor;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 28, 2008
* Time: 6:50:17 PM
*/
public class TupleExtractorDescriptor {

    private final TupleExtractor tupleExtractor;
    private final TypeInfo typeInfo;
    private final LinkedHashMap<String, String> usedColumnNamesToType;


    public TupleExtractorDescriptor(
            TupleExtractor e,
            TypeInfo t
    ) {
        this.tupleExtractor = e;
        this.typeInfo = t;
        this.usedColumnNamesToType = new LinkedHashMap<String, String>();
    }


    public void addUsedColumnName(String columnName, String className) {
        this.usedColumnNamesToType.put(columnName, className);
    }


    public void addUsedColumnNames(TupleExtractorDescriptor otherFragment) {
        this.usedColumnNamesToType.putAll(otherFragment.usedColumnNamesToType);
    }


    public TupleExtractor getTupleExtractor() {
        return this.tupleExtractor;
    }


    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }


    public LinkedHashMap<String, String> getUsedColumnNames() {
        return this.usedColumnNamesToType;
    }


}
