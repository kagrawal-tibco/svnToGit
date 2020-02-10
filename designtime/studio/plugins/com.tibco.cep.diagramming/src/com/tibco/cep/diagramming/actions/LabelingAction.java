package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

public class LabelingAction extends Action {
	private IWorkbenchPage page;
	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public LabelingAction( IWorkbenchPage page) {
		super("", SWT.NONE);
		this.page = page;
		this.setText(Messages.getString("labeling"));
		this.setToolTipText(Messages.getString("labeling.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/labeling.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.labelingAction(page);
	}	
}

