package com.tibco.cep.studio.ui.diagrams;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.studio.core.converter.IImportProjectListener;
import com.tibco.cep.studio.core.util.CommonUtil;

public class EventImportListener implements IImportProjectListener {

	class GenerateEventDiagramJob extends Job {

		private IProject fProject;

		public GenerateEventDiagramJob(String name, IProject project) {
			super(name);
			this.fProject = project;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				monitor.beginTask("Generating event diagram for "+fProject.getName(), 10);
				SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 8);
				EventDiagramManager manager = new EventDiagramManager(fProject, subMonitor);
				manager.initialize();
				manager.generateModel();
				monitor.subTask("Refreshing project");
				CommonUtil.refresh(fProject, IResource.DEPTH_ONE, false);
				monitor.done();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Status.OK_STATUS;
		}
		
	}
	
	public void projectImported(IProject project, IProgressMonitor monitor) {
		GenerateEventDiagramJob job = new GenerateEventDiagramJob("Generating event diagram for "+project.getName(), project);
//		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//		ISchedulingRule rule = ruleFactory.createRule(project);
//		job.setRule(rule);
		job.schedule();
	}

}
