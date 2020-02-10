package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.ui.model.elements.VariableLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate;

import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariable;
@SuppressWarnings("restriction")
public class RuleVariableLabelProvider extends VariableLabelProvider {

	protected void retrieveLabel(ILabelUpdate update) throws CoreException {
		Object element = update.getElement();
		if (element instanceof RuleDebugVariable) {
			RuleDebugVariable variable = (RuleDebugVariable) element;
			RuleDebugStackFrame frame = variable.getStackFrame();
			if (!frame.getThread().isSuspended()) {
				update.cancel();
				return;
			}

			try {
				frame.getUnderlyingStackFrame();
			} catch (CoreException e) {
				update.cancel();
				return;
			}
		}
		super.retrieveLabel(update);
	}
}
