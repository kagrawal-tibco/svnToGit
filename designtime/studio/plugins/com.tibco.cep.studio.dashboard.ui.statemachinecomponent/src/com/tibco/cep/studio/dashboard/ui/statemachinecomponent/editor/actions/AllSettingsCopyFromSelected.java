package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AllSettingsCopyFromSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -7639776224133647291L;

	public AllSettingsCopyFromSelected() {
		super("All Settings", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Copies all settings of the currently selected state visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization){
			LocalStateVisualization localStateVisualization = (LocalStateVisualization)targetElement;
			boolean hasContentSeriesConfig = StateMachineComponentHelper.getContentSeriesConfig(localStateVisualization) != null;
			boolean hasIndicatorSeriesConfig = StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) != null;
			boolean hasRelatedCharts = !localStateVisualization.getChildren(BEViewsElementNames.RELATED_COMPONENT).isEmpty();
			boolean hasDisplayName = !localStateVisualization.isPropertyValueSameAsDefault(LocalStateVisualization.PROP_KEY_DISPLAY_NAME);
			return hasContentSeriesConfig || hasIndicatorSeriesConfig || hasRelatedCharts || hasDisplayName;
		}
		return false;
	}

	@Override
	public void run(LocalElement targetElement, ActionEvent e) {
		if (targetElement instanceof LocalStateVisualization){
			try {
				LocalStateVisualization localStateVisualization = (LocalStateVisualization)targetElement;
				LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) localStateVisualization.getParent();
				localStateMachineComponent.setTransientElement(localStateVisualization);
			} catch (Exception ex) {
				String message = "could not copy all settings of "+targetElement.toShortString();
				logWarning(message, ex);
			}
		}
	}

}
