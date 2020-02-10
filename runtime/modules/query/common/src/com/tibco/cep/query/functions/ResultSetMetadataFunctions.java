package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: May 17, 2008
* Time: 12:45:38 PM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.ResultSet.Metadata",
        synopsis = "Functions to inspect the metadata of a ruleset.")
public class ResultSetMetadataFunctions {


    private static QuerySession checkAndGetQuerySession() {
        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "findColumn",
        synopsis = "Finds the index of a column in the result set.",
        signature = "int findColumn(String resultSetName, String columnName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "name of a column in the result of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "index of that column or -1 if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds the index of a column in the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int findColumn(String resultSetName, String columnName) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(resultSetName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return -1;
        }
        return planGenerator.getColumnNames().indexOf(columnName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnCount",
        synopsis = "Gets the number of columns in the result set.",
        signature = "int getColumnCount(String resultSetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "number of columns in the result set."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the number of columns in the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int getColumnCount(String resultSetName) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(resultSetName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return 0;
        }
        return planGenerator.getColumnNames().size();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnName",
        synopsis = "Gets the name of a column in the result set.",
        signature = "String getColumnName(String resultSetName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of that column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of a column in the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnName(String resultSetName, int columnIndex) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(resultSetName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnType",
        synopsis = "Gets the type of a column in the result set.",
        signature = "String getColumnType(String resultSetName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the type of a column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the type of a column in the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnType(String resultSetName, int columnIndex) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(resultSetName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnTypeNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getQueryName",
        synopsis = "Gets the name of the query used to build the statement that owns the result set.",
        signature = "String getQueryName(String resultSetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the query used to build the statement that owns the result set."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of the query used to build the statement that owns the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getQueryName(String resultSetName) {
        return StatementMetadataFunctions.getQueryName(getStatementName(resultSetName));
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getStatementName",
        synopsis = "Gets the name of the statement that owns the result set.",
        signature = "String getStatementName(String resultsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultsetName", type = "String", desc = "name of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the statement that owns the result set."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of the statement that owns the result set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getStatementName(String resultsetName) {
        final ResultSetFunctions.ResultSetContext context = ResultSetFunctions.NAME_TO_CONTEXT.get(resultsetName);
        if (null == context) {
            return null;
        }
        return context.getStatementName();
    }


}
