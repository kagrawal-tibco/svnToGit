package com.tibco.cep.bpmn.ui.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.utils.IgnoredSharedResourceFilter;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.core.converter.sharedresource.SharedResourceElements;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.views.FolderSelectionDialog;
import com.tibco.cep.studio.ui.views.TypedItemFilter;
import com.tibco.cep.studio.ui.views.TypedItemSelectionValidator;

public class GenerateProcessWizardPage extends WizardPage {

	@SuppressWarnings("unused")
	private Composite archiveExplorer;
	private Composite archiveDetails;
	private SashForm sashForm;
	@SuppressWarnings("unused")
	private TreeViewer viewer;
	private CTabFolder earDetailsSection;
	private CTabItem sharedArchivesItem;
	private CTabItem processArchivesItem;
	private Text codegenPath;
	private String projectName;
	private boolean isDirty=false;
	@SuppressWarnings("unused")
	private boolean isValid=true;
	private Button applyButton;
	private Button resetButton;
	private WidgetListener widgetListener;
	private Button addButton;
	private CheckboxTreeViewer processFileTree;
	private IProject project;
	
	private Map<String, IFile> treeInputs;
	private Set<IFile> selectedProcessPaths;
	private Set<IFile> savedProcessPaths;
	private Button selectAllButton;
	private Button clearAllButton;
	@SuppressWarnings("unused")
	private boolean isProcessPathModelChanged;

	protected GenerateProcessWizardPage(String projectName) {
		super(Messages.getString("generateProcessWizard.title"));
		this.projectName = projectName;
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		setTitle(Messages.getString("generateProcessWizard.title"));
		setDescription(Messages.getString("generateProcessWizard.genBeCode"));
		this.widgetListener = new WidgetListener();
		selectedProcessPaths = new HashSet<IFile>();
		savedProcessPaths = new HashSet<IFile>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
	
		setPageComplete(false);
		GridLayout layout = new GridLayout(1,false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		parent.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
//		layoutData.heightHint = 500;
		
		parent.setLayoutData(layoutData);
		
		sashForm = new SashForm(parent,SWT.HORIZONTAL); 
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		
		archiveDetails = new Composite(sashForm,SWT.BORDER);
		archiveDetails.setLayout(new GridLayout(1,false));
		archiveDetails.setLayoutData(new GridData(GridData.FILL_BOTH));

		
		Composite archiveNameComposite = new Composite(archiveDetails, SWT.NONE);
		archiveNameComposite.setLayout(new GridLayout(2,false));
		archiveNameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.processFileTree = new CheckboxTreeViewer(archiveNameComposite);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 2;
	    processFileTree.getTree().setLayoutData(gridData);
	    processFileTree.setContentProvider(new FileTreeContentProvider());
	    processFileTree.setFilters(new ViewerFilter[] {new BpmnProcessTreeViewerFilter()});
	    processFileTree.setLabelProvider(new FileTreeLabelProvider());
	    treeInputs = new HashMap<String, IFile>();
	    processFileTree.setInput(getProject()); // pass a non-null that will be ignored
	    processFileTree.expandAll();
	 // When user checks a checkbox in the tree, check all its children
	    processFileTree.addCheckStateListener(this.widgetListener);

//	    Composite selectClearButtonComp  = new Composite(archiveNameComposite, SWT.BORDER);
//	    selectClearButtonComp.setLayout(new GridLayout(1,false));
//	    selectClearButtonComp.setLayoutData(new GridData(GridData.END, GridData.BEGINNING));
	    createSelectClearButton(archiveNameComposite);

		Composite fileLocationComposite = new Composite(archiveDetails,SWT.NONE);
		fileLocationComposite.setLayout(new GridLayout(3,false));
		fileLocationComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(fileLocationComposite,SWT.NONE).setText(Messages.getString("bpmn.buildpath.tab.codegen.folder.label"));
		codegenPath = new Text(fileLocationComposite,SWT.BORDER);
		codegenPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		codegenPath.addModifyListener(this.widgetListener);
		this.addButton = new Button(fileLocationComposite, SWT.FLAT);
		addButton.setImage(StudioUIPlugin.getDefault().getImage("icons/browse_file_system.gif"));
//		addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(this.widgetListener);
		
		createButtonBar(archiveDetails);
		if(projectName!=null){
			loadConfig();
		} else {
			setErrorMessage(Messages.getString("bpmn.build.wizard.invalidProjectName")); //$NON-NLS-1$
			setPageComplete(false);
		}
	
//		sashForm.setWeights(new int[]{30,70});
		sashForm.setWeights(new int[]{100});
		setControl(sashForm);
	}
	
	IProject getProject() {
		return this.project;
	}
	
	private void treeSelectionChanged(Object[] checkedElements, boolean checked){
		if (checked) {
			for (Object object : checkedElements) {
				if (object instanceof IFile )
					selectedProcessPaths.add((IFile) object);
			}
		}else{
			if(checkedElements == null )
				selectedProcessPaths.clear();
			else {
				for (Object object : checkedElements) {
					selectedProcessPaths.remove(object);
				}
			}
		}
		isDirty = true;
		validatePage();
	}
	
	public class WidgetListener extends SelectionAdapter implements ModifyListener,ICheckStateListener {
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
				// If the item is check/uncheck , check/uncheck all its children . . .
			  boolean checked = event.getChecked();
			  Object element = event.getElement();
	          processFileTree.setSubtreeChecked(element, checked);
	          treeSelectionChanged(new Object[]{element}, checked);
		}
		
    	@Override
    	public void modifyText(ModifyEvent e) {
    		// TODO Auto-generated method stub
    		Object source = e.getSource();
    		if(source == codegenPath) {
    			isDirty = true;
			    validatePage();
    		}
    	}
    	
    	@Override
    	public void widgetSelected(SelectionEvent e) {
    		try{
    			Object source = e.getSource();
    			if(source == addButton) {
    				final IProject project = getProject();
    				Class<?>[] acceptedClasses= new Class<?>[] {IProject.class,IFolder.class };
    				ISelectionStatusValidator validator= new TypedItemSelectionValidator(acceptedClasses, false);
    				List<IResource> rejectedElements= new ArrayList<IResource>();
    				rejectedElements.add(project.getFolder(SharedResourceElements.GLOBAL_VARIABLE_DEFAULT_FOLDER));
    				ViewerFilter filter= new TypedItemFilter(acceptedClasses, rejectedElements.toArray());
    				ILabelProvider lp= new WorkbenchLabelProvider();
    				ITreeContentProvider cp= new WorkbenchContentProvider();
    				IResource initSelection= null;
    				String codeGenPath2 = getCodeGenPath();
    				if (getCodeGenPath() != null) {
    					initSelection= project.findMember(codeGenPath2);
    				}
    				FolderSelectionDialog dialog = new FolderSelectionDialog(getControl().getShell(),lp,cp);
    				dialog.setTitle(Messages.getString("bpmn.buildpath.tab.sourcepath.folderdialog")); //$NON-NLS-1$
    				dialog.setValidator(validator);
    				dialog.setMessage(Messages.getString("bpmn.buildpath.tab.sourcepath.foldermsg"));//$NON-NLS-1$ 
    				dialog.addFilter(filter);
    				dialog.addFilter(new IgnoredSharedResourceFilter());
    				dialog.setInput(project);
    				dialog.setInitialSelection(initSelection);
    				dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
    				if (dialog.open() == Window.OK) {
    					IContainer result = (IContainer)dialog.getFirstResult();
    					codegenPath.setText(result.getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString());
    					isDirty = true;
    					validatePage();
    				}
    			} else if(source == applyButton) {
    				isDirty = false;
    				saveConfig();					
    				validatePage();
    			} else if(source == resetButton) {
    				loadConfig();
    				isDirty=false;
    				validatePage();
    			}
    			else if(source == selectAllButton ) {
    				processFileTree.setSubtreeChecked(processFileTree.getInput(),true);
    				Object[] checkedElements = processFileTree.getCheckedElements();
    				treeSelectionChanged(checkedElements, true);
    			}else if( source == clearAllButton) {
    				processFileTree.setSubtreeChecked(processFileTree.getInput(),false);
    				treeSelectionChanged(null, false);
    			}
    		}catch (Exception ex) {
				BpmnUIPlugin.log(ex);
			}
    	}
    }
	
	class BpmnProcessTreeViewerFilter extends ViewerFilter {
		
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			try {
				if(element instanceof IFile) {
					IFile file = (IFile) element;
					if(file.getFileExtension().equalsIgnoreCase(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION)) {
						return true;
					} 
				} else if( element instanceof IContainer) {
					if(element instanceof IFolder) {
						IFolder f = (IFolder) element;
						return hasProcesses(f);
					}
					return true;
				}	
			} catch (CoreException e) {
				BpmnUIPlugin.log(e);
			}
			
			return false;
		}

		private boolean hasProcesses(IFolder parentFolder) throws CoreException {
			boolean found = false;
			for(IResource r:parentFolder.members()){
				if(r instanceof IFolder) {
					IFolder folder = (IFolder) r;
					found = found || hasProcesses(folder);
				} else if(r instanceof IFile) {
					IFile file = (IFile) r;
					if(file.getFileExtension() != null) {
						found = file.getFileExtension().equalsIgnoreCase(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION);	
					} 
				}
				if(found) {
					return found;
				}
			}
			return found;
		}
	}
	
	/**
	 * This class provides the content for the tree in FileTree
	 */

	class FileTreeContentProvider implements ITreeContentProvider {
	  /**
	   * Gets the children of the specified object
	   * 
	   * @param arg0
	   *            the parent object
	   * @return Object[]
	   */
	  public Object[] getChildren(Object arg0) {
		  try {
			  if(arg0 instanceof IContainer) {
					return ((IContainer)arg0).members();
			  }
		  } catch (CoreException e) {
			  BpmnUIPlugin.log(e);
		  }
		  return new Object[0];
	  }
	  
	  /**
	   * Gets the parent of the specified object
	   * 
	   * @param arg0
	   *            the object
	   * @return Object
	   */
	  public Object getParent(Object arg0) {
	    return null;
	  }

	  /**
	   * Returns whether the passed object has children
	   * 
	   * @param arg0
	   *            the parent object
	   * @return boolean
	   */
	  public boolean hasChildren(Object arg0) {
	    // Get the children
	    Object[] obj = getChildren(arg0);

	    // Return whether the parent has children
	    return obj == null ? false : obj.length > 0;
	  }

	  /**
	   * Gets the root element(s) of the tree
	   * 
	   * @param arg0
	   *            the input data
	   * @return Object[]
	   */
	  public Object[] getElements(Object arg0) {
	    // the root nodes in the file system
	    return getChildren(arg0);
	  }

	  /**
	   * Disposes any created resources
	   */
	  public void dispose() {
	    // Nothing to dispose
	  }

	  /**
	   * Called when the input changes
	   * 
	   * @param arg0
	   *            the viewer
	   * @param arg1
	   *            the old input
	   * @param arg2
	   *            the new input
	   */
	  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		  if (arg2 != null) {
			  	treeInputs = new HashMap<String, IFile>();
				addAllChildren(arg2);
			}
	  }
	  
	  private void addAllChildren(Object obj){
		  Object[] children = getChildren(obj);
		  for (Object object : children)  {
			  if(object instanceof IContainer){
				  addAllChildren(object);
			  }else if(object instanceof IFile){
				  IFile file = (IFile)object;
				  if(file.getFileExtension() != null) {
					  if(file.getFileExtension().equalsIgnoreCase(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION)){
						  treeInputs.put(file.getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString(), file);
					  }
				  }
			  }

		}
	  }
	}

	/**
	 * This class provides the labels for the file tree
	 */

	class FileTreeLabelProvider implements ILabelProvider {
		/**
		 * Gets the image to display for a node in the tree
		 * 
		 * @param arg0
		 *            the node
		 * @return Image
		 */
		public Image getImage(Object arg0) {
			return null;
		}

		/**
		 * Gets the text to display for a node in the tree
		 * 
		 * @param arg0
		 *            the node
		 * @return String
		 */
		public String getText(Object arg0) {
			if (arg0 instanceof IResource) {
				// Get the name of the file
				String text = ((IResource) arg0).getName();

				// If name is blank, get the path
				if (text.length() == 0) {
					text = ((IResource) arg0).getFullPath().toOSString();
				}
				// Check the case settings before returning the text
				return text;
			}
			return null;
		}

		/**
		 * Called when this LabelProvider is being disposed
		 */
		public void dispose() {
		}

		/**
		 * Returns whether changes to the specified property on the specified
		 * element would affect the label for the element
		 * 
		 * @param arg0
		 *            the element
		 * @param arg1
		 *            the property
		 * @return boolean
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

	}
	

	private void loadConfig() {
		BpmnProcessSettings bpmnConfig = getBpmnProcessConfig();
		if(bpmnConfig.getBuildFolder() != null) {
			codegenPath.setText(bpmnConfig.getBuildFolder());
		}
		refreshProcessPathModel();
		refreshTree();
	}
	
	private void saveConfig() throws Exception {

		BpmnProcessSettings bpmnConfig = getBpmnProcessConfig();

		bpmnConfig.setBuildFolder(getCodeGenPath());
		
		EList<BpmnProcessPathEntry> selectedpaths = bpmnConfig.getSelectedProcessPaths();
		selectedpaths.clear();
		for (IFile file : selectedProcessPaths) {
			if (file instanceof IFile) {
				BpmnProcessPathEntry entry =ConfigurationFactory.eINSTANCE
							.createBpmnProcessPathEntry();
					entry.setEntryType(LIBRARY_PATH_TYPE.BPMN_PROCESS_PATH_LIBRARY);
					entry.setPath(((IFile) file).getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString());
					selectedpaths.add(entry);
			}
		}
        
		saveConfiguration(this.projectName);
		refreshProcessPathModel();
	}
	
	private String getCodeGenPath(){
		return codegenPath.getText();
	}
	
	private void refreshTree(){
		for (IFile file : savedProcessPaths) {
			processFileTree.setChecked(file, true);
		}
	}
	
	private void refreshProcessPathModel(){
		savedProcessPaths.clear();
		BpmnProcessSettings bpmnConfig = getBpmnProcessConfig();
		EList<BpmnProcessPathEntry> selectedPaths = bpmnConfig.getSelectedProcessPaths();
		Set<String> keySet = treeInputs.keySet();
		for (Iterator<?> iterator = selectedPaths.iterator(); iterator
				.hasNext();) {
			BpmnProcessPathEntry bpmnProcessPathEntry = (BpmnProcessPathEntry) iterator
					.next();
			if(!keySet.contains(bpmnProcessPathEntry.getPath())){
				iterator.remove();
				continue;
			}
			savedProcessPaths.add(treeInputs.get(bpmnProcessPathEntry.getPath()));
		}
		selectedProcessPaths.clear();
		selectedProcessPaths.addAll(savedProcessPaths);
	}
	
	@SuppressWarnings("unused")
	private BpmnProcessPathEntry findBpmnProcessPathEntry(EList<BpmnProcessPathEntry> selectedProcessPaths, String path){
		for (BpmnProcessPathEntry bpmnProcessPathEntry : selectedProcessPaths) {
			if(bpmnProcessPathEntry.getPath().equalsIgnoreCase(path))
				return bpmnProcessPathEntry;
		}
		
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void saveConfiguration(String projectName) throws Exception {
		BpmnCorePlugin.saveBpmnProjectConfiguration(projectName);
	}

	private BpmnProcessSettings getBpmnProcessConfig() {
		BpmnProcessSettings config = BpmnCorePlugin.getBpmnProjectConfiguration(projectName);
		
		return config;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());

		// Add the buttons to the button bar.
		createButtonsForButtonBar(composite);
		return composite;
	}

	private void createSelectClearButton(Composite parent){
//		GridLayout glayout = new GridLayout(1, false);
//		parent.setLayout(glayout);
		selectAllButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.BEGINNING, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		selectAllButton.setText(BpmnMessages.getString("selectAll_label")); //$NON-NLS-1$
		selectAllButton.setLayoutData(gd);
		selectAllButton.addSelectionListener(this.widgetListener);

		clearAllButton = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.BEGINNING, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		clearAllButton.setText(BpmnMessages.getString("clearAll_label")); //$NON-NLS-1$
		clearAllButton.setLayoutData(gd);
		clearAllButton.addSelectionListener(this.widgetListener);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);

		applyButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		applyButton.setText(BpmnMessages.getString("applyButton_label")); //$NON-NLS-1$ 
		applyButton.setLayoutData(gd);
		applyButton.setEnabled(false);
		applyButton.addSelectionListener(this.widgetListener);

		resetButton = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		resetButton.setText("Revert"); //$NON-NLS-1$
		resetButton.setLayoutData(gd);
		resetButton.setEnabled(false);
		resetButton.addSelectionListener(this.widgetListener);
	}
	
    /**
     * @param section
     */
    protected void configureToolBar(Composite section){
    	ToolBar tbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem titem = new ToolItem(tbar, SWT.NULL);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_CUT));
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.DROP_DOWN);
		titem.setImage(StudioUIPlugin.getDefault().getImage("icons/linkNavigation.gif"));

   }
	
  
    protected void createBusinessEventsArchiveSection(){
    	earDetailsSection = new CTabFolder(archiveDetails, SWT.BORDER);
	    earDetailsSection.setLayoutData(new GridData(GridData.FILL_BOTH));
	    earDetailsSection.setSimple(false);
	    earDetailsSection.setUnselectedImageVisible(false);
	    earDetailsSection.setUnselectedCloseVisible(false);
	   
	    sharedArchivesItem = new CTabItem(earDetailsSection, SWT.NONE /*SWT.CLOSE*/);
	    sharedArchivesItem.setText(BpmnMessages.getString("sharedArchivesItem_label"));
	    //sharedArchivesItem.setImage(StudioUIPlugin.getDefault().getImage("icons/rules.png"));
	   
	    //Add necessary Components to the Tab section
	    Text text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	    text.setText(BpmnMessages.getString("sharedArchivesItem_label"));
	    
	    sharedArchivesItem.setControl(text);
	    
	   
	    processArchivesItem = new CTabItem(earDetailsSection, SWT.NONE /*SWT.CLOSE*/);
	    processArchivesItem.setText(BpmnMessages.getString("processArchivesItem_label"));
//	    inputDestItem.setImage(StudioUIPlugin.getDefault().getImage("icons/"));
	    
	    //Add necessary Components to the Tab section
	    text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	    text.setText(BpmnMessages.getString("processArchivesItem_label"));
	    processArchivesItem.setControl(text);
	    
	   
	    
	    //Add necessary Components to the Tab section
	    text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	    text.setText(BpmnMessages.getString("businessEventsArchiveSection_text_label"));
	    
	    earDetailsSection.setSelection(sharedArchivesItem); //default Selection
   }
    
    
    
	private void validatePage() {
		BpmnProcessSettings config = getBpmnProcessConfig();
		try {
			boolean isValid = validateFields();
			if(!isValid) {
				applyButton.setEnabled(false);
				return;
			}
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		String codeGenPath2 = getCodeGenPath();
		if(config.getBuildFolder().equals(codeGenPath2) && 
				compareTreeSelection(this.selectedProcessPaths,this.savedProcessPaths)) {
			isDirty = false;
		}
		
		if(isDirty ) {
			applyButton.setEnabled(true);
			resetButton.setEnabled(true);
			setPageComplete(false);
			setErrorMessage(Messages.getString("Build.EAR.doApply"));
			return;
		} else {
			applyButton.setEnabled(false);
			resetButton.setEnabled(false);
		}
	}

	private boolean compareTreeSelection(Set<IFile> selectedFiles, Set<IFile> savedFiles) {
		Set<String> clist = new HashSet<String>();
		Set<String> slist = new HashSet<String>();
		for(IFile cfileEntry:savedFiles) {
			clist.add(cfileEntry.getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString());
		}
		for(IFile file:selectedFiles) {
			String fileUri =  file.getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString();
			slist.add(fileUri);
		}
		return clist.containsAll(slist) && slist.containsAll(clist);
		
	}

	private boolean validateFields() throws Exception {
//		int version ;
		boolean found = false;
		//For validation of EAR name
		String cdgpath = getCodeGenPath();
		if(cdgpath.startsWith("%%") && cdgpath.endsWith("%%")){
			StudioEMFProject sproject = new StudioEMFProject(this.projectName);
			sproject.load();
			GlobalVariables gvars = sproject.getGlobalVariables();
			found = gvars.getVariables().contains(cdgpath);
		} else {
			Path path = new Path(cdgpath);
			found = getProject().findMember(path) != null ;
//			found = found || getProject().getParent().findMember(path) != null;
		}
		
		if(found) {
			setPageComplete(true);
			setErrorMessage(null);
		} else{
			setPageComplete(false);
			setErrorMessage("codegen folder not found");
			return false;
		}
		return true;
	}

	
}