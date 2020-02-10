package com.tibco.cep.studio.dashboard.migration.statemachinecomponent;

import com.tibco.cep.studio.dashboard.migration.AbstractDashboardMigrator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StateMachineComponentMigrator extends AbstractDashboardMigrator {

	@Override
	protected void loadMigrators() {
		migrators.put(BEViewsElementNames.EXT_STATE_MACHINE_COMPONENT, new StateMachineComponentMigrator_5_1_0());
	}

}