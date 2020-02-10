package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.ui.model.elements.StackFrameContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;

import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
@SuppressWarnings("restriction")
public class RuleStackFrameContentProvider extends StackFrameContentProvider {

	/*
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.model.elements.StackFrameContentProvider#getAllChildren(java.lang.Object, org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext, org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate)
	 */
	@Override
	protected Object[] getAllChildren(Object parent,
			IPresentationContext context, IViewerUpdate monitor)
			throws CoreException {
		try {
			// Add filters here
			return super.getAllChildren(parent, context, monitor);
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

}
