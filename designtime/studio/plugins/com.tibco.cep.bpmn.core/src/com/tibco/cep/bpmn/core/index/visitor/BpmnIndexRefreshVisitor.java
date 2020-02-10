/**
 * 
 */
package com.tibco.cep.bpmn.core.index.visitor;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnIndexRefreshVisitor extends BpmnElementVisitor {
	protected EObjectWrapper<EClass, EObject> fIndexWrapper;
	private IProject project;
	
	public BpmnIndexRefreshVisitor(EObjectWrapper<EClass, EObject> indexWrapper,IProject project, boolean resolve) {
		super(false, new NullProgressMonitor(), resolve);
		this.fIndexWrapper = indexWrapper;
		this.project = project;
	}
	
	@Override
	public boolean visitRootElement(EObject eObj) {
		final EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
		
		final String eid = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		Map<String, EObject> rootElementMap = ECoreHelper.getRootElementMap(fIndexWrapper);
		boolean toBeAdded = true;
		if(rootElementMap.containsKey(eid)) {
			// id process id already exist , replace it with latest one
			EObject object = rootElementMap.get(eid);
			if(object != eObj){
				if(object.eClass() == eObj.eClass())
					BpmnCommonIndexUtils.removeRootElement(fIndexWrapper, object);
			}
			else
				toBeAdded = false;
		}
		if (toBeAdded) {
			if (!BpmnModelClass.EVENT_DEFINITION.isSuperTypeOf(eObj.eClass()) 
					&& !BpmnModelClass.MESSAGE.equals(eObj.eClass())) {
				BpmnCorePlugin.debug("Index Refresh Adding RootElement Type->"
						+ eObj.eClass().getName() + " id->" + eid);
				BpmnCommonIndexUtils.addRootElement(fIndexWrapper, eObj);
			}
		}
	
		boolean hasProcess = (BpmnIndexUtils.getAllProcesses(file.getProject().getName()).size() > 0);
		BpmnProjectNatureManager.getInstance().enableBpmnNature(project,hasProcess);
		
		return true;
	}
	

	
	public EObjectWrapper<EClass, EObject> getIndexWrapper() {
		return fIndexWrapper;
	}
}