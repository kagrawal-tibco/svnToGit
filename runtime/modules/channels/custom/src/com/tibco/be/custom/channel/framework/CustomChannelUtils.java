package com.tibco.be.custom.channel.framework;

import java.util.Map.Entry;
import java.util.Properties;

import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.DestinationConfig;

/**
 * @author vasharma
 */

public class CustomChannelUtils {

	public static void populateGVConfigForChannel(CustomChannelConfig channelConfig) {
		GlobalVariables gvs = channelConfig.getGv();
		Properties props = (Properties) channelConfig.getProperties().clone();
		for (Entry<Object, Object> entry : props.entrySet()) {
			Object value = gvs.substituteVariables(entry.getValue().toString());
			if (value != null && !value.toString().isEmpty())
				channelConfig.getProperties().setProperty(entry.getKey().toString(), value.toString());
		}
	}

	public static void populateGVConfigForDestination(DestinationConfig destConfig, GlobalVariables gvs) {
		Properties props = new Properties(destConfig.getProperties());
		for (Entry<Object, Object> entry : props.entrySet()) {
			Object value = gvs.substituteVariables(entry.getValue().toString());
			if (value != null && !value.toString().isEmpty())
				destConfig.getProperties().setProperty(entry.getKey().toString(), value.toString());
		}
	}
}
