package com.tibco.cep.bpmn.ui.wizards;

import java.io.File;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.ui.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class BpmnOpenProjectWizardPage extends WizardPage {

	private Text filePathText;
	private Text graphNameText;
	private Group composite;

	protected BpmnOpenProjectWizardPage() {
		super(Messages.getString("OPEN_XPDL_WIZARD_WINDOW_TITLE"));
		setTitle(Messages.getString("OPEN_XPDL_WIZARD_WINDOW_TITLE"));
		setDescription(Messages.getString("OPEN_XPDL_WIZARD_PAGE_DESC"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		setPageComplete(false);
		composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		Label locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setLayoutData(new GridData(SWT.CENTER));
		locationLabel.setText(Messages.getString("OPEN_XPDL_WIZARD_PAGE_GRAPH_LOCATION"));

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
				fileDialog.setText(Messages.getString("OPEN_XPDL_FILE_DIALOG"));
				fileDialog.setFilterExtensions(extensions);
				String fullFilename = fileDialog.open();
				if (fullFilename != null) {
					File fl = new File(fullFilename);
					String dgname = fl.getName().substring(0,fl.getName().indexOf(".xpdl"));
					filePathText.setText(fullFilename);
					graphNameText.setText(dgname);
				}
			}
		});

		Label graphNameLabel = new Label(composite, SWT.NONE);
		graphNameLabel.setText(Messages.getString("OPEN_XPDL_WIZARD_PAGE_GRAPH_NAME"));

		graphNameText = new Text(composite, SWT.BORDER | SWT.MULTI);
		graphNameText.setEditable(false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		graphNameText.setLayoutData(gd);
		setControl(composite);
	}

	private void validatePage() {
		String fileName = filePathText.getText();
		if ((fileName != null && fileName.trim().length() > 0)) {
			File file = new File(fileName);
			if (file.exists() && file.getName().endsWith(".xpdl")) {
				setMessage(Messages.getString("OPEN_XPDL_WIZARD_PAGE_DESC"), NONE);
				setPageComplete(true);

			} else {
				setMessage(Messages.getString("OPEN_XPDL_WIZARD_PAGE_ERROR"), ERROR);
				setPageComplete(false);
			}

		} else {
			setMessage(Messages.getString("OPEN_XPDL_WIZARD_PAGE_ERROR"), NONE);
			setPageComplete(false);
		}
	}

	public Text getFilePathText() {
		return filePathText;
	}

	public Text getGraphNameText() {
		return graphNameText;
	}
}
