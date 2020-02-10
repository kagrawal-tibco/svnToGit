package com.tibco.cep.bpmn.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class SubProcessWizard extends Wizard {
	
	protected SubProcessWizardPage fSubProcessPage;
	protected IProject project;
	protected EObjectWrapper<EClass, EObject> process;
	
	/**
	 * @param project
	 * @param process
	 */
	public SubProcessWizard(IProject project, EObjectWrapper<EClass, EObject> process) {
		setWindowTitle(Messages.getString("new.subprocess.wizard.title"));
		this.project = project;
		this.process = process;
	}

	public SubProcessWizardPage getMapperPage() {
		return fSubProcessPage;
	}

	@Override
	public void addPages() {
		fSubProcessPage = new SubProcessWizardPage("", project, process);
		addPage(fSubProcessPage);
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	public String getSubProcessName() {
		String name = fSubProcessPage.getSubProcessName();
		return name.isEmpty() ? "Sub_Process" : name;
	}
}