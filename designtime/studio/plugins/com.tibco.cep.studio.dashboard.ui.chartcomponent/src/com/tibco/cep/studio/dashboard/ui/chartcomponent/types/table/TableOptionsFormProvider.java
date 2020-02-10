package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartCategoryColumnForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartSizeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableCategorySortForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableColorSchemeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableColumnWidthForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableHeaderRowForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableTitleForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class TableOptionsFormProvider extends BaseChartOptionsFormProvider {

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
		form = new ChartTableColorSchemeForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		//column widths
		section = createSection(formToolkit, parent);
		form = new ChartTableColumnWidthForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

//		//about this chart form
//		section = createSection(formToolkit, parent);
//		form = new ChartAboutForm(formToolkit,(Composite) section.getClient());
//		addForm(form);
//		section.setText(form.getTitle());

	}

	@Override
	protected void addSecondRowForms(FormToolkit formToolkit, Composite parent) {
		//header row form
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartTableHeaderRowForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		//category row form
		section = createSection(formToolkit, parent);
		form = new ChartCategoryColumnForm(formToolkit,(Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		//category sorting form
		section = createSection(formToolkit, parent);
		form = new ChartTableCategorySortForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

	}

}