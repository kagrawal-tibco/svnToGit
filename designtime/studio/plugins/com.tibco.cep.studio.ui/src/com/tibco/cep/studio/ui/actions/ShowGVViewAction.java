package com.tibco.cep.studio.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ShowGVViewAction extends Action {

	protected IWorkbenchWindow window;
	
	/**
	 * @param name
	 * @param window
	 * @param newImage
	 */
	public ShowGVViewAction(String name, 
			                IWorkbenchWindow window, 
			                ImageDescriptor newImage) {
		super(name, AS_PUSH_BUTTON);
	    this.window = window;
		setImageDescriptor(newImage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			window.getActivePage().showView("com.tibco.cep.studio.ui.views.GlobalVariablesView");
		} catch (PartInitException e) {
			StudioUIPlugin.debug(e.getMessage());
		}
	}
}
