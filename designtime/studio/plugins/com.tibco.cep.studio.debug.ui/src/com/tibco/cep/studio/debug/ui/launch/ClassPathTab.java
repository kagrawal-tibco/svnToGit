package com.tibco.cep.studio.debug.ui.launch;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.parser.semantic.JavaCustomFunctionsFactory;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.ui.property.page.ClassPathVarUtils;
import com.tibco.cep.studio.ui.property.page.NativeLibraryDialog;
import com.tibco.cep.studio.ui.providers.LibraryContentProvider;
import com.tibco.cep.studio.ui.providers.LibraryLabelProvider;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.xml.datamodel.XiNode;

public class ClassPathTab extends AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {
	private final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.classpathTab";
	private final static String TAB_NAME = "ClassPath";
	
	private WidgetListener wListener;
	private TreeViewer fclasspathTreeViewer;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	
	private Button addVarButton;
	
	
	private boolean fChanged = false;
	private String projectName;
	
	private ILaunchConfigurationWorkingCopy workingCopy;

	/**
	 * 
	 */
	public ClassPathTab() {
		super();
		wListener = new WidgetListener();	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.WRAP);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		control.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;
		
		
		Label desriptionLabel = new Label(control, SWT.WRAP);
		desriptionLabel.setText(Messages.getString("project.buildpath.tab.javalib.desc"));
	    new Label(control, SWT.WRAP);
		
		ITreeContentProvider contp = new LibraryContentProvider(new LIBRARY_PATH_TYPE[] {
			LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY,
			LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY,
			LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY
});
		IBaseLabelProvider labelProvider = new LibraryLabelProvider();
		fclasspathTreeViewer = createTreeViewer(control, contp, labelProvider);
		
		if (this.projectName != null) {
			fclasspathTreeViewer.setInput(getDesignerProject(this.projectName));
		}
		
		Composite buttonComposite = new Composite(control,SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;
		
		this.addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(wListener);
		addButton.setEnabled(!fclasspathTreeViewer.getSelection().isEmpty());

		this.addVarButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		addVarButton.setText(Messages.getString("project.buildpath.tab.addVariablebutton"));
		addVarButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addVarButton.addSelectionListener(wListener);
		addVarButton.setEnabled(!fclasspathTreeViewer.getSelection().isEmpty());
		
		this.removeButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		removeButton.setText(Messages.getString("project.buildpath.tab.removebutton"));
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		removeButton.addSelectionListener(wListener);
		removeButton.setEnabled(!fclasspathTreeViewer.getSelection().isEmpty());
		
		this.upButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		upButton.setText(Messages.getString("project.buildpath.tab.upbutton"));
		upButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		upButton.addSelectionListener(wListener);
		upButton.setEnabled(!fclasspathTreeViewer.getSelection().isEmpty());
		
		this.downButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		downButton.setText(Messages.getString("project.buildpath.tab.downbutton"));
		downButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		downButton.addSelectionListener(wListener);
		downButton.setEnabled(!fclasspathTreeViewer.getSelection().isEmpty());
		
		fclasspathTreeViewer.addSelectionChangedListener(wListener);
		fclasspathTreeViewer.addDoubleClickListener(wListener);
		
		setControl(control);
		
	}
	
	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		try {
			final String name = workingCopy.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(name != null) {
				this.projectName = name;
				DesignerProject project = getDesignerProject(this.projectName);
				fclasspathTreeViewer.setInput(project);
				this.workingCopy = workingCopy;
			}
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}
		
	}

	private DesignerProject getDesignerProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
			return null;
		}
		return StudioProjectCache.getInstance().getIndex(name);
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
    
    protected void addLibraryLocation(String jarPath, boolean isVar) {
		if(this.projectName == null) {
			return;
		}
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(this.projectName);
		if(isCustomFunctionsLibrary(jarPath)) {
//			boolean addtoCustomFunctions = MessageDialog.openQuestion(getShell()
//					, Messages.getString("project.buildpath.tab.javalib.title")
//					, Messages.getString("project.buildpath.tab.javalib.customfunc", jarPath));
			if(/*addtoCustomFunctions  && */!buildPathEntryExists(this.projectName, jarPath, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY)) {
				CustomFunctionLibEntry entry = ConfigurationFactory.eINSTANCE.createCustomFunctionLibEntry();
				entry.setEntryType(LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
				entry.setVar(isVar);
				entry.setPath(jarPath);
				if (isVar) {
					jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
					entry.setResolvedPath(jarPath);
				}
				File file = new File(jarPath);
				entry.setTimestamp(file.lastModified());
				configuration.getCustomFunctionLibEntries().add(entry);
			} else {
				if(!buildPathEntryExists(this.projectName, jarPath, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY)) {
					ThirdPartyLibEntry entry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
					entry.setEntryType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					entry.setVar(isVar);
					entry.setPath(jarPath);
					if (isVar) {
						jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
						entry.setResolvedPath(jarPath);
					}
					File file = new File(jarPath);
					entry.setTimestamp(file.lastModified());
					configuration.getThirdpartyLibEntries().add(entry);
				}
			}
		} else {		
			if(!buildPathEntryExists(this.projectName, jarPath, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY)){
				ThirdPartyLibEntry entry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
				entry.setEntryType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
				entry.setVar(isVar);
				entry.setPath(jarPath);
				if (isVar) {
					jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toOSString();
					entry.setResolvedPath(jarPath);
				}
				File file = new File(jarPath);
				entry.setTimestamp(file.lastModified());
				configuration.getThirdpartyLibEntries().add(entry);
			}
		}
		refreshTreeViewer(fclasspathTreeViewer);
	}
    
  //Wait for UpdateReferencedIndexJob to finish
	private static void refreshTreeViewer(TreeViewer tv){	
		if (tv == null) return;
		waitForUpdate();
		tv.refresh();				
	}
	
	public static void waitForUpdate() {
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(IndexResourceChangeListener.UPDATE_INDEX_FAMILY);        
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof UpdateReferencedIndexJob) {
            	UpdateReferencedIndexJob updateJob = (UpdateReferencedIndexJob) jobsOnProject[i];
            	try {
					updateJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
  
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return TAB_NAME;
	}
	
	@Override
	public String getId() {
		return TAB_ID;
	}
	
	@Override
	public Image getImage() {
		return (StudioDebugUIPlugin.getImage("icons/cp_order_obj.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			final String name = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(name != null && !"".equals(name)) {
				this.projectName = name;
				DesignerProject project = getDesignerProject(this.projectName);
				fclasspathTreeViewer.setInput(project);
			}
		} catch (CoreException e) {
			StudioDebugUIPlugin.log(e);
		}
		
	}
	@Override
	public boolean canSave() {
		return validateLocal();
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return validateLocal();
	}

	private boolean validateLocal() {
		if (!fChanged) {
			return true;
		}
		final String name = this.projectName;
		StudioProjectConfiguration spconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		try {
			checkConfiguration(spconfig);
			StudioProjectConfigurationManager.getInstance().saveConfiguration(name, spconfig);
			
		} catch (NoClassDefFoundError e) {
			StudioDebugUIPlugin.errorDialog(getShell()
					, Messages.getString("project.buildpath.tab.javalib.title")
					, Messages.getString("project.buildpath.tab.javalib.classnotfound",e.getMessage()),e);
			setErrorMessage(Messages.getString("project.buildpath.tab.javalib.classnotfound",e.getMessage()));
			return false;
		} catch (Throwable e) {
			StudioDebugUIPlugin.errorDialog(getShell()
					, Messages.getString("project.buildpath.tab.javalib.title")
					, Messages.getString("project.buildpath.tab.javalib.save.error"),e);
			setErrorMessage(Messages.getString("project.buildpath.tab.javalib.save.error"));
			return false;
		}
		
		//No need of showing message dialog for any change in Launch configuration
//		if (MessageDialog.openQuestion(getShell(), Messages.getString("project.buildpath.project.rebuild.title"), 
//				Messages.getString("project.buildpath.project.rebuild"))) 
//		{
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.projectName);
			Job buildJob = new Job("Build project") {
				
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
					} catch (CoreException e) {
						StudioDebugUIPlugin.log(e);
					}
					
					return Status.OK_STATUS;
				}
			};
			buildJob.schedule();
//		}
		setErrorMessage(null);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String name;
		try {
			name = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(name != null && !name.isEmpty()) {
				StudioProjectConfiguration spconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
				if (spconfig ==  null) {
					return;
				}
				StudioProjectConfigurationManager.getInstance().saveConfiguration(name, spconfig);
				if (fChanged /*&& MessageDialog.openQuestion(getShell(), Messages.getString("project.buildpath.project.rebuild.title"), 
						Messages.getString("project.buildpath.project.rebuild"))*/) {
					final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
					Job buildJob = new Job("Build project") {
						
						@Override
						protected IStatus run(final IProgressMonitor monitor) {
							try {
								project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
							} catch (CoreException e) {
								StudioDebugUIPlugin.log(e);
							}
							
							return Status.OK_STATUS;
						}
					};
					buildJob.schedule();
				}
				fChanged = false;
			}
		} catch (Exception e1) {
			StudioDebugUIPlugin.log(e1);
		}

		
		
	}
	
	private void checkConfiguration(StudioProjectConfiguration configuration) throws Throwable {
		EList<CustomFunctionLibEntry> entries = configuration.getCustomFunctionLibEntries();
		for (CustomFunctionLibEntry bpe : entries) {		
			
			String filePath = bpe.getPath();
			File file = new File(filePath);
				JarFile archiveFile = new JarFile(file);
				XiNode document = null;
				try {
					document = FunctionsCatalogManager.parseArchiveFile(archiveFile);
				} catch (Exception e) {
					throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted", e);
				}
				if (document == null) {
					throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted");
				}
				FunctionsCatalog catalog = new FunctionsCatalog();
	            JavaCustomFunctionsFactory.loadFunctionCategoriesFromDocument(catalog, document, archiveFile,configuration,null);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		try {
			this.projectName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}
	}
	
	protected boolean buildPathEntryExists(String projectName, String jarPath, LIBRARY_PATH_TYPE type) {
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		if (configuration ==  null) {
			return false;
		}
		EList<BuildPathEntry> entries = configuration.getEntriesByType(type);
		for (BuildPathEntry entry : entries) {
			String path = entry.getPath(entry.isVar());
			if (path.equals(jarPath)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCustomFunctionsLibrary(String jarPath) {
		File file = new File(jarPath);
		if(file.exists()){
			try {
				JarFile jFile = new JarFile(file);
				Enumeration<JarEntry> en = jFile.entries();
				while(en.hasMoreElements()) {
					JarEntry jEntry = en.nextElement();
					if(jEntry.getName().endsWith("functions.catalog")){
						return true;
					}
				}
			} catch (IOException e) {						
				return false;
			}
		}
		return false;
	}
	
	protected void removeBuildPathEntry(Object element) {
		if (!(element instanceof BuildPathEntry)) {
			return;
		}
		BuildPathEntry removedEntry = (BuildPathEntry) element;
		LIBRARY_PATH_TYPE type = removedEntry.getEntryType();
		DesignerProject proj = getDesignerProject(this.projectName);
		if (proj == null) {
			return;
		}
		String name = proj.getName();
		
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		if (configuration ==  null) {
			return;
		}
		if (removedEntry != null) {
			configuration.removeEntriesByType(removedEntry, type);
			fclasspathTreeViewer.refresh();
		}
		fChanged = true;
	}

	private class WidgetListener extends SelectionAdapter implements ModifyListener, SelectionListener,ISelectionChangedListener,IDoubleClickListener {
		@Override
		public void doubleClick(DoubleClickEvent event) {
			ISelection selection = event.getSelection();
			if(!selection.isEmpty()) {
				if(selection instanceof StructuredSelection) {
					StructuredSelection ss = (StructuredSelection) selection;
					if(!ss.isEmpty()) {
						Object element = ss.getFirstElement();
						if(element != null && element instanceof NativeLibraryPath ) {
							NativeLibraryPath nativePath = (NativeLibraryPath) element;
							IPath oldValue = null;
							if(nativePath.getPath() != null && !nativePath.getPath().isEmpty()){
								oldValue = new Path(nativePath.getPath());
							}
							NativeLibraryDialog dialog = new NativeLibraryDialog(getShell(),nativePath.getPath(),(JavaLibEntry)nativePath.eContainer());
							if(dialog.open() == Window.OK){
								IPath newValue = new Path(dialog.getNativeLibraryPath());
								if(!newValue.equals(oldValue)) {
									nativePath.setPath(newValue.toPortableString());
									refreshTreeViewer(fclasspathTreeViewer);
									fChanged = true;										
								}
							}
						}
					}
				}
				
			}
			
		}
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if(ClassPathTab.this.projectName == null || ClassPathTab.this.projectName.isEmpty() ) {
				return;
			}
			final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(ClassPathTab.this.projectName);
			if (configuration ==  null) {
				return;
			}
			ISelection selection = event.getSelection();
			if(selection instanceof StructuredSelection) {
				
				int size = ((StructuredSelection) selection).size();
				if(size > 0) {
					if(size == 1) {
						StructuredSelection s = (StructuredSelection) selection;
						Object element = s.getFirstElement();
						if(element instanceof ThirdPartyLibEntry || element instanceof CustomFunctionLibEntry) {
							int index = -1;
							int numEntries = -1;
							if(element instanceof ThirdPartyLibEntry) {
								numEntries = configuration.getThirdpartyLibEntries().size();
								index = configuration.getThirdpartyLibEntries().indexOf(element);
							} else if(element instanceof CustomFunctionLibEntry) {
								numEntries = configuration.getCustomFunctionLibEntries().size();
								index = configuration.getCustomFunctionLibEntries().indexOf(element);								
							}
							if(index <= (numEntries -1) && index >= 0){
								if(index == (numEntries -1)) {
									upButton.setEnabled(true);
									downButton.setEnabled(false);
								} else if(index == 0) {
									downButton.setEnabled(true);
									upButton.setEnabled(false);
								} else {
									downButton.setEnabled(true);
									upButton.setEnabled(true);
								}
							}
							if(index == 0 && index == (numEntries -1)) {
								upButton.setEnabled(true);
								downButton.setEnabled(false);
							}
							removeButton.setEnabled(true);
						} else if(element instanceof LIBRARY_PATH_TYPE ) {
							LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) element;
							if(type == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY || 
									type == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
								addButton.setEnabled(true);
								addVarButton.setEnabled(true);
								removeButton.setEnabled(false);
							} else {
								addButton.setEnabled(false);
								addVarButton.setEnabled(false);
								removeButton.setEnabled(false);
							}
							
						}else {
							upButton.setEnabled(false);
							downButton.setEnabled(false);
							removeButton.setEnabled(false);
						}
					} else {
						upButton.setEnabled(false);
						downButton.setEnabled(false);
						addButton.setEnabled(false);
						addVarButton.setEnabled(false);
						removeButton.setEnabled(false);
					}
				}
			}
			
		}
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == addButton) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterExtensions(new String[]{"*.jar"});
				fd.open();
				String[] jarPath = fd.getFileNames();
				for (String string : jarPath) {
					File f = new File(fd.getFilterPath()+File.separator+string);
					if(!f.isDirectory()) {
						addLibraryLocation(fd.getFilterPath()+File.separator+string, false);
					} else {
						File[] listFiles = f.listFiles(new FilenameFilter(){
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".jar");
							}
						});
						for (File file : listFiles) {
							addLibraryLocation(file.getPath(), false);
						}
					}
				}
				fChanged = true;
				refreshStatus();
			}else if (source == addVarButton) {
				openVariableSelectionDialog();
			}else if (source == removeButton) {
				ISelection selection = fclasspathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removeBuildPathEntry(element);
						fChanged = true;
						refreshStatus();
					}
				}
			}else if (source == upButton) {
				DesignerProject proj = getDesignerProject(ClassPathTab.this.projectName);
				if (proj == null) {
					return;
				}
				String name = proj.getName();
				StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
				ISelection selection = fclasspathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					if(((StructuredSelection) selection).size() < 1) 
						return;
					if(((StructuredSelection) selection).size() > 1) {
						upButton.setEnabled(false);						
					} else {
						Object element = ((StructuredSelection) selection).getFirstElement();
						if (element instanceof ThirdPartyLibEntry) {
							if(element != null) {
								final int oldPosition = configuration.getThirdpartyLibEntries().indexOf(element);
								if(oldPosition > 0) {
									configuration.getThirdpartyLibEntries().move(oldPosition-1, oldPosition);
									fclasspathTreeViewer.refresh();
									fChanged = true;
									refreshStatus();
								}
							}
						} else if (element instanceof CustomFunctionLibEntry) {
							if(element != null) {
								final int oldPosition = configuration.getCustomFunctionLibEntries().indexOf(element);
								if(oldPosition > 0) {
									configuration.getCustomFunctionLibEntries().move(oldPosition-1, oldPosition);
									fclasspathTreeViewer.refresh();
									fChanged = true;
									refreshStatus();
								}
							}
						}

					}
				}
			} else if (source == downButton) {
				DesignerProject proj = getDesignerProject(ClassPathTab.this.projectName);
				if (proj == null) {
					return;
				}
				String name = proj.getName();
				StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
				ISelection selection = fclasspathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					if(((StructuredSelection) selection).size() < 1) 
						return;
					if(((StructuredSelection) selection).size() > 1) {
						upButton.setEnabled(false);						
					} else {
						Object element = ((StructuredSelection) selection).getFirstElement();
						if (element instanceof ThirdPartyLibEntry) {
							if(element != null) {
								final int oldPosition = configuration.getThirdpartyLibEntries().indexOf(element);
								final int size = configuration.getThirdpartyLibEntries().size();
								if(oldPosition < (size -1)) {
									configuration.getThirdpartyLibEntries().move(oldPosition+1,oldPosition);
									fclasspathTreeViewer.refresh();
									fChanged = true;
									refreshStatus();
								}
							}
						} else if (element instanceof CustomFunctionLibEntry) {
							if(element != null) {
								final int oldPosition = configuration.getCustomFunctionLibEntries().indexOf(element);
								final int size = configuration.getCustomFunctionLibEntries().size();
								if(oldPosition < (size -1)) {
									configuration.getCustomFunctionLibEntries().move(oldPosition+1,oldPosition);
									fclasspathTreeViewer.refresh();
									fChanged = true;
									refreshStatus();
								}
							}
						}

					}
				}
			} 
		}
	}
	
	/**
	 * Refreshing working copy with dummy attribute
	 */
	private void refreshStatus() {
		workingCopy.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CLASSPATH_CHANGE, true);
		updateLaunchConfigurationDialog();
	}
	
	/**
	 * @param existing
	 * @param edit
	 */
	private void openVariableSelectionDialog() {
		IPath[] existingPathsArray= ClassPathVarUtils.getExistingBuildPaths(this.projectName);
		IPath[] paths= ClasspathVariableUiUtils.chooseVariableEntries(getShell(), existingPathsArray, false);
		if (paths != null) {
			for (IPath path : paths) {
				addLibraryLocation(path.toPortableString(), true);
			}
			fChanged = true;
			refreshStatus();
		}
	}
}