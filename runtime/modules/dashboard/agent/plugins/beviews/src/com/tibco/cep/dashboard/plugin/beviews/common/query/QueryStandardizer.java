package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.ConceptTupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.DrilldownQuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBindParameter;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryExpression;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryFieldName;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryFunctionCall;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryLiteralValue;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryProjectionField;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryUnaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.mal.ConceptSourceElement;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;

class QueryStandardizer {

	private static final String DEFAULT_ALIAS = "A";

	private boolean bindingsEnabled;

	private boolean usePosixRegExSyntaxDefault;

	public QueryStandardizer(boolean bindingsEnabled, boolean usePosixRegExSyntax) {
		super();
		this.bindingsEnabled = bindingsEnabled;
		this.usePosixRegExSyntaxDefault = usePosixRegExSyntax;
	}

	public boolean isBindingsEnabled() {
		return bindingsEnabled;
	}

	ViewsQuery standardize(ViewsQuery query) throws QueryException {
		try {
			ConceptSourceElement element = (ConceptSourceElement) MALSourceElementCache.getInstance().getMALSourceElement(query.getTypeID());
			QuerySpec querySpec = query.getQuerySpec();
			if (querySpec == null) {
				//start with 'select A from'
				StringBuilder sb = new StringBuilder("select "+DEFAULT_ALIAS+" from ");
				//append '<entitypath> A'
				sb.append(element.getScopeName());
				sb.append(" "+DEFAULT_ALIAS);
				//get the binded condition (do not convert datetime to string)
				String condition = bindingsEnabled == true ? query.getCondition() : query.bindCondition(false);
				//get the order by
				String orderBy = null;
				int idx = query.getQuery().indexOf("order by");
				if (idx != -1) {
					orderBy = query.getQuery().substring(idx);
				}
				//update the condition & order by replace <fieldname> by A.<fieldname>
				boolean conditionExists = StringUtil.isEmptyOrBlank(condition) == false;
				boolean orderByExists = StringUtil.isEmptyOrBlank(orderBy) == false;
				if (conditionExists == true || orderByExists == true) {
					for (MALFieldMetaInfo field : element.getFields()) {
						if (conditionExists == true) {
							condition = condition.replaceAll(field.getName()+"[ ]*[=]", DEFAULT_ALIAS+"."+field.getName()+" =");
						}
						if (orderByExists == true) {
							orderBy = orderBy.replaceAll(field.getName(), DEFAULT_ALIAS+"."+field.getName());
						}
					}
				}
				if (conditionExists == true) {
					sb.append(" where ");
					sb.append(condition);
				}
				if (element.isASuperClass() == true) {
					if (conditionExists == true) {
						sb.append(" and ");
					}
					else {
						sb.append(" where ");
					}
					sb.append(String.format("/Instance/getExpandedName(%1$s) = \"%2$s%3$s\"",DEFAULT_ALIAS,RDFTnsFlavor.BE_NAMESPACE, element.getScopeName()));
				}
				if (orderByExists == true) {
					sb.append(" ");
					sb.append(orderBy);
				}
				//return a new views query with no query params and query/condition fully binded
				return new ViewsQuery(sb.toString(), condition, bindingsEnabled == true ? query.getParameters() : QueryParams.NO_QUERY_PARAMS, query.getTypeID());
			}
			QuerySpec modifiedQuerySpec = null;
			Map<String,String> fieldNameReplacements = new HashMap<String, String>();
			if (querySpec instanceof DrilldownQuerySpec) {
				//we are dealing with a drilldown query
				//create a new query , since we want select * from <dvm> rather then drilldown * from <DVM>
				ConceptTupleSchema schema = (ConceptTupleSchema) querySpec.getSchema();
				modifiedQuerySpec = new QuerySpec(schema);
				modifiedQuerySpec.setCondition((QueryCondition) querySpec.getCondition().clone());
				//add group by fields
				for (String groupByField : querySpec.getGroupByFields()) {
					modifiedQuerySpec.addGroupByField(groupByField);
				}
				//add order by fields
				for (OrderBySpec orderBySpec : querySpec.getOrderByFields()) {
					modifiedQuerySpec.addOrderByField(orderBySpec.getOrderByField(), orderBySpec.getAscending());
				}
				//add group by having
				if (querySpec.getGroupByHavingCondition() != null) {
					modifiedQuerySpec.setGroupByHavingCondition((QueryCondition) querySpec.getGroupByHavingCondition().clone());
				}
				//the drilldown query uses the @id instead of pid$ to join DVM entries with metric @id
				fieldNameReplacements.put(schema.getIDField().getFieldName(), EntityUtils.getParentIdentifier(EntityCache.getInstance().getEntity(schema.getTypeID())).getName());
			}
			else {
				//just clone the original query to prevent modification propogation
				modifiedQuerySpec = (QuerySpec) querySpec.clone();
			}
			//set an alias
			modifiedQuerySpec.setAlias(DEFAULT_ALIAS);
			//convert condition for use as cache query
			QueryParams queryParams = QueryParams.NO_QUERY_PARAMS;
			boolean usePosixRegEx = element.isASuperClass() == true ? false : usePosixRegExSyntaxDefault;
			if (bindingsEnabled == false) {
				standardizeWithNoBindings(modifiedQuerySpec.getCondition(), fieldNameReplacements, usePosixRegEx);
			}
			else {
				queryParams = new QueryParams();
				standardizeWithBindings(modifiedQuerySpec.getCondition(), queryParams, fieldNameReplacements, usePosixRegEx);
			}
			TupleSchema schema = modifiedQuerySpec.getSchema();
			if (element.isASuperClass() == true) {
				//function call
				QueryFunctionCall functionCall = new QueryFunctionCall(schema, "/Instance/getExpandedName("+DEFAULT_ALIAS+")");
				QueryLiteralValue value = new QueryLiteralValue(schema, new FieldValue(BuiltInTypes.STRING, RDFTnsFlavor.BE_NAMESPACE+element.getScopeName()));
				QueryPredicate typeLimitingCondition = new QueryPredicate(schema, functionCall, QueryPredicate.EQ, value);
				modifiedQuerySpec.addAndCondition(typeLimitingCondition);
			}
			//are we dealing with a group by query
			List<String> groupByFields = modifiedQuerySpec.getGroupByFields();
			if (groupByFields.isEmpty() == false) {
				//yes we are dealing with group by query , we need to replace the "select * "with "select <groupbyfield>, count<groupbyfield>"
				if (groupByFields.size() > 1) {
					throw new QueryException("Only one group by field is supported at a time");
				}
				String groupByField = groupByFields.get(0);
				modifiedQuerySpec.addProjectionField(new QueryProjectionField(schema, groupByField));
				modifiedQuerySpec.addProjectionField(new QueryProjectionField(schema, groupByField, QueryProjectionField.COUNT));
			}
			modifiedQuerySpec.reset();
			return new ViewsQuery(modifiedQuerySpec, queryParams);
		} catch (CloneNotSupportedException e) {
			throw new QueryException(e);
		}
	}

	private void standardizeWithNoBindings(QueryCondition condition, Map<String,String> fieldNameReplacements, boolean usePosixRegExSyntax) {
		if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
			standardizeWithNoBindings(bTerm.getLeftTerm(), fieldNameReplacements, usePosixRegExSyntax);
			standardizeWithNoBindings(bTerm.getRightTerm(), fieldNameReplacements, usePosixRegExSyntax);
		} else if (condition instanceof QueryUnaryTerm) {
			QueryUnaryTerm uTerm = (QueryUnaryTerm) condition;
			standardizeWithNoBindings(uTerm.getTerm(), fieldNameReplacements, usePosixRegExSyntax);
		} else if (condition instanceof QueryPredicate) {
			QueryPredicate predicate = (QueryPredicate) condition;
			//replace the field name if provided
			if (fieldNameReplacements.containsKey(predicate.evalLeftArgument().toString()) == true) {
				predicate.updateLeftArgument(new QueryFieldName(predicate.getSchema(), fieldNameReplacements.get(predicate.evalLeftArgument().toString())));
			}
			if (predicate.getComparison().equals(QueryPredicate.IS_NULL) == true) {
				predicate.updateComparision(QueryPredicate.EQ, (QueryExpression)null);
			}
			else if (predicate.getComparison().equals(QueryPredicate.IS_NOT_NULL) == true) {
				predicate.updateComparision(QueryPredicate.NEQ, (QueryExpression)null);
			}
			else {
				QueryExpression expression = predicate.getRightArgument();
				if (expression instanceof QueryLiteralValue) {
					FieldValue literalValue = ((QueryLiteralValue) expression).getLiteralValue();
					if (literalValue.getDataType().equals(BuiltInTypes.STRING) == true) {
						String actualValue = literalValue.toString();
						actualValue = actualValue.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("\\\\"));
						literalValue = new FieldValue(BuiltInTypes.STRING, actualValue);
					}
					if (predicate.getComparison().equals(QueryPredicate.LIKE) == true) {
						literalValue = standardizeLikeExpression(literalValue, usePosixRegExSyntax);
					}
					predicate.updateRightArgument(new CacheQueryLiteralValue(expression.getSchema(), literalValue));
				}
			}
		}
	}

	private FieldValue standardizeLikeExpression(FieldValue literalValue, boolean usePosixRegExSyntax) {
		String likeExpression = literalValue.toString();
		boolean userWantsStartsWith = likeExpression.endsWith("%"); //this means user want starting with
		boolean userWantsEndsWith = likeExpression.startsWith("%"); //this means user want ending with
		if (userWantsStartsWith == true) {
			likeExpression = likeExpression.substring(0, likeExpression.length() - 1);
		}
		if (userWantsEndsWith == true) {
			likeExpression = likeExpression.substring(1);
		}
//		likeExpression = Pattern.quote(likeExpression);
		if (userWantsEndsWith == true && userWantsStartsWith == true) {
			//contains
			likeExpression = usePosixRegExSyntax == true ? Pattern.quote(likeExpression) : ".*" + Pattern.quote(likeExpression) + ".*";
		}
		else if (userWantsEndsWith == true) {
			//ends with
			likeExpression = usePosixRegExSyntax == true ? Pattern.quote(likeExpression) + "$" : ".*" + Pattern.quote(likeExpression);
		}
		else if (userWantsStartsWith == true) {
			//starts with
			likeExpression = usePosixRegExSyntax == true ? "^"+Pattern.quote(likeExpression) : Pattern.quote(likeExpression) + ".*";
		}
		return new FieldValue(BuiltInTypes.STRING, likeExpression);
	}

	private void standardizeWithBindings(QueryCondition condition, QueryParams queryParams, Map<String,String> fieldNameReplacements, boolean usePosixRegExSyntax) {
		if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
			standardizeWithBindings(bTerm.getLeftTerm(), queryParams, fieldNameReplacements, usePosixRegExSyntax);
			standardizeWithBindings(bTerm.getRightTerm(), queryParams, fieldNameReplacements, usePosixRegExSyntax);
		} else if (condition instanceof QueryUnaryTerm) {
			QueryUnaryTerm uTerm = (QueryUnaryTerm) condition;
			standardizeWithBindings(uTerm.getTerm(), queryParams, fieldNameReplacements, usePosixRegExSyntax);
		} else if (condition instanceof QueryPredicate) {
			QueryPredicate predicate = (QueryPredicate) condition;
			if (fieldNameReplacements.containsKey(predicate.evalLeftArgument().toString()) == true) {
				predicate.updateLeftArgument(new QueryFieldName(predicate.getSchema(), fieldNameReplacements.get(predicate.evalLeftArgument().toString())));
			}
			if (predicate.getComparison().equals(QueryPredicate.IS_NULL) == true) {
				predicate.updateComparision(QueryPredicate.EQ, (QueryExpression)null);
			}
			else if (predicate.getComparison().equals(QueryPredicate.IS_NOT_NULL) == true) {
				predicate.updateComparision(QueryPredicate.NEQ, (QueryExpression)null);
			}
			QueryExpression expression = predicate.getRightArgument();
			if (expression instanceof QueryLiteralValue) {
				FieldValue literalValue = ((QueryLiteralValue) expression).getLiteralValue();
				if (predicate.getComparison().equals(QueryPredicate.LIKE) == true) {
					literalValue = standardizeLikeExpression(literalValue, usePosixRegExSyntax);
					//LIKE does not support bindings
					predicate.updateRightArgument(new CacheQueryLiteralValue(expression.getSchema(), literalValue));

				} else {
					String bindName = "param" + queryParams.getParameterNames().size();
					String bindValue = literalValue.toString();
					queryParams.addParameter(bindName, literalValue.getDataType(), bindValue);
					expression = new QueryBindParameter(predicate.getSchema(), "$" + bindName);
					predicate.updateRightArgument(expression);
				}
			}
		}
	}

	String createCountQuery(ViewsQuery viewsQuery) throws CloneNotSupportedException {
		if (viewsQuery.getQuerySpec() != null) {
			QuerySpec countQuerySpec = new QuerySpec(viewsQuery.getQuerySpec().getSchema());
			//set alias
			countQuerySpec.setAlias(DEFAULT_ALIAS);
			//add projection field
			countQuerySpec.addProjectionField(new QueryProjectionField(viewsQuery.getQuerySpec().getSchema(), QueryProjectionField.ALL_FIELDS, QueryProjectionField.COUNT));
			//add condition
			if (viewsQuery.getQuerySpec().getCondition() != null) {
				countQuerySpec.setCondition((QueryCondition) viewsQuery.getQuerySpec().getCondition().clone());
			}
			//add group by
			return countQuerySpec.toString() + " group by 1";
		}
		StringBuilder sb = new StringBuilder("select count("+DEFAULT_ALIAS+") from ");
		sb.append(TupleSchemaFactory.getInstance().getTupleSchema(viewsQuery.getTypeID()).getScopeName());
		sb.append(" "+DEFAULT_ALIAS+" ");
		if (StringUtil.isEmptyOrBlank(viewsQuery.getCondition()) == false) {
			sb.append(" where ");
			sb.append(viewsQuery.getCondition());
		}
		sb.append(" group by 1");
		return sb.toString();
	}
}
