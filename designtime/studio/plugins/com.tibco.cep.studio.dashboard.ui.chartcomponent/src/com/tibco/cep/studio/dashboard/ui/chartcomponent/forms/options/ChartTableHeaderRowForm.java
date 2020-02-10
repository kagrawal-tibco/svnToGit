package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.FontStylePropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTableHeaderRowForm extends BaseForm {
	
	private Button btn_ShowHeaderRow;
	private SelectionListener btn_ShowHeaderRowSelectionListener;
	
	private SimplePropertyForm categoryColumnForm;
	
	private SimplePropertyForm valueColumnForm;

	public ChartTableHeaderRowForm(FormToolkit formToolKit, Composite parent) {
		super("Header Row", formToolKit, parent, false);
	}
	
	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		
		btn_ShowHeaderRow = createButton(formComposite, "Show Header Row", SWT.CHECK);
		
		categoryColumnForm = new SimplePropertyForm("Category Column Header", formToolKit, formComposite, true);
		categoryColumnForm.addProperty("Label", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnHeaderName"));
		categoryColumnForm.addProperty("Size", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnHeaderFontSize"));
		categoryColumnForm.addProperty(new FontStylePropertyControl(categoryColumnForm, "Font Style", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnHeaderFontStyle")));
		categoryColumnForm.addProperty("Alignment", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "CategoryColumnHeaderAlignment"));
		addForm(categoryColumnForm);		
		categoryColumnForm.init();
		categoryColumnForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		valueColumnForm = new SimplePropertyForm("Value Column Header", formToolKit, formComposite, true);
		valueColumnForm.addProperty("Size", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "ValueColumnHeaderFontSize"));
		valueColumnForm.addProperty(new FontStylePropertyControl(valueColumnForm, "Font Style", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "ValueColumnHeaderFontStyle")));
		valueColumnForm.addProperty("Alignment", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.TEXT_VISUALIZATION, "ValueColumnHeaderAlignment"));
		addForm(valueColumnForm);
		valueColumnForm.init();
		valueColumnForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
	}
	
	@Override
	public void setInput(LocalElement localElement) throws Exception {
		super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
	}
	
	@Override
	protected void doEnableListeners() {
		if (btn_ShowHeaderRowSelectionListener == null){
			btn_ShowHeaderRowSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					showHeaderRowButtonClicked();
				}
				
			};
		}
		btn_ShowHeaderRow.addSelectionListener(btn_ShowHeaderRowSelectionListener);
	}


	@Override
	protected void doDisableListeners() {
		btn_ShowHeaderRow.removeSelectionListener(btn_ShowHeaderRowSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
	}

	@Override
	public void refreshSelections() {
		try {
			boolean showHeader = Boolean.valueOf(localElement.getPropertyValue("ShowHeader"));
			btn_ShowHeaderRow.setSelection(showHeader);
			if (showHeader == true){
				categoryColumnForm.enableAll();
				valueColumnForm.enableAll();
			}
			else {
				categoryColumnForm.disableAll();
				valueColumnForm.disableAll();
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not parse 'show header'",e));
			disableAll();
		}
	}
	
	protected void showHeaderRowButtonClicked() {
		try {
			boolean showHeader = btn_ShowHeaderRow.getSelection();
			localElement.setPropertyValue("ShowHeader",String.valueOf(showHeader));
			if (showHeader == true){
				categoryColumnForm.enableAll();
				valueColumnForm.enableAll();
			}
			else {
				categoryColumnForm.disableAll();
				valueColumnForm.disableAll();
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not set 'show header'",e));
		}
	}

}