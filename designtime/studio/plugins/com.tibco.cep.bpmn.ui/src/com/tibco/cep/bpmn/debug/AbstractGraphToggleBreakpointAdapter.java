package com.tibco.cep.bpmn.debug;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractGraphToggleBreakpointAdapter implements IToggleBreakpointsTargetExtension {
	
	@Override
	public void toggleLineBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException {

	}

	@Override
	public boolean canToggleLineBreakpoints(IWorkbenchPart part, ISelection selection) {
		return false;
	}

	@Override
	public void toggleMethodBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException {

	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part, ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection) throws CoreException {

	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part, ISelection selection) {
		return false;
	}


}
