package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;

public abstract class DefaultStudioProjectMigrator implements IStudioProjectMigrator {

	protected File projectLocation;
	protected Map<String, String> httpProperties;
	protected XPATH_VERSION xpathVersion;
	
	@Override
	public void migrateProject(IStudioProjectMigrationContext context,
			IProgressMonitor monitor) {
		projectLocation = context.getProjectLocation();
		httpProperties = context.getHttpPropertiesToMigrate();
		xpathVersion = context.getXpathVersion();
		processDirectory(projectLocation, monitor);
		monitor.done();
	}

	protected void processDirectory(File parentFile, IProgressMonitor monitor) {
		if (!parentFile.isDirectory()) {
			return;
		}
		File[] listFiles = parentFile.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				processDirectory(file, monitor);
			} else {
				migrateFile(parentFile, file, monitor);
			}
		}
	}

	protected abstract void migrateFile(File parentFile, File file, IProgressMonitor monitor);
	/*
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		// default implementation does nothing, clients should override
		monitor.setTaskName("Migrating "+file.getName()+" from parent file "+parentFile.getAbsolutePath());
	}
	*/
	
	protected String getFileExtension(File file) {
		int idx = file.getName().lastIndexOf('.');
		String ext = null;
		if (idx > -1) {
			ext = file.getName().substring(idx+1);
		}
		return ext;
	}
}
