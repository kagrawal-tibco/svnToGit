package com.tibco.cep.studio.dashboard.migration;

import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class CoreDashboardMigrator extends AbstractDashboardMigrator {

	@Override
	protected void loadMigrators() {
		migrators.put(BEViewsElementNames.EXT_SYSTEM_ELEMENTS, new SystemElementsMigrator_5_0_0());
		migrators.put(BEViewsElementNames.EXT_ROLE_PREFERENCE, new RolePreferenceMigrator_5_1_2());
	}

}