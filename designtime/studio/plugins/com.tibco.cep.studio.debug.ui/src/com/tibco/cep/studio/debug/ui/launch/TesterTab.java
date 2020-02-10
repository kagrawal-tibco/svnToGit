package com.tibco.cep.studio.debug.ui.launch;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.views.FolderSelectionDialog;
import com.tibco.cep.studio.ui.views.TypedItemFilter;
import com.tibco.cep.studio.ui.views.TypedItemSelectionValidator;

/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class TesterTab extends AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {
	private final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.testerTab";
	private final static String TAB_NAME = "Tester";
	
	private WidgetListener wListener;
	private Text testerInput,testerOutput;
	private Button inputSelectionBtn,outputSelectionBtn;
	private Composite parent;
	private String projectName;
	private boolean fChanged = false;
	
	public TesterTab() {
		super();
		wListener = new WidgetListener();	
	}
	

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}


	/**
	 * @param parent the parent to set
	 */
	public void setParent(Composite parent) {
		this.parent = parent;
	}


	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}


	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		setParent(parent);
		Composite control = new Composite(parent, SWT.NONE);
//		GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true);
//		gd.grabExcessHorizontalSpace = true;
//		gd.grabExcessVerticalSpace = true;
//		control.setLayoutData(gd);
		GridLayout layout = new GridLayout(1,false);
		control.setLayout(layout);
		
		Composite inputComp = new Composite(control, SWT.NONE);
		inputComp.setLayout(new GridLayout(3, false));
		inputComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label inputLabel = new Label(inputComp, SWT.WRAP);
		inputLabel.setText("Test Data Input Folder");
		testerInput = new Text(inputComp, SWT.BORDER);
		testerInput.addModifyListener(wListener);
		testerInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		inputSelectionBtn = createPushButton(inputComp, "&Browse...", null);
		inputSelectionBtn.addSelectionListener(wListener);
		
		Composite outputComp = new Composite(control, SWT.NONE);
		outputComp.setLayout(new GridLayout(3, false));
		outputComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label outputLabel = new Label(outputComp, SWT.WRAP);
		outputLabel.setText("Test Data Output Folder");
		testerOutput = new Text(outputComp, SWT.BORDER);
		testerOutput.addModifyListener(wListener);
		testerOutput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outputSelectionBtn = createPushButton(outputComp, "&Browse...", null);
		outputSelectionBtn.addSelectionListener(wListener);
		
		
		setControl(control);
	}
	
	private IContainer chooseContainer(String projectName,String initialLocation) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject iProject = workspace.getRoot().getProject(projectName);
		Class[] acceptedClasses= new Class[] { IProject.class, IFolder.class };
		ISelectionStatusValidator validator= new TypedItemSelectionValidator(acceptedClasses, false);
		IProject[] allProjects= CommonUtil.getAllStudioProjects();
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

		
		FolderSelectionDialog dialog= new FolderSelectionDialog(getParent().getShell(), lp, cp);
		dialog.setTitle(Messages.getString("project.buildpath.tab.sourcepath.folderdialog")); 
		dialog.setValidator(validator);
		dialog.setMessage(Messages.getString("project.buildpath.tab.sourcepath.foldermsg")); 
		dialog.addFilter(filter);
		dialog.setInput(workspace.getRoot());
		IResource initSelection= workspace.getRoot().findMember(initialLocation);
		if(initSelection != null) {
			dialog.setInitialSelection(initSelection);
		}
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		
		if (dialog.open() == Window.OK) {
			return (IContainer)dialog.getFirstResult();
		}
		return null;
	}
	
	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		try {
			final String name = workingCopy.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(name != null) {
				this.projectName = name;
				DesignerProject project = getDesignerProject(this.projectName);
			}
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}
		
	}

	private DesignerProject getDesignerProject(String name) {
		return StudioProjectCache.getInstance().getIndex(name);
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
		return (StudioDebugUIPlugin.getImageDescriptor("icons/genTestData16x16.png")).createImage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String defTesterInputPath = StudioUIPlugin.getDefault().getPluginPreferences().getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
			String defTesterOutputPath = StudioUIPlugin.getDefault().getPluginPreferences().getString(StudioUIPreferenceConstants.TEST_DATA_OUTPUT_PATH);
			
			String testerInputPath = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_INPUT, defTesterInputPath);
			String testerOutputPath = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_OUTPUT, defTesterOutputPath);
			
			
			testerInput.setText(testerInputPath);
			testerOutput.setText(testerOutputPath);
			final String name = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if(name != null) {
				this.projectName = name;
				DesignerProject project = getDesignerProject(this.projectName);
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
		/*
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject iProject = workspace.getRoot().getProject(projectName);
		String inputPath = testerInput.getText();	
		IResource inputFile = iProject.findMember(inputPath);
		try {
			
			if(inputFile == null || !(inputFile instanceof IContainer)) {
				setErrorMessage("Test input data folder not found.");
				boolean result = MessageDialog.openQuestion(getParent().getShell()
						, "Tester Data"
						, MessageFormat.format("Tester input folder {0} does not exist in project {1}.\nWould you like to create it ?", inputPath,iProject.getName()));
				if(result) {
					IFolder folder= iProject.getFolder(inputPath);
					if(!folder.exists()){
						folder.create(true, true, new NullProgressMonitor());
						setErrorMessage(null);
						return true;
					}
				} else
					return false;
			}
			String outputPath = testerOutput.getText();	
			IResource outputFile = iProject.findMember(outputPath);
			if(outputFile == null || !(outputFile instanceof IContainer)) {
				setErrorMessage("Test output data folder not found.");
				boolean result = MessageDialog.openQuestion(getParent().getShell()
						, "Tester Data"
						, MessageFormat.format("Tester output folder {0} does not exist in project {1}.\nWould you like to create it ?", inputPath,iProject.getName()));
				if(result) {
					IFolder folder= iProject.getFolder(inputPath);
					if(!folder.exists()){
						folder.create(true, true, new NullProgressMonitor());
						setErrorMessage(null);
						return true;
					}
				} else
					return false;
			}
		} catch (CoreException e) {
			StudioDebugUIPlugin.log(e);
			return false;
		}
		*/
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_INPUT, testerInput.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_OUTPUT, testerOutput.getText());
		fChanged = false;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		String testerInputPath = StudioUIPlugin.getDefault().getPluginPreferences().getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
		String testerOutputPath = StudioUIPlugin.getDefault().getPluginPreferences().getString(StudioUIPreferenceConstants.TEST_DATA_OUTPUT_PATH);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_INPUT, testerInputPath);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_TEST_DATA_OUTPUT, testerOutputPath);
		try {
			this.projectName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}
	}
	

	private class WidgetListener extends SelectionAdapter implements ModifyListener, SelectionListener,ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if(TesterTab.this.projectName == null) {
				return;
			}
		}
		
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if(source.equals(testerInput)){
				fChanged = true;
			} else if(source.equals(testerOutput)) {
				fChanged = true;
			}
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			if(TesterTab.this.projectName == null) {
				return;
			}
			Object source = e.getSource();
			if(source.equals(inputSelectionBtn)) {
				IContainer c = chooseContainer(getProjectName(), testerInput.getText());
				testerInput.setText(((IResource)c).getProjectRelativePath().makeAbsolute().toPortableString());
				fChanged = true;
			} else if(source.equals(outputSelectionBtn)) {
				IContainer c = chooseContainer(getProjectName(), testerOutput.getText());
				testerOutput.setText(((IResource)c).getProjectRelativePath().makeAbsolute().toPortableString());
				fChanged = true;
			}
			updateLaunchConfigurationDialog();
		}
	}
	
	

}
