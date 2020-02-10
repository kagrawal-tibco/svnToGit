package com.tibco.cep.studio.dashboard.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class BaseViewsFormWizardPage extends BaseViewsWizardPage {

	protected BaseForm[] forms;

	protected LocalElement localElement;

	public BaseViewsFormWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public BaseViewsFormWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new FillLayout());
		forms = createForms(pageComposite);
		setControl(pageComposite);
	}

	protected abstract BaseForm[] createForms(Composite parent);

	public void enable() {
		for (BaseForm baseForm : forms) {
			baseForm.enableAll();
		}
	}

	public void disable() {
		for (BaseForm baseForm : forms) {
			baseForm.disableAll();
		}
	}

	public void setInput(LocalElement localElement) {
		this.localElement = localElement;
	}

	@Override
	public void populateControl() {
		try {
			for (BaseForm baseForm : forms) {
				baseForm.setInput(localElement);
			}
			setPopulated(true);
		} catch (Exception e) {
			setErrorMessage("An error occurred during refreshing...");
			disable();
		} 
	}

}
