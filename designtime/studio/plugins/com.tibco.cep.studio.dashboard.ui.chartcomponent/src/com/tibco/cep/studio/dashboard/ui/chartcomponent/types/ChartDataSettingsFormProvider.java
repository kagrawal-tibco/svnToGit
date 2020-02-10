package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.PageUtils;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartDesignTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartDataSettingsFormProvider implements SlavePageFormsProvider {

	private List<BaseForm> forms;

	public ChartDataSettingsFormProvider() {
		forms = new LinkedList<BaseForm>();
	}

	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		// default does nothing
	}

	@Override
	public final void createForm(FormToolkit formToolkit, Composite parent) {
		// set layout for the parent
		parent.setLayout(new GridLayout());

		// design time data
		Section section = createSection(formToolkit, parent);
		BaseForm form = new ChartDesignTimeDataForm(formToolkit, (Composite) section.getClient(), SWT.HORIZONTAL);
		addForm(form);
		section.setText(form.getTitle());

		// run time data
		section = createSection(formToolkit, parent);
		form = createRunTimeDataForm(formToolkit, (Composite) section.getClient());
		addForm(form);
		section.setText(form.getTitle());

	}

	protected ChartRunTimeDataForm createRunTimeDataForm(FormToolkit formToolkit, Composite parent) {
		return new ChartRunTimeDataForm(formToolkit, parent);
	}

	protected Section createSection(FormToolkit toolkit, Composite parent) {
		Section section = PageUtils.createSection(toolkit, parent);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return section;
	}

	protected void addForm(BaseForm form) {
		form.init();
		forms.add(form);
	}

	@Override
	public BaseForm[] getForms() {
		return forms.toArray(new BaseForm[forms.size()]);
	}

}