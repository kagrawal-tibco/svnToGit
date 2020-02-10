package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.properties;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.ContentDataSettingsForm;

public class ContentDataSection extends AbstractFormPropertySection {
	
	@Override
	protected BaseForm getForm(Composite parent) {
		return new ContentDataSettingsForm(getWidgetFactory(),parent,SWT.HORIZONTAL);
	}
	
	@Override
	protected LocalElement getTargetLocalElement() throws Exception {
		LocalElement targetElement = (LocalElement) ((StructuredSelection) getSelection()).getFirstElement();
		return StateMachineComponentHelper.getContentSeriesConfig((LocalStateVisualization) targetElement);
	}	
	
}