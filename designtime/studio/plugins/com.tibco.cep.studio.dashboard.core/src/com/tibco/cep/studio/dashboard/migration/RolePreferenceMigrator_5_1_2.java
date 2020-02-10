package com.tibco.cep.studio.dashboard.migration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;

public class RolePreferenceMigrator_5_1_2 implements IDashboardResourceMigrator {

	@Override
	public void migrate(IStudioProjectMigrationContext context, File resource, IProgressMonitor monitor) throws CoreException {
		try {
			RolePreference rolePreference = (RolePreference) DashboardCoreResourceUtils.loadMultipleElements(resource.getAbsolutePath()).get(0);
			boolean migrated = doMigrate(rolePreference);
			if (migrated == true) {
				List<Entity> entities = new ArrayList<Entity>();
				entities.add(rolePreference);
				DashboardCoreResourceUtils.persistEntities(entities, resource.getAbsolutePath(), null);
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not migrate dashboard system skin", e));
		}
	}

	private boolean doMigrate(RolePreference rolePreference) {
		if (rolePreference.eIsSet(rolePreference.eClass().getEStructuralFeature(BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE)) == false) {
			rolePreference.setRole(rolePreference.getName());
			return true;
		}
		return false;
	}

}
