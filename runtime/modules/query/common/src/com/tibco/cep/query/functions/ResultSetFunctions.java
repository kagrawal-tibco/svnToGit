package com.tibco.cep.query.functions;

import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.ResultSet",
        synopsis = "Query result set manipulation functions.")
public class ResultSetFunctions {


    protected static final ConcurrentHashMap<String, ResultSetContext> NAME_TO_CONTEXT =
            new ConcurrentHashMap<String, ResultSetContext>();


    protected static class ResultSetContext {
        private QueryResultSet resultSet;

        private String name;

        private String statementName;

        public ResultSetContext(String name, QueryResultSet resultSet, String statementName) {
            this.name = name;
            this.resultSet = resultSet;
            this.statementName = statementName;
        }

        public String getName() {
            return this.name;
        }

        public QueryResultSet getResultSet() {
            return this.resultSet;
        }

        public String getStatementName() {
            return this.statementName;
        }
    }


    private static QuerySession checkAndGetQuerySession() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "close",
        synopsis = "Closes a result set.",
        signature = "void close(String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes a result set.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void close(String resultsetName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.remove(resultsetName);
        if (null != context) {
            try {
                context.getResultSet().close();
                StatementFunctions.unregisterResultSet(context.getStatementName(), resultsetName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeAggregate(String resultsetName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.remove(resultsetName);
        if (null != context) {
            try {
                context.getResultSet().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "findColumn",
        synopsis = "Finds by name the index of a column in the result set.",
        signature = "String findColumn(String statementName, String columnName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "name of a column in the result set of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "index of that column or -1 if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds by name the index of a column in the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int findColumn(String resultsetName, String columnName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            throw new IllegalArgumentException();
        }

        return context.getResultSet().findColumn(columnName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "get",
        synopsis = "Gets the value of the column at the given index.",
        signature = "Object get(String resultsetName, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "index of the column.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The value of the column at the given index."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the column at the given index.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object get(String resultsetName, int index) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            throw new IllegalArgumentException();
        }

        return context.getResultSet().getObject(index);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRowCountIfPossible",
        synopsis = "Gets the value of the column at the given index.",
        signature = "int getRowCountIfPossible(String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The number of rows/results that were retrieved by the query, if all the results are retrieved as soon\nas the query is executed by the engine (>= 0). Otherwise returns -1.\n<p/>\nFor a query like - select cust from /Customer as cust where cust.age = 100, the Query Engine does not\nknow how many rows there are going to be in the result set because it immediately starts filtering and\nfeeding the results from the Cache to the ResultSet/user without even knowing when it will end.\n<p/>\nFor Snapshot queries without group-by and/or order-by and all Continuous queries this count cannot be\ncomputed and will return -1."),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The number of rows/results that were retrieved by the query.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int getRowCountIfPossible(String resultsetName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            throw new IllegalArgumentException();
        }

        return context.getResultSet().getRowCountIfPossible();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isBatchEnd",
        synopsis = "Returns <code>true</code> if and only if the cursor of the result set points to a batch end.",
        signature = "boolean isBatchEnd(String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "<code>true</code> if and only if the cursor of the result set points to a batch end."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns <code>true</code> if and only if the cursor of the result set points to a batch end.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static boolean isBatchEnd(String resultsetName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        return context.getResultSet().isBatchEnd();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "isOpen",
        synopsis = "Checks if a result with the given name is open.",
        signature = "boolean isOpen(String resultSetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if a result set by that name is open."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if the result with the given name is open.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static boolean isOpen(String resultSetName) {
        return null != NAME_TO_CONTEXT.get(resultSetName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "next",
        synopsis = "Moves the result set cursor to the next item, and returns a boolean which is <code>false</code> if the\nquery has ended.",
        signature = "boolean next(String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the statement currently executed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "false if and only if the query has no more row."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Moves the result set cursor to the next item, and returns a boolean which is <code>false</code> if\nthe query has ended.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean next(String resultsetName) {
        checkAndGetQuerySession();
        final ResultSetContext context = NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            throw new IllegalArgumentException();
        }
        return context.getResultSet().next();
    }


    public static void register(String statementName, String resultsetName, QueryResultSet resultSet) {
        if (isOpen(resultsetName)
                || (null == resultSet)) {
            throw new IllegalArgumentException();
        }
        final ResultSetContext context = new ResultSetContext(resultsetName, resultSet, statementName);
        if (null != NAME_TO_CONTEXT.putIfAbsent(resultsetName, context)) {
            throw new IllegalArgumentException("Result set name already in use.");
        }
    }

}
