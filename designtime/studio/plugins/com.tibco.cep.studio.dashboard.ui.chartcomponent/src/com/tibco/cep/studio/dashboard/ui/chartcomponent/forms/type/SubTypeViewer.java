package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubTypeGroup;

public class SubTypeViewer extends Viewer {
	
	private FormToolkit toolkit;
	
	private Composite composite;
	
	private ChartSubTypeGroup input;
	
	private ChartSubType selectedSubType;
	
	private SelectionListener buttonSelectionListener;
	
	public SubTypeViewer(FormToolkit toolkit, Composite parent) {
		this.toolkit = toolkit;
		if (this.toolkit == null) {
			this.composite = new Composite(parent,SWT.NONE);
		}
		else {
			this.composite = this.toolkit.createComposite(parent, SWT.NONE);
		}
		this.buttonSelectionListener = new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button b = (Button) e.getSource();
				ChartSubType subType = (ChartSubType) b.getData();
				if (selectedSubType != subType){
					if (selectedSubType != null) {
						unselect(getButton(selectedSubType));
					}
					selectedSubType = subType;
					select(b);
					fireSelectionChanged(new SelectionChangedEvent(SubTypeViewer.this,getSelection()));
				}				
			}
			
		};
	}

	@Override
	public Control getControl() {
		return composite;
	}

	@Override
	public Object getInput() {
		return input;
	}

	@Override
	public ISelection getSelection() {
		if (selectedSubType == null){
			return StructuredSelection.EMPTY;
		}
		return new StructuredSelection(selectedSubType);
	}

	@Override
	public void refresh() {
		//remove all children from the composite 
		Control[] children = composite.getChildren();
		for (Control child : children) {
			if (child instanceof Button){
				((Button) child).removeSelectionListener(buttonSelectionListener);
			}
			child.dispose();
		}
		GridLayout layout = new GridLayout(input.getSubTypes().length,true);
		layout.horizontalSpacing = 20;
		this.composite.setLayout(layout);
		//now go through sub types
		int i = 0;
		String[] descriptions = new String[input.getSubTypes().length];
		for (ChartSubType subType : input.getSubTypes()) {
			//create button 
			Button btn_SubType = null;
			if (this.toolkit == null){
				btn_SubType = new Button(composite,SWT.PUSH);
			}
			else {
				btn_SubType = this.toolkit.createButton(composite, null, SWT.PUSH);
			}
			GridData btn_SubTypeLayoutData = new GridData();
			//btn_SubTypeLayoutData.heightHint = 45;
			//btn_SubTypeLayoutData.widthHint = 45;	
			btn_SubType.setLayoutData(btn_SubTypeLayoutData);
			
			btn_SubType.setData(subType);
			btn_SubType.setToolTipText(subType.getDescription());
			
			unselect(btn_SubType);
			
			btn_SubType.addSelectionListener(buttonSelectionListener);
			
			descriptions[i] = subType.getDescription();
			i++;
			
			if (i == descriptions.length){
				for (String description : descriptions) {
					//create text 
					Label lbl_Description = null;
					if (this.toolkit == null){
						lbl_Description = new Label(composite, SWT.WRAP);
					}
					else {
						lbl_Description = this.toolkit.createLabel(composite, null, SWT.WRAP);
					}
					GridData txt_DescriptionLayoutData = new GridData(SWT.BEGINNING, SWT.TOP, true, false);
					txt_DescriptionLayoutData.widthHint = 90;
//					txt_DescriptionLayoutData.heightHint = 80;					
					lbl_Description.setLayoutData(txt_DescriptionLayoutData);
					lbl_Description.setText(description);
				}
				i = 0;
			}
		}
		//update selection, if any
		if (selectedSubType != null){
			Button button = getButton(selectedSubType);
			if (button != null){
				select(button);
			}
		}
	}

	@Override
	public void setInput(Object input) {
		if (input instanceof ChartSubTypeGroup){
			this.input = (ChartSubTypeGroup) input;
			refresh();
		}
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		if (selectedSubType != null){
			unselect(getButton(selectedSubType));
		}
		if (selection.isEmpty() == true){
			selection = null;
		}
		else {
			selectedSubType = (ChartSubType) ((StructuredSelection)selection).getFirstElement();
			select(getButton(selectedSubType));			
		}
	}

	private Button getButton(ChartSubType chartSubType){
		for (Control child : composite.getChildren()) {
			if (child.getData() == selectedSubType){
				return (Button) child;
			}
		}
		return null;
	}
	
	private void select(Button button){
		ChartSubType chartSubType = (ChartSubType) button.getData();
		button.setImage(DashboardChartPlugin.getDefault().getImageRegistry().get("subtypes/"+input.getId().toLowerCase()+chartSubType.getSelectedIconName()));
	}
	
	private void unselect(Button button){
		ChartSubType chartSubType = (ChartSubType) button.getData();
		button.setImage(DashboardChartPlugin.getDefault().getImageRegistry().get("subtypes/"+input.getId().toLowerCase()+chartSubType.getIconName()));
	}
	
	public void setEnabled(boolean enabled){
		for (Control child : composite.getChildren()) {
			child.setEnabled(enabled);
		}
	}
}