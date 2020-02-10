package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTableCategorySortForm extends ChartCategoryLabelForm {

	public ChartTableCategorySortForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
	}

	@Override
	public void setInput(LocalElement localElement) throws Exception {
		super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
	}

	@Override
	protected SynProperty getSortProperty() {
		return ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnLabelSortOrder");
	}

	@Override
	protected SynProperty getAllowDuplicatesProperty() {
		return ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnLabelAllowDuplicates");
	}

}