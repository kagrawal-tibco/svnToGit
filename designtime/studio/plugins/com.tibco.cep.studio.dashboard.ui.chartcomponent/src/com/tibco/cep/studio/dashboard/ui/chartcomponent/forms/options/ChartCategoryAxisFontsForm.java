package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.FontStylePropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCategoryAxisFontsForm extends BaseForm {

	private SimplePropertyForm axisTitleForm;

	private SimplePropertyForm axisValueForm;

	public ChartCategoryAxisFontsForm(FormToolkit formToolKit, Composite parent) {
		super("Category Axis Font", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());

		axisTitleForm = new SimplePropertyForm("Axis Title", formToolKit, formComposite, true);
		axisTitleForm.addProperty("Size", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisHeaderFontSize"));
		axisTitleForm.addProperty(new FontStylePropertyControl(axisTitleForm, "Font Style", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisHeaderFontStyle")));
		addForm(axisTitleForm);
		axisTitleForm.init();
		axisTitleForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		axisValueForm = new SimplePropertyForm("Axis Value", formToolKit, formComposite, true);
		axisValueForm.addProperty("Size", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisLabelFontSize"));
		axisValueForm.addProperty(new FontStylePropertyControl(axisTitleForm, "Font Style", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "CategoryAxisLabelFontStyle")));
		addForm(axisValueForm);
		axisValueForm.init();
		axisValueForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	@Override
	protected void doEnableListeners() {
	}

	@Override
	protected void doDisableListeners() {
	}

	@Override
	public void refreshEnumerations() {
	}

	@Override
	public void refreshSelections() {
	}

}