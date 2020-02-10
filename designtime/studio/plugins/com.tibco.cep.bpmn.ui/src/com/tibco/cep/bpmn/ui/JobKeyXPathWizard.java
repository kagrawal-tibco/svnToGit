package com.tibco.cep.bpmn.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * 
 * @author majha
 *
 */
public class JobKeyXPathWizard extends ProcessXPathWizard {
	

	/**
	 * @param project
	 * @param wrap
	 * @param processXPathValidate
	 */
	public JobKeyXPathWizard(IProject project, 
			EObjectWrapper<EClass, EObject> wrap, 
			IProcessXPathValidate processXPathValidate) {
		super(project, null, processXPathValidate, wrap, null);
	}

	public ProcessXPathWizardPage getMapperPage() {
		return fXPathPage;
	}

	@Override
	public void addPages() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(objectWrap);
		String xpath = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
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