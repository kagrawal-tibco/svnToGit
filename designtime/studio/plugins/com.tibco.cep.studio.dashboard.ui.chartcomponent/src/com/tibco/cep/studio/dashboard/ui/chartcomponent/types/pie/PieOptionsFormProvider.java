package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.pie;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartCategoryLabelForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartColorSchemeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartLegendForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartSizeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableTitleForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartGraphOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class PieOptionsFormProvider extends BaseChartGraphOptionsFormProvider {

	public PieOptionsFormProvider(){
		super();
	}

	@Override
	protected void addFirstRowForms(FormToolkit formToolkit, Composite parent) {
		//title form
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartTableTitleForm(formToolkit,(Composite) section.getClient(), false);
		addForm(form);
		section.setText(form.getTitle());

		//size form
		section = createSection(formToolkit, parent);
		form = new ChartSizeForm(formToolkit,(Composite) section.getClient(), false);
		addForm(form);
		section.setText(form.getTitle());

		//color scheme
		section = createSection(formToolkit, parent);
		form = new ChartColorSchemeForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

//		//about this chart form
//		section = createSection(formToolkit, parent);
//		form = new ChartAboutForm(formToolkit,(Composite) section.getClient());
//		addForm(form);
//		section.setText(form.getTitle());
	}

	@Override
	protected boolean hasTypeSpecificForm() {
		return false;
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		return null;
	}

	@Override
	protected void addSecondRowForms(FormToolkit formToolkit, Composite parent) {
		//category sorting form
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartCategoryLabelForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		//legend form
		section = createSection(formToolkit, parent);
		form = new ChartLegendForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

	}
}
