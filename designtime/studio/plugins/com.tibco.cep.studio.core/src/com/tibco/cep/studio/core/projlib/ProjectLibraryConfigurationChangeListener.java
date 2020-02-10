package com.tibco.cep.studio.core.projlib;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.update.IBuildPathEntryDelta;
import com.tibco.cep.studio.common.configuration.update.ProjectLibraryEntryDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;

public class ProjectLibraryConfigurationChangeListener implements IStudioProjectConfigurationChangeListener {





	public ProjectLibraryConfigurationChangeListener() {
	}
	
	
	

	@Override
	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
		try {
			/**
			 * Moved to {@link IndexResourceChangeListener} configurationChanged()
			 * Linked resources should be linked before any indexing is done.
			 */
			UpdateProjectLibraryLinkedResourcesJob updateJob = new UpdateProjectLibraryLinkedResourcesJob(event.getDelta());
			updateJob.schedule();
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}

	}
	
	public static class UpdateProjectLibraryLinkedResourcesJob extends WorkspaceJob {
		public static final String PROJLIB_UPDATE_LINK_JOB = "PROJLIB_UPDATE_LINK_JOB"; //$NON-NLS-1$
		private StudioProjectConfigurationDelta delta;

		public UpdateProjectLibraryLinkedResourcesJob(StudioProjectConfigurationDelta configDelta) {
			super(PROJLIB_UPDATE_LINK_JOB);
			this.delta=configDelta;
		}
		
		@Override
		public boolean belongsTo(Object family) {
			return IndexResourceChangeListener.UPDATE_INDEX_FAMILY.equals(family);
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			ProjectLibraryLinkResourceVisitor plibLinkResourceVisitor = new ProjectLibraryLinkResourceVisitor();
			StudioProjectConfiguration projConfig = delta.getAffectedChild();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projConfig.getName());
			if (!project.exists()) {
				return Status.CANCEL_STATUS;
			}
			project.accept(plibLinkResourceVisitor);
			final List<IResource> linkedResources = plibLinkResourceVisitor.getLinkedResources();
			
			try{
				EList<ProjectLibraryEntry> changedProjectLibEntries = delta.getProjectLibEntries();
				for (ProjectLibraryEntry chPLEntry : changedProjectLibEntries) {
					ProjectLibraryEntryDelta deltaEntry = (ProjectLibraryEntryDelta) chPLEntry;
					int type = deltaEntry.getType();
					Path deltaPath = new Path(deltaEntry.getAffectedChild().getPath(deltaEntry.getAffectedChild().isVar()));
					if (type == IBuildPathEntryDelta.ADDED) {
						addLinkedResources(deltaPath);
					} else if (type == IBuildPathEntryDelta.REMOVED) {
						removeLinkedResources(deltaPath, linkedResources);
					}
				}
			} catch (Exception e) {
				return new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"URI Syntax error.",e);
			}
			
			return Status.OK_STATUS;
		}

		/**
		 * Adds the linked project library resource
		 * @param added
		 * @throws CoreException
		 * @throws URISyntaxException
		 */
		private void addLinkedResources(IPath resourcePath) throws CoreException, URISyntaxException {
			String projName = delta.getAffectedChild().getName();
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			final IPath entryPath = resourcePath;
			final File entryFile = entryPath.toFile();
			if (entryFile.exists() && entryFile.isFile()) {
				IFolder pFolder = project.getFolder(entryFile.getName());
				String path = String.format("/%s:%s", entryFile.toURI().getScheme(),entryFile.toURI().getSchemeSpecificPart());
				path = path.replaceAll(" ", "%20");
				URI projlibURI = new URI(ProjectLibraryFileSystem.PROJLIB_SCHEME, null,path, null, null);
				pFolder.createLink(projlibURI, IResource.ALLOW_MISSING_LOCAL | IResource.REPLACE, new NullProgressMonitor());
				pFolder.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
		}
		
		/**
		 * Removes the linked resource matching the project library path
		 * @param resourcePath
		 * @param linkedResources
		 * @throws CoreException
		 */
		private void removeLinkedResources(IPath resourcePath, List<IResource> linkedResources) throws CoreException {
			for (IResource resource : linkedResources) {
				String resLocStr = ResourceHelper.getFileLocation(resource);
				Path resLocation = new Path(resLocStr);
				if(resLocation.equals(resourcePath)){
					// need to remove the referenced index here, as the linked location is lost after deleting the resource
					DesignerProject index = IndexUtils.getIndex(resource.getProject());
			    	EList<DesignerProject> referencedProjects = index.getReferencedProjects();
					for (int i = 0; i < referencedProjects.size(); i++) {
						DesignerProject proj = referencedProjects.get(i);
						if (proj.getRootPath().equals(resLocStr)) {
							referencedProjects.remove(i);
							break;
						}
					}

					resource.delete(IResource.FORCE, new NullProgressMonitor());
					resource.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
				}
			}
		}
	}

	
	public static class ProjectLibraryChangeDelta {
		private IProject project;
		private List<IPath> addedLibraries = new ArrayList<IPath>();
		private List<IPath> removedLibraries = new ArrayList<IPath>();
		private StudioProjectConfiguration configuration;
		
		private ProjectLibraryChangeDelta(StudioProjectConfigurationChangeEvent event) {
			this.project =  ResourcesPlugin.getWorkspace().getRoot().getProject(event.getDelta().getName());
			this.configuration = event.getDelta().getAffectedChild();
		}
		
		public IProject getProject() {
			return project;
		}

		public static ProjectLibraryChangeDelta getDelta(StudioProjectConfigurationChangeEvent event){
		//	no longer needed - use config delta
			ProjectLibraryChangeDelta delta = new ProjectLibraryChangeDelta(event);
			delta.compute();
			return delta;
		}
		
		/**
		 * computes the added and removed project library paths by comparing project configuration {@link StudioProjectConfiguration} 
		 * and index {@link DesignerProject}
		 */
		private void compute() {
			DesignerProject parentIndex = StudioCorePlugin.getDesignerModelManager().getIndex(project);
			EList<DesignerProject> referencedProjects = parentIndex.getReferencedProjects();
			
			// first, find added config entries
			for (ProjectLibraryEntry bpe: configuration.getProjectLibEntries()) {
				DesignerProject proj = findProject(bpe, referencedProjects);
				if (proj == null) {
					addedLibraries.add(new Path(bpe.getPath(bpe.isVar())));
				}
			}
			// now find removed ones
			for (int i=0; i<referencedProjects.size(); i++) {
				DesignerProject proj = referencedProjects.get(i);
				ProjectLibraryEntry bpe = findBuildPathEntry(proj, configuration.getProjectLibEntries());
				if (bpe == null) {
					removedLibraries.add(new Path(proj.getRootPath()));
				}
			}
			
		}
		
		/**
		 * Finds the Project Library entry from the list of entries from {@link StudioProjectConfiguration} which matched the given 
		 * referenced project from {@link DesignerProject}
		 * @param proj The referenced project
		 * @param list The list of project library entries
		 * @return
		 */
		private static ProjectLibraryEntry findBuildPathEntry(DesignerProject proj,
				EList<ProjectLibraryEntry> list) {
			for (ProjectLibraryEntry bpe : list) {	
				String path = bpe.getPath(bpe.isVar());
				if (path.equals(proj.getName())) {
					return bpe;
				}
			}
			return null;
		}
		
		/**
		 * Finds if a project lib entry exists in the list of referenced projects
		 * @param entry The project lib entry {@link ProjectLibraryEntry}
		 * @param referencedProjects The list of referenced project entries from the index {@link DesignerProject}
		 * @return The index entry of the project library project
		 */
		private static DesignerProject findProject(BuildPathEntry entry,
				EList<DesignerProject> referencedProjects) {
			for (DesignerProject designerProject : referencedProjects) {
				if (designerProject.getName().equals(entry.getPath())) {
					return designerProject;
				}
			}
			return null;
		}

		/**
		 * @return list of added project library paths {@link IPath}
		 */
		public List<IPath> getAddedLibraries() {
			return addedLibraries;
		}
		
		/**
		 * @return list of removed project library paths {@link IPath}
		 */
		public List<IPath> getRemovedLibraries() {
			return removedLibraries;
		}
		
	}

	/**
	 * @author Pranab Dhar visits all resources in the project to find linked
	 *         resources
	 * 
	 */
	public static class ProjectLibraryLinkResourceVisitor implements IResourceVisitor {
		private List<IResource> linkedResources;
		private List<IFile> linkedFiles;

		public ProjectLibraryLinkResourceVisitor() {
			linkedResources = new ArrayList<IResource>();
			linkedFiles = new ArrayList<IFile>();
		}

		public List<IResource> getLinkedResources() {
			return new ArrayList<IResource>(linkedResources);
		}
		
		public List<IFile> getLinkedFiles() {
			return new ArrayList<IFile>(linkedFiles);
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (resource instanceof IProject) {
				return true;
			}

			if (resource instanceof IFolder) {
				IFolder folder = (IFolder) resource;
				if (IndexUtils.isProjectLibType(folder) 
						&& CommonIndexUtils.isProjectLibraryType(folder.getFileExtension())) {
					linkedResources.add(resource);
				}
				return true;
			}
			
			if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				if (IndexUtils.isProjectLibType(file)) {
					linkedFiles.add(file);
				}
				return true;
			}
			
			return false;
		}

	}

	private void expandZip(IFile file) {
		try {
			URI zipURI = new URI(ProjectLibraryFileSystem.PROJLIB_SCHEME, null, "/", file.getLocationURI().toString(), null);
			IFolder link = file.getParent().getFolder(new Path(file.getName()));
			link.createLink(zipURI, IResource.REPLACE, null);
		} catch (Exception e) {
			StudioCorePlugin.logErrorMessage("Error opening zip file");
		}
	}

	private void collapseZip(IFolder folder) {
		try {
			URI zipURI = new URI(folder.getLocationURI().getQuery());
			// check if the zip file is physically stored below the folder in
			// the workspace
			IFileStore parentStore = EFS.getStore(folder.getParent().getLocationURI());
			URI childURI = parentStore.getChild(folder.getName()).toURI();
			if (URIUtil.equals(zipURI, childURI)) {
				// the zip file is in the workspace so just delete the link
				// and refresh the parent to create the resource
				folder.delete(IResource.NONE, null);
				folder.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
			} else {
				// otherwise the zip file must be a linked resource
				IFile file = folder.getParent().getFile(new Path(folder.getName()));
				file.createLink(zipURI, IResource.REPLACE, null);
			}
		} catch (Exception e) {
			StudioCorePlugin.logErrorMessage("Error opening zip file");
		}
	}

}
