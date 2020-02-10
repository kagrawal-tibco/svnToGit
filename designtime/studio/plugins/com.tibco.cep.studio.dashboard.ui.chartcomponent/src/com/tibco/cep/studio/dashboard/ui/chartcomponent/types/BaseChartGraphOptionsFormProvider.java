package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartCategoryAxisFontsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartCategoryLabelForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartColorSchemeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartGridLinesForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartLegendForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartSizeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTitlesForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartValueAxisFontsForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class BaseChartGraphOptionsFormProvider extends BaseChartOptionsFormProvider {

	@Override
	protected void addFirstRowForms(FormToolkit formToolkit, Composite parent) {
		// title form
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartTitlesForm(formToolkit, (Composite) section.getClient(), false);
		addForm(form);
		section.setText(form.getTitle());

		// size form
		section = createSection(formToolkit, parent);
		form = new ChartSizeForm(formToolkit, (Composite) section.getClient(), false);
		addForm(form);
		section.setText(form.getTitle());

		// color scheme
		section = createSection(formToolkit, parent);
		form = new ChartColorSchemeForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		// type specific form
		if (hasTypeSpecificForm() == true) {
			section = createSection(formToolkit, parent);
			form = getTypeSpecificForm(formToolkit, section);
			addForm(form);
			section.setText(form.getTitle());
		}

		// grid lines form
		section = createSection(formToolkit, parent);
		form = createGridLinesForm(formToolkit, section);
		addForm(form);
		section.setText(form.getTitle());

//		// about this chart form
//		section = createSection(formToolkit, parent);
//		form = new ChartAboutForm(formToolkit, (Composite) section.getClient());
//		addForm(form);
//		section.setText(form.getTitle());
	}

	protected ChartGridLinesForm createGridLinesForm(FormToolkit formToolkit, Section section) {
		return new ChartGridLinesForm(formToolkit, (Composite) section.getClient(), SWT.VERTICAL);
	}

	protected boolean hasTypeSpecificForm() {
		return true;
	}

	protected abstract BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section createSection);

	@Override
	protected void addSecondRowForms(FormToolkit formToolkit, Composite parent) {
		// category axis font form
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartCategoryAxisFontsForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		// value axis font form
		section = createSection(formToolkit, parent);
		form = new ChartValueAxisFontsForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		// category sorting form
		section = createSection(formToolkit, parent);
		form = new ChartCategoryLabelForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

		// legend form
		section = createSection(formToolkit, parent);
		form = new ChartLegendForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());
	}

}
