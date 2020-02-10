package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import org.mvel.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 12:56:55 PM
 */

public class SimpleExpression extends AbstractExpression implements EvaluatableExpression {
    protected final Serializable mvelExpression;

    protected final Map<String, Object> mvelContext;

    public SimpleExpression(Map<String, Class<? extends Tuple>> aliasAndTypes, String expression) {
        super(aliasAndTypes);

        this.mvelExpression = MVEL.compileExpression(expression);
        this.mvelContext = new HashMap<String, Object>();
    }

    public Boolean evaluate(GlobalContext globalContext, QueryContext queryContext,
                            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        mvelContext.clear();

        for (String alias : aliasAndTypes.keySet()) {
            mvelContext.put(alias, aliasAndTuples.get(alias));
        }

        return (Boolean) MVEL.executeExpression(mvelExpression, mvelContext);
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluate(globalContext, queryContext, aliasAndTuples);
    }

    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return 0;
    }

    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return 0;
    }

    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return 0;
    }

    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return 0;
    }
}
