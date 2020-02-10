package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.column;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartColumnValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartDataSettingsFormProvider;

public class ColumnDataSettingsFormProvider extends ChartDataSettingsFormProvider {
	
	@Override
	protected ChartRunTimeDataForm createRunTimeDataForm(FormToolkit formToolkit, Composite parent) {
		return new ChartRunTimeDataForm(formToolkit,parent) {
			
			@Override
			protected ChartValueFieldSettingsForm createValueFieldSettingsForm(FormToolkit formToolkit, Composite parent) {
				return new ChartColumnValueFieldSettingsForm(formToolkit, parent);
			}
		};
		
	}

}
