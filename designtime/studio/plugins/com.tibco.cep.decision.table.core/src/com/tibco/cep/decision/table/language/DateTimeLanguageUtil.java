package com.tibco.cep.decision.table.language;

import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.EQUALS;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.GT;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.LT;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.OR;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ARRAY_BRACKETS_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.A_FUNCTION_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.DATETIME_LITERAL_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.NULL_LITERAL_PATTERN;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS;
import com.tibco.cep.decision.table.language.DateToken.DateTokenType;



public class DateTimeLanguageUtil {

	public static final String WHITESPACE = " ";

	public static final String DATE_TIME_PREFIX = "DateTime.parseString(\"";

	public static final String DATE_TIME_BEFORE_PREFIX = "DateTime.before(";

	public static final String DATE_TIME_EQUALS_PREFIX = "DateTime.equals(";

	public static final String DATE_TIME_NOTEQUALS_PREFIX = "DateTime.notEquals(";

	public static final String DATE_TIME_AFTER_PREFIX = "DateTime.after(";

	public static final String UTC_FORMAT = "\"yyyy-MM-dd'T'HH:mm:ss\"";

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private static final Pattern DELIMITER_PATTERN = Pattern.compile("[!><[==]\\|\\&](>)?(\\&)?(\\|)?(=)?");



	/**
	 * Massage the date expression
	 * @param columnAlias
	 * @param expression
	 * @param condition
	 * @return
	 */
	public static StringBuilder massageDateExpression(String columnAlias,
			String expression,
			boolean condition) {
		if (expression == null) {
			return null;
		}
		StringBuilder massagedExpression = new StringBuilder();
		massageDateExpression(massagedExpression, columnAlias, expression, condition);
		return massagedExpression;
	}

	private static List<DateToken> scanExpression(String expression, boolean isCondition) {
		List<DateToken> expressionTokens = new LinkedList<DateToken>();
		Scanner expressionScanner = new Scanner(expression).useDelimiter(DELIMITER_PATTERN);

		//Scan for literals/function calls
		Scanner funcLiteralScanner = null;
		while (expressionScanner.hasNext()) {
			String operatorInHorizon = expressionScanner.findWithinHorizon(DELIMITER_PATTERN, 0);
			if (operatorInHorizon != null) {
				COND_OPERATORS op = COND_OPERATORS.getByOpString(operatorInHorizon);
				if (op != null) {
					DateToken operatorToken = new DateToken(DateTokenType.OPERATOR_TYPE, operatorInHorizon);
					expressionTokens.add(operatorToken);
				}
			} else {
				DateToken operatorToken = null;
				if (isCondition) {
					operatorToken = new DateToken(DateTokenType.OPERATOR_TYPE, COND_OPERATORS.EQUALS.opString());
				} else {
					//This is required so that consistency of operator is maintained.
					operatorToken = new DateToken(DateTokenType.OPERATOR_TYPE, COND_OPERATORS.ASSIGNMNT.opString());
				}
				expressionTokens.add(operatorToken);
			}
			String temp = expressionScanner.next();
			funcLiteralScanner = new Scanner(temp);
			DateToken dateToken = null;
			String token = funcLiteralScanner.findInLine(DATETIME_LITERAL_PATTERN);
			if (token != null) {
				String[] tokenSplit = token.split(ARRAY_BRACKETS_PATTERN.pattern());
				token = tokenSplit[tokenSplit.length - 1];
				dateToken = new DateToken(DateTokenType.LITERAL_TYPE, token);
			} else {
				token = funcLiteralScanner.findInLine(NULL_LITERAL_PATTERN);
				if (token != null) {
					String[] tokenSplit = token.split(ARRAY_BRACKETS_PATTERN.pattern());
					token = tokenSplit[tokenSplit.length - 1];
					dateToken = new DateToken(DateTokenType.NULL_TYPE, token);
				} else {
					token = funcLiteralScanner.findInLine(A_FUNCTION_PATTERN);
					if (token != null) {
						String[] tokenSplit = token.split(ARRAY_BRACKETS_PATTERN.pattern());
						token = tokenSplit[tokenSplit.length - 1];
						dateToken = new DateToken(DateTokenType.FUNCTION_TYPE, token);
					} else {
						//Could be anything else .e.g : DateTime of some other argument
						/**
						 * Use entire expression as is and make it a miscellaneous.
						 */
						 dateToken = new DateToken(DateTokenType.MISCELLANEOUS_TYPE, temp);
					}
				}
			}
			if (dateToken != null) {
				expressionTokens.add(dateToken);
			}
		}
		return expressionTokens;
	}

	private static void massageDateExpression(StringBuilder massagedExpression,
			String columnAlias,
			String fragment,
			boolean condition) {
		
		if (fragment.indexOf(OR.opString()) != -1) {
			String[] nodes = fragment.split("\\|\\|");
			for (int loop = 0, length = nodes.length; loop < length; loop++) {
				massageDateExpression(massagedExpression, columnAlias, nodes[loop], condition);
				massagedExpression.append(" ");
				if (loop != length - 1) {
					massagedExpression.append(OR.opString());
				}
				massagedExpression.append(" ");
			}
			return;
		}
		
		List<DateToken> dateTokens = scanExpression(fragment.trim(), condition);
		ListIterator<DateToken> listIterator = dateTokens.listIterator();

		while (listIterator.hasNext()) {
			DateToken dateToken = listIterator.next();
			//Get token type
			DateTokenType tokenType = dateToken.getTokenType();
			String tokenExpression = dateToken.getTokenExpression();
			switch (tokenType) {
			case OPERATOR_TYPE : {
				//Get the operator
				COND_OPERATORS operator = COND_OPERATORS.getByOpString(tokenExpression);
				switch (operator) {
				case GT :
					massageOperators(massagedExpression,
							columnAlias,
							DATE_TIME_AFTER_PREFIX);
					break;
				case LT :
					massageOperators(massagedExpression,
							columnAlias,
							DATE_TIME_BEFORE_PREFIX);
					break;
				case GTE :
					splitExpression(massagedExpression,
							columnAlias,
							listIterator.next(),
							condition,
							GT);
					break;
				case LTE :
					splitExpression(massagedExpression,
							columnAlias,
							listIterator.next(),
							condition,
							LT);
					break;
				case NE2 :     
				case NE :
					massageOperators(massagedExpression,
							columnAlias,
							DATE_TIME_NOTEQUALS_PREFIX);
					break;
				case EQUALS :
					massageOperators(massagedExpression,
							columnAlias,
							DATE_TIME_EQUALS_PREFIX);
					break;
				case ASSIGNMNT :
					//Need to append this to compensate for the ) coming later.
					massagedExpression.append("(");
					break;

				}
				break;
			}
			case LITERAL_TYPE : {
				massagedExpression.append(DATE_TIME_PREFIX).
				append(tokenExpression.trim()).
				append("\", ").
				append(UTC_FORMAT).
				append(")");
				massagedExpression.append(")");
				break;
			}
			case MISCELLANEOUS_TYPE : {
				//Handle same as function.
			}
			case NULL_TYPE : {
				//Handle same as function.
			}
			case FUNCTION_TYPE : {
				massagedExpression.append(" ");
				massagedExpression.append(tokenExpression);
				massagedExpression.append(")");
				break;
			}
			}
		}
	}

	/**
	 * Split expressions like >= ... or <= ...
	 * into equivalent expressions like > ... || == ...
	 * @param massagedExpression
	 * @param columnAlias
	 * @param expressionToken
	 * @param condition
	 * @param operator
	 */
	private static  void splitExpression(StringBuilder massagedExpression,
			String columnAlias,
			DateToken expressionToken,
			boolean condition,
			COND_OPERATORS operator) {
		/**
		 * The function expression or literal
		 */
		String expression = expressionToken.getTokenExpression();
		//Create 2 expressions
		StringBuilder splitExpression = new StringBuilder();
		splitExpression.append(operator.opString());
		splitExpression.append(WHITESPACE);
		splitExpression.append(expression);

		massagedExpression.append("(");
		massageDateExpression(massagedExpression,
				columnAlias,
				splitExpression.toString(),
				condition);

		massagedExpression.append(WHITESPACE);
		massagedExpression.append(OR.opString());
		splitExpression.append(WHITESPACE);

		splitExpression.delete(0, splitExpression.capacity());

		splitExpression.append(EQUALS.opString());
		splitExpression.append(WHITESPACE);
		splitExpression.append(expression);

		massageDateExpression(massagedExpression,
				columnAlias,
				splitExpression.toString(),
				condition);
		massagedExpression.append(")");
	}

	private static void massageOperators(StringBuilder massagedExpression,
			String columnAlias,
			String PREFIX) {
		massagedExpression.append(PREFIX);
		massagedExpression.append(columnAlias);
		massagedExpression.append(",");
		massagedExpression.append(" ");
	}
}
