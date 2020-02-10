package com.tibco.cep.query.exec.descriptors.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 28, 2008
* Time: 6:50:17 PM
*/
public class EvaluatorDescriptor {

    private final ExpressionEvaluator evaluator;
    private final TypeInfo typeInfo;
    private final LinkedHashMap<String, String> usedColumnNamesToType;


    public EvaluatorDescriptor(
            ExpressionEvaluator e,
            TypeInfo t) {
        this.evaluator = e;
        this.typeInfo = t;
        this.usedColumnNamesToType = new LinkedHashMap<String, String>();
    }


    public void addUsedColumnName(String columnName, String className) {
        this.usedColumnNamesToType.put(columnName, className);
    }


    public void addUsedColumnNames(EvaluatorDescriptor otherFragment) {
        this.usedColumnNamesToType.putAll(otherFragment == null ? new HashMap<String, String>() : otherFragment.usedColumnNamesToType);
    }


    public ExpressionEvaluator getEvaluator() {
        return this.evaluator;
    }


    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }


    public LinkedHashMap<String, String> getUsedColumnNames() {
        return this.usedColumnNamesToType;
    }


}
