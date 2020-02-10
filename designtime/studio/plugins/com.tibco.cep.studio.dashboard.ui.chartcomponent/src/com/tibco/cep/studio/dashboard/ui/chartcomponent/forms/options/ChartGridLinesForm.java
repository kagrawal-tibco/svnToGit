package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartGridLinesForm extends BaseForm {

	private Button btn_CategoryAxisGridLines;
	private SelectionListener btn_CategoryAxisGridLinesSelectionListener;
	
	private Button btn_ValueAxisGridLines;
	private SelectionListener btn_ValueAxisGridLinesSelectionListener;
	
	private int orientation;

	public ChartGridLinesForm(FormToolkit formToolKit, Composite parent, int orientation) {
		super("Grid Lines", formToolKit, parent, false);
		if (orientation != SWT.VERTICAL && orientation != SWT.HORIZONTAL) {
			throw new IllegalArgumentException("Illegal orientation value");
		}
		this.orientation = orientation;
	}
	

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2,true));
		
		btn_CategoryAxisGridLines = createButton(formComposite, "Category Axis Grid Lines", SWT.CHECK);
		
		btn_ValueAxisGridLines = createButton(formComposite, "Value Axis Grid Lines", SWT.CHECK);
		
	}
	
	@Override
	protected void doEnableListeners() {
		if (btn_CategoryAxisGridLinesSelectionListener == null){
			btn_CategoryAxisGridLinesSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					gridLinesButtonClicked();
					
				}
				
			};
		}
		btn_CategoryAxisGridLines.addSelectionListener(btn_CategoryAxisGridLinesSelectionListener);
		
		if (btn_ValueAxisGridLinesSelectionListener == null){
			btn_ValueAxisGridLinesSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					gridLinesButtonClicked();
					
				}
				
			};
		}
		btn_ValueAxisGridLines.addSelectionListener(btn_ValueAxisGridLinesSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		btn_CategoryAxisGridLines.removeSelectionListener(btn_CategoryAxisGridLinesSelectionListener);
		btn_ValueAxisGridLines.removeSelectionListener(btn_ValueAxisGridLinesSelectionListener);		
	}

	@Override
	public void refreshEnumerations() {
		//do nothing
		
	}

	@Override
	public void refreshSelections() {
		try {
			String value = localElement.getPropertyValue("GridStyle");
			LineEnum lineEnum = LineEnum.get(value.toLowerCase());
			if (orientation == SWT.HORIZONTAL) {
				if (lineEnum.compareTo(LineEnum.HORIZONTAL) == 0){
					lineEnum = LineEnum.VERTICAL;
				}
				else if (lineEnum.compareTo(LineEnum.VERTICAL) == 0){
					lineEnum = LineEnum.HORIZONTAL;
				}
			}
			switch (lineEnum) {
				case HORIZONTAL:
					btn_ValueAxisGridLines.setSelection(true);
					btn_CategoryAxisGridLines.setSelection(false);
					break;
				case VERTICAL:
					btn_ValueAxisGridLines.setSelection(false);
					btn_CategoryAxisGridLines.setSelection(true);
					break;
				case BOTH:
					btn_ValueAxisGridLines.setSelection(true);
					btn_CategoryAxisGridLines.setSelection(true);
					break;
				case NONE:
					btn_ValueAxisGridLines.setSelection(false);
					btn_CategoryAxisGridLines.setSelection(false);
					break;
			}
		} catch (Exception e) {
			disableAll();
			log(new Status(IStatus.ERROR,getPluginId(),"could not parse 'GridStyle' property",e));
		}
	}
	
	protected void gridLinesButtonClicked() {
		try {
			LineEnum lineEnum = LineEnum.NONE;
			if (btn_CategoryAxisGridLines.getSelection() && btn_ValueAxisGridLines.getSelection()){
				lineEnum = LineEnum.BOTH;
			}
			else if (btn_CategoryAxisGridLines.getSelection() == true){
				lineEnum = LineEnum.VERTICAL;
			}
			else if (btn_ValueAxisGridLines.getSelection() == true){
				lineEnum = LineEnum.HORIZONTAL;
			}
			if (orientation == SWT.HORIZONTAL) {
				if (lineEnum.compareTo(LineEnum.HORIZONTAL) == 0){
					lineEnum = LineEnum.VERTICAL;
				}
				else if (lineEnum.compareTo(LineEnum.VERTICAL) == 0){
					lineEnum = LineEnum.HORIZONTAL;
				}
			}
			localElement.setPropertyValue("GridStyle", lineEnum.getLiteral());	
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'GridStyle' property",e));
		}
	}
	
}