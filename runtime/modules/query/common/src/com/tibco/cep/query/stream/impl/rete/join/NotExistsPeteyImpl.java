package com.tibco.cep.query.stream.impl.rete.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 5:25:41 PM
 */

public class NotExistsPeteyImpl extends ExistsPeteyImpl {
    public NotExistsPeteyImpl(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);
    }

    @Override
    public ExpressionEvaluator generateExpressionEvaluator() {
        ExpressionEvaluator notExistsChecker = new ExpressionEvaluator() {
            private Map<String, Class<? extends Tuple>> aliasAndTypes =
                    new HashMap<String, Class<? extends Tuple>>();

            {
                aliasAndTypes.put(singleOuterAlias, outerTupleAliasAndInfos.get(singleOuterAlias)
                        .getContainerClass());
            }

            public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
                // This condition is evaluated when the Trigger arrives.
                Tuple tuple = aliasAndTuples.get(singleOuterAlias);

                if (outerNotExistsIdsAndTuples.containsKey(tuple.getId())) {
                    return true;
                }

                return false;
            }

            public Boolean evaluate(GlobalContext globalContext, QueryContext queryContext,
                                    FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
                return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
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

            public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
                return aliasAndTypes;
            }
        };

        return notExistsChecker;
    }
}
