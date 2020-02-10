package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.column;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartGridLinesForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.bar.BarOptionsFormProvider;

public class ColumnOptionsFormProvider extends BarOptionsFormProvider {
	
	public ColumnOptionsFormProvider() {
	}
	
	@Override
	protected ChartGridLinesForm createGridLinesForm(FormToolkit formToolkit, Section section) {
		return new ChartGridLinesForm(formToolkit, (Composite) section.getClient(), SWT.VERTICAL);
	}

}