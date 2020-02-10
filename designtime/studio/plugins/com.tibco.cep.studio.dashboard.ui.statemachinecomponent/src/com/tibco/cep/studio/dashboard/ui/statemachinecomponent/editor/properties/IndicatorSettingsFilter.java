package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;

public class IndicatorSettingsFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof LocalStateVisualization){
			try {
				if (StateMachineComponentHelper.getIndicatorSeriesConfig((LocalStateVisualization) toTest) != null){
					return true;
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not find indicator series config in "+toTest,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				return false;
			}
		}
		return false;
	}

}
