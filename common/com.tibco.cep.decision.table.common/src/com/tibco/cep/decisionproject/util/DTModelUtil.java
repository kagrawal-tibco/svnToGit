package com.tibco.cep.decisionproject.util;

import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;

public class DTModelUtil {
	
	public static int getRowPriority(TableRule rule) {
		MetaData met = null;
		if(rule != null) {
			met = rule.getMd();
		}
		return getIntProp(met, DTConstants.TABLE_RULE_PRIORITY, DTConstants.TABLE_RULE_PRIORITY_DEF);
	}
	
	public static int getTablePriority(Table table) {
		MetaData met = null;
		if(table != null) {
			met = table.getMd();
		}
		return getIntProp(met, DTConstants.TABLE_PRIORITY, DTConstants.TABLE_PRIORITY_DEF);
	}
	
	public static boolean getOneRowExec(Table table) {
		MetaData met = null;
		if(table != null) {
			met = table.getMd();
		}
		return getBooleanProp(met, DTConstants.TABLE_SINGLE_ROW_EXECUTION, DTConstants.TABLE_SINGLE_ROW_EXECUTION_DEF);
	}
	
	private static boolean getBooleanProp(MetaData met, String propName, boolean def) {
		String value = getStringProp(met, propName);
		if(value != null) {
			return Boolean.parseBoolean(value);
		}
		return def;
	}
	
	private static int getIntProp(MetaData met, String propName, int def) {
		String value = getStringProp(met, propName);
		if(value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException nfe) {}
		}
		return def;
	}
	
	private static String getStringProp(MetaData met, String propName) {
		return getStringProp(met, propName, null);
	}
	private static String getStringProp(MetaData met, String propName, String def) {
		String value = DecisionProjectUtil.getMetaDataValue(met, propName, def);
		if(value == null) value = def;
		return value;
	}
}
