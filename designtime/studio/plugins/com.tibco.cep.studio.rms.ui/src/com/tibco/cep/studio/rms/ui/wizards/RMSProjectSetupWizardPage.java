package com.tibco.cep.studio.rms.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author smarathe
 *
 */
public class RMSProjectSetupWizardPage extends WizardPage {

	
	Label projectNameLabel, sourceLocationLabel, baseLocationLabel, aclFilePathLabel;
	Text projectNameText, sourceLocationText, baseLocationText, aclFilePathText;
	Button sourceLocationBrowseButton, baseLocationBrowseButton, aclFilePathBrowseButton;
	Composite projectNameComposite, sourceLocationComposite, baseLocationComposite, aclFilePathComposite;
	GridLayout gridLayout;
	RowData labelLayoutData, textLayoutData, buttonLayoutData;

	protected RMSProjectSetupWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setPageComplete(false);
		
	}

	public void createControl(Composite parent) {
		
		parent.setLayout(new GridLayout(1, true));
		createProjectNameControls(parent);
		createSourceLocationControls(parent);
		createBaseLocationControls(parent);
		createACLFilePathControls(parent);
		setControl(parent);
	}
	

	private void createACLFilePathControls(Composite parent) {
		aclFilePathComposite = new Composite(parent, SWT.NONE);
		aclFilePathComposite.setLayout(new GridLayout(3, false));
		aclFilePathLabel = new Label(aclFilePathComposite, SWT.NONE);
		aclFilePathLabel.setText("ACL File Location  ");
		aclFilePathLabel.setData(labelLayoutData);
		aclFilePathText = new Text(aclFilePathComposite, SWT.BORDER | SWT.SINGLE);
		aclFilePathText.setData(textLayoutData);
		aclFilePathBrowseButton = new Button(aclFilePathComposite, SWT.PUSH);
		aclFilePathBrowseButton.setText("Browse...");
		aclFilePathBrowseButton.setData(buttonLayoutData);
		aclFilePathBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(getShell());
				dlg.setFilterExtensions(new String[] {".acl"});
				String dir = dlg.open();
				if (dir != null) {
					aclFilePathText.setText(dir);
				}

			}
			
		});
	}

	private void createBaseLocationControls(Composite parent) {
		baseLocationComposite = new Composite(parent, SWT.NONE);
		baseLocationComposite.setLayout(new GridLayout(3, false));
		baseLocationLabel = new Label(baseLocationComposite, SWT.NONE);
		baseLocationLabel.setText("Base Location       ");
		baseLocationLabel.setData(labelLayoutData);
		baseLocationText = new Text(baseLocationComposite, SWT.BORDER | SWT.SINGLE);
		baseLocationText.setData(textLayoutData);
		baseLocationBrowseButton = new Button(baseLocationComposite, SWT.PUSH);
		baseLocationBrowseButton.setText("Browse...");
		baseLocationBrowseButton.setData(buttonLayoutData);
		baseLocationBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setMessage("Select a directory");
				String dir = dlg.open();
				if (dir != null) {
					baseLocationText.setText(dir);
				}
			}
		});
	}

	private void createSourceLocationControls(Composite parent) {
		sourceLocationComposite = new Composite(parent, SWT.NONE);
		sourceLocationComposite.setLayout(new GridLayout(3, false));
		sourceLocationLabel = new Label(sourceLocationComposite, SWT.NONE);
		sourceLocationLabel.setText("Source Location    ");
		sourceLocationLabel.setData(labelLayoutData);
		sourceLocationText = new Text(sourceLocationComposite, SWT.BORDER | SWT.SINGLE);
		sourceLocationText.setData(textLayoutData);
		sourceLocationBrowseButton = new Button(sourceLocationComposite, SWT.PUSH);
		sourceLocationBrowseButton.setText("Browse...");
		sourceLocationBrowseButton.setData(buttonLayoutData);
		sourceLocationBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setMessage("Select a directory");
				String dir = dlg.open();
				if (dir != null) {
					sourceLocationText.setText(dir);
				}
			}
		});
		sourceLocationText.setText(((RMSProjectSetupWizard)this.getWizard()).getProject().getLocation().toOSString());
		
	}

	private void createProjectNameControls(Composite parent) {
		projectNameComposite = new Composite(parent, SWT.NONE);
		projectNameComposite.setLayout(new GridLayout(2, false));
		projectNameLabel = new Label(projectNameComposite, SWT.NONE);
		projectNameLabel.setText("Project Name        ");
		projectNameLabel.setData(labelLayoutData);
		projectNameText = new Text(projectNameComposite, SWT.BORDER | SWT.SINGLE);
		projectNameText.setData(textLayoutData);
		projectNameText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(projectNameText.getText() != null) {
					setPageComplete(true);
				}
				
			}
			
		});
		projectNameText.setText(((RMSProjectSetupWizard)this.getWizard()).getProject().getName());
		
	}
	
	public String getProjectName() {
		return projectNameText.getText();
	}
	
	public String getSourceLocation() {
		return sourceLocationText.getText();
	}
	
	public String getBaseLocation() {
		return baseLocationText.getText();
	}
	
	public String getACLFilePath() {
		return aclFilePathText.getText();
	}
}
