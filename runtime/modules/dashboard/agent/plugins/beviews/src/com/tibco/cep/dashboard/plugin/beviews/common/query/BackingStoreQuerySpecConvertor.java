package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.DrilldownQuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryExpression;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryFieldName;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryLiteralValue;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryProjectionField;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryUnaryTerm;

class BackingStoreQuerySpecConvertor {

	private QuerySpec querySpec;

	BackingStoreQuerySpecConvertor(QuerySpec querySpec) {
		super();
		this.querySpec = querySpec;
	}

	String convert() {
		StringBuilder sb = new StringBuilder();
		if (querySpec instanceof DrilldownQuerySpec) {
			sb.append("drilldown ");
		}
		else {
			sb.append("select ");
		}
		addProjections(sb);
		sb.append(" from ");
		if (querySpec instanceof DrilldownQuerySpec) {
			sb.append(((DrilldownQuerySpec) querySpec).getParentSchema().getScopeName());
		}
		else {
			sb.append(querySpec.getScopeName());
		}
		if (querySpec.getCondition() != null){
			sb.append(" where ");
			addCondition(querySpec.getCondition(), sb);
		}
		if (querySpec.getGroupByFields().isEmpty() == false) {
			sb.append(" group by ");
			addGroupBy(sb);
		}
		if (querySpec.getOrderByFields().isEmpty() == false) {
			sb.append(" order by ");
			addOrderBy(sb);
		}
		return sb.toString();
	}

	private void addProjections(StringBuilder sb) {
		ArrayList<QueryProjectionField> projectionFields = querySpec.getProjectionFields();
		if (projectionFields.isEmpty() == true) {
			sb.append(" * ");
		}
		else {
			Iterator<QueryProjectionField> iterator = projectionFields.iterator();
			while (iterator.hasNext()) {
				QueryProjectionField projection = (QueryProjectionField) iterator.next();
				addProjection(projection, sb);
				if (iterator.hasNext() == true) {
					sb.append(", ");
				}
			}
		}
	}

	private void addProjection(QueryProjectionField projectionField, StringBuilder sb) {
		String fieldName = projectionField.getFromFieldName();
		if (projectionField.getFunction() != null) {
			sb.append(projectionField.getFunction()).append("(").append(fieldName).append(")");
		} else {
			sb.append(fieldName);
		}
	}

	String convertCondition() {
		QueryCondition condition = querySpec.getCondition();
		if (condition == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		addCondition(condition, sb);
		return sb.toString();
	}


	private void addCondition(QueryCondition condition, StringBuilder sb) {
		if (condition instanceof QueryUnaryTerm) {
			QueryUnaryTerm queryUnaryTerm = (QueryUnaryTerm) condition;
			sb.append("(");
			if (queryUnaryTerm.getOperator() != null){
				sb.append(queryUnaryTerm.getOperator());
			}
			addCondition(queryUnaryTerm.getTerm(), sb);
			sb.append(")");
		}
		else if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm queryBinaryTerm = (QueryBinaryTerm) condition;
			sb.append("(");
			addCondition(queryBinaryTerm.getLeftTerm(), sb);
			sb.append(" ").append(queryBinaryTerm.getOperator()).append(" ");
			addCondition(queryBinaryTerm.getRightTerm(), sb);
			sb.append(")");
		}
		else if (condition instanceof QueryPredicate) {
			QueryPredicate queryPredicate = (QueryPredicate) condition;
			if (queryPredicate.getComparison().equals(QueryPredicate.IS_NULL) == true || queryPredicate.getComparison().equals(QueryPredicate.IS_NOT_NULL) == true) {
				addExpression(queryPredicate.getLeftArgument(), sb, false);
				sb.append(" ").append(queryPredicate.getComparison());
			}
			else {
				addExpression(queryPredicate.getLeftArgument(), sb, false);
				sb.append(" ");
				sb.append(queryPredicate.getComparison());
				sb.append(" ");
				addExpression(queryPredicate.getRightArgument(), sb, true);
			}
 		}
	}

	private void addExpression(QueryExpression expression, StringBuilder sb, boolean escapeValue){
		if (expression instanceof QueryFieldName) {
			sb.append(((QueryFieldName) expression).getFieldName());
		}
		else if (expression instanceof QueryLiteralValue) {
			QueryLiteralValue literalValueExpr = (QueryLiteralValue) expression;
			if (escapeValue == true) {
				FieldValue value = literalValueExpr.getLiteralValue();
				if (value.getDataType().equals(BuiltInTypes.STRING) == true) {
					String actualValue = value.toString();
					actualValue = actualValue.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("\\\\"));
					literalValueExpr = new QueryLiteralValue(literalValueExpr.getSchema(), new FieldValue(BuiltInTypes.STRING, actualValue));
				}
			}
			sb.append(literalValueExpr.toString());
		}
		else {
			sb.append(expression.toString());
		}
	}

	private void addGroupBy(StringBuilder sb) {
		Iterator<String> groupByFields = querySpec.getGroupByFields().iterator();
		while (groupByFields.hasNext()) {
			String fieldName = groupByFields.next();
			sb.append(fieldName);
			if (groupByFields.hasNext() == true) {
				sb.append(", ");
			}
		}
	}

	private void addOrderBy(StringBuilder sb) {
		Iterator<OrderBySpec> orderByFields = querySpec.getOrderByFields().iterator();
		while (orderByFields.hasNext()) {
			OrderBySpec orderBySpec = orderByFields.next();
			sb.append(orderBySpec.getOrderByField());
			if (orderBySpec.getAscending() == true) {
				sb.append(" asc");
			} else {
				sb.append(" desc");
			}
			if (orderByFields.hasNext() == true) {
				sb.append(", ");
			}
		}
	}
}
