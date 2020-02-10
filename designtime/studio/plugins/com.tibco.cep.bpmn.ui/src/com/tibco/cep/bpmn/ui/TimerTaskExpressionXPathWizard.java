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
public class TimerTaskExpressionXPathWizard extends ProcessXPathWizard {
	
	/**
	 * @param project
	 * @param userInstance
	 * @param process
	 * @param xpath
	 */
	public TimerTaskExpressionXPathWizard(IProject project, 
				                      EObjectWrapper<EClass, EObject> wrap, 
				                      EObjectWrapper<EClass, EObject> process, 
				                      IProcessXPathValidate processXPathValidate) {
		super(project, null, processXPathValidate, wrap, process);
	}

	public ProcessXPathWizardPage getMapperPage() {
		return fXPathPage;
	}

	@Override
	public void addPages() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(objectWrap);
		String xpath = "";
		EObject attribute = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
		if(attribute != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(attribute);
			xpath = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
			if(xpath == null)
				xpath = "";
		}
		fXPathPage = new ProcessXPathWizardPage("", project, processXPathValidate, process, xpath);
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