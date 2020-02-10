package com.tibco.cep.query.stream.impl.rete.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.join.GenericExistsPetey;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 5:25:41 PM
 */

public class ExistsPeteyImpl extends GenericExistsPetey {
    protected final FixedKeyHashMap<String, Tuple> matchAliasAndTuples;

    protected final String singleOuterAlias;

    public ExistsPeteyImpl(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);

        this.singleOuterAlias = outerTupleAliasAndInfos.keySet().iterator().next();
        this.matchAliasAndTuples =
                new FixedKeyHashMap<String, Tuple>(this.singleOuterAlias, this.innerTupleAlias);
    }

    @Override
    public ExpressionEvaluator generateExpressionEvaluator() {
        ExpressionEvaluator existsChecker = new ExpressionEvaluator() {
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

                return outerExistsIdsAndTuples.containsKey(tuple.getId());
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

        return existsChecker;
    }

    @Override
    protected boolean match(GlobalContext globalContext, DefaultQueryContext queryContext,
                            Tuple outerTuple, Tuple innerTuple) {
        matchAliasAndTuples.put(singleOuterAlias, outerTuple);
        matchAliasAndTuples.put(innerTupleAlias, innerTuple);

        boolean b =
                simpleExpression.evaluateBoolean(globalContext, queryContext, matchAliasAndTuples);

        matchAliasAndTuples.clear();

        return b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Tuple> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        Collection<Tuple> matchingOuterTuples = super.addInner(globalContext, queryContext,
                innerTuple);

        if (matchingOuterTuples != null) {
            try {
                rete.modifyObjects(matchingOuterTuples, true);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(resourceId, e);
            }
        }

        return matchingOuterTuples;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Tuple> removeInner(GlobalContext globalContext,
                                         DefaultQueryContext queryContext,
                                         Tuple innerTuple) {
        Collection<Tuple> nonMatchingOuterTuples = super.removeInner(globalContext, queryContext,
                innerTuple);

        if (nonMatchingOuterTuples != null) {
            try {
                rete.modifyObjects(nonMatchingOuterTuples, true);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(resourceId, e);
            }
        }

        return nonMatchingOuterTuples;
    }
}
