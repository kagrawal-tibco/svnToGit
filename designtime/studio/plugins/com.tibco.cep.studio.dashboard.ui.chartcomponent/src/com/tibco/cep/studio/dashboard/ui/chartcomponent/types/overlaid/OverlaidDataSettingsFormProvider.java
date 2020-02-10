package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.overlaid;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartDataSettingsForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartDataSettingsFormProvider;

public class OverlaidDataSettingsFormProvider extends ChartDataSettingsFormProvider {
	
	@Override
	protected ChartRunTimeDataForm createRunTimeDataForm(FormToolkit formToolkit, Composite parent) {
		return new ChartRunTimeDataForm(formToolkit,parent) {
			
			@Override
			protected ChartDataSettingsForm createDataSettingsForm(FormToolkit formToolkit, Composite parent) {
				return new ChartDataSettingsForm(formToolkit,parent, new OverlaidSeriesTypeProcessor());
			}
			
		};
		
	}

}