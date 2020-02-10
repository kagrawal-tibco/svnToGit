package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.overlaid;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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
import com.tibco.cep.studio.dashboard.ui.forms.SpinnerPropertyControl;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class OverlaidOptionsFormProvider extends BaseChartGraphOptionsFormProvider {
	
	private TypeSpecificForm typeSpecificForm;

	public OverlaidOptionsFormProvider() {
	}

	@Override
	protected BaseForm getTypeSpecificForm(FormToolkit formToolkit, Section section) {
		typeSpecificForm = new TypeSpecificForm(formToolkit,(Composite) section.getClient());
		return typeSpecificForm;
	}
	
	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		if (Arrays.asList(chartSubTypes).contains(ChartTypeRegistry.getInstance().get("ColumnLineScatter").getSubType("Overlapped")) == true){
			typeSpecificForm.overlapPercentageControl.enable();
			typeSpecificForm.overlapPercentageControl.setMinimum(1);
			typeSpecificForm.overlapPercentageControl.setMaximum(99);
		}
		else {
			typeSpecificForm.overlapPercentageControl.disable();
			typeSpecificForm.overlapPercentageControl.setMinimum(0);
			typeSpecificForm.overlapPercentageControl.setMaximum(100);
		}
		if (Arrays.asList(chartSubTypes).contains(ChartTypeRegistry.getInstance().get("ColumnLineScatter").getSubType("NoPoints")) == true){
			typeSpecificForm.plotShapeControl.disable();
			typeSpecificForm.plotShapeDimensionControl.disable();
		}
		else {
			typeSpecificForm.plotShapeControl.enable();
			typeSpecificForm.plotShapeDimensionControl.enable();
		}
		
	}
	
	class TypeSpecificForm extends BaseForm {

		private SpinnerPropertyControl overlapPercentageControl;
		private PropertyControl plotShapeControl;
		private PropertyControl plotShapeDimensionControl;

		public TypeSpecificForm(FormToolkit formToolKit, Composite parent) {
			super("Data Layout", formToolKit, parent, false);
		}
		
		@Override
		public void setInput(LocalElement localElement) throws Exception {
			super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
		}

		@Override
		public void init() {
			formComposite.setLayout(new FillLayout(SWT.VERTICAL));
			//column options form 
			SimplePropertyForm columnOptionsForm = new SimplePropertyForm("Column",formToolKit,formComposite,true);
			columnOptionsForm.addProperty("Bar Width", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "BarWidth"));
			//columnOptionsForm.addProperty("Top Cap Thickness", new SpanSpinnerControl(columnOptionsForm,"Top Cap Thickness",ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "TopCapThickness")));
			columnOptionsForm.addProperty("Top Cap Thickness", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "TopCapThickness"));
			overlapPercentageControl = (SpinnerPropertyControl) columnOptionsForm.addProperty("Bar Overlap", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.BAR_CHART_VISUALIZATION, "OverLapPercentage"));
			columnOptionsForm.init();
			addForm(columnOptionsForm);
			//line options form 
			SimplePropertyForm lineOptionsForm = new SimplePropertyForm("Line",formToolKit,formComposite,true);
			plotShapeControl = lineOptionsForm.addProperty("Plot Shape", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "PlotShape"));
			plotShapeDimensionControl = lineOptionsForm.addProperty("Plot Shape Dimension", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "PlotShapeDimension"));
			lineOptionsForm.addProperty("Line Thickness", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.LINE_CHART_VISUALIZATION, "LineThickness"));
			lineOptionsForm.init();
			addForm(lineOptionsForm);
		}

		@Override
		protected void doDisableListeners() {
		}

		@Override
		protected void doEnableListeners() {
		}


		@Override
		public void refreshEnumerations() {
		}

		@Override
		public void refreshSelections() {
		}
		
	}
	
}
