package com.tibco.cep.bpmn.debug.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;

import com.tibco.cep.bpmn.debug.ProcessToggleBreakpointTargetAdapter;


public class RetargettableActionAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IToggleBreakpointsTarget.class) {
			return new ProcessToggleBreakpointTargetAdapter();
		} 
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{
				IToggleBreakpointsTarget.class};
	}

}
