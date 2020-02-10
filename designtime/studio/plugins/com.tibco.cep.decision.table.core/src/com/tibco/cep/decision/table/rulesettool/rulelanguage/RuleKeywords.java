package com.tibco.cep.decision.table.rulesettool.rulelanguage;

import java.util.HashMap;

public class RuleKeywords implements IRuleKeywords {


	

	public String[] getKeywords() {
		return fgModuleKeywords;
	}

	private static String[] fgModuleKeywords;
	private  static String[] loaclVariables;
	@SuppressWarnings("rawtypes")
	private static HashMap attributeList = new HashMap();
	@SuppressWarnings("rawtypes")
	private static HashMap functions = new HashMap();
	
	public static void setKeywords(String[] variables) {

		RuleKeywords.fgModuleKeywords = variables;
	}

	public String[] getKeywords(int type) {
		if (type == MODULE) {
			return fgModuleKeywords;
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap getFunctions() {
		return functions;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setFunctions(HashMap functions) {
		RuleKeywords.functions = functions;
	}

	public String[] getLoaclVariables() {
		return loaclVariables;
	}

	public  void setLoaclVariables(String[] loaclVariables) {
		RuleKeywords.loaclVariables = loaclVariables;
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap getAttributeList() {
		return attributeList;
	}

	@SuppressWarnings("rawtypes")
	public static void setAttributeList(HashMap attributeList) {
		RuleKeywords.attributeList = attributeList;
	}

}
