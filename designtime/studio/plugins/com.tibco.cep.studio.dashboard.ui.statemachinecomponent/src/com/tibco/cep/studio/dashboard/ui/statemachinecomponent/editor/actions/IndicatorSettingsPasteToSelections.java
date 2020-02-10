package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

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

public class IndicatorSettingsPasteToSelections extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 2052079333034191226L;

	public IndicatorSettingsPasteToSelections() {
		super("Indicator Settings...", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Paste Copied Indicator Settings Copied To Selected State Visualization(s)");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement;
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			if (transientElement instanceof LocalStateSeriesConfig) {
				return StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) transientElement);
			}
			return false;
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
				LocalElement transientElement = component.getTransientElement();
				Object[] states = statesSelectionDialog.getResult();
				for (Object stateObj : states) {
					LocalStateVisualization stateVisualization = StateMachineComponentHelper.getStateVisualization(component, new LocalExternalReference((State) stateObj));
					LocalStateSeriesConfig indicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization);
					if (indicatorSeriesConfig != null) {
						stateVisualization.removeElement(indicatorSeriesConfig);
					}
					stateVisualization.addElement("StateSeriesConfig", (LocalElement) transientElement.clone());
					StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
				}
			}
		} catch (Exception ex) {
			String message = "could not paste indicator settings in selections in " + component.toShortString();
			logWarningAndAlert(message, ex);
		}
	}

}