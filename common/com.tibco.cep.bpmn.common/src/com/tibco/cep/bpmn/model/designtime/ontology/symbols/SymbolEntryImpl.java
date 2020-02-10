package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class SymbolEntryImpl  implements SymbolEntry {
	
	EObject symbol;
	
	public SymbolEntryImpl(EObject item) {
		this.symbol = item;
	}
	@Override
	public String getKey() {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
		return wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME);
	}
	
	@Override
	public EObject getValue() {			
		return symbol;
	}
	
	@Override
	public EObject setValue(EObject value) {
		EObject oldValue = symbol;
		symbol = value;
		return oldValue;
	}
	
	public String toString() {
		if(symbol != null) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
			String idName = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME);
			String type = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE);
			return "{"+idName+"->"+type+"}";
		} else 
			return super.toString();
	}
	
	public EObject getType() {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
		return wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE);
	}
	
	public String getPath() {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
		return wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ENTITY_PATH);
	}
	
	public boolean isArray() {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
		return wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_ARRAY);
	}
	
	public boolean isPrimitive() {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(symbol);
		return wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_PRIMITIVE);
	}
	
}