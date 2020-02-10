package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DETAILSINFO_PAGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DETAILS_PAGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_RANGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_SIMPLE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.RANGE_VALUE_SEPARATOR;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_BOOLEAN;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_DATE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_INT;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_LONG;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_REAL;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_STRING;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;

/**
 * 
 * @author sasahoo
 * 
 */
public class DomainViewerSelectionChangeListener implements	ISelectionChangedListener {

	private DomainFormViewer domainformviewer;
	private TableViewer viewer;
	private Text simpleIntValueText;
	private Text simpleValueText;
	private Button rangeBut;
	private Button singeBut;
	private Button intRangeLoIncBtn;
	private Button intRangeUpIncBtn;
	private Button boolTrueBtn;
	private Button boolFalseBtn;
	private Button realRangeLoIncBtn;
	private Button realRangeUpIncBtn;
	private Button longRangeLoIncBtn;
	private Button longRangeUpIncBtn;
	private Button dateTimeRangeLoIncBtn;
	private Button dateTimeRangeUpIncBtn;
	private Text intRangeLoText;
	private Text intRangeUpText;
	private Text simpleDateValueText;
	private Text simpleLongValueText;
	private Text simpleDoubleValueText;
	private Text longRangeLoText;
	private Text longRangeUpText;
	private Text realRangeLoText;
	private Text realRangeUpText;
	private Text dateTimeRangeLoText;
	private Text dateTimeRangeUpText;
	private ScrolledPageBook viewerScrollPageBook;
	private ScrolledPageBook valueScrollPageBook;
	private ToolItem duplicateButton;
	private ToolItem removeButton;

	/**
	 * @param domainformviewer
	 */
	public DomainViewerSelectionChangeListener(DomainFormViewer domainformviewer) {
		this.domainformviewer = domainformviewer;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (!domainformviewer.getEditor().isEnabled()) {
			return;
		}
		getWidgets();

		try{
			
			if (viewer.getTable().getSelection().length != 1 ) {
				viewerScrollPageBook.showPage(DETAILSINFO_PAGE);
				if (viewer.getTable().getSelection().length == 0) {
					duplicateButton.setEnabled(false);
					removeButton.setEnabled(false);
				}
				return;
			}
			DomainViewerListeners.removeRangeModifyListener(domainformviewer);
			init();

			
			TableItem selItem = viewer.getTable().getItem(viewer.getTable().getSelectionIndex());
			if(viewer.getTable().getSelectionCount()==0 && selItem.getText()==""){
				duplicateButton.setEnabled(false);
				removeButton.setEnabled(false);
					
			}
			else{
				duplicateButton.setEnabled(true);
				removeButton.setEnabled(true);
			}
			
			//Handling Duplicate button for boolean domain data type
			if (domainformviewer.getDomain().getDataType() == DOMAIN_DATA_TYPES.BOOLEAN 
					&& viewer.getTable().getItemCount() == 2) {
				duplicateButton.setEnabled(false);
			}
			
			singeBut.setSelection(true);
			rangeBut.setSelection(false);
			String rangeval = selItem.getText(1);
			switch(DOMAIN_DATA_TYPES.get(domainformviewer.getDomainDataTypesCombo().getText()))
			{
				case STRING:
					singeBut.setVisible(false);	
					rangeBut.setVisible(false);
					valueScrollPageBook.showPage(KIND_SIMPLE + TYPE_STRING);
					simpleValueText.setText(rangeval);
					break;
				case BOOLEAN:
					valueScrollPageBook.showPage(KIND_SIMPLE + TYPE_BOOLEAN);
					singeBut.setVisible(false);	
					rangeBut.setVisible(false);
					boolTrueBtn.setEnabled(true); 
					boolFalseBtn.setEnabled(true);
					boolTrueBtn.setSelection(selItem.getText(1).equalsIgnoreCase("true")?true:false);
					boolFalseBtn.setSelection(selItem.getText(1).equalsIgnoreCase("false")?true:false);
					break;	
				case INTEGER:
					if(containsSeparator(rangeval)){
						setRangeValuesOnSelection(TYPE_INT,	rangeval, intRangeLoIncBtn, intRangeUpIncBtn, intRangeLoText, intRangeUpText);
					}else{
						setSimpleValuesOnSelection(TYPE_INT, rangeval, simpleIntValueText);
					}
					break;
				case DOUBLE:
					if(containsSeparator(rangeval)){
						setRangeValuesOnSelection(TYPE_REAL, 
								rangeval, realRangeLoIncBtn, realRangeUpIncBtn, realRangeLoText, realRangeUpText);				
					}else{
						setSimpleValuesOnSelection(TYPE_REAL, rangeval, simpleDoubleValueText);
					}
					break;
				case LONG:
					if(containsSeparator(rangeval)){
						setRangeValuesOnSelection(TYPE_LONG, 
								rangeval, longRangeLoIncBtn, longRangeUpIncBtn, longRangeLoText, longRangeUpText);				
					}else{
						setSimpleValuesOnSelection(TYPE_LONG, rangeval, simpleLongValueText);
					}
					break;
				case DATE_TIME:
					if(containsSeparator(rangeval)){
						setRangeValuesOnSelection(TYPE_DATE, 
								rangeval, dateTimeRangeLoIncBtn, dateTimeRangeUpIncBtn, dateTimeRangeLoText, dateTimeRangeUpText);				
					}else{
						setSimpleValuesOnSelection(TYPE_DATE, rangeval, simpleDateValueText);
					}
					break;
	
				default:
					break;
			}
			DomainViewerListeners.addRangeModifyListener(domainformviewer);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param text
	 * @return
	 */
	private boolean containsSeparator(String text){
		if(text.contains(RANGE_VALUE_SEPARATOR))
			return true;
		return false;	
	}

	private void init(){
		viewerScrollPageBook.showPage(DETAILS_PAGE);
		intRangeLoIncBtn.setSelection(false);
		intRangeUpIncBtn.setSelection(false);
		realRangeLoIncBtn.setSelection(false);
		realRangeUpIncBtn.setSelection(false);
		longRangeLoIncBtn.setSelection(false);
		longRangeUpIncBtn.setSelection(false);
		dateTimeRangeLoIncBtn.setSelection(false);
		dateTimeRangeUpIncBtn.setSelection(false);
	}

	/**
	 * @param key
	 * @param rangeval
	 * @param loIncButton
	 * @param upIncButton
	 * @param LoText
	 * @param upText
	 */
	private void setRangeValuesOnSelection(Object key,String rangeval,Button loIncButton,Button upIncButton,Text LoText, Text upText){
		try {
			singeBut.setVisible(true);
			rangeBut.setVisible(true);
			singeBut.setSelection(false);
			rangeBut.setSelection(true);
			valueScrollPageBook.showPage(KIND_RANGE + key);
			loIncButton.setSelection(rangeval.startsWith("[") ? true : false);
			upIncButton.setSelection(rangeval.endsWith("]") ? true : false);
			LoText.setText(rangeval.substring(1, rangeval.indexOf(RANGE_VALUE_SEPARATOR)));
			upText.setText(rangeval.substring(rangeval.indexOf(RANGE_VALUE_SEPARATOR) + 1, rangeval.length() - 1));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param key
	 * @param rangeval
	 * @param text
	 */
	private void setSimpleValuesOnSelection(Object key,String rangeval,Text text){
		singeBut.setVisible(true);
		rangeBut.setVisible(true);
		valueScrollPageBook.showPage(KIND_SIMPLE + key);
		text.setText(rangeval);
	}


	private void getWidgets(){
		viewer = domainformviewer.getViewer();
		viewerScrollPageBook = domainformviewer.getViewerScrollPageBook();
		valueScrollPageBook = domainformviewer.getValueScrollPageBook();

		simpleIntValueText = domainformviewer.getSimpleIntValueText();
		simpleValueText = domainformviewer.getSimpleValueText();
		simpleDateValueText = domainformviewer.getSimpleDateValueText();
		simpleLongValueText = domainformviewer.getSimpleLongValueText();
		simpleDoubleValueText = domainformviewer.getSimpleDoubleValueText();

		rangeBut = domainformviewer.getRangeBut();
		singeBut = domainformviewer.getSingeBut();
		boolTrueBtn = domainformviewer.getBoolTrueBtn();
		boolFalseBtn = domainformviewer.getBoolFalseBtn();

		intRangeLoIncBtn = domainformviewer.getIntRangeLoIncBtn();
		intRangeUpIncBtn = domainformviewer.getIntRangeUpIncBtn();
		realRangeLoIncBtn = domainformviewer.getRealRangeLoIncBtn();
		realRangeUpIncBtn = domainformviewer.getRealRangeUpIncBtn();
		longRangeLoIncBtn = domainformviewer.getLongRangeLoIncBtn();
		longRangeUpIncBtn = domainformviewer.getLongRangeUpIncBtn();
		dateTimeRangeLoIncBtn = domainformviewer.getDateTimeRangeLoIncBtn();
		dateTimeRangeUpIncBtn = domainformviewer.getDateTimeRangeUpIncBtn();

		intRangeLoText = domainformviewer.getIntRangeLoText();
		intRangeUpText = domainformviewer.getIntRangeUpText();
		longRangeLoText = domainformviewer.getLongRangeLoText();
		longRangeUpText = domainformviewer.getLongRangeUpText();
		realRangeLoText = domainformviewer.getRealRangeLoText();
		realRangeUpText = domainformviewer.getRealRangeUpText();
		dateTimeRangeLoText = domainformviewer.getDateTimeRangeLoText();
		dateTimeRangeUpText = domainformviewer.getDateTimeRangeUpText();
		
		duplicateButton = domainformviewer.getDuplicateButton();
		removeButton = domainformviewer.getRemoveRowButton();
	}

}
