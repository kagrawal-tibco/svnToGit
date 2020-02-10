package com.tibco.cep.bpmn.ui.xpdl.wizard;


import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;
import com.tibco.cep.studio.ui.wizards.StudioResourceContainer;



/**
 * 
 * @author majha
 *
 */
public class XpdlImportWizardPage extends StudioNewFileWizardPage {

	private Text filePathText;
	private Composite composite;
	private String projName;

	public XpdlImportWizardPage(IStructuredSelection selection, String projectName) {
		super(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_TITLE"), selection);
//		setTitle(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_TITLE"));
		setDescription(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_DESC"));
		projName = projectName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		setPageComplete(false);
	

//		Label graphNameLabel = new Label(composite, SWT.NONE);
//		graphNameLabel.setText(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_GRAPH_NAME"));
//
//		processNameText = new Text(composite, SWT.BORDER | SWT.MULTI);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		processNameText.setLayoutData(gd);
//		processNameText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				validateProcessText();
//			}
//		});
//		setControl(parent);
//		composite.moveAbove(null);
	}
	
	@Override
	protected void createAdvancedControls(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setLayoutData(new GridData(SWT.CENTER));
		locationLabel.setText(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_GRAPH_LOCATION"));

		filePathText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		filePathText.setLayoutData(gd);
		filePathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		Button button = new Button(composite, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(),	SWT.OPEN);
				String[] extensions = { "*.xpdl" };
				fileDialog.setText(Messages.getString("IMPORT_XPDL_FILE_DIALOG"));
				fileDialog.setFilterExtensions(extensions);
				String fullFilename = fileDialog.open();
				if (fullFilename != null) {
					File fl = new File(fullFilename);
					@SuppressWarnings("unused")
					String dgname = fl.getName().substring(0,fl.getName().indexOf(".xpdl"));
					filePathText.setText(fullFilename);
				}
			}
		});
		
		composite.moveAbove(null);
	}

	protected boolean validatePage() {
		boolean valid = true;
		if(filePathText != null){
			String fileName = filePathText.getText();
			if ((fileName != null && fileName.trim().length() > 0)) {
				File file = new File(fileName);
				if (file.exists() && file.getName().endsWith(".xpdl")) {
					setMessage(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_DESC"), NONE);
					setPageComplete(true);

				} else {
					setMessage(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_ERROR"), ERROR);
					setPageComplete(false);
					valid = false;
				}

			} else {
				setMessage(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_ERROR"), ERROR);
				setPageComplete(false);
				valid = false;
			}
		}
		
		
//		if(valid)
//			valid = super.validatePage();
		
//		if(valid){
//			if (!EntityNameHelper.isValidBEEntityIdentifier(resourceGroup.getResourceName())) {
//				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_Resource_invalidFilename", resourceGroup.getResourceName());
//				setErrorMessage(problemMessage);
//				valid = false;
//			}
//		
//		}
//		
//		if(valid){
//			StringBuilder duplicateFileName  = new StringBuilder("");
//			IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
//			if (isDuplicateBEResource(resource.getProject(),resourceGroup.getResourceName(),duplicateFileName)) {
//				String problemMessage = Messages.getString("BE_Resource_FilenameExists", duplicateFileName ,resourceGroup.getResourceName());
//				setErrorMessage(problemMessage);
//				valid =  false;
//			}
//		}
		return valid;
	}
	
	protected void createResourceContainer(Composite container) {
		resourceContainer = new StudioResourceContainer(container, this,
				getNewFileLabel(),
				"file", false,
				CONTAINER_HEIGHT, projName);
		resourceContainer.setAllowExistingResources(true);
		init();
		createAdvancedControls(container);
		if (fileName != null) {
			resourceContainer.setResourceName(fileName);
		}
		if (fileExtension != null) {
			resourceContainer.setResourceExtension(fileExtension);
		}
	}
	
	protected boolean isDuplicateBEResource(IResource resource,
			String resourceName, StringBuilder duplicateFileName) {
		return StudioResourceUtils.isDuplicateBEResource(resource, resourceContainer.getResourceName(),duplicateFileName);
	}
	


	public String getXpdlFileFullPath() {
		return filePathText.getText();
	}
	
	@Override
	protected String getNewFileLabel() {
		return null;
	}


}
