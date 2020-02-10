package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 21, 2008
* Time: 8:07:39 PM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query",
        synopsis = "Query functions.")
public class QueryFunctions {

    static final ConcurrentHashMap<String, QueryContext> NAME_TO_CONTEXT = new ConcurrentHashMap<String, QueryContext>();

    protected static class QueryContext {
        private String name;
        private Query query;
        private ConcurrentHashMap<String, String> statementNames;

        public QueryContext(String name, Query query) {
            this.name = name;
            this.query = query;
            this.statementNames = new ConcurrentHashMap<String, String>();
        }

        public String getName() {
            return this.name;
        }

        public Query getQuery() {
            return this.query;
        }

        public ConcurrentHashMap<String, String> getStatementNames() {
            return this.statementNames;
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
        name = "create",
        synopsis = "Creates a query and registers it under the given name.",
        signature = "void create(String name, String query)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "name of the query."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "query", type = "String", desc = "text of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a query and registers it under the given name.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void create(String name, String query) {
        createInternal(name, query, false);
    }

    /**
     * @param name
     * @param query
     * @param ignoreIfExists
     * @return true if creation succeeded. false if it was already present.
     */
    public static boolean createInternal(String name, String query, boolean ignoreIfExists) {
        if ((null == name) || (null == query)) {
            throw new IllegalArgumentException();
        }
        try {
            QueryContext context = NAME_TO_CONTEXT.get(name);
            if (context != null) {
                if (ignoreIfExists) {
                    return false;
                }

                throw new IllegalArgumentException("Query name already in use.");
            }

            final QuerySession qs = checkAndGetQuerySession();
            final Query q = qs.createQuery(name, query);

            context = new QueryContext(name, q);
            if (null != NAME_TO_CONTEXT.putIfAbsent(name, context)) {
                if (ignoreIfExists) {
                    return false;
                }

                throw new IllegalArgumentException("Query name already in use.");
            }
        } catch (Throwable t) {
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new RuntimeException(t);
            }
        }
        return true;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "delete",
        synopsis = "Deletes a query.",
        signature = "void delete(String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "name of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes a query.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void delete(String name) {
        if (null == name) {
            throw new IllegalArgumentException();
        }
        checkAndGetQuerySession();
        final QueryContext context = NAME_TO_CONTEXT.remove(name);
        if (null != context) {
            for (String statementName : new ArrayList<String>(context.getStatementNames().keySet())) {
                StatementFunctions.close(statementName);
            }
            context.getQuery().getPlanGenerator().discard();
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "exists",
        synopsis = "Checks if a query exists.",
        signature = "boolean exists(String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "name of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if and only if the query exists."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if a query exists.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static boolean exists(String name) {
        return NAME_TO_CONTEXT.get(name) != null;
    }

    static PlanGenerator getPlanGenerator(String queryName) {
        final QueryContext context = NAME_TO_CONTEXT.get(queryName);
        if (null == context) {
            return null;
        }
        return context.getQuery().getPlanGenerator();
    }
}
