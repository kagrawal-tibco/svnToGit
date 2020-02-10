package com.tibco.cep.decision.table.language;

import static com.tibco.cep.decision.table.language.ArrayParseUtil.getArraySubstitutionString;
import static com.tibco.cep.decision.table.language.ArrayParseUtil.isArrayAccessColumn;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.AND;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.EQUALS;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.OR;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ANY_OPERATOR_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ENDS_WITH_SEMICOLON;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.STAR;
import static com.tibco.cep.decision.table.language.DateTimeLanguageUtil.DATE_TIME_PREFIX;
import static com.tibco.cep.decision.table.language.DateTimeLanguageUtil.UTC_FORMAT;
import static com.tibco.cep.decision.table.language.DateTimeLanguageUtil.massageDateExpression;

import java.util.List;
import java.util.StringTokenizer;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.utils.ExpressionBodyUtil;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.model.element.PropertyDefinition;

public class DTLanguageUtil {
	
	public final static String[] OPERATORS = new String[]{"==", ">=", "<=", "<>", "!=", ">", "<", "="};
	
	public enum COND_OPERATORS {
		OR("||"), AND("&&"), EQUALS("=="), GT(">"), LT("<"), GTE(">="), LTE("<="), NE("!="), NE2("<>"), ASSIGNMNT("=");
		
		private final String opString;
		
		private COND_OPERATORS(String value) {
			this.opString = value;
		}
		
		public String opString() {
			return opString;
		}
		
		public static String ASTERISK = "*";
		
		private static final COND_OPERATORS[] VALUES_ARRAY =
			new COND_OPERATORS[] {
				OR,
				AND,
				EQUALS,
				GT,
				LT,
				GTE,
				LTE,
				NE,
				NE2,
				ASSIGNMNT
			};
		
		public static COND_OPERATORS getByOpString(String opString) {
			for (int i = 0; i < VALUES_ARRAY.length; ++i) {
				COND_OPERATORS result = VALUES_ARRAY[i];
				if (result.opString.equals(opString)) {
					return result;
				}
			}
			return null;
		}
	}
	
	public static boolean isStar(String alias, String expBody) {
		if (expBody == null || alias == null) return false;
		CellTuple ct = new CellTuple(alias, expBody);
		if (ct.body != null) return STAR.matcher(ct.body).matches();
		return false;
	}
	
	public static String generateCustomConditionBody(String alias, String expBody) {
		return expBody;
	}
	
	
	/**
	 * Generate body for non-custom condition.
	 * Includes support for arrays also.
	 * @param ctx
	 * @param expBody
	 * @return
	 */
	public static String generateNonCustomConditionBody(DTCellCompilationContext ctx, 
			                                            String expBody) {
		if (expBody == null) {
			return null;
		}
		
		String expBodyPrefix = "";
		if (ArrayParseUtil.isArrayAccessColumn(ctx.col.getName())) {
			expBodyPrefix = getArrayIndexExpression(expBody);
			expBody = expBody.substring(expBodyPrefix.length());
		}
		StringBuilder stringBuilder = new StringBuilder();
		Column col = ctx.col;
		CellTuple ct = new CellTuple(col.getName(), expBody);
//		what about non-constants?
		String columnAlias = 
				(col.isSubstitution()) ? getAliasFromSubstitutedColName(ct.alias).trim() : ct.alias;
		if (expBody.indexOf(AND.opString) != -1) {
			return computeExprWithAlias(expBody, columnAlias, ctx);
		} else if (expBody.indexOf(OR.opString) != -1) {
			return computeExprWithAlias(expBody, columnAlias, ctx);
		} else {
            //None of the above cases
            stringBuilder.append(generateNonCustomConditionBody0(ctx, expBodyPrefix + expBody));
		}
		return stringBuilder.toString();
	}
	
	public static String computeExprWithAlias(String body, String alias,DTCellCompilationContext ctx) {
		
		StringTokenizer st = new StringTokenizer(body , ExpressionBodyUtil.DELIMITER);
		int tokenCounter = 1;
		int tokenCount = st.countTokens();
		StringBuilder stringBuilder = new StringBuilder();
		while (st.hasMoreTokens()){
			String token = st.nextToken().trim();
			
			buildExprWithAlias(ctx, alias, token, stringBuilder);
			if (tokenCounter < tokenCount){
				stringBuilder.append(ExpressionBodyUtil.DELIMITER).append(" ");
			}
			tokenCounter ++;
		}
		return stringBuilder.toString();
	}

	private static void buildExprWithAlias(DTCellCompilationContext ctx, String alias, String token,
			StringBuilder stringBuilder) {
		if (token.indexOf(AND.opString()) != -1) {
			String[] nodes = token.split(AND.opString());
			for (int loop = 0, length = nodes.length; loop < length; loop++) {
				buildExprWithAlias(ctx, alias, nodes[loop], stringBuilder);
				stringBuilder.append(" ");
				if (loop != length - 1) {
					stringBuilder.append(AND.opString());
				}
		       	stringBuilder.append(" ");
			}
			return;
		} else if (token.indexOf(OR.opString()) != -1) {
			String[] nodes = token.split("\\|\\|");
			for (int loop = 0, length = nodes.length; loop < length; loop++) {
				buildExprWithAlias(ctx, alias, nodes[loop], stringBuilder);
				stringBuilder.append(" ");
				if (loop != length - 1) {
					stringBuilder.append(OR.opString());
				}
		       	stringBuilder.append(" ");
			}
			return;
		}
		if (!token.startsWith(alias)) {
			int parenIdx = 0;
			token = token.trim();
			for (int i=0; i < token.length(); i++) {
				if (token.charAt(i) != '(') {
					break;
				}
				parenIdx++;
			}
			if (parenIdx > 0) {
				stringBuilder.append(token.substring(0, parenIdx));
				token = token.substring(parenIdx);
			}
			stringBuilder.append(generateNonCustomConditionBody0(ctx, token));
		} else {
			token=token.replace(alias, "").trim();
			if(token.startsWith("=")){
				token=token.replace("=", "").trim();
			}
			stringBuilder.append(token);
		}
	}


	
	/**
	 * 
	 * @param compilationCellCompilationContext
	 * @param expBody
	 * @return
	 */
	private static String generateNonCustomConditionBody0(DTCellCompilationContext compilationCellCompilationContext,
			                                              String expBody) {
		Column col = compilationCellCompilationContext.col;
		CellTuple ct = new CellTuple(col.getName(), expBody);
		String columnName = col.getName();
		if (ct.op == null || ct.op.length() <= 0) {
			ct.op = "== ";
		}
		
		String columnAlias = 
			(col.isSubstitution()) ? getAliasFromSubstitutedColName(ct.alias).trim() : ct.alias;
		StringBuilder newBody = new StringBuilder();
		boolean isMultiple = col.isArrayProperty();
		if (isMultiple) {
			newBody.append(columnAlias.substring(0, columnAlias.indexOf('[')));
			newBody.append("@length > 0 && ");
		}
		boolean isArrayAccess = isArrayAccessColumn(columnName);
		if (PropertyDefinition.PROPERTY_TYPE_DATETIME == col.getPropertyType()) {
			ct = new CellTuple(columnName, expBody);
			//Massage the date body
			if (isArrayAccess) {
				String substitutedBody = getArraySubstitutionString(col, ct.body, ct.op);
				/**
				 * e.g : concept.dateProp[0] == 2010-12-14T10:49:53
				 */
				//Split it using any of the operator
				//First part becomes column alias
				String[] splits = substitutedBody.split(DTRegexPatterns.ANY_OPERATOR_PATTERN.pattern());
				columnAlias = splits[0];
			} 
			newBody.append(massageDateExpression(columnAlias, expBody, true));
		} else {
			//Check if it is array access
			if (isArrayAccess) {
				newBody.append(getArraySubstitutionString(col, ct.body, ct.op));
			} else {
				newBody.append(columnAlias).append(" ");
				newBody.append(" ").append(ct.op);
				newBody.append(ct.body);
			}
		}
		return newBody.toString();
	}
	
	public static String generateCustomActionBody(String expBody) {
		if (!ENDS_WITH_SEMICOLON.matcher(expBody).matches()) {
			return new StringBuilder(expBody).append(";").toString();
		} else {
			return expBody;
		}
	}
	
	/**
	 * To be used for substitution columns only to find out the first index of 
	 * any of the operators.
	 * @param alias
	 * @return
	 */
	public static int firstIndexOfOp(String alias) {
		for (String op : OPERATORS) {
			int index = alias.indexOf(op);
			if (index != -1) {
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Get alias name (concept.<propertyname>) from substituted column name.
	 * @param aliasWithSubstitution
	 * @return
	 */
	private static final String getAliasFromSubstitutedColName(String aliasWithSubstitution) {
		String[] splits = aliasWithSubstitution.split(ANY_OPERATOR_PATTERN.pattern());
		//Look for first one
		if (splits != null && splits.length > 0) {
			return splits[0];
		}
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @return
	 */
	public static String generateNonCustomActionBody(DTCellCompilationContext ctx) {
		Column column = ctx.col;
		String columnName = column.getName();
		CellTuple ct = new CellTuple(columnName, ctx.getExpr());
		String columnAlias = 
			(column.isSubstitution()) ? getAliasFromSubstitutedColName(ct.alias).trim() : ct.alias;
		StringBuilder newBody = new StringBuilder();
		
		String value = null;
		boolean isArrayAccess = isArrayAccessColumn(columnName);
		
		//Massage the date 
		if (PropertyDefinition.PROPERTY_TYPE_DATETIME == column.getPropertyType()) {
			if (isArrayAccess) {
				String substitutedBody = getArraySubstitutionString(column, ct.body, ct.op);
				/**
				 * e.g : concept.dateProp[0] == 2010-12-14T10:49:53
				 */
				//Split it using any of the operator
				//First part becomes column alias
				String[] splits = substitutedBody.split(ANY_OPERATOR_PATTERN.pattern());
				columnAlias = splits[0];
				newBody.append(columnAlias);
				newBody.append(" = ");
			}
			value = massageDateExpression(columnAlias, ct.body, false).toString();
		} else {
			value = (isArrayAccess) ? getArraySubstitutionString(column, ct.body, ct.op) : ct.body;
		}
		if (!isArrayAccess) {
			newBody.append(columnAlias);
			String op = (ct.op == null) ? "=" : ct.op;
			newBody.append(" ");
			newBody.append(op);
			newBody.append(" ");
		}
		if (value != null) {
			newBody.append(value);
		}

		if (!ENDS_WITH_SEMICOLON.matcher(ct.body).matches()) {
			newBody.append(";");
		}
		return newBody.toString();
	}
	
	/**
	 * 
	 * @param expression
	 * @return
	 */
	public static String canonicalizeExpression(String expression) {
		if (expression.trim().length() == 0) {
			return "";
		}
		expression = expression.trim();
		String[] rangeElements = new String[] {expression};
		//Check for ==
		String equalsString = EQUALS.opString();
		int equalsOpsIndex = 
			expression.indexOf(equalsString);
		if (equalsOpsIndex != -1) {
			rangeElements = expression.split(equalsString);
			
			StringBuilder stringBuilder = new StringBuilder();
			for (String element : rangeElements) {
				stringBuilder.append(element.trim());
			}
			return stringBuilder.toString();
		}
		int andIndex = expression.indexOf(AND.opString());
		if (andIndex != -1) {
			rangeElements = expression.split(AND.opString());
		}
		int orIndex = expression.indexOf(OR.opString());
		if (orIndex != -1) {
			String orOp = "\\|\\|";
			rangeElements = expression.split(orOp);
		}
		StringBuilder canonicalized = new StringBuilder();
		for (String rangeEle : rangeElements) {
			rangeEle = rangeEle.trim();
			char opChar = rangeEle.charAt(0);
			
			if ('>' == opChar || '<' == opChar) {
				canonicalized.append(opChar);
				//Courtesy existing code
				int index = 1;
				//Check if the next operator is = or operand
				char next = rangeEle.charAt(1);
				if ('=' == next) {
					//Concat the 2 operators
					canonicalized.append(next);
					index = 2;
				}
				String lhsOperand = rangeEle.substring(index);
				lhsOperand = lhsOperand.trim();
				//Append this with one space
				canonicalized.append(" ");
				canonicalized.append(lhsOperand);
				
			} else {
				canonicalized.append(rangeEle);
			}
			//Check if and is added
			int containsAndIndex = canonicalized.indexOf(AND.opString());
			if (andIndex != -1 && containsAndIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(AND.opString());
				canonicalized.append(" ");
			}
			//Check if || is added
			int containsORIndex = canonicalized.indexOf(OR.opString());
			if (orIndex != -1 && containsORIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(OR.opString());
				canonicalized.append(" ");
			}
		}
		return canonicalized.toString();
	}

	
	/**
	 * Code to generate the condition/action body from domain entry.
	 * @param ctx
	 * @return
	 */
	public static String generateDomainBody(DTCellCompilationContext ctx) {
		List<DomainEntry> domainMatches = 
			DTDomainUtil.getDomainMatches(ctx.domainEntryMap, ctx.trv);
		if (domainMatches == null || domainMatches.size() <= 0) return null;
		Column col = ctx.col;
		int idx = firstIndexOfOp(col.getName());
		String colName = 
			(col.isSubstitution() && idx != -1) ? col.getName().substring(0, idx).trim() : col.getName();
		StringBuilder body = new StringBuilder();
		for (DomainEntry entry : domainMatches) {
			if (entry == null) continue; // || entry.getParent() == null) continue;
			boolean stringType = 
				com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_STRING == ctx.col.getPropertyType();//DOMAIN_DATA_TYPES.STRING.equals(entry.getParent().getDataType());
			if (entry instanceof Single) {
				if (ctx.col.getColumnType().isConditon()) {
					if (body.length() > 0) {
						body.append(" || ");
					}
					body.append(colName);
					body.append(" == ");
				}
				
				if (stringType) {
					appendEscapedString(entry.getValue().toString(), body);
				} else {
					//Massaging required only for validation purposes.
					if (PropertyDefinition.PROPERTY_TYPE_DATETIME == ctx.col.getPropertyType()) {
						body.append(DATE_TIME_PREFIX).
			            		append(entry.getValue()).
			            		append("\", ").
			            		append(UTC_FORMAT).
			            		append(")").toString();
					} else {
						body.append(entry.getValue());
					}
				}
				if (ctx.col.getColumnType().isAction()) break; 
			} else if (entry instanceof Range && ctx.col.getColumnType().isConditon()) {
				Range range = (Range)entry;
				String lower = range.getLower();
				String upper = range.getUpper();

				boolean includeLower = range.isLowerInclusive();
				boolean includeUpper = range.isUpperInclusive();
				
				StringBuilder lowerExp = null;
				if (lower != null) {
					lowerExp = new StringBuilder(lower.length()+5);
					lowerExp.append(includeLower ? " >= " : " > ");
					if (stringType) {
						appendEscapedString(lower, lowerExp);
					} else {
						//Massaging required only for validation purposes.
						if (PropertyDefinition.PROPERTY_TYPE_DATETIME == ctx.col.getPropertyType()) {
							lowerExp.append(DATE_TIME_PREFIX).
				            		append(lower).
				            		append("\", ").
				            		append(UTC_FORMAT).
				            		append(")").toString();
						} else {
							lowerExp.append(lower);
						}
					}
				}
				
				StringBuilder upperExp = null;
				if (upper != null) {
					upperExp = new StringBuilder(upper.length()+5);
					upperExp.append(includeUpper ? " <= " : " < ");
					if (stringType) {
						appendEscapedString(upper, upperExp);
					} else {
						//Massaging required only for validation purposes.
						if (PropertyDefinition.PROPERTY_TYPE_DATETIME == ctx.col.getPropertyType()) {
							upperExp.append(DATE_TIME_PREFIX).
				            		append(upper).
				            		append("\", ").
				            		append(UTC_FORMAT).
				            		append(")").toString();
						} else {
							upperExp.append(upper);
						}
					}
				}
				
				boolean lowerExpPresent = lowerExp != null && lowerExp.length() > 0;
				boolean upperExpPresent = upperExp != null && upperExp.length() > 0;
				if (lowerExpPresent || upperExpPresent) {
					if (body.length() > 0) body.append(" || ");
				}
				if (lowerExpPresent) {
					body.append(colName);
					body.append(lowerExp);
					if (upperExpPresent) body.append(" && ");
				}
				if (upperExpPresent) {
					body.append(colName);
					body.append(upperExp);
				}
			}
		}
		return body.toString();
	}
	
	
		
	//to avoid escaping it would be possible to create "advanced mode" that just
	//changed the dataType of the domain to something other than STRING, and this
	//function wouldn't be called.
	private static void appendEscapedString(String str, StringBuilder buf) {
//		str = str.trim();
//		boolean quote = !(str.startsWith("\"") && str.endsWith("\""));
//		if(quote) 
		buf.append('"');
		for (int ii = 0; ii < str.length(); ii++) {
			char chr = str.charAt(ii);
			switch (chr) {
				case '\\':
					buf.append("\\\\");
					break;
				case '"':
					buf.append("\\\"");
					break;
				case '\n':
					buf.append("\\n");
					break;
				default:
					buf.append(chr);
			}
		}
		buf.append('"');
	}

	private static String getArrayIndexExpression(String expBody) {
		int startIndex = expBody.indexOf('[');
		int endIndex = expBody.lastIndexOf(']');
		if (startIndex == -1 || endIndex == -1) {
			return "";
		}
		return expBody.substring(startIndex, endIndex + 1);
	}
	
	

	private static class CellTuple {
		String alias = null;
		String op = null;
		String body = null;
		
		private CellTuple(String _alias, String expbody) {
			this(_alias, expbody, true);
		}
		private CellTuple(String _alias, String expbody, boolean splitOpAndBody) {
			alias = _alias;
			body = expbody;
			removeAlias();
			if (splitOpAndBody) {
				splitOpAndBody();
			}
		}
		
		private void removeAlias() {
			if (alias == null || body == null) return;
			body = body.trim();
			alias = alias.trim();
			
			//Fix for BE-14085 
			if (body.startsWith(alias+"=")) {
				body = body.substring(alias.length());
			}
		}

		
		private void splitOpAndBody() {
			if (body == null) return;
			body = body.trim();

			if (ArrayParseUtil.isArrayAccessColumn(alias)) {
				String expBodyPrefix = getArrayIndexExpression(body);
				body = body.substring(expBodyPrefix.length());
				body = body.trim();
				for (String opsElem : OPERATORS) {
					if (body.startsWith(opsElem)) {
						body = body.substring(opsElem.length());
						if (opsElem.equals("=")) {
							op = "==";
						} else if (opsElem.equals("<>")) {
							op = "!=";
						} else {
							op = opsElem;
						}
						break;
					}
				}
				body = expBodyPrefix.concat(body);
			} else {
				for (String opsElem : OPERATORS) {
					if (body.startsWith(opsElem)) {
						body = body.substring(opsElem.length());
						if (opsElem.equals("=")) {
							op = "==";
						} else if (opsElem.equals("<>")) {
							op = "!=";
						} else {
							op = opsElem;
						}
						break;
					}
				}
			}
		}
	}
}