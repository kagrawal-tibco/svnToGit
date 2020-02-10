package com.tibco.cep.bpmn.ui.validation.resolution;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.BASE_ELEMENT;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectVisitor;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnProcessRefreshVisitor implements EObjectVisitor {
	boolean fixMissingExtensionData = false;
	private boolean isMissingExtensionData = false;

	public BpmnProcessRefreshVisitor(boolean fixMissingExtensionData) {
		this.fixMissingExtensionData = fixMissingExtensionData;
	}
	
	public boolean isMissingExtensionData() {
		return isMissingExtensionData;
	}	
	
	@Override
	public boolean visit(EObject eObj) {
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(eObj);
		if(eWrapper.isInstanceOf(BASE_ELEMENT)) {
			return visitBaseElement(eObj);
		} 
		return true;
	}
	
	

		
	public boolean visitBaseElement(EObject obj) {
		EObjectWrapper<EClass, EObject> ew = EObjectWrapper.wrap(obj);
		if(ew.isInstanceOf(BpmnModelClass.BASE_ELEMENT)){
				boolean hasExtensionDef = BpmnMetaModel.INSTANCE.hasExtensionDefinition(obj.eClass());
				List<EObjectWrapper<EClass, EObject>> extdefs = ew.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
				if(extdefs.size() == 0) {
					this.isMissingExtensionData = this.isMissingExtensionData || (true && hasExtensionDef);
					if(fixMissingExtensionData) {
						ExtensionHelper.getAddDataExtensionAttrDefinition(obj);
					} 
				} else {
					boolean hasExtAttrDefs = ExtensionHelper.hasExtensionAttrDefinition(obj);
					this.isMissingExtensionData = this.isMissingExtensionData || (true && !hasExtAttrDefs);
					if(fixMissingExtensionData) {
						ew.clearListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
						ExtensionHelper.getAddDataExtensionAttrDefinition(obj);
					} 
					
				}
				List<EObjectWrapper<EClass, EObject>> vlist = ew.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
				for(EObjectWrapper<EClass, EObject> v: vlist){
					EObject attrdef = v.getAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION);
					if(attrdef == null) {
						this.isMissingExtensionData = this.isMissingExtensionData || (true && hasExtensionDef);
						if(fixMissingExtensionData) {
							v.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, null);
							attrdef = ExtensionHelper.getExtensionAttrDefintionByType(obj, BpmnMetaModelConstants.EXTENSION_ATTRIBUTE_NAME);
							v.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, attrdef);
						} 
					}
				}
				
			}
			
//			ew.clearListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
//			ExtensionHelper.getAddDataExtensionAttrDefinition(obj);
//			
//			List<EObjectWrapper<EClass, EObject>> vlist = ew.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
//			for(EObjectWrapper<EClass, EObject> v: vlist){
//				EObject attrdef = v.getAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION);
//				if(attrdef != null) {
//					v.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, null);
//					attrdef = ExtensionHelper.getExtensionAttrDefintionByType(obj.eClass(), BpmnMetaModelConstants.EXTENSION_ATTRIBUTE_NAME);
//					v.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, attrdef);
//				}
//			}
			
		return true;
	}

}
