package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.PageUtils;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public abstract class BaseChartOptionsFormProvider implements SlavePageFormsProvider {
	
	private List<BaseForm> forms;
	
	protected BaseChartOptionsFormProvider() {
		forms = new LinkedList<BaseForm>();
	}
	
	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		//default does nothing
	}
	
	@Override
	public final void createForm(FormToolkit formToolkit, Composite parent) {
		//set layout for the parent
		GridLayout layout = new GridLayout(2,true);
		layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);
		
		//first row 
		Composite firstRowComposite = formToolkit.createComposite(parent, SWT.NONE);
		firstRowComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		firstRowComposite.setLayout(new GridLayout());
		addFirstRowForms(formToolkit,firstRowComposite);
		
		//second row 
		Composite secondRowComposite = formToolkit.createComposite(parent, SWT.NONE);
		secondRowComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		secondRowComposite.setLayout(new GridLayout());
		addSecondRowForms(formToolkit,secondRowComposite);		
	}
	
	protected abstract void addFirstRowForms(FormToolkit formToolkit, Composite parent);
	
	protected abstract void addSecondRowForms(FormToolkit formToolkit, Composite parent);

	protected Section createSection(FormToolkit toolkit, Composite parent){
		Section section = PageUtils.createSection(toolkit, parent);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return section;
	}
	
	protected void addForm(BaseForm form){
		form.init();
		forms.add(form);
	}

	@Override
	public BaseForm[] getForms() {
		return forms.toArray(new BaseForm[forms.size()]);
	}

}
