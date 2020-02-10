package com.tibco.cep.bpmn.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * 
 * @author majha
 *
 */
public class ActivationExprXPathWizard extends ProcessXPathWizard {
	
	/**
	 * @param project
	 * @param wrap
	 * @param processXPathValidate
	 */
	public ActivationExprXPathWizard(IProject project, 
									 EObjectWrapper<EClass, EObject> wrap, 
									 IProcessXPathValidate processXPathValidate) {
		super(project, null, processXPathValidate, wrap, null);
	}

	public ProcessXPathWizardPage getMapperPage() {
		return fXPathPage;
	}

	@Override
	public void addPages() {
		String xpath = objectWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION);
		fXPathPage = new ProcessXPathWizardPage("", project, processXPathValidate, objectWrap, xpath);
		addPage(fXPathPage);
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
		return super.performFinish();
	}
	
	public String getXPath(){
		String xPath = fXPathPage.getXPath();
		if(xPath == null)
			xPath = "";
		return xPath;
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}
}