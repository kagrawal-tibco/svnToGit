package com.tibco.cep.query.stream.impl.rete.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 5:25:41 PM
 */

public class NotExistsPetey2Impl extends ExistsPetey2Impl {
    public NotExistsPetey2Impl(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);
    }

    @Override
    public Boolean addOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                            String alias,
                            Tuple outerTuple) {
        boolean b = super.addOuter(globalContext, queryContext, alias, outerTuple);
        if (b == false) {
            // ???
            // AbstractTrigger trigger = createTrigger();
            // trigger.setId(group.getOuterTuplesHash());
            // trigger.setGroupMembers(group.getOuterTupleIds());
            //
            // newTriggers.add(trigger);
            //
            // triggers.put(trigger.getId(), trigger);
        }

        return false;
    }

    @Override
    public Collection<Group> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        return super.addInner(globalContext, queryContext, innerTuple);
    }

    @Override
    public Collection<Group> removeInner(GlobalContext globalContext,
                                         DefaultQueryContext queryContext,
                                         Tuple innerTuple) {
        return super.removeInner(globalContext, queryContext, innerTuple);
    }

    @Override
    public Boolean removeOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                               String alias, Tuple outerTuple) {
        return super.removeOuter(globalContext, queryContext, alias, outerTuple);
    }

    // ----------

    public class Exists2Evaluator implements ExpressionEvaluator {
        private final Tuple[] evalTuples = new Tuple[outerAliases.length];

        private Map<String, Class<? extends Tuple>> aliasAndTypes =
                new HashMap<String, Class<? extends Tuple>>();

        {
            for (String alias : outerAliases) {
                aliasAndTypes.put(alias, outerTupleAliasAndInfos.get(alias).getContainerClass());
            }

            aliasAndTypes.put(triggerAlias, reloadedTriggerClass);
        }

        public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            // This condition is evaluated when the Trigger arrives.
            for (int i = 0; i < outerAliases.length; i++) {
                evalTuples[i] = aliasAndTuples.get(outerAliases[i]);
            }

            //todo IMP: This hashcode is not sufficient if it clashes.
            Long hash = Group.findHash(evalTuples);

            Tuple trigger = aliasAndTuples.get(triggerAlias);
            if (trigger.getId().equals(hash) == false) {
                return false;
            }

            // Hash could be same, but ensure the groups are also the same.
            Trigger.AbstractTrigger abstractTrigger = (Trigger.AbstractTrigger) trigger;
            return abstractTrigger.areSameGroupMembers(evalTuples);
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
    }
}
