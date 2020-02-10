package com.tibco.cep.bpmn.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * 
 * @author majha
 *
 */
public class VrfImplDeployedXPathWizard extends ProcessXPathWizard {
	
	private EObjectWrapper<EClass, EObject> vrfImpl;

	/**
	 * @param project
	 * @param vrfImpl
	 * @param processXPathValidate
	 * @param objectWrap
	 */
	public VrfImplDeployedXPathWizard(IProject project, 
										EObjectWrapper<EClass, EObject> vrfImpl, 
										IProcessXPathValidate processXPathValidate,
										EObjectWrapper<EClass, EObject> objectWrap) {

		super(project, null, processXPathValidate, objectWrap, null);
		this.vrfImpl = vrfImpl;
	}

	public ProcessXPathWizardPage getMapperPage() {
		return fXPathPage;
	}

	@Override
	public void addPages() {
		String xpath = vrfImpl.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
		fXPathPage = new ProcessXPathWizardPage("", project, processXPathValidate, objectWrap, xpath);
		addPage(fXPathPage);
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
		boolean status = super.performFinish();
		if (status) {
			String xpString = fXPathPage.getXPath(); 
			if (xpString != null) {
				vrfImpl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, xpString);
			}else{
				vrfImpl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, "");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}
}