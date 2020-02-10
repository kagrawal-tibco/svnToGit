package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class AbstractFormPropertySection extends AbstractPropertySection {

	protected BaseForm form;

	@Override
	public final void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout());
		form = getForm(parent);
		form.init();
		GridData layoutData = new GridData(SWT.FILL,SWT.FILL,true,true);
		layoutData.widthHint = 1;
		form.getControl().setLayoutData(layoutData);
	}

	protected abstract BaseForm getForm(Composite parent);

	@Override
	public void aboutToBeShown() {
		if (form.getInput() != null) {
			form.enableListeners();
		}
	}

	@Override
	public void refresh() {
		LocalElement targetElement = null;
		try {
			targetElement = getTargetLocalElement();
		} catch (Exception e) {
			form.log(new Status(IStatus.ERROR, form.getPluginId(), "could not set extract input from "+ getSelection(), e));
			form.disableAll();
			return;
		}
		try {
			if (targetElement != null /*&& form.getInput() != targetElement*/) {
				form.setInput(targetElement);
			}
		} catch (Exception e) {
			form.log(new Status(IStatus.ERROR, form.getPluginId(), "could not set input " + form + " using " + targetElement, e));
			form.disableAll();
			MessageDialog.openError(Display.getCurrent().getActiveShell(), form.getTitle(), "could not set "+targetElement.getName()+" as input");
		}
	}

	protected abstract LocalElement getTargetLocalElement() throws Exception;

	@Override
	public void aboutToBeHidden() {
		try {
			if (form.getInput() != null) {
				form.disableListeners();
			}
		} catch (SWTException ex) {
			if (ex.code != SWT.ERROR_WIDGET_DISPOSED){
				throw ex;
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		try {
			if (form != null) {
				form.dispose();
				form = null;
			}
		} catch (SWTException ex) {
			if (ex.code != SWT.ERROR_WIDGET_DISPOSED){
				throw ex;
			}
		}
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
}
