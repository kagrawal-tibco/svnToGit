package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class SelectAction extends Action {

	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public SelectAction(IWorkbenchPage page) {
		super("", SWT.NONE);
		this.page = page;
		setText(Messages.getString("select"));
		setToolTipText(Messages.getString("select.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/select.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.selectAction(page);
	}
}
