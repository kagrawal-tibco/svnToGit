package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class ContentSettingsDeleteFromSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -366341228298666141L;

	public ContentSettingsDeleteFromSelected() {
		super("Content Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Content Settings From Selected State Visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			return StateMachineComponentHelper.getContentSeriesConfig((LocalStateVisualization) targetElement) != null;
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete the content settings from the selected state?");
		if (userClicked == MessageDialog.OK) {
			// do something
			if (targetElement instanceof LocalStateVisualization) {
				try {
					LocalElement targetSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig((LocalStateVisualization) targetElement);
					if (targetSeriesConfig != null) {
						targetElement.removeElement(targetSeriesConfig);
						refresh(targetElement);
					}
				} catch (Exception ex) {
					String message = "could not delete content settings from " + targetElement.getName();
					logWarningAndAlert(message, ex);
				}
			}
		}
	}

}