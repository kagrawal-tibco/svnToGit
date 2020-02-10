package com.tibco.cep.studio.decision.table.utils;

import java.util.StringTokenizer;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
/**
 * These methods used to be in DecisionTableUtil but the needed to be 
 * accessed outside of the decision table plugin
 * @author aamaya
 *
 */
public class ExpressionBodyUtil {
	public static final String DELIMITER = ";";

	public static String getOperator(String cellValue) {
		//int leastIndex = Integer.MAX_VALUE;
		//String op = null;
		for (String _op : DTLanguageUtil.OPERATORS) {
			// commented because if condition is , 10 || < 30 
			// it returns operator as "<" instead of "="
			/*
			int opIndex = cellValue.indexOf(_op);
			if (opIndex != -1 && (opIndex < leastIndex || op == null)) {
				op = _op;
				leastIndex = opIndex;
			}
			*/
			if (cellValue.trim().startsWith(_op)){
				return _op;
			}
		}
		
		//if(op != null) return op;
		/*
		if ((cellValue.indexOf("(") != -1) && (cellValue.indexOf(")") != -1)) {
			return null;
		}
		*/
	
		return "=";
	
	}

	public static String getActualValue(String operator, String cellValue) {
		if (operator != null) {
			if (cellValue.indexOf('=') == -1 && "=".equals(operator)) {
				return cellValue;
			}
			return cellValue.substring(
					cellValue.indexOf(operator) + operator.length()).trim();
		}
	
		else
			return cellValue;
	
	}

	public static String getRuleVariableConverterExpBody(String body, boolean _textAliasVisible, String alias) {
		String cellValue = "";
		StringTokenizer st = new StringTokenizer(body , DELIMITER);
		int tokenCounter = 1;
		int tokenCount = st.countTokens();
		while (st.hasMoreTokens()){
			String token = st.nextToken().trim();
			String operator = getOperator(token);
			if (_textAliasVisible && !token.startsWith(alias)){
				if ("=".equals(operator)){
					cellValue = cellValue + alias + operator + token;
				}
				else {
					cellValue = cellValue + alias + token;
				}
			}
			else {
				cellValue = cellValue + token;
			}
			if (tokenCounter < tokenCount){
				cellValue = cellValue + DELIMITER + " ";
			}
			tokenCounter ++;
		}
	
	    //return body.trim();
		return cellValue;
	}
	
	
}
