package com.jidesoft.decision.cell.editors.utils;


import static com.tibco.cep.decision.table.language.DTLanguageUtil.OPERATORS;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.AND_OR_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.STAR;
import static com.tibco.cep.decision.table.utils.DecisionTableUtil.getFormattedString;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.ASTERISK;

import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * All cell editor utilty methods to be added here to avoid cluttering code there.
 * @author aathalye
 *
 */
public class CellEditorUtils {
	
	private static final String CLASS = CellEditorUtils.class.getName();
	

	/**
	 * Determine if an expression begins with {@link DTLanguageUtil#ASTERISK}
	 * and also has something after it.
	 * <p>
	 * Valid value is *.
	 * </p>
	 * <p>
	 * Invalid value <b>* || abc</b>, <b>* && abc</b> etc.
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isAsterixedExpression(String value) {
		if (value != null) {
			return value.startsWith(ASTERISK) ^ STAR.matcher(value).matches();
		}
		return false;
	}
	
	/**
	 * TODO Handle undo case.
	 * @param rawValue
	 * @param projectName
	 * @param column
	 * @return
	 */
	public static String massageCellEditorRawValue(String rawValue, 
			                                       String projectName, 
			                                       Column column) {
		String massagedValue = (column.isSubstitution()) ? getFormattedString(column, rawValue) : massageRawString(rawValue, column);
		return massagedValue;
	}
	
	/**
	 * 
	 * @param rawValue
	 * @param column
	 * @return
	 */
	public static String massageRawString(String rawValue, Column column) {
		if( rawValue == null) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		//The column name generally that preceeds the actual expression.
		String expressionAlias = column.getName();
		Scanner expressionScanner = new Scanner(rawValue).useDelimiter(AND_OR_PATTERN);
		
		while (expressionScanner.hasNext()) {
			String token = expressionScanner.next().trim();
			//Try to locate the delimiter itself as well.
			String delimiter = expressionScanner.findWithinHorizon(AND_OR_PATTERN, 0);
			//Case when the token contains alias also. 
			if (token.indexOf(expressionAlias) != -1) {
				token = getActualValueFromToken(token, expressionAlias);
				DecisionTableUIPlugin.debug(CLASS, "Token obtained from raw value {0} is {1}", rawValue, token);
			} else {
				//Condition case ==
				if (token.startsWith(OPERATORS[0])) {
					token = token.substring(OPERATORS[0].length()).trim();
				} //Action case =
				else if (token.startsWith(OPERATORS[7])) {
					token = token.substring(OPERATORS[7].length()).trim();
				}
			}
			stringBuilder.append(token);
			if (delimiter != null) {
				stringBuilder.append(delimiter);
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static DecisionTableModel getTableModel(JTable mainTable) {
		//Get tableModel associated with it
		TableModel filterableTableModel = mainTable.getModel();
    	//Get inner model
    	DecisionTableModel decisionTableModel = 
    		(DecisionTableModel)TableModelWrapperUtils.getActualTableModel(filterableTableModel);
    	return decisionTableModel;
	}
	
	/**
	 * 
	 * @param mainTable
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public static DecisionField getDecisionFieldAt(JTable mainTable, int rowIndex, int columnIndex) {
		DecisionTableModel decisionTableModel = getTableModel(mainTable);
    	//Get field at this selected row and column. 
    	DecisionField decisionField = decisionTableModel.getDecisionFieldAt(rowIndex, columnIndex);
    	return decisionField;
	}
	
	/**
	 * 
	 * @param token
	 * @param alias
	 * @return
	 */
	private static String getActualValueFromToken(String token, String alias) {
		//Split it with "&&" as pattern
		String str1 = organizeValue(token, COND_OPERATORS.AND.opString(), alias);
		//Split it with "||" as pattern
		if (str1.indexOf(COND_OPERATORS.OR.opString()) != -1) {
			return organizeValue(str1, COND_OPERATORS.OR.opString(), alias);
		}
		return str1;
	}	
	
	/**
	 * TODO improve this logic
	 * @param token
	 * @param operator
	 * @param alias
	 * @return
	 */
	private static String organizeValue(String token, String operator, String alias) {
		int counter = 1;
		StringBuilder sb = new StringBuilder();
		String[] splitString;
		if (COND_OPERATORS.OR.opString().equals(operator)) {
			splitString = token.split("\\|\\|");
		} else {
			splitString = token.split(operator);
		}
		for (String str : splitString) {
			str = str.trim();
			if (str.startsWith(alias)) {
				str = str.substring(alias.length());
				str = str.trim();
				if (str.startsWith(COND_OPERATORS.EQUALS.opString())) {
					str = str.substring(COND_OPERATORS.EQUALS.opString().length()).trim();
				} else if (str.startsWith(COND_OPERATORS.ASSIGNMNT.opString())) {
					str = str.substring(COND_OPERATORS.ASSIGNMNT.opString().length()).trim();
				}

			} else {
				if (str.startsWith(COND_OPERATORS.EQUALS.opString())) {
					str = str.substring(COND_OPERATORS.EQUALS.opString().length()).trim();
				} else if (str.startsWith(COND_OPERATORS.ASSIGNMNT.opString())) {
					str = str.substring(COND_OPERATORS.ASSIGNMNT.opString().length()).trim();
				}
			}
			if (counter < splitString.length) {
				sb.append(str).append(operator);
			} else {
				sb.append(str);
			}
			counter++;
		}
		return sb.toString();
	}
}
