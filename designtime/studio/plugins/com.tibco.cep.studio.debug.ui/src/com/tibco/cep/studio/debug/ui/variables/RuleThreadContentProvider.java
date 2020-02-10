package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.internal.ui.model.elements.ElementContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenCountUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IHasChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;
import org.eclipse.debug.ui.IDebugUIConstants;

import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
@SuppressWarnings({"restriction"})
public class RuleThreadContentProvider extends ElementContentProvider {

	@Override
	protected int getChildCount(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		RuleDebugThread thread = (RuleDebugThread)element;
		if (!thread.isSuspended()) {
			return 0;
		}
		int childCount = thread.getFrameCount();
		return childCount;
	}

	@Override
	protected Object[] getChildren(Object parent, int index, int length,
			IPresentationContext context, IViewerUpdate monitor)
			throws CoreException {
		IRuleDebugThread thread = (IRuleDebugThread)parent;
		if (!thread.isSuspended()) {
			return EMPTY;
		}
		return getElements(getChildren(thread), index, length);
	}
	
	protected Object[] getChildren(IRuleDebugThread thread) {
		try {
			IStackFrame[] frames = thread.getStackFrames();
			return frames;
		} catch (DebugException e){
			return EMPTY;
		}
	}

	@Override
	protected boolean supportsContextId(String id) {
		return IDebugUIConstants.ID_DEBUG_VIEW.equals(id);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.model.elements.ElementContentProvider#getRule(org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenCountUpdate[])
	 */
	protected ISchedulingRule getRule(IChildrenCountUpdate[] updates) {
		return getThreadRule(updates);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.model.elements.ElementContentProvider#getRule(org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenUpdate[])
	 */
	protected ISchedulingRule getRule(IChildrenUpdate[] updates) {
		return getThreadRule(updates);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.model.elements.ElementContentProvider#getRule(org.eclipse.debug.internal.ui.viewers.model.provisional.IHasChildrenUpdate[])
	 */
	protected ISchedulingRule getRule(IHasChildrenUpdate[] updates) {
		return getThreadRule(updates);
	}
	
	/**
	 * Returns a scheduling rule to ensure we aren't trying to get thread content
	 * while executing an implicit evaluation (like toString() for the details
	 * pane).
	 * 
	 * @param updates viewer updates
	 * @return scheduling rule or <code>null</code>
	 */
	private ISchedulingRule getThreadRule(IViewerUpdate[] updates) {
		if (updates.length > 0) {
			Object element = updates[0].getElement();
			if (element instanceof RuleDebugThread) {
				return ((RuleDebugThread)element).getThreadRule();
			}
		}
		return null;
	}

}
