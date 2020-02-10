package com.tibco.cep.bpmn.model.designtime.utils;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class EObjectChangeAdapter extends AdapterImpl {
	
	@Override
	public void notifyChanged(Notification msg) {
		if(msg.isTouch()) return;
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		if(type instanceof EObject) {
			return true;
		} else 
		return super.isAdapterForType(type);
	}

}
