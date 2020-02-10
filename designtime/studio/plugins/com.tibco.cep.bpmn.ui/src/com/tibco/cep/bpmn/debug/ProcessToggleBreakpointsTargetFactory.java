package com.tibco.cep.bpmn.debug;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.bpmn.ui.editor.BpmnEditor;

public class ProcessToggleBreakpointsTargetFactory implements IToggleBreakpointsTargetFactory {
	/**
     * Toggle breakpoints target ID which refers to a target contributed
     * through the legacy adapter mechanism.
     */
    public static String DEFAULT_TOGGLE_TARGET_ID = "process"; //$NON-NLS-1$
    
    private static Set<Object> DEFAULT_TOGGLE_TARGET_ID_SET = new HashSet<Object>();
    static {
        DEFAULT_TOGGLE_TARGET_ID_SET.add(DEFAULT_TOGGLE_TARGET_ID);
    }
    
    ProcessToggleBreakpointTargetAdapter bpAdapter = null;

	public ProcessToggleBreakpointsTargetFactory() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set getToggleTargets(IWorkbenchPart part, ISelection selection) {
		if(part instanceof BpmnEditor) {			
			return DEFAULT_TOGGLE_TARGET_ID_SET;
		}
		return Collections.EMPTY_SET;
	}

	@Override
	public String getDefaultToggleTarget(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		return DEFAULT_TOGGLE_TARGET_ID;
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(String targetID) {
		if(DEFAULT_TOGGLE_TARGET_ID.equals(targetID)) {
			if(bpAdapter == null) {
				bpAdapter = new ProcessToggleBreakpointTargetAdapter();
			}
		}
		return bpAdapter;
	}

	@Override
	public String getToggleTargetName(String targetID) {
		if(DEFAULT_TOGGLE_TARGET_ID.equals(targetID)) {
			return DEFAULT_TOGGLE_TARGET_ID;
		}
		return null;
	}

	@Override
	public String getToggleTargetDescription(String targetID) {
		return DEFAULT_TOGGLE_TARGET_ID;
	}

}
