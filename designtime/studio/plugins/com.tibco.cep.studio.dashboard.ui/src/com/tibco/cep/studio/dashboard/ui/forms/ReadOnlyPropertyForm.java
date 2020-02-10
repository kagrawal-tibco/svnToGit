package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ReadOnlyPropertyForm extends SimplePropertyForm {

	public ReadOnlyPropertyForm(String title, FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super(title, formToolKit, parent, showGroup);
	}
	
	public ReadOnlyPropertyForm(String title, FormToolkit formToolKit, Composite parent, boolean showGroup, boolean compact) {
		super(title, formToolKit, parent, showGroup, compact);
	}

	@Override
	public void init() {
		//go thru each property control and make it read only 
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.setReadOnly(true);
		}
		super.init();
	}

}
