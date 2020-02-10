package com.tibco.cep.bpmn.core.index.update;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapperFactory;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.TnsCacheManager;

/**
 * Job of this class is to update tns cache , when ever bpmn process added/changed/removed
 * ideally visit method of this class should be called  inside {@link Job} , where bpmnIndex is set as property
 *  
 * @author majha
 *
 */
public class BpmnTnsCacheUpdater implements IResourceVisitor, IResourceDeltaVisitor {
	
	private EMFTnsCache tnsCache;
	private List<DesignerProject> fReferencedProjectLocations = new ArrayList<DesignerProject>();
	

	public BpmnTnsCacheUpdater(EMFTnsCache cache) {
		this.tnsCache = cache;
	}
	
	private void addReferencedProjectElements(IProject project,
			DesignerProject referencedProject) {
		addReferenceProjLocation(referencedProject);
		cacheEntries(project, referencedProject);
	}

	private void addReferenceProjLocation(DesignerProject referencedProject) {
		if (!fReferencedProjectLocations.contains(referencedProject)) {
			fReferencedProjectLocations.add(referencedProject);
		} else {
			System.err.println("Reference proj location already exists");
		}
	}

	private void cacheEntries(IProject project, ElementContainer container) {
		EList<DesignerElement> entries = container.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement designerElement = entries.get(i);
			if (designerElement instanceof ElementContainer) {
				cacheEntries(project, (ElementContainer) designerElement);
			} else if (designerElement instanceof SharedElement) {
				SharedElement element = (SharedElement) designerElement;
				cacheSharedElement(project, element);
			}
		}
	}

	private void cacheSharedElement(IProject project,final SharedElement sharedElement) {
		String fileName = sharedElement.getFileName();
		int idx = fileName.lastIndexOf('.');
		String ext = "";
		if (idx >= 0) {
			ext = fileName.substring(idx + 1);
		}
		// TODO: Need to check for BPMN EXtension
		if (!TnsCacheManager.isCacheResource(ext)) {
			return;
		}
		InputStream stream = null;
		if (sharedElement instanceof SharedEntityElement) {
			// TODO Manish: The shared element for Process does not exist in the Core Index.I will work with Ryan to get those changes
			// In case of Process it will be SharedProcessElement which is not a Core EMF Entity type because process belongs to bpmn ecore.
			// ProcesElement will exist in the core index as a process element reference .I need to talk to ryan to sort it out.
			Entity e = ((SharedEntityElement) sharedElement).getSharedEntity();
			try {
				stream = CommonIndexUtils.getEObjectInputStream(e);
			} catch (IOException e1) {
				tnsCache.addError(e1);
			}
		} else {
			
			stream = getInputStream(sharedElement);
		}

		if (stream != null) {
			final IProject p = project;
			final InputStream s = stream;
			final QualifiedName qn = new QualifiedName(p.getName(),GUIDGenerator.getGUID());
			Job j = new IndexJob<EObject>("Update Tns ") {
				@Override
				public IStatus runJob(IProgressMonitor monitor) {
					// TODO Manish: The shared element for Process does not exist in the Core Index.I will work with Ryan to get those changes
					
					tnsCache.resourceChanged(p.getLocation().toString() + "/" + sharedElement.getEntryPath() + sharedElement.getFileName(), s);
					return Status.OK_STATUS;
				}
				
				public QualifiedName getKey() {
					return qn;
				}

			};
			j.setProperty(qn, BpmnModelCache.getInstance().getIndex(p.getName()));
			j.schedule();
			try {
				stream.close();
			} catch (Exception e) {
				tnsCache.addError(e);
			}
		}

	}

	private InputStream getInputStream(SharedElement sharedElement) {
		JarFile jarFile;
		try {
			jarFile = new JarFile(sharedElement.getArchivePath());
			JarEntry entry = (JarEntry) jarFile.getEntry(sharedElement.getEntryPath() + sharedElement.getFileName());
			return jarFile.getInputStream(entry);
		} catch (IOException e) {
			tnsCache.addError(e);
		}
		return null;
	}

	public EMFTnsCache getTnsCache() {
		return tnsCache;
	}

	@SuppressWarnings("unused")
	private void processProjectReferences(IProject project) {
		DesignerProject index = IndexUtils.getIndex(project);
		if (index == null) {
			return;
		}
		EList<DesignerProject> projects = index.getReferencedProjects();
		for (int i = 0; i < projects.size(); i++) {
			DesignerProject referencedProject = projects.get(i);
			addReferencedProjectElements(project, referencedProject);
		}
	}

	
	public Queue<Throwable> getErrors() {
		return tnsCache.getErrors();
	}


	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
        if (delta.getResource() instanceof IProject
                && delta.getKind() == IResourceDelta.REMOVED) {
        	removeCache((IProject) delta.getResource());
            return false;
        }
        
		IResource resource = delta.getResource();
		if (resource instanceof IProject) {
			return true;
		}
		
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		if (delta.getKind() == IResourceDelta.REMOVED) {
			if (!BpmnTnsCacheManager.isCacheResource(file)) {
				if(file.getFileExtension()!= null  && !file.getFileExtension().isEmpty() && file.getFileExtension().equals("wsdl")){
//					String path = file.getLocation().toPortableString(); 
					WsdlWrapperFactory.removeWsdl(file);
				}else if(file.getFileExtension()!= null  && !file.getFileExtension().isEmpty() && TnsCacheManager.isCacheResource(file)){
					String path = file.getProjectRelativePath().makeAbsolute().removeFileExtension().toPortableString();
					CommonECoreHelper.removeItemDefinitionsForLoc(file.getProject().getName(), path);
				}
			}
			return false;
		}
	
		if (delta.getKind() == IResourceDelta.ADDED ) {
			if (!BpmnTnsCacheManager.isCacheResource(file)
					&& file.getFileExtension() != null
					&& !file.getFileExtension().isEmpty()
					&& !file.getFileExtension().equals("wsdl")
					&& TnsCacheManager.isCacheResource(file)) {
				String path = file.getLocation().toString();
				ECoreHelper.getItemDefinitionUsingLocation(file.getProject()
						.getName(), path);// this will add itemdef for this
											// resource in index
			}
			
		}
		
		return visit(file);
	}
	
	private void removeCache(IProject project) {
		BpmnCorePlugin.removeCache(project.getName());
	}
	
	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IProject) {
			//processProjectReferences((IProject) resource);
		}
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		if (!file.exists() /*|| !file.isSynchronized(0)*/ /* should we check sync state here? */) {
			return false;
		}
		if (BpmnTnsCacheManager.isCacheResource(file)) {
				InputStream contents = file.getContents();
				try {
					addProcessFolderIfAbsentToIndex(file);
					tnsCache.resourceChanged(file.getLocation().toString(), contents);
				} catch (Exception e) {
					tnsCache.addError(e);
				} finally {
					try {
						contents.close();
					} catch (IOException e) {
						//ignore
					}
				}
		}else if(file.getFileExtension()!= null && !file.getFileExtension().isEmpty() && file.getFileExtension().equals("wsdl")){
//			String path = file.getLocation().toPortableString();
			WsdlWrapperFactory.wsdlChanged(file.getProject(), file); 
			
		}
		return false;
	}

	private void addProcessFolderIfAbsentToIndex(IFile file){
		String projName = file.getProject().getName();
		DesignerProject fIndex = StudioProjectCache.getInstance().getIndex(projName);
		if(fIndex != null){
			IPath projectRelativePath = file.getProjectRelativePath();
			projectRelativePath = projectRelativePath.removeLastSegments(1);
			CommonIndexUtils.getFolderForFile(fIndex,
					projectRelativePath.toPortableString(), true);
		}
	}
}
