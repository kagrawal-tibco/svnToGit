package com.tibco.cep.studio.debug.core.launch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.WorkspaceSourceContainer;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class StudioProjectSourceContainer extends CompositeSourceContainer {

	// Studio project
	private IProject fProject;
	// Source folders
	private ISourceContainer[] fProjectSourceContainer;
	// Generic project container
	@SuppressWarnings("unused")
	private ISourceContainer[] fCoreLibraryContainers;
	private ISourceContainer[] fProjectLibraryContainers;
	@SuppressWarnings("unused")
	private ISourceContainer[] fCustomFunctionContainers;
	@SuppressWarnings("unused")
	private ISourceContainer[] fThirdPartyContainers;
	private ISourceContainer[] fOtherContainers;
	
	private static String[] fgJavaExtensions = null;
	
	/**
	 * initialize java file extensions
	 */
	static {
		String[] extensions = StudioCore.getCodeExtensions();
		fgJavaExtensions = new String[extensions.length];
		for (int i = 0; i < extensions.length; i++) {
			String ext = extensions[i];
			fgJavaExtensions[i] = '.' + ext;
		}
	}
	
	/**
	 * Unique identifier for Java project source container type
	 * (value <code>org.eclipse.jdt.launching.sourceContainer.javaProject</code>).
	 */
	public static final String TYPE_ID = StudioDebugCorePlugin.getUniqueIdentifier() + ".sourceContainer.studioProject";   //$NON-NLS-1$
	
	/**
	 * Constructs a source container on the given Java project.
	 * 
	 * @param project project to look for source in
	 */
	public StudioProjectSourceContainer(IProject project) {
		fProject = project;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainer#getName()
	 */
	public String getName() {
		return fProject.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainer#getType()
	 */
	public ISourceContainerType getType() {
		return getSourceContainerType(TYPE_ID);
	}
	
	/**
	 * Returns the Studio project associated with this source container.
	 * 
	 * @return Studio project
	 */
	public IProject getStudioProject() {
		return fProject;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.containers.CompositeSourceContainer#createSourceContainers()
	 */
	protected ISourceContainer[] createSourceContainers() throws CoreException {
		List<ISourceContainer> containers = new ArrayList<ISourceContainer>();
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (fProject.getProject().isOpen()) {
			ISourceContainer theProject = new ProjectSourceContainer(fProject, false);
			fProjectSourceContainer = new ISourceContainer[] {theProject};
			containers.add(theProject);
			
			StudioProjectConfiguration pcfg = StudioProjectConfigurationManager
					.getInstance().getProjectConfiguration(getName());
			if(pcfg != null) {
				List<ISourceContainer> otherSourceContainers = new ArrayList<ISourceContainer>();
				
				 List<ISourceContainer> projectLibraryContainers = getProjectLibarySourceContainers(pcfg);
				 fProjectLibraryContainers = (ISourceContainer[]) projectLibraryContainers.toArray(new ISourceContainer[projectLibraryContainers.size()]);
				 otherSourceContainers.addAll(projectLibraryContainers);
				 
				 List<ISourceContainer> coreLibraryContainers = getCoreLibrarySourceContainers(pcfg);
				 fCoreLibraryContainers = (ISourceContainer[]) coreLibraryContainers.toArray(new ISourceContainer[coreLibraryContainers.size()]);
				 otherSourceContainers.addAll(coreLibraryContainers);
				 
				 List<ISourceContainer> customFunctionContainers = getCustomFunctionSourceContainers(pcfg);
				 fCustomFunctionContainers = (ISourceContainer[]) customFunctionContainers.toArray(new ISourceContainer[customFunctionContainers.size()]);
				 otherSourceContainers.addAll(customFunctionContainers);
				 
				 List<ISourceContainer> thirdPartyContainers = getThirdPartySourceContainers(pcfg);
				 fThirdPartyContainers = (ISourceContainer[]) thirdPartyContainers.toArray(new ISourceContainer[thirdPartyContainers.size()]);
				 otherSourceContainers.addAll(thirdPartyContainers);
				 
				 
				 containers.addAll(otherSourceContainers);
				 
				 fOtherContainers = otherSourceContainers.toArray(new ISourceContainer[otherSourceContainers.size()]);
				 
			}
			
		}
		return (ISourceContainer[]) containers.toArray(new ISourceContainer[containers.size()]);
	}
	
	private static List<ISourceContainer> getProjectLibarySourceContainers(StudioProjectConfiguration pcfg) {
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		Iterator<BuildPathEntry> iter = pcfg.getEntriesByType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY).iterator();
		for(Iterator<BuildPathEntry> it = iter; it.hasNext();) {
			BuildPathEntry entry = it.next();
			String path = entry.getPath(entry.isVar());
			if(entry.getEntryType() == LIBRARY_PATH_TYPE.PROJECT_LIBRARY) {
				sourceContainers.add(new JarEntrySourceContainer(path,pcfg.getName()));
			}
		}		
		return sourceContainers;
	}
	
	private static List<ISourceContainer> getCoreLibrarySourceContainers(StudioProjectConfiguration pcfg) {
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		Iterator<BuildPathEntry> iter = pcfg.getEntriesByType(LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY).iterator();
		for(Iterator<BuildPathEntry> it = iter; it.hasNext();) {
			BuildPathEntry entry = it.next();
			String path = entry.getPath();
			if(entry.getEntryType() == LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY) {
				sourceContainers.add(new JarEntrySourceContainer(path,pcfg.getName()));
			}
		}		
		return sourceContainers;
	}
	
	private static List<ISourceContainer> getCustomFunctionSourceContainers(StudioProjectConfiguration pcfg) {
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		Iterator<BuildPathEntry> iter = pcfg.getEntriesByType(LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY).iterator();
		for(Iterator<BuildPathEntry> it = iter; it.hasNext();) {
			BuildPathEntry entry = it.next();
			String path = entry.getPath(entry.isVar());
			if(entry.getEntryType() == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY) {
				sourceContainers.add(new JarEntrySourceContainer(path,pcfg.getName()));
			}
		}		
		return sourceContainers;
	}
	private static List<ISourceContainer> getThirdPartySourceContainers(StudioProjectConfiguration pcfg) {
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		Iterator<BuildPathEntry> iter = pcfg.getEntriesByType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY).iterator();
		for(Iterator<BuildPathEntry> it = iter; it.hasNext();) {
			BuildPathEntry entry = it.next();
			String path = entry.getPath(entry.isVar());
			if(entry.getEntryType() == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
				sourceContainers.add(new JarEntrySourceContainer(path,pcfg.getName()));
			}
		}		
		return sourceContainers;
	}

	
	private static ISourceContainer getDefaultSourceContainer(IPath path) {
		ISourceContainer sourceContainer = null;
		if (path != null) {
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			if (resource != null) {
				IContainer container = null;
				if(resource instanceof IContainer) {
					container = (IContainer) resource;
				} else {
					container = resource.getParent();
				}
				if (container.getType() == IResource.PROJECT) {
					sourceContainer = new ProjectSourceContainer((IProject)container, false);
				} else if (container.getType() == IResource.FOLDER) {
					sourceContainer = new FolderSourceContainer(container, false);
				}
			}
		}
		if (sourceContainer == null) { 
			sourceContainer = new WorkspaceSourceContainer();
		}
		return sourceContainer;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof StudioProjectSourceContainer) {
			return getStudioProject().equals(((StudioProjectSourceContainer)obj).getStudioProject());
		}
		return super.equals(obj);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getStudioProject().hashCode();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.sourcelookup.ISourceContainer#findSourceElements(java.lang.String)
	 */
	public Object[] findSourceElements(String name) throws CoreException {
		// force container initialization
		getSourceContainers();
		
		if (isStudioCodeFileName(name)) {
			Object[] objects = findSourceElements(name, fProjectSourceContainer);
			if(objects.length < 1) {
				objects = findSourceElements(name, fProjectLibraryContainers);
				if(objects.length > 0) {
					return objects;
				}
			} else {
				return objects;
			}
		} 
		// look elsewhere if not a studio code 
		return findSourceElements(name, fOtherContainers);
	}
	
	@SuppressWarnings("unused")
	private boolean isProjectResource(IResource resource) {
		return getStudioProject().findMember(resource.getFullPath()) != null;
	}
	
	public void dispose() {
		fProjectSourceContainer = null;
		fProjectLibraryContainers = null;
		fCoreLibraryContainers = null;
		fCustomFunctionContainers = null;
		fThirdPartyContainers = null;
		
		super.dispose();
	}
	
	private boolean isStudioCodeFileName(String name) {
		for (int i = 0; i < fgJavaExtensions.length; i++) {
			String ext = fgJavaExtensions[i];
			if (name.endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

}
