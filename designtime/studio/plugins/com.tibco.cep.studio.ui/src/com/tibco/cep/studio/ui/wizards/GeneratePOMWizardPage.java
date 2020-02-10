package com.tibco.cep.studio.ui.wizards;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.widgets.StudioWizardPage;

/**
 * 
 * @author pdeokar
 * 
 */
public class GeneratePOMWizardPage extends StudioWizardPage {

	private Composite archiveDetails;
	private SashForm sashForm;
	/*
	 * private CTabItem functionsitem; private CTabItem objectMgmtItem;
	 */
	private Text groupId;
	private Text artifactId;
	private Text pomVersion;
	private String projectName;
	private boolean isDirty = false;
	private Button applyButton;
	private Button resetButton;
	private WidgetListener widgetListner;

	protected GeneratePOMWizardPage(String projectName) {
		super("Generate POM file: " + projectName);
		this.projectName = projectName;
		setTitle("Generate POM File: "+projectName);
		setDescription("Create and manage POM file configurations");
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



		Composite pomConfigurationComposite = new Composite(archiveDetails, SWT.NONE);
		pomConfigurationComposite.setLayout(new GridLayout(2, false));
		pomConfigurationComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		new Label(pomConfigurationComposite, SWT.NONE).setText(Messages
				.getString("Generate.POM.GroupId"));

		groupId = new Text(pomConfigurationComposite, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		groupId.setLayoutData(gd);
		groupId.addModifyListener(this.widgetListner);

		new Label(pomConfigurationComposite, SWT.NONE).setText(Messages
				.getString("Generate.POM.ArtifactId"));
		artifactId = new Text(pomConfigurationComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		artifactId.setLayoutData(gd);
		artifactId.addModifyListener(this.widgetListner);
		
		new Label(pomConfigurationComposite, SWT.NONE).setText(Messages
				.getString("Generate.POM.Version"));
		pomVersion = new Text(pomConfigurationComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 40;
		pomVersion.setLayoutData(gd);
		pomVersion.addModifyListener(this.widgetListner);
		
		createButtonBar(archiveDetails);
		
		if (projectName != null) {
			loadDefaultData();
		} else {
			setErrorMessage(Messages.getString("Build.EAR.invalidProjectName"));
			setPageComplete(false);
		}

		// sashForm.setWeights(new int[]{30,70});
		sashForm.setWeights(new int[] { 100 });
		setControl(sashForm);
	}

	private void loadDefaultData() {
		groupId.setText("com.tibco.be");
		artifactId.setText(projectName);
		pomVersion.setText("1.0.0");
		setPageComplete(true);
		validatePage();
	}
	public String getGroupId(){
		return groupId.getText();
	}
	public String getArtifactId(){
		return artifactId.getText();
	}
	public String getVerison(){
		return pomVersion.getText();
	}
	private void validatePage() {
		boolean isValid = validateFields();
		if (isDirty) {
			applyButton.setEnabled(true);
			resetButton.setEnabled(true);
			setPageComplete(false);
			setErrorMessage(Messages.getString("Generate.POM.doApply"));
			return;
		} else {
			applyButton.setEnabled(false);
			resetButton.setEnabled(false);
		}
		if(isValid){
			setErrorMessage(null);
			setPageComplete(true);
		}
		else
			setPageComplete(false);
		if (!isValid) {
			applyButton.setEnabled(false);
			return;
		}
		
	}
	private boolean validateFields() {
		if (groupId.getText() == null
				|| groupId.getText().trim().isEmpty()) {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Generate.POM.invalidGroupId"));
			return false;
		}
		if (artifactId.getText() == null
				|| artifactId.getText().trim().isEmpty()) {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Generate.POM.invalidArtifactId"));
			return false;
		}
		if (pomVersion.getText() == null
				|| pomVersion.getText().trim().isEmpty()) {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Generate.POM.invalidVersion"));
			return false;
		}
		return true;
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

	public class WidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if(source == groupId) {
				isDirty = true;
				validatePage();
			} else if(source == artifactId) {
				isDirty = true;
				validatePage();
			} else if(source == pomVersion) {
				isDirty = true;
				validatePage();
			} 			
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if(source == applyButton) {
				try {
					isDirty = false;
					validatePage();
				} catch (Exception e1) {
					StudioUIPlugin.log(e1);
				}
			} else if( source == resetButton) {
				loadDefaultData();
				isDirty = false;
			}
		}
	}

	@Override
	public void performGlobalVariable(String projectName) {
		// TODO Auto-generated method stub
		
	}
	
}