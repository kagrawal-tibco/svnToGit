package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class PrintAction extends Action {

	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public PrintAction( IWorkbenchPage page) {
		super("", SWT.PUSH);
		this.page = page;
		this.setText(Messages.getString("print"));
		this.setToolTipText(Messages.getString("print.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/print.gif"));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.printAction(page);
	}
}

