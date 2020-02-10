package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartPreviewForm extends BaseForm {
	
	private ChartPreview chartPreview;
	
	private LocalPreviewDataElement localPreviewDataElement;
	
	public ChartPreviewForm(FormToolkit formToolKit, Composite parent) {
		super("Chart Preview", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new FillLayout());
		//create chart preview 
		chartPreview = new ChartPreview(formComposite,SWT.NONE);		
		//load chart preview 
		chartPreview.load();
	}
	
	@Override
	public void setInput(LocalElement localElement) {
		try {
			super.setInput(localElement);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not set input on "+getTitle(),e));
			chartPreview.disable(false, "could not update preview...");
		}
	}
	
	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		localPreviewDataElement = (LocalPreviewDataElement) newLocalElement;
	}
	
	@Override
	protected void doEnableListeners() {
	}
	
	@Override
	protected void doDisableListeners() {
	}

	@Override
	public void refreshEnumerations() {
	}

	@Override
	public void refreshSelections() {
		switch (localPreviewDataElement.getEnablementLevel()) {
			case NEVER : 
				chartPreview.disable(true, localPreviewDataElement.getMessage());
				return;
			case NO : 
				chartPreview.disable(false, localPreviewDataElement.getMessage());
				return;
			case YES : 
				chartPreview.setCompleteXML(localPreviewDataElement.getCompleteXML());
				return;					
		}
	}

}