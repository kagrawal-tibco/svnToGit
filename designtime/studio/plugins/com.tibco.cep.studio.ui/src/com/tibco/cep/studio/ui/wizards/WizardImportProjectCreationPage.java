package com.tibco.cep.studio.ui.wizards;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.TextProcessor;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class WizardImportProjectCreationPage extends WizardPage {

	private String initialProjectFieldValue;
	protected Text projectNameField;
	protected Combo projectComboField;
	protected boolean importDesignerProject;
	protected Combo xpathVersionCombo;
	private ProjectLocationSelectionGroup projectLocGrp;
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	public WizardImportProjectCreationPage(String pageName,boolean importDesignerProject) {
		super(pageName);
		setPageComplete(false);
		this.importDesignerProject = importDesignerProject;
	}

	/** (non-Javadoc)
	 * Method declared on IDialogPage.
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		initializeDialogUnits(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createProjectNameGroup(composite);

		//For Import Designer Project the Project Contents Location area will be visible
		if(importDesignerProject){
			projectLocGrp = new ProjectLocationSelectionGroup(getMessageHandle(), composite);
			if(initialProjectFieldValue != null) {
				projectLocGrp.updateProjectName(initialProjectFieldValue);
			}

			// Scale the button based on the rest of the dialog
			setButtonLayoutData(projectLocGrp.getBrowseButton());
		}

		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	private final void createProjectNameGroup(Composite parent) {
		// project specification group
		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridData data  = new GridData(GridData.FILL_HORIZONTAL);
		if(!importDesignerProject){
			Label projectListLabel = new Label(projectGroup, SWT.NONE);
			projectListLabel.setText(Messages.getString("DecisionProjectImportWizardPage.Info.projectlist.label"));
			projectListLabel.setFont(parent.getFont());

			projectComboField = new Combo(projectGroup, SWT.BORDER);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			projectComboField.setLayoutData(data);
			projectComboField.setFont(parent.getFont());
		}

		Label projectLabel = new Label(projectGroup, SWT.NONE);
		projectLabel.setText("&Project name:");
		projectLabel.setFont(parent.getFont());

		projectNameField = new Text(projectGroup, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		projectNameField.setLayoutData(data);
		projectNameField.setFont(parent.getFont());

		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		if (initialProjectFieldValue != null) {
			projectNameField.setText(initialProjectFieldValue);
		}
		projectNameField.addListener(SWT.Modify, nameModifyListener);
		
		final Label xpathVersionLabel = new Label(projectGroup,SWT.NULL);
		xpathVersionLabel.setText(Messages.getString("ImportExistingProjectDetailsPage.xpathVersion"));
		xpathVersionCombo = new Combo(projectGroup, SWT.DROP_DOWN|SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 100;
		gd.verticalIndent = 10;
		xpathVersionCombo.setLayoutData(gd);
		xpathVersionCombo.setEnabled(false);
		xpathVersionCombo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				final Combo eventSource = (Combo) e.widget;
				final int selectionIndex = eventSource.getSelectionIndex();
				if(xpathVersionCombo.getItemCount() == 0) {
					((ImportDesignerProjectWizard)getWizard()).setXpathVersion(XPATH_VERSION.XPATH_10);
				} else {
					((ImportDesignerProjectWizard)getWizard()).setXpathVersion(XPATH_VERSION.get(selectionIndex));
				}
				
			}
		});
		
		xpathVersionCombo.removeAll();
		for(XPATH_VERSION x:XPATH_VERSION.VALUES) {
			xpathVersionCombo.add(x.getLiteral());
		}
	}


	public IPath getLocationPath() {
		return new Path(projectLocGrp.getProjectLocation());
	}

	public URI getLocationURI() {
		return projectLocGrp.getProjectLocationURI();
	}

	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(
				getProjectName());
	}

	public String getProjectName() {
		if (projectNameField == null) {
			return initialProjectFieldValue;
		}

		return getProjectNameFieldValue();
	}

	private String getProjectNameFieldValue() {
		if (projectNameField == null) {
			return ""; //$NON-NLS-1$
		}

		return projectNameField.getText().trim();
	}

	public void setInitialProjectName(String name) {
		if (name == null) {
			initialProjectFieldValue = null;
		} else {
			initialProjectFieldValue = name.trim();
			if(projectLocGrp != null) {
				projectLocGrp.updateProjectName(name.trim());
			}
		}
	}

	void setLocationForSelection() {
		if(projectLocGrp !=null){
			projectLocGrp.updateProjectName(getProjectNameFieldValue());
		}
	}

	protected boolean validatePage() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectFieldContents = getProjectNameFieldValue();
		if (projectFieldContents.equals("")) { //$NON-NLS-1$
			setErrorMessage(null);
			setMessage("Project name must be specified");
			return false;
		}

		IStatus nameStatus = workspace.validateName(projectFieldContents,
				IResource.PROJECT);
		if (!nameStatus.isOK()) {
			setErrorMessage(nameStatus.getMessage());
			return false;
		}

		IProject handle = getProjectHandle();
		if (handle.exists()) {
			setErrorMessage("A project with that name already exists in the workspace.");
			return false;
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				getProjectNameFieldValue());
		projectLocGrp.setExistingProject(project);

		String validLocationMessage = projectLocGrp.checkValidLocation();
		if (validLocationMessage != null) { // there is no destination location given
			setErrorMessage(validLocationMessage);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	/*
	 * see @DialogPage.setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			projectNameField.setFocus();
		}
	}

	public boolean useDefaults() {
		return projectLocGrp.isDefault();
	}

	private Listener nameModifyListener = new Listener() {
		public void handleEvent(Event e) {
			setLocationForSelection();
			boolean valid = validatePage();
			setPageComplete(valid);

		}
	};
	
	private IMessageSetter getMessageHandle() {
		return new IMessageSetter(){
			/* (non-Javadoc)
			 * @see com.tibco.cep.studio.ui.wizards.WizardImportProjectCreationPage.IMessageSetter#reportError(java.lang.String, boolean)
			 */
			public void reportError(String errorMessage, boolean infoOnly) {
				if (infoOnly) {
					setMessage(errorMessage, IStatus.INFO);
					setErrorMessage(null);
				}
				else {
					setErrorMessage(errorMessage);
				}
				boolean valid = errorMessage == null;
				if(valid) {
					valid = validatePage();
				}
				setPageComplete(valid);
			}
		};
	}

	public interface IMessageSetter {
		public void reportError(String errorMessage, boolean infoOnly);
	}

	class ProjectLocationSelectionGroup {
		
		private static final String FILE_SCHEME = "file"; //$NON-NLS-1$
		private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$
		private Label locationLabel;
		private Text locationPathField;
		private Button browseButton;
		private IMessageSetter errorReporter;
		private String projectName = "";
		private String userPath = "";
		private Button useDefaultsButton;
		private IProject existingProject;

		/**
		 * @param reporter
		 * @param composite
		 */
		public ProjectLocationSelectionGroup(IMessageSetter reporter,
				Composite composite) {
			errorReporter = reporter;
			createContents(composite, true);
		}

		public void setExistingProject(IProject existingProject) {
			projectName = existingProject.getName();
			this.existingProject = existingProject;
		}


		private void createContents(Composite composite, boolean defaultEnabled) {
			int columns = 4;
			Composite projectGroup = new Composite(composite, SWT.NONE);
			GridLayout layout = new GridLayout();
			layout.numColumns = columns;
			projectGroup.setLayout(layout);
			projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			useDefaultsButton = new Button(projectGroup, SWT.CHECK | SWT.RIGHT);
			useDefaultsButton
					.setText("Use &default location");
			useDefaultsButton.setSelection(defaultEnabled);
			GridData buttonData = new GridData();
			buttonData.horizontalSpan = columns;
			useDefaultsButton.setLayoutData(buttonData);

			createUserEntryArea(projectGroup, defaultEnabled);

			useDefaultsButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					boolean useDefaults = useDefaultsButton.getSelection();

					if (useDefaults) {
						userPath = locationPathField.getText();
						locationPathField.setText(TextProcessor
								.process(getDefaultPathDisplayString()));
					} else {
						locationPathField.setText(TextProcessor.process(userPath));
					}
					String error = checkValidLocation();
					errorReporter.reportError(error,
							error != null && error.equals("Project location directory must be specified"));
					setUserAreaEnabled(!useDefaults);
				}
			});
			setUserAreaEnabled(!defaultEnabled);
		}

		public boolean isDefault() {
			return useDefaultsButton.getSelection();
		}

		private void createUserEntryArea(Composite composite, boolean defaultEnabled) {
			// location label
			locationLabel = new Label(composite, SWT.NONE);
			locationLabel
					.setText("&Location:");

			// project location entry field
			locationPathField = new Text(composite, SWT.BORDER);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			data.horizontalSpan = 2;
			locationPathField.setLayoutData(data);

			// browse button
			browseButton = new Button(composite, SWT.PUSH);
			browseButton.setText("B&rowse...");
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					handleLocationBrowseButtonPressed();
				}
			});

			if (defaultEnabled) {
				locationPathField.setText(TextProcessor
						.process(getDefaultPathDisplayString()));
			} else {
				if (existingProject == null) {
					locationPathField.setText("");
				} else {
					locationPathField.setText(TextProcessor.process(existingProject
							.getLocation().toOSString()));
				}
			}

			locationPathField.addModifyListener(new ModifyListener() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
				 */
				public void modifyText(ModifyEvent e) {
					errorReporter.reportError(checkValidLocation(), false);
				}
			});
		}

		private String getDefaultPathDisplayString() {
			URI defaultURI = null;
			if (existingProject != null) {
				defaultURI = existingProject.getLocationURI();
			}
			if (defaultURI == null || defaultURI.getScheme().equals(FILE_SCHEME)) {
				return Platform.getLocation().append(projectName).toOSString();
			}
			return defaultURI.toString();

		}

		private void setUserAreaEnabled(boolean enabled) {
			locationLabel.setEnabled(enabled);
			locationPathField.setEnabled(enabled);
			browseButton.setEnabled(enabled);
		}

		public Button getBrowseButton() {
			return browseButton;
		}

		private IDialogSettings getDialogSettings() {
			IDialogSettings ideDialogSettings = StudioUIPlugin.getDefault().getDialogSettings();
			IDialogSettings result = ideDialogSettings.getSection(getClass().getName());
			if (result == null) {
				result = ideDialogSettings.addNewSection(getClass().getName());
			}
			return result;
		}
		
		private void handleLocationBrowseButtonPressed() {

			String selectedDirectory = null;
			String dirName = getPathFromLocationField();

			if (!dirName.equals("")) {
				if (!new File(dirName).exists()){
					dirName = "";
				}
			} else {
				String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
				if (value != null) {
					dirName = value;
				}
			}
			DirectoryDialog dialog = new DirectoryDialog(locationPathField.getShell(), SWT.SHEET);
			dialog.setMessage("Select the location directory.");
			dialog.setFilterPath(dirName);
			selectedDirectory = dialog.open();
			if (selectedDirectory != null) {
				updateLocationField(selectedDirectory);
				getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
			}
		}

		private void updateLocationField(String selectedPath) {
			locationPathField.setText(TextProcessor.process(selectedPath));
		}

		private String getPathFromLocationField() {
			URI fieldURI;
			try {
				fieldURI = new URI(locationPathField.getText());
			} catch (URISyntaxException e) {
				return locationPathField.getText();
			}
			String path= fieldURI.getPath();
			return path != null ? path : locationPathField.getText();
		}

		public String checkValidLocation() {

			String locationFieldContents = locationPathField.getText();
			if (locationFieldContents.length() == 0) {
				return "Project location directory must be specified";
			}

			IPath path = new Path(locationFieldContents);
			int count = path.segmentCount();
			path = path.uptoSegment(count-1);
			File file = path.toFile();
			if (!file.exists()) {
				return "Invalid location path";
			}

			URI newPath = getProjectLocationURI();
			if (existingProject != null) {
				URI projectPath = existingProject.getLocationURI();
				if (projectPath != null && URIUtil.sameURI(projectPath, newPath)) {
					return "Location is the current location";
				}
			}

			if (!isDefault()) {
				IStatus locationStatus = ResourcesPlugin.getWorkspace()
						.validateProjectLocationURI(existingProject, newPath);

				if (!locationStatus.isOK()) {
					return locationStatus.getMessage();
				}
			}

			return null;
		}

		public URI getProjectLocationURI() {
			return new File(locationPathField.getText()).toURI();
		}

		public void updateProjectName(String newName) {
			projectName = newName;
			if (isDefault()) {
				locationPathField.setText(TextProcessor
						.process(getDefaultPathDisplayString()));
			}
		}

		public String getProjectLocation() {
			if (isDefault()) {
				return Platform.getLocation().toOSString();
			}
			return locationPathField.getText();
		}
	}
}