package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class IndicatorSettingsCopyFromSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -4685107916788431310L;

	public IndicatorSettingsCopyFromSelected() {
		super("Indicator Settings", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Copies indicator settings of the currently selected state visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateVisualization localStateVisualization = (LocalStateVisualization) targetElement;
			return StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) != null;
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		if (targetElement instanceof LocalStateVisualization) {
			try {
				LocalStateVisualization localStateVisualization = (LocalStateVisualization) targetElement;
				LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) localStateVisualization.getParent();
				localStateMachineComponent.setTransientElement(StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization));
			} catch (Exception ex) {
				String message = "could not copy indicator settings of " + targetElement.toShortString();
				logWarningAndAlert(message, ex);
			}
		}
	}

}
