package com.tibco.cep.studio.dashboard.migration;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;

public interface IDashboardResourceMigrator {
	
	public void migrate(IStudioProjectMigrationContext context, File resource, IProgressMonitor monitor) throws CoreException;

}
