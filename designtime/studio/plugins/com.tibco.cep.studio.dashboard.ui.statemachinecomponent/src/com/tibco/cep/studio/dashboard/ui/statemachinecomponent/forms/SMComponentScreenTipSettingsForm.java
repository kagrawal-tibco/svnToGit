package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.forms.ScreenTipSettingsForm;

public class SMComponentScreenTipSettingsForm extends ScreenTipSettingsForm {

	public SMComponentScreenTipSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super(formToolKit, parent, showGroup);
	}

	protected String getValue() throws Exception {
		if (StateMachineComponentHelper.isProgressBarContentSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			return localElement.getPropertyValue("ProgressTooltipFormat");
		} else if (StateMachineComponentHelper.isTextContentSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			return localElement.getPropertyValue("TextTooltipFormat");
		} else if (StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			return localElement.getPropertyValue("IndicatorTooltipFormat");
		}
		throw new Exception(localElement+" is not acceptable");
	}
	
	protected void setValue(String value) throws Exception {
		if (StateMachineComponentHelper.isProgressBarContentSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			localElement.setPropertyValue("ProgressTooltipFormat",value);
		} else if (StateMachineComponentHelper.isTextContentSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			localElement.setPropertyValue("TextTooltipFormat",value);
		} else if (StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) localElement) == true) {
			localElement.setPropertyValue("IndicatorTooltipFormat",value);
		}
	}
}
