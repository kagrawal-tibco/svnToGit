package com.tibco.cep.studio.ui.update;

import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.refreshGVView;
import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.refreshView;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.closeJarEntryFileEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.DecisionTableRefactoringParticipant;
import com.tibco.cep.studio.ui.editors.DiagramEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioResourceChangeListener  implements IResourceChangeListener {

	private List<IResource> removedresources;
	private List<IResource> changedresources;
	private List<IResource> addedresources;
	private IWorkbenchPage page;
	private List<IResource> projectsClosed;
	private IResource project;
	private Set<IFile> unSyncfileSet = new HashSet<IFile>();
	
	/**
	 * @param page
	 */
	public StudioResourceChangeListener(IWorkbenchPage page) {
		super();
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		removedresources = new ArrayList<IResource>();
		changedresources = new ArrayList<IResource>();
		projectsClosed =   new ArrayList<IResource>();
		addedresources = new ArrayList<IResource>();
		
		//Removing Editors from Project Library
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE){
			//Removing Editors from Project Library
			closeProjectJarEntryEditors(event.getResource());
			if (event.getResource() instanceof IProject) {
				StudioUIUtils.closeProjectEditors(event.getResource().getName());
			}
		}
		
		if (event.getType() == IResourceChangeEvent.PRE_DELETE){
			//Removing Editors from Project Library
			closeProjectJarEntryEditors(event.getResource());
		}
		
		if (event.getType() == IResourceChangeEvent.POST_CHANGE){
			IResource resource = delta.getResource();
			if (resource instanceof IWorkspaceRoot) {
				IResourceDelta[] affectedChildren = delta.getAffectedChildren();
				for (IResourceDelta resourceDelta : affectedChildren) {
					processDelta(resourceDelta);
				}
			}
		}
		
		unSyncfileSet.clear();
		
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				try {
					if (!PlatformUI.isWorkbenchRunning() || PlatformUI.getWorkbench().isClosing()) {
						return;
					}
					if (page == null) {
						page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					}
					processFiles(page);
					for(IFile file: unSyncfileSet){
						if (!file.isSynchronized(0)){
							file.refreshLocal(0, null/*IFile.DEPTH_INFINITE, new NullProgressMonitor()*/);
						}
					}
					for (IResource resource : projectsClosed) {
						if (resource instanceof IProject) {
							IProject project = (IProject)resource;
							IPath path = project.getFullPath();
							String workspacePath = 
								ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
							String projectFullPath = workspacePath + path;
							File projectFile = new File(projectFullPath);
							if (projectFile.exists()) {
								String[] files = projectFile.list();
								if (files.length == 1 && files[0].endsWith(".ars")) {
									//Remove the project directory
									File arsFile = new File(projectFile, files[0]);
									boolean delete = arsFile.delete();
									if (delete) {
										projectFile.delete();
									}
								}
							}
							project.refreshLocal(0, null);
							if (page != null) {
								refreshView(page, resource);
							}
						}
					}
					if (page != null) {
						for (IResource resource:changedresources) {
							refreshView(page, resource);
						}
						//Added refresh GV view
						refreshGVView(page);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param page
	 */
	private void processFiles(final IWorkbenchPage page){
		try{
			for (IResource resource:removedresources) {
				if (resource instanceof IFile) {
					IFile file = (IFile)resource;
					Map<Object,Object> map = DecisionTableRefactoringParticipant.getDeletedDTCache();
					if (file.getFileExtension() != null
							&& file.getFileExtension().equals(
									CommonIndexUtils.RULE_FN_IMPL_EXTENSION)) {
						String dtPath = file.getProjectRelativePath()
								.removeFileExtension().toString();
						if (dtPath != null && !dtPath.startsWith("/")) {
							dtPath = "/" + dtPath;
						}
						for (Map.Entry<Object, Object> entry : map.entrySet()) {
							if (dtPath != null
									&& dtPath.equals((String) entry.getKey())) {
								IFile vrfFile = (IFile) entry.getValue();
								if (vrfFile.exists()) {
									refreshView(page, vrfFile);
									break;
								}
							}
						}
					}
					removeEditorReference((IFile)resource);
					DecisionTableRefactoringParticipant.getDeletedDTCache().clear();
				}
			}
			for(IResource resource:addedresources) {
				if (resource instanceof IFile) {
					IFile file = (IFile)resource;
					if (file.getFileExtension() != null && file.getFileExtension().equals(CommonIndexUtils.RULE_FN_IMPL_EXTENSION)){
						DecisionTableElement element = (DecisionTableElement)IndexUtils.getElement(resource);
						if (element == null) {
							continue;
						}
						Table table = (Table) element.getImplementation();
						String vrfImplements = table.getImplements().concat(".").concat(CommonIndexUtils.RULEFUNCTION_EXTENSION);
						IFile vrfFile = file.getProject().getFile(Path.fromOSString(vrfImplements));
						if (vrfFile.exists()) {
							refreshView(page, vrfFile);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param delta
	 */
	private void processDelta(IResourceDelta delta) {
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			/* (non-Javadoc)
			 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
			 */
			public boolean visit(IResourceDelta delta) {
				IResource resource = delta.getResource();
				if (delta.getKind() == IResourceDelta.CHANGED){
					if (resource.getType() == IResource.PROJECT){
						project = resource;
						if (!resource.isAccessible()) {
							StudioUIUtils.closeProjectEditors(resource.getName());
						}
					}
					if (resource instanceof IFile) {
						changedresources.add(resource);
					}
				}
				if (delta.getKind() == IResourceDelta.REMOVED){
					if (resource.getType() == IResource.PROJECT) {
						projectsClosed.add((IProject)resource);
						return true;
					}
					if (resource.getType() == IResource.FILE) {
						if ("project".equalsIgnoreCase(resource.getFileExtension())){
							if (project!=null)
								projectsClosed.add(project);
							return true;
						}
						removedresources.add(resource);
					}
				}
				if (delta.getKind() == IResourceDelta.ADDED){
					addedresources.add(resource);
				}
				return true;
			}
		};
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 */
	private void removeEditorReference(final IFile file){
		try{
			//Close File Editor
			for(IEditorReference reference:page.getEditorReferences()){
				try {
					if (reference.getEditorInput() instanceof IFileEditorInput){
						IFileEditorInput fileEditorInput = (IFileEditorInput)reference.getEditorInput();
						if (fileEditorInput.getFile().getFullPath().toPortableString().equals(file.getFullPath().toPortableString())){
							page.closeEditor(reference.getEditor(false), false);
							break;
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
			
			//Close Dependency/Sequence Diagram Editor 
			for(IEditorReference reference:page.getEditorReferences()){
				try {
					if (reference.getEditorInput() instanceof DiagramEditorInput) {
						DiagramEditorInput input = (DiagramEditorInput)reference.getEditorInput();
						if (input.getFile().getFullPath().toPortableString().equals(file.getFullPath().toPortableString())) {
							page.closeEditor(reference.getEditor(false), false);
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param resource
	 */
	private void closeProjectJarEntryEditors(IResource resource) {
		if (resource instanceof IProject) {
			DesignerProject pro = IndexUtils.getIndex(((IProject)resource).getName());
			if (pro == null) {
				return;
			}
			for (DesignerProject refPro : pro.getReferencedProjects()) {
				closeJarEntryFileEditor(refPro.getArchivePath());
			}
		}
	}
}