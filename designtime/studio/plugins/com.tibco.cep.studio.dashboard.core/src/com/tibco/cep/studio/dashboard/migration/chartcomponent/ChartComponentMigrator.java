package com.tibco.cep.studio.dashboard.migration.chartcomponent;

import com.tibco.cep.studio.dashboard.migration.AbstractDashboardMigrator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartComponentMigrator extends AbstractDashboardMigrator {

	@Override
	protected void loadMigrators() {
		migrators.put(BEViewsElementNames.EXT_CHART_COMPONENT, new ChartComponentMigrator_5_1_1());
	}

}