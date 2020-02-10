package com.tibco.cep.studio.dashboard.migration.chartcomponent;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class ChartComponentMigrator_5_1_1 extends ChartComponentMigrator_5_1_0 {

	protected boolean doMigrate(Component chartOrTextComponent) {
		boolean migrated = super.doMigrate(chartOrTextComponent);
		boolean migratedDrillableFields = migrateDrillableFields(chartOrTextComponent);
		return migrated || migratedDrillableFields;
	}

	private boolean migrateDrillableFields(Component component) {
		boolean migrated = false;
		List<Visualization> visualizations = component.getVisualization();
		for (Visualization visualization : visualizations) {
			List<SeriesConfig> seriesConfigs = visualization.getSeriesConfig();
			for (SeriesConfig seriesConfig : seriesConfigs) {
				ActionRule actionRule = seriesConfig.getActionRule();
				if (actionRule.getDrillableFields().isEmpty() == true) {
					Metric srcMetric = (Metric) actionRule.getDataSource().getSrcElement();
					EcoreUtil.resolveAll(srcMetric);
					List<PropertyDefinition> properties = srcMetric.getProperties();
					for (PropertyDefinition propertyDefinition : properties) {
						if (propertyDefinition.isGroupByField() == true) {
							actionRule.getDrillableFields().add(propertyDefinition);
							migrated = true;
						}
					}
				}
			}
		}
		return migrated;
	}

}
