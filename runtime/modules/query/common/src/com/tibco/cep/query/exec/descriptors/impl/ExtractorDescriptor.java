package com.tibco.cep.query.exec.descriptors.impl;

import java.util.LinkedHashMap;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 28, 2008
* Time: 6:50:17 PM
*/
public class ExtractorDescriptor {

    private final TupleValueExtractor extractor;
    private final TypeInfo typeInfo;
    private final LinkedHashMap<String, String> usedColumnNamesToType;


    public ExtractorDescriptor(
            EvaluatorDescriptor ed) {
        this(new EvaluatorToExtractorAdapter(ed.getEvaluator()), ed.getTypeInfo());
    }


    public ExtractorDescriptor(
            TupleValueExtractor e,
            TypeInfo t) {
        this.extractor = e;
        this.typeInfo = t;
        this.usedColumnNamesToType = new LinkedHashMap<String, String>();
    }


    public void addUsedColumnName(String columnName, String className) {
        this.usedColumnNamesToType.put(columnName, className);
    }


    public void addUsedColumnNames(ExtractorDescriptor otherFragment) {
        this.usedColumnNamesToType.putAll(otherFragment.usedColumnNamesToType);
    }


    public TupleValueExtractor getExtractor() {
        return this.extractor;
    }


    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }


    public LinkedHashMap<String, String> getUsedColumnNames() {
        return this.usedColumnNamesToType;
    }


}