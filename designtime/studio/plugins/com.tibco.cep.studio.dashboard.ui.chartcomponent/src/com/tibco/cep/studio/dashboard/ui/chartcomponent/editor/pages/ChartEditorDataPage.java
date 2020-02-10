package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages;

import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;

public class ChartEditorDataPage extends ChartEditorChangeableSlavePage {

	public ChartEditorDataPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement, "data", "Data Settings");
	}

	@Override
	protected SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String dataFormProvider = chartType.getEditorDataFormProvider();
		return (SlavePageFormsProvider) Class.forName(dataFormProvider).newInstance();
	}

}