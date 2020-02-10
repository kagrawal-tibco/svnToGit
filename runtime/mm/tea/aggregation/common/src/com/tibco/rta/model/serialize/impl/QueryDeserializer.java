package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.FunctionDescriptorImpl;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.model.rule.impl.ActionDefImpl;
import com.tibco.rta.model.rule.impl.ActionFunctionDescriptorImpl;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.model.rule.mutable.MutableInvokeConstraint;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.rule.mutable.MutableTimeBasedConstraint;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.query.impl.QueryFilterDefImpl;
import com.tibco.rta.query.impl.QueryKeyDefImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ALERT_LEVEL;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_LEVEL_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_QUERYDEFBY_FILTER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_QUERYDEFBY_KEY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_QUERYDEF_TYPE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_PARAM;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_PARAMS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_AND_FILTER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CLEARACTIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CLEAR_CONDITION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CONDITION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CREATED_DATE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DESC_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_LEVEL;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_EQFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FILTER_QUALIFIER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_GEFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_GTFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_INFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_KEY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_LEFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_LIKEFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_LTFILTER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METADATA_BATCH;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METADATA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRIC_QUALIFIER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MODIFIED_DATE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_NOT_FILTER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ORDERBY_TUPLE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_OR_FILTER;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_QUERY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULEINFO_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_ENABLED;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_PRIORITY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEDULE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCOPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SETACTIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SET_CONDITION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_STREAMING_QUERY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_USER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VALUES_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VERSION_NAME;

public class QueryDeserializer {

	static QueryDeserializer INSTANCE = new QueryDeserializer();
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());
	DocumentBuilder builder = null;

	public RuleDef deserializeRule(InputSource in, DocumentBuilder documentBuilder, Element parentElement) throws Exception {
		MutableRuleDef ruleDef = null;
		RuleFactory rfac = new RuleFactory();
		builder = documentBuilder;
		Element rootElement;

		if (parentElement == null) {
			Document rootDocument = builder.parse(in);
			rootElement = rootDocument.getDocumentElement();
		} else {
			rootElement = parentElement;
		}

		if (ELEM_RULE_NAME.equals(rootElement.getNodeName())) {
			String name = rootElement.getAttribute(ATTR_NAME_NAME);
			ruleDef = rfac.newRuleDef(name);
			NodeList ruleInfoNodeList = rootElement.getElementsByTagName(ELEM_RULEINFO_NAME);
			if (ruleInfoNodeList.getLength() != 0) {
				deserializeRuleInfo(ruleDef, ruleInfoNodeList.item(0));
			}

			NodeList conditionsNodeList = rootElement.getElementsByTagName(ELEM_CONDITION_NAME);
			if (conditionsNodeList.getLength() != 0) {
				deserializeCondition(ruleDef, conditionsNodeList.item(0));
			}

			NodeList actionsNodeList = rootElement.getElementsByTagName(ELEM_ACTIONS_NAME);
			if (conditionsNodeList.getLength() != 0) {
				deserializeActions(ruleDef, actionsNodeList.item(0));
			}
		}
		return ruleDef;
	}

	private void deserializeRuleInfo(RuleDef ruleDef, Node ruleInfo) throws Exception {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd G 'T'HH:mm:ss z");
		if (((Element) ruleInfo).getElementsByTagName(ELEM_SCHEDULE_NAME).getLength() != 0) {
			((RuleDefImpl) ruleDef).setScheduleName(((Element) ruleInfo).getElementsByTagName(ELEM_SCHEDULE_NAME).item(0).getTextContent());
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_RULE_PRIORITY).getLength() != 0) {
			String prio = ((Element) ruleInfo).getElementsByTagName(ELEM_RULE_PRIORITY).item(0).getTextContent();
			((RuleDefImpl) ruleDef).setPriority(Integer.parseInt(prio));
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_USER_NAME).getLength() != 0) {
			((RuleDefImpl) ruleDef).setUserName(((Element) ruleInfo).getElementsByTagName(ELEM_USER_NAME).item(0).getTextContent());
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_DESC_NAME).getLength() != 0) {
			((RuleDefImpl) ruleDef).setDescription(((Element) ruleInfo).getElementsByTagName(ELEM_DESC_NAME).item(0).getTextContent());
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_VERSION_NAME).getLength() != 0) {
			((RuleDefImpl) ruleDef).setVersion(((Element) ruleInfo).getElementsByTagName(ELEM_VERSION_NAME).item(0).getTextContent());
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_CREATED_DATE).getLength() != 0) {
			Calendar createdDate = Calendar.getInstance();
			String source = ((Element) ruleInfo).getElementsByTagName(ELEM_CREATED_DATE).item(0).getTextContent();
			Date date = ft.parse(source);
			createdDate.setTime(date);
			((RuleDefImpl) ruleDef).setCreatedDate(createdDate);
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_MODIFIED_DATE).getLength() != 0) {
			Calendar modifiedDate = Calendar.getInstance();
			String source = ((Element) ruleInfo).getElementsByTagName(ELEM_MODIFIED_DATE).item(0).getTextContent();
			Date date = ft.parse(source);
			modifiedDate.setTime(date);
			((RuleDefImpl) ruleDef).setModifiedDate(modifiedDate);
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_STREAMING_QUERY).getLength() != 0) {
			String enStr = ((Element) ruleInfo).getElementsByTagName(ELEM_STREAMING_QUERY).item(0).getTextContent();
			Boolean isStreaming = Boolean.valueOf(enStr);
			((RuleDefImpl) ruleDef).setAsStreamingQuery(isStreaming);
		} else {
			((RuleDefImpl) ruleDef).setEnabled(false);
		}

		if (((Element) ruleInfo).getElementsByTagName(ELEM_RULE_ENABLED).getLength() != 0) {
			String enStr = ((Element) ruleInfo).getElementsByTagName(ELEM_RULE_ENABLED).item(0).getTextContent();
			Boolean isEnabled = Boolean.valueOf(enStr);
			((RuleDefImpl) ruleDef).setEnabled(isEnabled);
		} else {
			((RuleDefImpl) ruleDef).setEnabled(true);
		}
	}

	private void deserializeActions(MutableRuleDef ruleDef, Node actionsNode) throws Exception {
		setActionDefs(ruleDef, actionsNode, ELEM_SETACTIONS_NAME, true);
		setActionDefs(ruleDef, actionsNode, ELEM_CLEARACTIONS_NAME, false);
	}

	private void setActionDefs(MutableRuleDef ruleDef, Node actionsNode, String tagName, boolean set) throws Exception, DataTypeMismatchException {
		if (((Element) actionsNode).getElementsByTagName(tagName).getLength() != 0) {
			NodeList setActionsElement = ((Element) actionsNode).getElementsByTagName(tagName);
			NodeList actionList = ((Element) setActionsElement.item(0)).getElementsByTagName(ELEM_ACTION_NAME);

			for (int i = 0; i < actionList.getLength(); i++) {
				if (actionList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element actionElem = (Element) actionList.item(i);
					String refActionName = actionElem.getAttribute(ATTR_REF_NAME);
					String actionName = actionElem.getAttribute(ATTR_NAME_NAME);
					String alertLevel = actionElem.getAttribute(ATTR_ALERT_LEVEL);
					// String value=actionElem.getAttribute(ATTR_VALUE_NAME);
					ActionFunctionDescriptor descRef = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor(refActionName);

					if (descRef == null) {
						LOGGER.log(Level.ERROR, "Invalid Action:%s is not defined in action catlog", refActionName);
						throw new Exception("Invalid Action:" + refActionName + " is not defined in action catlog");
					}


					ActionFunctionDescriptorImpl descriptor = new ActionFunctionDescriptorImpl(descRef.getName(), descRef.getCategory(), descRef.getImplClass(), null, descRef.getDescription());

					MutableInvokeConstraint constraint = getConstraint(actionElem);
					MutableActionDef actionDef = null;

					if (tagName.equals(ELEM_SETACTIONS_NAME)) {
						actionDef = RuleFactory.INSTANCE.newSetActionDef(ruleDef, descriptor, constraint);
					} else if (tagName.equals(ELEM_CLEARACTIONS_NAME)) {
						actionDef = RuleFactory.INSTANCE.newClearActionDef(ruleDef, descriptor, constraint);
					}

					actionDef.setName(actionName);
					actionDef.setAlertLevel(alertLevel);
					((ActionDefImpl)actionDef).setRuleDef(ruleDef);

					NodeList actionParams = actionElem.getElementsByTagName(ELEM_ACTION_PARAMS);
					NodeList actionParam = ((Element) actionParams.item(0)).getElementsByTagName(ELEM_ACTION_PARAM);

					for (int j = 0; j < actionParam.getLength(); j++) {
						if (actionParam.item(j).getNodeType() == Node.ELEMENT_NODE) {
							String pName = ((Element) actionParam.item(j)).getAttribute(ATTR_REF_NAME);
							String pValue = ((Element) actionParam.item(j)).getAttribute(ATTR_VALUE_NAME);
							FunctionParam param = descRef.getFunctionParam(pName);

							if (param == null) {
								LOGGER.log(Level.ERROR, "Invalid parameter:%s for action:%s", pName, refActionName);
								throw new Exception("Invalid parameter:" + pName + " for action:" + refActionName);
							}
							ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
							paramValue.setName(param.getName());
							paramValue.setDataType(param.getDataType());
							paramValue.setIndex(param.getIndex());
							paramValue.setDescription(param.getDescription());
							paramValue.setValue(pValue);
							descriptor.addFunctionParamValue(paramValue);
						}
					}

				}
			}
		}
	}

	private MutableInvokeConstraint getConstraint(Element actionElem) {
		Element constraintElem = (Element) actionElem.getElementsByTagName("constraint").item(0);
		String type = constraintElem.getAttribute("type");
		InvokeConstraint.Constraint constraint = Enum.valueOf(InvokeConstraint.Constraint.class, type);

		MutableInvokeConstraint constraint1 = RuleFactory.INSTANCE.newInvokeConstraint(constraint);
		if (constraint1.getConstraint().equals(Constraint.TIMED)) {
			MutableTimeBasedConstraint tbc = (MutableTimeBasedConstraint) constraint1;
			String freq = constraintElem.getAttribute("frequency");
			String maxCount = constraintElem.getAttribute("maxCount");
			String timeConstraint = constraintElem.getAttribute("timeconstraint");
			tbc.setInvocationFrequency(Long.parseLong(freq));
			tbc.setMaxInvocationCount(Long.parseLong(maxCount));
			tbc.setTimeConstraint(Enum.valueOf(TimeBasedConstraint.Constraint.class, timeConstraint));
		}
		return constraint1;
	}

	private void deserializeCondition(RuleDef ruleDef, Node conditionsNode) throws Exception {
		QueryDef setCondition = null;
		QueryDef clearCondition = null;

		if (((Element) conditionsNode).getElementsByTagName(ELEM_SET_CONDITION).getLength() != 0) {
			NodeList setCondElement = ((Element) conditionsNode).getElementsByTagName(ELEM_SET_CONDITION);
			NodeList queryElement = ((Element) setCondElement.item(0)).getElementsByTagName(ELEM_QUERY_NAME);
			if (queryElement.getLength() != 0) {
				setCondition = deserializeQuery(null, (Element) queryElement.item(0), builder);
			}
			if (setCondition != null) {
				((RuleDefImpl) ruleDef).setSetCondition(setCondition);
			}
		}

		if (((Element) conditionsNode).getElementsByTagName(ELEM_CLEAR_CONDITION).getLength() != 0) {
			NodeList clearCondElement = ((Element) conditionsNode).getElementsByTagName(ELEM_CLEAR_CONDITION);
			NodeList queryElement = ((Element) clearCondElement.item(0)).getElementsByTagName(ELEM_QUERY_NAME);
			if (queryElement.getLength() != 0) {
				clearCondition = deserializeQuery(null, (Element) queryElement.item(0), builder);
			}
			if (clearCondition != null) {
				((RuleDefImpl) ruleDef).setClearCondition(clearCondition);
			}
		}
	}

	public QueryDef deserializeQuery(InputSource in, Element parentElement, DocumentBuilder builder) throws Exception {
		QueryDef queryDef = null;
		Element rootElement;
		Filter filter = null;

		if (parentElement == null) {
			Document rootDocument = builder.parse(in);
			rootElement = rootDocument.getDocumentElement();
		} else {
			rootElement = parentElement;
		}

		if (ELEM_QUERY_NAME.equals(rootElement.getNodeName())) {
			if (rootElement.getAttribute(ATTR_QUERYDEF_TYPE).equals(ATTR_QUERYDEFBY_KEY)) {
				queryDef = new QueryKeyDefImpl();
			} else if (rootElement.getAttribute(ATTR_QUERYDEF_TYPE).equals(ATTR_QUERYDEFBY_FILTER)) {
				queryDef = new QueryFilterDefImpl();
			}

			NullPointerValidation(queryDef, "QueryDefType is not specified");
			queryDef.setName(rootElement.getAttribute(ATTR_NAME_NAME));
			// queryDef.persistQuery(Boolean.valueOf(rootElement.getAttribute(ATTR_PERSIST_NAME)));
			queryDef.setQueryType(QueryType.valueOf(rootElement.getAttribute(ATTR_TYPE_NAME)));
			NodeList modelNodeList = rootElement.getElementsByTagName(ELEM_SCOPE_NAME);

			if (modelNodeList.getLength() != 0) {
				deserializeScopeElement(queryDef, modelNodeList, rootElement);
			}

			if (queryDef instanceof QueryByFilterDef) {
				for (int item = 0; item < rootElement.getChildNodes().getLength(); item++) {
					if (rootElement.getChildNodes().item(item).getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) rootElement.getChildNodes().item(item);
						if (FilterFactory.getTagName(childElement) != null) {
							filter = FilterFactory.INSTANCE.getFilter(childElement);
							if (filter != null) {
								((QueryByFilterDef) queryDef).setFilter(filter);
							}
							break;
						}
					}
				}
			}

			NodeList oByTupleNodeList = rootElement.getElementsByTagName(ELEM_ORDERBY_TUPLE);
			if (oByTupleNodeList.getLength() != 0) {
				deserializeOrderByTupleElement(queryDef, oByTupleNodeList);
			}

			NodeList metaDataNodeList = rootElement.getElementsByTagName(ELEM_METADATA_NAME);
			if (metaDataNodeList.getLength() != 0) {
				deserializeMetaDataElement(queryDef, metaDataNodeList);
			}
		}
		return queryDef;
	}

	private void deserializeOrderByTupleElement(QueryDef queryDef, NodeList oByTupleNodeList) {
		int i;
		Element oByTupleElement = (Element) oByTupleNodeList.item(0);
		NodeList fQualifierNodeList = oByTupleElement.getElementsByTagName(ELEM_FILTER_QUALIFIER);
		NodeList mQualifierNodeList = oByTupleElement.getElementsByTagName(ELEM_METRIC_QUALIFIER);

		for (i = 0; i < fQualifierNodeList.getLength(); i++) {
			String type = ((Element) fQualifierNodeList.item(i)).getAttribute(ATTR_TYPE_NAME);
			String key = ((Element) fQualifierNodeList.item(i)).getAttribute(ATTR_VALUE_NAME);
			FilterKeyQualifier fQualifier = FilterKeyQualifier.valueOf(type);
			queryDef.addOrderByTuple(fQualifier, key);
		}

		for (i = 0; i < mQualifierNodeList.getLength(); i++) {
			String level = ((Element) mQualifierNodeList.item(i)).getAttribute(ATTR_LEVEL_NAME);
			String key = ((Element) mQualifierNodeList.item(i)).getAttribute(ATTR_VALUE_NAME);
			MetricQualifier mQualifier = MetricQualifier.valueOf(level);
			queryDef.addOrderByTuple(mQualifier);

		}
	}

	private static NodeList getChildNodes(Element parentElement, String tagName) {
		NodeList childNodes = parentElement.getElementsByTagName(tagName);
		return childNodes;
	}

	private static class FilterFactory {

		private FilterFactory() {
		}

		static FilterFactory INSTANCE = new FilterFactory();

		Filter getFilter(Element element) throws Exception {
			Filter filter;
			QueryFactory qfac = QueryFactory.INSTANCE;
			int i;

			if (element.getNodeName().equals(ELEM_AND_FILTER)) {
				filter = qfac.newAndFilter();
				Filter childFilter;
				Element andElement = element;

				for (i = 0; i < andElement.getChildNodes().getLength(); i++) {
					if (andElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) andElement.getChildNodes().item(i);
						if ((childFilter = FilterFactory.INSTANCE.getFilter(childElement)) != null) {
							((AndFilter) filter).addFilter(childFilter);
						}
					}
				}
				return filter;

			} else if (element.getNodeName().equals(ELEM_OR_FILTER)) {
				filter = qfac.newOrFilter();
				Filter childFilter;
				Element orElement = element;

				for (i = 0; i < orElement.getChildNodes().getLength(); i++) {
					if (orElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) orElement.getChildNodes().item(i);
						if ((childFilter = FilterFactory.INSTANCE.getFilter(childElement)) != null) {
							((OrFilter) filter).addFilter(childFilter);
						}
					}
				}
				return filter;
			} else if (element.getNodeName().equals(ELEM_NOT_FILTER)) {
				Filter childFilter;
				Element notElement = element;
				filter = null;

				for (i = 0; i < notElement.getChildNodes().getLength() && notElement.getChildNodes().getLength() < 2; i++) {
					if (notElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) notElement.getChildNodes().item(i);
						if ((childFilter = FilterFactory.INSTANCE.getFilter(childElement)) != null) {
							filter = qfac.newNotFilter(childFilter);
						}
					}
				}
				if (filter == null) {
					filter = qfac.newNotFilter();
				}
				return filter;
			} else {
				String tagName = getTagName(element);
				if (tagName != null) {
					Element filterElement = getElement(element, tagName);
					String strValue = getValueFromElement(filterElement);
					Object value = null;
					String dataType = null;
					if (strValue.equals("")) {
						value = null;
					} else {
						dataType = getDataTypeFromElement(filterElement);
						value = getDataTypeValue(dataType, strValue);
					}

					NodeList fQualifierNodeList = getChildNodes(filterElement, ELEM_FILTER_QUALIFIER);
					NodeList mQualifierNodeList = getChildNodes(filterElement, ELEM_METRIC_QUALIFIER);

					FilterKeyQualifier fQualifier = null;
					MetricQualifier mQualifier = null;
					String name = "";
					Filter elseFilter;

					if (tagName.equals(ELEM_EQFILTER_NAME)) {
						if (mQualifierNodeList.getLength() > 0) {
							String level = ((Element) mQualifierNodeList.item(0)).getAttribute(ATTR_LEVEL_NAME);
							mQualifier = MetricQualifier.valueOf(level);
							elseFilter = getFilter(mQualifier, value, tagName);
							return elseFilter;
						}

					} else if (tagName.equals(ELEM_INFILTER_NAME)) {
						InFilter inFilter = null;
						Node valuesNList = filterElement.getElementsByTagName(ELEM_VALUES_NAME).item(0);
						NodeList valueList = ((Element) valuesNList).getElementsByTagName(ELEM_VALUE_NAME);
						List<Object> values = new LinkedList<Object>();

						for (i = 0; i < valueList.getLength(); i++) {
							if (valueList.item(i).getNodeType() == Node.ELEMENT_NODE) {
								strValue = valueList.item(i).getTextContent();
								if (strValue.equals("")) {
									value = null;
								} else {
									dataType = ((Element) valueList.item(i)).getAttribute(ATTR_DATATYPE_NAME);
									value = getDataTypeValue(dataType, strValue);
								}
								values.add(value);
							}
						}

						if (mQualifierNodeList.getLength() != 0) {
							String level = ((Element) mQualifierNodeList.item(0)).getAttribute(ATTR_LEVEL_NAME);
							mQualifier = MetricQualifier.valueOf(level);
							inFilter = qfac.newInFilter(mQualifier);
						}

						if (fQualifierNodeList.getLength() != 0) {
							name = filterElement.getElementsByTagName(ELEM_NAME_NAME).item(0).getTextContent();
							String type = ((Element) fQualifierNodeList.item(0)).getAttribute(ATTR_TYPE_NAME);
							fQualifier = FilterKeyQualifier.valueOf(type);
							inFilter = qfac.newInFilter(fQualifier, name);
						}

						for (Object tmp : values) {
							inFilter.addToSet(tmp);
						}

						return inFilter;
					} else if (tagName.equals(ELEM_LIKEFILTER_NAME)) {
						if (fQualifierNodeList.getLength() != 0) {
							String type = ((Element) fQualifierNodeList.item(0)).getAttribute(ATTR_TYPE_NAME);
							fQualifier = FilterKeyQualifier.valueOf(type);
						}

						if (mQualifierNodeList.getLength() != 0) {
							String level = ((Element) mQualifierNodeList.item(0)).getAttribute(ATTR_LEVEL_NAME);
							mQualifier = MetricQualifier.valueOf(level);
							return qfac.newLikeFilter(mQualifier, strValue);
						}
						name = getNameFromElement(filterElement);
						return qfac.newLikeFilter(fQualifier, name, strValue);
					}

					if (fQualifierNodeList.getLength() != 0) {
						String type = ((Element) fQualifierNodeList.item(0)).getAttribute(ATTR_TYPE_NAME);
						fQualifier = FilterKeyQualifier.valueOf(type);
					}

					name = getNameFromElement(filterElement);
					elseFilter = getFilter(fQualifier, name, value, tagName);
					return elseFilter;
				}
			}

			return null;
		}

		private String getNameFromElement(Element filterElement) {
			return filterElement.getElementsByTagName(ELEM_NAME_NAME).item(0).getTextContent();
		}

		private Filter getFilter(FilterKeyQualifier fQualifier, String name, Object value, String tagName) {
			QueryFactory qFac = QueryFactory.INSTANCE;

			if (tagName.equals(ELEM_EQFILTER_NAME)) {
				return qFac.newEqFilter(fQualifier, name, value);
			} else if (tagName.equals(ELEM_GTFILTER_NAME)) {
				return qFac.newGtFilter(fQualifier, name, value);
			} else if (tagName.equals(ELEM_GEFILTER_NAME)) {
				return qFac.newGEFilter(fQualifier, name, value);
			} else if (tagName.equals(ELEM_LEFILTER_NAME)) {
				return qFac.newLEFilter(fQualifier, name, value);
			} else if (tagName.equals(ELEM_LTFILTER_NAME)) {
				return qFac.newLtFilter(fQualifier, name, value);
			} else if (tagName.equals(ELEM_LIKEFILTER_NAME)) {
				return qFac.newLikeFilter(fQualifier, name, value.toString());
			} else if (tagName.equals(ELEM_INFILTER_NAME)) {
				return qFac.newInFilter(fQualifier, name);
			}

			return null;
		}

		private Filter getFilter(MetricQualifier mQualifier, Object value, String tagName) {
			QueryFactory qFac = QueryFactory.INSTANCE;

			if (tagName.equals(ELEM_EQFILTER_NAME)) {
				return qFac.newEqFilter(mQualifier, value);
			} else if (tagName.equals(ELEM_LIKEFILTER_NAME)) {
				return qFac.newLikeFilter(mQualifier, value.toString());
			} else if (tagName.equals(ELEM_INFILTER_NAME)) {
				return qFac.newInFilter(mQualifier);
			}

			return null;
		}

		private String getDataTypeFromElement(Element filterElement) {
			return ((Element) filterElement.getElementsByTagName(ELEM_VALUE_NAME).item(0)).getAttribute(ATTR_DATATYPE_NAME);
		}

		private String getValueFromElement(Element filterElement) {
			return filterElement.getElementsByTagName(ELEM_VALUE_NAME).item(0).getTextContent();
		}

		private Element getElement(Element element, String tagName) {
			Element tagedElement = null;
			if (element.getElementsByTagName(tagName).getLength() == 0) {
				tagedElement = element;
			} else {
				tagedElement = (Element) element.getElementsByTagName(tagName).item(0);
			}

			return tagedElement;
		}

		private static String getTagName(Element element) {
			List<String> filterNames = new ArrayList<String>();
			filterNames.add(ELEM_EQFILTER_NAME);
			filterNames.add(ELEM_GTFILTER_NAME);
			filterNames.add(ELEM_GEFILTER_NAME);
			filterNames.add(ELEM_LTFILTER_NAME);
			filterNames.add(ELEM_LIKEFILTER_NAME);
			filterNames.add(ELEM_INFILTER_NAME);
			filterNames.add(ELEM_LEFILTER_NAME);
			filterNames.add(ELEM_OR_FILTER);
			filterNames.add(ELEM_AND_FILTER);
			filterNames.add(ELEM_NOT_FILTER);

			for (String filterName : filterNames) {
				if (element.getNodeName().equals(filterName)) {
					return filterName;
				}
			}
			return null;
		}

		private Object getDataTypeValue(String dType, String value) throws Exception {
			if (DataType.INTEGER.toString().equalsIgnoreCase(dType)) {
				return Integer.parseInt(value);
			}
			if (DataType.LONG.toString().equalsIgnoreCase(dType)) {
				return Long.parseLong(value);
			}
			if (DataType.DOUBLE.toString().equalsIgnoreCase(dType)) {
				return Double.parseDouble(value);
			}
			if (DataType.BOOLEAN.toString().equalsIgnoreCase(dType)) {
				return Boolean.parseBoolean(value);
			}
			if (DataType.STRING.toString().equalsIgnoreCase(dType)) {
				return value;
			}
			;
			LOGGER.log(Level.ERROR, "Invalid Data Type:%s\tTaking defult type as String", dType);
			return value;
		}

	}

	private void deserializeScopeElement(QueryDef queryDef, NodeList modelNodeList, Element rootElement) throws Exception {
		RtaSchema schema = null;
		Cube cube = null;
		String schemaName = null, cubeName = null, hierarchyName = null, dimensionName = null;

		if (((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_SCHEMA_NAME).getLength() != 0) {
			schemaName = ((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_SCHEMA_NAME).item(0).getTextContent();
		}

		if (((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_CUBE_NAME).getLength() != 0) {
			cubeName = ((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_CUBE_NAME).item(0).getTextContent();
		}

		if (((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_HIERARCHY_NAME).getLength() != 0) {
			hierarchyName = ((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_HIERARCHY_NAME).item(0).getTextContent();
		}

		if (queryDef instanceof QueryByFilterDef) {
			((QueryByFilterDef) queryDef).setSchemaName(schemaName);
			((QueryByFilterDef) queryDef).setCubeName(cubeName);
			((QueryByFilterDef) queryDef).setHierarchyName(hierarchyName);

			if (((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_MEASUREMENT_NAME).getLength() != 0) {
				String measurementName = ((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_MEASUREMENT_NAME).item(0).getTextContent();
				((QueryByFilterDef) queryDef).setMeasurementName(measurementName);
			}
		} else {
			if (((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_DIMENSION_NAME).getLength() != 0) {
				dimensionName = ((Element) modelNodeList.item(0)).getElementsByTagName(ELEM_DIMENSION_NAME).item(0).getTextContent();
			}

			MetricKeyImpl key = new MetricKeyImpl(schemaName, cubeName, hierarchyName, dimensionName);
			NodeList keyNodeList = rootElement.getElementsByTagName(ELEM_KEY_NAME);
			if (((Element) keyNodeList.item(0)).getElementsByTagName(ELEM_DIMENSION_LEVEL).getLength() != 0) {
				NodeList dimNodeList = ((Element) keyNodeList.item(0)).getElementsByTagName(ELEM_DIMENSION_LEVEL);
				Map<String, Object> sortedMap = new LinkedHashMap<String, Object>();
				Map<String, Object> dimensionValueMap = new LinkedHashMap<String, Object>();

				for (int i = 0; i < dimNodeList.getLength(); i++) {
					Element dimElement = (Element) dimNodeList.item(i);
					String dimName = dimElement.getAttribute(ATTR_NAME_NAME);

					String strValue = dimElement.getTextContent();
					Object value = null;
					if (strValue != null && !strValue.equals("")) {
						String dataType = dimElement.getAttribute(ATTR_DATATYPE_NAME);
						value = FilterFactory.INSTANCE.getDataTypeValue(dataType, strValue);
					}
					dimensionValueMap.put(dimName, value);
				}

				DimensionHierarchy dh = schema.getCube(cubeName).getDimensionHierarchy(hierarchyName);
				for (int i = 0; i < dh.getDepth(); i++) {
					sortedMap.put(dh.getDimension(i).getName(), dimensionValueMap.get(dh.getDimension(i).getName()));
				}

				dimensionValueMap.clear();
				key.setDimensionValuesMap(sortedMap);
			}
			((QueryByKeyDef) queryDef).setQueryKey(key);
		}
	}

	private void deserializeMetaDataElement(QueryDef queryDef, NodeList metaDataNodeList) {
		if (((Element) metaDataNodeList.item(0)).getElementsByTagName(ELEM_METADATA_BATCH).getLength() != 0) {
			int value = Integer.parseInt(((Element) metaDataNodeList.item(0)).getElementsByTagName(ELEM_METADATA_BATCH).item(0).getTextContent());
			queryDef.setBatchSize(value);
		}
	}

	private void NullPointerValidation(Object object, String message) throws Exception {
		if (object == null) {
			throw new Exception(message);
		}
	}

}
