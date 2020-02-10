package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartSizeForm extends BaseForm {

	private Label lbl_Width;
	private Label lbl_Height;
	
	private GridChooser gridChooser;
	private ISelectionChangedListener gridChooserSelectionChangedListener;
	

	public ChartSizeForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Chart Size", formToolKit, parent, showGroup);
	}
	
	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(3,false));
		//first row first column 
		Label rowOneLeftFiller = createLabel(formComposite, "", SWT.NONE);
		rowOneLeftFiller.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		//first row second column
		lbl_Width = createLabel(formComposite, "", SWT.CENTER);
		lbl_Width.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,true));
		//first row third column
		Label rowOneRightFiller = createLabel(formComposite, "", SWT.NONE);
		rowOneRightFiller.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		//second row first column
		lbl_Height = createLabel(formComposite, "", SWT.RIGHT);
		lbl_Height.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true));
		//second row second column
		gridChooser = new GridChooser(formComposite, SWT.NONE);
		GridData gridChooserGridData = new GridData(SWT.CENTER,SWT.CENTER,false,false);
		gridChooserGridData.heightHint = 3 * GridChooser.CELL_SIZE;
		gridChooserGridData.widthHint = 3 * GridChooser.CELL_SIZE;
		gridChooser.setLayoutData(gridChooserGridData);
		//second row third column
		Label rowTwoRightFiller = createLabel(formComposite, "", SWT.NONE);
		rowTwoRightFiller.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		//third row all columns
		Label rowThreeFiller = createLabel(formComposite, "", SWT.NONE);
		rowThreeFiller.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,3,1));
		
	}
	
	@Override
	protected void doEnableListeners() {
		if (gridChooserSelectionChangedListener == null){
			gridChooserSelectionChangedListener = new ISelectionChangedListener(){

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					gridSizeChanged();
					
				}
				
			};
		}
		gridChooser.addSelectionChangedListener(gridChooserSelectionChangedListener);
	}

	@Override
	protected void doDisableListeners() {
		gridChooser.removeSelectionChangedListener(gridChooserSelectionChangedListener);
	}

	@Override
	public void refreshEnumerations() {
		//do nothing
	}

	@Override
	public void refreshSelections() {
		int rowSpan = 1;
		try {
			rowSpan = Integer.parseInt(localElement.getPropertyValue("RowSpan"));
		} catch (NumberFormatException e) {
			log(new Status(IStatus.WARNING,getPluginId(),"could not parse row span property, setting it to 1",e));
		} catch (Exception e) {
			log(new Status(IStatus.WARNING,getPluginId(),"could not read row span property, setting it to 1",e));
		}
		int colSpan = 1;
		try {
			colSpan = Integer.parseInt(localElement.getPropertyValue("ColSpan"));
		} catch (NumberFormatException e) {
			log(new Status(IStatus.WARNING,getPluginId(),"could not parse col span property, setting it to 1",e));
		} catch (Exception e) {
			log(new Status(IStatus.WARNING,getPluginId(),"could not read col span property, setting it to 1",e));
		}
		lbl_Width.setText("Width: "+colSpan+"x");
		lbl_Height.setText("Height: "+rowSpan+"y");
		gridChooser.setSelection(new StructuredSelection(new Point(colSpan-1,rowSpan-1)));
	}

	protected void gridSizeChanged() {
		Point cell = (Point) ((StructuredSelection)gridChooser.getSelection()).getFirstElement();
		int rowSpan = cell.y + 1;
		int colSpan = cell.x + 1;
		lbl_Width.setText("Width: "+colSpan+"x");
		lbl_Height.setText("Height: "+rowSpan+"y");		
		try {
			localElement.setPropertyValue("RowSpan", String.valueOf(rowSpan));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not set height span to "+rowSpan,e));
		}
		try {
			localElement.setPropertyValue("ColSpan", String.valueOf(colSpan));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not set width span to "+colSpan,e));
		}
	}
}
