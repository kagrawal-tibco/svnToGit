package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

public interface IComponentContributor {

	public void selectionChanged(IStructuredSelection selection);

	public Action getAction();

	public int getPriority();
}