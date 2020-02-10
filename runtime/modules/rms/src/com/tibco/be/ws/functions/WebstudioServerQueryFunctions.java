package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.query.functions.QueryUtilFunctions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/4/12
 * Time: 7:48 AM
 * Wrapper over query function classes.
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
    category = "WS.Common.Query",
    synopsis = "Functions to query cache.",
    enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Common.Query", value=true))

public class WebstudioServerQueryFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "executeQuery",
        synopsis = "Executes the sql string synchronously in a collocated query agent and returns the results.",
        signature = "Object executeQuery(String querySessionName, String sqlString, Object mapOfParameters, boolean reuse)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "querySessionName", type = "String", desc = "A valid query session/agent name that has been deployed in the same processing unit."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sqlString", type = "String", desc = "A valid snapshot query sql string."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapOfParameters", type = "Object", desc = "types. Or, null if there are no bind parameters."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "reuse", type = "boolean", desc = "string is automatically registered the first time (Query.Create(sql)).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Return a List of rows. Each row may be a single Object column or an an Object[] of columns."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public static Object executeQuery(String querySessionName,
                                      String sqlString,
                                      Object mapOfParameters,
                                      boolean reuse) {
        return QueryUtilFunctions.executeInQuerySession(querySessionName, sqlString, mapOfParameters, reuse, -1);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "listToArray",
        synopsis = "Copies the list contents by reference to a new array.",
        signature = "Object[] listToArray(Object list)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List", desc = "The list whose elements are to be copied (references only) to a new java.lang.Object array.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The elements of the list in a new array."),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public static Object[] listToArray(Object list) {
        return QueryUtilFunctions.listToArray(list);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sizeOfList",
        synopsis = "Returns the size/length of the given list.",
        signature = "int sizeOfList(Object list)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List", desc = "The list whose size/length is to be retrieved.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The size of the list."),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public static int sizeOfList(Object list) {
        return QueryUtilFunctions.sizeOfList(list);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clearList",
        synopsis = "Removes all the elements in the list.",
        signature = "void clearList(Object list)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "java.util.List", desc = "The list to be cleared.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public static void clearList(Object list) {
        QueryUtilFunctions.clearList(list);
    }

}
