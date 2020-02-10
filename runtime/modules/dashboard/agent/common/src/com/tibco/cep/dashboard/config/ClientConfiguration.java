package com.tibco.cep.dashboard.config;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.cep_dashboardVersion;
import com.tibco.cep.dashboard.common.utils.ArrayUtil;



public class ClientConfiguration {

	private static final String CLIENT_CONFIG_XML_HEADER =
			"<!-- \n" +
			"	This file is auto-generated every time dashboard agent starts\n" +
			"	All modifications to the file will be lost\n" +
			"	Please update the appropriate cdd file to reflect changes needed in this file\n" +
			"   Created On {0} by {1}\n" +
			"-->\n";

	private static ClientConfiguration instance;

	public static final synchronized ClientConfiguration getInstance() {
		if (instance == null) {
			instance = new ClientConfiguration();
		}
		return instance;
	}

	private Map<String,String[]> configuration;

	private ClientConfiguration() {
		configuration = new HashMap<String, String[]>();
	}

	public void addConfigurationValue(String name,String value){
		String[] values = configuration.get(name);
		if (values == null){
			values = new String[]{value};
			configuration.put(name,values);
		}
		values = ArrayUtil.expandArray(values, values.length+1);
		values[values.length-1] = value;
	}

	public Iterator<String> getNames(){
		return configuration.keySet().iterator();
	}

	public String[] getValues(String name){
		return configuration.get(name);
	}

	public String toXML(boolean prettyPrint){
		StringBuilder sb = new StringBuilder();
		if (prettyPrint == true) {
			//append header
			sb.append(MessageFormat.format(CLIENT_CONFIG_XML_HEADER, new Date(), cep_dashboardVersion.getComponent() + " " + cep_dashboardVersion.getVersion()));
			sb.append('\n');
		}
		sb.append("<configuration>");
		if (prettyPrint == true){
			sb.append('\n');
		}
		Iterator<String> names = getNames();
		while (names.hasNext()) {
			String name = (String) names.next();
			String[] values = getValues(name);
			for (String value : values) {
				if (prettyPrint == true){
					sb.append("    ");
				}
				sb.append("<"+name+">");
				sb.append(value);
				sb.append("</"+name+">");
				if (prettyPrint == true){
					sb.append('\n');
				}

			}
		}
		sb.append("</configuration>");
		return sb.toString();
	}
}
