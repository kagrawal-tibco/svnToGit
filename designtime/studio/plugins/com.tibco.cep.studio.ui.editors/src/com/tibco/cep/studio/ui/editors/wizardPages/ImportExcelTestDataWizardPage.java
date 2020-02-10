/**
 * 
 */
package com.tibco.cep.studio.ui.editors.wizardPages;

import java.io.File;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
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

import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author mgujrath
 *
 */
public class ImportExcelTestDataWizardPage extends WizardPage implements ITestDataSourceWizardPage {
	

	
	private Text fileLocationText;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */

	public ImportExcelTestDataWizardPage(String pageName) {
		super(pageName);
		setTitle(Messages.getString("ImportTestDataWizard.page.Title"));
		setDescription(Messages.getString("ImportTestDataWizard.Desc"));
	}

	public Text getFileLocationText() {
		return fileLocationText;
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return validatePage();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.wizardPages.IDomainSourceWizardPage#getDataSource()
	 */
	@SuppressWarnings("unchecked")
	public String getDataSource() {
		return fileLocationText.getText();
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = 0;
		label.setLayoutData(gData);
		label.setText(Messages.getString("import.domain.file.select"));
		
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		fileLocationText = new Text(childContainer, SWT.BORDER);
		GridData fileLocationTextData = new GridData(GridData.FILL_HORIZONTAL);
		fileLocationTextData.widthHint = 150;
		fileLocationText.setLayoutData(fileLocationTextData);
		fileLocationText.addModifyListener(new ModifyListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		
		Button button = new Button(childContainer, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				String[] extensions = { "*.xls", "*.prr" };
				fd.setText(Messages.getString("import.domain.filedialog.title"));
				fd.setFilterExtensions(extensions);
				if (fileLocationText.getText() != null) {
					fd.setFileName(fileLocationText.getText());
				}
				String fileName = fd.open();
				if (fileName != null) {
					fileLocationText.setText(fileName);
				}
			}
		});

		validatePage();
		setErrorMessage(null);
		setMessage(null);

		setControl(container);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		if (fileLocationText == null) {
			return false;
		}
		String fullFileName = fileLocationText.getText();
		if (fullFileName == null) {
			return false;
		}
		if ((!fullFileName.endsWith(".xls"))) {
			setErrorMessage(Messages.getString("ImportDomainWizard.error"));
			return false;
		}
		File excelFile = new File(fullFileName);
		if (excelFile.isDirectory() || !excelFile.canRead()) {
			setErrorMessage(Messages.getString("ImportDomainWizard.Desc.Excel.ExcelError"));
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public String getProjectName() {
		return null;
	}

	@Override
	public void setProjectName(String projectName) {
	}


}
