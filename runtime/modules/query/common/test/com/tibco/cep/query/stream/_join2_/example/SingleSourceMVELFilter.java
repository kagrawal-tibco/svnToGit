/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.query.stream._join2_.example;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.util.SingleValueLiteMap;
import org.mvel.MVEL;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 6:20:01 PM
*/
public class SingleSourceMVELFilter implements Evaluator<Object, Boolean> {
    protected Serializable compiledExpression;

    protected SingleValueLiteMap variableMap;

    protected String inputVariableName;

    protected String filterString;

    public SingleSourceMVELFilter(String filterString, String inputVariableName) {
        this.filterString = filterString;
        this.inputVariableName = inputVariableName;
        this.compiledExpression = MVEL.compileExpression(filterString);
        this.variableMap = new SingleValueLiteMap();
    }

    public Boolean evaluate(Object input) {
        variableMap.put(inputVariableName, input);

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
