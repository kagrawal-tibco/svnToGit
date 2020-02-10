package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryInterpreter;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalDataSource extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.DATA_SOURCE;

	public static final String PROP_KEY_QUERY = "Query";

	public static final String ELEMENT_KEY_SRC_ELEMENT = "SrcElement";

	public static final String ELEMENT_KEY_QUERY_PARAM = "QueryParam";

	public static final String ENUM_AGGR_FIELD = "AggrField";

	public static final String ENUM_GROUP_BY_FIELD = "GroupByField";

	public static final String ENUM_FIELD = "Field";

	public static final String ENUM_NUMERIC_FIELD = "NumericField";

	public static final String ENUM_NON_NUMERIC_FIELD = "NonNumericField";

	public LocalDataSource() {
		super(THIS_TYPE);
		setDefaultChildType(ELEMENT_KEY_QUERY_PARAM);
	}

	public LocalDataSource(LocalElement parentElement, String insightType, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
		setDefaultChildType(ELEMENT_KEY_QUERY_PARAM);
	}

	public LocalDataSource(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
		setDefaultChildType(ELEMENT_KEY_QUERY_PARAM);
	}

	public LocalDataSource(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
		setDefaultChildType(ELEMENT_KEY_QUERY_PARAM);
	}

	public List<LocalElement> getFields(String type) {
		List<Object> enumerations = getEnumerations(type);
		List<LocalElement> fields = new LinkedList<LocalElement>(getSourceElementFields());
		ListIterator<LocalElement> fieldsListIterator = fields.listIterator();
		while (fieldsListIterator.hasNext()) {
			LocalElement localElement = (LocalElement) fieldsListIterator.next();
			if (enumerations.contains(localElement.getName()) == false) {
				fieldsListIterator.remove();
			}
		}
		return fields;
	}

	public LocalElement getFieldByName(String type, String name) {
		if (hasFieldByName(type, name) == true) {
			LocalMetric metric = getMetric();
			if (metric != null) {
				return metric.getFieldByName(name);
			}
		}
		return null;
	}

	public boolean hasFieldByName(String type, String name) {
		return getEnumerations(type).contains(name);
	}

	@Override
	public List<Object> getEnumerations(String propName) {
		if (propName.equals(ELEMENT_KEY_SRC_ELEMENT) == true) {
			List<LocalElement> metrics = getRoot().getChildren(BEViewsElementNames.METRIC);
			List<Object> returnList = new ArrayList<Object>(metrics.size());
			for (LocalElement localElement : metrics) {
				returnList.add(localElement);
			}
			return returnList;
		}
		if (propName.equals(ENUM_AGGR_FIELD) == true) {
			List<Object> valueFields = new ArrayList<Object>();
			List<LocalElement> fields = getSourceElementFields();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				if (metricField.isGroupBy() == false && metricField.isTimeWindowField() == false) {
					valueFields.add(metricField.getName());
				}
			}
			return valueFields;
		}
		if (propName.equals(ENUM_GROUP_BY_FIELD) == true) {
			List<Object> groupByFields = new ArrayList<Object>();
			List<LocalElement> fields = getSourceElementFields();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				if (metricField.isGroupBy() == true) {
					groupByFields.add(metricField.getName());
				}
			}
			return groupByFields;
		}
		if (propName.equals(ENUM_FIELD)) {
			List<Object> allFields = new ArrayList<Object>();
			List<LocalElement> fields = getSourceElementFields();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				allFields.add(metricField.getName());
			}
			return allFields;
		}
		if (propName.equals(ENUM_NUMERIC_FIELD) == true) {
			List<Object> numericFields = new ArrayList<Object>();
			List<LocalElement> fields = getSourceElementFields();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				if (metricField.isNumeric(metricField.getDataType()) == true) {
					numericFields.add(metricField.getName());
				}
			}
			return numericFields;
		}
		if (propName.equals(ENUM_NON_NUMERIC_FIELD) == true) {
			List<Object> numericFields = new ArrayList<Object>();
			List<LocalElement> fields = getSourceElementFields();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				if (metricField.isNumeric(metricField.getDataType()) == false) {
					numericFields.add(metricField.getName());
				}
			}
			return numericFields;
		}
		return super.getEnumerations(propName);
	}

	private List<LocalElement> getSourceElementFields() {
		LocalMetric metric = getMetric();
		if (metric == null) {
			return Collections.emptyList();
		} else {
			return metric.getFields();
		}
	}

	private LocalMetric getMetric() {
		return (LocalMetric) getElement(ELEMENT_KEY_SRC_ELEMENT);
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		if (prop.getName().equals(PROP_KEY_QUERY) == true) {
			List<Metric> allMetrics = new LinkedList<Metric>();
			List<Object> allLocalMetrics = getEnumerations(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
			for (Object localMetric : allLocalMetrics) {
				allMetrics.add((Metric) ((LocalMetric) localMetric).getEObject());
			}
			BEViewsQueryInterpreter queryValidator = new BEViewsQueryInterpreter(allMetrics);
			queryValidator.interpret(prop.getValue());
			if (queryValidator.hasError() == true) {
				addValidationErrorMessage("Invalid or unsupported query - " + queryValidator.getErrorMessage());
			}

			Collection<String> variableNames = queryValidator.getVariableNames();
			for (String variableName : variableNames) {
				PROPERTY_TYPES dataType = queryValidator.getDataType(variableName);
				LocalQueryParam queryParam = (LocalQueryParam) getElement(ELEMENT_KEY_QUERY_PARAM, variableName, FOLDER_NOT_APPLICABLE);
				if (queryParam.getDataType().equals(dataType.toString()) == false) {
					addValidationErrorMessage("Data type of variable [" + variableName + "/" + queryParam.getDataType() + "] does not match with field [" + queryValidator.getFieldName(variableName) + "/" + dataType + "]");
				}
			}

		}
	}

}