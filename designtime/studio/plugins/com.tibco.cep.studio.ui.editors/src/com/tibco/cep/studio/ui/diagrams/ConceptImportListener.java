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

public class ConceptImportListener implements IImportProjectListener {

	class GenerateConceptDiagramJob extends Job {

		private IProject fProject;

		public GenerateConceptDiagramJob(String name, IProject project) {
			super(name);
			this.fProject = project;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				monitor.beginTask("Generating concept diagram for "+fProject.getName(), 10);
				SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 8);
				ConceptDiagramManager manager = new ConceptDiagramManager(fProject, subMonitor);
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
		GenerateConceptDiagramJob job = new GenerateConceptDiagramJob("Generating concept diagram for "+project.getName(), project);
//		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//		ISchedulingRule rule = ruleFactory.createRule(project);
//		job.setRule(rule);
		job.schedule();
	}

}
