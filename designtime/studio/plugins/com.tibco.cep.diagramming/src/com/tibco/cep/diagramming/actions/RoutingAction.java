package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class RoutingAction extends Action {

	private IWorkbenchPage page;
	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public RoutingAction(IWorkbenchPage page) {
		super("", SWT.NONE);
		this.page = page;
		this.setText(Messages.getString("link.routing"));
		this.setToolTipText(Messages.getString("link.routing.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/routing.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.routingAction(page);
	}
}
