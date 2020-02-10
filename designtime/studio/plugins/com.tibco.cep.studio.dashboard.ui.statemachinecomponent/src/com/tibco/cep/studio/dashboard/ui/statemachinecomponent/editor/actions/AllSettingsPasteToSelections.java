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

public class AllSettingsPasteToSelections extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -8907366848063956377L;

	public AllSettingsPasteToSelections() {
		super("All Settings...", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Paste All Settings To Selected State Visualization(s)");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement;
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			return transientElement instanceof LocalStateVisualization;
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
				// do something
				Object[] states = statesSelectionDialog.getResult();
				for (Object stateObj : states) {
					LocalStateVisualization stateVisualization = StateMachineComponentHelper.getStateVisualization(component, new LocalExternalReference((State) stateObj));
					LocalElement transientElement = component.getTransientElement();
					if (stateVisualization != transientElement) {
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
						// clone all series configs from transient element to selected element
						List<LocalElement> transientElementChildren = transientElement.getChildren("StateSeriesConfig");
						for (LocalElement transientElementChild : transientElementChildren) {
							stateVisualization.addElement("StateSeriesConfig", (LocalElement) transientElementChild.clone());
						}
						// transfer all related charts from transient element to selected element
						transientElementChildren = transientElement.getChildren(BEViewsElementNames.RELATED_COMPONENT);
						for (LocalElement transientElementChild : transientElementChildren) {
							stateVisualization.addElement(BEViewsElementNames.RELATED_COMPONENT, (LocalElement) transientElementChild);
						}
						StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
					}
				}
			}
		} catch (Exception ex) {
			String message = "could not replace all settings in selections of " + targetElement.getName();
			logWarningAndAlert(message, ex);
		}
	}

}