package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartGridValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartDataSettingsFormProvider;

public class TableDataSettingsFormProvider extends ChartDataSettingsFormProvider {
	
	@Override
	protected ChartRunTimeDataForm createRunTimeDataForm(FormToolkit formToolkit, Composite parent) {
		return new ChartRunTimeDataForm(formToolkit,parent) {
			
			@Override
			protected ChartValueFieldSettingsForm createValueFieldSettingsForm(FormToolkit formToolkit, Composite parent) {
				return new ChartGridValueFieldSettingsForm(formToolkit, parent);
			}
		};
		
	}

}
