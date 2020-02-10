package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class ChartWizardChangeableSlavePage extends ChartWizardSlavePage {

	protected SlavePageFormsProvider currentSlavePageFormsProvider;

	protected Composite parent;

	public ChartWizardChangeableSlavePage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public ChartWizardChangeableSlavePage(String pageName) {
		super(pageName);
	}

	@Override
	protected BaseForm[] createForms(Composite parent) {
		this.parent = parent;
		try {
			if (this.chartType != null){
				this.currentSlavePageFormsProvider = getSlavePageFormsProvider(chartType);
				//get chart options form provider based on current chart type
				currentSlavePageFormsProvider.createForm(null, parent);
				return currentSlavePageFormsProvider.getForms();
			}
			return new BaseForm[]{};
		} catch (Exception e) {
			String msg = "could not create "+getName()+" page for "+chartType.getName();
			setErrorMessage(msg);
			setPageComplete(false);
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,DashboardChartPlugin.PLUGIN_ID,msg,e));
			return new BaseForm[]{};
		}
	}

	protected abstract SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

	@Override
	public void setChartType(ChartType chartType) throws Exception {
		//check for parent since setChartType can be called before the page is created
		if (parent == null){
			//if page is not created, then just set the chart type
			this.chartType = chartType;
		}
		else if (chartType.equals(this.chartType) == false){
			this.chartType = chartType;
			SlavePageFormsProvider newSlavePageFormsProvider = getSlavePageFormsProvider(this.chartType);
			//check if the existing forms provider and the new forms providers are the same or not
			if (currentSlavePageFormsProvider == null || currentSlavePageFormsProvider.getClass().equals(newSlavePageFormsProvider.getClass()) == false){
				if (currentSlavePageFormsProvider != null) {
					//they are not same, so we need to change the forms
					//de-register all forms
					for (BaseForm baseForm : currentSlavePageFormsProvider.getForms()) {
						baseForm.disableAll();
						baseForm.disableListeners();
					}
					//remove all children controls from parent
					for (Control child : parent.getChildren()) {
						child.dispose();
					}
				}
				this.currentSlavePageFormsProvider = newSlavePageFormsProvider;
				currentSlavePageFormsProvider.createForm(null, parent);
				parent.layout(true);
				forms = currentSlavePageFormsProvider.getForms();
			}
		}

	}

}