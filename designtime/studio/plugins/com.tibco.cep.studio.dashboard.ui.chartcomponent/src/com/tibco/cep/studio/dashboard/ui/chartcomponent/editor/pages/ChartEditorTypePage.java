package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.ChartEditorBasePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.PageUtils;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type.ChartDescForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type.ChartTypeForm;

public class ChartEditorTypePage extends ChartEditorBasePage {

	public ChartEditorTypePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement, "type", "Chart Type");
	}
	
	@Override
	protected void createForm(FormToolkit toolkit, Composite parent) {
		//set layout for the parent
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);
		
		//create description form 
		Section descFormSection = PageUtils.createSection(toolkit,parent);
		ChartDescForm chartDescForm = new ChartDescForm(toolkit,((Composite) descFormSection.getClient()));
		descFormSection.setText(chartDescForm.getTitle());
		chartDescForm.init();
		registerForm(chartDescForm);
		descFormSection.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		
		//create type form 
		Section typeFromSection = PageUtils.createSection(toolkit, parent);
		ChartTypeForm chartTypeForm = new ChartTypeForm(toolkit,(Composite) typeFromSection.getClient(), false);
		typeFromSection.setText(chartTypeForm.getTitle());
		chartTypeForm.init();
		registerForm(chartTypeForm);
		typeFromSection.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
	}

}