package com.tibco.cep.studio.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPart;

public interface IReusableTrayView {
	
	public void createControl(Composite parent, IProject project);
	
	public Control getControl();
	
	public void init(IActionBars bars, IToolBarManager toolBarManager,
			IStatusLineManager statusLineManager, IMemento memento);

	public void dispose();

	public void update(IWorkbenchPart part,	Control c, boolean b);
	
	public void setInput(Object input);

}
