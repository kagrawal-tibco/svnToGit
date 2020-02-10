package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AllSettingsDeleteFromAll extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 8017743257876948385L;

	public AllSettingsDeleteFromAll() {
		super("Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete All Settings From All State Visualizations");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
			for (LocalElement stateVisualization : stateVisualizations) {
				LocalStateVisualization localStateVisualization = (LocalStateVisualization) stateVisualization;
				boolean hasContentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(localStateVisualization) != null;
				boolean hasIndicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) != null;
				boolean hasRelatedCharts = !localStateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT).isEmpty();
				SynProperty displayNameProp = (SynProperty) localStateVisualization.getProperty(LocalStateMachineComponent.PROP_KEY_DISPLAY_NAME);
				List<String> rawValues = displayNameProp.getValues();
				boolean hasDisplayName = rawValues.isEmpty() == false && rawValues.get(0) != null;
				boolean hasSettings = hasContentSeriesConfig || hasIndicatorSeriesConfig || hasRelatedCharts || hasDisplayName;
				if (hasSettings == true){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete all the settings for all the states?");
		if (userClicked == MessageDialog.OK) {
			int cnt = 0;
			try {
				List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
				for (LocalElement visualization : stateVisualizations) {
					LocalStateVisualization stateVisualization = (LocalStateVisualization) visualization;
					LocalStateSeriesConfig contentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(stateVisualization);
					if (contentSeriesConfig != null) {
						visualization.removeElement(contentSeriesConfig);
					}
					LocalStateSeriesConfig indicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization);
					if (indicatorSeriesConfig != null){
						visualization.removeElement(indicatorSeriesConfig);
					}
					List<LocalElement> relatedCharts = stateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT);
					for (LocalElement relatedChart : relatedCharts) {
						//Modified by Anand on 03/03/2011 to fix BE-11493, we cannot use the element directly since the framework will look for particle by the chart type whereas we want to use BEViewsElementNames.RELATED_COMPONENT
						stateVisualization.removeElement(BEViewsElementNames.RELATED_COMPONENT, relatedChart.getName(), relatedChart.getFolder());
					}
					stateVisualization.setPropertyValue(LocalStateVisualization.PROP_KEY_DISPLAY_NAME, null);
					cnt++;
				}
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Delete All Settings", "Deleted " + cnt + " settings");
			} catch (Exception ex) {
				String message = "could not delete all settings in " + targetElement.getName();
				logWarningAndAlert(message, ex);
			}
		}
	}

}