package com.tibco.cep.bpmn.core.index.visitor;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnIndexCreationResourceVisitor extends BpmnIndexRefreshVisitor {
	


	public BpmnIndexCreationResourceVisitor(EObjectWrapper<EClass, EObject> indexWrapper, IProject proj, boolean resolve) {
		super(indexWrapper,proj,resolve);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.core.visitor.BpmnElementVisitor#visitBaseElement(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean visitBaseElement(EObject obj) {
		ExtensionHelper.getAddDataExtensionAttrDefinition(obj);
		return super.visitBaseElement(obj);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.core.visitor.BpmnElementVisitor#visitExtension(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean visitExtension(EObject obj) {
		getIndexWrapper().addToListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSIONS,obj);
		return true;
	}

	
	

}
