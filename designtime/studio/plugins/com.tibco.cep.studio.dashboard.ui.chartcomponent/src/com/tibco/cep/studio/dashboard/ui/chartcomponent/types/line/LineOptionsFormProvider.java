package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.line;

import java.util.Arrays;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartGraphOptionsFormProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LineOptionsFormProvider extends BaseChartGraphOptionsFormProvider {
	
	private PropertyControl plotShapeControl;
	private PropertyControl plotShapeDimensionControl;

	public LineOptionsFormProvider(){
		super();
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		SimplePropertyForm form = new SimplePropertyForm("Data Layout",formToolkit,(Composite) section.getClient(),false) {
			
			@Override
			public void setInput(LocalElement localElement) throws Exception {
				super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
			}
			
		};
		plotShapeControl = form.addProperty("Plot Shape", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "PlotShape"));
		plotShapeDimensionControl = form.addProperty("Plot Shape Dimension", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "PlotShapeDimension"));
		form.addProperty("Line Thickness", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "LineThickness"));
		return form;
	}

	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		if (Arrays.asList(chartSubTypes).contains(ChartTypeRegistry.getInstance().get("Line").getSubType("NoPoints")) == true){
			plotShapeControl.disable();
			plotShapeDimensionControl.disable();
		}
		else {
			plotShapeControl.enable();
			plotShapeDimensionControl.enable();
		}
	}
	
}
