package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.forms.FontStylePropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCategoryColumnForm extends SimplePropertyForm {
	
	public ChartCategoryColumnForm(FormToolkit formToolKit, Composite parent) {
		super("Category (Row) Label", formToolKit, parent, false);
		addProperty("Size", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnLabelFontSize"));
		addProperty(new FontStylePropertyControl(this, "Font Style", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnLabelFontStyle")));
		addProperty("Alignment", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnLabelAlignment"));
	}
	
	@Override
	public void setInput(LocalElement localElement) throws Exception {
		super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
	}
	
}