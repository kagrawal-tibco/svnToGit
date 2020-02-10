package com.tibco.cep.studio.debug.ui.launch.classpath;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.ui.util.Messages;
@SuppressWarnings({"rawtypes","unchecked"})
public class ClasspathTab extends AbstractLaunchConfigurationTab implements
		ILaunchConfigurationTab {
	
	private final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.classpathTab";
	private final static String TAB_NAME = "ClassPath";
	
	private String fProjectName;
	
	List<ThirdPartyLibPath> tplList = new LinkedList<ThirdPartyLibPath>();
	List<CustomFunctionLibPath> custFnList = new LinkedList<CustomFunctionLibPath>();
	
	private WidgetListener wListener;
	private TreeViewer fclasspathTreeViewer;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	
	private ClasspathContentProvider classpathContentProvider;
	
	/**
	 * 
	 */
	public ClasspathTab() {
		super();
		wListener = new WidgetListener();	
	}
	
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
		
		LIBRARY_PATH_TYPE[] types = new LIBRARY_PATH_TYPE[] {
				LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY,
				LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY,
				LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY
				};
		classpathContentProvider = new ClasspathContentProvider(types, this);
		IBaseLabelProvider labelProvider  = new ClasspathLabelProvider();
		fclasspathTreeViewer = createTreeViewer(control, classpathContentProvider, labelProvider);

		if (this.fProjectName != null) {
			fclasspathTreeViewer.setInput(getDesignerProject(this.fProjectName));
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
			final String projectName = workingCopy.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(projectName != null) {
				this.fProjectName = projectName;
				DesignerProject fProject = getDesignerProject(this.fProjectName);
				StudioProjectConfiguration fConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(fProjectName);
				String studioConfigTimestampAttr = workingCopy.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_STUDIO_CONFIG_FILE_TIMESTAMP, "");
				if (!studioConfigTimestampAttr.isEmpty()) {
					long oldval = Long.parseLong(studioConfigTimestampAttr);
					long newval = fConfig.getFileTimeStamp();
					if (newval > oldval) {
						boolean status = MessageDialog.openQuestion(getShell(), com.tibco.cep.studio.debug.ui.utils.Messages.getString("classpathtab.launch.message.dialog.title"), 
								com.tibco.cep.studio.debug.ui.utils.Messages.getString("classpathtab.launch.message.dialog.desc"));
						try {
							if (status) {
								StudioProjectConfigurationManager.getInstance().reloadProjectConfiguration(this.fProjectName);
								workingCopy.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_STUDIO_CONFIG_FILE_TIMESTAMP, String.valueOf(newval));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					workingCopy.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_STUDIO_CONFIG_FILE_TIMESTAMP, String.valueOf(fConfig.getFileTimeStamp()));
				}
				
				fclasspathTreeViewer.setInput(fProject);
			}
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
		}
	}
	
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

	@Override
	public String getName() {
		return TAB_NAME;
	}
	
	@Override
	public String getId() {
		return TAB_ID;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
	
		try {
			List<String> defualtTPLPathList = new LinkedList<String>();
			defualtTPLPathList.add(IStudioDebugLaunchConfigurationConstants.DEFAULT_THIRD_PARTY_LIB_NAME);
			List<String> tplLibPaths = configuration.getAttribute(
					IStudioDebugLaunchConfigurationConstants.ATTR_THIRD_PARTY_LIBRARIES,
					defualtTPLPathList);
			this.tplList.clear();
			for(String libPath: tplLibPaths) {
				this.tplList.add(new ThirdPartyLibPath(libPath));
			}
			
			List<String> defualtCustFnPathList = new LinkedList<String>();
			defualtCustFnPathList.add(IStudioDebugLaunchConfigurationConstants.DEFAULT_CUSTOM_FN_LIB_NAME);
			List<String> custFnLibPaths = configuration.getAttribute(
					IStudioDebugLaunchConfigurationConstants.ATTR_CUSTOM_FN_LIBRARIES,
					defualtCustFnPathList);
			this.custFnList.clear();
			for(String libPath: custFnLibPaths) {
				this.custFnList.add(new CustomFunctionLibPath(libPath));
			}
			
			fclasspathTreeViewer.refresh();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		List<String> tplPathList = new LinkedList<String>();
		for(ThirdPartyLibPath lib: tplList){
			tplPathList.add(lib.getLibPath());
		}
		configuration.setAttribute(
				IStudioDebugLaunchConfigurationConstants.ATTR_THIRD_PARTY_LIBRARIES,
				tplPathList);
		
		List<String> custFnPathList = new LinkedList<String>();
		for(CustomFunctionLibPath lib: custFnList){
			custFnPathList.add(lib.getLibPath());
		}
		configuration.setAttribute(
				IStudioDebugLaunchConfigurationConstants.ATTR_CUSTOM_FN_LIBRARIES,
				custFnPathList);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {

		try {
			this.fProjectName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
		}

		List<String> tplPathList = new LinkedList<String>();
		tplPathList.add(IStudioDebugLaunchConfigurationConstants.DEFAULT_THIRD_PARTY_LIB_NAME);
		configuration.setAttribute(
				IStudioDebugLaunchConfigurationConstants.ATTR_THIRD_PARTY_LIBRARIES,
				tplPathList);

		List<String> custFnPathList = new LinkedList<String>();
		custFnPathList.add(IStudioDebugLaunchConfigurationConstants.DEFAULT_CUSTOM_FN_LIB_NAME);
		configuration.setAttribute(
				IStudioDebugLaunchConfigurationConstants.ATTR_CUSTOM_FN_LIBRARIES,
				custFnPathList);
	}
	
	private DesignerProject getDesignerProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
			return null;
		}
		return StudioProjectCache.getInstance().getIndex(name);
	}
	
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
	
    protected void addLibraryLocation(String jarPath) {
		if(this.fProjectName == null) {
			return;
		}
		boolean libExist = buildPathEntryExists(jarPath);
		if(libExist) {
			return;
		}
		boolean isValidCustomFnLib = isCustomFunctionsLibrary(jarPath);
		if(isValidCustomFnLib) {
			this.custFnList.add(new CustomFunctionLibPath(jarPath));
		} else {
			this.tplList.add(new ThirdPartyLibPath(jarPath));
		}
		refreshTreeViewer(fclasspathTreeViewer);
	}

	
	protected void removeBuildPathEntry(Object element) {
		if(!(element instanceof JavaLibPath)) {
			return;
		}
		if(element instanceof ThirdPartyLibPath) {
			this.tplList.remove(element);
		}
		if(element instanceof CustomFunctionLibPath) {
			this.custFnList.remove(element);
		}
		fclasspathTreeViewer.refresh();
	}
	
	protected boolean buildPathEntryExists(String jarPath) {
		for(ThirdPartyLibPath tplLib: this.tplList) {
			if(tplLib.getLibPath().equals(jarPath)){
				return true;
			}
		}
		for(CustomFunctionLibPath custFnLib: this.custFnList) {
			if(custFnLib.getLibPath().equals(jarPath)){
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
	
	public List<ThirdPartyLibPath> getThirdPartyLib(){
		return this.tplList;
	}
	
	public List<CustomFunctionLibPath> getCustomFunctionLib(){
		return this.custFnList;
	}
	
	private class WidgetListener extends SelectionAdapter implements ModifyListener, SelectionListener,ISelectionChangedListener,IDoubleClickListener {
		@Override
		public void doubleClick(DoubleClickEvent event) {
			// DO NOTHING
		}
		
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if(ClasspathTab.this.fProjectName == null || ClasspathTab.this.fProjectName.isEmpty() ) {
				return;
			}
			ISelection selection = event.getSelection();
			if(selection instanceof StructuredSelection) {
				int size = ((StructuredSelection) selection).size();
				if(size <= 0) {
					return;
				}
				upButton.setEnabled(false);
				downButton.setEnabled(false);
				addButton.setEnabled(false);
				removeButton.setEnabled(false);
				if(size != 1){
					return;
				}
				StructuredSelection s = (StructuredSelection) selection;
				Object element = s.getFirstElement();
				if(element instanceof LIBRARY_PATH_TYPE ) {
					LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) element;
					if(type == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY || 
							type == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
						addButton.setEnabled(true);
						removeButton.setEnabled(false);
					}							
				} else if(element instanceof JavaLibPath){
					int index = -1;
					int numEntries = -1;
					removeButton.setEnabled(true);
					if(element instanceof ThirdPartyLibPath){
						index=tplList.indexOf(element);
						numEntries=tplList.size();
						if(((ThirdPartyLibPath)element).isDefault()){
							removeButton.setEnabled(false);
						}
					} else if(element instanceof CustomFunctionLibPath){
						index=custFnList.indexOf(element);
						numEntries=custFnList.size();
						if(((CustomFunctionLibPath)element).isDefault()){
							removeButton.setEnabled(false);
						}
					}
					adjustUpDownButtons(index, numEntries);
				}
			}
		}

		/**
		 * @param index
		 * @param numEntries
		 */
		private void adjustUpDownButtons(int index, int numEntries) {
			upButton.setEnabled(true);
			downButton.setEnabled(true);
			if(index  == 0) {
				upButton.setEnabled(false);
			}
			if(index == (numEntries-1)) {
				downButton.setEnabled(false);
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
						addLibraryLocation(fd.getFilterPath()+File.separator+string);
					} else {
						File[] listFiles = f.listFiles(new FilenameFilter(){
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".jar");
							}
						});
						for (File file : listFiles) {
							addLibraryLocation(file.getPath());
						}
					}
				}
			}else if (source == removeButton) {
				ISelection selection = fclasspathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removeBuildPathEntry(element);
					}
				}
			}else if (source == upButton) {
				ISelection selection = fclasspathTreeViewer.getSelection();
				Object element = ((StructuredSelection) selection).getFirstElement();

				if(element instanceof ThirdPartyLibPath) {
					moveUp(element, tplList);
				} else if(element instanceof CustomFunctionLibPath) {
					moveUp(element, custFnList);
				}
				fclasspathTreeViewer.refresh();
			} else if (source == downButton) {
				ISelection selection = fclasspathTreeViewer.getSelection();
				Object element = ((StructuredSelection) selection).getFirstElement();

				if(element instanceof ThirdPartyLibPath) {
					moveDown(element, tplList);
				} else if(element instanceof CustomFunctionLibPath) {
					moveDown(element, custFnList);
				}
				fclasspathTreeViewer.refresh();
			} 
			updateLaunchConfigurationDialog();
		}

		/**
		 * @param element
		 */
		private void moveDown(Object element, List lst) {
			int indexOfLib;
			indexOfLib = lst.indexOf(element);
			if(indexOfLib > -1 && indexOfLib < lst.size()-1){
				Object removedLib = lst.remove(indexOfLib);
				int newIndex = indexOfLib+1;
				lst.add(newIndex, removedLib);
				adjustUpDownButtons(newIndex, lst.size());
			}
		}

		/**
		 * @param element
		 */
		private void moveUp(Object element, List lst) {
			int indexOfLib;
			indexOfLib = lst.indexOf(element);
			if(indexOfLib > 0){
				Object removedLib = lst.remove(indexOfLib);
				int newIndex = indexOfLib-1;
				lst.add(newIndex, removedLib);
				adjustUpDownButtons(newIndex, lst.size());
			}
		}
	}

}
