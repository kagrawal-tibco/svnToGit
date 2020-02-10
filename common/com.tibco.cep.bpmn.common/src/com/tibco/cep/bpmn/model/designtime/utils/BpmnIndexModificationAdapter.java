package com.tibco.cep.bpmn.model.designtime.utils;

import java.util.Date;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;

public class BpmnIndexModificationAdapter implements Adapter {
	
	public static int DEFINITIONS_LAST_MODIFIED_ID = BpmnMetaModel.getFeatureID(
			BpmnModelClass.DEFINITIONS,BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED);
	public static final int DEFINITIONS_LAST_PERSISTED_ID = BpmnMetaModel.getFeatureID(
			BpmnModelClass.DEFINITIONS,BpmnMetaModelConstants.E_ATTR_LAST_PERSISTED);
	

	private EObject fIndex;

	public BpmnIndexModificationAdapter(EObject index) {
		this.fIndex = index;
	}

	@Override
	public Notifier getTarget() {
		return fIndex;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return true;
	}

	@Override
	public void notifyChanged(Notification notification) {
		if(notification.getEventType() == Notification.REMOVING_ADAPTER) 
			return;
		if (fIndex == notification.getNotifier()) {
			EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
			int featureID = feature.getFeatureID();
			if (featureID == DEFINITIONS_LAST_MODIFIED_ID
					|| featureID == DEFINITIONS_LAST_PERSISTED_ID) {
				return;
			}
//			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(fIndex);
//			BpmnCorePlugin.debug("["+indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)+"]index modified");
			EObjectWrapper.wrap(fIndex).setAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED, new Date());
		} else {
			System.err.println("not the same index");
		}
	}

	@Override
	public void setTarget(Notifier newTarget) {
	}

}
