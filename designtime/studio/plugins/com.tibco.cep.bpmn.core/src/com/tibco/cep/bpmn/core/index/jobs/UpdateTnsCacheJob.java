package com.tibco.cep.bpmn.core.index.jobs;

import java.util.Queue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.update.BpmnTnsCacheUpdater;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.studio.core.index.jobs.IndexJob;

/**
 * 
 * @author majha
 *
 */
public class UpdateTnsCacheJob  extends IndexJob<EObject> {

	private IProject project;
	private IResourceDelta delta;
	private QualifiedName qn;
	private BpmnTnsCacheUpdater tnsCacheUpdater;

	public UpdateTnsCacheJob(IProject project, IResourceDelta delta, BpmnTnsCacheUpdater updator) {
		super("Updating BPMN TnsCache resources for "+project.getName());
		this.project = project;
		this.delta = delta;
		this.qn = new QualifiedName(project.getName(),GUIDGenerator.getGUID());
		this.tnsCacheUpdater = updator;
		EObject index = BpmnModelCache.getInstance().getIndex(project.getName());
		if(index == null)// getting null index some times , don't know why so trying again
			index = BpmnCorePlugin.getDefault().getBpmnModelManager().loadIndex(project);
		
		setProperty(qn, index);
	}
	
	public IProject getProject() {
		return project;
	}
	
	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		if(getProperty(qn) == null)
			return Status.OK_STATUS;// may be index not loaded till now, so no point of going ahead
		
		if (tnsCacheUpdater == null) {
			return Status.OK_STATUS;
		}
		try {
			delta.accept(tnsCacheUpdater);
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, BpmnCorePlugin.PLUGIN_ID, "Bpmn Cache update failed for project:"+project.getName(),e);
		}catch (Exception e) {
			BpmnCorePlugin.log(e);
		}
		Queue<Throwable> q = tnsCacheUpdater.getErrors();
		Throwable t = null;
		do {
			// drain the error queue
			t = q.poll();
			if(t != null)
				BpmnCorePlugin.log(t);
		} while (t != null);
		
		return Status.OK_STATUS;
	}
	
	public QualifiedName getKey() {
		return qn;
	}

}
