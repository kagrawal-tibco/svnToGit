package com.tibco.cep.studio.core.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.ClasspathValidator;
import com.tibco.cep.studio.core.validation.DependentResources;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.IResourceValidatorExtension;
import com.tibco.cep.studio.core.validation.SharedElementValidationContext;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;

public class StudioProjectBuilder extends IncrementalProjectBuilder {

	class StudioDeltaVisitor implements IResourceDeltaVisitor {
		
		private IProgressMonitor fMonitor;
		private boolean fCountOnly;
		private int fCount = 0;
		private int fCurrentCount = 1;
		private List<IFile> fProcessedFiles = new ArrayList<IFile>();
		private List<IFile> fDependentResources = new ArrayList<IFile>();

		public StudioDeltaVisitor(boolean countOnly, IProgressMonitor monitor) {
			this.fCountOnly = countOnly;
			this.fMonitor = monitor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
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
			if ("class".equals(file.getFileExtension()) || "java".equals(file.getFileExtension()) || "smap".equals(file.getFileExtension())) {
				return false; // skip class/java files for efficiency, rather than go through the validation mechanism
			}
//			if(!file.exists()){
//				return true;
//			}
			fMonitor.subTask("Building "+file.getName()+" ("+fCurrentCount+" of "+fCount+")");
			ValidationContext vldContext = new ValidationContext(file ,IResourceDelta.ADDED, INCREMENTAL_BUILD);
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

	class StudioResourceVisitor implements IResourceVisitor {
		
		private boolean fCountOnly;
		private int fCount = 0;
		private int fCurrentCount = 1;
		private IProgressMonitor fMonitor;

		public StudioResourceVisitor(boolean countOnly, IProgressMonitor monitor) {
			this.fCountOnly = countOnly;
			this.fMonitor = monitor;
		}

		public boolean visit(IResource resource) {
			if (resource instanceof IProject && !fCountOnly && !fMonitor.isCanceled()) {
				try {
					validateReferencedProjects((IProject)resource);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			if (!(resource instanceof IFile)) {
				return true;
			}
			if (fMonitor.isCanceled()) {
				return false;
			}
			if (fCountOnly) {
				fCount++;
				return true;
			}
			IFile file = (IFile) resource;
			fMonitor.subTask("Building "+file.getName()+" ("+fCurrentCount+" of "+fCount+")");
			try {
				ValidationContext vldContext = new ValidationContext(resource ,IResourceDelta.CHANGED , FULL_BUILD);
				processFile(vldContext);
			} catch (CoreException e) {
				fMonitor.setCanceled(true);
				StudioCorePlugin.log(e);
				return false;
			} catch (Exception e) {
				StudioCorePlugin.log(e);
				return false;
			}
			fMonitor.worked(1);
			fCurrentCount++;
			//return true to continue visiting children.
			return true;
		}
		
		private void validateReferencedProjects(IProject proj) throws CoreException {
			ValidatorInfo[] projectResourceValidators = ValidationUtils.getProjectResourceValidators();
			if (projectResourceValidators == null) {
				return;
			}
			DesignerProject index = IndexUtils.getIndex(proj.getName());
			proj.deleteMarkers(IResourceValidatorExtension.SHARED_ELEMENT_VALIDATION_MARKER_TYPE, false, IResource.DEPTH_INFINITE);
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
			for (DesignerProject designerProject : referencedProjects) {
				processReferencedProject(proj, designerProject, projectResourceValidators);
			}
		}

		public int getCount() {
			return fCount;
		}

		public void setCountOnly(boolean countOnly) {
			fCountOnly = countOnly;
		}
		
		public void processReferencedProject(IProject proj,
				ElementContainer container,
				ValidatorInfo[] projectResourceValidators) throws CoreException {
			EList<DesignerElement> entries = container.getEntries();
			for (DesignerElement designerElement : entries) {
				if (designerElement instanceof ElementContainer) {
					processReferencedProject(proj, (ElementContainer) designerElement, projectResourceValidators);
				} else if (designerElement instanceof SharedElement) {				
					validateSharedResource(proj, (SharedElement) designerElement, projectResourceValidators);
				}
			}
		}
		
		private void validateSharedResource(IProject project, SharedElement sharedElement, 
				ValidatorInfo[] projectResourceValidators) throws CoreException {
			for (ValidatorInfo validatorInfo : projectResourceValidators) {
				if (!(validatorInfo.fValidator instanceof IResourceValidatorExtension)) {
					continue;
				}
				IResourceValidatorExtension validator = (IResourceValidatorExtension) validatorInfo.fValidator;
				if (validatorInfo.enablesFor(sharedElement)) {
					SharedElementValidationContext vldContext = new SharedElementValidationContext(project, sharedElement, IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD);
					if (!validator.validate(vldContext)) {
						if (!validatorInfo.fValidator.canContinue()) {
							// something happened (for example, an unrecoverable error), and we cannot continue
							fMonitor.setCanceled(true);
							throw new CoreException(Status.CANCEL_STATUS);
						}
					}
				}
			}
		}


	}

	public static final String BUILDER_ID = "com.tibco.cep.studio.core.studioProjectBuilder";

	private static final int TYPE_CLEAN 		= 0;
	private static final int TYPE_FULL_BUILD	= 1;
	private static final int TYPE_INC_BUILD		= 2;
	
	public StudioProjectBuilder() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		ClasspathValidator classpathValidation = new ClasspathValidator(getProject());
		boolean validated = classpathValidation.validate();
		if(!validated){
			return null;
		}
		if (StudioModelManager.isCLIMode()) {
			return null; // no need to build while running studio-tools
		}
		updateStudioConfiguration();
		
		FunctionsCatalogManager.getInstance().getStaticRegistry(); // This will schedule a registry load if it hasn't been done already
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else{
				incrementalBuild(delta, monitor);
			}
		}
		return null;
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
		if (vldContext.getBuildType() != FULL_BUILD) {
			// collect dependent resources for rebuilding
			collectDependentResources(vldContext);
		}
		for (ValidatorInfo validatorInfo : projectResourceValidators) {
			if (validatorInfo.enablesFor(resource)) {			
				try {
					if (!validatorInfo.fValidator.validate(vldContext)) {
						if (!validatorInfo.fValidator.canContinue()) {
							// something happened (for example, an unrecoverable error), and we cannot continue
							StudioCorePlugin.log("Project Validation Error:["+resource.getProject().getName()+"]:"+resource.getFullPath().toPortableString());
							throw new CoreException(Status.CANCEL_STATUS);
						}
					}
				} catch (Exception e) {
//						e.printStackTrace(); // do not allow individual validators to stop validation
					StudioCorePlugin.log("Project Validation Error:["+resource.getProject().getName()+"]:"+resource.getFullPath().toPortableString(), e);
				}
			}
		}

	}
	
	public void updateStudioConfiguration() {
		StudioProjectConfiguration configuration = 
				StudioProjectConfigurationManager.getInstance().getProjectConfiguration(getProject().getName());
		if (!configuration.getName().equals(getProject().getName())) {
			String oldProjectName = configuration.getName();
			configuration.setName(getProject().getName());
			for (JavaSourceFolderEntry entry: configuration.getJavaSourceFolderEntries()) {
				String path = entry.getPath();
				path = path.replace(oldProjectName, getProject().getName());
				entry.setPath(path);
			}
			configuration.getEnterpriseArchiveConfiguration().setName(getProject().getName());
			try {
				StudioProjectConfigurationManager.getInstance().saveConfiguration(getProject().getName(), configuration);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void collectDependentResources(ValidationContext vldContext) {
		IResource resource = vldContext.getResource();
		
		if (CommonUtil.isNonEntityResource(resource))
			return;
		
		String name = null;
		Object resourceElement = IndexUtils.getElement(resource);
		if (resourceElement == null && resource instanceof IFile){
			if (IndexUtils.isEMFType((IFile) resource)) {
				resourceElement = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource), false);
				if (resourceElement instanceof Entity) {
					name = ((Entity) resourceElement).getName();
				}
			}
		}
		else if (resourceElement instanceof DesignerElement) {
			name = ((DesignerElement) resourceElement).getName();
		}
		
		if (resourceElement != null) {
			SearchResult searchIndex = SearchUtils.searchIndex(resourceElement, resource.getProject().getName(), name);
			List<EObject> exactMatches = searchIndex.getExactMatches();
			for (EObject object : exactMatches) {
				IFile file = SearchUtils.getFile(object);
				vldContext.addDependentResource(file);
			}
		}
		if (resourceElement != null && resourceElement instanceof EntityElement) {
			Entity entity = ((EntityElement) resourceElement).getEntity();
			if (entity instanceof Concept || entity instanceof Event) {
				// also need to collect super/sub entities' dependencies.  
				// For instance, you can refer to a super concept's properties through the sub concept, so we need to re-validate
				// those dependencies as well
				List<? extends Entity> ancestorEntities = ModelUtils.collectAncestorEntities(entity, true);
				for (Entity ancestor : ancestorEntities) {
					SearchResult depIndex = SearchUtils.searchIndex(ancestor, resource.getProject().getName(), ancestor.getName());
					List<EObject> matches = depIndex.getExactMatches();
					for (EObject object : matches) {
						IFile file = SearchUtils.getFile(object);
						vldContext.addDependentResource(file);
					}
				}
			}
		}
	}
	
	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			StudioResourceVisitor visitor = new StudioResourceVisitor(true, monitor);
			getProject().accept(visitor);
			monitor.beginTask("Building "+getProject().getName(), visitor.getCount());
			System.out.println("building(full) a total of "+visitor.getCount());
			visitor.setCountOnly(false);
			Date start = new Date();
			getProject().accept(visitor);
			Date end = new Date();
			long time = end.getTime() - start.getTime();
			if (visitor.getCount() > 0) {
				System.out.println(getProject().getName()+ " built. Total build time "+time
						+"ms for "+visitor.getCount() + " objects (average time "
						+ (time/visitor.getCount())+"ms)");
			}
			notifyParticipants(getProject(), null, TYPE_FULL_BUILD);
			monitor.done();
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"Indexing error",e));
		}
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		try {
			StudioDeltaVisitor visitor = new StudioDeltaVisitor(true, monitor);
			// first, just count the number of files to be processed (for reporting)
			delta.accept(visitor);
			monitor.beginTask("Building "+getProject().getName(), visitor.getCount());
			System.out.println("building a total of "+visitor.getCount());
			visitor.setCountOnly(false);
			Date start = new Date();
			// the visitor does the work.
			delta.accept(visitor);
			
			// process the dependent resources
			processDependentResources(visitor, new SubProgressMonitor(monitor, 1));
			
			Date end = new Date();
			long time = end.getTime() - start.getTime();
			if (visitor.getCount() > 0) {
				System.out.println(getProject().getName()+ " built. Total build time "+time
						+"ms for "+visitor.getCount() + " objects (average time "
						+ (time/visitor.getCount())+"ms)");
			}
			notifyParticipants(getProject(), delta, TYPE_INC_BUILD);
			monitor.done();
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"Indexing error",e));
		}
	}

	private void processDependentResources(StudioDeltaVisitor visitor, SubProgressMonitor subProgressMonitor) {
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
				ValidationContext vldContext = new ValidationContext(file ,IResourceDelta.CHANGED, FULL_BUILD); // mark as full build so other dependent resources are not collected
				processFile(vldContext);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		if (!project.isAccessible()) {
			return;
		}
		monitor.subTask("Deleting project markers");
		project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		// rebuild the index, in case there were issues in the creation/persistence of the index
		monitor.subTask("Rebuilding index for "+project.getName());
		StudioCorePlugin.getDesignerModelManager().rebuildIndex(project);
		// remove the cache as well, and it will be rebuilt on demand
		StudioCorePlugin.removeCache(project.getName());
		try {
			FunctionsCatalogManager.getInstance().reloadCustomRegistryEntry(project.getName(), StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName()));
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		notifyParticipants(project, null, TYPE_CLEAN);
		monitor.done();
	}

	private void notifyParticipants(IProject project, IResourceDelta delta,
			int type) {
		IStudioBuildParticipant[] buildParticipants = StudioBuildUtils.getBuildParticipants();
		for (int i = 0; i < buildParticipants.length; i++) {
			try {
				switch (type) {
				case TYPE_CLEAN:
					buildParticipants[i].cleanProject(project, new NullProgressMonitor());
					break;

				case TYPE_FULL_BUILD:
					buildParticipants[i].fullProjectBuild(project, new NullProgressMonitor());
					break;
					
				case TYPE_INC_BUILD:
					buildParticipants[i].incrementalProjectBuild(project, delta, new NullProgressMonitor());
					break;
					
				default:
					break;
				}
			} catch (Exception e) {
				// don't let any single participant to break the build
			}
		}
	}
	
}