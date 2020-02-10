/**
 * 
 */
package com.tibco.cep.decisionproject.util;

import com.tibco.cep.decision.table.model.dtmodel.MetaData;


/**
 * @author aathalye
 * @author rmishra
 * 
 * 
 */
public class DecisionProjectUtil {
	public static String DIRECTORY_FILE_PATH = null;
	
	public static final String SEPARATOR = "|| == ";
	
	/*
	 * private static final PluginLoggerImpl TRACE = LoggerRegistry
	 * .getLogger(PluginIds.PLUGIN_DECISION_PROJECT);
	 */

	/**
	 * This class object is not intended to be instantiated by client
	 */
	DecisionProjectUtil() {
	}

	public static String getMetaDataValue(MetaData met, String propName) {
		return getMetaDataValue(met, propName, null);
	}
	public static String getMetaDataValue(MetaData met, String propName, String def) {
		if(met != null) {
			com.tibco.cep.decision.table.model.dtmodel.Property p = met.search(propName);
			if(p != null) {
				return p.getValue();
			}
		}
		return def;
	}
}
