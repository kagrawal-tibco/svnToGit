package com.tibco.cep.query.stream.impl.rete.join;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ConditionImpl;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.core.ContextRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:37:34 PM
 */

public class CustomCondition extends ConditionImpl {
    protected FixedKeyHashMap<String, Tuple> aliasAndTuples;

    private String[] identifierNames;

    protected ExpressionEvaluator evaluator;

    protected GlobalContext globalContext;

    protected DefaultQueryContext queryContext;

    protected String reqionName;

    protected String queryName;

    protected CustomCondition(Rule rule, Identifier[] identifiers, String reqionName,
                              String queryName) {
        this(rule, identifiers, reqionName, queryName, null);
    }

    public CustomCondition(Rule rule, Identifier[] identifiers,
                           String reqionName, String queryName,
                           ExpressionEvaluator evaluator) {
        super(rule, identifiers);

        this.identifierNames = Helper.extractIdentifierNames(identifiers);
        this.aliasAndTuples = new FixedKeyHashMap<String, Tuple>(identifierNames);

        this.reqionName = reqionName;
        this.queryName = queryName;
        this.evaluator = evaluator;
    }

    @Override
    public boolean eval(Object[] objects) {
        for (int j = 0; j < identifierNames.length; j++) {
            aliasAndTuples.put(identifierNames[j], (Tuple) objects[j]);
        }

        if (globalContext != null) {
            return doEvaluate();
        }

        //---------

        ContextRepository repository = Registry.getInstance().getComponent(
                ContextRepository.class);
        globalContext = repository.getGlobalContext();
        queryContext = repository.getQueryContext(reqionName, queryName);

        if (globalContext == null || queryContext == null) {
            throw new RuntimeException(
                    "Query: " + queryName + " Condition initiliazation did not work.");
        }

        return doEvaluate();
    }

    protected boolean doEvaluate() {
        boolean b = evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);

        aliasAndTuples.clear();

        return b;
    }
}
