package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class ContentSettingsPasteToSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = 2788598963075593261L;

	public ContentSettingsPasteToSelected() {
		super("Content Settings", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Paste all copied content settings to the selected state visualization replacing existing content settings, if any");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement.getParent();
			LocalElement transientElement = localStateMachineComponent.getTransientElement();
			if (transientElement instanceof LocalStateSeriesConfig) {
				if (transientElement.getParent() != targetElement) {
					return StateMachineComponentHelper.isContentSeriesConfig((LocalStateSeriesConfig) transientElement);
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
				LocalElement contentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(stateVisualization);
				if (contentSeriesConfig != null) {
					int userClicked = openWarningDialog("Are you sure you want to replace existing content settings for "+targetElement.getName()+"?");
					if (userClicked == MessageDialog.CANCEL) {
						return;
					}
					stateVisualization.removeElement(contentSeriesConfig);
				}
				LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) targetElement.getParent();
				LocalElement transientElement = localStateMachineComponent.getTransientElement();
				stateVisualization.addElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG, (LocalElement) transientElement.clone());
				StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
				refresh(targetElement);
			} catch (Exception ex) {
				String message = "could not paste content settings in " + targetElement.toShortString();
				logWarningAndAlert(message, ex);
			}
		}
	}
}
