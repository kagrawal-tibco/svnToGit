package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class MeasureChangeMediator {

	private LocalStateSeriesConfig seriesConfig;
	private List<BaseForm> dependentForms;

	public MeasureChangeMediator(LocalStateSeriesConfig seriesConfig, List<BaseForm> dependentForms) {
		this.seriesConfig = seriesConfig;
		this.dependentForms = dependentForms;
	}

	public void updateSeriesConfig() throws Exception {
		if (StateMachineComponentHelper.isContentSeriesConfig(seriesConfig) == true) {
			updateContentSeriesConfig();
		} else if (StateMachineComponentHelper.isIndicatorSeriesConfig(seriesConfig) == true) {
			updateIndicatorSeriesConfig();
		}
		for (BaseForm dependentForm : dependentForms) {
			dependentForm.setInput(seriesConfig);
		}

	}

	private void updateContentSeriesConfig() throws Exception {
		// the measure has changed, so we need to update the incoming series
		// config
		LocalDataSource dataSource = (LocalDataSource) seriesConfig.getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		List<Object> valueFields = dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
		String valueFieldName = (String) valueFields.get(0);
		// LocalAttribute valueField = (LocalAttribute)
		// dataSource.getFieldByName(valueFieldName);

		boolean isUsingProgressBar = StateMachineComponentHelper.isProgressBarContentSeriesConfig(seriesConfig);
		String valueFieldPropertyName = (isUsingProgressBar == true ? "ProgressValueField" : "TextValueField");
		String valueDisplayFormatPropertyName = (isUsingProgressBar == true ? "ProgressDisplayFormat" : "TextDisplayFormat");
		String valueScreenTipFormatPropertyName = (isUsingProgressBar == true ? "ProgressTooltipFormat" : "TextTooltipFormat");

		// LocalAttribute oldValueField = (LocalAttribute)
		// dataSource.getFieldByName(localContentSeriesConfig.getPropertyValue(valueFieldPropertyName));
		seriesConfig.setPropertyValue(valueFieldPropertyName, valueFieldName);

		// String displayFormat =
		// localContentSeriesConfig.getPropertyValue(valueDisplayFormatPropertyName);
		// String screenTipFormat =
		// localContentSeriesConfig.getPropertyValue(valueScreenTipFormatPropertyName);
		// String regex = "\\{" + oldValueField.getName() + "[,\\S]*\\}";
		//
		// if (valueField.getDataType().equals(oldValueField.getDataType()) ==
		// true){
		// regex = "{"+oldValueField.getName();
		// }
		// displayFormat = displayFormat.replaceAll(regex, "{"+valueFieldName);
		// screenTipFormat = screenTipFormat.replaceAll(regex,
		// "{"+valueFieldName);
		// localContentSeriesConfig.setPropertyValue(valueDisplayFormatPropertyName,
		// displayFormat);
		// localContentSeriesConfig.setPropertyValue(valueScreenTipFormatPropertyName,
		// screenTipFormat);

		seriesConfig.setPropertyValue(valueDisplayFormatPropertyName, "{" + valueFieldName + "}");
		seriesConfig.setPropertyValue(valueScreenTipFormatPropertyName, "{" + valueFieldName + "}");

		if (isUsingProgressBar == true) {
			seriesConfig.setPropertyValue("ProgressMinField", null);
			seriesConfig.setPropertyValue("ProgressMaxField", null);
		}
	}

	private void updateIndicatorSeriesConfig() throws Exception {
		// the measure has changed, so we need to update the incoming series
		// config
		LocalDataSource dataSource = (LocalDataSource) seriesConfig.getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		List<Object> valueFields = dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
		String valueFieldName = (String) valueFields.get(0);
//		LocalAttribute valueField = (LocalAttribute) dataSource.getFieldByName(valueFieldName);

		// LocalAttribute oldValueField = (LocalAttribute)
		// dataSource.getFieldByName(localStateSeriesConfig.getPropertyValue("IndicatorValueField"));
		seriesConfig.setPropertyValue("IndicatorValueField", valueFieldName);

		// String screenTipFormat =
		// localStateSeriesConfig.getPropertyValue("IndicatorTooltipFormat");
		// String regex = "\\{" + oldValueField.getName() + "[,\\S]*\\}";
		// if (valueField.getDataType().equals(oldValueField.getDataType()) ==
		// true){
		// regex = "{"+oldValueField.getName();
		// }
		// screenTipFormat = screenTipFormat.replaceAll(regex,
		// "{"+valueField.getName());
		seriesConfig.setPropertyValue("IndicatorTooltipFormat", "{" + valueFieldName + "}");

	}

}
