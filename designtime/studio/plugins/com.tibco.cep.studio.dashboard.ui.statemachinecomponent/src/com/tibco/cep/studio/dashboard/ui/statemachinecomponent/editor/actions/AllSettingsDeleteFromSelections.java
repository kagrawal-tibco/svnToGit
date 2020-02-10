package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;

import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.dialogs.StatesSelectionDialog;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AllSettingsDeleteFromSelections extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 6299127506364618565L;

	public AllSettingsDeleteFromSelections() {
		super("All Settings...", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Content And Indicator Settings From Selected State Visualizations");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			List<LocalElement> stateVisualizations = targetElement.getChildren("StateVisualization");
			for (LocalElement stateVisualization : stateVisualizations) {
				LocalStateVisualization localStateVisualization = (LocalStateVisualization) stateVisualization;
				boolean hasContentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(localStateVisualization) != null;
				boolean hasIndicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) != null;
				boolean hasRelatedCharts = !localStateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT).isEmpty();
				boolean hasDisplayName = !localStateVisualization.isPropertyValueSameAsDefault(LocalStateVisualization.PROP_KEY_DISPLAY_NAME);
				boolean hasSettings = hasContentSeriesConfig || hasIndicatorSeriesConfig || hasRelatedCharts || hasDisplayName;
				if (hasSettings == true) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		LocalStateMachineComponent component = (LocalStateMachineComponent) targetElement;
		try {
			LocalExternalReference stateMachineReference = (LocalExternalReference) component.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE);
			StateMachine stateMachine = (StateMachine) stateMachineReference.getEObject();
			StatesSelectionDialog statesSelectionDialog = new StatesSelectionDialog(Display.getCurrent().getActiveShell(), stateMachine);
			int whichBtnClicked = statesSelectionDialog.open();
			if (whichBtnClicked == StatesSelectionDialog.OK) {
				Object[] states = statesSelectionDialog.getResult();
				for (Object stateObj : states) {
					LocalStateVisualization stateVisualization = StateMachineComponentHelper.getStateVisualization(component, new LocalExternalReference((State) stateObj));
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
				}
			}
		} catch (Exception ex) {
			String message = "could not delete all settings in selections of " + targetElement.getName();
			logWarningAndAlert(message, ex);
		}
	}

}