package com.tibco.cep.studio.debug.ui.launch;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.utils.Messages;
import com.tibco.cep.studio.util.StudioConfig;

/*
@author ssailapp
@date Jun 18, 2009 12:21:10 PM
 */

public class MainTab extends AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {

	public final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.maintab";
	public final static String TAB_NAME = "Main";
	
	private Text tProj, tVMArgs,tCMDStartupOpts, tCDDFile, tWorkingDir, tEARFile;
	private Combo tUnitName;
	private Button bProjBrowse, bTRAFileBrowse, bCDDFileBrowse, bWorkingDirBrowse, bEARFileBrowse, bPUBrowse;
	private WidgetListener wListener;
	private Composite parent;
	private String launchMode;
	private boolean remote;
	private Label lCDDFile,lUnitName,lWorkingDir,lEARFile;
	private Group vmArgsGroup;
	private Group cmdStartUpOptsGroup;

	private String[] options = {"--propFile", "--propVar", "-p", "-n"};

	public MainTab(String mode, boolean remote) {
		super();
		wListener = new WidgetListener();
		setLaunchMode(mode);
		setRemote(remote);
	}
	
	
	
	/**
	 * @return the remote
	 */
	public boolean isRemote() {
		return remote;
	}



	/**
	 * @param remote the remote to set
	 */
	public void setRemote(boolean remote) {
		this.remote = remote;
	}



	/**
	 * @return the launchMode
	 */
	public String getLaunchMode() {
		return launchMode;
	}



	/**
	 * @param launchMode the launchMode to set
	 */
	public void setLaunchMode(String launchMode) {
		this.launchMode = launchMode;
	}



	@Override
	public boolean canSave() {
		return true;
		//return validateLocal();
	}

	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		comp.setLayout(gl);

		createProjectNameEditor(comp);
		createVMArgsEditor(comp);
		createCommandLineArguementsEditor(comp);
		Composite container = new Composite(comp, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		lCDDFile = new Label(container, SWT.NONE);
		lCDDFile.setText(Messages.getString("maintab.cddfile"));
		Composite childContainer = getChildContainer(container);
		tCDDFile = new Text(childContainer, SWT.BORDER);
		tCDDFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tCDDFile.addModifyListener(wListener);
		bCDDFileBrowse = createPushButton(childContainer, Messages.getString("browse"), null);
		bCDDFileBrowse.addSelectionListener(wListener);
		
		lUnitName = new Label(container, SWT.NONE);
		lUnitName.setText(Messages.getString("maintab.pu"));
		childContainer = getChildContainer(container);
		tUnitName = new Combo(childContainer, SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 200;
		tUnitName.setLayoutData(gd);
		tUnitName.addModifyListener(wListener);
		createPushButton(childContainer, Messages.getString("browse"), null).setVisible(false);
		
		lWorkingDir = new Label(container, SWT.NONE);
		lWorkingDir.setText(Messages.getString("maintab.workdir"));
		childContainer = getChildContainer(container);
		tWorkingDir = new Text(childContainer, SWT.BORDER);
		tWorkingDir.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tWorkingDir.addModifyListener(wListener);
		bWorkingDirBrowse = createPushButton(childContainer, Messages.getString("browse"), null);
		bWorkingDirBrowse.addSelectionListener(wListener);
		
		lEARFile = new Label(container, SWT.NONE);
		lEARFile.setText(Messages.getString("maintab.earfile"));
		childContainer = getChildContainer(container);
		tEARFile = new Text(childContainer, SWT.BORDER);
		tEARFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tEARFile.addModifyListener(wListener);
		bEARFileBrowse = createPushButton(childContainer, Messages.getString("browse"), null);
		bEARFileBrowse.addSelectionListener(wListener);
		
		refresh(isRemote());
		setControl(comp);
	}

	/**
	 * @param container
	 * @return
	 */
	private Composite getChildContainer(Composite container){
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		return childContainer;
	}
	
	private void refresh(boolean isAttach) {
		Control[] controls = new Control[] { vmArgsGroup, tVMArgs,tCMDStartupOpts, 
				tCDDFile, tUnitName, tWorkingDir, tEARFile, bTRAFileBrowse,
				bCDDFileBrowse, bWorkingDirBrowse, bEARFileBrowse, bPUBrowse, lCDDFile,
				lUnitName, lWorkingDir, lEARFile };
		
		for(Control c: controls) {
			if(c != null) {
				c.setEnabled(!isAttach);
				c.setVisible(!isAttach);
			}
			
		}
	
	}



	@Override
	public Image getImage() {
		return StudioDebugUIPlugin.getImage("icons/maintab.gif");
	}

	@Override
	public String getName() {
		return (TAB_NAME);
	}
	
	public String getId() {
		return (TAB_ID);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			// TODO: Get the defaults from the Preference setting
			String defProjName = "";
			String defVMArgs = "";
			String defCMDStartUpOpts = "";
			String defCDDFile = "";
			String defUnitName = "";
			String defWorkingDir = "";
			String defEARFile = "";
			
			String projName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, defProjName);
			String vmArgs = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_VM_ARGS, defVMArgs);
			String cmdArgs = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CMD_STARTUP_OPTS, defCMDStartUpOpts);
			String cddFile = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CDD_FILE, defCDDFile);
			String unitName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_UNIT_NAME, defUnitName);
			String workingDir = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_WORKING_DIR, defWorkingDir);
			String earFile = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE, defEARFile);
			
			configuration.getWorkingCopy().setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CLASSPATH_CHANGE, false);
			
			tProj.setText(projName);
			tVMArgs.setText(vmArgs);
			tCMDStartupOpts.setText(cmdArgs);
			tCDDFile.setText(cddFile);
			tUnitName.setText(unitName);
			tWorkingDir.setText(workingDir);
			tEARFile.setText(earFile);	
			
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		refresh(isRemote());
		return validateLocal();
	}



	private boolean validateLocal() {
		if (tProj.getText().trim().equals("")) {
			setErrorMessage("Project name can't be blank.");
			return false;
		} else {
			boolean found = false;
			IProject[] projects = CommonUtil.getAllStudioProjects();
			for (IProject project : projects) {
				if(project.getName().equals(tProj.getText())) {
					if (!project.isAccessible()) {
						setErrorMessage(MessageFormat.format("Project {0} is not accessible.", tProj.getText()));
						return false;
					}
					if (!CommonUtil.isStudioProject(project)) {
						setErrorMessage(MessageFormat.format("Project {0} is not a valid Studio project.", tProj.getText()));
						return false;
					}
					found = true;
					break;
				}
			}
			if(!found) {
				setErrorMessage(MessageFormat.format("Project {0} not found in the workspace.",tProj.getText()));
				return found;
			}
		}
		if(!isRemote()) {
			if(!validateStartupOptions()) { 
				setErrorMessage("Invalid Startup Options.");
				return false;
			} else if (tCDDFile.getText().trim().equals("")) {
				setErrorMessage("CDD File Location can't be blank.");
				return false;
			} else if(!tCDDFile.getText().toLowerCase().trim().endsWith(".cdd")) { 
				setErrorMessage("CDD File must have *.cdd file extension");
				return false;
			} else if(!(new File(tCDDFile.getText().trim()).exists()) || 
					!(new File(tCDDFile.getText().trim()).isFile())) { 
				setErrorMessage("CDD file does not exist.");
				return false;
			} else if (tUnitName.getText().trim().equals("")) {
				setErrorMessage("Processing Unit name can't be blank.");
				return false;
			} else if (tWorkingDir.getText().trim().equals("")) {
				setErrorMessage("Working Directory can't be blank.");
				return false;
			} else if(!(new File(tWorkingDir.getText().trim()).exists()) || 
					!(new File(tWorkingDir.getText().trim()).isDirectory())) { 
				setErrorMessage("Working directory does not exist.");
				return false;
			} else if (tEARFile.getText().trim().equals("")) {
				setErrorMessage("EAR File Location can't be blank.");
				return false;
			} else if(!tEARFile.getText().toLowerCase().trim().endsWith(".ear")) { 
				setErrorMessage("EAR File must have *.ear file extension");
				return false;
			} else if(!(new File(tEARFile.getText().trim()).exists()) || 
					!(new File(tEARFile.getText().trim()).isFile())) { 
				setErrorMessage("EAR file does not exist.");
				return false;
			} 
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String projName = tProj.getText();
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, projName);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_VM_ARGS, tVMArgs.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CMD_STARTUP_OPTS, tCMDStartupOpts.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CDD_FILE, tCDDFile.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_UNIT_NAME, tUnitName.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_WORKING_DIR, tWorkingDir.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE, tEARFile.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CLASSPATH_CHANGE, false);
		if(projName != null && !"".equals(projName)){
			IProject proj  = ResourcesPlugin.getWorkspace().getRoot().getProject(tProj.getText());
			configuration.setMappedResources(new IResource[]{proj});
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO: Get the defaults from the Preference setting
		String defProjName = "";
		String defVMArgs = "";
		String defCMDStartUpOpts = "";
		String defCDDFile = "";
		String defUnitName = "";
		String defWorkingDir = "";
		String defEARFile = "";

		setDefaultEnvironment(configuration);
		
		// Pre-select a project name
		IProject proj = getContext(StudioDebugUIPlugin.getActivePage());
		if (proj != null) {
			if(proj.isOpen()){
				defProjName = proj.getName();
			}
		} else {
			IProject projsList[] = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			if (projsList.length > 0) {
				defProjName = projsList[0].getName();
			}
		}
		if (!defProjName.trim().equals("")) {
			String configName = getLaunchConfigurationDialog().generateName(defProjName);
			configuration.rename(configName);
			configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, defProjName);
		}
		
		// Set configuration properties
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_VM_ARGS, defVMArgs);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CMD_STARTUP_OPTS, defCMDStartUpOpts);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CDD_FILE, defCDDFile);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_UNIT_NAME, defUnitName);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_WORKING_DIR, defWorkingDir);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE, defEARFile);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CLASSPATH_CHANGE, false);
	}
	
	private void setDefaultEnvironment(ILaunchConfigurationWorkingCopy configuration) {
		Map<String, String> map = new HashMap<String, String>();
		boolean isLinux = Platform.OS_LINUX.equals(Platform.getOS());
		boolean isMac = Platform.OS_MACOSX.equals(Platform.getOS());
//		String osName = System.getProperty("os.name");
//		if (osName != null && (osName.toLowerCase().indexOf("mac") > -1 || osName.toLowerCase().indexOf("darwin") > -1)) {
//			String dyLdLibPath = StudioConfig.substituteEnvVars("tibco.env.AS_HOME");
//			if(dyLdLibPath != null) {
//				map.put("DYLD_LIBRARY_PATH", dyLdLibPath+"/lib");
//			}
//		}
		String extraPath = StudioConfig.substituteEnvVars("tibco.env.PATH");
		if (extraPath != null) {
			if (isLinux) {
				map.put("LD_LIBRARY_PATH", extraPath);
			} else if (isMac) {
				map.put("DYLD_LIBRARY_PATH", extraPath);
			} else {
				map.put("PATH", extraPath);
			}
		}
		if (map.size() > 0) {
			configuration.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, map);
			configuration.setAttribute(ILaunchManager.ATTR_APPEND_ENVIRONMENT_VARIABLES, true);
		}
	}

	private IProject getContext(IWorkbenchPage page) {
		Object obj = null;
		if (page != null) {
			ISelection selection = page.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection)selection;
				if (!ss.isEmpty()) {
					obj = ss.getFirstElement();
					if (obj != null && obj instanceof IResource) {
						return ((IResource)obj).getProject();
					}
				}
			}
		}
		return null;
	}
	

	private void createProjectNameEditor(Composite parent) {
		Font font= parent.getFont();
		Group group= new Group(parent, SWT.NONE);
		group.setText("&Project:"); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		group.setFont(font);
		tProj = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		tProj.setLayoutData(gd);
		tProj.setFont(font);
		tProj.addModifyListener(wListener);
		bProjBrowse = createPushButton(group, Messages.getString("browse"), null); 
		bProjBrowse.addSelectionListener(wListener);
	}
	
	private void createVMArgsEditor(Composite parent) {
		Font font = parent.getFont();

		vmArgsGroup = new Group(parent, SWT.NONE);
		setControl(vmArgsGroup);
		GridLayout topLayout = new GridLayout();
		vmArgsGroup.setLayout(topLayout);	
		GridData gd = new GridData(GridData.FILL_BOTH);
		vmArgsGroup.setLayoutData(gd);
		vmArgsGroup.setFont(font);
		vmArgsGroup.setText(Messages.getString("maintab.vmargs")); 
		
		tVMArgs = new Text(vmArgsGroup, SWT.MULTI | SWT.WRAP| SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		tVMArgs.setLayoutData(gd);
		tVMArgs.setFont(font);
		
		//tVMArgs.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		tVMArgs.setBackground(tVMArgs.getBackground());
		
		tVMArgs.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});	
	}
	
	
	
	
	private void createCommandLineArguementsEditor(Composite parent) {
		Font font = parent.getFont();

		cmdStartUpOptsGroup = new Group(parent, SWT.NONE);
		setControl(cmdStartUpOptsGroup);
		GridLayout topLayout = new GridLayout();
		cmdStartUpOptsGroup.setLayout(topLayout);	
		GridData gd = new GridData(GridData.FILL_BOTH);
		cmdStartUpOptsGroup.setLayoutData(gd);
		cmdStartUpOptsGroup.setFont(font);
		cmdStartUpOptsGroup.setText(Messages.getString("maintab.startupopts")); 
		
		tCMDStartupOpts = new Text(cmdStartUpOptsGroup, SWT.MULTI | SWT.WRAP| SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		tCMDStartupOpts.setLayoutData(gd);
		tCMDStartupOpts.setFont(font);
		
		//tCMDStartupOpts.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		tCMDStartupOpts.setBackground(tCMDStartupOpts.getBackground());
		
		tCMDStartupOpts.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});	
	}
	
	private class WidgetListener implements ModifyListener, SelectionListener {
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if (source == tCDDFile) {
				String[] items = getProcessingUnits(tCDDFile.getText());
				if (items.length > 0) {
					tUnitName.setItems(getProcessingUnits(tCDDFile.getText()));
				}
			}
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == bProjBrowse) {
				handleProjectButtonSelected();
			} 
			else if (source == bCDDFileBrowse) {
				String file = getFileSelection(new String[] { "CDD Files", "All Files (*.*)" }, 
						new String[] { "*.cdd", "*.*"}, tCDDFile.getText(), tCDDFile.getText());
				if (file != null) {
					tCDDFile.setText(file);
				}
			}else if (source == bWorkingDirBrowse) {
				String dir = getDirectorySelection(tWorkingDir.getText());
				if (dir != null) {
					tWorkingDir.setText(dir);
				}
			} else if (source == bEARFileBrowse) {
				String file = getFileSelection(new String[] { "EAR Files", "All Files (*.*)" }, 
						new String[] { "*.ear", "*.*"}, tEARFile.getText(), tEARFile.getText());
				if (file != null) {
					tEARFile.setText(file);
				}
			}
			updateLaunchConfigurationDialog();
		}

		private String[] getProcessingUnits(String cddFilePath) {
			if(cddFilePath == null || "".equals(cddFilePath)){
				return new String[0];
			}
			ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(getStudioProject(), cddFilePath);
			cddModelMgr.parseModel();
			ArrayList<ProcessingUnit> procUnits = cddModelMgr.getProcessingUnits();
			List<String> list = new ArrayList<String>();
			for (ProcessingUnit processingUnit : procUnits) {
				
				list.add(processingUnit.getName());
			}
			String[] puIdsArray = new String[list.size()];
			list.toArray(puIdsArray);
			if (puIdsArray.length > 0 ){
				return puIdsArray;
			}
			return new String[0];
		}
	}
	
	private String getFileSelection(String filterNames[], String filterExtensions[], String filterPath, String defFile) {
	    FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);
	    dialog.setFilterNames(filterNames);
	    dialog.setFilterExtensions(filterExtensions);
	    File file = null;
	    if (filterPath != null && !"".equals(filterPath)) {
	    	file = new File(filterPath);
	    } else {
			IProject studioProject = getStudioProject();
			if(studioProject != null) {
				IPath path = studioProject.getLocation();
				file = path.toFile();
			}
	    }
    	if(file != null && file.exists()) {
    		if(file.isFile()) {
    			dialog.setFilterPath(file.getParent());
    		} else if(file.isDirectory()) {
    			dialog.setFilterPath(file.getPath());
    		}
    	}
	    
	    if (defFile != null) {
	    	File f = new File(defFile);
	    	if (f != null && f.exists() && f.isFile()) {
	    		dialog.setFileName(f.getName());
	    	}
	    }
	    String selectionPath = dialog.open();
	    return selectionPath; 	
	}
	
	private String getDirectorySelection(String filterPath) {
	    DirectoryDialog dialog = new DirectoryDialog(parent.getShell(), SWT.OPEN);
	    if (filterPath != null && new File(filterPath).exists()) {
	    	dialog.setFilterPath(filterPath);
	    }
	    String file = dialog.open();
	    return file; 	
	}
	
	private void handleProjectButtonSelected() {
		IProject project = selectProject();
		if (project == null) {
			return;
		}
		if (!isStudioProject(project)) {
			return;
		}
		
		String projName = project.getName();
		tProj.setText(projName);
	}
	
	private IProject selectProject() {
		ILabelProvider labelProvider= WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Project Selection"); 
		dialog.setMessage("Select a project to constrain your search."); 
		try {
			IProject projects[] = CommonUtil.getAllStudioProjects();
			dialog.setElements(projects);
		} catch (Exception e) {
		}
		IProject initProj = getStudioProject();
		if (initProj != null) {
			dialog.setInitialSelections(new Object[] { initProj });
		}
		if (dialog.open() == Window.OK) {			
			return (IProject) dialog.getFirstResult();
		}		
		return null;
	}
	
	private boolean isStudioProject(IProject project) {
		if (CommonUtil.isStudioProject(project)) {
			return true;
		}
		return false;
	}
	
	protected IProject getStudioProject() {
		String projName = tProj.getText().trim();
		if (projName.length() < 1) {
			return null;
		}
		return ((IProject) ResourcesPlugin.getWorkspace().getRoot().getProject(projName));
	}

	public String getProjectName() {
		return tProj.getText();		
	}
	
	protected boolean validateStartupOptions() {
		boolean valid = true;
		String cmdstartupopts = tCMDStartupOpts.getText();
		if (!cmdstartupopts.isEmpty()) {
			ArrayList<String> processArgs = new ArrayList<String>();
			List<String> invalidOptions = new ArrayList<String>();
			String[] splits = cmdstartupopts.split(" ");
			List<String> splitList = Arrays.asList(splits);
			if (splitList.contains("-h")) {
				processArgs.add("-h");
			}
			for (String opt : options) {
				if (splitList.contains(opt)) {
					int index = splitList.indexOf(opt);
					String val = null;
					try {
						val = splitList.get(index + 1);
					} catch (Exception e) {
						invalidOptions.add(opt);
						valid = false;
						break;
					}
					processArgs.add(opt);
					processArgs.add(val);
				}
			}
		}
		return valid;
	}
}