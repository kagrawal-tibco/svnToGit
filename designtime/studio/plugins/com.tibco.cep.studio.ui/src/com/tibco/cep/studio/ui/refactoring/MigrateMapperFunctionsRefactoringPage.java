package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.Messages;

public class MigrateMapperFunctionsRefactoringPage extends UserInputWizardPage {

	protected MigrateMapperFunctionsRefactoringPage(String name) {
		super(name);
	}

	@Override
	protected Refactoring getRefactoring() {
		return super.getRefactoring();
	}

	@Override
	public void createControl(Composite parent) {
		setTitle(Messages.getString("mapper.function.migration.wizard.title"));
		
		Group group = new Group(parent, SWT.WRAP);
		group.setText("The following actions will be performed:");
		GridLayout layout = new GridLayout(1, false);
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.minimumHeight= 500;
		gd.heightHint = 500;
		gd.widthHint = 250;
		group.setLayoutData(gd);
		group.setBackground(ColorConstants.white);
		Label label = new Label(group, SWT.WRAP);
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.widthHint = 250;
		gd2.heightHint = 200;
		label.setLayoutData(gd2);
		label.setText(Messages.getString("mapper.function.migration.wizard.desc"));
		
		final Button updateVersionBtn = new Button(group, SWT.CHECK);
		updateVersionBtn.setText("Update all XSLT versions from 1.0 to 2.0\n(optional, the mapper will update the version automatically if you modify any existing mapping)");
		updateVersionBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean updateVersion = updateVersionBtn.getSelection();
				((MigrateMapperFunctionsRefactoringWizard)getRefactoringWizard()).setUpdateVersions(updateVersion);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		setControl(group);
		setPageComplete(true);
	}
	
}
