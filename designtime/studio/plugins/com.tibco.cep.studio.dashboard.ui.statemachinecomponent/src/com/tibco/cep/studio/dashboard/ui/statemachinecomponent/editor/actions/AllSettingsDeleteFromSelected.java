package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AllSettingsDeleteFromSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 8386160911565754898L;

	public AllSettingsDeleteFromSelected() {
		super("All Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete All Settings From Selected State Visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateVisualization localStateVisualization = (LocalStateVisualization) targetElement;
			boolean hasContentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(localStateVisualization) != null;
			boolean hasIndicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) != null;
			boolean hasRelatedCharts = !localStateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT).isEmpty();
			boolean hasDisplayName = !localStateVisualization.isPropertyValueSameAsDefault(LocalStateVisualization.PROP_KEY_DISPLAY_NAME);
			return hasContentSeriesConfig || hasIndicatorSeriesConfig || hasRelatedCharts || hasDisplayName;
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete all the settings for the selected state?");
		if (userClicked == MessageDialog.OK) {
			if (targetElement instanceof LocalStateVisualization) {
				try {
					LocalStateVisualization stateVisualization = (LocalStateVisualization) targetElement;
					LocalStateSeriesConfig contentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(stateVisualization);
					if (contentSeriesConfig != null) {
						stateVisualization.removeElement(contentSeriesConfig);
					}
					LocalStateSeriesConfig indicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization);
					if (indicatorSeriesConfig != null) {
						stateVisualization.removeElement(indicatorSeriesConfig);
					}
					List<LocalElement> relatedCharts = stateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT);
					for (LocalElement relatedChart : relatedCharts) {
						//Modified by Anand on 03/03/2011 to fix BE-11493, we cannot use the element directly since the framework will look for particle by the chart type whereas we want to use BEViewsElementNames.RELATED_COMPONENT
						stateVisualization.removeElement(BEViewsElementNames.RELATED_COMPONENT, relatedChart.getName(), relatedChart.getFolder());
					}
					stateVisualization.setPropertyValue(LocalStateVisualization.PROP_KEY_DISPLAY_NAME, null);
					refresh(targetElement);
				} catch (Exception ex) {
					String message = "could not delete all settings in " + targetElement.getName();
					logWarningAndAlert(message, ex);
				}
			}
		}
	}

}