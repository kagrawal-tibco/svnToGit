package com.tibco.cep.dashboard.plugin.beviews.common.query;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;

public class ViewsQuery implements Query {

	private String query;

	private String condition;

	private QuerySpec querySpec;

	private QueryParams parameters;

	private String typeID;

	public ViewsQuery(String query, String condition, QueryParams parameters, String typeID) {
		super();
		if (condition != null && query.contains(condition) == false) {
			throw new IllegalArgumentException("condition is not part of the query");
		}
		this.query = query;
		this.condition = condition;
		this.parameters = parameters;
		this.typeID = typeID;
	}

	public ViewsQuery(QuerySpec querySpec) {
		this(querySpec,QueryParams.NO_QUERY_PARAMS);
	}

	public ViewsQuery(QuerySpec querySpec, QueryParams parameters) {
		super();
		this.querySpec = querySpec;
		this.query = this.querySpec.toString();
		this.condition = this.querySpec.getCondition() == null ? null : this.querySpec.getCondition().toString();
		this.typeID = this.querySpec.getSchema().getTypeID();
		this.parameters = parameters;
	}

	public String getTypeID() {
		return typeID;
	}

	@Override
	public String getQuery() {
		return query;
	}

	public String getCondition() {
		return condition;
	}

	@Override
	public QueryParams getParameters() {
		return this.parameters;
	}

	@Override
	public String bindQuery() {
		return bindQuery(true);
	}

	public String bindCondition() {
		return bindCondition(true);
	}

	public String bindQuery(boolean bindDateTimeAsString) {
		return bind(query, bindDateTimeAsString);
	}

	public String bindCondition(boolean bindDateTimeAsString) {
		return bind(condition, bindDateTimeAsString);
	}

	private String bind(String expression, boolean bindDateTimeAsString) {
		if (expression == null) {
			return null;
		}
		if (StringUtil.isEmptyOrBlank(expression) == true) {
			return expression;
		}
		if (parameters.equals(QueryParams.NO_QUERY_PARAMS)) {
			return expression;
		}
		StringBuilder sb = new StringBuilder(expression);
		for (String paramName : parameters.getParameterNames()) {
			int i = sb.indexOf("$" + paramName);
			if (i != -1) {
				DataType dataType = parameters.getParameterType(paramName);
				String value = parameters.getParameter(paramName);
				if (value == null) {
					value = String.valueOf(dataType.getDefaultValue());
				}
				if (dataType.equals(BuiltInTypes.STRING) == true) {
					value = "\"" + value + "\"";
				}
				else if (dataType.equals(BuiltInTypes.DATETIME) == true) {
					if (bindDateTimeAsString == true) {
						value = "\'" + value + "\'";
					}
				}
				sb.replace(i, i + paramName.length() + 1, value);
			}
		}
		return sb.toString();
	}

	public QuerySpec getQuerySpec() {
		return querySpec;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ViewsQuery[");
		if (query != null) {
			sb.append("query="+query);
		}
		else {
			sb.append("queryspec="+querySpec);
		}
		sb.append(",parameters="+parameters);
		sb.append("]");
		return sb.toString();
	}
}
