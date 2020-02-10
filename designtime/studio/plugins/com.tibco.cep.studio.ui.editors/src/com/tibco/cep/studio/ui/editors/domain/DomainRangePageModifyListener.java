package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.RANGE_VALUE_SEPARATOR;
import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.resetWidget;
import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.setErrorWidget;

import java.text.ParseException;
import java.util.Date;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainRangePageModifyListener implements ModifyListener {

	private DomainFormViewer formViewer;

	/**
	 * @param formViewer
	 */
	public DomainRangePageModifyListener(DomainFormViewer formViewer) {
		this.formViewer = formViewer;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
		try{
			if(e.getSource() == formViewer.getIntRangeLoText()){
				resetWidget(formViewer.getIntRangeLoText());
				if (DomainUtils.isNumeric(formViewer.getIntRangeUpText().getText().trim())) {
					resetWidget(formViewer.getIntRangeUpText());
				}
				if (!DomainUtils.isNumeric(formViewer.getIntRangeLoText().getText().trim())) {
					setErrorWidget(formViewer.getIntRangeLoText(), DOMAIN_DATA_TYPES.INTEGER.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getLowerSelectedItemValue();
					DomainUtils.rangeLowerValuesRefresh(DOMAIN_DATA_TYPES.INTEGER, formViewer);
					String newValue = getLowerSelectedItemValue();
					
					String text = formViewer.getIntRangeUpText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isNumeric(text))) {
						int up = Integer.parseInt(text);
						int low =  Integer.parseInt(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getIntRangeLoText(), DOMAIN_DATA_TYPES.INTEGER.getLiteral(), newValue, text, false);
							return;
						}
					}
					
					if(  !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}
				}
			}
			if(e.getSource() == formViewer.getIntRangeUpText()){
				resetWidget(formViewer.getIntRangeUpText());
				if (DomainUtils.isNumeric(formViewer.getIntRangeLoText().getText().trim())) {
					resetWidget(formViewer.getIntRangeLoText());
				}
				if (!DomainUtils.isNumeric(formViewer.getIntRangeUpText().getText().trim())) {
					setErrorWidget(formViewer.getIntRangeUpText(), DOMAIN_DATA_TYPES.INTEGER.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getUpperSelectedItemValue();
					DomainUtils.rangeUpperValuesRefresh(DOMAIN_DATA_TYPES.INTEGER, formViewer);
					String newValue = getUpperSelectedItemValue();
					
					String text = formViewer.getIntRangeLoText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isNumeric(text))) {
						int low = Integer.parseInt(text);
						int up =  Integer.parseInt(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getIntRangeUpText(), DOMAIN_DATA_TYPES.INTEGER.getLiteral(), newValue, text, true);
							return;
						}
					}
					
					if( !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}

				}
			}
			if(e.getSource() == formViewer.getRealRangeLoText()){
				resetWidget(formViewer.getRealRangeLoText());
				if (DomainUtils.isDouble(formViewer.getRealRangeUpText().getText().trim())) {
					resetWidget(formViewer.getRealRangeUpText());
				}
				if (!DomainUtils.isDouble(formViewer.getRealRangeLoText().getText().trim())) {
					setErrorWidget(formViewer.getRealRangeLoText(), DOMAIN_DATA_TYPES.DOUBLE.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getLowerSelectedItemValue();
					DomainUtils.rangeLowerValuesRefresh(DOMAIN_DATA_TYPES.DOUBLE, formViewer);
					String newValue = getLowerSelectedItemValue();
					
					String text = formViewer.getRealRangeUpText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isDouble(text))) {
						double up = Double.parseDouble(text);
						double low = Double.parseDouble(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getRealRangeLoText(), DOMAIN_DATA_TYPES.DOUBLE.getLiteral(), newValue, text, false);
							return;
						}
					}
					
					if(!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}
				}
			}
			if(e.getSource() == formViewer.getRealRangeUpText()){
				resetWidget(formViewer.getRealRangeUpText());
				if (DomainUtils.isDouble(formViewer.getRealRangeLoText().getText().trim())) {
					resetWidget(formViewer.getRealRangeLoText());
				}
				if (!DomainUtils.isDouble(formViewer.getRealRangeUpText().getText().trim())) {
					setErrorWidget(formViewer.getRealRangeUpText(), DOMAIN_DATA_TYPES.DOUBLE.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getUpperSelectedItemValue();
					DomainUtils.rangeUpperValuesRefresh(DOMAIN_DATA_TYPES.DOUBLE, formViewer);
					String newValue = getUpperSelectedItemValue();
					
					String text = formViewer.getRealRangeLoText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isDouble(text))) {
						double low = Double.parseDouble(text);
						double up =  Double.parseDouble(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getRealRangeUpText(), DOMAIN_DATA_TYPES.DOUBLE.getLiteral(), newValue, text, true);
							return;
						}
					}
					
					if(!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}
				}
			}
			if(e.getSource() == formViewer.getLongRangeLoText()){
				resetWidget(formViewer.getLongRangeLoText());
				if (DomainUtils.isLong(formViewer.getLongRangeUpText().getText().trim())) {
					resetWidget(formViewer.getLongRangeUpText());
				}
				if (!DomainUtils.isLong(formViewer.getLongRangeLoText().getText().trim())) {
					setErrorWidget(formViewer.getLongRangeLoText(), DOMAIN_DATA_TYPES.LONG.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getLowerSelectedItemValue();
					DomainUtils.rangeLowerValuesRefresh(DOMAIN_DATA_TYPES.LONG, formViewer);
					String newValue = getLowerSelectedItemValue();
					
					String text = formViewer.getLongRangeUpText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isLong(text))) {
						long up = Long.parseLong(text);
						long low = Long.parseLong(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getRealRangeLoText(), DOMAIN_DATA_TYPES.LONG.getLiteral(), newValue, text, false);
							return;
						}
					}
					
					if(!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}
				}
			}
			if(e.getSource() == formViewer.getLongRangeUpText()){
				resetWidget(formViewer.getLongRangeUpText());
				if (DomainUtils.isLong(formViewer.getLongRangeLoText().getText().trim())) {
					resetWidget(formViewer.getLongRangeLoText());
				}
				if (!DomainUtils.isLong(formViewer.getLongRangeUpText().getText().trim())) {
					setErrorWidget(formViewer.getLongRangeUpText(), DOMAIN_DATA_TYPES.LONG.getLiteral());
					formViewer.getEditor().modified();//Domain Editor Dirty
				} else {
					String oldValue = getUpperSelectedItemValue();
					DomainUtils.rangeUpperValuesRefresh(DOMAIN_DATA_TYPES.LONG, formViewer);
					String newValue = getUpperSelectedItemValue();
					
					String text = formViewer.getLongRangeLoText().getText();
					if (!text.isEmpty() && !newValue.isEmpty() && (DomainUtils.isLong(text))) {
						long low = Long.parseLong(text);
						long up =  Long.parseLong(newValue);
						if (up <= low) {
							setErrorWidget(formViewer.getLongRangeUpText(), DOMAIN_DATA_TYPES.LONG.getLiteral(), newValue, text, true);
							return;
						}
					}
					
					if(!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())){
						formViewer.getEditor().modified();
					}
				}
			}
			if(e.getSource() == formViewer.getDateTimeRangeLoText()){
				
				resetWidget(formViewer.getDateTimeRangeLoText());
				resetWidget(formViewer.getDateTimeRangeUpText());
				
				
				String oldValue = getLowerSelectedItemValue();
				DomainUtils.rangeLowerValuesRefresh(DOMAIN_DATA_TYPES.DATE_TIME, formViewer);
				String newValue = getLowerSelectedItemValue();

				try {
					String text = formViewer.getDateTimeRangeUpText().getText();
					if (!text.isEmpty() && !newValue.isEmpty()) {
						Date low = ModelUtils.SIMPLE_DATE_FORMAT().parse(newValue);
						Date high = ModelUtils.SIMPLE_DATE_FORMAT().parse(text);
						if (low.compareTo(high) >= 0) {
							setErrorWidget(formViewer.getDateTimeRangeLoText(), DOMAIN_DATA_TYPES.DATE_TIME.getLiteral(), newValue, text, false);
							return;
						}
					}
				} catch (ParseException ex) {
					EditorsUIPlugin.debug(ex.getMessage());
					return;
				}

				if(!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())){
					formViewer.getEditor().modified();
				}
			}
			if (e.getSource() == formViewer.getDateTimeRangeUpText()) {
				
				resetWidget(formViewer.getDateTimeRangeLoText());
				resetWidget(formViewer.getDateTimeRangeUpText());
				
				String oldValue = getUpperSelectedItemValue();
				DomainUtils.rangeUpperValuesRefresh(DOMAIN_DATA_TYPES.DATE_TIME, formViewer);
				String newValue = getUpperSelectedItemValue();
				
				try {
					String text = formViewer.getDateTimeRangeLoText().getText();
					if (!text.isEmpty() && !newValue.isEmpty()) {
						Date high = ModelUtils.SIMPLE_DATE_FORMAT().parse(newValue);
						Date low = ModelUtils.SIMPLE_DATE_FORMAT().parse(text);
						if (low.compareTo(high) >= 0) {
							setErrorWidget(formViewer.getDateTimeRangeUpText(), DOMAIN_DATA_TYPES.DATE_TIME.getLiteral(), newValue, text, true);
							return;
						}
					}
				} catch (ParseException ex) {
					EditorsUIPlugin.debug(ex.getMessage());
					return;
				}
				
				if (!oldValue.trim().equals("") && !newValue.trim().equals("") && !oldValue.trim().equals(newValue.trim())) {
					formViewer.getEditor().modified();
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * @return
	 */
	protected String getLowerSelectedItemValue(){
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
		String value = item.entryValue;
		if(!value.trim().equalsIgnoreCase("") && value.indexOf(RANGE_VALUE_SEPARATOR) > -1){
			value = value.substring(1, value.indexOf(RANGE_VALUE_SEPARATOR));
		}
		return value;
	}

	/**
	 * 
	 * @return
	 */
	protected String getUpperSelectedItemValue(){
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
		String value = item.entryValue;
		if(!value.trim().equalsIgnoreCase("") && value.indexOf(RANGE_VALUE_SEPARATOR) > -1){
			value = value.substring(value.indexOf(RANGE_VALUE_SEPARATOR) + 1,value.length() - 1).trim();
		}
		return value;
	}
}
