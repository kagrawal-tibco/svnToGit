package com.tibco.cep.studio.core.mapper;

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
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class EntitySchemaCacheUpdater implements IResourceVisitor, IResourceDeltaVisitor {

	private EntitySchemaCache fCache;
	private List<DesignerProject> fReferencedProjectLocations = new ArrayList<DesignerProject>();
	private boolean deferLoad = false;

	public EntitySchemaCacheUpdater(EntitySchemaCache cache) {
		this.fCache = cache;
	}

	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IProject) {
//			processProjectReferences((IProject) resource);
		}
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		if (!file.exists() /*|| !file.isSynchronized(0)*/ /* should we check sync state here? */) {
			return false;
		}
		if (file.isLinked(IFile.CHECK_ANCESTORS)) {
//			return false;
		}
		if (EntitySchemaCacheManager.isCacheResource(file)) {
			if (!IndexUtils.isEntityType(file) && !IndexUtils.isXmlSchemaResource(file.getFileExtension())) {
				return true;
			}
			try {
				fCache.resourceChanged(file, deferLoad);
			} catch (Exception e) {
				fCache.addError(e);
			}
		}
		return false;
	}

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

	private void reloadReferencedProjectElements(IProject project,
			DesignerProject referencedProject) {
		removeReferencedProjectElements(project, referencedProject);
		addReferencedProjectElements(project, referencedProject);
	}
	
	private void addReferencedProjectElements(IProject project,
			DesignerProject referencedProject) {
		addReferenceProjLocation(referencedProject);
		cacheEntries(project, referencedProject);
	}

	private void cacheEntries(IProject project,
			ElementContainer container) {
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

	private void addReferenceProjLocation(DesignerProject referencedProject) {
		if (!fReferencedProjectLocations.contains(referencedProject)) {
			fReferencedProjectLocations.add(referencedProject);
		} else {
			System.err.println("Reference proj location already exists");
		}
	}

	private void removeReferenceProjLocation(DesignerProject referencedProject) {
		if (fReferencedProjectLocations.contains(referencedProject)) {
			fReferencedProjectLocations.remove(referencedProject);
		} else {
			System.err.println("Reference proj location didn't exist");
		}
	}
	
	private void removeReferencedProjectElements(IProject project,
			DesignerProject referencedProject) {
		removeReferenceProjLocation(referencedProject);
		removeEntries(project, referencedProject);
	}
	
	private void removeEntries(IProject project,
			ElementContainer container) {
		EList<DesignerElement> entries = container.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement designerElement = entries.get(i);
			if (designerElement instanceof ElementContainer) {
				removeEntries(project, (ElementContainer) designerElement);
			} else if (designerElement instanceof SharedElement) {
				SharedElement element = (SharedElement) designerElement;
				fCache.resourceRemoved("/"+element.getEntryPath()+element.getFileName());
//				fCache.resourceRemoved(project.getLocation().toString()+"/"+element.getEntryPath()+element.getFileName());
			}
		}
	}

	private void cacheSharedElement(IProject project, SharedElement sharedElement) {
		String fileName = sharedElement.getFileName();
		int idx = fileName.lastIndexOf('.');
		String ext = "";
		if (idx >= 0) {
			ext = fileName.substring(idx+1);
		}
		if (!EntitySchemaCacheManager.isCacheResource(ext)) {
			return;
		}
		InputStream stream = null;
		JarFile jarFile = null;
		try {
			if (sharedElement instanceof SharedRuleElement) {
				Compilable rule = ((SharedRuleElement) sharedElement).getRule();
				if (rule == null) {
					return;
				}
				rule.setOwnerProjectName(project.getName());
				try {
					stream = IndexUtils.getEObjectInputStream(rule);
				} catch (IOException e) {
					fCache.addError(e);
				}
			} else {
				if (sharedElement instanceof SharedEntityElement) {
					Entity e = ((SharedEntityElement) sharedElement).getSharedEntity();
					try {
						stream = CommonIndexUtils.getEObjectInputStream(e);
					} catch (IOException e1) {
						fCache.addError(e1);
					}
				} else {
					try {
						jarFile = new JarFile(sharedElement.getArchivePath());
						JarEntry entry = (JarEntry) jarFile.getEntry(sharedElement.getEntryPath()+sharedElement.getFileName());
						stream = jarFile.getInputStream(entry);
					} catch (IOException e) {
						fCache.addError(e);
					}
//					stream = getInputStream(sharedElement);
				}
			}

			if (stream != null) {
				
				if(sharedElement.getFileName().endsWith(".wsdl")||sharedElement.getFileName().endsWith(".xsd")) {
					fCache.resourceChanged(getSysIdforWsdlAndXsdInProjLib(sharedElement),stream);
				} else {
					fCache.resourceChanged(getSysIdforWsdlAndXsdInProjLib(sharedElement), stream);
//					fCache.resourceChanged(project.getLocation().toString()+"/"+sharedElement.getEntryPath()+sharedElement.getFileName(), stream);
				}
			}
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					fCache.addError(e);
				}
			}
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					fCache.addError(e);
				}
			}
		}
		
		
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
        if (delta.getResource() instanceof IProject
                && delta.getKind() == IResourceDelta.REMOVED) {
        	// remove cache
        	removeCache((IProject) delta.getResource());
            return false;
        }
        
		IResource resource = delta.getResource();
		if (resource instanceof IProject) {
			return handleProjectChanged(delta, (IProject) resource);
		}
		
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		if (delta.getFlags() == IResourceDelta.MARKERS) {
			return true;
		}
		
		IFile file = (IFile) resource;
		if (delta.getKind() == IResourceDelta.REMOVED) {
			// can't use isLinked here, as the linked resource is already gone
			fCache.resourceRemoved(getCacheKeyForRemovedFile(file));
			SharedElement sharedElement = IndexUtils.getSharedElement(file.getProject().getName(), file.getProjectRelativePath().toString());
			if (sharedElement != null) {
				// this element existed locally in the project as well as a project library.  The local file was deleted,
				// so we must insert the project library version into the cache
				cacheSharedElement(file.getProject(), sharedElement);
			}
			return false;
		}
		return visit(file);
	}

	private String getCacheKeyForRemovedFile(IFile file) {
		IPath path = file.getFullPath().removeFirstSegments(1);
		if (path.segment(0).endsWith("projlib")) {
			path = path.removeFirstSegments(1);
		} else {
			return IndexUtils.getFullPath(file);
		}
		return path.removeFileExtension().toString();
	}

	private void removeCache(IProject project) {
		StudioCorePlugin.removeCache(project.getName());
	}

	private boolean handleProjectChanged(IResourceDelta delta,
			IProject project) {
		if (delta.getKind() == IResourceDelta.REMOVED) {
			removeCache(project);
			return false;
		} else if (delta.getKind() == IResourceDelta.CHANGED) {
			if (!project.isOpen()) {
				removeCache(project);
				return false;
			}
		}
		return true;
	}

	public void updateProjectReferences(IProject project,
			EList<DesignerProject> referencedProjects) {
		for (int i = 0; i < referencedProjects.size(); i++) {
			DesignerProject designerProject = referencedProjects.get(i);
			if (!fReferencedProjectLocations.contains(designerProject)) {
				addReferencedProjectElements(project, designerProject);
			}
		}
		List<DesignerProject> removedProjects = new ArrayList<DesignerProject>();
		for (int i = 0; i < fReferencedProjectLocations.size(); i++) {
			DesignerProject designerProject = fReferencedProjectLocations.get(i);
			boolean found = false;
			for (int j = 0; j < referencedProjects.size(); j++) {
				if (referencedProjects.get(j).getArchivePath().equals(designerProject.getArchivePath())) {
					found = true;
					break;
				}
			}
			if (!found) {
				removedProjects.add(designerProject);
			}
		}
		for (DesignerProject designerProject : removedProjects) {
			removeReferencedProjectElements(project, designerProject);
		}
	}
	
	public Queue<Throwable> getErrors() {
		return fCache.getErrors();
	}
	
	/*
	 * Returns SysId for a WSDl or XSD file to be accessed from projLib
	 * @param SharedElement
	 * @return SysId for Wsdl or Xsd accessed inside the projlib
	 */
			 
	private String getSysIdforWsdlAndXsdInProjLib(SharedElement sharedElement){
//		String archivePath = sharedElement.getArchivePath().replaceAll("\\\\" ,"/");
//		int idx = archivePath.lastIndexOf('/');
//		String archiveName = archivePath.substring(idx);
		String jarPath=sharedElement.getArchivePath().replaceAll("\\\\" ,"/");
		String SysId="jar:file:///"+jarPath +"!/"+sharedElement.getEntryPath()+sharedElement.getFileName();
		
		return SysId;
		
	}

	public void deferLoad(boolean deferLoad) {
		this.deferLoad  = deferLoad;
	}
}
