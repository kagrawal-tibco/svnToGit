package com.tibco.cep.query.stream.impl.rete.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.join.GenericInPetey;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 1:57:54 PM
 */

public class InPeteyImpl extends GenericInPetey {
    protected final String singleOuterAlias;

    public InPeteyImpl(InPeteyInfo inPeteInfo) {
        super(inPeteInfo);

        this.singleOuterAlias = outerTupleAliasAndInfos.keySet().iterator().next();
    }

    @Override
    public ExpressionEvaluator generateExpressionEvaluator() {
        ExpressionEvaluator inChecker = new ExpressionEvaluator() {
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

                return ids.hasInnerTupleIds();
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

        return inChecker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Tuple> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        Collection<Tuple> matchingOuterTuples = super.addInner(globalContext, queryContext,
                innerTuple);

        handleMatchChange(matchingOuterTuples, true);

        return matchingOuterTuples;
    }

    protected void handleMatchChange(Collection<Tuple> outerTuples, boolean matches) {
        if (outerTuples != null) {
            try {
                rete.modifyObjects(outerTuples, true);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(resourceId, e);
            }
        }
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

        handleMatchChange(nonMatchingOuterTuples, false);

        return nonMatchingOuterTuples;
    }
}
