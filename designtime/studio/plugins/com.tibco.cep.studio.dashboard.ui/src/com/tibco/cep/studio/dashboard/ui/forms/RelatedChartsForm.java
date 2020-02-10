package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.widgets.MemberComponentsEditor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class RelatedChartsForm extends BaseForm {

	private int style;

	private MemberComponentsEditor selector;

	public RelatedChartsForm(FormToolkit formToolKit, Composite parent, boolean showGroup, int style) {
		super("Related Charts", formToolKit, parent, showGroup);
		this.style = style;
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		selector = new MemberComponentsEditor("Chart ", BEViewsElementNames.RELATED_COMPONENT, formToolKit, formComposite, style | SWT.MULTI, true);
		selector.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	@Override
	protected void doDisableListeners() {
		selector.setProcessEvents(false);
	}

	@Override
	public void refreshEnumerations() {
		try {
			List<LocalElement> choices = localElement.getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
			choices.remove(localElement);
			selector.setEnumeration(choices);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		try {
			selector.setLocalElement(localElement);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	protected void doEnableListeners() {
		selector.setProcessEvents(true);
	}

	@Override
	public void enableAll() {
		super.enableAll();
		selector.setEnabled(true);
	}

	@Override
	public void disableAll() {
		super.disableAll();
		selector.setEnabled(false);
	}

}
