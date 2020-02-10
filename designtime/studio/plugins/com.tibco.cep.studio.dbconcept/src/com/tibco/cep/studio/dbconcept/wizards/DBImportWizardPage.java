package com.tibco.cep.studio.dbconcept.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/*
@author ssailapp
@date Aug 12, 2009 1:53:24 PM
 */

public class DBImportWizardPage extends WizardPage {
	
	private IStructuredSelection selection;
	private Combo cProjName;
	
	protected DBImportWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		this.selection = selection;
		setTitle(pageName);
		setDescription("DB Import description");
	}

	/*
	protected DBImportWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}
	*/

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Group group = new Group(composite, SWT.NULL);
		group.setText("");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		cProjName = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
		GridData data  = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 250;
		cProjName.setLayoutData(data);
		cProjName.setFont(parent.getFont());
		fillProjectsList();

		//TODO - edit controls
		
		setControl(composite);
	}
	
	public String getProjectName() {
		return cProjName.getText();
	}

	private void fillProjectsList() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		for (IProject project : projects) {
			cProjName.add(project.getName());
		}
		if (selection != null) {
			Object o = selection.getFirstElement();
			if (o instanceof IProject) {
				IProject project = (IProject)o;
				cProjName.setText(project.getName());
			}
		}
	}
	
}
