package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IPageChangedListener;

public class GlobalVariablesTray extends AbstractDialogTray implements IPageChangedListener{
	
	public GlobalVariablesTray(String viewId, IProject project) {
		super(viewId);
		setInput(project);
	}

}
