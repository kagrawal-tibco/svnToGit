package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class IndicatorSettingsDeleteFromAll extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -3308470780551384512L;

	public IndicatorSettingsDeleteFromAll() {
		super("Indicator Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Indicator Settings From All State Visualizations");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
			for (LocalElement stateVisualization : stateVisualizations) {
				if (StateMachineComponentHelper.getIndicatorSeriesConfig((LocalStateVisualization) stateVisualization) != null) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete indicator settings from all states?");
		if (userClicked == MessageDialog.OK) {
			int cnt = 0;
			try {
				List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
				for (LocalElement stateVisualization : stateVisualizations) {
					LocalElement indicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig((LocalStateVisualization) stateVisualization);
					if (indicatorSeriesConfig != null) {
						stateVisualization.removeElement(indicatorSeriesConfig);
						cnt++;
					}
				}
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), String.valueOf(getValue(NAME)), "Deleted " + cnt + " indicator settings");
			} catch (Exception ex) {
				String message = "could not delete indicator settings from all states in " + targetElement.getName();
				logWarningAndAlert(message, ex);
			}
		}
	}

}