package com.tibco.cep.studio.core.migration;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IStudioProjectMigrator {

	/**
	 * Implementors of the com.tibco.cep.studio.core.projectMigrator extension point
	 * must implement this interface.  Implementors are responsible for reading the
	 * java.io.Files in the relevant <code>projectLocation</code> and re-saving any
	 * resources as necessary.<br>
	 * <b>NOTE</b>: The <code>context's IProject project</code> has not yet been created when this
	 * method is called, so clients cannot access the IFiles from this project 
	 * via the org.eclipse.core.resources APIs.
	 *
	 * @param context
	 * @param monitor
	 */
	public void migrateProject(IStudioProjectMigrationContext context, IProgressMonitor monitor);
	
	/**
	 * Returns the priority for this migrator.  Higher priority
	 * migrators will run before lower priority ones.  This is important
	 * in cases where syntax/format/schemas have changed.  In that case,
	 * migrators running against those resources need to know whether the
	 * format of the resource has been updated, or whether it is still in
	 * the old format.  
	 * <br><br>
	 * For instance, if an EMF model has changed, the
	 * usual EMF APIs can not be used to load the old persisted model into the 
	 * new format, as FeatureNotFoundExceptions will occur.  Instead, a high
	 * priority migrator should first update the format of the file, and then
	 * a lower priority one will be free to load the object using EMF APIs.
	 * <br><br>
	 * <b>NOTE</b>: A lower number mean a higher priority.  For instance, 
	 * a priority of 2 will run before a priority of 5.
	 * @return the priority for this migrator
	 */
	public int getPriority();
}
