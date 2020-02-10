package com.tibco.rta.model.serialize.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.rta.MetricKey;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.FunctionDescriptor.FunctionParamValue;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LikeFilter;
import com.tibco.rta.query.filter.LogicalFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.filter.RelationalFilter;
import com.tibco.rta.query.impl.QueryFilterDefImpl;

public class QuerySerializer {

	static QuerySerializer INSTANCE = new QuerySerializer();
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

	@SuppressWarnings("unused")
	void serialize(RuleDef ruleDef, Document rootDocument, Element parentElement) throws Exception {
		NullPointerValidation(ruleDef, "RuleDef is null");
		Element ruleElement = rootDocument.createElement(ELEM_RULE_NAME);
		ruleElement.setAttribute(ATTR_NAME_NAME, ruleDef.getName());

		serializeRuleInfo(ruleDef, rootDocument, ruleElement);
		serializeRuleConditions(ruleDef, rootDocument, ruleElement);
		serializeRuleActions(ruleDef, rootDocument, ruleElement);
		if (parentElement != null) {
			parentElement.appendChild(ruleElement);
		}
	}

	private void serializeRuleActions(RuleDef ruleDef, Document rootDocument, Element ruleElement) {
		Element actionsElement = rootDocument.createElement(ELEM_ACTIONS_NAME);
		Element setActionsElement = rootDocument.createElement(ELEM_SETACTIONS_NAME);
		Element clearActionsElement = rootDocument.createElement(ELEM_CLEARACTIONS_NAME);
		Collection<ActionDef> setActions = ruleDef.getSetActionDefs();
		Collection<ActionDef> clearActions = ruleDef.getClearActionDefs();

		for (ActionDef setAction: setActions) {
			serializeRuleAction(setAction, rootDocument, setActionsElement);
		}

		for (ActionDef clearAction: clearActions) {
			serializeRuleAction(clearAction, rootDocument, clearActionsElement);
		}

		actionsElement.appendChild(setActionsElement);
		actionsElement.appendChild(clearActionsElement);
		ruleElement.appendChild(actionsElement);
		rootDocument.appendChild(ruleElement);
	}

	private void serializeRuleConditions(RuleDef ruleDef, Document rootDocument, Element ruleElement) throws Exception {
		QueryDef setCondition = ruleDef.getSetCondition();
		QueryDef clearCondition = ruleDef.getClearCondition();

		Element conditionElement = rootDocument.createElement(ELEM_CONDITION_NAME);
		Element setConditionElement = rootDocument.createElement(ELEM_SET_CONDITION);
		Element clearConditionElement = rootDocument.createElement(ELEM_CLEAR_CONDITION);

		if (setCondition != null) {
			serializeQueryDef(setCondition, rootDocument, setConditionElement);
		}

		if (clearCondition != null) {
			serializeQueryDef(clearCondition, rootDocument, clearConditionElement);
		}

		conditionElement.appendChild(setConditionElement);
		conditionElement.appendChild(clearConditionElement);
		ruleElement.appendChild(conditionElement);
	}

	private void serializeRuleAction(ActionDef actionDef, Document rootDocument, Element actionsElement) {
		Element actionElem = rootDocument.createElement(ELEM_ACTION_NAME);
		String refActionName = actionDef.getActionFunctionDescriptor().getName();
		actionElem.setAttribute(ATTR_NAME_NAME, actionDef.getName());
		if (actionDef.getAlertLevel() != null && actionDef.getAlertLevel().length() != 0) {
			actionElem.setAttribute(ATTR_ALERT_LEVEL, actionDef.getAlertLevel());
		}
		actionElem.setAttribute(ATTR_REF_NAME, refActionName);
		Element actionParamsElem = rootDocument.createElement(ELEM_ACTION_PARAMS);
		// Collection<FunctionParam> functionParam = actionDef.getActionFunctionDescriptor().getFunctionParams();
		Collection<FunctionParamValue> functionParamValues = actionDef.getActionFunctionDescriptor().getFunctionParamValues();
		Element actionParamElem;

		for (FunctionParamValue fpv: functionParamValues) {
			actionParamElem = rootDocument.createElement(ELEM_ACTION_PARAM);
			actionParamElem.setAttribute(ATTR_REF_NAME, fpv.getName());
			actionParamElem.setAttribute(ATTR_VALUE_NAME, fpv.getValue().toString());
			actionParamsElem.appendChild(actionParamElem);
		}

		// append the constraint element
		Element constraintElem = rootDocument.createElement("constraint");
		constraintElem.setAttribute(ATTR_TYPE_NAME, actionDef.getConstraint().getConstraint().toString());

		if (actionDef.getConstraint().getConstraint().equals(Constraint.TIMED)) {
			TimeBasedConstraint tc = (TimeBasedConstraint) actionDef.getConstraint();
			constraintElem.setAttribute(ATTR_FREQUENCY_NAME, "" + tc.getInvocationFrequency());
			constraintElem.setAttribute(ATTR_MAXCOUNT_NAME, "" + tc.getMaxInvocationCount());
			if (tc.getTimeConstraint() != null) {
				constraintElem.setAttribute(ATTR_TIME_CONSTRAINT, tc.getTimeConstraint().toString());
			}
		}

		actionElem.appendChild(constraintElem);
		actionElem.appendChild(actionParamsElem);
		actionsElement.appendChild(actionElem);
	}

	private void NullPointerValidation(Object object, String message) throws Exception {
		if (object == null) {
			LOGGER.log(Level.ERROR, message);
			throw new Exception(message);
		}
	}

	private void serializeRuleInfo(RuleDef ruleDef, Document rootDocument, Element ruleElement) {
		Element ruleInfoElement = rootDocument.createElement(ELEM_RULEINFO_NAME);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd G 'T'HH:mm:ss z");
		Calendar createdDateTime = ruleDef.getCreatedDate();
		Calendar modifiedDateTime = ruleDef.getModifiedDate();

		Element prioElem = rootDocument.createElement(ELEM_RULE_PRIORITY);
		prioElem.appendChild(rootDocument.createTextNode("" + ruleDef.getPriority()));
		ruleInfoElement.appendChild(prioElem);

		if (ruleDef.getUserName() != null) {
			Element userNameElement = rootDocument.createElement(ELEM_USER_NAME);
			userNameElement.appendChild(rootDocument.createTextNode(ruleDef.getUserName()));
			ruleInfoElement.appendChild(userNameElement);
		}

		if (ruleDef.getDescription() != null) {
			Element descElement = rootDocument.createElement(ELEM_DESC_NAME);
			descElement.appendChild(rootDocument.createTextNode(ruleDef.getDescription()));
			ruleInfoElement.appendChild(descElement);
		}

		if (ruleDef.getScheduleName() != null) {
			Element scheduleElement = rootDocument.createElement(ELEM_SCHEDULE_NAME);
			scheduleElement.appendChild(rootDocument.createTextNode(ruleDef.getScheduleName()));
			ruleInfoElement.appendChild(scheduleElement);
		}

		if (ruleDef.getVersion() != null) {
			Element versionElement = rootDocument.createElement(ELEM_VERSION_NAME);
			versionElement.appendChild(rootDocument.createTextNode(ruleDef.getVersion()));
			ruleInfoElement.appendChild(versionElement);
		}

		if (createdDateTime != null) {
			String temp = ft.format(createdDateTime.getTime());
			Element createdDateElement = rootDocument.createElement(ELEM_CREATED_DATE);
			createdDateElement.appendChild(rootDocument.createTextNode(temp));
			ruleInfoElement.appendChild(createdDateElement);
		}

		if (modifiedDateTime != null) {
			Element modifiedDateElement = rootDocument.createElement(ELEM_MODIFIED_DATE);
			modifiedDateElement.appendChild(rootDocument.createTextNode(ft.format(modifiedDateTime.getTime())));
			ruleInfoElement.appendChild(modifiedDateElement);
		}

		if (ruleDef.isStreamingQuery()) {
			Element sQueryElement = rootDocument.createElement(ELEM_STREAMING_QUERY);
			sQueryElement.appendChild(rootDocument.createTextNode(ruleDef.isStreamingQuery() + ""));
			ruleInfoElement.appendChild(sQueryElement);
		}

		if (!ruleDef.isEnabled()) {
			Element ruleEnabledElement = rootDocument.createElement(ELEM_RULE_ENABLED);
			ruleEnabledElement.appendChild(rootDocument.createTextNode(ruleDef.isEnabled() + ""));
			ruleInfoElement.appendChild(ruleEnabledElement);
		}

		ruleElement.appendChild(ruleInfoElement);
	}

	@SuppressWarnings("unused")
	void serializeQueryDef(QueryDef queryDef, Document rootDocument, Element parentElement) throws Exception {
		Element queryElement = rootDocument.createElement(ELEM_QUERY_NAME);
		queryElement.setAttribute(ATTR_NAME_NAME, queryDef.getName());
		queryElement.setAttribute(ATTR_TYPE_NAME, queryDef.getQueryType().toString());
		// queryElement.setAttribute(ATTR_PERSIST_NAME, String.valueOf(queryDef.isPersistable()));

		if (queryDef instanceof QueryByFilterDef) {
			QueryByFilterDef queryByFilterDef = (QueryByFilterDef) queryDef;
			queryElement.setAttribute(ATTR_QUERYDEF_TYPE, ATTR_QUERYDEFBY_FILTER);
			serializeScope(queryDef, null, rootDocument, queryElement);

			Filter filter = ((QueryByFilterDef) queryDef).getFilter();
			if (filter != null) {
				if (filter instanceof LogicalFilter) {
					Element FilterElement = rootDocument.createElement(ElementFactory.INSTANCE.getElementName(filter));
					LogicalFilter logicalFilter = (LogicalFilter) filter;
					// Get the children
					Filter[] childFilters = logicalFilter.getFilters();
					for (Filter childFilter: childFilters) {
						Element childFilterElement = ElementFactory.INSTANCE.getElement(childFilter, rootDocument, queryDef);
						FilterElement.appendChild(childFilterElement);
					}
					queryElement.appendChild(FilterElement);
				} else {
					Element childFilterElement = ElementFactory.INSTANCE.getElement(filter, rootDocument, queryDef);
					queryElement.appendChild(childFilterElement);
				}
			}
		} else if (queryDef instanceof QueryByKeyDef) {
			QueryByKeyDef queryByKeyDef = (QueryByKeyDef) queryDef;
			MetricKey key = queryByKeyDef.getQueryKey();
			queryElement.setAttribute(ATTR_QUERYDEF_TYPE, ATTR_QUERYDEFBY_KEY);
			serializeScope(queryByKeyDef, key, rootDocument, queryElement);
			
		}

		serializeQueryOrderTuples(queryDef, rootDocument, queryElement);
		serializeQueryMetaData(queryDef, rootDocument, queryElement);

		if (parentElement != null) {
			parentElement.appendChild(queryElement);
		} else {
			rootDocument.appendChild(queryElement);
		}
	}

	private void serializeQueryMetaData(QueryDef queryDef, Document rootDocument, Element queryElement) {
		Element metaDataElement = rootDocument.createElement(ELEM_METADATA_NAME);
		Element metaDataBatchElement = rootDocument.createElement(ELEM_METADATA_BATCH);
		metaDataBatchElement.appendChild(rootDocument.createTextNode(new Integer(queryDef.getBatchSize()).toString()));
		metaDataElement.appendChild(metaDataBatchElement);
		queryElement.appendChild(metaDataElement);
	}

	private void serializeQueryOrderTuples(QueryDef queryDef, Document rootDocument, Element queryElement) {
		Element oByTupleElement = rootDocument.createElement(ELEM_ORDERBY_TUPLE);
		for (MetricFieldTuple queryFieldTuple: queryDef.getOrderByTuples()) {
			serializeMetricQualifierFTuple(queryFieldTuple, rootDocument, oByTupleElement);
			serializeFilterQaulifierFTuple(queryFieldTuple, rootDocument, oByTupleElement);
		}

		queryElement.appendChild(oByTupleElement);
	}

	private void serializeFilterQaulifierFTuple(MetricFieldTuple queryFieldTuple, Document rootDocument, Element oByTupleElement) {
		if (queryFieldTuple.getKeyQualifier() != null) {
			FilterKeyQualifier fKeyQualifier = queryFieldTuple.getKeyQualifier();
			String key = queryFieldTuple.getKey();
			Element fKeyElement = rootDocument.createElement(ELEM_FILTER_QUALIFIER);

			if (fKeyQualifier.equals(FilterKeyQualifier.DIMENSION_NAME)) {
				fKeyElement.setAttribute(ATTR_TYPE_NAME, FilterKeyQualifier.DIMENSION_NAME.toString());
			} else if (fKeyQualifier.equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
				fKeyElement.setAttribute(ATTR_TYPE_NAME, FilterKeyQualifier.MEASUREMENT_NAME.toString());
			}
			fKeyElement.setAttribute(ATTR_VALUE_NAME, key);
			oByTupleElement.appendChild(fKeyElement);
		}
	}

	private void serializeMetricQualifierFTuple(MetricFieldTuple queryFieldTuple, Document rootDocument, Element oByTupleElement) {
		if (queryFieldTuple.getMetricQualifier() != null) {
			MetricQualifier metricQualifier = queryFieldTuple.getMetricQualifier();
			String key = queryFieldTuple.getKey();
			Element mQualifierElement = rootDocument.createElement(ELEM_METRIC_QUALIFIER);

			mQualifierElement.setAttribute(ATTR_LEVEL_NAME, MetricQualifier.DIMENSION_LEVEL.toString());
			mQualifierElement.setAttribute(ATTR_VALUE_NAME, key);
			oByTupleElement.appendChild(mQualifierElement);
		}
	}

	private void serializeScope(QueryDef queryDef, MetricKey key, Document rootDocument, Element queryElement) throws Exception {
		Element scopeElement = rootDocument.createElement(ELEM_SCOPE_NAME);
		Element schemaElement = rootDocument.createElement(ELEM_SCHEMA_NAME);
		Element cubeElement = rootDocument.createElement(ELEM_CUBE_NAME);
		Element hierarchyElement = rootDocument.createElement(ELEM_HIERARCHY_NAME);

		schemaElement.appendChild(rootDocument.createTextNode(queryDef.getSchemaName()));
		cubeElement.appendChild(rootDocument.createTextNode(queryDef.getCubeName()));
		hierarchyElement.appendChild(rootDocument.createTextNode(queryDef.getHierarchyName()));

		scopeElement.appendChild(schemaElement);
		scopeElement.appendChild(cubeElement);
		scopeElement.appendChild(hierarchyElement);

		if (queryDef instanceof QueryFilterDefImpl) {
			Element measurementElement = rootDocument.createElement(ELEM_MEASUREMENT_NAME);
			QueryFilterDefImpl queryDefImpl = (QueryFilterDefImpl) queryDef;
			if (queryDefImpl.getMeasurementName() == null) {
				LOGGER.log(Level.ERROR, "Invalid Measurement:measurement is not defined for query:%s", queryDef.getName());;
				throw new Exception("Invalid Measurement:measurement is not defined for query:" + queryDef.getName());
			}
			measurementElement.appendChild(rootDocument.createTextNode(queryDefImpl.getMeasurementName()));
			scopeElement.appendChild(measurementElement);
		} else if (key != null) {
			Element dimensionElement = rootDocument.createElement(ELEM_DIMENSION_NAME);
			dimensionElement.appendChild(rootDocument.createTextNode(key.getDimensionLevelName()));
			scopeElement.appendChild(dimensionElement);
			serializeQueryDefKey(queryDef, key, rootDocument, queryElement);
		}

		queryElement.appendChild(scopeElement);
	}

	private void serializeQueryDefKey(QueryDef queryDef, MetricKey key,
			Document rootDocument, Element queryElement) {
		Element keyElement = rootDocument.createElement(ELEM_KEY_NAME);

		for (String dimName : key.getDimensionNames()) {
			Element dimElem = rootDocument.createElement(ELEM_DIMENSION_LEVEL);
			dimElem.setAttribute(ATTR_NAME_NAME, dimName);
			Object value = key.getDimensionValue(dimName);
			if (value != null) {
				String dataType = value.getClass().getSimpleName();
				dimElem.setAttribute(ATTR_DATATYPE_NAME, dataType);
				dimElem.appendChild(rootDocument.createTextNode(value + ""));
			} else {
				dimElem.appendChild(rootDocument.createTextNode(""));
			}
			
			keyElement.appendChild(dimElem);
		}
		queryElement.appendChild(keyElement);
	}

	private static class ElementFactory {

		private ElementFactory() {
		}

		static ElementFactory INSTANCE = new ElementFactory();

		Element getElement(Filter filter, Document rootDocument, QueryDef queryDef) {
			if (filter instanceof AndFilter) {
				Element parentElement = rootDocument.createElement(ELEM_AND_FILTER);
				Element childFilterElement = null;
				Filter[] childFilters = ((LogicalFilter) filter).getFilters();
				for (Filter childFilter: childFilters) {
					childFilterElement = ElementFactory.INSTANCE.getElement(childFilter, rootDocument, queryDef);
					parentElement.appendChild(childFilterElement);
				}
				return parentElement;

			} else if (filter instanceof OrFilter) {
				Element parentElement = rootDocument.createElement(ELEM_OR_FILTER);
				Element childFilterElement = null;
				Filter[] childFilters = ((LogicalFilter) filter).getFilters();
				for (Filter childFilter: childFilters) {
					childFilterElement = ElementFactory.INSTANCE.getElement(childFilter, rootDocument, queryDef);
					parentElement.appendChild(childFilterElement);
				}
				return parentElement;
			} else if (filter instanceof NotFilter) {
				Element parentElement = rootDocument.createElement(ELEM_NOT_FILTER);
				Element childFilterElement = null;
				Filter baseFilter = ((NotFilter) filter).getBaseFilter();
				childFilterElement = ElementFactory.INSTANCE.getElement(baseFilter, rootDocument, queryDef);
				if (childFilterElement != null) {
					parentElement.appendChild(childFilterElement);
				}

				return parentElement;
			} else if (filter instanceof RelationalFilter) {
				Element cFElement = rootDocument.createElement(ElementFactory.INSTANCE.getElementName(filter));
				Element cFNameElement = rootDocument.createElement(ELEM_FILTER_NAME);
				Element cFValuesElement = null;
				Element cFValueElement = null;
				RelationalFilter rFilter = (RelationalFilter) filter;

				// FIXME handle null values

				if (filter instanceof InFilter) {
					cFValuesElement = rootDocument.createElement(ELEM_VALUES_NAME);
					List<Object> values = ((InFilter) filter).getInSet();

					for (int i = 0; i < values.size(); i++) {
						cFValueElement = rootDocument.createElement(ELEM_VALUE_NAME);
						String dataType = values.get(i).getClass().getSimpleName();
						cFValueElement.setAttribute(ATTR_DATATYPE_NAME, dataType);
						cFValueElement.appendChild(rootDocument.createTextNode(values.get(i).toString()));
						cFValuesElement.appendChild(cFValueElement);
					}
					cFElement.appendChild(cFValuesElement);
				} else {
					cFValueElement = rootDocument.createElement(ELEM_FILTER_VALUE);
					Object filterValue = rFilter.getValue();
					String dataType = null;
					if (filterValue == null) {
						dataType = new String().getClass().getSimpleName();
						cFValueElement.appendChild(rootDocument.createTextNode(""));
					} else {
						dataType = rFilter.getValue().getClass().getSimpleName();
						cFValueElement.appendChild(rootDocument.createTextNode(rFilter.getValue().toString()));
					}
					cFValueElement.setAttribute(ATTR_DATATYPE_NAME, dataType);
					cFElement.appendChild(cFValueElement);
				}

				if (rFilter.getKeyQualifier() != null) {
					FilterKeyQualifier keyQualifier = rFilter.getKeyQualifier();
					Element cFQualifierElement = rootDocument.createElement(ELEM_FILTER_QUALIFIER);
					if (keyQualifier.equals(FilterKeyQualifier.DIMENSION_NAME)) {
						cFQualifierElement.setAttribute(ATTR_TYPE_NAME, FilterKeyQualifier.DIMENSION_NAME.toString());
					} else if (keyQualifier.equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
						cFQualifierElement.setAttribute(ATTR_TYPE_NAME, FilterKeyQualifier.MEASUREMENT_NAME.toString());
					} else if (keyQualifier.equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
						cFQualifierElement.setAttribute(ATTR_TYPE_NAME, FilterKeyQualifier.MEASUREMENT_NAME.toString());
					}
					cFNameElement.appendChild(rootDocument.createTextNode(rFilter.getKey()));
					cFElement.appendChild(cFNameElement);
					cFElement.appendChild(cFQualifierElement);
				}

				if (rFilter.getMetricQualifier() != null) {
					Element cFMQualifierElement = rootDocument.createElement(ELEM_METRIC_QUALIFIER);
					MetricQualifier metricQualifier = rFilter.getMetricQualifier();
					// TODO: check if dimension name is specified or not
					cFMQualifierElement.setAttribute(ATTR_LEVEL_NAME, MetricQualifier.DIMENSION_LEVEL.toString());
					cFElement.appendChild(cFMQualifierElement);
				}
				return cFElement;
			}
			return null;
		}

		String getElementName(Filter filter) {

			if (filter instanceof AndFilter) {
				return ELEM_AND_FILTER;
			} else if (filter instanceof OrFilter) {
				return ELEM_OR_FILTER;
			} else if (filter instanceof EqFilter) {
				return ELEM_EQFILTER_NAME;
			} else if (filter instanceof NEqFilter) {
				return ELEM_NEQFILTER_NAME;
			} else if (filter instanceof GtFilter) {
				return ELEM_GTFILTER_NAME;
			} else if (filter instanceof GEFilter) {
				return ELEM_GEFILTER_NAME;
			} else if (filter instanceof LEFilter) {
				return ELEM_LEFILTER_NAME;
			} else if (filter instanceof LtFilter) {
				return ELEM_LTFILTER_NAME;
			} else if (filter instanceof NotFilter) {
				return ELEM_NOT_FILTER;
			} else if (filter instanceof LikeFilter) {
				return ELEM_LIKEFILTER_NAME;
			} else if (filter instanceof InFilter) {
				return ELEM_INFILTER_NAME;
			}
			return null;
		}
	}

}
