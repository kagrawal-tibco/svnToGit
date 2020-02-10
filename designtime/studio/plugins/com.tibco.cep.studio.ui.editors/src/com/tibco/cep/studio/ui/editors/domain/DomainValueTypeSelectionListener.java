package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_RANGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_SIMPLE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_DATE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_INT;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_LONG;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_REAL;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainValueTypeSelectionListener extends AbstractDomainPagesListener {

	private DomainFormViewer formViewer;
	public DomainValueTypeSelectionListener(DomainFormViewer formViewer){
		this.formViewer = formViewer;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
		/*try{
			if(e.getSource()== formViewer.getDomainDataTypesCombo()){
				DOMAIN_DATA_TYPES types = formViewer.getSelectedDataType() ;
				DOMAIN_DATA_TYPES comboType = DOMAIN_DATA_TYPES.get(formViewer.getDomainDataTypesCombo().getSelectionIndex());
				if(types != comboType){
					int count = formViewer.getViewer().getTable().getItems().length;
					if(count>0){
						boolean result = MessageDialog.openQuestion(formViewer.getEditor().getEditorSite().getShell(),
								Messages.getString("domain.datatype.selection.message.dialog.title"),
								Messages.getString("domain.datatype.selection.message.dialog.description"));
						if(result){

							for(int k=count -1;k >=0; k--){formViewer.getViewer().getTable().remove(k);}

							formViewer.getViewerScrollPageBook().showPage(DETAILSINFO_PAGE);
							formViewer.setSelectedDataType(comboType);
						}else{
							formViewer.getDomainDataTypesCombo().select(formViewer.getSelectedDataType().getValue());
						}
					}//If no rows available
					else{
						formViewer.setSelectedDataType(comboType);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button)e.getSource();
		if(button.getText().equalsIgnoreCase("Single")){
			TableItem selItem = formViewer.getViewer().getTable().getItem(formViewer.getViewer().getTable().getSelectionIndex());
			DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
			if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Int")) {
				formViewer.getValueScrollPageBook().showPage(KIND_SIMPLE +TYPE_INT);
				selItem.setText(1, "");
				item.entryValue = "";
				formViewer.getSimpleIntValueText().setText("");
			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Double")) {
				formViewer.getValueScrollPageBook().showPage(KIND_SIMPLE + TYPE_REAL);
				selItem.setText(1, "");
				item.entryValue = "";
				formViewer.getSimpleDoubleValueText().setText("");
			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Long")) {
				formViewer.getValueScrollPageBook().showPage(KIND_SIMPLE + TYPE_LONG);
				selItem.setText(1, "");
				item.entryValue = "";
				formViewer.getSimpleLongValueText().setText("");
			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("DateTime")) {
				formViewer.getValueScrollPageBook().showPage(KIND_SIMPLE + TYPE_DATE);
				selItem.setText(1, DomainUtils.getFormattedDateTime());
				item.entryValue = DomainUtils.getFormattedDateTime();
				formViewer.getSimpleDateValueText().setText(DomainUtils.getFormattedDateTime());
			}
		}else if(button.getText().equalsIgnoreCase("Range")){
			if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Int")) {
				formViewer.getValueScrollPageBook().showPage(KIND_RANGE + TYPE_INT);
				
				formViewer.getIntRangeLoText().setText("");
				formViewer.getIntRangeUpText().setText("");
				formViewer.getIntRangeLoIncBtn().setSelection(false);
				formViewer.getIntRangeUpIncBtn().setSelection(false);

			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Double")) {
				formViewer.getValueScrollPageBook().showPage(KIND_RANGE + TYPE_REAL);
				formViewer.getRealRangeLoText().setText("");
				formViewer.getRealRangeUpText().setText("");
				formViewer.getRealRangeLoIncBtn().setSelection(false);
				formViewer.getRealRangeUpIncBtn().setSelection(false);
				

			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("Long")) {
				formViewer.getValueScrollPageBook().showPage(KIND_RANGE + TYPE_LONG);
				formViewer.getLongRangeLoText().setText("");
				formViewer.getLongRangeUpText().setText("");
				formViewer.getLongRangeLoIncBtn().setSelection(false);
				formViewer.getLongRangeUpIncBtn().setSelection(false);
			} else if (formViewer.getDomainDataTypesCombo().getText().equalsIgnoreCase("DateTime")) {
				formViewer.getValueScrollPageBook().showPage(KIND_RANGE + TYPE_DATE);
				formViewer.getDateTimeRangeLoText().setText(DomainUtils.getFormattedDateTime());
				formViewer.getDateTimeRangeUpText().setText(DomainUtils.getFormattedDateTime());
				formViewer.getDateTimeRangeLoIncBtn().setSelection(false);
				formViewer.getDateTimeRangeUpIncBtn().setSelection(false);
			}
		}
		
		//Dirty Domain Editor
		formViewer.getEditor().modified();
	}
}