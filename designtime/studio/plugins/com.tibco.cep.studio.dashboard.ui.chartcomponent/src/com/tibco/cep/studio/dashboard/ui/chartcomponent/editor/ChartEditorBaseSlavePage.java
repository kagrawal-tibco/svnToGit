package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor;

import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;

public abstract class ChartEditorBaseSlavePage extends ChartEditorBasePage implements SlavePage {
	
	protected ChartType chartType;
	protected ChartSubType[] chartSubTypes;
	
	public ChartEditorBaseSlavePage(FormEditor editor, LocalElement localElement, String id, String title) {
		super(editor, localElement, id, title);
	}

	@Override
	public ChartType getChartType() {
		return chartType;
	}

	@Override
	public void setChartType(ChartType chartType) throws Exception {
		this.chartType = chartType;
	}

	@Override
	public ChartSubType[] getChartSubTypes() {
		return chartSubTypes;
	}

	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) throws Exception {
		this.chartSubTypes = chartSubTypes;
	}

}
