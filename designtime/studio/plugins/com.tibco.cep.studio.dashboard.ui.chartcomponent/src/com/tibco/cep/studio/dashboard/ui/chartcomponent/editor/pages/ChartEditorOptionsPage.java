package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages;

import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;

public class ChartEditorOptionsPage extends ChartEditorChangeableSlavePage {
	
	public ChartEditorOptionsPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement, "options", "Type Options");
	}
	
	@Override
	protected SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String optionsFormProvider = chartType.getEditorOptionsFormProvider();
		return (SlavePageFormsProvider) Class.forName(optionsFormProvider).newInstance();
	}

}