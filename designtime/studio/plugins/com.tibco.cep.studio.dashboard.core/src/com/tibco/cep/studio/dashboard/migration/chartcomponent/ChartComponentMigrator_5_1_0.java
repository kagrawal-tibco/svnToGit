package com.tibco.cep.studio.dashboard.migration.chartcomponent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.Alert;
import com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam;
import com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.migration.IDashboardResourceMigrator;

public class ChartComponentMigrator_5_1_0 implements IDashboardResourceMigrator {

	@Override
	public void migrate(IStudioProjectMigrationContext context, File resource, IProgressMonitor monitor) throws CoreException {
		try {
			Component chartOrTextComponent = (Component) DashboardCoreResourceUtils.loadMultipleElements(resource.getAbsolutePath()).get(0);
			boolean migrated = doMigrate(chartOrTextComponent);
			if (migrated == true) {
				List<Entity> entities = new ArrayList<Entity>();
				entities.add(chartOrTextComponent);
				DashboardCoreResourceUtils.persistEntities(entities, resource.getAbsolutePath(), null);
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not migrate chart component", e));
		}
	}

	protected boolean doMigrate(Component chartOrTextComponent) {
		boolean migratedHelpText = migrateHelpText(chartOrTextComponent);
		boolean migrateActionRuleBindings = migrateActionRuleBindings(chartOrTextComponent);
		boolean migrateRangeChartOrientation = migrateRangeChartOrientation(chartOrTextComponent);
		boolean migrateVisualAlerts = migrateVisualActions(chartOrTextComponent);
		return migratedHelpText == true || migrateActionRuleBindings == true || migrateRangeChartOrientation == true || migrateVisualAlerts == true;
	}

	private boolean migrateHelpText(Component component) {
		String helpText = component.getHelpText();
		if (StringUtil.isEmpty(helpText) == false) {
			StringBuilder description = new StringBuilder(component.getDescription());
			if (description.length() > 0) {
				description.append("\r\n\r\n");
			}
			description.append(helpText);
			component.setHelpText(null);
			component.setDescription(description.toString());
			return true;
		}
		return false;
	}

	private boolean migrateActionRuleBindings(Component component) {
		boolean modified = false;
		List<Visualization> visualizations = component.getVisualization();
		for (Visualization visualization : visualizations) {
			List<SeriesConfig> seriesConfigs = visualization.getSeriesConfig();
			for (SeriesConfig seriesConfig : seriesConfigs) {
				if (seriesConfig instanceof TwoDimSeriesConfig) {
					TwoDimSeriesConfig twoDimSeriesConfig = (TwoDimSeriesConfig) seriesConfig;
					List<QueryParam> params = twoDimSeriesConfig.getActionRule().getParams();
					for (QueryParam queryParam : params) {
						String value = queryParam.getValue();
						PRE_DEFINED_BINDS predefinedBind = BEViewsQueryDateTypeInterpreter.getPredefinedBind(value);
						if (predefinedBind != null) {
							queryParam.setValue("${SYS:"+predefinedBind+"}");
							modified = true;
						}
					}
				}
			}
		}
		return modified;
	}

	private boolean migrateRangeChartOrientation(Component component) {
		boolean modified = false;
		List<Visualization> visualizations = component.getVisualization();
		if (visualizations.size() == 1) {
			if (visualizations.get(0) instanceof RangePlotChartVisualization) {
				RangePlotChartVisualization rangePlotChartVisualization = (RangePlotChartVisualization) visualizations.get(0);
				if (rangePlotChartVisualization.getOrientation().equals(OrientationEnum.HORIZONTAL) == true) {
					rangePlotChartVisualization.setOrientation(OrientationEnum.VERTICAL);
					modified = true;
				}
			}
		}
		return modified;
	}

	private boolean migrateVisualActions(Component component) {
		boolean modified = false;
		List<Visualization> visualizations = component.getVisualization();
		for (Visualization visualization : visualizations) {
			List<SeriesConfig> seriesConfigs = visualization.getSeriesConfig();
			for (SeriesConfig seriesConfig : seriesConfigs) {
				ActionRule actionRule = seriesConfig.getActionRule();
				if (actionRule != null) {
					List<Alert> alerts = actionRule.getAlert();
					for (Alert alert : alerts) {
						for (AlertAction action : alert.getAction()) {
							if (action.isEnabled() == false) {
								action.setEnabled(true);
								modified = true;
							}
						}
					}
				}
			}
		}
		return modified;
	}

}
