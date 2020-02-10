package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.resetWidget;
import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.setErrorWidget;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainSimplePageWidgetListener extends AbstractDomainPagesListener{
	
	private DomainFormViewer formViewer;
	private TableViewer viewer;
	/**
	 * @param formViewer
	 */
	public DomainSimplePageWidgetListener(DomainFormViewer formViewer) {
		this.formViewer = formViewer;
		viewer = formViewer.getViewer();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == formViewer.getSimpleValueText()) {
			resetWidget(formViewer.getSimpleValueText());
			if (!DomainUtils.isValidSimpleStringValue(formViewer.getSimpleValueText().getText().trim())) {
				setErrorWidget(formViewer.getSimpleValueText(), DOMAIN_DATA_TYPES.STRING.getLiteral());
				formViewer.getEditor().modified();//Domain Editor Dirty
			} else {
				setSimpleDomainValue(viewer, formViewer.getSimpleValueText());
			}
		}
		if (e.getSource() == formViewer.getSimpleIntValueText()) {
			resetWidget(formViewer.getSimpleIntValueText());
			if (!DomainUtils.isNumeric(formViewer.getSimpleIntValueText().getText().trim())) {
				setErrorWidget(formViewer.getSimpleIntValueText(), DOMAIN_DATA_TYPES.INTEGER.getLiteral());
				formViewer.getEditor().modified();//Domain Editor Dirty
			} else {
				setSimpleDomainValue(viewer, formViewer.getSimpleIntValueText());
			}
		}
		if (e.getSource() == formViewer.getSimpleDoubleValueText()) {
			resetWidget(formViewer.getSimpleDoubleValueText());
			if (!DomainUtils.isDouble(formViewer.getSimpleDoubleValueText().getText().trim())) {
				setErrorWidget(formViewer.getSimpleDoubleValueText(), DOMAIN_DATA_TYPES.DOUBLE.getLiteral());
				formViewer.getEditor().modified();//Domain Editor Dirty
			} else {
				setSimpleDomainValue(viewer, formViewer.getSimpleDoubleValueText());
			}
		}
		if (e.getSource() == formViewer.getSimpleLongValueText()) {
			resetWidget(formViewer.getSimpleLongValueText());
			if (!DomainUtils.isLong(formViewer.getSimpleLongValueText().getText().trim())) 
			{
				setErrorWidget(formViewer.getSimpleLongValueText(), DOMAIN_DATA_TYPES.LONG.getLiteral());
				formViewer.getEditor().modified();//Domain Editor Dirty
			} else {
				setSimpleDomainValue(viewer, formViewer.getSimpleLongValueText());
			}
		}
		if (e.getSource() == formViewer.getSimpleDateValueText()) {
			setSimpleDomainValue(viewer, formViewer.getSimpleDateValueText());
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == formViewer.getBoolFalseBtn()) {
	       	setBooleanValue(viewer, "false");
	     }
	    if (e.getSource() == formViewer.getBoolTrueBtn()) {
	       	setBooleanValue(viewer, "true");
	     }
	    if (e.getSource() == formViewer.getSimpleDatebutton()) {
	    	DomainUtils.openDomainCalendar(formViewer.getEditor().getEditorSite().getShell(), formViewer.getSimpleDateValueText());
	     }
	   
	    formViewer.getEditor().modified();//Domain Editor Dirty
	}
	
	/**
	 * 
	 * @param viewer
	 * @param text
	 */
	private void setSimpleDomainValue(TableViewer viewer, Text text){
		TableItem selItem = viewer.getTable().getItem(viewer.getTable().getSelectionIndex());
		
		if (!selItem.getText(1).equals(text.getText().trim())) {
		    formViewer.getEditor().modified();//Domain Editor Dirty
		}
		
		selItem.setText(1, text.getText());
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		item.entryValue = text.getText();
	}
	
	/**
	 * 
	 * @param viewer
	 * @param bool
	 */
	private void setBooleanValue(TableViewer viewer, String bool){
		TableItem selItem = viewer.getTable().getItem(viewer.getTable().getSelectionIndex());
		
		if(!selItem.getText(1).equals(bool)){
		    formViewer.getEditor().modified();//Domain Editor Dirty
		}
		
		selItem.setText(1, bool);
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		item.entryValue = bool;
	}
}