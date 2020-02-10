package com.tibco.cep.bpmn.ui.graph.model.controller;

import org.eclipse.emf.common.notify.Notification;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectChangeAdapter;

public class ModelChangeAdapter extends EObjectChangeAdapter {

	ModelChangeListener modelChangeListener;
	
	public ModelChangeAdapter(ModelChangeListener listener) {
		this.modelChangeListener = listener;
	}
	
	
	
	@Override
	public void notifyChanged(Notification msg) {
		if(msg.isTouch()) {
			return;
		}
		modelChangeListener.modelChanged(new ModelChangeEvent(msg.getNotifier()));
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		if(type == ModelChangeListener.class ) {
			return true;
		} else 
			return super.isAdapterForType(type);
	}
}
