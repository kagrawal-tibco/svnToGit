package com.tibco.cep.bpmn.debug.adapter;

import java.util.Arrays;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;

import com.tibco.cep.bpmn.debug.ProcessToggleBreakpointTargetAdapter;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.studio.core.StudioCore;

/*
@author ssailapp
@date Jul 2, 2009 2:05:36 PM
 */

public class ProcessBreakpointAdapterFactory implements IAdapterFactory {
	public static String[] validExtensions =StudioCore.getCodeExtensions(); 
	
	public ProcessBreakpointAdapterFactory() {
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if(adaptableObject instanceof BpmnEditor) {
			return new ProcessToggleBreakpointTargetAdapter();
		}
		return null;
	}
	
	public boolean isValidExtension(String extension) {
		return Arrays.binarySearch(validExtensions, extension) >= 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{IToggleBreakpointsTarget.class};
	}

}
