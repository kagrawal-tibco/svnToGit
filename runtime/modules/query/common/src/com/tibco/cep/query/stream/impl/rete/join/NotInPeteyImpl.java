package com.tibco.cep.query.stream.impl.rete.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 1:57:54 PM
 */

public class NotInPeteyImpl extends InPeteyImpl {
    public NotInPeteyImpl(InPeteyInfo inPeteInfo) {
        super(inPeteInfo);
    }

    /*
     * todo count(x) where ... in ( .... ). Here the count changes when the
     * In/Not-In returns false. In such cases the Join-Tuple that is not true
     * anymore must be returned - i.e JoinStream must produce a Delete-Stream.
     */

    @Override
    public ExpressionEvaluator generateExpressionEvaluator() {
        ExpressionEvaluator notInChecker = new ExpressionEvaluator() {
            private Map<String, Class<? extends Tuple>> aliasAndTypes =
                    new HashMap<String, Class<? extends Tuple>>();

            {
                aliasAndTypes.put(singleOuterAlias, outerTupleAliasAndInfos.get(singleOuterAlias)
                        .getContainerClass());
            }

            public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
                Tuple tuple = aliasAndTuples.get(singleOuterAlias);

                OuterTupleAndExtractedValue holder = outerTupleIdAndHolders.get(tuple.getId());
                OuterAndInnerTupleIds ids = extractedValueAndTupleIds.get(holder
                        .getExtractedValue());

                return !ids.hasInnerTupleIds();
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

        return notInChecker;
    }
}
