package com.tibco.cep.functions.channel.hawk;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @.category Hawk
 * @.synopsis Hawk Functions
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Hawk",
        category = "Hawk",
        synopsis = "Functions to operate on Hawk Channel.")

public class HawkChannelFunctions {

	@com.tibco.be.model.functions.BEFunction(
			name = "setDefaultHawkChannel",
			synopsis = "Get hawk Channel's properties and initialize the hawkConsoleBase",
			signature = "void setDefaultHawkChannel(String channelURI)",
			params = { @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "channelURI",
					type = "String",
					desc = "Channel's URI") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get hawk Channel's properties and initialize the hawkConsoleBase",
			cautions = "none",
			fndomain = {ACTION, CONDITION},
			example = "")
	public static void setDefaultHawkChannel(String channelURI) {
		HawkChannelFunctionsImpl.setDefaultHawkChannel(channelURI);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getAllAgentNames",
			synopsis = "Get name of all available TIBCO Hawk agents.",
			signature = "String[] getAllAgentNames()",
			params = {},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "String[]",
					desc = "Array of Hawk Agents' name"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get all TIBCO Hawk agent names.",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static String[] getAllAgentNames() {
		return HawkChannelFunctionsImpl.getAllAgentNames();
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getAllMicroAgentInfo",
			synopsis = "Get all microagent of a special agent",
			signature = "Object[] getAllMicroAgentInfo(String agentName)",
			params = { @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "agentName",
					type = "String",
					desc = "Agent's name") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "Object[]",
					desc = "All available Microagents' name list. Notice:The first row is the name array of columns, the second row is the display name array of columns, the following rows are actual data."),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get all microagent of a special agent",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static Object[] getAllMicroAgentInfo(String agentName) {
		return HawkChannelFunctionsImpl.getAllMicroAgentInfo(agentName);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getAllMicroAgentMethodInfo",
			synopsis = "Get all microagent methods information",
			signature = "Object[] getAllMicroAgentMethodInfo(String agentName, String microAgentName)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "agentName",
							type = "String",
							desc = "Agent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "microAgentName",
							type = "String",
							desc = "Microagent's name") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "Object[]",
					desc = "All available Microagent Methods' name list. Notice:The first row is the name array of columns, the second row is the display name array of columns, the following rows are actual data."),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get all microagent methods information",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static Object[] getAllMicroAgentMethodInfo(String agentName, String microAgentName) {
		return HawkChannelFunctionsImpl.getAllMicroAgentMethodInfo(agentName, microAgentName);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "describeHawkMicroAgentsMethod",
			synopsis = "Get the descripition of microagent method",
			signature = "String describeHawkMicroAgentsMethod(String agentName, String microAgentName, String methodName)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "agentName",
							type = "String",
							desc = "Agent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "microAgentName",
							type = "String",
							desc = "Microagent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "methodName",
							type = "String",
							desc = "Method name") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "String",
					desc = "Description of Microagent method"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get the descripition of microagent method",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static String describeHawkMicroAgentsMethod(String agentName, String microAgentName, String methodName) {
		return HawkChannelFunctionsImpl.describeHawkMicroAgentsMethod(agentName, microAgentName, methodName);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "invokeHawkMethod",
			synopsis = "Invoke microagent method on a certain agent",
			signature = "Object[] invokeHawkMethod(String agentName, String microAgentName, String methodName, SimpleEvent params)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "agentName",
							type = "String",
							desc = "Agent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "microAgentName",
							type = "String",
							desc = "Microagent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "methodName",
							type = "String",
							desc = "Method name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "params",
							type = "SimpleEvent",
							desc = "Paramters for method invocation") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "Object[]",
					desc = "Result of Microagent Method invocation. Notice:The first row is the name array of columns, the second row is the display name array of columns, the following rows are actual data."),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Generic method invoke, using event to carry all the parameters",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static Object[] invokeHawkMethod(String agentName, String microAgentName, String methodName,
			SimpleEvent params) {
		return HawkChannelFunctionsImpl.invokeHawkMethod(agentName, microAgentName, methodName, params);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "invokeHawkMethodOnMutipleAgents",
			synopsis = "Invoke microagent method on mutiple agents",
			signature = "Object[] invokeHawkMethodOnMutipleAgents(String[] agentNames, String microAgentName,String methodName, SimpleEvent params)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "agentNames",
							type = "String[]",
							desc = "Agents name list"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "microAgentName",
							type = "String",
							desc = "Microagent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "methodName",
							type = "String",
							desc = "Method name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "params",
							type = "SimpleEvent",
							desc = "Paramters for method invocation") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "Object[]",
					desc = "Result of Microagent Method invocation on multiple agents. Notice: Result contain 2 parts, first part is the name of agent, second part is the result of Microagent Method invocation on this agent"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Invoke microagent method on mutiple agents",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static Object[] invokeHawkMethodOnMutipleAgents(String[] agentNames, String microAgentName,
			String methodName, SimpleEvent params) {
		return HawkChannelFunctionsImpl.invokeHawkMethodOnMutipleAgents(agentNames, microAgentName, methodName, params);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "invokeHawkMethodOnAllAgents",
			synopsis = "Invoke microagent method on all available agents",
			signature = "Object[] invokeHawkMethodOnAllAgents(String microAgentName, String methodName, SimpleEvent params)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "microAgentName",
							type = "String",
							desc = "Microagent's name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "methodName",
							type = "String",
							desc = "Method name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(
							name = "params",
							type = "SimpleEvent",
							desc = "Paramters for method invocation") },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "",
					type = "Object[]",
					desc = "Result of Microagent Method invocation on all agents. Notice: Result contain 2 parts, first part is the name of agent, second part is the result of Microagent Method invocation on this agent"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Invoke microagent method on all available agents",
			cautions = "none",
			fndomain = {ACTION, BUI},
			example = "")
	public static Object[] invokeHawkMethodOnAllAgents(String microAgentName, String methodName, SimpleEvent params) {
		return HawkChannelFunctionsImpl.invokeHawkMethodOnAllAgents(microAgentName, methodName, params);
	}

}
