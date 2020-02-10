package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.range;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartGridLinesForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartGraphOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class VerticalRangeOptionsFormProvider extends BaseChartGraphOptionsFormProvider {
	
	public VerticalRangeOptionsFormProvider(){
		super();
	}
	
	@Override
	protected ChartGridLinesForm createGridLinesForm(FormToolkit formToolkit, Section section) {
		return new ChartGridLinesForm(formToolkit, (Composite) section.getClient(), SWT.VERTICAL);
	}
	
	@Override
	protected boolean hasTypeSpecificForm() {
		return false;
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		return null;
	}

}
