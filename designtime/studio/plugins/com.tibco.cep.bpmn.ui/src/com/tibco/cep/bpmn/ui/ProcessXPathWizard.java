package com.tibco.cep.bpmn.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessXPathWizard extends Wizard {
	
	protected ProcessXPathWizardPage fXPathPage;
	protected IProject project;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> objectWrap;
	protected Text textField;
	protected IProcessXPathValidate processXPathValidate;
	private String[] loopArgs;
	
	/**
	 * @param project
	 * @param objectWrap
	 * @param process
	 * @param xpath
	 */
	public ProcessXPathWizard(IProject project, 
							  Text textField, 
							  IProcessXPathValidate processXPathValidate,
							  EObjectWrapper<EClass, EObject> objectWrap,
							  EObjectWrapper<EClass, EObject> process, String...args) {
		setWindowTitle(com.tibco.cep.studio.ui.xml.utils.Messages.getString("xpath.window.title"));
		this.project = project;
		this.objectWrap = objectWrap;
		this.process = process;
		this.textField = textField;
		this.processXPathValidate = processXPathValidate;
		this.loopArgs = args;
	}

	public ProcessXPathWizardPage getMapperPage() {
		return fXPathPage;
	}

	@Override
	public void addPages() {
		String xpath = textField.getText().isEmpty()? null : textField.getText();
		fXPathPage = new ProcessXPathWizardPage("", project, processXPathValidate, process, xpath, loopArgs);
		addPage(fXPathPage);
	}

	@Override
	public boolean performCancel() {
		return super.performCancel();
		// return true;
	}

	@Override
	public boolean performFinish() {
		fXPathPage.setDefaultMessage();
		String xpString = fXPathPage.getXPath(); 
		if (fXPathPage.getErrors() != null) {
			MessageDialog.openError(getContainer().getShell(), BpmnMessages.getString("processXpathWizard_error_title"), fXPathPage.getErrors());
			fXPathPage.setErrorMessage(fXPathPage.getErrors());
			return false;
		}
		if(xpString != null && !xpString.trim().isEmpty()){
			if (!fXPathPage.isValid()) {
				MessageDialog.openError(getContainer().getShell(), BpmnMessages.getString("processXpathWizard_error_title"), 
																			BpmnMessages.getString("processXpathWizard_error_message")+
																									processXPathValidate.getExpectedType() + ".");
				return false;
			}
		}
		
		if (xpString != null && textField != null) {
			textField.setText(xpString);
		}
		if(xpString == null && textField != null) {
			textField.setText("");
		}
		return true;
	}


	@Override
	public boolean canFinish() {
		return super.canFinish();
	}
}