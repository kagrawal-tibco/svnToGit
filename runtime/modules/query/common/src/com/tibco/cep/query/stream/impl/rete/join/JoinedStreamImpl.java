package com.tibco.cep.query.stream.impl.rete.join;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.core.rete.conflict.SimpleConflictResolver;
import com.tibco.cep.kernel.helper.TimerTaskOnce;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ActionImpl;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.impl.DefaultObjectManager;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.join.AbstractPetey;
import com.tibco.cep.query.stream.join.JoinedStream;
import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.NestedSource;
import com.tibco.cep.query.stream.join.ReteAdapter;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;

/*
 * Author: Ashwin Jayaprakash Date: Oct 2, 2007 Time: 2:27:59 PM
 */

public class JoinedStreamImpl extends JoinedStream implements ReteAdapter, RuleOutputHandler {
    private AppendOnlyQueue<JoinedTuple> sessionAdditions;

    private CustomHashSet<JoinedTuple> sessionDeletions;

    private Context currContext;

    private JoinedTupleInfo joinedOutputTupleInfo;

    private IdGenerator idGenerator;

    protected String regionName;

    protected String queryName;

    protected ReteWM rete;

    public JoinedStreamImpl(String regionName, String queryName, ResourceId id,
                            Map<String, Stream> sources,
                            Collection<NestedSource> nestedSources, Expression joinExpression,
                            JoinedTupleInfo outputInfo) throws Exception {
        super(id, sources, nestedSources, joinExpression, outputInfo);

        this.regionName = regionName;
        this.queryName = queryName;

        // Don't send duplicates that might result from Or conditions.
        this.sessionDeletions = new CustomHashSet<JoinedTuple>();

        if (this.nestedSources != null) {
            for (NestedSource nestedSource : this.nestedSources) {
                AbstractPetey petey = nestedSource.getPetey();
                petey.init(this);
            }
        }

        this.joinedOutputTupleInfo = outputInfo;

        this.rete = initRete(id, regionName, queryName, joinExpression, outputInfo, this);
    }

    //TODO - LogManager is needed for RETEWM
    private static ReteWM initRete(ResourceId id, String regionName, String queryName,
                                   Expression joinExpression, JoinedTupleInfo outputInfo,
                                   RuleOutputHandler ruleOutputHandler) throws Exception {
        String name = queryName + ":" + id + ":Rule";

        DefaultObjectManager objectManager = new DefaultObjectManager(name);

        Logger logger = Registry.getInstance().getComponent(Logger.class);
        com.tibco.cep.kernel.service.logging.Logger logger2 =
                (com.tibco.cep.kernel.service.logging.Logger) logger;
        DefaultExceptionHandler handler = new DefaultExceptionHandler(logger2);

        ConflictResolver conflictResolver = new SimpleConflictResolver();

        BaseTimeManager timeManager = new DummyTimeManager();

        LogManager logManager = LogManagerFactory.getLogManager();
        if (logManager == null) {
            logManager = new LogManagerImpl();
        }

        ReteWM localRete =
                new ReteWM(name, logManager, handler, objectManager, timeManager, conflictResolver);

        objectManager.init(localRete);

        // ----------

        localRete.start(true);

        Set<Rule> rules = new HashSet<Rule>();

        MyJoinRule joinRule =
                new MyJoinRule(name, regionName, queryName, id, joinExpression, outputInfo);
        joinRule.initAction(ruleOutputHandler);

        localRete.getRuleLoader().loadRule(joinRule);
        rules.add(joinRule);

        localRete.init(null, null, rules);
        localRete.initEntitySharingLevels();

        return localRete;
    }

    @Override
    protected void beforeHandling(Context context) {
        if (sessionAdditions == null) {
            DefaultQueryContext qc = context.getQueryContext();

            idGenerator = qc.getIdGenerator();

            sessionAdditions =
                    new AppendOnlyQueue<JoinedTuple>(qc.getArrayPool());
        }

        sessionAdditions.clear();
        sessionDeletions.clear();

        this.currContext = context;
    }

    @Override
    protected void handleSource(Context context) throws Exception {
        final LocalContext lc = context.getLocalContext();

        Collection<? extends Tuple> deadList = lc.getDeadTuples();
        if (deadList != null) {
            retractObjects(deadList, false);
        }

        Collection<? extends Tuple> newList = lc.getNewTuples();
        if (newList != null) {
            assertObjects(newList, true);
        }
    }

    @Override
    protected void afterHandling(Context context) {
        localContext.clear();

        if (sessionDeletions.isEmpty() == false) {
            localContext.setDeadTuples(sessionDeletions);
        }

        if (sessionAdditions.isEmpty() == false) {
            localContext.setNewTuples(sessionAdditions);
        }
    }

    // ------------

    public void handleRuleOutput(Object[] joinColumns) {
        try {
            Number id = idGenerator.generateNewId();
            JoinedTuple containerTuple = joinedOutputTupleInfo.createTuple(id);
            containerTuple.setColumns(joinColumns);

            if (tupleTracker != null) {
                tupleTracker.recordJoin(containerTuple);
            }

            sessionAdditions.add(containerTuple);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(getResourceId(), e);
        }
    }

    // ------------

    public void assertObject(Object object, boolean executeRules) throws Exception {
        rete.assertObject(object, executeRules);
    }

    public void assertObjects(Collection<?> collection, boolean executeRules)
            throws Exception {
        for (Object object2 : collection) {
            rete.assertObject(object2, executeRules);
        }
    }

    public void modifyObject(Object object, boolean executeRules) throws Exception {
        Tuple tuple = (Tuple) object;
        disconnectAndCollectDeletes(tuple);

        rete.modifyObject(object, executeRules, true);
    }

    public void modifyObjects(Collection<?> collection, boolean executeRules)
            throws Exception {
        for (Object element : collection) {
            Tuple tuple = (Tuple) element;
            disconnectAndCollectDeletes(tuple);

            rete.modifyObject(element, executeRules, true);
        }
    }

    public void retractObject(Object object, boolean executeRules) throws Exception {
        Tuple tuple = (Tuple) object;
        disconnectAndCollectDeletes(tuple);

        rete.retractObject(object, executeRules);
    }

    public void retractObjects(Collection<?> collection, boolean executeRules)
            throws Exception {
        for (Object element : collection) {
            Tuple tuple = (Tuple) element;
            disconnectAndCollectDeletes(tuple);

            rete.retractObject(element, executeRules);
        }
    }

    private void disconnectAndCollectDeletes(Tuple constituentTuple) {
        if (constituentTuple instanceof Trigger.AbstractTrigger == false) {
            Collection<? extends JoinedTuple> oldJoins = tupleTracker.disconnectJoin(currContext,
                    constituentTuple);

            if (oldJoins != null) {
                sessionDeletions.addAll(oldJoins);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        rete.stopAndShutdown(null);
        rete = null;

        super.stop();

        if (sessionAdditions != null) {
            sessionAdditions.clear();
            sessionAdditions = null;
        }

        sessionDeletions.clear();
        sessionDeletions = null;

        idGenerator = null;

        currContext = null;

        joinedOutputTupleInfo = null;
    }

    // ------------

    protected static class MyJoinRule extends RuleImpl {
        public MyJoinRule(String ruleName, String regionName, String queryName, ResourceId joinId,
                          Expression joinCondition, JoinedTupleInfo outputInfo) {
            super(ruleName, "/" + ruleName);

            ExpressionConvertor convertor = new ExpressionConvertor(regionName, queryName);
            this.m_conditions = convertor.convert(joinCondition, this);

            HashMap<String, Identifier> allIdentifiers = new HashMap<String, Identifier>();
            for (Condition condition : this.m_conditions) {
                Identifier[] identifiers = condition.getIdentifiers();

                for (Identifier identifier : identifiers) {
                    allIdentifiers.put(identifier.getName(), identifier);
                }
            }

            // Arrange the Identifiers based in the OutputInfo alias order.
            String[] aliases = outputInfo.getColumnAliases();
            Class[] types = outputInfo.getColumnTypes();

            this.m_identifiers = new Identifier[aliases.length];
            for (int i = 0; i < aliases.length; i++) {
                this.m_identifiers[i] = allIdentifiers.remove(aliases[i]);

                if (this.m_identifiers[i] == null) {
                    /*
                     * No condition was specified for this alias. So, create
                     * one.
                     */
                    this.m_identifiers[i] = new IdentifierImpl(types[i], aliases[i]);
                }
            }

            if (allIdentifiers.isEmpty() == false) {
                throw new CustomRuntimeException(joinId,
                        "There are some Identifiers being used in the Expressions: ["
                                + allIdentifiers + "] that are not part of the Join output: "
                                + Arrays.asList(aliases));
            }
        }

        protected void initAction(RuleOutputHandler outputHandler) {
            MyAction action = new MyAction(this, outputHandler);

            m_actions = new Action[]{action};
        }
    }

    protected static class MyAction extends ActionImpl {
        protected RuleOutputHandler outputHandler;

        public MyAction(Rule rule, RuleOutputHandler outputHandler) {
            super(rule);

            this.outputHandler = outputHandler;
        }


        @Override
        public void execute(Object[] objects) {
            outputHandler.handleRuleOutput(objects);
        }
    }

    // ------------

    //Do nothing class.

    protected static class DummyTimeManager extends BaseTimeManager {
        public void start() {
        }

        public void stop() {
        }

        public void shutdown() {
        }

        public void reset() {
        }

        public Event scheduleOnceOnlyEvent(Event event, long delay) {
            return null;
        }

        public BaseHandle scheduleOnceOnlyEvent2(Event event, long delay) {
            return null;
        }

        public BaseHandle loadScheduleOnceOnlyEvent(Event event, long delay) {
            return null;
        }

        public void scheduleEventExpiry(Handle handle, long expireTTL) {
        }

        public void registerEvent(Class eventClass) {
        }

        public void unregisterEvent(Class eventClass) {
        }

        public void scheduleOnceOnly(TimerTaskOnce timerTask, long time) {
        }

        public void scheduleRepeating(TimerTaskRepeat timerTask, long time, long period) {
        }
    }
}
