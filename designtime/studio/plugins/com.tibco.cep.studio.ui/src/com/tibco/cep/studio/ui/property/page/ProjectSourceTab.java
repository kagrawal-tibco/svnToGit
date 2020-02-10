package com.tibco.cep.studio.ui.property.page;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.BuildPathSourcePathContentProvider;
import com.tibco.cep.studio.ui.providers.BuildPathSourcePathLabelProvider;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.RunnableAdapter;
import com.tibco.cep.studio.ui.views.FolderSelectionDialog;
import com.tibco.cep.studio.ui.views.TypedItemFilter;
import com.tibco.cep.studio.ui.views.TypedItemSelectionValidator;

public class ProjectSourceTab extends TabItem {
	
	private TreeViewer fSourcePathTreeViewer;
	private IPath outputLocationPath;
	
	/**
	 * @param parent
	 * @param style
	 * @param index
	 */
	public ProjectSourceTab(TabFolder parent, int style, int index) {
		super(parent, style, index);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param parent
	 * @param style
	 */
	public ProjectSourceTab(TabFolder parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	protected DesignerProject getViewerInput() {
//		IAdaptable adaptable = getElement();
//		if (adaptable instanceof IProject){			
//			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex((IProject) adaptable);	
//			return index;
//		}
		return null;
	}
	
	
	private TabItem createSourcePathTab(TabFolder composite) throws Exception {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final DesignerProject input = getViewerInput();
		IProject project = workspaceRoot.getProject(input.getName());
		final StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(input.getName());
		String configPath = config.getEnterpriseArchiveConfiguration().getTempOutputPath();
		if(configPath == null || configPath.isEmpty()) {
//			outputLocationPath = project.getFullPath().append(EnterpriseArchiveEntry.BUILD_OUTPUT_FOLDER_NAME).makeRelative();
		} else {
			outputLocationPath =workspaceRoot.getFile(new Path(configPath)).getFullPath().makeRelative();
		}
		
		TabItem parent = new TabItem(composite, SWT.NONE /*SWT.CLOSE*/);
		parent.setText(Messages.getString("project.buildpath.tab.sourcepath.title"));
		parent.setImage(StudioUIPlugin.getDefault().getImage("icons/packagefolder_obj.png"));
		
		Composite parentControl = new Composite(composite, SWT.WRAP);
		parentControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout parentLayout = new GridLayout();
		parentControl.setLayout(parentLayout);
		
		Composite control = new Composite(parentControl, SWT.WRAP);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;
		
		Composite outPutFolderComposite = new Composite(parentControl,SWT.NULL);
		outPutFolderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout = new GridLayout();
		outPutFolderComposite.setLayout(layout);
		layout.numColumns = 2;
		
		Composite applyResetComposite = new Composite(parentControl,SWT.NULL);
		applyResetComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		layout = new GridLayout();
		applyResetComposite.setLayout(layout);
		layout.numColumns = 2;

		
		
		Label desriptionLabel = new Label(control, SWT.WRAP);
	    desriptionLabel.setText(Messages.getString("project.buildpath.tab.sourcepath.desc"));
	    desriptionLabel = new Label(control, SWT.WRAP);
	    
	    ITreeContentProvider contp = new BuildPathSourcePathContentProvider(input.getName());
	    ILabelProvider labelProvider = new BuildPathSourcePathLabelProvider();
	    
	    fSourcePathTreeViewer = createTreeViewer(control, contp, labelProvider);

	    if (input != null){	    	
	    	fSourcePathTreeViewer.setInput(input);
	    }
	    
	    Composite buttonComposite = new Composite(control,SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;
		
	    final Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
	    addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
	    addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
	    
	    Button removeButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
	    removeButton.setText(Messages.getString("project.buildpath.tab.removebutton"));
	    removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
	    
	    
	    Label defaultOutputFolderLabel = new Label(outPutFolderComposite,SWT.WRAP);
	    defaultOutputFolderLabel.setText(Messages.getString("project.buildpath.tab.sourcepath.defaultoutputpath"));
	    defaultOutputFolderLabel = new Label(outPutFolderComposite, SWT.WRAP);
	    
		final Text outputPath = new Text(outPutFolderComposite,SWT.BORDER);
		outputPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if(outputLocationPath != null) {
			outputPath.setText(outputLocationPath.toString());
		}
		outputPath.addModifyListener(new ModifyListener() {		
			@Override
			public void modifyText(ModifyEvent e) {
				try {
					buildPathChanged(outputPath.getText());
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});
		
		
		
		Button outputFolderButton = new Button(outPutFolderComposite, SWT.PUSH);
		outputFolderButton.setText(Messages.getString("project.buildpath.tab.sourcepath.browse"));
		outputFolderButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		outputFolderButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContainer container= chooseContainer();
				if (container != null) {
					String path = container.getFullPath().makeRelative().toString();
					outputPath.setText(path);
					config.getEnterpriseArchiveConfiguration().setTempOutputPath(path);
				}
		
			}		
		});
		
		Button applyButton = new Button(applyResetComposite,SWT.PUSH);
		applyButton.setText(Messages.getString("project.buildpath.tab.sourcepath.applyButton"));
		applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String projectName = getViewerInput().getName();
				final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		        final IProject project = workspace.getRoot().getProject(projectName);
		        String output = outputPath.getText();
		        if(output != null && !output.isEmpty()) {
		        	IPath newPath = workspace.getRoot().getFile(new Path(output)).getFullPath().makeRelative();
		        	final StudioProjectConfiguration cfg = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
		        	final String oldOutputPath = cfg.getEnterpriseArchiveConfiguration().getTempOutputPath();
		        	IPath oldPath = workspace.getRoot().getFile(new Path(oldOutputPath)).getFullPath().makeRelative();
		        	try {
		        		configureBuildPath(project,oldPath,newPath);
		        		StudioProjectConfigurationManager.getInstance().saveConfiguration(project.getName(), cfg);
		        		cfg.getEnterpriseArchiveConfiguration().setTempOutputPath(newPath.makeRelative().toString());
		        	} catch(Exception e1) {
		        		e1.printStackTrace();
		        	}
		        }
			
			}
		});
		
		Button resetButton = new Button(applyResetComposite,SWT.PUSH);
		resetButton.setText(Messages.getString("project.buildpath.tab.sourcepath.resetButton"));
		resetButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		resetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String projectName = getViewerInput().getName();
				final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		        final IProject project = workspace.getRoot().getProject(projectName);
		        final StudioProjectConfiguration cfg = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
		        String path = cfg.getEnterpriseArchiveConfiguration().getTempOutputPath();
		        if(path != null && !path.isEmpty()) {
		        	outputPath.setText(path);
		        	outputLocationPath = workspace.getRoot().getFile(new Path(outputPath.getText())).getFullPath().makeRelative();		        
		        }
			}
		});

		parent.setControl(parentControl);
		return parent;
	}
	private void buildPathChanged(String newPath) throws CoreException {
		final String projectName = getViewerInput().getName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject project = workspace.getRoot().getProject(projectName);
        StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
        String oldPath = config.getEnterpriseArchiveConfiguration().getTempOutputPath();
        outputLocationPath= null;
		
		String text= newPath;
		if ("".equals(text) ) {
			MessageDialog.openError(getParent().getShell(), Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.title"), Messages.getString("project.buildpath.tab.sourcepath.emptyfolder"));
			return;
		}
		IPath path= new Path(newPath).makeAbsolute();
		outputLocationPath= path;
		
		IResource res= workspace.getRoot().findMember(path);
		if (res != null) {
			// if exists, must be a folder or project
			if (res.getType() == IResource.FILE) {
				MessageDialog.openError(getParent().getShell(), Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.title"), Messages.getString("project.buildpath.tab.sourcepath.invalidfolder",path.toString())); 
				return;
			}
		}
		
		String pathStr= newPath;
		Path outputPath= (new Path(pathStr));
		pathStr= outputPath.lastSegment();
		if (pathStr.equals(".settings") && outputPath.segmentCount() == 2) { 
			MessageDialog.openWarning(getParent().getShell(), Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.title"), Messages.getString("project.buildpath.tab.sourcepath.dotfolder"));
			
		}
		
		if (pathStr.charAt(0) == '.' && pathStr.length() > 1) {
			MessageDialog.openWarning(getParent().getShell(), Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.title"), Messages.getString("project.buildpath.tab.sourcepath.dotfolder"));
		}		
        
	}
	

	private void configureBuildPath(final IProject project,final IPath oldOutputLocation,final IPath newOutputLocation) throws InvocationTargetException, InterruptedException {
		Shell shell= getParent().getShell();
		IWorkspaceRunnable runnable= new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor)	throws CoreException, OperationCanceledException {
				try {
					changeOutputLocation(project,oldOutputLocation,newOutputLocation,monitor);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}			
		};
		IRunnableWithProgress op= new RunnableAdapter(runnable); // lock on root
		PlatformUI.getWorkbench().getProgressService().run(true, true, op);
	}
	
	private void changeOutputLocation(IProject project ,IPath oldOutputLocation ,IPath newOutputLocation,IProgressMonitor monitor) throws Exception {
		IPath projPath= project.getFullPath();
		
		if(oldOutputLocation.toString().equals("")){
			oldOutputLocation= projPath.append(EnterpriseArchiveEntry.BUILD_OUTPUT_FOLDER_NAME);
		} 
		
		if (oldOutputLocation.equals(projPath) && !newOutputLocation.equals(projPath)) {
			if (hasGeneratedfiles(project)) {
				if (getRemoveOldBinariesQuery(false, projPath)) {
					removeOldClassfiles(project);
				}
			}
		} else if (!newOutputLocation.equals(oldOutputLocation)) {
			IFolder folder= ResourcesPlugin.getWorkspace().getRoot().getFolder(oldOutputLocation);
			if (folder.exists()) {
				if (folder.members().length == 0) {
					removeOldClassfiles(folder);
				} else {
					if (getRemoveOldBinariesQuery(true, oldOutputLocation)) {
						removeOldClassfiles(folder);
					}
				}
			}
		}
		
	}
	
	public static boolean hasGeneratedfiles(IResource resource) throws CoreException {
		if (resource.isDerived()) { 
			return true;
		}		
		if (resource instanceof IContainer) {
			IResource[] members= ((IContainer) resource).members();
			for (int i= 0; i < members.length; i++) {
				if (hasGeneratedfiles(members[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void removeOldClassfiles(IResource resource) throws CoreException {
		if (resource.isDerived()) {
			resource.delete(false, null);
		} else if (resource instanceof IContainer) {
			IResource[] members= ((IContainer) resource).members();
			for (int i= 0; i < members.length; i++) {
				removeOldClassfiles(members[i]);
			}
		}
	}
	
	public  boolean getRemoveOldBinariesQuery(final boolean removeFolder, final IPath oldOutputLocation) throws OperationCanceledException {
				final int[] res= new int[] { 1 };
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						Shell sh= getParent().getShell();
						String title= Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.title"); 
						String message;
						String pathLabel= oldOutputLocation.makeRelative().toString();
						if (removeFolder) {
							message= Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.query", pathLabel);
						} else {
							message= Messages.getString("project.buildpath.tab.sourcepath.outputpath.change.query", pathLabel);
						}
						MessageDialog dialog= new MessageDialog(sh, title, null, message, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
						res[0]= dialog.open();
					}
				});
				if (res[0] == 0) {
					return true;
				} else if (res[0] == 1) {
					return false;
				}
				throw new OperationCanceledException();
	};
	
	private IContainer chooseContainer() {
		final String projectName = getViewerInput().getName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject iProject = workspace.getRoot().getProject(projectName);
		Class[] acceptedClasses= new Class[] { IProject.class, IFolder.class };
		ISelectionStatusValidator validator= new TypedItemSelectionValidator(acceptedClasses, false);
		IProject[] allProjects= workspace.getRoot().getProjects();
		ArrayList rejectedElements= new ArrayList(allProjects.length);
		IProject currProject= iProject.getProject();
		for (int i= 0; i < allProjects.length; i++) {
			if (!allProjects[i].equals(currProject)) {
				rejectedElements.add(allProjects[i]);
			}
		}
		ViewerFilter filter= new TypedItemFilter(acceptedClasses, rejectedElements.toArray());

		ILabelProvider lp= new WorkbenchLabelProvider();
		ITreeContentProvider cp= new WorkbenchContentProvider();

		IResource initSelection= null;
		if (outputLocationPath != null) {
			initSelection= workspace.getRoot().findMember(outputLocationPath);
		}
		
		FolderSelectionDialog dialog= new FolderSelectionDialog(getParent().getShell(), lp, cp);
		dialog.setTitle(Messages.getString("project.buildpath.tab.sourcepath.folderdialog")); 
		dialog.setValidator(validator);
		dialog.setMessage(Messages.getString("project.buildpath.tab.sourcepath.foldermsg")); 
		dialog.addFilter(filter);
		dialog.setInput(workspace.getRoot());
		dialog.setInitialSelection(initSelection);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		
		if (dialog.open() == Window.OK) {
			return (IContainer)dialog.getFirstResult();
		}
		return null;
	}
	
	/**
     * Create tab's tree viewer.
     * @param parent
     * @param treeContentProvider
     * @param treeLabelProvider
     * @return
     */
    protected TreeViewer createTreeViewer(Composite parent, ITreeContentProvider treeContentProvider, IBaseLabelProvider treeLabelProvider) {
        Tree tree = new Tree(parent, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.verticalSpan = 2;
        tree.setLayoutData(data);
        tree.setFont(parent.getFont());

        TreeViewer treeViewer = new TreeViewer(tree);
        if(treeContentProvider !=null){
        	treeViewer.setContentProvider(treeContentProvider);
        }
        if(treeLabelProvider != null){
        	treeViewer.setLabelProvider(treeLabelProvider);
        }
        return treeViewer;
    }

}
