/**
 * 
 */
package com.tibco.be.ws.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.repo.EMFTnsCache;

/**
 * @author vpatil
 *
 */
class LocalECoreHelper extends CommonECoreHelper {
	
	public static List<EObjectWrapper<EClass, EObject>> getItemDefinitionUsingLocation(
			String projName, String entityPath, EMFTnsCache cache) {
		EObject index = BpmnCommonIndexUtils.getIndex(projName);
		if (index != null) {
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper
					.wrap(index);
			return LocalECoreHelper.getItemDefinitionUsingLocation(cache,
					indexWrapper, projName, entityPath);
		}

		return new ArrayList<EObjectWrapper<EClass, EObject>>();
	}
	
	public static EObjectWrapper<EClass, EObject> getItemDefinitionUsingNameSpace(
			String projName, String nameSpace, EMFTnsCache cache) {
		EObject index = BpmnCommonIndexUtils.getIndex(projName);
		if (index != null) {
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper
					.wrap(index);
			return CommonECoreHelper.getItemDefinitionUsingNameSpace(cache,
					indexWrapper, projName, nameSpace);
		}
		return null;
	}
}
