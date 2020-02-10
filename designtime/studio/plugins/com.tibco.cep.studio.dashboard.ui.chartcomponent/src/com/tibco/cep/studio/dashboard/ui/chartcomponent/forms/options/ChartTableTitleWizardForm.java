package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTableTitleWizardForm extends SimplePropertyForm {

	private PropertyControl categoryColumnHdrPropertyControl;
	private PropertyControl displayNamePropertyControl;

	public ChartTableTitleWizardForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Title", formToolKit, parent, showGroup);
		displayNamePropertyControl = addProperty("Chart Title", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_COMPONENT, LocalTextComponent.PROP_KEY_DISPLAY_NAME));
		//add category column
		categoryColumnHdrPropertyControl = addProperty("Category Column", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnHeaderName"));
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		//we don't call super
		//we send the newLocalElement to display name property control (since it works @ component level)
		displayNamePropertyControl.setLocalElement(newLocalElement);
		//we send the visualization in newLocalElement to category column header property control (since it works @ visualization level)
		categoryColumnHdrPropertyControl.setLocalElement(newLocalElement.getElement(LocalUnifiedVisualization.TYPE));
	}

}
