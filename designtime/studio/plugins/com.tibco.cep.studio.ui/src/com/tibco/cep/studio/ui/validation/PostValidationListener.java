package com.tibco.cep.studio.ui.validation;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class PostValidationListener implements IResourceChangeListener {


	public void resourceChanged(IResourceChangeEvent event) {
		// IResourceChangeEvent.POST_BUILD does not seem to get broadcast, use POST_CHANGE instead.  Less efficient, but we have no choice.
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) { 
			refresh();
		}
	}

	public void refresh() {
		boolean isConsole = Boolean.valueOf(System.getProperty("studio.console", "false"));
		if (!isConsole) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					IDecoratorManager decoratorManager = StudioUIPlugin.getDefault().getWorkbench().getDecoratorManager();
					decoratorManager.update("com.tibco.cep.studio.ui.decorators.ValidationErrorDecorator");
				}
			});

		}
	}

}
