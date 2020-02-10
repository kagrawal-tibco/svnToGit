package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.bar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartBarValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartValueFieldSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartDataSettingsFormProvider;

public class BarDataSettingsFormProvider extends ChartDataSettingsFormProvider {
	
	@Override
	protected ChartRunTimeDataForm createRunTimeDataForm(FormToolkit formToolkit, Composite parent) {
		return new ChartRunTimeDataForm(formToolkit,parent) {
			
			@Override
			protected ChartValueFieldSettingsForm createValueFieldSettingsForm(FormToolkit formToolkit, Composite parent) {
				return new ChartBarValueFieldSettingsForm(formToolkit, parent);
			}
		};
		
	}

}
