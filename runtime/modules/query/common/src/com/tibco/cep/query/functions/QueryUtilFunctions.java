package com.tibco.cep.query.functions;

import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CddFactory;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.impl.CddFactoryImpl;
import com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.query.stream.impl.rete.service.QueryAgent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentBuilder;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.tibco.be.model.functions.FunctionDomain.*;

/*
* Author: Ashwin Jayaprakash / Date: Jun 4, 2010 / Time: 3:25:30 PM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.Util",
        synopsis = "Query utility functions.")
public class QueryUtilFunctions {
    private static final ConcurrentHashMap<String, RuleSessionImpl> queryRuleSessionImpls =
            new ConcurrentHashMap<String, RuleSessionImpl>();

    private static final AtomicReference<Future> recentDynamicSessionOp = new AtomicReference<Future>();

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(QueryUtilFunctions.class);

    /**
     * @param name
     * @param queryRuleSessionImpl Should implement {@link QueryRuleSessionImpl}
     */
    public static void registerQueryRuleSession(String name, RuleSessionImpl queryRuleSessionImpl) {
        if (queryRuleSessionImpl instanceof QueryRuleSessionImpl) {
            queryRuleSessionImpls.putIfAbsent(name, queryRuleSessionImpl);

            return;
        }

        throw new IllegalArgumentException(
                "The parameter passed should implement " + QueryRuleSessionImpl.class.getName());
    }

    public static Collection<RuleSessionImpl> getQueryRuleSessionImpls() {
        return queryRuleSessionImpls.values();
    }

    public static RuleSessionImpl getQueryRuleSessionImpl(String sessionName) {
        return queryRuleSessionImpls.get(sessionName);
    }

    public static RuleSessionImpl getDynamicQueryRuleSessionImpl() {
        return queryRuleSessionImpls.get(getDynamicQuerySessionName());
    }
    
    public static Set<String> getQuerySessionNames() {
    	return queryRuleSessionImpls.keySet();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeFunctionInQuerySession",
            synopsis = "Invokes a rule function in another query session/agent whose name is given along with the parameters.",
            signature = "Object invokeFunctionInQuerySession(String querySessionName, String queryRuleFunctionUri, Object[] parameters)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "querySessionName", type = "String",
                            desc = "A valid query session/agent name that has been deployed in the same processing unit."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryRuleFunctionUri", type = "String",
                            desc = "parameters."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parameters", type = "Object[]",
                            desc = "Parameters to the rule function.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Result returned by the rule function.\n<p/>\nIf a concept, event or a tree of concept and contained concepts are returned, then it should be noted that these\ninstances from the query session do not affect the inference working memory. They are snapshot copies made by the\nquery session. They cannot be asserted into or deleted from the inference agent. These objects can be de-referenced\nafter the values have been read. By de-referencing them, the JVM Garbage Collector will clear these objects but\ntheir copies in the Distributed Cache will remain unaffected.\n<p/>\nConsistency may be enforced by acquiring appropriate locks."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes a rule function in another query session/agent whose name is given along with the parameters.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invokeFunctionInQuerySession(String querySessionName, String queryRuleFunctionUri,
                                                      Object[] parameters) {
        RuleSessionImpl queryRuleSessionImpl = queryRuleSessionImpls.get(querySessionName);

        if (queryRuleSessionImpl == null) {
            throw new IllegalArgumentException(
                    "The query session name [" + querySessionName + "] is invalid." +
                            " Valid names are " + queryRuleSessionImpls.keySet());
        }

        return callRF(queryRuleSessionImpl, queryRuleFunctionUri, parameters);
    }

    /**
     * @param querySession         RuleSessionImpl
     * @param queryRuleFunctionUri String
     * @param params               Object[]
     * @return Object
     */
    public static Object callRF(RuleSessionImpl querySession, String queryRuleFunctionUri, Object[] params) {
        RuleFunction ruleFunction = querySession.getRuleFunction(queryRuleFunctionUri);

        RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();

        Object result = null;

        try {
            RuleSessionManager.currentRuleSessions.set(querySession);

            result = ruleFunction.invoke(params);
        } finally {
            RuleSessionManager.currentRuleSessions.set(oldSession);
        }

        return result;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "executeInQuerySession",
            synopsis = "Executes the sql string synchronously in a collocated query agent and returns the results.",
            signature = "Object executeInQuerySession(String querySessionName, String sqlString, Object mapOfParameters, boolean reuse, long... timeout)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "querySessionName", type = "String",
                            desc = "A valid query session/agent name that has been deployed in the same processing unit."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sqlString", type = "String",
                            desc = "A valid snapshot query sql string."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapOfParameters", type = "Object",
                            desc = "types. Or, null if there are no bind parameters."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "reuse", type = "boolean",
                            desc = "string is automatically registered the first time (Query.Create(sql))."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "Long",
                    desc = "Optional Timeout value in milliseconds; default is no timeout(-1) ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an an Object[] of columns."),
            version = "5.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Executes the sql string synchronously in a collocated query agent and returns the results.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object executeInQuerySession(String querySessionName, String sqlString, Object mapOfParameters,
                                               boolean reuse, long... timeout) {
        RuleSessionImpl queryRuleSession = queryRuleSessionImpls.get(querySessionName);

        if (queryRuleSession == null) {
            throw new IllegalArgumentException(
                    "The query session name [" + querySessionName + "] is invalid." +
                            " Valid names are " + queryRuleSessionImpls.keySet());
        }

        RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();

        try {
            RuleSessionManager.currentRuleSessions.set(queryRuleSession);
			long timeOut = -1;
			if (timeout.length > 0) {
				timeOut = timeout[0];
			}
            return SsQueryExecutionHelper.execute(sqlString, (Map<String, Object>) mapOfParameters, reuse, timeOut, querySessionName);
        } finally {
            RuleSessionManager.currentRuleSessions.set(oldSession);
        }
    }

    //----------
    //*******Its used internally by ASAggregate Catalog Functions and should not be exposed to user*******
    //-----------
    private static Object executeInQuerySession(String querySessionName, Map<Object, Object> resultMap, String resultSetName, String valueFieldName, String... groupByFieldNames) {
        RuleSessionImpl queryRuleSession = queryRuleSessionImpls.get(querySessionName);

        if (queryRuleSession == null) {
            throw new IllegalArgumentException(
                    "The query session name [" + querySessionName + "] is invalid." +
                            " Valid names are " + queryRuleSessionImpls.keySet());
        }

        RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();
        Object result = null;

        try {
            RuleSessionManager.currentRuleSessions.set(queryRuleSession);
            return AggQueryExecutionHelper.execute(resultMap, resultSetName, valueFieldName, groupByFieldNames);
        } finally {
            RuleSessionManager.currentRuleSessions.set(oldSession);
        }
    }

    //---------------

    @com.tibco.be.model.functions.BEFunction(
            name = "getDynamicQuerySessionName",
            synopsis = "Returns the name of the collocated query agent that can be started dynamically. This method does not require the\nsession to be running.",
            signature = "String getDynamicQuerySessionName()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String",
                    desc = "The name of the dynamic query session (Fixed)."),
            version = "5.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the name of the collocated query agent that can be started dynamically. This method does not require the\nsession to be running.",
            cautions = "none",
            fndomain = {ACTION, CONDITION},
            example = ""
    )
    public static String getDynamicQuerySessionName() {
        return QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "startDynamicQuerySession",
            synopsis = "Starts a collocated query session, dynamically. The name is always fixed. Only 1 such session can be started per\nJVM.",
            signature = "void startDynamicQuerySession()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Starts a collocated query session, dynamically. The name is always fixed. Only 1 such session can be started per\nJVM.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )

    public static void startDynQrySessionIfNeeded() {
        for (; ; ) {
            Future job;
            for (; (job = recentDynamicSessionOp.get()) != null; ) {
                try {
                    job.get(5, TimeUnit.SECONDS);

                    return;
                } catch (TimeoutException to) {
                    logger.log(Level.INFO, "Waiting for dynamic query session start job to complete");
                } catch (Throwable t) {
                    logger.log(Level.ERROR, "Error occurred in the dynamic query session start job.", t);

                    break;
                }
            }

            RuleSession callersRS = RuleSessionManager.getCurrentRuleSession();
            final RuleServiceProvider rsp = callersRS.getRuleServiceProvider();
            final Cluster cluster = rsp.getCluster();

            Callable<Object> c = new DynamicQueryAgentStarter(rsp, cluster);

            FutureTask currentOp = new FutureTask(c);
            Thread t = new Thread(currentOp, QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName() + ".starter");
            t.setDaemon(true);

            if (recentDynamicSessionOp.compareAndSet(job, currentOp)) {
                logger.log(Level.INFO,
                        "Dynamic query session has not started. An attempt will be made to start it now");
                t.start();
                logger.log(Level.INFO,
                        "Dynamic query session %s has been started", getDynamicQuerySessionName());
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "executeInDynamicQuerySession",
            synopsis = "Executes the sql string in the collocated, dynamic query session. Obviously the session should've been started\nbeforehand.",
            signature = "Object executeInDynamicQuerySession(String sqlString, Object mapOfParameters, boolean reuse, long... timeout)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sqlString", type = "String",
                            desc = "A valid snapshot query sql string."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapOfParameters", type = "Object",
                            desc = "types. Or, null if there are no bind parameters."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "reuse", type = "boolean",
                            desc = "string is automatically registered the first time (Query.Create(sql))."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "Long",
                    desc = "Optional Timeout value in milliseconds; default is no timeout(-1) ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an an Object[] of columns."),
            version = "5.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Executes the sql string in the collocated, dynamic query session. Obviously the session should've been started\nbeforehand.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object executeInDynamicQuerySession(String sqlString, Object mapOfParameters, boolean reuse, long... timeout) {
        try {
            startDynQrySessionIfNeeded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return executeInQuerySession(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName(), 
                sqlString, mapOfParameters, reuse, timeout);
    }

    //----------
    //*******Its used internally by ASAggregate Catalog Functions and should not be exposed to user*******
    //-----------
    public static Object executeInDynamicQuerySession_AsAggregateFunctions(Map<Object, Object> resultMap, String resultSetName, String valueFieldName, String... groupByFieldNames) {
        try {
            startDynQrySessionIfNeeded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return executeInQuerySession(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName(), resultMap, resultSetName, valueFieldName, groupByFieldNames);
    }
    //---------------

    @com.tibco.be.model.functions.BEFunction(
            name = "newArray",
            synopsis = "Creates an Object array.",
            signature = "Object[] newArray(int length)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int",
                            desc = "The length of the Object array to be created.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]",
                    desc = "The java.lang.Object array."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates an Object array.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object[] newArray(int length) {
        return new Object[length];
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "newList",
            synopsis = "Creates a new List.",
            signature = "Object newList()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object(java.util.List)",
                    desc = "A new List instance."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new List.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object newList() {
        return new LinkedList();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addToList",
            synopsis = "Adds the given item into the list.",
            signature = "int addToList(Object list, Object item)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List",
                            desc = "The list to add the item to."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "item", type = "Object",
                            desc = "The item to be added to the list.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int",
                    desc = "The new size of the list after the addition."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the given item into the list.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int addToList(Object list, Object item) {
        List l = (List) list;

        l.add(item);

        return l.size();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "removeFromList",
            synopsis = "Removes the element at the given position in the list.",
            signature = "Object removeFromList(Object list, int index)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List",
                            desc = "The list from which the item at the position/index number will be removed."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int",
                            desc = "The position number from which the item will be removed.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "the item that was removed."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Removes the element at the given position in the list.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object removeFromList(Object list, int index) {
        List l = (List) list;
        return l.remove(index);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "clearList",
            synopsis = "Removes all the elements in the list.",
            signature = "void clearList(Object list)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List",
                            desc = "The list to be cleared.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Removes all the elements in the list.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void clearList(Object list) {
        List l = (List) list;

        l.clear();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "sizeOfList",
            synopsis = "Returns the size/length of the given list.",
            signature = "int sizeOfList(Object list)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List",
                            desc = "The list whose size/length is to be retrieved.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int",
                    desc = "The size of the list."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the size/length of the given list.",
            cautions = "none",
            fndomain = {ACTION, CONDITION},
            example = ""
    )
    public static int sizeOfList(Object list) {
        List l = (List) list;

        return l.size();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "listToArray",
            synopsis = "Copies the list contents by reference to a new array.",
            signature = "Object[] listToArray(Object list)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List",
                            desc = "The list whose elements are to be copied (references only) to a new java.lang.Object array.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]",
                    desc = "The elements of the list in a new array."),
            version = "4.0.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Copies the list contents by reference to a new array.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object[] listToArray(Object list) {
        List l = (List) list;

        return l.toArray();
    }

    public static class DynamicQueryAgentStarter implements Callable<Object> {
        private final RuleServiceProvider rsp;

        private final Cluster cluster;

        public DynamicQueryAgentStarter(RuleServiceProvider rsp, Cluster cluster) {
            this.rsp = rsp;
            this.cluster = cluster;
        }

        @Override
        public Object call() throws Exception {
            try {
                final CddFactory cddFactory = new CddFactoryImpl();

                QueryAgentClassConfig agentClassConfig = cddFactory.createQueryAgentClassConfig();
                agentClassConfig.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());

                AgentConfig agentConfig = cddFactory.createAgentConfig();
                agentConfig.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());
                QueryAgentClassConfigImpl qac = new QueryAgentClassConfigImpl() {
                };
                qac.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());
                agentConfig.setRef(qac);

                QueryAgent queryAgent = (QueryAgent) AgentBuilder.getInstance().build(
                        rsp, cluster.getClusterName(), agentConfig);

                //--------------

                queryAgent.init(cluster.getAgentManager());

                queryAgent.start(CacheAgent.AgentState.REGISTERED);

                registerQueryRuleSession(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName(),
                        (RuleSessionImpl) queryAgent.getRuleSession());
            } catch (Throwable t) {
                Logger logger = LogManagerFactory.getLogManager().getLogger(QueryUtilFunctions.class);
                logger.log(Level.ERROR, "Error occurred while starting the dynamic query session", t);

                throw new RuntimeException(t);
            }

            return null;
        }
    }
}
