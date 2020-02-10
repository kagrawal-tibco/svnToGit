package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.core.util.CoherenceFunctionMigrator;

public class MigrateCoherenceFunctionsRefactoringPage extends UserInputWizardPage {

	protected MigrateCoherenceFunctionsRefactoringPage(String name) {
		super(name);
	}

	@Override
	protected Refactoring getRefactoring() {
		return super.getRefactoring();
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Migrate Coherence Function Calls");
		String[][] arr = CoherenceFunctionMigrator.fCoherenceFunctionMappings;
		StringBuffer buffer = new StringBuffer();
		for (String[] mapping : arr) {
			String cFunction = mapping[0];
			String asFunction = mapping[1];
			buffer.append(cFunction.substring(0, cFunction.length()-1));
			buffer.append("  -->  ");
			buffer.append(asFunction.substring(0, asFunction.length()-1));
			buffer.append('\n');
		}

		Group group = new Group(parent, SWT.NULL);
		group.setText("The following functions will be renamed:");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		group.setLayout(layout);
		Label label = new Label(group, SWT.NULL);
		label.setText(buffer.toString());
		setControl(group);
		setPageComplete(true);
	}
	
}
