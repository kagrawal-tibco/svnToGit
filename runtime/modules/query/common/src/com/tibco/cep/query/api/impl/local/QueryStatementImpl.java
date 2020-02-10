package com.tibco.cep.query.api.impl.local;


import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.*;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryFeatures;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.impl.QueryImpl;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotFeederForCQ;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.runtime.session.RuleSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 19, 2008
* Time: 9:04:16 PM
*/
public class QueryStatementImpl implements QueryStatement, QueryResultSetManager {
    protected volatile ReteQuery[] cachedReteQueries;

    protected QueryConnection connection;

    protected QueryStatementManager statementManager;

    protected QuerySession querySession;

    protected Logger logger;

    protected RegionManager regionManager;

    private ConcurrentHashMap<String, Container> queries;

    protected static AtomicLong queryIdGenerator = new AtomicLong();

    protected HashMap<String, Object> reteQueryParams;

    protected PlanGenerator generator;

    protected boolean generatorCreatedHere;


    public QueryStatementImpl(QueryConnection connection, QueryStatementManager statementManager,
                              QuerySession querySession, RegionManager regionManager,
                              String queryText) throws QueryException {
        this.connection = connection;
        this.statementManager = statementManager;
        this.regionManager = regionManager;
        this.queries = new ConcurrentHashMap<String, Container>();

       	// Made this static
       	//this.queryIdGenerator = new AtomicLong();

        this.querySession = querySession;
        this.logger = this.querySession.getRuleSession().getRuleServiceProvider().getLogger(this.getClass());

        Query query = new QueryImpl(queryText, querySession);
        this.generator = new PlanGeneratorImpl(query, this.regionManager.getRegionName());
        this.generatorCreatedHere = true;
        this.cachedReteQueries = new ReteQuery[]{};
    }


    public QueryStatementImpl(QueryConnection connection, QueryStatementManager statementManager,
                              QuerySession querySession, RegionManager regionManager,
                              PlanGenerator generator) {
        this.connection = connection;
        this.statementManager = statementManager;
        this.regionManager = regionManager;
        this.queries = new ConcurrentHashMap<String, Container>();

       	// Made this static
       	//this.queryIdGenerator = new AtomicLong();

        this.querySession = querySession;

        this.generator = generator;
        this.generatorCreatedHere = false;
        this.cachedReteQueries = new ReteQuery[]{};
    }

    public SharedObjectSource getFakeSharedObjectSource() {
        SharedObjectSourceRepository sosRepo = regionManager.getSOSRepository();

        return sosRepo.getDefaultSource();
    }

    public QueryConnection getConnection() {
        return connection;
    }

    /**
     * Gets the source text of the query.
     *
     * @return String source text of the query.
     */
    public String getText() {
        return this.generator.getQueryText();
    }


    public void unregisterAndStopQuery(ReteQuery q) throws Exception {
        if (statementManager == null) {
            return;
        }

        String queryName = q.getName();
        Container container = queries.get(queryName);

        QueryPolicy policy = container.policy;

        if (policy instanceof QueryPolicy.Continuous) {
            ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
            dispatcher.unregisterAndStopQuery(q);
        } else if (policy instanceof QueryPolicy.Snapshot) {
            SnapshotQueryManager sqm = regionManager.getSSQueryManager();
            sqm.stopFeedingAndUnregister(q);
        } else {
            SnapshotFeederForCQ feederForCQ = container.getOptionalFeederForCQ();
            feederForCQ.cancelFeedingAndDiscard();

            ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
            dispatcher.unregisterAndStopQuery(q);
        }

        queries.remove(queryName);

        q.stop();

        recacheQueries();
    }

    protected void onNewQuery(ReteQuery reteQuery, Container container) {
        queries.put(reteQuery.getName(), container);

        recacheQueries();
    }

    private void recacheQueries() {
        LinkedList<ReteQuery> list = new LinkedList<ReteQuery>();
        for (Container c : queries.values()) {
            list.add(c.getReteQuery());
        }

        cachedReteQueries = list.toArray(new ReteQuery[list.size()]);
    }

    public ReteQuery[] getCachedReteQueries() {
        return cachedReteQueries;
    }

    /**
     * Closes this statement and all open resultsets.
     */
    public void close() {
        if (null != this.queries) {
            ArrayList<Container> list = new ArrayList<Container>(queries.values());
            for (Container container : list) {
                try {
                    container.discard();
                } catch (Exception e) {
                    this.logger.log(Level.WARN, e, this.regionManager.getRegionName(), "ResultSet close error.");
                }
            }
            queries.clear();
            queries = null;
        }

        if (reteQueryParams != null) {
            reteQueryParams.clear();
            reteQueryParams = null;
        }

        querySession = null;
        connection = null;
        regionManager = null;

        if (this.generatorCreatedHere && (null != this.generator)) {
            generator.discard();
            generator = null;
        }

        if (null != this.statementManager) {
            statementManager.unregisterStatement(this);
            statementManager = null;
        }
    }

    protected GeneratedArtifacts createAndRegisterQueryInstance(String name, QueryPolicy policy, QueryFeatures queryFeatures)
            throws QueryException {
        RuleSession ruleSession = querySession.getRuleSession();
        SharedObjectSourceRepository repository = regionManager.getSOSRepository();
        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();
        ExecutorService executorService = ssQueryManager.getExecutorService();

        GeneratedArtifacts artifacts =
                generator.generateInstance(name, policy, ruleSession, repository, executorService);

        QueryPolicy appliedPolicy = artifacts.getAppliedPolicy();

        ReteQuery internalQuery = artifacts.getReteQuery();
        internalQuery.setSelfJoin(queryFeatures.isSelfJoin());

        try {
            internalQuery.init(repository, reteQueryParams);
            internalQuery.start();

            reteQueryParams = null;
        } catch (Exception e) {
            throw new QueryException(e);
        }

        try {
            if (appliedPolicy instanceof QueryPolicy.SnapshotThenContinuous) {
                final SnapshotFeederForCQ feederForCQ = artifacts.getOptionalFeederForCQ();
                if (null != feederForCQ) {
                    feederForCQ.stepGo();
                }
                final ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
                dispatcher.registerQuery(internalQuery);

            } else if (appliedPolicy instanceof QueryPolicy.Continuous) {
                final ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
                dispatcher.registerQuery(internalQuery);

            } else if (appliedPolicy instanceof QueryPolicy.Snapshot) {
                final SnapshotQueryManager sqm = regionManager.getSSQueryManager();
                final Bridge optionalBridge = artifacts.getOptionalBridge();

                //********************************Hope so*****************************
                sqm.registerAndStartFeeding(internalQuery, optionalBridge);
            }
        } catch (Exception e) {
            try {
                if (appliedPolicy instanceof QueryPolicy.SnapshotThenContinuous) {
                    final SnapshotFeederForCQ feederForCQ = artifacts.getOptionalFeederForCQ();
                    if (null != feederForCQ) {
                        feederForCQ.cancelFeedingAndDiscard();
                    } else {
                        final ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
                        dispatcher.unregisterAndStopQuery(internalQuery);
                    }

                } else if (appliedPolicy instanceof QueryPolicy.Continuous) {
                    final ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
                    dispatcher.unregisterAndStopQuery(internalQuery);

                } else if (appliedPolicy instanceof QueryPolicy.Snapshot) {
                    final SnapshotQueryManager sqm = regionManager.getSSQueryManager();
                    sqm.stopFeedingAndUnregister(internalQuery);
                }
            } catch (Exception e1) {
                this.logger.log(Level.WARN, e, "%s - Error occured during clean up.",
                        this.regionManager.getRegionName());
            }

            throw new QueryException(e);
        }

        return artifacts;
    }

    protected SinkAdapter createAndRegisterResultSet(ReteQuery query,
                                                     QueryPolicy policy) {
        Sink sink = query.getSink();

        if (sink instanceof StreamedSink) {
            if ((policy instanceof QueryPolicy.SnapshotThenContinuous)
                    || (policy instanceof QueryPolicy.Continuous)) {
                return new StreamedSinkAdapter(query, this, this);
            } else if (policy instanceof QueryPolicy.Snapshot) {
                return new StreamedSinkCloak(query, this, this);
            }
        }

        return new StaticSinkAdapter(query, this, this);
    }

    protected String generateQueryName() {
        return this.regionManager.getRegionName() + ":" + System.identityHashCode(this)
                + ":" + queryIdGenerator.incrementAndGet();
    }


    public QueryResultSet executeQuery(QueryPolicy policy) throws QueryException {
        return null;
    }

    /**
     * Executes the query and returns a {@link com.tibco.cep.query.api.QueryResultSet}.
     *
     * @param policy QueryPolicy describing the policies to use when executing the query.
     * @return QueryResultSet from where the query results can be read.
     * @throws com.tibco.cep.query.api.QueryException
     *          is anything goes wrong.
     */
    public QueryResultSet executeQuery(QueryPolicy policy, QueryFeatures queryFeatures) throws QueryException {
        GeneratedArtifacts artifacts =
                createAndRegisterQueryInstance(/*x = */generateQueryName(), policy, queryFeatures);
        ReteQuery reteQuery = artifacts.getReteQuery();

        SinkAdapter resultSet =
                createAndRegisterResultSet(reteQuery, policy);

        Container container = new Container(reteQuery, resultSet, artifacts.getAppliedPolicy(),
                artifacts.getOptionalFeederForCQ());
        String registeredName = resultSet.getReteQuery().getName();

        onNewQuery(reteQuery, container);

        return resultSet;
    }

    public QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener) {
        return null;
    }

    /**
     * Executes the query and binds a {@link com.tibco.cep.query.api.QueryListener} to its results.
     *
     * @param policy   QueryPolicy describing the policies to use when executing the query.
     * @param listener QueryListener which can read the query results.
     * @return QueryListenerHandle
     * @throws com.tibco.cep.query.api.QueryException
     *          is anything goes wrong.
     */
    public QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener, QueryFeatures queryFeatures)
            throws QueryException {
        GeneratedArtifacts artifacts =
                createAndRegisterQueryInstance(generateQueryName(), policy, queryFeatures);

        ReteQuery reteQuery = artifacts.getReteQuery();

        SinkAdapter resultSet =
                createAndRegisterResultSet(reteQuery, artifacts.getAppliedPolicy());

        Sink sink = reteQuery.getSink();
        TupleInfo outputInfo = sink.getOutputInfo();
        String id = generateListenerHandlerId(reteQuery);

        QueryListenerHandler listenerHandler =
                new QueryListenerHandler(resultSet, outputInfo, listener, id);

        Container container = new Container(reteQuery, resultSet, artifacts.getAppliedPolicy(),
                artifacts.getOptionalFeederForCQ(), listenerHandler);
        onNewQuery(resultSet.getReteQuery(), container);

        listener.onQueryStart(container);

        return container;
    }

    protected String generateListenerHandlerId(ReteQuery reteQuery) {
        return reteQuery.getName() + ":Listener:" + System.currentTimeMillis();
    }

    /**
     * Sets the value of the binding variable of given name.
     *
     * @param parameterName String name of the binding variable.
     * @param o             Object value to set the binding variable to.
     */
    public void setObject(String parameterName, Object o) {
        if (reteQueryParams == null) {
            reteQueryParams = new HashMap<String, Object>();
        }

        reteQueryParams.put("BV$" + parameterName, o);
    }

    public void clearParameters() {
        if (reteQueryParams != null) {
            reteQueryParams.clear();
        }

        reteQueryParams = null;
    }

    //-----------

    protected static class Container implements QueryListenerHandle {
        protected ReteQuery reteQuery;

        protected SinkAdapter sinkAdapter;

        protected SnapshotFeederForCQ optionalFeederForCQ;

        protected QueryListenerHandler optionalHandler;

        protected QueryPolicy policy;

        public Container(ReteQuery reteQuery, SinkAdapter sinkAdapter, QueryPolicy policy) {
            this(reteQuery, sinkAdapter, policy, null, null);
        }

        public Container(ReteQuery reteQuery, SinkAdapter sinkAdapter, QueryPolicy policy,
                         SnapshotFeederForCQ optionalFeederForCQ) {
            this(reteQuery, sinkAdapter, policy, optionalFeederForCQ, null);
        }

        public Container(ReteQuery reteQuery, SinkAdapter sinkAdapter, QueryPolicy policy,
                         QueryListenerHandler optionalHandler) {
            this(reteQuery, sinkAdapter, policy, null, optionalHandler);
        }

        public Container(ReteQuery reteQuery, SinkAdapter sinkAdapter, QueryPolicy policy,
                         SnapshotFeederForCQ optionalFeederForCQ,
                         QueryListenerHandler optionalHandler) {
            this.reteQuery = reteQuery;
            this.sinkAdapter = sinkAdapter;
            this.optionalFeederForCQ = optionalFeederForCQ;
            this.optionalHandler = optionalHandler;
            this.policy = policy;
        }

        public ReteQuery getReteQuery() {
            return reteQuery;
        }

        public SnapshotFeederForCQ getOptionalFeederForCQ() {
            return optionalFeederForCQ;
        }

        public QueryPolicy getPolicy() {
            return policy;
        }

        public QueryListenerHandler getOptionalHandler() {
            return optionalHandler;
        }

        public SinkAdapter getSinkAdapter() {
            return sinkAdapter;
        }

        public void requestStop() {
            try {
                this.discard();
            } catch (Exception e) {
                Registry registry = Registry.getInstance();

                com.tibco.cep.query.stream.monitor.Logger logger =
                        registry.getComponent(com.tibco.cep.query.stream.monitor.Logger.class);

                logger.log(com.tibco.cep.query.stream.monitor.Logger.LogLevel.WARNING, e);
            }
        }

        public void discard() throws Exception {
            if (null != sinkAdapter) {
                //Invokes unregisterAndStopQuery(String) internally.
                sinkAdapter.close();

                sinkAdapter = null;
                optionalHandler = null;
                policy = null;
            }
        }
    }
}
