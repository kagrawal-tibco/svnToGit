package com.tibco.cep.query.stream._join2_.example;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import org.mvel.MVEL;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 6:20:01 PM
*/
public class MultiSourceMVELFilter implements Evaluator<Object[], Boolean> {
    protected Serializable compiledExpression;

    protected FixedKeyHashMap<Object, Object> variableMap;

    protected String[] inputVariableNames;

    protected String filterString;

    public MultiSourceMVELFilter(String filterString, String... inputVariableNames) {
        this.filterString = filterString;
        this.inputVariableNames = inputVariableNames;
        this.compiledExpression = MVEL.compileExpression(filterString);
        this.variableMap = new FixedKeyHashMap<Object, Object>(inputVariableNames);
    }

    public Boolean evaluate(Object[] input) {
        for (int i = 0; i < input.length; i++) {
            variableMap.put(inputVariableNames[i], input[i]);
        }

        Boolean result = null;
        try {
            result = (Boolean) MVEL.executeExpression(compiledExpression, variableMap);
        }
        finally {
            variableMap.clear();
        }

        return result;
    }
}