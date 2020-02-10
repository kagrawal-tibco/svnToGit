package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.ControllablePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.preview.ChartPreviewForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsFormWizardPage;

public abstract class ChartWizardBasePage extends BaseViewsFormWizardPage implements ControllablePage {

	private ChartPreviewForm chartPreviewForm;

	protected ExceptionHandler exceptionHandler;

	public ChartWizardBasePage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public ChartWizardBasePage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		//create form + chart preview holder
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout(2,false));
		//add form body composite to hold main form
		Composite formBodyComposite = new Composite(pageComposite, SWT.NONE);
		formBodyComposite.setLayout(new GridLayout());
		forms = createForms(formBodyComposite);
		for (BaseForm form : forms) {
			form.getControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		}
		GridData formBodyCompositeLayoutData = new GridData(SWT.FILL,SWT.FILL,true,true);
		formBodyCompositeLayoutData.widthHint = 500;
		formBodyComposite.setLayoutData(formBodyCompositeLayoutData);
		//add chart preview form
		chartPreviewForm = new ChartPreviewForm(null,pageComposite);
		chartPreviewForm.init();
		GridData chartPreviewGridData = new GridData(SWT.FILL,SWT.TOP,false,false);
		chartPreviewGridData.widthHint = 200;
		chartPreviewGridData.heightHint = 140;
		chartPreviewForm.getControl().setLayoutData(chartPreviewGridData);
		setControl(pageComposite);
	}


	@Override
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public void populateControl() {
		for (BaseForm form : forms) {
			form.setExceptionHandler(exceptionHandler);
		}
		super.populateControl();
	}

	@Override
	public void refresh() throws Exception {
		populateControl();
	}

	@Override
	public void refreshPreview(LocalElement localElement) {
		try {
			chartPreviewForm.setInput(localElement);
		} catch (Exception e) {
			String message = "could not refresh preview on page['"+getTitle()+"'] for "+this.localElement;
			exceptionHandler.log(new Status(IStatus.WARNING,exceptionHandler.getPluginId(),message,e));
		}
	}

	ChartPreviewForm getChartPreviewForm(){
		return chartPreviewForm;
	}
}