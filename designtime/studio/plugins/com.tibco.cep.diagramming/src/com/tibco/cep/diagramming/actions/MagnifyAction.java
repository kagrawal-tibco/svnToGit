package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.Messages;

/**
 * 
 * @author ggrigore
 *
 */
public class MagnifyAction extends Action {

	private IWorkbenchPage page;

	@SuppressWarnings("static-access")
	public MagnifyAction( IWorkbenchPage page) {
		super("", SWT.NONE);
		this.page = page;
		setText(Messages.getString("magnify"));
		setToolTipText(Messages.getString("magnify.tooltip"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/magnifyZoom.png"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		DiagramUtils.magnifyAction(page);
	}
}
