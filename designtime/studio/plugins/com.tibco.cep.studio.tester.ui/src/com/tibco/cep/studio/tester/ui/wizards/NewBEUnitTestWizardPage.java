package com.tibco.cep.studio.tester.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.util.config.cdd.AgentClassesConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.BEHomeInitializer;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.forms.components.TestDataSelector;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (.java).
 */

public class NewBEUnitTestWizardPage extends StudioNewFileWizardPage {
	
	private String folderName;
	private String fileName;
	private String earFile;
	private String tra;
	private String cdd;
	private String agent;
	private String procUnit;
	protected String description;
	protected String conceptTestData;
	protected String eventTestData;
	private Link junitLink;
	protected Text conceptTestDataTxt;
	protected Text eventTestDataTxt;
	protected String prjName;

//	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewBEUnitTestWizardPage(ISelection selection) {
		super("wizardPage", (IStructuredSelection) selection);
		setTitle("New BusinessEvents Unit Test Wizard");
		setDescription("This wizard creates a new BusinessEvents Unit Test Suite.");
//		this.selection = selection;
	}

	@Override
	protected void createAdvancedControls(Composite parent) {
//		super.createAdvancedControls(parent);
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayoutData(gd);

		Label label = new Label(container, SWT.NULL);
		label.setText("&EAR File:");
		final Text earFileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		earFileText.setLayoutData(gd);
		earFileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				earFile = earFileText.getText();
				validatePage();
			}
		});
		IProject project = getProject();
		if (project != null) {
			prjName=project.getName();
			StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
			if (config != null) {
				earFileText.setText(config.getEnterpriseArchiveConfiguration().getPath().replace("\\", "/"));
			}
		}
		
		label = new Label(container, SWT.NULL);
		label.setText("&CDD File:");

		final Combo cddText = new Combo(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cddText.setLayoutData(gd);
		cddText.setItems(getCddFiles());

		label = new Label(container, SWT.NULL);
		label.setText("&Processing Unit:");

		final Combo procUnitText = new Combo(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		procUnitText.setLayoutData(gd);
		procUnitText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				procUnit = procUnitText.getText();
				validatePage();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&TRA File (optional):");

		final Text traText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		traText.setLayoutData(gd);
		traText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				tra = traText.getText();
				validatePage();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Agent Name (optional):");

		final Combo agentText = new Combo(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		agentText.setLayoutData(gd);
		agentText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				agent = agentText.getText();
				validatePage();
			}
		});
		
		cddText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String tmpCdd = cddText.getText();
				if (!tmpCdd.equals(cdd)) {
					cdd = tmpCdd;
					populateCddItems();
					validatePage();
				}
			}

			private void populateCddItems() {
				ClusterConfig clusterConfig = readCdd(cdd);
				procUnitText.setItems(getProcUnits(clusterConfig));
				agentText.setItems(getAgentNames(clusterConfig));
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Description (optional):");
		final Text descText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		descText.setLayoutData(gd);
		descText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				description = descText.getText();
				validatePage();
			}
		});

	
		gd = new GridData(GridData.FILL_HORIZONTAL);
		descText.setLayoutData(gd);
		descText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				description = descText.getText();
				validatePage();
			}
		});
				
		Composite tdcomp = new Composite(parent, SWT.NULL);
		GridLayout tdlayout = new GridLayout();
		tdcomp.setLayout(tdlayout);
		tdlayout.numColumns = 3;
		tdlayout.verticalSpacing = 9;
		GridData tdgd = new GridData(GridData.FILL_HORIZONTAL);
		tdcomp.setLayoutData(tdgd);
		
		Label lblctd = new Label(tdcomp, SWT.NULL);
		lblctd.setText("&Concept TestData (optional):");

		conceptTestDataTxt = new Text(tdcomp, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		conceptTestDataTxt.setLayoutData(gd);

		Button ctdBrowseButton = new Button(tdcomp, SWT.PUSH);
		ctdBrowseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT,false, false));
		ctdBrowseButton.setToolTipText("Browse resources...");
		ctdBrowseButton.setImage(EditorsUIPlugin.getDefault().getImage(
				"/icons/browse_file_system.gif"));
		ctdBrowseButton.addSelectionListener(new ConceptTestDataSelector());
		
		Label lbletd = new Label(tdcomp, SWT.NULL);
		lbletd.setText("&Event TestData (optional):");

		eventTestDataTxt = new Text(tdcomp, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		eventTestDataTxt.setLayoutData(gd);

		Button etdBrowseButton = new Button(tdcomp, SWT.PUSH);
		etdBrowseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT,false, false));
		etdBrowseButton.setToolTipText("Browse resources...");
		etdBrowseButton.setImage(EditorsUIPlugin.getDefault().getImage(
				"/icons/browse_file_system.gif"));
		etdBrowseButton.addSelectionListener(new EventTestDataSelector());
		
		Composite junitComp = new Composite(tdcomp, SWT.NULL);
		junitComp.setLayout(new GridLayout());
		GridData junitData = new GridData();
		junitData.horizontalSpan = 2;
		junitComp.setLayoutData(junitData);
		junitLink = new Link(junitComp, SWT.NULL);
		junitLink.setText("The selected project does not contain the JUnit library on its classpath.  Click <a>here</a> to add it now");
		junitLink.setVisible(false);
		junitLink.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					changeClasspath(getProject());
					validatePage();
				} catch (Exception e1) {
					e1.printStackTrace();
					StudioCorePlugin.log(e1);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
			
		initialize();
		dialogChanged();
	}
	
	protected ClusterConfig readCdd(String cddPath) {
		final ClusterConfigFactory factory = new ClusterConfigFactory();  

        // First tries project relative path.
		IProject project = getProject();
        if (project != null && project.findMember(cddPath) != null) {
        	try {
        		String fullPath = project.findMember(cddPath).getLocation().toString();
				return factory.newConfig(fullPath);
			} catch (IOException e) {
			}
        }
        
        // Then tries in the local file system.
        if (new File(cddPath).isFile()) {
        	try {
				return factory.newConfig(cddPath);
			} catch (IOException e) {
			}
        }
        return null;
	}

	protected String[] getAgentNames(ClusterConfig clusterConfig) {
		if (clusterConfig == null) {
			return new String[0];
		}
		List<String> agentNames = new ArrayList<String>();
		AgentClassesConfig agentClasses = clusterConfig.getAgentClasses();
		EList<InferenceAgentClassConfig> inferenceAgentConfig = agentClasses.getInferenceAgentConfig();
		for (InferenceAgentClassConfig infConfig : inferenceAgentConfig) {
			agentNames.add(infConfig.getId());
		}
		return (String[]) agentNames.toArray(new String[agentNames.size()]);
	}

	protected String[] getProcUnits(ClusterConfig clusterConfig) {
		if (clusterConfig == null) {
			return new String[0];
		}
		List<String> puNames = new ArrayList<String>();
		ProcessingUnitsConfig processingUnits = clusterConfig.getProcessingUnits();
		EList<ProcessingUnitConfig> processingUnit = processingUnits.getProcessingUnit();
		for (ProcessingUnitConfig puConfig : processingUnit) {
			puNames.add(puConfig.getId());
		}
		return (String[]) puNames.toArray(new String[puNames.size()]);
	}

	private String[] getCddFiles() {
		IProject project = getProject();
		final List<String> cdds = new ArrayList<String>();
		if (project != null) {
			try {
				project.accept(new IResourceVisitor() {
					
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFile && "cdd".equalsIgnoreCase(resource.getFileExtension())) {
							cdds.add(resource.getFullPath().removeFirstSegments(1).toString());
							return false;
						}
						return true;
					}
				});
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return (String[]) cdds.toArray(new String[cdds.size()]);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
//		if (selection != null && selection.isEmpty() == false
//				&& selection instanceof IStructuredSelection) {
//			IStructuredSelection ssel = (IStructuredSelection) selection;
//			if (ssel.size() > 1)
//				return;
//			Object obj = ssel.getFirstElement();
//			if (obj instanceof IResource) {
//				IContainer container;
//				if (obj instanceof IContainer)
//					container = (IContainer) obj;
//				else
//					container = ((IResource) obj).getParent();
//				containerText.setText(container.getFullPath().toString());
//			}
//		}
		setFileName(getUniqueName());
	}

	private String getUniqueName() {
		String name = "BEUnitTestSuite";
		IProject project = getProject();
		if (project == null || getContainerFullPath() == null) {
			return name;
		}
		IPath path = getContainerFullPath().removeFirstSegments(1);
		String uniqueName = name + ".java";
		IResource resource = project.findMember(path.append(uniqueName));
		int counter = 1;
		while (resource != null && resource.exists()) {
			uniqueName = name + (counter++) + ".java";
			resource = project.findMember(path.append(uniqueName));
		}
		
		return uniqueName;
	}

	@Override
	protected boolean validatePage() {
		if (!super.validatePage()) {
			return false;
		}
		IPath path = getContainerFullPath().removeFirstSegments(1);
		folderName = path.toString();
		if (!StudioJavaUtil.isInsideJavaSourceFolder(getContainerFullPath().toPortableString(), getContainerFullPath().segment(0))) {
			String problemMessage = Messages.getString("invalid.javaSource.folder");
			setErrorMessage(problemMessage);
			return false;
		}
		IProject proj = getProject();
		String javaFolder = ModelUtilsCore.getJavaSrcFolderName(folderName, proj.getName());
		if (javaFolder == null || "".equals(javaFolder)) {
			// need to create java folder for this?
			updateStatus("Selected folder is not a java source folder");
			return false;
		} else {
			folderName = folderName.substring(javaFolder.length());
		}
		fileName = getFileName();
		IResource resource = proj.findMember(path.append(fileName));
		if (resource != null && resource.exists()) {
			updateStatus("Resource already exists");
			return false;
		}
		
		updateStatus(null);

		try {
			checkForJUnit(proj);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private void checkForJUnit(IProject proj) throws JavaModelException {
        IJavaProject javaProject = JavaCore.create(proj);
        IClasspathEntry[] entries = javaProject.getRawClasspath();
        Path junitPath = new Path("org.eclipse.jdt.junit.JUNIT_CONTAINER/4");
        for (IClasspathEntry entry : entries) {
			if (entry.getPath().equals(junitPath)) {
		        junitLink.setVisible(false);
				return;
			}
		}
        junitLink.setVisible(true);
	}

	private void changeClasspath(IProject project) throws Exception {
        IJavaProject javaProject = JavaCore.create(project);
        IClasspathEntry[] entries = javaProject.getRawClasspath();
        IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];

        System.arraycopy(entries, 0, newEntries, 0, entries.length);

        // add a new entry using the path to the container
        Path junitPath = new Path(
                        "org.eclipse.jdt.junit.JUNIT_CONTAINER/4");
        newEntries[entries.length] = JavaCore
                        .newContainerEntry(junitPath);
        
		StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
        // First, try to use the path variable JUNIT_HOME to add junit.jar
        IPath junitHome = StudioCore.getClasspathVariable(BEHomeInitializer.JUNIT_HOME);
        if (junitHome != null) {
			ThirdPartyLibEntry thirdPartyLibEntry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
			thirdPartyLibEntry.setPath(BEHomeInitializer.JUNIT_HOME+"/junit.jar");
			thirdPartyLibEntry.setVar(true);
			if (!config.getThirdpartyLibEntries().contains(thirdPartyLibEntry)) {
				config.getThirdpartyLibEntries().add(thirdPartyLibEntry);
        		StudioProjectConfigurationManager.getInstance().saveConfiguration(project.getName());
			}
        } else {
        	// JUNIT_HOME is not defined, use the classpath container instead (which contains absolute paths)
        	IClasspathContainer classpathContainer = JavaCore.getClasspathContainer(junitPath, javaProject);
        	IClasspathEntry[] classpathEntries = classpathContainer.getClasspathEntries();
        	if (classpathEntries != null && classpathEntries.length > 0) {
        		for (IClasspathEntry entry : classpathEntries) {
        			IPath path = entry.getPath();
        			ThirdPartyLibEntry thirdPartyLibEntry = ConfigurationFactory.eINSTANCE.createThirdPartyLibEntry();
        			thirdPartyLibEntry.setPath(path.toOSString());
        			if (!config.getThirdpartyLibEntries().contains(thirdPartyLibEntry)) {
        				config.getThirdpartyLibEntries().add(thirdPartyLibEntry);
        			}
        		}
        		StudioProjectConfigurationManager.getInstance().saveConfiguration(project.getName());
        	}
        }
        javaProject.setRawClasspath(newEntries, null);
        
    }
    
	private IProject getProject() {
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(getContainerFullPath().segment(0));
		return proj;
	}
	
	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(getContainerFullPath());
		String fileName = getFileName();

//		if (getContainerName().length() == 0) {
//			updateStatus("File container must be specified");
//			return;
//		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase(".java") == false) {
				updateStatus("File extension must be \".java\"");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

//	public String getContainerName() {
//		return containerText.getText();
//	}
//
//	public String getFileName() {
//		return fileText.getText();
//	}
	public InputStream getInitialContents() {
		String packageName = folderName;
    	if (packageName.startsWith("/")) {
    		packageName = packageName.replaceFirst("/", "");
    	}
    	if (packageName.endsWith("/")) {
    		packageName = packageName.substring(0, packageName.lastIndexOf("/"));
    	}
    	packageName = packageName.replaceAll("/", ".");
    	String newFileName = fileName;
    	if (newFileName.indexOf('.') != -1) {
    		newFileName = newFileName.substring(0, newFileName.lastIndexOf('.'));
    	}

		Map<String, String> props = new HashMap<String, String>();
		props.put("packageName", packageName);
		props.put("className", newFileName);
		props.put("earFile", earFile);
		if (tra == null || tra.trim().length() == 0) {
			String be_home = System.getProperty("tibco.env.BE_HOME");
			if (be_home == null || be_home.trim().length() == 0) {
				be_home = System.getProperty("BE_HOME");
			}
			if (be_home != null) {
				tra = be_home+"/bin/be-engine.tra";
			}
		}
		props.put("traFile", tra);
		props.put("cddFile", cdd);
		props.put("procUnit", procUnit);
		props.put("agentName", agent);
		props.put("description", description);
		if(conceptTestData != null && !conceptTestData.isEmpty()){
			props.put("conceptTestData", conceptTestData);
		}else{
			props.put("conceptTestData", "/TestData/<test data file name>");
		}
		if(eventTestData != null && !eventTestData.isEmpty()){
			props.put("eventTestData", eventTestData);
		}else{
			props.put("eventTestData", "/TestData/<test data file name>");
		}
		
		NewBEUnitTestWizard wiz = (NewBEUnitTestWizard) getWizard();
		NewBEUnitTestDetailsWizardPage detailsPage = wiz.getDetailsPage();
		
		String s = ModelUtils.getUnitTestSource(props, detailsPage.shouldTestRuleOrderExecution(), detailsPage.shouldTestEventFired(), detailsPage.shouldTestConceptModification(), detailsPage.shouldTestWorkingMemory(), detailsPage.shouldTestRuleExecution());
		try {
			return new ByteArrayInputStream(s.getBytes(ModelUtils.UTF8_ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class ConceptTestDataSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("concepttestdata");
			try {
				TestDataSelector fileSelector = new TestDataSelector(
						Display.getDefault().getActiveShell(), prjName,"concept");
				if (fileSelector.open() == Window.OK) {
					if (fileSelector.getFirstResult() instanceof IFile) {
						String firstResult = ((IResource) fileSelector
								.getFirstResult()).getProjectRelativePath()
								.toOSString();
						conceptTestDataTxt.setText(("/"+firstResult.replace("\\", "/")).split(".concepttestdata")[0]);
						conceptTestData = conceptTestDataTxt.getText();
					}
				}
			} catch (Exception e1) {
				StudioUIPlugin.log(e1);
			}
		}
	}
	
	private class EventTestDataSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("eventtestdata");
			try {
				TestDataSelector fileSelector = new TestDataSelector(
						Display.getDefault().getActiveShell(), prjName,"event");
				if (fileSelector.open() == Window.OK) {
					if (fileSelector.getFirstResult() instanceof IFile) {
						String firstResult = ((IResource) fileSelector
								.getFirstResult()).getProjectRelativePath()
								.toOSString();
						eventTestDataTxt.setText(("/"+firstResult.replace("\\", "/")).split(".eventtestdata")[0]);
						eventTestData = eventTestDataTxt.getText();
					}
				}
			} catch (Exception e1) {
				StudioUIPlugin.log(e1);
			}
		}
	}
}