package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class LinkNavigatorAction extends Action {

	private IWorkbenchPage page;
	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public LinkNavigatorAction( IWorkbenchPage page) {
		super("", SWT.NONE);
		this.page = page;
		setText(Messages.getString("link.navigator"));
		setToolTipText(Messages.getString("link.navigator.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/linkNavigation.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.linkNavigatorAction(page);
	}
}
