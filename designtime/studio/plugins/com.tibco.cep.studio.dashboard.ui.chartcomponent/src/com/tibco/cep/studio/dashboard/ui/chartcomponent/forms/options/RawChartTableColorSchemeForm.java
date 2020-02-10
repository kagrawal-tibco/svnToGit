package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class RawChartTableColorSchemeForm extends SimplePropertyForm {

	@SuppressWarnings("unused")
	private PropertyControl colorSetControl;

	public RawChartTableColorSchemeForm(FormToolkit formToolKit, Composite parent) {
		super("Color Scheme", formToolKit, parent, false);
		colorSetControl = addPropertyAsSpinner("Chart Area", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_COMPONENT, "ColorSet"));
	}
	
	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
	}

}
