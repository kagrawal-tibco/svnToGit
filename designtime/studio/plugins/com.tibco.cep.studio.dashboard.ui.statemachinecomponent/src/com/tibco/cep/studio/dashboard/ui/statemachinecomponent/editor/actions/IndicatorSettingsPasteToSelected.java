package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class IndicatorSettingsPasteToSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -8948080094011567775L;

	public IndicatorSettingsPasteToSelected() {
		super("Indicator Settings", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Paste all copied indicator settings to the selected state visualization replacing existing indicator settings, if any");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement.getParent();
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			if (transientElement instanceof LocalStateSeriesConfig) {
				if (transientElement.getParent() != targetElement) {
					return StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) transientElement);
				}
			}
			return false;
		}
		return false;
	}

	@Override
	protected void run(LocalElement targetElement, ActionEvent e) {
		if (targetElement instanceof LocalStateVisualization) {
			try {
				LocalStateVisualization stateVisualization = (LocalStateVisualization) targetElement;
				LocalElement indicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization);
				if (indicatorSeriesConfig != null) {
					int userClicked = openWarningDialog("Are you sure you want to replace existing indicator settings?");
					if (userClicked == MessageDialog.CANCEL) {
						return;
					}
					stateVisualization.removeElement(indicatorSeriesConfig);
				}
				LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement.getParent();
				LocalElement transientElement = localStateMachineComponent.getTransientElement();
				stateVisualization.addElement("StateSeriesConfig", (LocalElement) transientElement.clone());
				StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
				refresh(targetElement);
			} catch (Exception ex) {
				String message = "could not paste indicator settings in " + targetElement.toShortString();
				logWarningAndAlert(message, ex);
			}
		}
	}

}
