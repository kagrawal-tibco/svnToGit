package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartAboutForm extends SimplePropertyForm {

	private PropertyControl aboutPropertyControl;

	public ChartAboutForm(FormToolkit formToolKit, Composite parent) {
		super("About This Chart", formToolKit, parent, false);
		//since this is a common property we can use either CHART_COMPONENT or TEXT_COMPONENT, we will use CHART_COMPONENT
		aboutPropertyControl = addPropertyAsText(null, ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "About This Chart"), true);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		Control control = aboutPropertyControl.createControl(formComposite);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = 150;
		control.setLayoutData(layoutData);
	}

}