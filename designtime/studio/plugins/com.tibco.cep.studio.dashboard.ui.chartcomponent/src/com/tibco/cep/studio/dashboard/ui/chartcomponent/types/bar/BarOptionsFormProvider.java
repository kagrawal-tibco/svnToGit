package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.bar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartGridLinesForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartGraphOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.ui.forms.SpinnerPropertyControl;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class BarOptionsFormProvider extends BaseChartGraphOptionsFormProvider {
	
	private SpinnerPropertyControl overlapPercentageControl;

	public BarOptionsFormProvider(){
		super();
	}
	
	@Override
	protected ChartGridLinesForm createGridLinesForm(FormToolkit formToolkit, Section section) {
		return new ChartGridLinesForm(formToolkit, (Composite) section.getClient(), SWT.HORIZONTAL);
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		SimplePropertyForm form = new SimplePropertyForm("Data Layout",formToolkit,(Composite) section.getClient(),false) {
			
			@Override
			public void setInput(LocalElement localElement) throws Exception {
				super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
			}
			
		};
		form.addProperty("Bar Width", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "BarWidth"));
//		form.addProperty(new PercentageSpinnerControl(form,"Top Cap Thickness",ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "TopCapThickness")));
//		form.addProperty("Top Cap Thickness", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "TopCapThickness"));
		overlapPercentageControl = (SpinnerPropertyControl) form.addProperty("Bar Overlap", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "OverLapPercentage"));
		return form;
	}
	
	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		if (chartSubTypes[0].getId().equals("Overlapped") == true){
			overlapPercentageControl.enable();
			overlapPercentageControl.setMinimum(1);
			overlapPercentageControl.setMaximum(99);
		}
		else {
			overlapPercentageControl.disable();
			overlapPercentageControl.setMinimum(0);
			overlapPercentageControl.setMaximum(100);
		}
	}
}
