package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.forms.CheckBoxPropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCategoryLabelForm extends SimplePropertyForm {

	public ChartCategoryLabelForm(FormToolkit formToolKit, Composite parent) {
		super("Category Labels", formToolKit, parent, false);
		//add sort property control
		addProperty("Sort", getSortProperty());
		//add allow duplicates control
		CheckBoxPropertyControl propertyControl = (CheckBoxPropertyControl) addProperty("Allow Repeats", getAllowDuplicatesProperty());
		propertyControl.setShowDisplayName(false);
	}

	protected SynProperty getSortProperty() {
		return ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisLabelSortOrder");
	}

	protected SynProperty getAllowDuplicatesProperty() {
		return ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisLabelAllowDuplicates");
	}

}