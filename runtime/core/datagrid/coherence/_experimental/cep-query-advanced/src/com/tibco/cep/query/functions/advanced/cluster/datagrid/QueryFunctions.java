package com.tibco.cep.query.functions.advanced.cluster.datagrid;

/*
* Author: Ashwin Jayaprakash / Date: 3/15/11 / Time: 10:54 PM
*/

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.query.client.console.swing.util.Registry;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

import java.io.ByteArrayOutputStream;
import java.util.List;

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query Advanced",
        category = "Cluster_DataGrid.Query",
        synopsis = "Advanced datagrid query functions.")
public class QueryFunctions {
    @com.tibco.be.model.functions.BEFunction(
        name = "getInternalEventPropertyName",
        synopsis = "Gets the internal name of the property. No validation is performed.",
        signature = "String getInternalEventPropertyName(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "$1volume$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The internal representation of the property name."),
        version = "4.0.1",
        see = "",
        mapper = false,
        description = "",
        cautions = "none",
        fndomain = {QUERY},
        example = ""
    )
    public static String getInternalEventPropertyName(String propertyName) {
        return "$2z" + propertyName;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getInternalConceptPropertyName",
        synopsis = "Gets the internal name of the property. No validation is performed.",
        signature = "String getInternalConceptPropertyName(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "$1volume$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The internal representation of the property name."),
        version = "4.0.1",
        see = "",
        mapper = false,
        description = "",
        cautions = "none",
        fndomain = {QUERY},
        example = ""
    )
    public static String getInternalConceptPropertyName(String propertyName) {
        return getInternalEventPropertyName(propertyName) + ".value";
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeQuery",
        synopsis = "Invokes a query.",
        signature = "Object executeQuery(String sql)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sql", type = "String", desc = "A valid cluster datagrid query.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Array of rows where each row has an array of columns. Consistency may be enforced by acquiring\nappropriate locks."),
        version = "4.0.1",
        see = "",
        mapper = false,
        description = "",
        cautions = "none",
        fndomain = {QUERY},
        example = ""
    )
    public static Object executeQuery(String sql) {
        ClassLoader origCL = Thread.currentThread().getContextClassLoader();

        RuleSessionImpl rs = (RuleSessionImpl) RuleSessionManagerImpl.getCurrentRuleSession();
        if (rs == null) {
            rs = (RuleSessionImpl) Registry.getRegistry().getQuerySession().getRuleSession();
        }

        Thread.currentThread().setContextClassLoader(rs.getRuleServiceProvider().getClassLoader());
        try {
            QueryPlusPlus queryPlus = new QueryPlusPlus();
            ByteArrayOutputStream dummyBaos = new ByteArrayOutputStream(128);
            ResultSetCapturingPW printWriter = new ResultSetCapturingPW(dummyBaos);
            queryPlus.evalLine(sql, printWriter);

            printWriter.close();

            List<Object[]> rows = printWriter.getRows();
            Object[] table = rows.toArray();
            rows.clear();

            return table;
        }
        finally {
            Thread.currentThread().setContextClassLoader(origCL);
        }
    }
}
