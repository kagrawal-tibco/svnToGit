package com.tibco.cep.studio.ui.property.page;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.common.configuration.update.CustomFunctionLibEntryDelta;
import com.tibco.cep.studio.common.configuration.update.IBuildPathEntryDelta;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.ProjectLibraryEntryDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.ThirdPartyLibEntryDelta;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.refactoring.MapperFunctionRefactoring;
import com.tibco.cep.studio.core.util.StudioProjectUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.LibraryContentProvider;
import com.tibco.cep.studio.ui.providers.LibraryLabelProvider;
import com.tibco.cep.studio.ui.refactoring.MigrateMapperFunctionsRefactoringWizard;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author rmishra
 * @author hitesh
 *
 */
public class ProjectBuildPathPropertyPage extends AbstractStudioPropertyPage {
	
	private TreeViewer fProjectLibraryTreeViewer;
	private TreeViewer fCustomFunctionTreeViewer;
	private TreeViewer fJavaLibraryTreeViewer;
	private TabItem projectLibTab;
	private TabItem customFunctionLibTab;
	private TabItem javaLibTab;
	private TabFolder parentFolder;
	private Combo xpathVersionCombo;
	private boolean fChanged = false;
	private XPATH_VERSION xpathversion = null;

	private Button removeAllButton;
//	private List<BuildPathEntry> removedBuildPathEntries = new ArrayList<BuildPathEntry>();
	private StudioProjectConfigurationDelta configurationDelta;
	
	public ProjectBuildPathPropertyPage() {
		super(false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.property.page.AbstractStudioPropertyPage#createTabbedPages()
	 */
	protected void createTabbedPages(TabFolder parent) {
		this.parentFolder = parent;
		this.projectLibTab = createProjectLibraryTab(parent);
		this.customFunctionLibTab = createCustomFunctionsTab(parent);
		this.javaLibTab = createJavaLibraryTab(parent);
//		this.removedBuildPathEntries.clear();
		this.configurationDelta = null;
	}
	
	private StudioProjectConfigurationDelta getConfigurationDelta() {
		if (this.configurationDelta == null) {
			this.configurationDelta = new StudioProjectConfigurationDelta(StudioProjectConfigurationManager.getInstance().getProjectConfiguration(getViewerInput().getName()), IStudioProjectConfigurationDelta.CHANGED);
		}
		return this.configurationDelta;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performCancel()
	 */
	@Override
	public boolean performCancel() {
		StudioProjectConfigurationManager.getInstance().reload();
		fChanged = false;
		return super.performCancel();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {

		if (!fChanged) {
			return true;
		}
		StudioProjectConfigurationDelta configDelta = getConfigurationDelta();
		
		final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(getViewerInput().getName());
		boolean runMapperWizard = false;
		boolean xpathChanged = false;

		try {
			StudioProjectUtil.checkConfiguration(configuration);
			
			// TODO : this should be moved to a listener in the editor, not here
//			for(BuildPathEntry entry:removedBuildPathEntries){
//				closeJarEntryFileEditor(entry.getPath());
//			}
			if (xpathversion != null) {
				xpathChanged = xpathversion != configuration.getXpathVersion();
				configuration.setXpathVersion(xpathversion);
			}
			
			StudioProjectConfigurationManager.getInstance().fireDelta(configDelta);
			StudioProjectConfigurationManager.getInstance().saveConfiguration(getViewerInput().getName(), configuration);
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getViewerInput().getName());
			GlobalVariablesMananger.getInstance().updateGlobalVariables(project.getName());
		} catch (NoClassDefFoundError e) {
			StudioUIPlugin.errorDialog(getShell(),
					Messages.getString("project.buildpath.tab.javalib.title"), 
					Messages.getString("project.buildpath.tab.javalib.classnotfound",e.getMessage()), 
					e);
			return false;
		} catch (Exception e) {	
			StudioUIPlugin.errorDialog(getShell(),
					Messages.getString("project.buildpath.tab.javalib.title"), 
					Messages.getString("project.buildpath.tab.javalib.save.error"), 
					e);

			return false;
		}
		
		if (MessageDialog.openQuestion(getShell(), Messages.getString("project.buildpath.project.rebuild.title"), 
				Messages.getString("project.buildpath.project.rebuild"))) {
			String name = getViewerInput().getName();
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
			Job cleanJob = new Job("Clean project") {
			
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			cleanJob.schedule();
			Job buildJob = new Job("Build project") {
				
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			buildJob.schedule();
		}
		if (xpathChanged && xpathversion == XPATH_VERSION.XPATH_20) {
			// present the user with a dialog asking whether to run the mapper function migration wizard
			if (MessageDialog.openQuestion(getShell(), Messages.getString("project.buildpath.project.xpathchanged.title"), 
					Messages.getString("project.buildpath.project.xpath.function.wizard"))) {
				runMapperWizard = true;
				runMapperWizard(configuration.getName());
			}
		}

		fChanged = false;
		return true;
	}

	private void runMapperWizard(final String name) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				MapperFunctionRefactoring refactoring = new MapperFunctionRefactoring(name);
				MigrateMapperFunctionsRefactoringWizard refactoringWizard = new MigrateMapperFunctionsRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);

				RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
				try {
					op.run(Display.getDefault().getActiveShell(), "Migrate Mapper Function Calls");
				} catch (InterruptedException e) {
				}
			}
		});
	}

	private TabItem createProjectLibraryTab(TabFolder composite) {		
		TabItem parent = new TabItem(composite, SWT.NONE /*SWT.CLOSE*/);
		parent.setText(Messages.getString("project.buildpath.tab.projectlibrary.title"));
		parent.setImage(StudioUIPlugin.getDefault().getImage("icons/library_obj.gif"));
		
		Composite control = new Composite(composite, SWT.WRAP);

		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;

		Label desriptionLabel = new Label(control, SWT.WRAP);
	    desriptionLabel.setText(Messages.getString("project.buildpath.tab.projectlibrary.desc"));
	    desriptionLabel = new Label(control, SWT.WRAP);
	
	    DesignerProject input = getViewerInput();
	    final String name = input.getName();
	    final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
	    
	    ITreeContentProvider contp = new LibraryContentProvider(new LIBRARY_PATH_TYPE[] {LIBRARY_PATH_TYPE.PROJECT_LIBRARY});
	    ILabelProvider labelProvider = new LibraryLabelProvider();
	  
	    fProjectLibraryTreeViewer = createTreeViewer(control, contp, labelProvider, SWT.BORDER | SWT.MULTI);

	    if (input != null) {	    	
	    	fProjectLibraryTreeViewer.setInput(input);
	    }
	    
		final Composite buttonComposite = new Composite(control,SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;

	    final Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.OPEN);
				fd.setFilterExtensions(new String[]{"*.projlib"});
				String libPath = fd.open();
				if (libPath == null) {
					return;
				}
				addProjectLibrary(libPath, false, false);
				fChanged = true;
			}
		});
		
		addVariableWidget(buttonComposite);
		addEmptyWidget(buttonComposite);
		addEditWidget(buttonComposite);
		
		final Button removeButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		removeButton.setText(Messages.getString("project.buildpath.tab.removebutton"));
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		//added check to disable/enable Remove button
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(new SelectionAdapter() {		
			
			@Override
			public void widgetSelected(SelectionEvent e) {	

				ISelection selection = fProjectLibraryTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removeBuildPathEntry(element, LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
					}
					
					//added check to disable/enable Remove button
					removeButton.setEnabled(configuration.getProjectLibEntries().size() > 0 && !fProjectLibraryTreeViewer.getSelection().isEmpty());
				}
			}
		});
		
		addEmptyWidget(buttonComposite);
		final Button upButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		upButton.setText(Messages.getString("project.buildpath.tab.upbutton"));
		upButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		upButton.setEnabled(false);
		final Button downButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		downButton.setText(Messages.getString("project.buildpath.tab.downbutton"));
		downButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		downButton.setEnabled(false);
		
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(true, upButton, downButton, fProjectLibraryTreeViewer);
			}			
		});
		
		
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(false, upButton, downButton, fProjectLibraryTreeViewer);
			}
		});
		//added check to disable/enable Remove button
		fProjectLibraryTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				enableButtons(upButton, downButton, removeButton, event);
			}
		});
		
		parent.setControl(control);		
		return parent;
	}
	
	private void addProjectLibrary(String libPath, boolean isVar, boolean edit) {
		DesignerProject proj = getViewerInput();
		String name = proj.getName();
		
		if (buildPathEntryExists(name, libPath, LIBRARY_PATH_TYPE.PROJECT_LIBRARY)) {
			// don't add duplicate entries
			return;
		}
		File file = new File(libPath);
		String resolvedPath = libPath;
		if (isVar) {
			resolvedPath = StudioCore.getResolvedVariablePath(new Path(libPath)).toPortableString();
			file = new File(resolvedPath);
			if (file.exists() && file.isDirectory()) {
				return;
			}
		}
		
		ProjectLibraryEntry entry = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
		entry.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
		entry.setPath(libPath);
		entry.setVar(isVar);
		if (isVar) {
			entry.setResolvedPath(resolvedPath);
		}
		entry.setTimestamp(file.lastModified());
		
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		if (edit) {
			BuildPathEntry selectedEntry = getSelectedBuildPathEntry();
			if (selectedEntry !=  null) {
				int index = configuration.getIndexOfEntryByType(selectedEntry, LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
				createBuildPathDelta(entry, LIBRARY_PATH_TYPE.PROJECT_LIBRARY, IBuildPathEntryDelta.ADDED);
				configuration.getProjectLibEntries().add(index, entry);
				removeBuildPathEntry(selectedEntry, LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
			}
		} else {
			createBuildPathDelta(entry, LIBRARY_PATH_TYPE.PROJECT_LIBRARY, IBuildPathEntryDelta.ADDED);
			configuration.getProjectLibEntries().add(entry);
		}
		refreshTreeViewer(fProjectLibraryTreeViewer);
	}
	
	private boolean isCustomFunctionsLibrary(String jarPath, boolean isVar) {
		if (isVar) {
			jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
		}
		File file = new File(jarPath);
		if(file.exists()){
			try {
				JarFile jFile = new JarFile(file);
				Enumeration<JarEntry> en = jFile.entries();
				while(en.hasMoreElements()) {
					JarEntry jEntry = en.nextElement();
					if(jEntry.getName().equals("functions.catalog")){
						return true;
					}
				}
			} catch (IOException e) {						
				return false;
			}
		}
		return false;
	}
	
	private TabItem createCustomFunctionsTab(TabFolder composite) {
		
		TabItem parent = new TabItem(composite, SWT.NONE);
		parent.setText(Messages.getString("project.buildpath.tab.customfunctions.title"));
		parent.setImage(StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png"));
		
		Composite control = new Composite(composite, SWT.WRAP);
		
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;
		
		Label desriptionLabel = new Label(control, SWT.WRAP);
		desriptionLabel.setText(Messages.getString("project.buildpath.tab.customfunctions.desc"));
		desriptionLabel = new Label(control, SWT.WRAP);
		
		final DesignerProject input = getViewerInput();
		final String name = input.getName();
		final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		
		ITreeContentProvider contp = new LibraryContentProvider(new LIBRARY_PATH_TYPE[] {LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY});
		IBaseLabelProvider labelProvider = new LibraryLabelProvider();
		fCustomFunctionTreeViewer = createTreeViewer(control, contp, labelProvider, SWT.BORDER|SWT.MULTI);
		
		if (input != null) {
			fCustomFunctionTreeViewer.setInput(input);
		}
		
		final Composite buttonComposite = new Composite(control,SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;
		
		
		final Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterExtensions(new String[]{"*.jar"});
				fd.open();
				String[] jarPath = fd.getFileNames();
				for (String string : jarPath) {
					File f = new File(fd.getFilterPath()+File.separator+string);
					if(!f.isDirectory()) {
						addCustomFunctionLocation(fd.getFilterPath()+File.separator+string, false, false);
					} else {
						File[] listFiles = f.listFiles(new FilenameFilter(){
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".jar");
							}
						});
						for (File file : listFiles) {
							addCustomFunctionLocation(file.getPath(), false, false);
						}
					}
				}				
				fChanged = true;
			}
		});
		
		addVariableWidget(buttonComposite);
		
		addEmptyWidget(buttonComposite);
		addEditWidget(buttonComposite);
		
		final Button removeButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		removeButton.setText(Messages.getString("project.buildpath.tab.removebutton"));
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = fCustomFunctionTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removeBuildPathEntry(element, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
					}
					
					//added check to disable/enable Remove button
					removeButton.setEnabled(configuration.getCustomFunctionLibEntries().size() > 0 && !fCustomFunctionTreeViewer.getSelection().isEmpty());
				}
			}		
		});
		
		addEmptyWidget(buttonComposite);

		final Button upButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		upButton.setText(Messages.getString("project.buildpath.tab.upbutton"));
		upButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		upButton.setEnabled(false);
		final Button downButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		downButton.setText(Messages.getString("project.buildpath.tab.downbutton"));
		downButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		downButton.setEnabled(false);
		
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(true, upButton, downButton, fCustomFunctionTreeViewer);			}			
		});
		
		
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(false, upButton, downButton, fCustomFunctionTreeViewer);
			}
		});
		
		fCustomFunctionTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				enableButtons(upButton, downButton, removeButton, event);
			}
		});
		fCustomFunctionTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
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
								if(nativePath.getPath() != null && !nativePath.getPath().isEmpty()) {
									oldValue = new Path(nativePath.getPath());
								}
								NativeLibraryDialog dialog = new NativeLibraryDialog(getShell(),nativePath.getPath(),(JavaLibEntry)nativePath.eContainer());
								if(dialog.open() == Window.OK){
									IPath newValue = new Path(dialog.getNativeLibraryPath());
									if(!newValue.equals(oldValue)) {
										nativePath.setPath(newValue.toPortableString());
										refreshTreeViewer(fCustomFunctionTreeViewer);
										fChanged = true;										
									}
								}
							}
						}
					}
					
				}
		
			}
		});
		
		parent.setControl(control);
		return parent;
	}
	
	
	
	private void addCustomFunctionLocation(String jarPath, boolean isVar, boolean edit) {
		DesignerProject proj = getViewerInput();
		String name = proj.getName();
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		if(!isCustomFunctionsLibrary(jarPath, isVar)) {
			boolean addtoThirdParty = MessageDialog.openQuestion(getShell()
					, Messages.getString("project.buildpath.tab.customfunctions.title")
					, Messages.getString("project.buildpath.tab.customfunctions.javalib", jarPath));
			if(addtoThirdParty &&!buildPathEntryExists(name, jarPath, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY)) {
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
				createBuildPathDelta(entry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY, IBuildPathEntryDelta.ADDED);
				configuration.getThirdpartyLibEntries().add(entry);
				parentFolder.setSelection(javaLibTab);
				refreshTreeViewer(fJavaLibraryTreeViewer);						
			} 
		} else {
			
			if (buildPathEntryExists(name, jarPath, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY)) {
				// don't add duplicate entries
				return;
			}
			
			CustomFunctionLibEntry entry = ConfigurationFactory.eINSTANCE.createCustomFunctionLibEntry();
			entry.setEntryType(LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
			entry.setPath(jarPath);
			entry.setVar(isVar);
			if (isVar) {
				jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
				entry.setResolvedPath(jarPath);
			}

			File file = new File(jarPath);
			entry.setTimestamp(file.lastModified());
			if (edit) {
				BuildPathEntry selectedEntry = getSelectedBuildPathEntry();
				if (selectedEntry !=  null) {
					int index = configuration.getIndexOfEntryByType(selectedEntry, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
					configuration.getCustomFunctionLibEntries().add(index, entry);
					removeBuildPathEntry(selectedEntry, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
				}
			} else {
				configuration.getCustomFunctionLibEntries().add(entry);
			}
			refreshTreeViewer(fCustomFunctionTreeViewer);
		}
	}
	
	private TabItem createJavaLibraryTab(TabFolder composite) {
		final TabItem parent = new TabItem(composite, SWT.NONE);
		parent.setText(Messages.getString("project.buildpath.tab.javalib.title"));
		parent.setImage(StudioUIPlugin.getDefault().getImage("icons/javalib_16x16.png"));
		
		final Composite control = new Composite(composite, SWT.WRAP);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		control.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;
		
		
		Label desriptionLabel = new Label(control, SWT.WRAP);
		desriptionLabel.setText(Messages.getString("project.buildpath.tab.javalib.desc"));
		desriptionLabel = new Label(control, SWT.WRAP);
		
		final DesignerProject input = getViewerInput();
		final String name = input.getName();
		final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		
		final ITreeContentProvider contp = new LibraryContentProvider(new LIBRARY_PATH_TYPE[] {LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY,
		                          LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY});
		final IBaseLabelProvider labelProvider = new LibraryLabelProvider();
		fJavaLibraryTreeViewer = createTreeViewer(control, contp, labelProvider,SWT.BORDER|SWT.MULTI);
		
		if (input != null) {
			fJavaLibraryTreeViewer.setInput(input);
		}
		
		final Composite buttonComposite = new Composite(control,SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;
		
		final Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterExtensions(new String[]{"*.jar"});
				fd.open();
				String[] jarPath = fd.getFileNames();
				for (String string : jarPath) {
					File f = new File(fd.getFilterPath()+File.separator+string);
					if(!f.isDirectory()) {
						addThirdPartyLibraryLocation(fd.getFilterPath()+File.separator+string, false, false);
					} else {
						File[] listFiles = f.listFiles(new FilenameFilter(){
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".jar");
							}
						});
						for (File file : listFiles) {
							addThirdPartyLibraryLocation(file.getPath(), false, false);
						}
					}
				}
				if(ProjectBuildPathPropertyPage.this.removeAllButton != null) {
					removeAllButton.setEnabled(configuration.getThirdpartyLibEntries().size() > 0);
				}
				fChanged = true;
			}
		});

		addVariableWidget(buttonComposite);
		addEmptyWidget(buttonComposite);
		addEditWidget(buttonComposite);
		
		final Button removeButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		removeButton.setText(Messages.getString("project.buildpath.tab.removebutton"));
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = fJavaLibraryTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removeBuildPathEntry(element, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					}
				}
			}
		
		});
		
		this.removeAllButton = new Button(buttonComposite, SWT.PUSH  | SWT.CENTER);
		removeAllButton.setText(Messages.getString("project.buildpath.tab.removeallbutton"));
		removeAllButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		removeAllButton.setEnabled(configuration.getThirdpartyLibEntries().size() > 0);
		removeAllButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				configuration.getThirdpartyLibEntries().clear();
				refreshTreeViewer(fJavaLibraryTreeViewer);
				removeAllButton.setEnabled(configuration.getThirdpartyLibEntries().size() > 0);
				fChanged=true;
			}
		
		});
		
		addEmptyWidget(buttonComposite);
		
		final Button upButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		upButton.setText(Messages.getString("project.buildpath.tab.upbutton"));
		upButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		upButton.setEnabled(false);
		final Button downButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		downButton.setText(Messages.getString("project.buildpath.tab.downbutton"));
		downButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		downButton.setEnabled(false);
		
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(true, upButton, downButton, fJavaLibraryTreeViewer);
			}			
		});
		
		
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableArrowButtons(false, upButton, downButton, fJavaLibraryTreeViewer);
			}
			
		});
		
		fJavaLibraryTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				enableButtons(upButton, downButton, removeButton, event);
			}
		});
		
		fJavaLibraryTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
		
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
								if(nativePath.eContainer() instanceof CoreJavaLibEntry ) {
									return;
								}
								IPath oldValue = null;
								if(nativePath.getPath() != null && !nativePath.getPath().isEmpty()){
									oldValue = new Path(nativePath.getPath());
								}
								NativeLibraryDialog dialog = new NativeLibraryDialog(getShell(),nativePath.getPath(),(JavaLibEntry)nativePath.eContainer());
								if(dialog.open() == Window.OK){
									IPath newValue = new Path(dialog.getNativeLibraryPath());
									if(!newValue.equals(oldValue)) {
										nativePath.setPath(newValue.toPortableString());
										refreshTreeViewer(fJavaLibraryTreeViewer);
										fChanged = true;										
									}
								}
							}
						}
					}
					
				}
		
			}
		});
		parent.setControl(control);
		parent.getParent().update();
		return parent;
	}
	
	
	protected void addThirdPartyLibraryLocation(String jarPath, boolean isVar, boolean edit) {
		DesignerProject proj = getViewerInput();
		String name = proj.getName();
		
		if (buildPathEntryExists(name, jarPath, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY)) {
			// don't add duplicate entries
			return;
		}
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		if(isCustomFunctionsLibrary(jarPath, isVar)) {
			boolean addtoCustomFunctions = MessageDialog.openQuestion(getShell()
					, Messages.getString("project.buildpath.tab.javalib.title")
					, Messages.getString("project.buildpath.tab.javalib.customfunc", jarPath));
			if(addtoCustomFunctions && !buildPathEntryExists(name, jarPath, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY)) {
				CustomFunctionLibEntry entry = ConfigurationFactory.eINSTANCE.createCustomFunctionLibEntry();
				entry.setEntryType(LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
				entry.setPath(jarPath);
				entry.setVar(isVar);
				
				if (isVar) {
					jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
					entry.setResolvedPath(jarPath);
				}
				File file = new File(jarPath);
				entry.setTimestamp(file.lastModified());
				
				// create an empty native lib if none exists
				if(entry.getNativeLibraryLocation() == null) {
					NativeLibraryPath libPath = ConfigurationFactory.eINSTANCE.createNativeLibraryPath();
					entry.setNativeLibraryLocation(libPath);
				}
				createBuildPathDelta(entry, LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY, IBuildPathEntryDelta.ADDED);
				configuration.getCustomFunctionLibEntries().add(entry);
				parentFolder.setSelection(customFunctionLibTab);
				fCustomFunctionTreeViewer.refresh();
			} else {
				
				ThirdPartyLibEntry entry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
				entry.setEntryType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
				entry.setPath(jarPath);
				entry.setVar(isVar);
				
				if (isVar) {
					jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toOSString();
					entry.setResolvedPath(jarPath);
				}
				
				File file = new File(jarPath);
				entry.setTimestamp(file.lastModified());
				
				// create an empty native lib if none exists
				if(entry.getNativeLibraryLocation() == null) {
					NativeLibraryPath libPath = ConfigurationFactory.eINSTANCE.createNativeLibraryPath();
					entry.setNativeLibraryLocation(libPath);
				}
				if (edit) {
					BuildPathEntry selectedEntry = getSelectedBuildPathEntry();
					if (selectedEntry !=  null) {
						int index = configuration.getIndexOfEntryByType(selectedEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
						configuration.getThirdpartyLibEntries().add(index, entry);
						removeBuildPathEntry(selectedEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					}
				} else {
					// handling duplicate build variable path entry
					if (existingVariablePath(configuration, entry, isVar)) {
						return;
					}
					configuration.getThirdpartyLibEntries().add(entry);
				}
			}
		} else {	
			ThirdPartyLibEntry entry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
			entry.setEntryType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
			entry.setPath(jarPath);
			entry.setVar(isVar);
			
			if (isVar) {
				jarPath = StudioCore.getResolvedVariablePath(new Path(jarPath)).toPortableString();
				entry.setResolvedPath(jarPath);
			}
			
			File file = new File(jarPath);
			entry.setTimestamp(file.lastModified());
			// create an empty native lib if none exists
			if(entry.getNativeLibraryLocation() == null) {
				NativeLibraryPath libPath = ConfigurationFactory.eINSTANCE.createNativeLibraryPath();
				entry.setNativeLibraryLocation(libPath);
			}
			if (edit) {
				BuildPathEntry selectedEntry = getSelectedBuildPathEntry();
				if (selectedEntry !=  null) {
					int index = configuration.getIndexOfEntryByType(selectedEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					configuration.getThirdpartyLibEntries().add(index, entry);
					removeBuildPathEntry(selectedEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
				}
			} else {
				// handling duplicate build variable path entry
				if (existingVariablePath(configuration, entry, isVar)) {
					return;
				}
				configuration.getThirdpartyLibEntries().add(entry);
			}
		}
		refreshTreeViewer(fJavaLibraryTreeViewer);
	}
	
	/**
	 * @param configuration
	 * @param entry
	 * @param isVar
	 * @return
	 */
	private boolean existingVariablePath(StudioProjectConfiguration configuration, 
			                             ThirdPartyLibEntry entry, 
			                             boolean isVar) {
		if (isVar) {
			for(ThirdPartyLibEntry thirdPartyEntry : configuration.getThirdpartyLibEntries()) {
				if (thirdPartyEntry.getPath(false).equals(entry.getPath(false))) {
					int index = configuration.getIndexOfEntryByType(thirdPartyEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					configuration.getThirdpartyLibEntries().add(index, entry);
					removeBuildPathEntry(thirdPartyEntry, LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param projectName
	 * @param jarPath
	 * @param type
	 * @return
	 */
	protected boolean buildPathEntryExists(String projectName, String jarPath, LIBRARY_PATH_TYPE type) {
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		EList<BuildPathEntry> entries = configuration.getEntriesByType(type);
		for (BuildPathEntry entry : entries) {
			String path = entry.getPath();
			if (entry.isVar()) {
				path = entry.getPath(true);
			}
			String jarPathStr = new Path(jarPath).toPortableString();
			String pathStr = new Path(path).toPortableString();
			if (jarPathStr.equals(pathStr)) {
				return true;
			}
		}
		return false;
	}

	protected void removeBuildPathEntry(Object element, LIBRARY_PATH_TYPE type) {
		if (!(element instanceof BuildPathEntry)) {
			return;
		}
		BuildPathEntry removedEntry = (BuildPathEntry) element;
		DesignerProject proj = getViewerInput();
		String name = proj.getName();
		
		createBuildPathDelta(removedEntry, type, IBuildPathEntryDelta.REMOVED);
//		removedBuildPathEntries.add(removedEntry);
		
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		if (removedEntry != null) {
			configuration.removeEntriesByType(removedEntry, type);
		}
		if (type == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY) {
			refreshTreeViewer(fCustomFunctionTreeViewer);
		} else if (type == LIBRARY_PATH_TYPE.PROJECT_LIBRARY) {
			refreshTreeViewer(fProjectLibraryTreeViewer);
			closeProjectDiagramEditor();
		} else if (type == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
			refreshTreeViewer(fJavaLibraryTreeViewer);
		}
		fChanged = true;
	}

	private void createBuildPathDelta(BuildPathEntry affectedEntry,
			LIBRARY_PATH_TYPE type, int changeType) {
		StudioProjectConfigurationDelta configDelta = getConfigurationDelta();
		switch(type.getValue()) {
		case LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY_VALUE:
		{
			CustomFunctionLibEntryDelta delta = new CustomFunctionLibEntryDelta(affectedEntry, changeType);
			configDelta.getCustomFunctionLibEntries().add(delta);
			break;
		}
		
		case LIBRARY_PATH_TYPE.PROJECT_LIBRARY_VALUE:
		{
			ProjectLibraryEntryDelta delta = new ProjectLibraryEntryDelta(affectedEntry, changeType);
			configDelta.getProjectLibEntries().add(delta);
			break;
		}
			
		case LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY_VALUE:
		{
			ThirdPartyLibEntryDelta delta = new ThirdPartyLibEntryDelta(affectedEntry, changeType);
			configDelta.getThirdpartyLibEntries().add(delta);
			break;
		}
			
		default:
		}
	}

	private void closeProjectDiagramEditor() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			for(IEditorReference reference:  page.getEditorReferences()) {
				try {
					IEditorPart editor = page.findEditor(reference.getEditorInput());
					if (editor !=null) {
						page.closeEditor(editor, false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	protected DesignerProject getViewerInput() {
		IAdaptable adaptable = getElement();
		if (adaptable instanceof IProject){			
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex((IProject) adaptable);	
			return index;
		}
		return null;
	}
	
	//Wait for UpdateReferencedIndexJob to finish
	public static void refreshTreeViewer(TreeViewer tv){	
		if (tv == null) return;
		waitForUpdate();
		tv.refresh();				
	}
	
	// wait for Update until Index Reference Update Job is finished
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
	
	/**
	 * @param control
	 */
	private void addEmptyWidget(Composite control) {
		new Button(control, SWT.PUSH  | SWT.CENTER).setVisible(false);
	}
	
	/**
	 * @param control
	 * @return
	 */
	private Button addVariableWidget(Composite control) {
		final Button button = new Button(control, SWT.PUSH  | SWT.CENTER);
		button.setText(Messages.getString("project.buildpath.tab.addVariablebutton"));
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		button.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				openVariableSelectionDialog(null, false);
				
		}});
		return button;
	}
	
	/**
	 * @param control
	 * @return
	 */
	private Button addEditWidget(Composite control) {
		final Button button = new Button(control, SWT.PUSH  | SWT.CENTER);
		button.setText(Messages.getString("project.buildpath.tab.editbutton"));
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		setEnabled(button, getSelection());
		button.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = getSelection();
				if (selection != null 
						&& selection instanceof StructuredSelection 
						&& ((StructuredSelection) selection).size() == 1) {
					Object selectedElement = ((StructuredSelection) selection).getFirstElement();
					if (selectedElement instanceof BuildPathEntry) {
						openVariableSelectionDialog((BuildPathEntry)selectedElement, true);
					}
				}
				setEnabled(button, selection);
			}});
		return button;
	}
	
	/**
	 * @param button
	 * @param selection
	 */
	private void setEnabled(final Button button, ISelection selection) {
		final DesignerProject input = getViewerInput();
		final String name = input.getName();
		final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		boolean size = configuration.getProjectLibEntries().size() > 0 ;
		boolean select = selection != null && selection instanceof StructuredSelection 
						              && ((StructuredSelection) selection).size() == 1 && 
						              ((StructuredSelection) selection).getFirstElement() instanceof BuildPathEntry;
		button.setEnabled(size && select);
	}
	
	/**
	 * @return
	 */
	private ISelection getSelection() {
		TabItem selectedTab = mainFolder.getSelection()[0];
		ISelection selection = null;
		if (selectedTab == projectLibTab) {
			selection = fProjectLibraryTreeViewer.getSelection();
		} else if (selectedTab == customFunctionLibTab)  {
			selection = fCustomFunctionTreeViewer.getSelection();
		} else if ( selectedTab == javaLibTab) {
			selection = fJavaLibraryTreeViewer.getSelection();
		} 
		return selection;
	}

	/**
	 * @return BuildPathEntry
	 */
	private BuildPathEntry getSelectedBuildPathEntry() {
		ISelection selection = getSelection();
		if (selection != null && selection instanceof StructuredSelection) {
			Object object = ((StructuredSelection)selection).getFirstElement();
			if (object instanceof BuildPathEntry) {
				return (BuildPathEntry)object;
			}
		}
		return null;
	}
	
	/**
	 * @param existing
	 */
	private void openVariableSelectionDialog(BuildPathEntry existing, boolean edit) {
		IPath[] existingPathsArray= ClassPathVarUtils.getExistingBuildPaths(getViewerInput().getName());
		TabItem selectedTab = mainFolder.getSelection()[0];
		boolean projectlib = false;
		if (selectedTab == projectLibTab) {
			projectlib = true;
		}
		if (existing == null) {
			
			IPath[] paths= ClasspathVariableUiUtils.chooseVariableEntries(getShell(), existingPathsArray, projectlib);
			if (paths != null) {
				for (IPath path : paths) {
					String ext = getExtension(path);
					if(projectlib){
						
						if(ClasspathVariableUiUtils.isProjlibFileExtension(ext))
							updateBuildPath(selectedTab, path, true, false);
						
							}
					else if(ClasspathVariableUiUtils.isArchiveFileExtension(ext))
						updateBuildPath(selectedTab, path, true, false);
				}
				fChanged = true;
			}
		} else {
			IPath existingPath =  new Path (existing.isVar() ? existing.getPath(false) : existing.getPath());
			IPath path= ClasspathVariableUiUtils.configureVariableEntry(getShell(),existingPath, existingPathsArray, projectlib);
			if (path != null) {
				String ext =getExtension(path);
				if(projectlib){
					
					if(ClasspathVariableUiUtils.isProjlibFileExtension(ext))
						updateBuildPath(selectedTab, path, ClassPathVarUtils.containsVar(path), true);
						}
				
				else if(ClasspathVariableUiUtils.isArchiveFileExtension(ext))
					updateBuildPath(selectedTab, path, ClassPathVarUtils.containsVar(path), true);
				
				fChanged = true;
			}
		}
	}

	
	/**
	 * @param selectedTab
	 * @param path
	 * @param isVar
	 * @param edit
	 */
	private void updateBuildPath(TabItem selectedTab, IPath path, boolean isVar, boolean edit) {
		if (selectedTab == projectLibTab) {
			addProjectLibrary(path.toPortableString(), isVar, edit);
		} else if (selectedTab == customFunctionLibTab)  {
			addCustomFunctionLocation(path.toPortableString(), isVar, edit);
		} else if ( selectedTab == javaLibTab) {
			addThirdPartyLibraryLocation(path.toPortableString(), isVar, edit);
		} 
	}
	
	public TreeViewer getCustomFunctionTreeViewer() {
		return fCustomFunctionTreeViewer;
	}

	public TreeViewer getJavaLibraryTreeViewer() {
		return fJavaLibraryTreeViewer;
	}
	
	public void setChanged(boolean changed) {
		fChanged = changed;
	}
	
	/**
	 * @param upButton
	 * @param downButton
	 * @param removeButton
	 * @param event
	 */
	private void enableButtons(Button upButton, Button downButton, Button removeButton, SelectionChangedEvent event) {
		StudioProjectConfiguration configuration = 
			StudioProjectConfigurationManager.getInstance().getProjectConfiguration( getViewerInput().getName());
		ISelection selection = event.getSelection();
		if(isBuildPathEntrySelection(selection)) {
			Object element = ((StructuredSelection) selection).getFirstElement();
			List<? extends BuildPathEntry> entries = getEntries(element, configuration);
			int size = entries.size();
			int index = entries.indexOf(element);
			removeButton.setEnabled(true);
			upButton.setEnabled(true);
			downButton.setEnabled(true);
			if (index == 0) {
				upButton.setEnabled(false);
			}
			if (index == size-1) {
				downButton.setEnabled(false);
			}
		} else {
			removeButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
	}
	
	/**
	 * @param up
	 * @param upButton
	 * @param downButton
	 * @param viewer
	 */
	private void enableArrowButtons(boolean up, Button upButton, Button downButton, TreeViewer viewer) {
		StudioProjectConfiguration configuration = 
			StudioProjectConfigurationManager.getInstance().getProjectConfiguration( getViewerInput().getName());
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		ISelection selection = viewer.getSelection();
		if(isBuildPathEntrySelection(selection)) {
			upButton.setEnabled(true);
			downButton.setEnabled(true);
			Object element = ((StructuredSelection) selection).getFirstElement();
			EList<? extends BuildPathEntry> entries = getEntries(element, configuration);
			int size = entries.size();
			int oldPosition = entries.indexOf(element);
			if (up) {
				if(oldPosition > 0) {
					entries.move(oldPosition-1, oldPosition);
					refreshTreeViewer(viewer);
					fChanged = true;
				}
				int newPosition = entries.indexOf(element);
				if (newPosition == 0) {
					upButton.setEnabled(false);
					downButton.setEnabled(true);
				}
			} else {
				if(oldPosition < (size -1)) {
					entries.move(oldPosition+1,oldPosition);
					refreshTreeViewer(viewer);
					fChanged = true;
				}
				int newPosition = entries.indexOf(element);
				if (newPosition == size - 1) {
					upButton.setEnabled(true);
					downButton.setEnabled(false);
				}
			}
		}
	}

	/**
	 * @param element
	 * @param configuration
	 * @return
	 */
	private EList<? extends BuildPathEntry> getEntries(Object element, StudioProjectConfiguration configuration) {
		if (element instanceof ProjectLibraryEntry) {
			return configuration.getProjectLibEntries();
		}
		if (element instanceof CustomFunctionLibEntry) {
			return configuration.getCustomFunctionLibEntries();
		}
		if (element instanceof ThirdPartyLibEntry) {
			return configuration.getThirdpartyLibEntries();
		}
		return null;
	}

	/**
	 * @param selection
	 * @return
	 */
	private boolean isBuildPathEntrySelection(ISelection selection) {
		return selection instanceof StructuredSelection 
		&& ((StructuredSelection) selection).size() == 1 
		&& (((StructuredSelection) selection).getFirstElement() instanceof ProjectLibraryEntry ||
				((StructuredSelection) selection).getFirstElement() instanceof CustomFunctionLibEntry ||
				((StructuredSelection) selection).getFirstElement() instanceof ThirdPartyLibEntry);
	}
	
	private String getExtension(IPath path){
		String resolvedPath = StudioCore.getResolvedVariablePath(path).toPortableString();
		int extIndex= resolvedPath.lastIndexOf('.');
		return resolvedPath.substring(extIndex+1);
	}

	@Override
	protected void createXPathField(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label xpathVersionLabel = new Label(composite,SWT.NULL);
		xpathVersionLabel.setText(Messages.getString("ImportExistingProjectDetailsPage.xpathVersion"));
		xpathVersionCombo = new Combo(composite, SWT.DROP_DOWN|SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 100;
		xpathVersionCombo.setLayoutData(gd);
		xpathVersionCombo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				final Combo eventSource = (Combo) e.widget;
				final int selectionIndex = eventSource.getSelectionIndex();
				if(xpathVersionCombo.getItemCount() == 0) {
					xpathversion = XPATH_VERSION.XPATH_10;
					fChanged = true;	
				} else {
					XPATH_VERSION newxpathversion = XPATH_VERSION.get(selectionIndex);
					if (xpathversion != newxpathversion) {
						xpathversion = newxpathversion;
						fChanged = true;		
					}
				}
				
			}
		});
		
		xpathVersionCombo.removeAll();
		for(XPATH_VERSION x:XPATH_VERSION.VALUES) {
			xpathVersionCombo.add(x.getLiteral());
		}
		
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(getViewerInput().getName());
		if (configuration.getXpathVersion() == XPATH_VERSION.XPATH_10) {
			xpathversion = XPATH_VERSION.XPATH_10;
			xpathVersionCombo.select(0);
		} else {
			xpathversion = XPATH_VERSION.XPATH_20;
			xpathVersionCombo.select(1);
		}
		
	}
}