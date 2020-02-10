package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
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
        category = "Query.Metadata",
        synopsis = "Functions to inspect the metadata of a query.")
public class QueryMetadataFunctions {


    private static QuerySession checkAndGetQuerySession() {
        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "findColumn",
        synopsis = "Finds the index of a column in the result of the query.",
        signature = "int findColumn(String queryName, String columnName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryName", type = "String", desc = "name of the query."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "name of a column in the result of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "index of that column or -1 if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds the index of a column in the result of the query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int findColumn(String queryName, String columnName) {
        checkAndGetQuerySession();
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return -1;
        }
        return planGenerator.getColumnNames().indexOf(columnName);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnCount",
        synopsis = "Gets the number of columns in the result of the query.",
        signature = "int getColumnCount(String queryName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryName", type = "String", desc = "name of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "number of columns in the result of the query."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the number of columns in the result of the query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int getColumnCount(String queryName) {
        checkAndGetQuerySession();
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return 0;
        }
        return planGenerator.getColumnNames().size();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnType",
        synopsis = "Gets the type of a column in the result of the query.",
        signature = "String getColumnType(String queryName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryName", type = "String", desc = "name of the queryName."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the result of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of the type of a column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the type of a column in the result of the query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnType(String queryName, int columnIndex) {
        checkAndGetQuerySession();
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnTypeNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnName",
        synopsis = "Gets the name of a column in the result of the query.",
        signature = "String getColumnName(String queryName, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryName", type = "String", desc = "name of the query."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "index of a column in the result of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "name of that column or null if the column was not found."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the name of a column in the result of the query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getColumnName(String queryName, int columnIndex) {
        checkAndGetQuerySession();
        final PlanGenerator planGenerator = QueryFunctions.getPlanGenerator(queryName);
        if (null == planGenerator) {
            return null;
        }
        return planGenerator.getColumnNames().get(columnIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getText",
        synopsis = "Gets the text of the query.",
        signature = "String getText(String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "name of the query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "text of the query"),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the text of the query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getText(String name) {
        if (null == name) {
            throw new IllegalArgumentException();
        }
        final QueryFunctions.QueryContext context = QueryFunctions.NAME_TO_CONTEXT.get(name);
        if (null == context) {
            return null;
        }
        return context.getQuery().getSourceText();
    }


}
