package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTableTitleForm extends SimplePropertyForm {

	public ChartTableTitleForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Titles", formToolKit, parent, showGroup);
		addProperty("Chart", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_COMPONENT, LocalTextComponent.PROP_KEY_DISPLAY_NAME));
	}

}
