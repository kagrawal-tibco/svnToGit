package com.tibco.cep.query.stream._join2_.example;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import org.mvel.MVEL;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 4, 2009 Time: 5:58:04 PM
*/
public class MultiSourceMVELExtractor implements Evaluator<Object[], Comparable> {
    protected Serializable compiledExpression;

    protected FixedKeyHashMap<Object, Object> variableMap;

    protected String[] inputVariableNames;

    protected String filterString;

    public MultiSourceMVELExtractor(String filterString, String... inputVariableNames) {
        this.filterString = filterString;
        this.inputVariableNames = inputVariableNames;
        this.compiledExpression = MVEL.compileExpression(filterString);
        this.variableMap = new FixedKeyHashMap<Object, Object>(inputVariableNames);
    }

    public Comparable evaluate(Object[] input) {
        for (int i = 0; i < input.length; i++) {
            variableMap.put(inputVariableNames[i], input[i]);
        }

        Comparable result = null;
        try {
            result = (Comparable) MVEL.executeExpression(compiledExpression, variableMap);
        }
        finally {
            variableMap.clear();
        }

        return result;
    }
}
