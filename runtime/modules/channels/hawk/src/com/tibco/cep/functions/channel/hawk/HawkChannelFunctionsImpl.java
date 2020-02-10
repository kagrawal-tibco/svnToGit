package com.tibco.cep.functions.channel.hawk;

import COM.TIBCO.hawk.talon.DataElement;

import com.tibco.cep.driver.hawk.HawkChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;
import com.tibco.hawk.jshma.util.NamedArray;
import com.tibco.hawk.jshma.util.NamedTabularData;

public class HawkChannelFunctionsImpl {
	public static HawkConsoleBase hawkConsoleBase;

	/**
	 * Get hawk Channel's properties and initialize the hawkConsoleBase
	 * 
	 * @param channelURI
	 */
	public static void setDefaultHawkChannel(String channelURI) {
		if (hawkConsoleBase == null) {
			HawkChannel hawkChannel = (HawkChannel) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()
					.getChannelManager().getChannel(channelURI);
			hawkConsoleBase = hawkChannel.getHawkConsoleBase();
		}
	}

	/**
	 * Get all TIBCO Hawk agent names.
	 * 
	 * @return agents' name array
	 */
	public static String[] getAllAgentNames() {
		return hawkConsoleBase.getAllAgentNames();
	}

	/**
	 * Get all microagent of a special agent
	 * 
	 * @param agentName
	 * @return
	 */
	public static Object[] getAllMicroAgentInfo(String agentName) {

		return transformNamedTabularData(hawkConsoleBase.getAllMicroAgentInfo(agentName));

	}

	public static Object[] getAllMicroAgentMethodInfo(String agentName, String microAgentName) {

		return transformNamedTabularData(hawkConsoleBase.getAllMethodInfo(agentName, microAgentName));

	}

	/**
	 * Get the descripition of microagent method
	 * 
	 * @param agentName
	 * @param microAgentName
	 * @param methodName
	 * @return
	 */
	public static String describeHawkMicroAgentsMethod(String agentName, String microAgentName, String methodName) {
		if (hawkConsoleBase.getMethodDescriptor(agentName, microAgentName, methodName) != null) {
			return hawkConsoleBase.getMethodDescriptor(agentName, microAgentName, methodName).getDescription();
		} else {
			return null;
		}

	}

	/**
	 * Genaric method invoke, using event to carry all the parameters
	 * 
	 * @param agentName
	 * @param microAgentName
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Object[] invokeHawkMethod(String agentName, String microAgentName, String methodName,
			SimpleEvent params) {
		DataElement[] args = null;
		if (params != null) {
			String paramNames[] = params.getPropertyNames();
			args = new DataElement[paramNames.length];

			for (int i = 0; i < args.length; i++) {
				try {
					args[i] = new DataElement(paramNames[i], params.getPropertyValue(paramNames[i]));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}
		return invoke(agentName, microAgentName, methodName, args);
	}

	/**
	 * Invoke microagent methdod on all agents.
	 * 
	 * @param microAgentName
	 * @param methodName
	 * @param params
	 * @return Map of agentName and invocation result
	 */
	public static Object[] invokeHawkMethodOnAllAgents(String microAgentName, String methodName, SimpleEvent params) {

		String[] agentNames = getAllAgentNames();
		return invokeHawkMethodOnMutipleAgents(agentNames, microAgentName, methodName, params);
	}

	/**
	 * Invoke microagent methdod on multiple agents.
	 * 
	 * @param agentNames
	 *            name list of agent
	 * @param microAgentName
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Object[] invokeHawkMethodOnMutipleAgents(String[] agentNames, String microAgentName,
			String methodName, SimpleEvent params) {
		if (agentNames != null && agentNames.length > 0) {
			Object[][] resultList = new Object[agentNames.length][2];

			for (int i = 0; i < agentNames.length; i++) {
				resultList[i][0] = agentNames[i];
				resultList[i][1] = invokeHawkMethod(agentNames[i], microAgentName, methodName, params);
			}
			return resultList;
		}
		return null;
	}

	private static Object[] invoke(String agentName, String microAgentName, String methodName, DataElement[] args) {
		NamedArray inputParameters = null;
		if (args != null && args.length > 0) {
			String[] nameList = new String[args.length];
			Object[] data = new Object[args.length];
			for (int i = 0; i < data.length; i++) {
				// since BE does't support parameter name with blank space, need to transform here.
				nameList[i] = args[i].getName().replace("_", " ");
				data[i] = args[i].getValue();
			}
			inputParameters = new NamedArray(nameList, data);
		}

		Object[] finalResult = null;
		try {
			NamedTabularData result = hawkConsoleBase.invokeHawkMethod(agentName, microAgentName, methodName,
					inputParameters);

			finalResult = transformNamedTabularData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return finalResult;
	}

	// the first and sencond row is the name and display name of column, the
	// follow row is data.
	private static Object[] transformNamedTabularData(NamedTabularData result) {
		if (result != null && result.rowCount > 0) {
			String[] columnNames = result.getColumnNames();
			String[] columnDispalyName = result.getColumnDisplayNames();
			Object[][] resultData = result.getData();

			Object[] finalResult = new Object[result.rowCount + 2];
			finalResult[0] = columnNames;
			finalResult[1] = columnDispalyName;
			for (int i = 0; i < finalResult.length - 2; i++) {
				finalResult[i + 2] = resultData[i];
			}
			return finalResult;
		} else {
			return null;
		}
	}

}
