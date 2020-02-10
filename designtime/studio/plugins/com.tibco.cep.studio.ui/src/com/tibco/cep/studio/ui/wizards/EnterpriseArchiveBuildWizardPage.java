package com.tibco.cep.studio.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.widgets.StudioWizardPage;

/**
 * 
 * @author sasahoo
 * 
 */
public class EnterpriseArchiveBuildWizardPage extends StudioWizardPage {

	private Composite archiveDetails;
	private SashForm sashForm;
	private CTabFolder earDetailsSection;
	private CTabItem sharedArchivesItem;
	private CTabItem processArchivesItem;
	/*
	 * private CTabItem functionsitem; private CTabItem objectMgmtItem;
	 */
	private Text earFilePath;
	private Text earFileName;
	private Text earFileAuthor;
	private Text earFileDesc;
	private Text earFileVersion;
	private Button includeServiceLevelGVBtn;
	private Button genDebugInfoVBtn;
	private String projectName;
	private boolean isDirty = false;
	private Button applyButton;
	private Button resetButton;
	private Text outputPath;
	private Button deleteTempFilesBtn;
	private Button outputFolderButton;
	private WidgetListener widgetListner;
	private Button addButton;

	protected EnterpriseArchiveBuildWizardPage(String projectName) {
		super("Build Enterprise Archive: " + projectName);
		this.projectName = projectName;
		setTitle("Build Enterprise Archive: "+projectName);
		setDescription("Create and manage Enterprise Archive Configurations.");
		this.widgetListner = new WidgetListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {

		setPageComplete(false);
		GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		parent.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		// layoutData.heightHint = 500;

		parent.setLayoutData(layoutData);

		sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		archiveDetails = new Composite(sashForm, SWT.NONE);
		archiveDetails.setLayout(new GridLayout(1, false));
		archiveDetails.setLayoutData(new GridData(GridData.FILL_BOTH));



		Composite archiveNameComposite = new Composite(archiveDetails, SWT.NONE);
		archiveNameComposite.setLayout(new GridLayout(2, false));
		archiveNameComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.Name"));

		earFileName = new Text(archiveNameComposite, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		earFileName.setLayoutData(gd);
		earFileName.addModifyListener(this.widgetListner);

		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.Author"));
		earFileAuthor = new Text(archiveNameComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		earFileAuthor.setLayoutData(gd);
		earFileAuthor.addModifyListener(this.widgetListner);
		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.Description"));
		earFileDesc = new Text(archiveNameComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		earFileDesc.setLayoutData(gd);
		earFileDesc.addModifyListener(this.widgetListner);
		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.Version"));
		earFileVersion = new Text(archiveNameComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		earFileVersion.setLayoutData(gd);
		earFileVersion.addModifyListener(this.widgetListner);

		Composite fileLocationComposite = new Composite(archiveDetails, SWT.NONE);
		fileLocationComposite.setLayout(new GridLayout(3, false));
		fileLocationComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		new Label(fileLocationComposite, SWT.NONE).setText(Messages.getString("Build.EAR.Path"));
		earFilePath = new Text(fileLocationComposite, SWT.BORDER);
		earFilePath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		earFilePath.addModifyListener(this.widgetListner);
		PanelUiUtil.setDropTarget(earFilePath);
		
		this.addButton = new Button(fileLocationComposite, SWT.FLAT);
		addButton.setImage(StudioUIPlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		// addButton.setText(Messages.getString("project.buildpath.tab.addbutton"));
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(this.widgetListner);
		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.genDebug"));
		this.genDebugInfoVBtn = new Button(archiveNameComposite, SWT.CHECK);
		genDebugInfoVBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		genDebugInfoVBtn.addSelectionListener(this.widgetListner);
		new Label(archiveNameComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.IncludeAllServiceLevelGV"));
		this.includeServiceLevelGVBtn = new Button(archiveNameComposite,
				SWT.CHECK);
		includeServiceLevelGVBtn.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		includeServiceLevelGVBtn.addSelectionListener(this.widgetListner);
		

		Composite outPutFolderComposite = new Composite(archiveDetails,
				SWT.NONE);
		outPutFolderComposite.setLayout(new GridLayout(3, false));
		outPutFolderComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		new Label(outPutFolderComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.deleteTempFiles"));
		this.deleteTempFilesBtn = new Button(outPutFolderComposite, SWT.CHECK);
		new Label(outPutFolderComposite, SWT.NONE).setText("");
		deleteTempFilesBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deleteTempFilesBtn.addSelectionListener(this.widgetListner);

		new Label(outPutFolderComposite, SWT.NONE).setText(Messages
				.getString("Build.EAR.compile.folder"));
		this.outputPath = new Text(outPutFolderComposite, SWT.BORDER);
		outputPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		outputPath.addModifyListener(this.widgetListner);

		this.outputFolderButton = new Button(outPutFolderComposite, SWT.PUSH);
		outputFolderButton.setImage(StudioUIPlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		// outputFolderButton.setText(Messages.getString("Build.EAR.compile.browse"));
		outputFolderButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_BEGINNING));
		outputFolderButton.addSelectionListener(this.widgetListner);

		createButtonBar(archiveDetails);
		
		if (projectName != null) {
			loadConfig();
		} else {
			setErrorMessage(Messages.getString("Build.EAR.invalidProjectName"));
			setPageComplete(false);
		}

		// sashForm.setWeights(new int[]{30,70});
		sashForm.setWeights(new int[] { 100 });
		setControl(sashForm);
	}

	private void loadConfig() {
		EnterpriseArchiveEntry eaconfig = getEnterpriseArchiveConfig();
//		buildClassesOnlyBtn.setSelection(eaconfig.isBuildClassesOnly());
		if (eaconfig.getName() != null) {
			earFileName.setText(eaconfig.getName());
		}
		if (eaconfig.getAuthor() != null) {
			earFileAuthor.setText(eaconfig.getAuthor());
		}
		if (eaconfig.getDescription() != null) {
			earFileDesc.setText(eaconfig.getDescription());
		}
		//earFilePath.setEnabled(!eaconfig.isBuildClassesOnly());
		earFilePath.setEnabled(true);
		earFileVersion.setText("" + eaconfig.getVersion());
		if (eaconfig.getPath() != null) {
			earFilePath.setText(eaconfig.getPath());
		}
		includeServiceLevelGVBtn.setSelection(eaconfig.isIncludeServiceLevelGlobalVars());
		if (eaconfig.getTempOutputPath() != null) {
			this.outputPath.setText(eaconfig.getTempOutputPath());
		}
		genDebugInfoVBtn.setSelection(eaconfig.isDebug());
		deleteTempFilesBtn.setSelection(eaconfig.isDeleteTempFiles());
		validatePage();
	}

	private void saveConfig() throws Exception {
		EnterpriseArchiveEntry eaconfig = getEnterpriseArchiveConfig();
		eaconfig.setName(earFileName.getText());
		eaconfig.setAuthor(earFileAuthor.getText());
		eaconfig.setDescription(earFileDesc.getText());
		int version;
		if (earFileVersion.getText() != null
				&& !earFileVersion.getText().isEmpty()) {
			try {
				version = Integer.valueOf(earFileVersion.getText()).intValue();
			} catch (NumberFormatException e) {
				version = 1;
			}
		} else {
			version = 1;
		}
		eaconfig.setVersion(version);
		IPath ePath = new Path(earFilePath.getText());
		if (ePath.segmentCount() == 1 && ePath.getDevice() == null
				&& !ePath.isAbsolute()) {
			ePath = new Path(System.getProperty("user.home"))
					.append(earFilePath.getText());
			eaconfig.setPath(ePath.toOSString());
			earFilePath.setText(ePath.toOSString());
		} else {
			eaconfig.setPath(earFilePath.getText());
		}
		eaconfig.setIncludeServiceLevelGlobalVars(includeServiceLevelGVBtn
				.getSelection());
		eaconfig.setDebug(genDebugInfoVBtn.getSelection());
		eaconfig.setTempOutputPath(outputPath.getText());
//		eaconfig.setBuildClassesOnly(buildClassesOnly());
		eaconfig.setDeleteTempFiles(deleteTempFilesBtn.getSelection());
		saveConfiguration(this.projectName);
	}

	/**
	 * @throws Exception
	 * 
	 */
	protected void saveConfiguration(String projectName) throws Exception {
		StudioProjectConfiguration config = StudioProjectConfigurationManager
				.getInstance().getProjectConfiguration(projectName);
		StudioProjectConfigurationManager.getInstance().saveConfiguration(
				projectName, config);
	}

	private EnterpriseArchiveEntry getEnterpriseArchiveConfig() {
		StudioProjectConfiguration config = StudioProjectConfigurationManager
				.getInstance().getProjectConfiguration(projectName);
		EnterpriseArchiveEntry eaconfig = config
				.getEnterpriseArchiveConfiguration();
		if (eaconfig == null) {
			eaconfig = ConfigurationFactory.eINSTANCE
					.createEnterpriseArchiveEntry();
			config.setEnterpriseArchiveConfiguration(eaconfig);
			eaconfig.setName(projectName);
			eaconfig.setAuthor(System.getProperty("user.name"));
		}
		return eaconfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());

		// Add the buttons to the button bar.
		createButtonsForButtonBar(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);

		applyButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		applyButton.setText("Apply"); //$NON-NLS-1$
		applyButton.setLayoutData(gd);
		applyButton.setEnabled(false);
		applyButton.addSelectionListener(this.widgetListner);

		resetButton = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		resetButton.setText("Revert"); //$NON-NLS-1$
		resetButton.setLayoutData(gd);
		resetButton.setEnabled(false);
		resetButton.addSelectionListener(this.widgetListner);
	}

	/**
	 * @param section
	 */
	protected void configureToolBar(Composite section) {
		ToolBar tbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem titem = new ToolItem(tbar, SWT.NULL);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_CUT));
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_COPY));
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_DELETE));
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.DROP_DOWN);
		titem.setImage(StudioUIPlugin.getDefault().getImage(
				"icons/linkNavigation.gif"));

	}

	protected void createBusinessEventsArchiveSection() {
		earDetailsSection = new CTabFolder(archiveDetails, SWT.BORDER);
		earDetailsSection.setLayoutData(new GridData(GridData.FILL_BOTH));
		earDetailsSection.setSimple(false);
		earDetailsSection.setUnselectedImageVisible(false);
		earDetailsSection.setUnselectedCloseVisible(false);

		sharedArchivesItem = new CTabItem(earDetailsSection, SWT.NONE /* SWT.CLOSE */);
		sharedArchivesItem.setText("Shared Archives");
		// sharedArchivesItem.setImage(StudioUIPlugin.getDefault().getImage("icons/rules.png"));

		// Add necessary Components to the Tab section
		Text text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		text.setText("Shared Archives");

		sharedArchivesItem.setControl(text);

		processArchivesItem = new CTabItem(earDetailsSection, SWT.NONE /*
																		 * SWT.CLOSE
																		 */);
		processArchivesItem.setText("Process Archives");
		// inputDestItem.setImage(StudioUIPlugin.getDefault().getImage("icons/"));

		// Add necessary Components to the Tab section
		text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		text.setText("Process Archives");
		processArchivesItem.setControl(text);

		// Add necessary Components to the Tab section
		text = new Text(earDetailsSection, SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		text.setText("Sample Text for Start up/Shutdown Functions");

		earDetailsSection.setSelection(sharedArchivesItem); // default Selection
	}

	private void validatePage() {
		EnterpriseArchiveEntry config = getEnterpriseArchiveConfig();
		boolean isValid = validateFields();
		int version = 1;
		if (!isValid) {
			applyButton.setEnabled(false);
			return;
		}
		if (earFileVersion.getText() != null
				&& !earFileVersion.getText().isEmpty()) {
			try {
				version = Integer.valueOf(earFileVersion.getText()).intValue();
			} catch (NumberFormatException e) {
				version = 1;
			}
		} else {
			version = 1;
		}
		
//		if (buildClassesOnly()) {
//			if (config.isBuildClassesOnly() == buildClassesOnly()
//					&& config.getTempOutputPath().equals(outputPath.getText())) {
//				//Nothing changed
//				isDirty = false;
//			}
//		} else {
			if (config.getName().equals(earFileName.getText())
					&& config.getAuthor().equals(earFileAuthor.getText())
					&& config.getDescription().equals(earFileDesc.getText())
					&& config.getPath().equals(earFilePath.getText())
					&& config.isIncludeServiceLevelGlobalVars() == includeServiceLevelGVBtn.getSelection()
					/*&& config.isBuildClassesOnly() == buildClassesOnly()*/
					&& config.isDebug() == genDebugInfoVBtn.getSelection()
					&& config.getVersion() == version
					&& config.isDeleteTempFiles() == deleteTempFilesBtn.getSelection()
					&& config.getTempOutputPath().equals(outputPath.getText())) {
				isDirty = false;
			}
//		}

		if (isDirty) {
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

	private boolean validateFields() {
		int version;

		// For validation of EAR name
		String earFile = earFilePath.getText();
		if (earFile.length() > 0) {
			IPath path = new Path(earFile);
			earFile = path.toOSString();
			int lastSlashIndex = earFile.lastIndexOf(File.separator);
			int earIndex = earFile.lastIndexOf(".");
			if (lastSlashIndex == -1 || earIndex == -1
					|| lastSlashIndex + 1 > earIndex)
				return false;
			String earName = earFile.substring(lastSlashIndex + 1, earIndex);
			if (earName.length() > 0) {
				final IStatus nameStatus = ResourcesPlugin.getWorkspace()
						.validateName(earName, IResource.FOLDER);
				if (!nameStatus.isOK()) {
					setErrorMessage(nameStatus.getMessage());
					return false;
				}
			} else if (earName.length() == 0) {
				setErrorMessage(File.separator
						+ Messages.getString("Build.EAR.invalidCharacter"));
				return false;
			}
		}

		if (earFileName.getText() == null
				|| earFileName.getText().trim().isEmpty()) {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Build.EAR.invalidName"));
			return false;
		}
		if (earFileVersion.getText() != null
				&& !earFileVersion.getText().isEmpty()) {
			try {
				version = Integer.valueOf(earFileVersion.getText()).intValue();
			} catch (NumberFormatException e) {
				setPageComplete(false);
				setErrorMessage(Messages.getString("Build.EAR.invalidVersion"));
				return false;
			}
			if (version < 1) {
				setPageComplete(false);
				setErrorMessage(Messages.getString("Build.EAR.invalidVersion"));
				return false;
			}
		} else {
			version = 1;
		}

		if (earFilePath.getText() != null
				&& !earFilePath.getText().trim().isEmpty()) {

			String diskDrivePathMessage = null;
			String diskDrivePath = "";
			if (System.getProperty("os.name").startsWith("Windows")) {
				// We may have efficient way of accessing the root drive of the
				// given file name
				diskDrivePath = earFilePath.getText().substring(0, 3);
				File fDriveRoot = new File(diskDrivePath);
				if (!fDriveRoot.exists()) {
					if(diskDrivePath.startsWith("\\")){
						//network drive, do nothing, instead allow EAR creation.
					}else{
						diskDrivePathMessage = Messages.getString(
								"Build.EAR.diskDriveInvalid", diskDrivePath);
					}
				}
			} else { // Linux
				diskDrivePath = earFilePath.getText();
				if (!diskDrivePath.startsWith("/"))
					diskDrivePathMessage = Messages
							.getString("Build.EAR.filePathInvalid");
			}

			if (diskDrivePathMessage == null) {
				setPageComplete(true);
				setErrorMessage(null);
			} else {
				setPageComplete(false);
				setErrorMessage(diskDrivePathMessage);
				return false;
			}

			if (earFilePath.getText().toLowerCase().endsWith(".ear")) {
				setPageComplete(true);
				setErrorMessage(null);
			} else {
				setPageComplete(false);
				setErrorMessage(Messages.getString("Build.EAR.fileNotEar"));
				return false;
			}
			File f = new File(earFilePath.getText());
			try {
				f.getCanonicalFile();
				f.toURI().toURL();
			} catch (MalformedURLException me) {
				setPageComplete(false);
				setErrorMessage(Messages.getString("Build.EAR.filePathInvalid"));
				return false;
			} catch (IOException e) {
				setPageComplete(false);
				setErrorMessage(Messages.getString("Build.EAR.filePathInvalid"));
				return false;
			}

		} else {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Build.EAR.fileNotSpecified"));
			return false;
		}

		if (deleteTempFilesBtn.getSelection()) {
			outputPath.setEnabled(false);
			outputFolderButton.setEnabled(false);
		} else {
			outputPath.setEnabled(true);
			outputFolderButton.setEnabled(true);
			if (outputPath.getText() != null
					|| outputPath.getText().length() > 0) {
				File f = new File(outputPath.getText());
				if (!f.exists() || !f.isDirectory()) {
					setPageComplete(false);
					setErrorMessage(Messages
							.getString("Build.EAR.folderPathInvalid"));
					return false;
				}
			} else {
				setPageComplete(false);
				setErrorMessage(Messages
						.getString("Build.EAR.folderNotSpecified"));
				return false;
			}
		}

		return true;
	}

	public class WidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if(source == earFileName) {
				isDirty = true;
				validatePage();
			} else if(source == earFileVersion) {
				if (earFileVersion.getText() == null
						|| earFileVersion.getText().isEmpty()) {
					earFileVersion.setText("1");
				}
				isDirty = true;
				validatePage();
			} else if(source == earFileAuthor) {
				isDirty = true;
				validatePage();
			} else if(source == earFileDesc) {
				isDirty = true;
				validatePage();
			} else if(source == earFilePath) {
				isDirty = true;
				validatePage();
			} else if(source == outputPath) {
				isDirty = true;
				validatePage();
			}
			
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if(source == addButton) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.SAVE);
				fd.setFilterExtensions(new String[] { "*.ear" });
				if (!earFilePath.getText().isEmpty()) {
					fd.setText("EAR file path");
					final IPath path = new Path(earFilePath.getText());
					final File f = new File(path.toOSString());
					fd.setFileName(f.getPath());
					if (f.exists()) {
						fd.setFilterPath(f.getParent());
					} else {
						fd.setFilterPath(System.getProperty("user.home"));
					}
				}
				String s = fd.open();
				if (s != null) {
					earFilePath.setText(s);
					isDirty = true;
					validatePage();
				}
			} else if( source == genDebugInfoVBtn) {
				isDirty = true;
				validatePage();
			} else if( source == includeServiceLevelGVBtn) {
				isDirty = true;
				validatePage();
			} else if( source == deleteTempFilesBtn) {
				isDirty = true;
				validatePage();
			} else if(source == outputFolderButton) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(getEnterpriseArchiveConfig()
						.getTempOutputPath());

				// Change the title bar text
				dlg.setText("SWT's DirectoryDialog");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					outputPath.setText(dir);
				}
			} else if(source == applyButton) {
				try {
					isDirty = false;
					saveConfig();
					validatePage();
				} catch (Exception e1) {
					StudioUIPlugin.log(e1);
				}
			} else if( source == resetButton) {
				loadConfig();
				isDirty = false;
				validatePage();
			}
		}
	}

	@Override
	public void performGlobalVariable(String projectName) {
		// TODO Auto-generated method stub
		
	}
	
}