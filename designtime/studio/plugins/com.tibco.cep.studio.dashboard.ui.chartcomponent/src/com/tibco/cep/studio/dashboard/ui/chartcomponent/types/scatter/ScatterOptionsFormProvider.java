package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.scatter;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartGraphOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ScatterOptionsFormProvider extends BaseChartGraphOptionsFormProvider {
	
	public ScatterOptionsFormProvider(){
		super();
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		SimplePropertyForm form = new SimplePropertyForm("Data Layout",formToolkit,(Composite) section.getClient(),false){

			@Override
			public void setInput(LocalElement localElement) throws Exception {
				super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
			}
			
		};
		form.addProperty("Plot Shape", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.SCATTER_CHART_VISUALIZATION, "PlotShape"));
		form.addProperty("Plot Shape Dimension", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.SCATTER_CHART_VISUALIZATION, "PlotShapeDimension"));
		return form;
	}

	
}
