package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class StudioBuildpathPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		// ensure the page has no special buttons
		noDefaultAndApplyButton();
	}

}
