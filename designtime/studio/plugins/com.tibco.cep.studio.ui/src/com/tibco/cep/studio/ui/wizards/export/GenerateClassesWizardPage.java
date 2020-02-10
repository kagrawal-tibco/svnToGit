package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
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
public class GenerateClassesWizardPage extends WizardPage {

	DirectoryDialog dialog;
	Label outputDirectoryLabel, extendedClassPathLabel;
	Text outputDirectoryText, extendedClassPathText;
	Button overwriteExistingClassesButton, outputDirectoryBrowseButton, extendedClassPathBrowseButton, addExtendedClassPathButton;
	RowLayout rowLayout;
	RowData labelLayoutData, textLayoutData, buttonLayoutData;
	Composite outputDirectoryComposite, overwriteExistingClassesComposite, extendedClassPathComposite;
	
	protected GenerateClassesWizardPage(String pageName) {
		super(pageName);
		setTitle(getName());
		setPageComplete(false);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createOutputDirectoryControls(composite);
		createOverwriteExistingClassesControls(composite);
		createExtendedClassPath(composite);
		setControl(composite);
	}
	

	
	private void createExtendedClassPath(Composite parent) {
		extendedClassPathComposite = new Composite(parent, SWT.NONE);
		extendedClassPathComposite.setLayout(new GridLayout(3, false));
		extendedClassPathLabel = new Label(extendedClassPathComposite, SWT.NONE);
		extendedClassPathLabel.setText("Jar to be added in classpath  ");
		extendedClassPathLabel.setData(labelLayoutData);
		extendedClassPathText = new Text(extendedClassPathComposite, SWT.BORDER | SWT.SINGLE);
		extendedClassPathText.setData(textLayoutData);
		extendedClassPathBrowseButton = new Button(extendedClassPathComposite, SWT.PUSH);
		extendedClassPathBrowseButton.setText("Browse...");
		extendedClassPathBrowseButton.setData(buttonLayoutData);
		extendedClassPathBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(getShell());
				dlg.setFilterExtensions(new String[] {"*.jar"});
				String dir = dlg.open();
				if (dir != null) {
					extendedClassPathText.setText(extendedClassPathText.getText().equals("")?dir:extendedClassPathText.getText()+";"+dir);
				}

			}
			
		});
		
	}

	private void createOverwriteExistingClassesControls(Composite parent) {
		overwriteExistingClassesComposite = new Composite(parent, SWT.NONE);
		overwriteExistingClassesComposite.setLayout(new GridLayout(1, false));
		overwriteExistingClassesButton = new Button(parent, SWT.CHECK);
		overwriteExistingClassesButton.setText("Overwrite Existing Classes");
		overwriteExistingClassesButton.setData(buttonLayoutData);
	}

	private void createOutputDirectoryControls(Composite parent) {
		outputDirectoryComposite = new Composite(parent, SWT.NONE);
		outputDirectoryComposite.setLayout(new GridLayout(3, false));
		outputDirectoryLabel = new Label(outputDirectoryComposite, SWT.NONE);
		outputDirectoryLabel.setText("Output Directory  ");
		outputDirectoryLabel.setData(labelLayoutData);
		outputDirectoryText = new Text(outputDirectoryComposite, SWT.BORDER | SWT.SINGLE);
		outputDirectoryText.setData(textLayoutData);
		outputDirectoryBrowseButton = new Button(outputDirectoryComposite, SWT.PUSH);
		outputDirectoryBrowseButton.setText("Browse...");
		outputDirectoryBrowseButton.setData(buttonLayoutData);
		outputDirectoryBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				String dir = dlg.open();
				if (dir != null) {
					outputDirectoryText.setText(dir);
				}

			}
			
		});
		
	}

	
	public String getOutputDirectory() {
		return outputDirectoryText.getText();
	}
	
	public String getOverWriteExistingClasses() {
		return overwriteExistingClassesButton.getSelection()?"true":"false";
	}
	
	public String getExtendedClasspath() {
		return extendedClassPathText.getText();
	}

}
