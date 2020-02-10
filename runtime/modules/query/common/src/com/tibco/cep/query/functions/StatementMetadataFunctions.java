package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 21, 2008
* Time: 8:07:39 PM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.Statement.Metadata",
        synopsis = "Functions to inspect the metadata of a statement.")
public class StatementMetadataFunctions {


    private static QuerySession checkAndGetQuerySession() {
        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }

    
    @com.tibco.be.model.functions.BEFunction(
        name = "findColumn",
        synopsis = "Finds the index of a column in the result of the statement.",
        signature = "int findColumn(String statementName, String columnName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "name of a column in the result of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "index of that column or -1 if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds the index of a column in the result of the statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int findColumn(String statementName, String columnName) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(statementName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return -1;
        }
        return planGenerator.getColumnNames().indexOf(columnName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnCount",
        synopsis = "Gets the number of columns in the result of the statement.",
        signature = "int getColumnCount(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "number of columns in the result of the statement."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the number of columns in the result of the statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int getColumnCount(String statementName) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(statementName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return 0;
        }
        return planGenerator.getColumnNames().size();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnType",
        synopsis = "Gets the type of a column in the result set.",
        signature = "String getColumnType(String statementName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the type of a column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the type of a column in the statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnType(String statementName, int columnIndex) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(statementName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnTypeNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnName",
        synopsis = "Gets the name of a column in the result of the statement.",
        signature = "String getColumnName(String statementName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the result of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of that column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of a column in the result of the statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnName(String statementName, int columnIndex) {
        checkAndGetQuerySession();
        final String queryName = getQueryName(statementName);
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getQueryName",
        synopsis = "Gets the name of the query that owns the statement.",
        signature = "String getQueryName(String statementName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statementName", type = "String", desc = "name of the statement.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the query that owns the statement."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of the query that owns the statement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getQueryName(String statementName) {
        final StatementFunctions.StatementContext context = StatementFunctions.NAME_TO_CONTEXT.get(statementName);
        if (null == context) {
            return null;
        }
        return context.getQueryName();
    }

}
