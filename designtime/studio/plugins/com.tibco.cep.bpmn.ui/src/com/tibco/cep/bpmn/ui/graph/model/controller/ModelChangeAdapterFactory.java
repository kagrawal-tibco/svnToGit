package com.tibco.cep.bpmn.ui.graph.model.controller;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

public class ModelChangeAdapterFactory extends AdapterFactoryImpl {
	
	private ModelChangeAdapter adapter;
	public ModelChangeAdapterFactory(ModelChangeListener mcl) {
		adapter = new ModelChangeAdapter(mcl);
	}
	
	@Override
	protected void associate(Adapter adapter, Notifier target) {
		if(adapter != null) {
			if(target.eAdapters().contains(target))
				return;
		}
		super.associate(adapter, target);
	}
	
	@Override
	protected Adapter createAdapter(Notifier target) {
		return adapter;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#isFactoryForType(java.lang.Object)
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return type == ModelChangeListener.class;
	}
	

}
