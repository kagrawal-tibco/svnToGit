package com.tibco.cep.studio.core.repo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class AdapterCacheUpdater implements IResourceVisitor, IResourceDeltaVisitor {

	private List<DesignerProject> fReferencedProjectLocations = new ArrayList<DesignerProject>();

	public AdapterCacheUpdater() {
	}

	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IProject) {
			processProjectReferences((IProject) resource);
		}
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		if (!file.exists() /*|| !file.isSynchronized(0)*/ /* should we check sync state here? */) {
			return false;
		}
//		if (TnsCacheManager.isCacheResource(file)) {
			if (IndexUtils.isRuleFunctionType(file.getFileExtension())
					|| IndexUtils.isRuleType(file.getFileExtension())) {
				Compilable rule = IndexUtils.getRule(file.getProject().getName(), file.getProjectRelativePath().removeFileExtension().toString(), ELEMENT_TYPES.RULE_FUNCTION);
				if (rule == null) {
					rule = IndexUtils.getRule(file.getProject().getName(), file.getProjectRelativePath().removeFileExtension().toString(), ELEMENT_TYPES.RULE);
				}
				if (rule == null) {
					rule = IndexUtils.getRule(file.getProject().getName(), file.getProjectRelativePath().removeFileExtension().toString(), ELEMENT_TYPES.RULE_TEMPLATE);
				}
				if (rule == null) {
					return false;
				}
//				rule.setOwnerProjectName(file.getProject().getName());
				CoreAdapterFactory.clearCachedAdapter(file.getProject().getName(), rule);
			} else if (IndexUtils.isEntityType(file)) {
				Entity entity = IndexUtils.getEntity(file.getProject().getName(), file.getProjectRelativePath().removeFileExtension().toString());
				if (entity == null) {
					DesignerElement element = IndexUtils.getElement(file);
					if (element instanceof EntityElement) {
						entity = ((EntityElement) element).getEntity();
					}
				}
				if (entity == null) {
					return false;
				}
				CoreAdapterFactory.clearCachedAdapter(file.getProject().getName(), entity);
				// clear all 'child' adapters, for instance event expiry actions or state machine actions
				TreeIterator<EObject> allContents = entity.eAllContents();
				while (allContents.hasNext()) {
					EObject object = (EObject) allContents.next();
					if (object instanceof Entity) {
						CoreAdapterFactory.clearCachedAdapter(file.getProject().getName(), (Entity)object);
					}
				}
			}
//		}
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

//	private void reloadReferencedProjectElements(IProject project,
//			DesignerProject referencedProject) {
//		removeReferencedProjectElements(project, referencedProject);
//		addReferencedProjectElements(project, referencedProject);
//	}
	
	private void addReferencedProjectElements(IProject project,
			DesignerProject referencedProject) {
		addReferenceProjLocation(referencedProject);
		// this will be done lazily, no need to do this here
//		cacheEntries(project, referencedProject);
	}

//	private void cacheEntries(IProject project,
//			ElementContainer container) {
//		EList<DesignerElement> entries = container.getEntries();
//		for (int i = 0; i < entries.size(); i++) {
//			DesignerElement designerElement = entries.get(i);
//			if (designerElement instanceof ElementContainer) {
//				cacheEntries(project, (ElementContainer) designerElement);
//			} else if (designerElement instanceof SharedElement) {
//				SharedElement element = (SharedElement) designerElement;
//				cacheSharedElement(project, element);
//			}
//		}
//	}

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
				if (element instanceof SharedEntityElement) {
					CoreAdapterFactory.clearCachedAdapter(project.getName(), ((SharedEntityElement) element).getEntity());
				} else if (element instanceof SharedRuleElement) {
					CoreAdapterFactory.clearCachedAdapter(project.getName(), ((SharedRuleElement) element).getRule());
				}
			}
		}
	}

//	private void cacheSharedElement(IProject project, SharedElement sharedElement) {
//		String fileName = sharedElement.getFileName();
//		int idx = fileName.lastIndexOf('.');
//		String ext = "";
//		if (idx >= 0) {
//			ext = fileName.substring(idx+1);
//		}
//		if (!TnsCacheManager.isCacheResource(ext)) {
//			return;
//		}
//		InputStream stream = null;
//		if (sharedElement instanceof SharedRuleElement) {
//			Compilable rule = ((SharedRuleElement) sharedElement).getRule();
//			if (rule == null) {
//				return;
//			}
//			rule.setOwnerProjectName(project.getName());
//			try {
//				stream = IndexUtils.getEObjectInputStream(rule);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			if (sharedElement instanceof SharedEntityElement) {
//				Entity e = ((SharedEntityElement) sharedElement).getSharedEntity();
//				try {
//					stream = CommonIndexUtils.getEObjectInputStream(e);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			} else {
//				stream = getInputStream(sharedElement);
//			}
//		}
//
//		if (stream != null) {
////			fCache.resourceChanged(project.getLocation().toString()+"/"+sharedElement.getEntryPath()+sharedElement.getFileName(), stream);
//			try {
//				stream.close();
//			} catch (Exception e) {
//			}
//		}
//		
//	}

//	private InputStream getInputStream(SharedElement sharedElement) {
//		JarFile jarFile;
//		try {
//			jarFile = new JarFile(sharedElement.getArchivePath());
//			JarEntry entry = (JarEntry) jarFile.getEntry(sharedElement.getEntryPath()+sharedElement.getFileName());
//			return jarFile.getInputStream(entry);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

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
		
		IFile file = (IFile) resource;
		if (delta.getKind() == IResourceDelta.REMOVED) {
//			fCache.resourceRemoved(file.getLocation().toString());
			return false;
		}
		return visit(file);
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
}
