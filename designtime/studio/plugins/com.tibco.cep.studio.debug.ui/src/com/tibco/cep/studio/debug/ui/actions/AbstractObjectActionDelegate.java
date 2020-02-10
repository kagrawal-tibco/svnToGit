package com.tibco.cep.studio.debug.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

public abstract class AbstractObjectActionDelegate implements IObjectActionDelegate, IActionDelegate2 {
	
	IWorkbenchPart fPart = null;
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;
	}
	
	protected IWorkbenchPart getPart() {
		return fPart;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {
	}
	
	/**
	 * Returns the currently selected item(s) from the current workbench page or <code>null</code>
	 * if the current active page could not be resolved.
	 * @return the currently selected item(s) or <code>null</code>
	 */
	protected IStructuredSelection getCurrentSelection() {
		IWorkbenchPage page = StudioDebugUIPlugin.getActivePage();
		if (page != null) {
			ISelection selection= page.getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection)selection;
			}	
		}
		return null;
	}
	
	/**
	 * Displays the given error message in the status line.
	 * 
	 * @param message
	 */
	protected void showErrorMessage(String message) {
		if (fPart instanceof IViewPart) {
			IViewSite viewSite = ((IViewPart)fPart).getViewSite();
			IStatusLineManager manager = viewSite.getActionBars().getStatusLineManager();
			manager.setErrorMessage(message);
			Display.getCurrent().beep();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	public void dispose() {
		fPart = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	/**
	 * Returns the workbench window this action is installed in, or <code>null</code>
	 */
	protected IWorkbenchWindow getWorkbenchWindow() {
		if (fPart != null) {
			return fPart.getSite().getWorkbenchWindow();
		}
		return null;
	}
}

