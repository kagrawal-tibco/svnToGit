package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTitlesForm extends SimplePropertyForm {

	public ChartTitlesForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Titles", formToolKit, parent, showGroup);
		ViewsConfigReader instance = ViewsConfigReader.getInstance();
		addProperty("Chart", instance.getProperty(BEViewsElementNames.CHART_COMPONENT, LocalConfig.PROP_KEY_DISPLAY_NAME));
		addProperty("Category Axis", instance.getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisHeaderName"));
		addProperty("Value Axis", instance.getProperty(BEViewsElementNames.CHART_COMPONENT, "ValueAxisHeaderName"));
	}

}
