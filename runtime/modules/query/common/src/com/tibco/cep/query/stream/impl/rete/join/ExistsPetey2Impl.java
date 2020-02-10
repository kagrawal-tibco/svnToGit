package com.tibco.cep.query.stream.impl.rete.join;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.rete.join.Trigger.AbstractTrigger;
import com.tibco.cep.query.stream.join.GenericExistsPetey2;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 5:25:41 PM
 */

public class ExistsPetey2Impl extends GenericExistsPetey2 {
    protected final FixedKeyHashMap<String, Tuple> matchAliasAndTuples;

    /**
     * Each Exists node needs a custom Loader because there could be multiple Exists in a single
     * Query and the Triggers would conflict. To avoid this, the same Trigger class gets loaded by
     * different ClassLoaders.
     */
    protected final Trigger.CustomClassLoader customClassLoader;

    protected final String triggerAlias;

    protected final Class<? extends Trigger.AbstractTrigger> reloadedTriggerClass;

    protected final Constructor<? extends AbstractTrigger> reloadedTriggerClassCtor;

    protected final HashMap<Number, Trigger.AbstractTrigger> triggers;

    private Collection<Trigger.AbstractTrigger> newTriggers;

    private Collection<Trigger.AbstractTrigger> deadTriggers;

    /**
     * @param peteyInfo
     * @throws CustomRuntimeException
     */
    @SuppressWarnings("unchecked")
    public ExistsPetey2Impl(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);

        this.customClassLoader = new Trigger.CustomClassLoader(TriggerImpl.class.getName());
        this.triggerAlias = TriggerImpl.class.getName() + "_" + System.nanoTime() + "_"
                + new Random().nextLong();

        CustomHashSet<String> refAliases = new CustomHashSet<String>();
        refAliases.addAll(outerTupleAliasAndInfos.keySet());
        refAliases.add(innerTupleAlias);
        refAliases.add(triggerAlias);
        this.matchAliasAndTuples = new FixedKeyHashMap<String, Tuple>(refAliases);
        refAliases.clear();


        try {
            this.reloadedTriggerClass = (Class<? extends Trigger.AbstractTrigger>) customClassLoader
                    .loadClass(TriggerImpl.class.getName());

            this.reloadedTriggerClassCtor = this.reloadedTriggerClass.getConstructor(Long.class);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }

        this.triggers = new HashMap<Number, AbstractTrigger>();
    }

    @Override
    public ExpressionEvaluator generateExpressionEvaluator() {
        return new Exists2Evaluator();
    }

    @Override
    protected boolean match(GlobalContext globalContext, DefaultQueryContext queryContext,
                            String[] outerTupleAliases, Tuple[] outerTuples, Tuple innerTuple) {
        matchAliasAndTuples.clear();

        for (int i = 0; i < outerTupleAliases.length; i++) {
            matchAliasAndTuples.put(outerTupleAliases[i], outerTuples[i]);
        }

        matchAliasAndTuples.put(innerTupleAlias, innerTuple);

        return simpleExpression.evaluateBoolean(globalContext, queryContext, matchAliasAndTuples);
    }

    private Trigger.AbstractTrigger createTrigger(Long id) {
        Trigger.AbstractTrigger trigger = null;

        final Thread currThread = Thread.currentThread();
        final ClassLoader ctxLoader = currThread.getContextClassLoader();
        currThread.setContextClassLoader(customClassLoader);

        try {
            // todo Change to new ClassBlah().
            trigger = reloadedTriggerClassCtor.newInstance(id);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
        finally {
            // Restore.
            currThread.setContextClassLoader(ctxLoader);
        }

        return trigger;
    }

    @Override
    public Map<String, Class<? extends Tuple>> populateAndGetAliasAndTypes() {
        Map<String, Class<? extends Tuple>> map = super.populateAndGetAliasAndTypes();

        map.put(triggerAlias, reloadedTriggerClass);

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Group> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        Collection<Group> matchingGroups = super.addInner(globalContext, queryContext, innerTuple);

        if (newTriggers != null) {
            newTriggers.clear();
        }
        else {
            newTriggers = new AppendOnlyQueue<AbstractTrigger>(queryContext.getArrayPool());
        }

        if (matchingGroups != null) {
            for (Group group : matchingGroups) {
                Long id = group.getOuterTuplesHash();
                Trigger.AbstractTrigger trigger = createTrigger(id);
                trigger.setGroupMembers(group.getOuterTupleIds());

                newTriggers.add(trigger);

                triggers.put(trigger.getId(), trigger);
            }

            try {
                rete.assertObjects(newTriggers, true);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(resourceId, e);
            }
        }

        return matchingGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Group> removeInner(GlobalContext globalContext,
                                         DefaultQueryContext queryContext,
                                         Tuple innerTuple) {
        Collection<Group> nonMatchingOuterTuples = super.removeInner(globalContext, queryContext,
                innerTuple);

        if (deadTriggers != null) {
            deadTriggers.clear();
        }
        else {
            deadTriggers = new AppendOnlyQueue<AbstractTrigger>(queryContext.getArrayPool());
        }

        if (nonMatchingOuterTuples != null) {
            for (Group group : nonMatchingOuterTuples) {
                Trigger.AbstractTrigger trigger = triggers.remove(group.getOuterTuplesHash());
                deadTriggers.add(trigger);
            }

            try {
                rete.retractObjects(deadTriggers, true);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(resourceId, e);
            }
        }

        return nonMatchingOuterTuples;
    }

    // ----------

    // ??? Action still contains the Trigger tuple. Remove it.

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

    public static class TriggerImpl extends Trigger.AbstractTrigger {
        protected TriggerImpl(Long id) {
            super(id);
        }
    }
}
