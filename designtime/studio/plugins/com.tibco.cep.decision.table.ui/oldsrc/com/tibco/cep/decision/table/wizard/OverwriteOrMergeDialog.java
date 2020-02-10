package com.tibco.cep.decision.table.wizard;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.decision.table.ui.utils.Messages;

public class OverwriteOrMergeDialog extends IconAndMessageDialog {

	private int fReturnCode = -1;
	private Button fOverwriteButton;
	private Button fManualMergeButton;
	private Button fAutoMergeButton;
	
	public OverwriteOrMergeDialog(Shell parentShell) {
		super(parentShell);
	}


	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Import");
	}


	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label questionImage = new Label(composite, SWT.NULL);
		questionImage.setImage(getImage());
		Label existingTableLabel = new Label(composite, SWT.NULL);
		existingTableLabel.setText(Messages.getString("ImportExcel_decisionTable_exists_question"));
		
		SelectionListener listener = new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fOverwriteButton.getSelection()) {
					fReturnCode = ImportDecisionTableWizard.OPTION_OVERWRITE;
				} else if (fManualMergeButton.getSelection()) {
					fReturnCode = ImportDecisionTableWizard.OPTION_MERGE_MANUAL;
				} else if (fAutoMergeButton.getSelection()) {
					fReturnCode = ImportDecisionTableWizard.OPTION_MERGE_AUTO;
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		};
		
		Composite buttonComposite = new Composite(composite, SWT.NULL);
		buttonComposite.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		buttonComposite.setLayoutData(data);

		fOverwriteButton = new Button(buttonComposite, SWT.RADIO);
		fOverwriteButton.setText(Messages.getString("ImportExcel_decisionTable_exists_option_overwrite"));
		fOverwriteButton.addSelectionListener(listener);
		
		fManualMergeButton = new Button(buttonComposite, SWT.RADIO);
		fManualMergeButton.setText(Messages.getString("ImportExcel_decisionTable_exists_option_merge_manual"));
		fManualMergeButton.addSelectionListener(listener);
		
		fAutoMergeButton = new Button(buttonComposite, SWT.RADIO);
		fAutoMergeButton.setText(Messages.getString("ImportExcel_decisionTable_exists_option_merge_auto"));
		fAutoMergeButton.addSelectionListener(listener);
		
		return composite;
	}

	@Override
	public int getReturnCode() {
		if (super.getReturnCode() == OK) {
			return fReturnCode;
		}
		return -1;
	}

	@Override
	protected Image getImage() {
		return getQuestionImage();
	}

	
	
}
