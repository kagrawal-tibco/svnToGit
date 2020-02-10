package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class PrintSetupAction extends Action {

	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public PrintSetupAction( IWorkbenchPage page) {
		super("");
		this.page = page;
		this.setText(Messages.getString("print.setup"));
		this.setToolTipText(Messages.getString("print.setup.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/printSetup.gif"));
	}

	// does not quite work
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.printSetupAction(page);
	}
}

