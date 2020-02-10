package com.tibco.cep.studio.wizard.as.internal.ui.components;

import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;

public class ASDebugWizardPageComponent extends Composite {

	public ASDebugWizardPageComponent(Composite parent, IController<?> controller) {
		super(parent, NONE);
		initialize();
	}

	private void initialize() {
		initUI();
	}

	private void initUI() {
		this.setLayout(new FillLayout());
		Composite container = new Composite(this, NONE);
		container.setLayout(new FillLayout());
		Label label = new Label(container, NONE);
		label.setText("Test Page"); //$NON-NLS-1$
	}

}
