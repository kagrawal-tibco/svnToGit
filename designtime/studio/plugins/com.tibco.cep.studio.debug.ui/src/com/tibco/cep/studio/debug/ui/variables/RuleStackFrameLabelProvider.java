package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.debug.internal.ui.model.elements.DebugElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate;

import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
@SuppressWarnings({"restriction"})
public class RuleStackFrameLabelProvider extends DebugElementLabelProvider {
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.model.elements.ElementLabelProvider#retrieveLabel(org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate)
	 */
	protected void retrieveLabel(ILabelUpdate update) throws CoreException {
		Object element = update.getElement();
		if (element instanceof RuleDebugStackFrame) {
			RuleDebugStackFrame frame = (RuleDebugStackFrame) element;
			if (!frame.getThread().isSuspended()) {
				update.cancel();
				return;
			}
			try{
				frame.getUnderlyingStackFrame();
			} catch (CoreException e) {
				update.cancel();
				return;
			}
		}
		super.retrieveLabel(update);
	}

	/* (non-Javadoc)
	 * @see xorg.eclipse.debug.internal.ui.model.elements.ElementLabelProvider#getRule(xorg.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate)
	 */
	protected ISchedulingRule getRule(ILabelUpdate update) {
		Object element = update.getElement();
		if (element instanceof RuleDebugStackFrame) {
			RuleDebugStackFrame frame = (RuleDebugStackFrame) element;
			return ((RuleDebugThread)frame.getThread()).getThreadRule();
		}
		return null;
	}
}
