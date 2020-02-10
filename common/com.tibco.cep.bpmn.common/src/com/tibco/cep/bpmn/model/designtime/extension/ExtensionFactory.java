package com.tibco.cep.bpmn.model.designtime.extension;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class ExtensionFactory {
	
	
	public static EObjectWrapper<EClass, EObject> createExtension(EObjectWrapper<EClass, EObject> defintion) {
		EObjectWrapper<EClass, EObject> instance = EObjectWrapper.createInstance(BpmnModelClass.EXTENSION);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_MUST_UNDERSTAND, true);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_DEFINITION, defintion.getEInstance());
		final String extensionDefName = (String)defintion.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME,String.valueOf(Integer.toHexString(extensionDefName.hashCode())));
		return instance;
	}
}
