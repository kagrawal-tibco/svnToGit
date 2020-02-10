package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AllSettingsPasteToSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 1521373965319083078L;

	public AllSettingsPasteToSelected() {
		super("All Settings", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Paste all copied settings to the selected state visualization replacing existing settings, if any");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement.getParent();
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			return transientElement != targetElement && transientElement instanceof LocalStateVisualization;
		}
		return false;
	}

	@Override
	protected void run(LocalElement targetElement, ActionEvent e) {
		if (targetElement instanceof LocalStateVisualization) {
			try {
				LocalStateVisualization stateVisualization = (LocalStateVisualization) targetElement;
				boolean hasContentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(stateVisualization) != null;
				boolean hasIndicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization) != null;
				boolean hasRelatedCharts = !stateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT).isEmpty();
				boolean hasSettings = hasContentSeriesConfig || hasIndicatorSeriesConfig || hasRelatedCharts;
				if (hasSettings == true) {
					int userClicked = openWarningDialog("Are you sure you want to replace all existing settings in the selected state?");
					if (userClicked == MessageDialog.CANCEL) {
						return;
					}
				}
				// remove all target element settings
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
				LocalElement transientElement = ((LocalStateMachineComponent) targetElement.getParent()).getTransientElement();
				// clone all series configs from transient element to target element
				List<LocalElement> transientElementChildren = transientElement.getChildren("StateSeriesConfig");
				for (LocalElement transientElementChild : transientElementChildren) {
					stateVisualization.addElement("StateSeriesConfig", (LocalElement) transientElementChild.clone());
				}
				// transfer all related charts from transient element to target element
				transientElementChildren = transientElement.getChildren(BEViewsElementNames.RELATED_COMPONENT);
				for (LocalElement transientElementChild : transientElementChildren) {
					stateVisualization.addElement(BEViewsElementNames.RELATED_COMPONENT, (LocalElement) transientElementChild);
				}
				StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
				refresh(targetElement);
			} catch (Exception ex) {
				String message = "could not replace all settings in " + targetElement.toShortString();
				logWarningAndAlert(message, ex);
			}
		}
	}

}
