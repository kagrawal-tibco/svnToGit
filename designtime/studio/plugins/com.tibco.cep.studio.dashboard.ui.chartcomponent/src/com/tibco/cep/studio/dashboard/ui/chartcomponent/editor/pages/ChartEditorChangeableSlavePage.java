package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.ChartEditorBaseSlavePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class ChartEditorChangeableSlavePage extends ChartEditorBaseSlavePage {
	
	protected FormToolkit toolkit;
	protected Composite parent;
	
	protected SlavePageFormsProvider currentSlavePageFormsProvider;
	
	public ChartEditorChangeableSlavePage(FormEditor editor, LocalElement localElement, String id, String title) {
		super(editor, localElement, id, title);
	}

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
			if (currentSlavePageFormsProvider != null && currentSlavePageFormsProvider.getClass().equals(newSlavePageFormsProvider.getClass()) == false){
				//they are not same, so we need to change the forms 
				//de-register all forms 
				for (BaseForm baseForm : currentSlavePageFormsProvider.getForms()) {
					baseForm.disableAll();
					baseForm.disableListeners();
					deregisterForm(baseForm);
				}
				//remove all children controls from parent 
				for (Control child : parent.getChildren()) {
					child.dispose();
				}
				this.currentSlavePageFormsProvider = newSlavePageFormsProvider;
				addSlaveForms(toolkit, parent);
				//we need to populate the controls 
				refresh();
			}
		}
	}
	
	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		this.chartSubTypes = chartSubTypes;
		if (this.currentSlavePageFormsProvider != null) {
			this.currentSlavePageFormsProvider.setChartSubTypes(chartSubTypes);
			parent.layout(true);
		}
	}
	
	@Override
	protected void createForm(FormToolkit toolkit, Composite parent) throws Exception {
		this.toolkit = toolkit;
		this.parent = parent;
		if (this.chartType != null){
			this.currentSlavePageFormsProvider = getSlavePageFormsProvider(chartType);
			//get chart options form provider based on current chart type 
			addSlaveForms(toolkit, parent);
			this.currentSlavePageFormsProvider.setChartSubTypes(chartSubTypes);
		}
	}

	private void addSlaveForms(FormToolkit toolkit, Composite parent) {
		if (currentSlavePageFormsProvider != null){
			currentSlavePageFormsProvider.createForm(toolkit, parent);
			BaseForm[] forms = currentSlavePageFormsProvider.getForms();
			for (BaseForm baseForm : forms) {
				registerForm(baseForm);
			}
			parent.layout(true);
		}
	}
	
	protected abstract SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

}