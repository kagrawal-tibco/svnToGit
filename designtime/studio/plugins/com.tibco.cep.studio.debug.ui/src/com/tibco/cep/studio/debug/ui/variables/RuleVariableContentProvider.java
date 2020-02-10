package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.ui.model.elements.VariableContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;

import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
@SuppressWarnings("restriction")
public class RuleVariableContentProvider extends VariableContentProvider {

	@Override
	protected Object[] getAllChildren(Object parent,
			IPresentationContext context) throws CoreException {
		return super.getAllChildren(parent, context);
		// add filters here
	}

	@Override
	protected int getChildCount(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		try {
			return super.getChildCount(element, context, monitor);
		} catch (CoreException e) {
			if (e.getStatus().getCode() == IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED) {
				monitor.cancel();
				return 0;
			} else if(e.getStatus().getCode() == IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME){
				monitor.cancel();
				return 0;		
			}
			StudioDebugUIPlugin.log(e);
			throw e;
		}
	}

	@Override
	protected Object[] getChildren(Object parent, int index, int length,
			IPresentationContext context, IViewerUpdate monitor)
			throws CoreException {
		try {
			return super.getChildren(parent, index, length, context, monitor);
		} catch (CoreException e) {
			if (e.getStatus().getCode() == IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED) {
				monitor.cancel();
				return EMPTY;
			} else if(e.getStatus().getCode() == IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME){
				monitor.cancel();
				return EMPTY;		
			}
			StudioDebugUIPlugin.log(e);
			throw e;
		}
	}

	@Override
	protected boolean hasChildren(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		try {
			return super.hasChildren(element, context, monitor);
		} catch (CoreException e) {
			if (e.getStatus().getCode() == IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED) {
				monitor.cancel();
				return false;
			} else if(e.getStatus().getCode() == IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME){
				monitor.cancel();
				return false;		
			}
			StudioDebugUIPlugin.log(e);
			throw e;
		}
	}

}
