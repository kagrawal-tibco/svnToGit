package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.properties;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.SMComponentGeneralForm;

public class GeneralSection extends AbstractFormPropertySection {

	@Override
	protected BaseForm getForm(Composite parent) {
		return new SMComponentGeneralForm(getWidgetFactory(),parent);
	}

	@Override
	protected LocalElement getTargetLocalElement() throws Exception {
		return (LocalElement) ((StructuredSelection) getSelection()).getFirstElement();
	}

}
