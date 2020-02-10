package com.tibco.cep.studio.wizard.ftl.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;


public class ProjectSelectionErrorPage extends FTLWizardPage {

	public ProjectSelectionErrorPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.projectSelectionError.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.projectSelectionError.description"));
		setPageComplete(false);
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();

		layout.numColumns = 1;

		container.setLayout(layout);

		Label label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("ftl.wizard.page.projectSelectionError.content"));

		setControl(container);
	}
	
}
