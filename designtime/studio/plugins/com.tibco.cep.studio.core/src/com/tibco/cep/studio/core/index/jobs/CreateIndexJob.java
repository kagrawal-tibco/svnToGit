package com.tibco.cep.studio.core.index.jobs;

import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.IndexModificationAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexFactory;

public class CreateIndexJob extends IndexJob<DesignerProject> {

	private IProject fProject;
	private boolean fPersistResult = true;

	public CreateIndexJob(IProject project, boolean persistResult) {
		super("Creating index for "+project.getName());
		this.fProject = project;
		setUser(false);
		this.fPersistResult  = persistResult;
	}
	
	public CreateIndexJob(IProject project) {
		this(project, true);
	}

	@Override
	public boolean belongsTo(Object family) {
		return fProject.equals(family);
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		if (fProject == null) {
			System.err.println("Could not obtain project, unable to index project "+fProject);
			return new Status(Status.ERROR, StudioCorePlugin.PLUGIN_ID, 
					"Could not obtain project, unable to index project "+fProject);
		}
		printDebug("running create job for "+fProject.getName());
		DesignerProject index = IndexFactory.eINSTANCE.createDesignerProject();
		setProperty(getKey(), index);
		index.setCreationDate(new Date());
		index.eAdapters().add(new IndexModificationAdapter(index));
		index.setName(fProject.getName());
		index.setRootPath(fProject.getLocation().toOSString());
		IndexCreationResourceVisitor visitor = new IndexCreationResourceVisitor(index);
		if (!fProject.isOpen()) {
			return Status.OK_STATUS;
		}
		try {
			fProject.accept(visitor);
			// hack for project library error, in which indexing the project libs first
			// causes index issues if there are overlapping folders
			
			// this is done via the FileStore now
//			visitor.indexProjectLibraries(fProject);
		} catch (CoreException e) {
			System.err.println("Got error while visiting project");
			e.printStackTrace();
		}
		
		if (fPersistResult) {
			IStatus status = StudioCorePlugin.getDesignerModelManager().saveIndex(index, true);
//			PersistIndexJob persistJob = new PersistIndexJob(index);
//			IStatus status = persistJob.run(monitor);
			if (!status.isOK()) {
				return status;
			}
		}
		return new IndexJobResult(Status.OK, StudioCorePlugin.PLUGIN_ID, 
				"successful", index);
	}

	public IProject getProject() {
		return fProject;
	}

	private void printDebug(String message) {
		if (StudioCorePlugin.fDebug ) {
			System.out.println(message);
		}
	}
	
}
