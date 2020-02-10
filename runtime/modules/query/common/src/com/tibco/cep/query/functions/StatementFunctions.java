package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.api.*;
import com.tibco.cep.query.api.impl.local.QueryConnectionImpl;
import com.tibco.cep.query.service.QueryFeatures;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.impl.rete.LiteReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 21, 2008
* Time: 8:07:39 PM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.Statement",
        synopsis = "Query Statement functions.")
public class StatementFunctions {


    protected static final ConcurrentHashMap<String, StatementContext> NAME_TO_CONTEXT =
            new ConcurrentHashMap<String, StatementContext>();

    protected static final QueryPolicy POLICY_FOR_SNAPSHOT = new QueryPolicy.Snapshot();
    protected static final QueryPolicy POLICY_FOR_CONTINUOUS = new QueryPolicy.Continuous();



    protected static class StatementContext {
        private QueryFunctions.QueryContext queryContext;
        private ConcurrentHashMap<String, String> listenerNames;
        private String name;
        private String queryName;
        private ConcurrentHashMap<String, String> resultSetNames;
        private QueryPolicy.SnapshotThenContinuous snapshotPolicy;
        private QueryStatement statement;
        private ConcurrentHashMap<String, Object> variables;

        public StatementContext(QueryFunctions.QueryContext queryContext, String name, QueryStatement statement, String queryName) {
            this.queryContext = queryContext;
            this.listenerNames = new ConcurrentHashMap<String, String>();
            this.name = name;
            this.queryName = queryName;
            this.resultSetNames = new ConcurrentHashMap<String, String>();
            this.snapshotPolicy = new QueryPolicy.SnapshotThenContinuous();
            this.statement = statement;
            this.variables = new ConcurrentHashMap<String, Object>();
        }

        public QueryFunctions.QueryContext getQueryContext() {
            return queryContext;
        }

        public ConcurrentHashMap<String, String> getListenerNames() {
            return this.listenerNames;
        }

        public String getName() {
            return this.name;
        }

        public String getQueryName() {
            return this.queryName;
        }

        public ConcurrentHashMap<String, String> getResultSetNames() {
            return this.resultSetNames;
        }

        public QueryPolicy.SnapshotThenContinuous getSnapshotThenContinuousPolicy() {
            return this.snapshotPolicy;
        }

        public QueryStatement getStatement() {
            return this.statement;
        }

        public ConcurrentHashMap<String, Object> getVariables() {
            return this.variables;
        }
    }


    private static QuerySession checkAndGetQuerySession() {
        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "clearSnapshotRequired",
        synopsis = "Clears all the requests for snapshots associated to the statement.",
        signature = "void clearSnapshotRequired(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clears all the requests for snapshots associated to the statement.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void clearSnapshotRequired(String statementName) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }

        context.getSnapshotThenContinuousPolicy().clearSnapshotRequired();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "clearVars",
        synopsis = "Clears the values of all the bind variables associated to the statement.",
        signature = "void clearVars(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clears the values of all the bind variables associated to the statement.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void clearVars(String statementName) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        context.getStatement().clearParameters();
        context.getVariables().clear();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "close",
        synopsis = "Closes the statement and all its resultsets.",
        signature = "void close(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes the statement and all its resultsets.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void close(String statementName) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.remove(statementName);
        if (null != context) {
            for (String resultsetName : new ArrayList<String>(context.getResultSetNames().keySet())) {
                ResultSetFunctions.close(resultsetName);
            }
            for (String listenerName : new ArrayList<String>(context.getListenerNames().keySet())) {
                CallbackFunctions.delete(listenerName);
            }
            context.getStatement().close();
            context.getQueryContext().getStatementNames().remove(statementName);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "assertEvent",
        synopsis = "Sends the event directly to all query instances registered under the given statement name.",
        signature = "void assertEvent(String statementName, Event event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "The", desc = "query instances registered under the name to which the event must be sent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "Event.assertEvent(event)",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends the event directly to all query instances registered under the given statement name.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void assertEvent(String statementName, Event event) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }

        QueryStatement qs = context.getStatement();
        ReteQuery[] cachedReteQueries = qs.getCachedReteQueries();
        if(cachedReteQueries.length == 0){
            return;
        }

        ReteEntityHandle handle =
                new ReteEntityHandle(event.getClass(), event.getId(), ReteEntityHandleType.NEW);
        SharedObjectSource sos = qs.getFakeSharedObjectSource();
        SharedPointer sharedPointer = new LiteReteEntity(event, sos);
        handle.setSharedPointer(sharedPointer);

        for (ReteQuery cachedReteQuery : cachedReteQueries) {
            try {
                cachedReteQuery.enqueueInput(handle);
            }
            catch (Exception e1) {
                throw new RuntimeException("Error occurred while asserting event to query: " +
                        cachedReteQuery.getName(), e1);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "execute",
        synopsis = "Executes a statement and binds a result set to it.",
        signature = "void execute(String statementName, String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement to execute."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set produced by the execution.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a statement and binds a result set to it.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void execute(String statementName, String resultsetName) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if ((null == context) || ResultSetFunctions.isOpen(resultsetName)) {
            throw new IllegalArgumentException();
        }

        try {
            QueryFeatures queryFeatures = context.getQueryContext().getQuery().getQueryFeatures();
            final QueryResultSet resultSet = context.getStatement().executeQuery(POLICY_FOR_SNAPSHOT,queryFeatures);
            ResultSetFunctions.register(statementName, resultsetName, resultSet);
            context.getResultSetNames().put(resultsetName, resultsetName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeWithCallback",
        synopsis = "Executes a statement and binds a callback rule function to it.",
        signature = "void executeWithCallback(String statementName, String listenerName, String callbackUri, boolean isContinuous, Object closure)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement to execute."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerName", type = "String", desc = "name of the listener to create."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "callbackUri", type = "String", desc = "<li><code>Object closure</code>: the closure provided to <code>executeWithCallback</code>.</li></ul>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isContinuous", type = "boolean", desc = "true for the statement to be executed in continuous mode."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "closure", type = "Object", desc = "that will be blindly returned through a callback parameter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a query and binds a callback rule function to it.",
        cautions = "none",
        async=true,
        fndomain = {ACTION},
        example = ""
    )
    public static void executeWithCallback(String statementName, String listenerName, String callbackUri, boolean isContinuous,
                                           Object closure) {

        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if ((null == context)
                || CallbackFunctions.exists(listenerName)
                || (null == callbackUri)) {
            throw new IllegalArgumentException();
        }

        final QueryPolicy policy = isContinuous ? context.getSnapshotThenContinuousPolicy() : POLICY_FOR_SNAPSHOT;

        final RuleFunctionQueryListener listener = new RuleFunctionQueryListener(
                listenerName,
                RuleSessionManager.getCurrentRuleSession(),
                callbackUri,
                closure);

        try {
            QueryFeatures queryFeatures = context.getQueryContext().getQuery().getQueryFeatures();
            context.getStatement().executeQuery(policy, listener, queryFeatures);
            CallbackFunctions.register(statementName, listenerName, listener);
            context.getListenerNames().put(listenerName, listenerName);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeWithCallback_Internal(String statementName, String listenerName, QueryListener listener, boolean isContinuous,
                                           Object closure) {

        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if ((null == context)
                || CallbackFunctions.exists(listenerName)
                || (null == listener)) {
            throw new IllegalArgumentException();
        }

        final QueryPolicy policy = isContinuous ? context.getSnapshotThenContinuousPolicy() : POLICY_FOR_SNAPSHOT;

        try {
            QueryFeatures queryFeatures = context.getQueryContext().getQuery().getQueryFeatures();
            context.getStatement().executeQuery(policy, listener,queryFeatures);
            CallbackFunctions.register(statementName, listenerName, listener);
            context.getListenerNames().put(listenerName, listenerName);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeSSThenContinuousWithCallback",
        enabled = @Enabled(value=false),
        synopsis = "Executes a statement in $1Snapshot then Continuous$1 Mode and binds a callback rule function to it.",
        signature = "void executeSSThenContinuousWithCallback(String statementName, String listenerName, String callbackUri, boolean isContinuous, Object closure)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement to execute."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerName", type = "String", desc = "name of the listener to create."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listener", type = "Query", desc = "listener"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "closure", type = "Object", desc = "that will be blindly returned through a callback parameter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a statement in $1Snapshot then Continuous$1 Mode.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void executeSSThenContinuousWithCallback(String statementName, String listenerName,
                                                           QueryListener listener, Object closure) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if ((null == context)
                || CallbackFunctions.exists(listenerName)
                || (null == listener)) {
            throw new IllegalArgumentException();
        }

        final QueryPolicy policy = context.getSnapshotThenContinuousPolicy();

        try {
            List<String> aliasList = context.getQueryContext().getQuery().getPlanGenerator().getSourceNames();
            for(String alias : aliasList) {
                context.getSnapshotThenContinuousPolicy().setSnapshotRequired(alias, true);    
            }
            QueryFeatures queryFeatures = context.getQueryContext().getQuery().getQueryFeatures();
            context.getStatement().executeQuery(policy, listener,queryFeatures);
            CallbackFunctions.register(statementName, listenerName, listener);
            context.getListenerNames().put(listenerName, listenerName);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeWithBatchCallback",
        synopsis = "Executes a statement and binds a callback rule function to it.",
        signature = "void executeWithBatchCallback(String statementName, String listenerName, String callbackUri, boolean isContinuous, Object closure)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement to execute."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerName", type = "String", desc = "name of the listener to create."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "callbackUri", type = "String", desc = "<li><code>Object closure</code>: the closure provided to <code>executeWithBatchCallback</code>.</li></ul>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isContinuous", type = "boolean", desc = "true for the statement to be executed in continuous mode."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "closure", type = "Object", desc = "that will be blindly returned through a callback parameter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a query and binds a callback rule function to it.",
        cautions = "Since the entire set of rows from a batch accumulate until the end of the batch, this method is not suitable for batches that produce\na large number of rows in each batch.",
        async=true,
        fndomain = {ACTION},
        example = ""
    )
    public static void executeWithBatchCallback(String statementName, String listenerName, String callbackUri, boolean isContinuous,
                                           Object closure) {

        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if ((null == context)
                || CallbackFunctions.exists(listenerName)
                || (null == callbackUri)) {
            throw new IllegalArgumentException();
        }

        final QueryPolicy policy = isContinuous ? context.getSnapshotThenContinuousPolicy() : POLICY_FOR_SNAPSHOT;

        final RuleFunctionQueryListener listener = new RuleFunctionBatchedQueryListener(
                listenerName,
                RuleSessionManager.getCurrentRuleSession(),
                callbackUri,
                closure);

        try {
            QueryFeatures queryFeatures = context.getQueryContext().getQuery().getQueryFeatures();
            context.getStatement().executeQuery(policy, listener, queryFeatures);
            CallbackFunctions.register(statementName, listenerName, listener);
            context.getListenerNames().put(listenerName, listenerName);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSnapshotRequired",
        synopsis = "Checks if the statement will make a snapshot, or not, for an item in the FROM, at execution time,\nfor continuous queries.",
        signature = "boolean getSnapshotRequired(String statementName, String alias)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "alias of a item in the FROM of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "true", desc = "if and only if the statement will make a snapshot, or not, for an item in the FROM, at execution time."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if the statement will make a snapshot, or not, for an item in the FROM, at execution time,\nfor continuous queries.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean getSnapshotRequired(String statementName, String alias) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        return context.getSnapshotThenContinuousPolicy().getSnapshotRequired(alias);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getVar",
        synopsis = "Gets the value of a bind variable in a statement.",
        signature = "Object getVar(String statementName, String bindVarName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindVarName", type = "String", desc = "name of a bind variable in the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "value of the bind variable in the statement."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of a bind variable in a statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static Object getVar(String statementName, String bindVarName) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        return context.getVariables().get(bindVarName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "isOpen",
        synopsis = "Checks if a statement is open.",
        signature = "boolean isOpen(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if and only if the statement is open."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if a statement is open.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static boolean isOpen(String statementName) {
        return null != NAME_TO_CONTEXT.get(statementName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "open",
        synopsis = "Opens a statement using a registered query name.",
        signature = "void open(String queryName, String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryName", type = "String", desc = "name of the query that the statement will use."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name given to the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Opens a statement using a registered query name.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void open(String queryName, String statementName) {
        checkAndGetQuerySession();
        final QueryFunctions.QueryContext queryContext = QueryFunctions.NAME_TO_CONTEXT.get(queryName);
        if ((null == queryContext)
                || (null == statementName)
                || "".equals(statementName)) {
            throw new IllegalArgumentException();
        }
        try {
            final QueryConnectionImpl connection = (QueryConnectionImpl) ConnectionCache.getInstance().get();
            final QueryStatement statement = connection.prepareStatement(queryContext.getQuery().getPlanGenerator());
            final StatementContext statementContext = new StatementContext(queryContext, statementName, statement, queryName);
            if (null != NAME_TO_CONTEXT.putIfAbsent(statementName, statementContext)) {
                throw new IllegalArgumentException("Statement name already in use.");                            
            }
            queryContext.getStatementNames().put(statementName, statementName);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

// Not implemented yet.
//
//    /**
//     * @param statementName String name of the statement.
//     * @.name pause
//     * @.synopsis Pauses the statement.
//     * @.signature void pause(String name)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Pauses the statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void pause(String statementName) {
//        final QueryStatement statement = NAME_TO_STATEMENT.get(statementName);
//        if (null == statement) {
//            throw new IllegalArgumentException();
//        }
//        statement.pause();
//    }

// Not implemented yet.
//
//    /**
//     * @param statementName String name of the statement.
//     * @.name resume
//     * @.synopsis Resumes a paused statement.
//     * @.signature void resume(String name)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Resumes a paused statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void resume(String statementName) {
//        final QueryStatement statement = NAME_TO_STATEMENT.get(statementName);
//        if (null == statement) {
//            throw new IllegalArgumentException();
//        }
//        statement.resume();
//    }

// Unnecessary.
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Boolean value to bind to the given bind variable name.
//     * @.name setBoolean
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature void setBoolean(String statementName, String bindvarName, boolean value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setBoolean(String statementName, String bindVarName, Boolean value) {
//        set(statementName, bindVarName, value);
//    }
//
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Concept value to bind to the given bind variable name.
//     * @.name setConcept
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature double setConcept(String statementName, String bindvarName, Concept value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setConcept(String statementName, String bindVarName, Concept value) {
//        set(statementName, bindVarName, value);
//    }
//
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Double value to bind to the given bind variable name.
//     * @.name setDouble
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature void setDouble(String statementName, String bindvarName, double value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setDouble(String statementName, String bindVarName, Double value) {
//        set(statementName, bindVarName, value);
//    }
//
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Event value to bind to the given bind variable name.
//     * @.name setEvent
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature void setEvent(String statementName, String bindvarName, Event value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setEvent(String statementName, String bindVarName, Event value) {
//        set(statementName, bindVarName, value);
//    }
//
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Integer value to bind to the given bind variable name.
//     * @.name setInt
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature void setInt(String statementName, String bindvarName, int value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setInt(String statementName, String bindVarName, Integer value) {
//        set(statementName, bindVarName, value);
//    }
//
//
//    /**
//     * @param statementName String name of the statement.
//     * @param bindVarName   name of a bind variable in the statement.
//     * @param value         Long value to bind to the given bind variable name.
//     * @.name setLong
//     * @.synopsis Sets the value of a bind variable in a statement.
//     * @.signature void setLong(String statementName, String bindvarName, long value)
//     * @.version 3.0.0
//     * @.see
//     * @.mapper false
//     * @.description Sets the value of a bind variable in a statement.
//     * @.cautions none
//     * @.domain action
//     * @.example
//     */
//    public static void setLong(String statementName, String bindVarName, Long value) {
//        set(statementName, bindVarName, value);
//    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setSnapshotRequired",
        synopsis = "Requests that a snapshot be made, or not, for an item in the FROM, at execution time,\nfor continuous queries. This is not used when executing non-continuous queries.",
        signature = "void setSnapshotRequired(String statementName, String alias, boolean isRequired)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "alias of a item in the FROM of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isRequired", type = "boolean", desc = "true if and only if a snapshot is required for the aliased item.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Requests that a snapshot be made, or not, for an item in the FROM, at execution time,\nfor continuous queries. This is not used when executing non-continuous queries.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setSnapshotRequired(String statementName, String alias, boolean isRequired) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException("Unkown statement: " + statementName);
        }
        if (null == alias) {
            throw new IllegalArgumentException("Alias is null.");
        }
        if (!context.getQueryContext().getQuery().getPlanGenerator().getSourceNames().contains(alias)) {
            throw new IllegalArgumentException("Alias not found: " + alias);
        }
        context.getSnapshotThenContinuousPolicy().setSnapshotRequired(alias, isRequired);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setVar",
        synopsis = "Sets the value of a bind variable in a statement.",
        signature = "void setVar(String statementName, String bindvarName, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindVarName", type = "name", desc = "of a bind variable in the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "value to bind to the given bind variable name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of a bind variable in a statement.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setVar(String statementName, String bindVarName, Object value) {
        checkAndGetQuerySession();
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        context.getVariables().put(bindVarName, value);
        context.getStatement().setObject(bindVarName, value);
    }

    static void unregisterResultSet(String statementName, String resultSetName) {
        final StatementContext context = NAME_TO_CONTEXT.get(statementName);
        if (null != context) {
            context.getResultSetNames().remove(resultSetName);
        }
    }
}
