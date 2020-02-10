package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class IndicatorSettingsDeleteFromSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 8331323946182263349L;

	public IndicatorSettingsDeleteFromSelected() {
		super("Indicator Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Indicator Settings From Selected State Visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			return StateMachineComponentHelper.getIndicatorSeriesConfig((LocalStateVisualization) targetElement) != null;
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete indicator settings from the selected state?");
		if (userClicked == MessageDialog.OK) {
			// do something
			if (targetElement instanceof LocalStateVisualization) {
				try {
					LocalElement targetSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig((LocalStateVisualization) targetElement);
					if (targetSeriesConfig != null) {
						targetElement.removeElement(targetSeriesConfig);
						refresh(targetElement);
					}
				} catch (Exception ex) {
					String message = "could not delete indicator settings in " + targetElement.getName();
					logWarningAndAlert(message, ex);
				}
			}
		}

	}

}