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

public class ContentSettingsDeleteFromAll extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -6510208328002863781L;

	public ContentSettingsDeleteFromAll() {
		super("Content Settings", TRANSPARENT_ICON);
		putValue(Action.LONG_DESCRIPTION, "Delete Content Settings From All State Visualizations");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateMachineComponent) {
			List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
			for (LocalElement stateVisualization : stateVisualizations) {
				if (StateMachineComponentHelper.getContentSeriesConfig((LocalStateVisualization) stateVisualization) != null) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		int userClicked = openWarningDialog("Are you sure you want to delete content settings from all states?");
		if (userClicked == MessageDialog.OK) {
			int cnt = 0;
			try {
				List<LocalElement> stateVisualizations = targetElement.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
				for (LocalElement stateVisualization : stateVisualizations) {
					LocalElement contentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig((LocalStateVisualization) stateVisualization);
					if (contentSeriesConfig != null) {
						stateVisualization.removeElement(contentSeriesConfig);
						cnt++;
					}
				}
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), (String)getValue(NAME), "Deleted " + cnt + " content settings");
			} catch (Exception ex) {
				String message = "could not delete content settings from all states in " + targetElement.getName();
				logWarningAndAlert(message, ex);
			}
		}
	}

}