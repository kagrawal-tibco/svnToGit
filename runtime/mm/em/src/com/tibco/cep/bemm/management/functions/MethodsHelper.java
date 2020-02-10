/**
 * 
 */
package com.tibco.cep.bemm.management.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "methods",
        synopsis = "Methods for various entity and remote invocation methods")
public class MethodsHelper {
	
	@com.tibco.be.model.functions.BEFunction(
        name = "removeJMXClientConnection",
        synopsis = "Returns the Entity using <code>extId</code> as the external ID.",
        signature = "void removeJMXClientConnection(String monitoredEntityName, String entityType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "Monitered Entity Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = "Instance with the external ID as <code>extId</code>.  Returns <code>null</code> if no\nsuch entity exists in Working Memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Entity using <code>extId</code> as the external ID.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static void removeJMXClientConnection(String monitoredEntityName, String entityType) {
		RemoteMngmtProvider.removeJMXClientConnection(monitoredEntityName, entityType);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "invoke",
        synopsis = "Invokes a given method returning the failure/success response of the invocation.",
        signature = "Object invoke(String monitoredEntityName, String entityType, String methodGroup, String methodName," +
            "String[] properties, String[] params, String userName, String encodedPwd)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "Monitered Entity Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "methodGroup", type = "String", desc = "Method Group"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "methodName", type = "String", desc = "Method Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String[]", desc = "Properties"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "params", type = "String[]", desc = "Parameters."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encodedPwd", type = "String", desc = "Encoded Password.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns success/failure response of the invocation"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns success/failure response of the invocation",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static Object invoke(String monitoredEntityName, String entityType, String methodGroup, String methodName,
            String[] properties, String[] params, String userName, String encodedPwd ) {
		return RemoteInvoke.invoke(monitoredEntityName, entityType, methodGroup, methodName, properties, params, userName, encodedPwd);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getMethodsLayout",
		synopsis = "Gets the list of all the methods exposed for a given agent type.",
		signature = "String getMethodsLayout(String entityType)",
				params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns list of all the methods exposed for a given agent type"),
		version = "1.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns list of all the methods exposed for a given agent type",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getMethodsLayout(String entityType) {
		return EntityMethodsLayout.getMethodsLayout(entityType);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getMethodDescriptor",
		synopsis = "Gets the method descriptor.",
		signature = "String getMethodDescriptor((String entityType, String methodGroup, String methodName)",
				params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "methodGroup", type = "String", desc = "Method Group"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "methodName", type = "String", desc = "Method Name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the method descriptor"),
		version = "1.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the method descriptor",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getMethodDescriptor(String entityType, String methodGroup, String methodName) {
		return EntityMethodsDescriptor.getMethodDescriptor(entityType, methodGroup, methodName);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "startThreadAnalyzer",
        synopsis = "Starts the thread analyzer",
        signature = "String startThreadAnalyzer(String entityType, String monitoredEntityName," +
             "String[] properties, String threadReportDir, String samplingInterval," +
             "String username, String encodedPwd)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "Monitered Entity Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String[]", desc = "Properties"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "threadReportDir", type = "String", desc = "Report directory."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "samplingInterval", type = "String", desc = "Interval."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encodedPwd", type = "String", desc = "Encoded Password.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns success/failure response of starting the analyzer"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns success/failure response of starting the analyzer",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static String startThreadAnalyzer(String entityType, String monitoredEntityName,
             String[] properties, String threadReportDir, String samplingInterval,
             String username, String encodedPwd) {
		 return RemoteThreadAnalyzer.startThreadAnalyzer(entityType, monitoredEntityName, properties, threadReportDir, samplingInterval, username, encodedPwd);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "stopThreadAnalyzer",
        synopsis = "Stops the thread analyzer",
        signature = "String stopThreadAnalyzer(String entityType, String monitoredEntityName," +
             "String[] properties, String username, String encodedPwd)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "Entity Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "Monitered Entity Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String[]", desc = "Properties"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encodedPwd", type = "String", desc = "Encoded Password.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns success/failure response of stopping the analyzer"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns success/failure response of stopping the analyzer",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static String stopThreadAnalyzer (String entityType, String monitoredEntityName, String[] properties,
            String username, String encodedPwd) {
		return RemoteThreadAnalyzer.stopThreadAnalyzer(entityType, monitoredEntityName, properties, username, encodedPwd);
	}
}
