package com.tibco.cep.studio.ui.util;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.DependencyUtils;
import com.tibco.cep.studio.core.util.StudioCoreResourceUtils;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils.ProjectVersionComparator;
import com.tibco.cep.studio.core.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.MigrateStudioProjectsOperation;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioResourceUtils extends StudioCoreResourceUtils {

	private static boolean hideMigrateDialog;
	
	public static IProject getCurrentProject(IStructuredSelection selection) {
		Object object = selection.getFirstElement();
		if (object instanceof IResource) {
			return ((IResource) object).getProject();
		}
		if (object instanceof IAdaptable) {
			IFile resource = (IFile) ((IAdaptable) object).getAdapter(IFile.class);
			if (resource != null) {
				return resource.getProject();
			}
		}
		// why are we doing all of this?
		if (object instanceof IFolder) {
			IFolder folder = (IFolder) object;
			IPath path = folder.getFullPath();
			String pname = path.segments()[0];
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = workspace.getRoot().getProject(pname);
			if (project != null) {
				return project;
			}
		} else if (object instanceof IFile) {
			IFile folder = (IFile) object;
			IPath path = folder.getFullPath();
			String pname = path.segments()[0];
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = workspace.getRoot().getProject(pname);
			if (project != null) {
				return project;
			}
		} else if (object instanceof IProject){
			return (IProject) object;
		} else if (object instanceof AbstractNavigatorNode) {
			AbstractNavigatorNode node = (AbstractNavigatorNode) object;
			Entity e = node.getEntity();
			if( e != null && !(e instanceof StateEntity)){
				String projectName = e.getOwnerProjectName();
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IProject project = workspace.getRoot().getProject(projectName);
				if (project != null) {
					return project;
				}
			}
		}
		return null;
	}
	public static EObject getEntity(IEditorInput input) {

		if (!(input instanceof FileEditorInput)) {
			return null;
		}
		FileEditorInput fei = (FileEditorInput) input;
		
		return IndexUtils.loadEObject(fei.getURI());
	}
	
	public static String getExtensionFor(String type) {
		return StudioWorkbenchConstants.extensionMap.get(type);		
	}
	
	public static IProject getProjectForInput(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFileEditorInput finput = (IFileEditorInput) input;
			IFile file = finput.getFile();
			if (file != null) {
				return file.getProject();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param selection
	 * @return
	 * @throws Exception
	 */
	public static IProject getProjectForWizard(IStructuredSelection selection)throws Exception{
		IProject project = null;
		if(!(selection.getFirstElement() instanceof IProject)){
			if (!StudioResourceUtils.isStudioProject(selection)) {
				return null;
			}
			project = StudioResourceUtils.getCurrentProject(selection);
		}else{
		 	project = (IProject)selection.getFirstElement();
		}
		return project;
	}

	
	

	
	public static boolean isStudioProject(IStructuredSelection selection)
			throws Exception {
		IProject project = getCurrentProject(selection);
		if (project != null && project.isOpen() && CommonUtil.isStudioProject(project)) {
			return true;
		}
		return false;
	}
	
	public static boolean isVirtual(IFile file){
		try {
			if(file.getFileExtension()!=null){
				if(file.getFileExtension().equalsIgnoreCase("rulefunction")){
					RuleElement ruleElement = IndexUtils.getRuleElement( file.getProject().getName(), IndexUtils.getFullPath(file), ELEMENT_TYPES.RULE_FUNCTION);
					if(ruleElement != null && ruleElement.isVirtual()) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * @param selectedResource
	 * @param resourceToValidateList
	 */
	public static void listAllDependentResources(IResource selectedResource, List<IResource> resourceToValidateList) {
		List<IResource> dependentResources = DependencyUtils.getDependentResources(selectedResource);
		for (IResource resource : dependentResources) {
			if (!resourceToValidateList.contains(resource)) {
				resourceToValidateList.add(resource);
			}
		}
		if (!resourceToValidateList.contains(selectedResource)) {
			resourceToValidateList.add(selectedResource);
		}
	}
	
	
	
	
	
	/**
	 * Show designer perspective for the designer explorer.
	 */
	public static void switchStudioPerspective(){
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IPerspectiveDescriptor descriptor = page.getPerspective();
		if (descriptor != null) {
			String perspectiveId = descriptor.getId();
			if (!containsDesignerNavigator(perspectiveId)) {
				try {
					workbench.showPerspective("com.tibco.cep.studio.application.perspective", window);
				} catch (WorkbenchException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param container
	 * @param resourceToValidate
	 */
	public static void traverseResources(IContainer container, List<IResource> resourceToValidate){
		for (Object element : CommonUtil.getResources(container)) {
			if (element instanceof IFile) {
				IFile file = (IFile)element;
				resourceToValidate.add(file);
			}
			if (element instanceof IContainer) {
				traverseResources((IContainer)element, resourceToValidate);
			}
		}
	}

	static class RootNode {

		IProject[] fChildren;

		public RootNode(IProject[] children) {
			this.fChildren = children;
		}
		
	}
	
	private static final Object[] EMPTY_CHILDREN = new Object[0];
	static class ProjectContentProvider extends WorkbenchContentProvider {


		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof RootNode) {
				return ((RootNode) element).fChildren;
			}
			return EMPTY_CHILDREN;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof RootNode;
		}
		
	}
	
	/**
	 * Checks whether the Activity Pattern in enabled
	 * @param String categoryId
	 * @param @see {@link IWorkbench} workbench
	 * @return
	 */
	public static boolean isActivityEnabled(String activityId, IWorkbench workbench) {
		try {
			IWorkbenchActivitySupport workbenchActivitySupport =
					workbench.getActivitySupport();
			IActivityManager activityManager = workbenchActivitySupport
					.getActivityManager();
			return activityManager.getActivity(activityId).isEnabled();
		} catch (Exception e) {
			// workbench not yet activated; nothing enabled yet
		}
		return false;
	}
	
	
    /**
	 * Checks whether the Activity category in enabled
	 * @param String categoryId
	 * @param @see {@link IWorkbench} workbench
	 * @return
	 */
	public static boolean isCategoryEnabled(String categoryId, IWorkbench workbench) {
		try {
			IWorkbenchActivitySupport workbenchActivitySupport =
					workbench.getActivitySupport();
			IActivityManager activityManager = workbenchActivitySupport
					.getActivityManager();
			return WorkbenchActivityHelper.isEnabled(activityManager,
					categoryId);
		} catch (Exception e) {
			// workbench not yet activated; nothing enabled yet
		}
		return false;
	}
	
	static class ProjectLabelProvider extends WorkbenchLabelProvider {

		@Override
		protected String decorateText(String input, Object element) {
			if (element instanceof IProject) {
				StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(((IProject) element).getName());
				String projVersion = configuration.getVersion();
				if(projVersion==null){
					projVersion="4.0";
				}
				return input + " [currently version "+projVersion+"]";
			}
			return super.decorateText(input, element);
		}
		
	}
	
	static class DoNotAskAgainSelectionDialog extends CheckedTreeSelectionDialog {

		public DoNotAskAgainSelectionDialog(Shell parent,
				ILabelProvider labelProvider,
				ITreeContentProvider contentProvider) {
			super(parent, labelProvider, contentProvider);
		}
		
		@Override
		protected CheckboxTreeViewer createTreeViewer(Composite parent) {
			// TODO Auto-generated method stub
			CheckboxTreeViewer treeViewer=super.createTreeViewer(parent);
			treeViewer.addCheckStateListener(new ICheckStateListener() {
	            public void checkStateChanged(CheckStateChangedEvent event) {
	             	getOkButton().setEnabled(event.getChecked());
	            }
	        });
			return treeViewer;
		}

		@Override
		protected void updateButtonsEnableState(IStatus status) {
			// TODO Auto-generated method stub
			super.updateButtonsEnableState(status);
			getOkButton().setEnabled(!getOkButton().getEnabled());
		}

		@Override
		protected void updateStatus(IStatus status) {
			super.updateStatus(status);
			if (status.isOK()) {
				getOkButton().setEnabled(true);
			}
		}

		@Override
		protected Control createButtonBar(Composite parent) {
			Composite c = (Composite) super.createButtonBar(parent);
			final Button b = new Button(c, SWT.CHECK);
			b.setText("Do not show this dialog again");
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			b.setLayoutData(gridData);
			b.addSelectionListener(new SelectionListener() {
				

				@Override
				public void widgetSelected(SelectionEvent e) {
					hideMigrateDialog = b.getSelection();
					getOkButton().setEnabled(!getOkButton().getEnabled());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			return c;
		}
		
	}
	
	public static synchronized void updateProjects(final IProject[] oldProjects) {
		if (StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioUIPreferenceConstants.MIGRATE_OLD_PROJECTS_HIDE)) {
			// user selected 'do not show again'
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				CheckedTreeSelectionDialog dialog = new DoNotAskAgainSelectionDialog(Display.getDefault().getActiveShell(), new ProjectLabelProvider(), new ProjectContentProvider());
				dialog.setInput(new RootNode(oldProjects));
				dialog.setBlockOnOpen(true);
				dialog.setTitle("Project conversion");
				dialog.setMessage("Some of the projects in the workspace are from a previous version \nof TIBCO BusinessEvents.  " +
						"Select the projects you would like to \nautomatically convert to the current version ["+
						StudioProjectMigrationUtils.getCurrentVersion()+
						"].\nNOTE : The converted projects may no longer be compatible \nwith previous versions of TIBCO BusinessEvents.");
				int ret = dialog.open();
				if (hideMigrateDialog) {
					StudioUIPlugin.getDefault().getPreferenceStore().setValue(StudioUIPreferenceConstants.MIGRATE_OLD_PROJECTS_HIDE, true);
				}
				if (ret == CheckedTreeSelectionDialog.CANCEL) {
					return;
				}
				Object[] result = dialog.getResult();
				if (result == null || result.length == 0) {
					return;
				}
				MigrateStudioProjectsOperation op = new MigrateStudioProjectsOperation(result, true);
				ProgressMonitorDialog progDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
				try {
					progDialog.run(true, true, op);
				} catch (InvocationTargetException e) {
					StudioUIPlugin.log(e);
				} catch (InterruptedException e) {
					StudioUIPlugin.log(e);
				} catch (Exception e) {
					StudioUIPlugin.log(e);
				}
			}
		});
	}
	
	public static void migrateOldProjects() {
		IProject[] projects = CommonUtil.getAllStudioProjects();
		List<IProject> oldProjects = new ArrayList<IProject>();
		for (IProject project : projects) {
			try {
				StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
				String version = config.getVersion();
				if (version == null) { // if version ==null, its a 4.0 project.
					//Adding a hack to include 4.0 projects as well while  studio startup migration.
					version="4.0.0";
				//	StudioUIPlugin.logErrorMessage("Unable to determine version of project "+project.getName()+", unable to migrate project.");
				//	continue; // 
				}
				String currentVersion = StudioProjectMigrationUtils.getCurrentVersion();
				ProjectVersionComparator comparator = new StudioProjectMigrationUtils.ProjectVersionComparator();
				if (comparator.compare(version, currentVersion) < 0) {
					oldProjects.add(project);
				}
			} catch (Exception e) {
				StudioUIPlugin.log(e);
			}
		}
		if (oldProjects.size() > 0) {
			updateProjects((IProject[]) oldProjects.toArray(new IProject[oldProjects.size()]));
		}
	}


}