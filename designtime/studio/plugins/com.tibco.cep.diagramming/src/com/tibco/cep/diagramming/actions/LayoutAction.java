package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class LayoutAction extends Action {

	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public LayoutAction( IWorkbenchPage page) {
		super("", SWT.PUSH);
		this.page = page;
		this.setText(Messages.getString("layout"));
		this.setToolTipText(Messages.getString("layout.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/globalLayout.gif"));
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.layoutAction(page);
	}
}

