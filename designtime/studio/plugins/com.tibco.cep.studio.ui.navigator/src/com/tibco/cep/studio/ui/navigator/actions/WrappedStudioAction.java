package com.tibco.cep.studio.ui.navigator.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.SelectionListenerAction;

public class WrappedStudioAction extends SelectionListenerAction {

	private IWorkbenchWindowActionDelegate fWrappedAction;

	public IWorkbenchWindowActionDelegate getWrappedAction() {
		return fWrappedAction;
	}

	public WrappedStudioAction(String text, IWorkbenchWindowActionDelegate action) {
		super(text);
		this.fWrappedAction = action;
	}

	@Override
	public void run() {
		fWrappedAction.run(this);
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		fWrappedAction.selectionChanged(this, selection);
		return super.updateSelection(selection);
	}
	
}
