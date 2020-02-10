package com.tibco.cep.studio.dashboard.migration;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrator;

public abstract class AbstractDashboardMigrator implements IStudioProjectMigrator {

	protected Map<String, IDashboardResourceMigrator> migrators;

	public AbstractDashboardMigrator() {
		migrators = new HashMap<String, IDashboardResourceMigrator>();
		loadMigrators();
	}

	protected abstract void loadMigrators();

	@Override
	public void migrateProject(IStudioProjectMigrationContext context, IProgressMonitor monitor) {
		try {
			File projectLocation = context.getProjectLocation();
			monitor.subTask("Scanning for dashboard resources to migrate...");
			// find resources to migrate
			List<File> resources = new LinkedList<File>();
			search(projectLocation, resources);
			for (File resource : resources) {
				monitor.subTask(resource.getAbsolutePath().substring(context.getProjectLocation().getAbsolutePath().length())+"...");
				String extension = resource.getName().substring(resource.getName().lastIndexOf(".") + 1);
				IDashboardResourceMigrator migrator = migrators.get(extension);
				migrator.migrate(context, resource, monitor);
			}
		} catch (CoreException e) {
			//Todo add logging
			//DashboardUIPlugin.getInstance().getLog().log(e.getStatus());
			throw new RuntimeException(e);
		}
	}

	protected void search(File location, List<File> hits) throws CoreException {
		File[] files = location.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory() == true) {
					return true;
				}
				if (pathname.isFile() == true) {
					String extension = pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1);
					if (migrators.containsKey(extension) == true) {
						return true;
					}
				}
				return false;
			}

		});
		for (File file : files) {
			if (file.isDirectory() == true) {
				search(file, hits);
			}
			else if (file.isFile() == true){
				hits.add(file);
			}
		}
	}

	@Override
	public int getPriority() {
		return 10;
	}

}
