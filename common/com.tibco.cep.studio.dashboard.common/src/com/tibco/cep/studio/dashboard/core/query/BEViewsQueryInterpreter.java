package com.tibco.cep.studio.dashboard.core.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import com.tibco.cep.query.ast.parser.ParserUtil;

public final class BEViewsQueryInterpreter {
	
	private Logger logger;
	
	private Map<String, Metric> metricsMap;

	private boolean error;

	private String errorMessage;

	private String query;

	private Metric queriedMetric;

	private String queriedMetricNameSpace;

	private String queriedMetricAlias;
	
	private String condition;

	private LinkedHashMap<String, PropertyDefinition> variables;
	
	private LinkedHashMap<String, Boolean> orderBySpecs;
	
	public BEViewsQueryInterpreter(List<Metric> metrics) {
		variables = new LinkedHashMap<String, PropertyDefinition>();
		orderBySpecs = new LinkedHashMap<String, Boolean>();
		metricsMap = new HashMap<String, Metric>();
		if (metrics != null && metrics.isEmpty() == false) {
			for (Metric metric : metrics) {
				String key = getPath(metric);
				metricsMap.put(key, metric);
			}
		}
	}

	private String getPath(Metric metric) {
		String key = metric.getNamespace();
		if (key.endsWith("/") == false) {
			key = key + "/";
		}
		key = key + metric.getName();
		return key;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void interpret(String query) {
		error = false;
		errorMessage = null;
		queriedMetric = null;
		queriedMetricAlias = null;
		variables.clear();
		orderBySpecs.clear();
		this.query = query;
		if (query == null || query.trim().length() == 0) {
			return;
		}
		try {
			ParserUtil parser = new ParserUtil(query);
			CommonTree root = (CommonTree) parser.getAST();
			validateSelectClause(root);
			if (error == true) {
				return;
			}
			validateFromClause(root);
			if (error == true) {
				return;
			}
			validateWhereClause(root);
			if (error == true) {
				return;
			}
			validateGroupByClause(root);
			if (error == true) {
				return;
			}
			validateOrderByClause(root);
			//extract condition 
			Tree whereClause = root.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
			if (whereClause != null) {
				int whereClauseStart = whereClause.getCharPositionInLine();
				Tree orderByClause = root.getFirstChildWithType(BEOqlParser.ORDER_CLAUSE);
				int whereClauseEnd = orderByClause == null ? query.length() : orderByClause.getCharPositionInLine();
				condition = query.substring(whereClauseStart, whereClauseEnd).trim();
				if (condition.toLowerCase().startsWith("where") == true){
					condition = condition.substring("where".length()).trim();
				}
				if (condition.endsWith(";") == true) {
					condition = condition.substring(0,condition.length()-1);
				}
			}
		} catch (NullPointerException e){
			if (logger != null){
				logger.log(Level.ERROR, e, "could not interpret ["+query+"]");
			}
			handleError("Invalid query");
		} catch (Exception e) {
			if (logger != null){
				logger.log(Level.ERROR, e, "could not interpret ["+query+"]");
			}
			String message = e.getMessage();
			if (message == null) {
				message = "Invalid query";
			}
			handleError(message);
		}
	}

	private void validateSelectClause(CommonTree node) {
		CommonTree limitClause = (CommonTree) node.getFirstChildWithType(BEOqlParser.LIMIT_CLAUSE);
		if (limitClause != null) {
			handleError("limit in select is not supported");
			return;
		}
		CommonTree projectionAttributes = (CommonTree) node.getFirstChildWithType(BEOqlParser.PROJECTION_ATTRIBUTES);
		int childCount = projectionAttributes.getChildCount();
		if (childCount > 1) {
			handleError("Only '*' is supported as projection");
			return;
		}
		CommonTree expressionTree = (CommonTree) projectionAttributes.getChild(0).getChild(0);
		if (expressionTree.getType() == BEOqlParser.SCOPE_IDENTIFIER && expressionTree.getText().equals("*") == true) {
			// we are ok
			return;
		}
		handleError("Only '*' is supported as projection");
	}

	private void validateFromClause(CommonTree node) {
		CommonTree fromClause = (CommonTree) node.getFirstChildWithType(BEOqlParser.FROM_CLAUSE);
		int childCount = fromClause.getChildCount();
		if (childCount > 1) {
			handleError("Only a single metric is supported in from clause");
			return;
		}
		CommonTree child = (CommonTree) fromClause.getChild(0);
		if (child.getType() != BEOqlParser.ITERATOR_DEF) {
			handleError("Unsupport from clause");
			return;
		}
		// stream is not support
		CommonTree selectExpression = (CommonTree) child.getFirstChildWithType(BEOqlParser.SELECT_EXPR);
		if (selectExpression != null) {
			handleError("sub select not supported");
		}
		// stream is also not support
		CommonTree streamDefinition = (CommonTree) child.getFirstChildWithType(BEOqlParser.STREAM_DEF);
		if (streamDefinition != null) {
			handleError("streaming is not supported");
		}
		// we support path expression e.g. /folder/metric or metric
		CommonTree pathExpression = (CommonTree) child.getFirstChildWithType(BEOqlParser.PATH_EXPRESSION);
		if (pathExpression == null) {
			handleError("no metric specified in from clause");
		}
		String metricPath = getPath(pathExpression);
		CommonTree aliasClause = (CommonTree) child.getFirstChildWithType(BEOqlParser.ALIAS_CLAUSE);
		queriedMetric = searchMetric(metricPath);
		if (queriedMetric != null) {
			queriedMetricNameSpace = getPath(queriedMetric);
		}
		if (aliasClause != null) {
			queriedMetricAlias = aliasClause.getChild(0).getText();
		}
	}

	private String getPath(CommonTree pathExpression) {
		StringBuilder path = new StringBuilder();
		int numChildren = pathExpression.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			path.append(pathExpression.getChild(i).getText());
		}
		String entityPath = path.toString();
		return entityPath;
	}

	private Metric searchMetric(String name) {
		if (metricsMap == null) {
			return null;
		}
		// search by full scope
		Metric metric = metricsMap.get(name);
		// if (metric == null){
		// //search by name
		// for (Metric localMetric : metricsMap.values()) {
		// if (localMetric.getName().equals(name) == true){
		// if (metric == null){
		// metric = localMetric;
		// }
		// else {
		// handleError("Found more then one metric with name as "+name);
		// return null;
		// }
		// }
		// }
		// }
		if (metric == null) {
			handleError("Unknown metric - " + name);
			return null;
		}
		return metric;
	}

	private void validateWhereClause(CommonTree node) {
		CommonTree whereClause = (CommonTree) node.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
		if (whereClause != null) {
			CommonTree actualWhereClause = (CommonTree) whereClause.getChild(0);
			validateExpression(actualWhereClause);
//			condition = whereClause.toString();
//			String[] tokens = query.split(" ");
//			int whereStartIdx = -1;
//			int whereEndIdx = -1;
//			for (int i = 0; i < tokens.length; i++) {
//				if (tokens[i].trim().equalsIgnoreCase("where") == true) {
//					whereStartIdx = i+1;
//				}
//				else if (whereStartIdx != -1 && tokens[i].trim().equalsIgnoreCase("")) {
//					
//				}
//			}
//			if (condition.startsWith("where") == true){
//				condition = condition.substring("where".length()).trim();
//			}
		}
	}

	private void validateExpression(CommonTree node) {
		switch (node.getType()) {
			case BEOqlParser.BLOCK_EXPRESSION:
			case BEOqlParser.TOK_NOT:
			case BEOqlParser.TOK_ABS:
			case BEOqlParser.EXISTS_CLAUSE:
				validateExpression((CommonTree) node.getChild(0));
				break;
			case BEOqlParser.UNARY_MINUS:
				System.out.println("BEOqlParser.UNARY_MINUS[" + node.getText() + "]");
				if (node.getChild(0).getType() != BEOqlParser.NUMBER_LITERAL) {
					handleError("Incompatible datatype with unary minus");
				}
				PropertyDefinition fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && isNumeric(fieldReference) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}				
				break;
			case BEOqlParser.IDENTIFIER:
				System.out.println("BEOqlParser.IDENTIFIER[" + node.getText() + "]");
				searchForFieldReference(node.getText());
				break;
			case BEOqlParser.SCOPE_IDENTIFIER:
				System.out.println("BEOqlParser.SCOPE_IDENTIFIER[" + node.getText() + "]");
				break;
			case BEOqlParser.SELECT_EXPR:
				handleError("select expression is not supported");
				break;
			case BEOqlParser.BIND_VARIABLE_EXPRESSION:
				String variableName = node.getChild(0).getText();
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null) {
					if (variables.containsKey(variableName) == true){
						handleError(variableName + " is already defined with for use against " + variables.get(variableName).getName());
					}
					else {
						variables.put(variableName, fieldReference);
					}
				}
				break;
			case BEOqlParser.NUMBER_LITERAL:
				System.out.println("BEOqlParser.NUMBER_LITERAL[" + node.getText() + "]");
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && isNumeric(fieldReference) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}
				break;
			case BEOqlParser.CHAR_LITERAL:
				System.out.println("BEOqlParser.CHAR_LITERAL[" + node.getText() + "]");
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && fieldReference.getType().equals(PROPERTY_TYPES.STRING) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}
				break;
			case BEOqlParser.STRING_LITERAL:
				System.out.println("BEOqlParser.STRING_LITERAL[" + node.getText() + "]");
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && fieldReference.getType().equals(PROPERTY_TYPES.DATE_TIME) == true) {
					boolean valid = BEViewsQueryDateTypeInterpreter.isValidDate(node.getText());
					if (valid == false) {
						handleError("Incompatible datatype used against " + fieldReference.getName());
					}
				}				
				else if (fieldReference != null && fieldReference.getType().equals(PROPERTY_TYPES.STRING) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}
				break;
			case BEOqlParser.NULL_LITERAL:
				System.out.println("BEOqlParser.NULL_LITERAL[" + node.getText() + "]");
				break;
			case BEOqlParser.BOOLEAN_LITERAL:
				System.out.println("BEOqlParser.BOOLEAN_LITERAL[" + node.getText() + "]");
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && fieldReference.getType().equals(PROPERTY_TYPES.BOOLEAN) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}
				break;
			case BEOqlParser.DATETIME_LITERAL:
				System.out.println("BEOqlParser.DATETIME_LITERAL[" + node.getText() + "]");
				fieldReference = traverseForFieldReference(node);
				if (fieldReference != null && fieldReference.getType().equals(PROPERTY_TYPES.DATE_TIME) == false) {
					handleError("Incompatible datatype used against " + fieldReference.getName());
				}
				break;
			case BEOqlParser.FIELD_LIST:
				// TODO what is field list?
				break;
			case BEOqlParser.ARRAY_INDEX:
				handleError("Arrays are not supported");
			case BEOqlParser.TOK_DOT:
				searchForMetricReference(node.getChild(0).getText());
				searchForFieldReference(node.getChild(1).getText());
				break;
			case BEOqlParser.TOK_AT:
				searchForMetricReference(node.getChild(0).getText());
				break;
			case BEOqlParser.RANGE_EXPRESSION:
			case BEOqlParser.OR_EXPRESSION:
			case BEOqlParser.AND_EXPRESSION:
			case BEOqlParser.BETWEEN_CLAUSE:
			case BEOqlParser.TOK_CONCAT:
			case BEOqlParser.TOK_EQ:
//			case BEOqlParser.TOK_GE:
//			case BEOqlParser.TOK_GT:
//			case BEOqlParser.TOK_LE:
//			case BEOqlParser.TOK_LT:
			case BEOqlParser.TOK_MINUS:
			case BEOqlParser.TOK_NE:
			case BEOqlParser.TOK_LIKE:
			case BEOqlParser.TOK_MOD:
			case BEOqlParser.TOK_PLUS:
			case BEOqlParser.TOK_STAR:
			case BEOqlParser.IN_CLAUSE:
			case BEOqlParser.TOK_SLASH:
				validateExpression((CommonTree) node.getChild(0));
				validateExpression((CommonTree) node.getChild(1));
				break;
			// special handling for string datatype used with operators >, <, <=, >=
			case BEOqlParser.TOK_GE:
			case BEOqlParser.TOK_GT:
			case BEOqlParser.TOK_LE:
			case BEOqlParser.TOK_LT:
				if(node.getChild(0).getType() == BEOqlParser.IDENTIFIER) {
					PropertyDefinition property = searchForFieldReference(node.getChild(0).getText());
					if(property != null && property.getType() == PROPERTY_TYPES.STRING) {
						handleError("Field "+ property.getName()+" with String datatype cannot be used with operator "+node.getText());
					}
				}
				validateExpression((CommonTree) node.getChild(0));
				validateExpression((CommonTree) node.getChild(1));
				break;
			case BEOqlParser.CAST_EXPRESSION:
				break;
			case BEOqlParser.FUNCTION_EXPRESSION:
				System.out.println("BEOqlParser.FUNCTION_EXPRESSION[" + node.getText() + "]");
				handleError("Functions are not supported");
			case BEOqlParser.PATHFUNCTION_EXPRESSION:
				System.out.println("BEOqlParser.PATHFUNCTION_EXPRESSION[" + node.getText() + "]");
				handleError("Functions are not supported");
			case BEOqlParser.TOK_BOOLEAN:
				break;
			case BEOqlParser.TOK_CONCEPT:
				// should I check against entities
				break;
			case BEOqlParser.TOK_DATETIME:
				break;
			case BEOqlParser.TOK_DOUBLE:
				break;
			case BEOqlParser.TOK_ENTITY:
				// should I check against entities
				break;
			case BEOqlParser.TOK_EVENT:
				handleError("Events are not supported");
			case BEOqlParser.TOK_INT:
				break;
			case BEOqlParser.TOK_LONG:
				break;
			case BEOqlParser.TOK_OBJECT:
				handleError("Objects are not supported");
			case BEOqlParser.TOK_STRING:
				break;
			case BEOqlParser.PATH_EXPRESSION:
				// validateMetric(node);
				break;
			default:
				break;
		}
	}

	private boolean isNumeric(PropertyDefinition fieldReference) {
		PROPERTY_TYPES type = fieldReference.getType();
		if (type.equals(PROPERTY_TYPES.INTEGER) == true || type.equals(PROPERTY_TYPES.LONG) == true || type.equals(PROPERTY_TYPES.DOUBLE) == true) {
			return true;
		}
		return false;
	}

	private PropertyDefinition traverseForFieldReference(CommonTree node) {
		PropertyDefinition fieldReference = null;
		Tree fieldNode = node.parent.getChild(node.childIndex - 1);
		if (fieldNode.getType() == BEOqlParser.TOK_DOT) {
			// we do not need to validate the alias since it will be
			// validated earlier in the tree traversal
			// searchForMetricReference(fieldNode.getChild(0).getText());
			fieldReference = searchForFieldReference(fieldNode.getChild(1).getText());
		} else {
			fieldReference = searchForFieldReference(fieldNode.getText());
		}
		return fieldReference;
	}

	private void searchForMetricReference(String text) {
		if (queriedMetric != null) {
			// check if text is direct match
			if (queriedMetric.getName().equals(text) == true || queriedMetricNameSpace.equals(text) == true) {
				// we have a direct match
				return;
			}
			// check for alias match
			if (text.equals(queriedMetricAlias) == true) {
				// we have a alias match
				return;
			}
			if (queriedMetricAlias != null) {
				handleError("Incorrect alias - " + text);
				return;
			}
			handleError(text + " is not accessible");
		}
	}

	private PropertyDefinition searchForFieldReference(String text) {
		if (queriedMetric != null) {
			for (PropertyDefinition property : queriedMetric.getProperties()) {
				if (property.getName().equals(text) == true) {
					return property;
				}
			}
			handleError(text + " is not identifible as a property of " + queriedMetric.getName());
		}
		return null;
	}

	private void validateGroupByClause(CommonTree node) {
		CommonTree groupClause = (CommonTree) node.getFirstChildWithType(BEOqlParser.GROUP_CLAUSE);
		if (groupClause != null) {
			handleError("group by is not supported");
		}
	}

	private void validateOrderByClause(CommonTree node) {
		CommonTree orderByClause = (CommonTree) node.getFirstChildWithType(BEOqlParser.ORDER_CLAUSE);
		if (orderByClause != null) {
			int numChildren = orderByClause.getChildCount();
			for (int i = 0; i < numChildren; i++) {
				CommonTree sortTree = (CommonTree) orderByClause.getChild(i);
				CommonTree sortLimitTree = (CommonTree) sortTree.getFirstChildWithType(BEOqlParser.LIMIT_CLAUSE);
				if (sortLimitTree != null) {
					handleError("sort limit is not supported");
					return;
				}
				PropertyDefinition sortByField = searchForFieldReference(((CommonTree) sortTree.getChild(0)).getText());
				if (orderBySpecs.containsKey(sortByField.getName()) == true){
					handleError(sortByField.getName()+" has been used more then once in order by");
					return;
				}
				CommonTree sortDirectionTree = (CommonTree) sortTree.getFirstChildWithType(BEOqlParser.SORT_DIRECTION);
				boolean asc = sortDirectionTree.getChild(0) == null || sortDirectionTree.getChild(0).getType() == BEOqlParser.TOK_ASC;
				orderBySpecs.put(sortByField.getName(), asc);
			}
		}
	}

	private void handleError(String msg) {
		if (error == true) {
			return;
		}
		error = true;
		errorMessage = msg;
	}

	public boolean hasError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Metric getQueriedMetric() {
		return queriedMetric;
	}
	
	public Collection<String> getVariableNames(){
		return variables.keySet();
	}
	
	public PROPERTY_TYPES getDataType(String variableName){
		PropertyDefinition propertyDefinition = variables.get(variableName);
		if (propertyDefinition == null){
			throw new IllegalArgumentException(variableName+" does not exist");
		}
		return propertyDefinition.getType();
	}
	
	public String getFieldName(String variableName){
		PropertyDefinition propertyDefinition = variables.get(variableName);
		if (propertyDefinition == null){
			throw new IllegalArgumentException(variableName+" does not exist");
		}
		return propertyDefinition.getName();
	}

	public String getQuery() {
		return query;
	}

	public Collection<String> getSortByFieldNames(){
		return orderBySpecs.keySet();
	}
	
	public boolean isSortAscending(String fieldName){
		return orderBySpecs.get(fieldName);
	}
	
	public String getCondition(){
		return condition;
	}
	
}