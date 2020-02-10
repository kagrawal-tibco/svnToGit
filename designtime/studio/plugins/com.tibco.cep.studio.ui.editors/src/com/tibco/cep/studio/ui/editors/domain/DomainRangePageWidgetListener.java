package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.swt.events.SelectionEvent;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainRangePageWidgetListener extends AbstractDomainPagesListener {

	private DomainFormViewer formViewer;

	/**
	 * @param formViewer
	 */
	public DomainRangePageWidgetListener(DomainFormViewer formViewer){
		this.formViewer = formViewer;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.domain.AbstractDomainPagesListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == formViewer.getIntRangeLoIncBtn() || e.getSource() == formViewer.getIntRangeUpIncBtn()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.INTEGER, formViewer);
			formViewer.getEditor().modified();//Domain Editor Dirty
		}
		if(e.getSource() == formViewer.getRealRangeLoIncBtn() || e.getSource() == formViewer.getRealRangeUpIncBtn()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.DOUBLE, formViewer);
			formViewer.getEditor().modified();//Domain Editor Dirty
		}
		if(e.getSource() == formViewer.getLongRangeLoIncBtn() || e.getSource() == formViewer.getLongRangeUpIncBtn()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.LONG, formViewer);
			formViewer.getEditor().modified();//Domain Editor Dirty
		}
		if(e.getSource() == formViewer.getDateTimeRangeLoIncBtn() || e.getSource() == formViewer.getDateTimeRangeUpIncBtn()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.DATE_TIME, formViewer);
			formViewer.getEditor().modified();//Domain Editor Dirty
		}
		if(e.getSource() == formViewer.getLoDatebutton()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.DATE_TIME, formViewer);
			DomainUtils.openDomainCalendar(formViewer.getEditor().getEditorSite().getShell(), formViewer.getDateTimeRangeLoText());
		}
		if(e.getSource() == formViewer.getHiDatebutton()){
			DomainUtils.rangeValuesRefresh(DOMAIN_DATA_TYPES.DATE_TIME, formViewer);
			DomainUtils.openDomainCalendar(formViewer.getEditor().getEditorSite().getShell(), formViewer.getDateTimeRangeUpText());
		}
	}
}