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

public class ContentSettingsPasteToSelections extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 6985308142259625489L;

	public ContentSettingsPasteToSelections() {
		super("Content Settings...", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Content Settings From Selected State Visualizations");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement;
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			if (transientElement instanceof LocalStateSeriesConfig) {
				return StateMachineComponentHelper.isContentSeriesConfig((LocalStateSeriesConfig) transientElement);
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
					LocalStateSeriesConfig contentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(stateVisualization);
					if (contentSeriesConfig != null) {
						stateVisualization.removeElement(contentSeriesConfig);
					}
					stateVisualization.addElement("StateSeriesConfig", (LocalElement) transientElement.clone());
					StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
				}
			}
		} catch (Exception ex) {
			String message = "could not open state selection dialog for " + component.toShortString();
			logWarningAndAlert(message, ex);
		}
	}

}