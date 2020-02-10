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

public class ProjectDiagramImportListener implements IImportProjectListener {

	class GenerateProjectDiagramJob extends Job {

		private IProject fProject;

		public GenerateProjectDiagramJob(String name, IProject project) {
			super(name);
			this.fProject = project;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				monitor.beginTask("Generating project diagram for "+fProject.getName(), 10);
				SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 8);
				ProjectDiagramManager manager = new ProjectDiagramManager(fProject, subMonitor);
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
		GenerateProjectDiagramJob job = new GenerateProjectDiagramJob("Generating Project diagram for "+project.getName(), project);
//		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//		ISchedulingRule rule = ruleFactory.createRule(project);
//		job.setRule(rule);
		job.schedule();
	}
	
}
