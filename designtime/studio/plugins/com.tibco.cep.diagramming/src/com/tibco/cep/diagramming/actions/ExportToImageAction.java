package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class ExportToImageAction extends Action {

	private IWorkbenchPage page;
	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public ExportToImageAction( IWorkbenchPage page) {
		super("");
		this.page = page;
		this.setText(Messages.getString("export.image"));
		this.setToolTipText(Messages.getString("export.image.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/imageExport.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.exportToImageAction(page);
	}
}
