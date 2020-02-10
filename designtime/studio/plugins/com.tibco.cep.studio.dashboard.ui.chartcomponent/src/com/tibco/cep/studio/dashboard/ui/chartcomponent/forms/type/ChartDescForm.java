package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDescriptionType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;

public class ChartDescForm extends SimplePropertyForm {

	private PropertyControl descPropertyControl;

	public ChartDescForm(FormToolkit formToolKit, Composite parent) {
		super(LocalElement.PROP_KEY_DESCRIPTION, formToolKit, parent, false);
		descPropertyControl = addProperty(null, new SynOptionalProperty(LocalElement.PROP_KEY_DESCRIPTION, new SynDescriptionType()));
	}
	
	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		Control control = descPropertyControl.createControl(formComposite);
		GridData layoutData = new GridData(SWT.FILL,SWT.FILL,true,true);
		layoutData.heightHint = 200;
		control.setLayoutData(layoutData);
	}

}