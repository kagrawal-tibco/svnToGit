package com.tibco.cep.bpmn.core.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.studio.core.builder.DefaultStudioBuildParticipant;
import com.tibco.cep.studio.core.builder.IStudioBuildParticipant;
import com.tibco.cep.studio.core.validation.DependentResources;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;

public class BpmnBuildParticipant extends DefaultStudioBuildParticipant implements IStudioBuildParticipant {

	public BpmnBuildParticipant() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cleanProject(IProject project,IProgressMonitor monitor) {
		BpmnCorePlugin.getDefault().getBpmnModelManager().rebuildIndex(project);
		BpmnCorePlugin.removeCache(project.getName());
	}

	@Override
	public void fullProjectBuild(IProject project,IProgressMonitor monitor) throws CoreException {
		BpmnResourceVisitor visitor = new BpmnResourceVisitor(true, monitor, true);
		project.accept(visitor);
		monitor.beginTask("Building BPMN project:"+project.getName(), visitor.getCount());
		BpmnCorePlugin.debug("building(full) a total of "+visitor.getCount());
		visitor.setCountOnly(true);
		Date start = new Date();
		project.accept(visitor);
		Date end = new Date();
		long time = end.getTime() - start.getTime();
		if (visitor.getCount() > 0) {
			BpmnCorePlugin.debug(project.getName()+ " built. Total build time "+time
					+"ms for "+visitor.getCount() + " objects (average time "
					+ (time/visitor.getCount())+"ms)");
		}
		monitor.done();
	}

	@Override
	public void incrementalProjectBuild(IProject project, IResourceDelta delta,IProgressMonitor monitor) throws CoreException {
//		BpmnResourceDeltaVisitor visitor = new BpmnResourceDeltaVisitor(true, monitor);
//		// first, just count the number of files to be processed (for reporting)
//		delta.accept(visitor);
//		monitor.beginTask("Building "+project.getName(), visitor.getCount());
//		System.out.println("building a total of "+visitor.getCount());
//		visitor.setCountOnly(false);
//		Date start = new Date();
//		// the visitor does the work.
//		delta.accept(visitor);
//		
//		// process the dependent resources
//		processDependentResources(visitor, new SubProgressMonitor(monitor, 1));
//		
//		Date end = new Date();
//		long time = end.getTime() - start.getTime();
//		if (visitor.getCount() > 0) {
//			System.out.println(project.getName()+ " built. Total build time "+time
//					+"ms for "+visitor.getCount() + " objects (average time "
//					+ (time/visitor.getCount())+"ms)");
//		}
//		monitor.done();

	}
	
	void processFile(ValidationContext vldContext) throws CoreException {
		if (vldContext == null) return;
		IResource resource = vldContext.getResource();		
		if(resource instanceof IFile){
			IFile file = (IFile) resource;
			if(!file.exists()){
				QualifiedName key = ValidationUtils.getDependentQN(file);
				Object sessionProperty = file.getProject().getSessionProperty(key);
				if (sessionProperty instanceof DependentResources && ((DependentResources) sessionProperty).isActivated()) {
					List<IResource> dependentResources = ((DependentResources) sessionProperty).getDependentResources();
					for (int i = 0; i < dependentResources.size(); i++) {
						IFile dependentResource = (IFile) dependentResources.get(i);
						vldContext.addDependentResource(dependentResource);
					}
				}
				file.getProject().setSessionProperty(key, null);
				return;
			}
		}


		ValidatorInfo[] projectResourceValidators = ValidationUtils.getProjectResourceValidators();
		if (projectResourceValidators == null) {
			return;
		}
		if (vldContext.getBuildType() != IncrementalProjectBuilder.FULL_BUILD) {
			// collect dependent resources for rebuilding
			collectDependentResources(vldContext);
		}
		for (ValidatorInfo validatorInfo : projectResourceValidators) {
			if (validatorInfo.enablesFor(resource)) {			
				try {
					if (!validatorInfo.fValidator.validate(vldContext)) {
						if (!validatorInfo.fValidator.canContinue()) {
							// something happened (for example, an unrecoverable error), and we cannot continue
							throw new CoreException(Status.CANCEL_STATUS);
						}
					}
				} catch (Exception e) {
					BpmnCorePlugin.log(e); // do not allow individual validators to stop validation
				}
			}
		}
	}
	
	/**
	 * @param vldContext
	 */
	private void collectDependentResources(ValidationContext vldContext) {
		IResource resource = vldContext.getResource();
		
//		String name = null;
		try {
		EObject resourceElement = BpmnIndexUtils.getElement(resource);
		if (resourceElement == null){
			resourceElement = BpmnIndexUtils.loadEObject(resource, true);
		}
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
		}

	}
	
	/**
	 * @param visitor
	 * @param subProgressMonitor
	 */
	@SuppressWarnings("unused")
	private void processDependentResources(BpmnResourceDeltaVisitor visitor, SubProgressMonitor subProgressMonitor) {
		List<IFile> processedFiles = visitor.getProcessedFiles();
		List<IFile> dependentResources = visitor.getDependentResources();
		for (IFile file : processedFiles) {
			if (dependentResources.contains(file)) {
				dependentResources.remove(file);
			}
		}
		try {
			for (IFile file : dependentResources) {
				if (file == null) {
					continue;
				}
				ValidationContext vldContext = new ValidationContext(file ,IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD); // mark as full build so other dependent resources are not collected
				processFile(vldContext);
			}
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////
	class BpmnResourceDeltaVisitor implements IResourceDeltaVisitor {
		private IProgressMonitor fMonitor;
		private boolean fCountOnly;
		private int fCount = 0;
		private int fCurrentCount = 1;
		private List<IFile> fProcessedFiles = new ArrayList<IFile>();
		private List<IFile> fDependentResources = new ArrayList<IFile>();
		

		/**
		 * @param monitor
		 * @param countOnly
		 */
		public BpmnResourceDeltaVisitor(boolean countOnly,IProgressMonitor monitor) {
			super();
			fMonitor = monitor;
			fCountOnly = countOnly;
		}


		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			if (fMonitor.isCanceled()) {
				return false;
			}
			IResource resource = delta.getResource();
			if (!(resource instanceof IFile)) {
				return true;
			}
			if (fCountOnly) {
				fCount ++;
				return true;
			}
			IFile file = (IFile) resource;
//			if(!file.exists()){
//				return true;
//			}
			fMonitor.subTask("Building "+file.getName()+" ("+fCurrentCount+" of "+fCount+")");
			ValidationContext vldContext = new ValidationContext(file ,IResourceDelta.ADDED, IncrementalProjectBuilder.INCREMENTAL_BUILD);
			switch (delta.getKind()) {			
			case IResourceDelta.ADDED:
				// handle added resource	
				vldContext.setModificationType(IResourceDelta.ADDED);
				processFile(vldContext);
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource	
				vldContext.setModificationType(IResourceDelta.REMOVED);
				processFile(vldContext);
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				vldContext.setModificationType(IResourceDelta.CHANGED);
				processFile(vldContext);
				break;
			}
			if (!fProcessedFiles.contains(file)) {
				fProcessedFiles.add(file);
			}
			if (vldContext.getDependentResources().size() > 0) {
				List<IFile> dependentResources = vldContext.getDependentResources();
				for (IFile depRes : dependentResources) {
					if (!fDependentResources.contains(depRes)) {
						fDependentResources.add(depRes);
					}
				}
			}
			fMonitor.worked(1);
			fCurrentCount++;
			//return true to continue visiting children.
			return true;
		}
		
		public List<IFile> getProcessedFiles() {
			return fProcessedFiles;
		}

		public List<IFile> getDependentResources() {
			return fDependentResources;
		}

		public int getCount() {
			return fCount;
		}
		
		public void setCountOnly(boolean countOnly) {
			fCountOnly = countOnly;
		}
		
	}

}
