/**
 * 
 */
package com.tibco.cep.decision.table.language;

import static com.tibco.cep.decision.table.language.DTRegexPatterns.ANY_OPERATOR_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ARRAY_INDEX_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ARRAY_STYLE_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ENDS_WITH_ARRAY;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;


/**
 * @author aathalye
 *
 */
public class ArrayParseUtil {
	
		
	/**
	 * 
	 * @param column -> Column name ex : concept.contained1[loop0].contained[loop1].property
	 * @param actualBody -> Pattern String of format [0:1]value
	 * @return
	 */
	public static String getArraySubstitutionString(Column column, 
			                                        String actualBody,
			                                        String operator) {
		String colName = column.getName().trim();
		actualBody = DTLanguageUtil.canonicalizeExpression(actualBody);
		//Check actual body
		if (ARRAY_INDEX_PATTERN.matcher(actualBody).matches()) {
			//Legal Value
			//Canonicalize it for spaces
			String[] indexTokens = getIndexTokens(actualBody);
			//Get the actual value (assignment/equality etc.) which will 
			//follow the ]
			String value = 
				actualBody.substring(actualBody.lastIndexOf(']') + 1, actualBody.length());
			if(operator != null){
				value = operator + value;
			}
			//Start parsing column info
			return massageCellValue(colName, indexTokens, value, 
					column.getColumnType() == ColumnType.CONDITION || 
					column.getColumnType() == ColumnType.CUSTOM_CONDITION);
		} 
		return null;
	}
	
	/**
	 * 
	 * @param columnName
	 * @param indexTokens
	 * @param value
	 * @param isCondition
	 * @return
	 */
	private static String massageCellValue(String columnName, 
			                               String[] indexTokens, 
			                               String value,
			                               boolean isCondition) {
		
		//Split Column Name
		String[] columnNameSplits = columnName.split("\\[\\]");
		StringBuilder stringBuilder = new StringBuilder();
		
		//How many array indexes are used in the name?
		boolean endWithArray = ENDS_WITH_ARRAY.matcher(columnName).matches();
		int splitArrayLength = 
			 (endWithArray) ? columnNameSplits.length : columnNameSplits.length - 1;
		
		/**
		 * It is possible that no index is specified in the cell value
		 * Append to each cascading array's last element.
		 * e.g : abc in which case indextokens will be empty array
		 */

		final Stack<String> expressionStack = new Stack<String>();
		
		if (indexTokens.length <= splitArrayLength) {
			StringBuilder cumulative = new StringBuilder();
			for (int loopCount = 0; loopCount < splitArrayLength; loopCount++) {
				//Peek at the item in the stack
				String pop = (expressionStack.isEmpty()) ? "" : expressionStack.pop();
				if (indexTokens.length > loopCount) {
					cumulative.append(indexTokens[loopCount]);
				}  else {
					cumulative.append(pop);
					cumulative.append(columnNameSplits[loopCount]);
					cumulative.append("@length - 1");
				}
				push(expressionStack, pop, columnNameSplits[loopCount], cumulative.toString());
				cumulative.delete(0, cumulative.capacity());
			}
		}
		
		CharSequence finalArrayExpression = expressionStack.pop();
		//DecisionTableCorePlugin.debug(CLASS, "Massaged array column name {0}", finalArrayExpression);
		stringBuilder.append(finalArrayExpression);
		
		/**
		 * Handle case where lesser array arguments exist.
		 * e.g :
		 * 1.) a.b[].int = 10
		 * 2.) a.b[].c.int = 20 etc.
		 */
		if (splitArrayLength < columnNameSplits.length) {
			for (int loop = splitArrayLength; loop < columnNameSplits.length; loop++) {
				stringBuilder.append(columnNameSplits[loop]);
			}
		}
		
		if (isCondition) {
			//Check if any operator exists
			if (!ANY_OPERATOR_PATTERN.matcher(value).matches()) {
				value = new StringBuilder(" ").
				            append(DTLanguageUtil.COND_OPERATORS.EQUALS.opString()).
				            append(" ").
				            append(value).
				            toString();
			}
		} else {
			stringBuilder.append(" = ");
		}
		stringBuilder.append(value);
		return stringBuilder.toString();
	}
	
	/**
	 * Push each massaged expression on the stack.
	 * The stack will contain only one element which
	 * will be the final expression after the last iteration.
	 * @param expressionStack
	 * @param poppedStackValue
	 * @param columnSplit
	 * @param massagedExpression
	 */
	private static void push(Stack<String> expressionStack, 
							 String poppedStackValue,
			                 String columnSplit,
			                 String massagedExpression) {
		StringBuilder stringBuilder = new StringBuilder(poppedStackValue);
		stringBuilder.append(columnSplit);
		stringBuilder.append("[");
		stringBuilder.append(massagedExpression);
		stringBuilder.append("]");
		String stackExpression = stringBuilder.toString();
		//DecisionTableCorePlugin.debug(CLASS, "Expression {0} pushed on stack", stackExpression);
		expressionStack.push(stackExpression);
	}
	
	/**
	 * 
	 * @param canonicalizedBody
	 * @return
	 */
	private static String[] getIndexTokens(String canonicalizedBody) {
		//Split on basis of :
		int startIndex = canonicalizedBody.indexOf('[');
		int endIndex = canonicalizedBody.lastIndexOf(']');
		if (startIndex == -1 || endIndex == -1) {
			return new String[]{};
		}
		StringTokenizer sToken = 
			new StringTokenizer(canonicalizedBody.substring(startIndex + 1, endIndex), ":");
		List<String> indexTokens = new ArrayList<String>();
		
		while (sToken.hasMoreTokens()) {
			indexTokens.add(sToken.nextToken());
		}
		return indexTokens.toArray(new String[indexTokens.size()]);
	}
	
	/**
	 * 
	 * @param columnName
	 * @return
	 */
	public static boolean isArrayAccessColumn(String columnName) {
		return ARRAY_STYLE_PATTERN.matcher(columnName).matches();
	}
}
