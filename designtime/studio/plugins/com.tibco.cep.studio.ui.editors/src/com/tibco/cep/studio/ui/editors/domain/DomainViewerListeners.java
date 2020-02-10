package com.tibco.cep.studio.ui.editors.domain;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainViewerListeners {
	
	private static DomainRangePageModifyListener domainRangeListener;

	/**
	 * 
	 * @param formViewer
	 */
	public static void addPagesWidgetsListeners(DomainFormViewer formViewer){
		
	
		 //Listener for type checks
		AbstractDomainPagesListener  listener = new DomainValueTypeSelectionListener(formViewer);
		{
			formViewer.getDomainDataTypesCombo().addModifyListener(listener);
			formViewer.getSingeBut().addSelectionListener(listener);
			formViewer.getRangeBut().addSelectionListener(listener);
		}
		
      //Listener for Simple page widgets
		listener = new DomainSimplePageWidgetListener(formViewer);
		{
			formViewer.getSimpleValueText().addModifyListener(listener);
			formViewer.getSimpleIntValueText().addModifyListener(listener);
			formViewer.getSimpleDoubleValueText().addModifyListener(listener);
			formViewer.getSimpleDateValueText().addModifyListener(listener);
			formViewer.getSimpleLongValueText().addModifyListener(listener);
			formViewer.getBoolTrueBtn().addSelectionListener(listener);
			formViewer.getBoolFalseBtn().addSelectionListener(listener);
			formViewer.getSimpleDatebutton().addSelectionListener(listener);
		}
		
		//Listener for Range page widgets
		listener = new DomainRangePageWidgetListener(formViewer);
		{
			formViewer.getIntRangeLoIncBtn().addSelectionListener(listener);
			formViewer.getIntRangeUpIncBtn().addSelectionListener(listener);
			
			formViewer.getRealRangeLoIncBtn().addSelectionListener(listener);
			formViewer.getRealRangeUpIncBtn().addSelectionListener(listener);
			
			formViewer.getLongRangeLoIncBtn().addSelectionListener(listener);
			formViewer.getLongRangeUpIncBtn().addSelectionListener(listener);
			
			formViewer.getDateTimeRangeLoIncBtn().addSelectionListener(listener);
			formViewer.getDateTimeRangeUpIncBtn().addSelectionListener(listener);
			
			formViewer.getLoDatebutton().addSelectionListener(listener);
			formViewer.getHiDatebutton().addSelectionListener(listener);
			
		}
		
		domainRangeListener = new DomainRangePageModifyListener(formViewer); 
		{
			formViewer.getIntRangeLoText().addModifyListener(domainRangeListener);
			formViewer.getIntRangeUpText().addModifyListener(domainRangeListener);

			formViewer.getRealRangeLoText().addModifyListener(domainRangeListener);
			formViewer.getRealRangeUpText().addModifyListener(domainRangeListener);

			formViewer.getLongRangeLoText().addModifyListener(domainRangeListener);
			formViewer.getLongRangeUpText().addModifyListener(domainRangeListener);

			formViewer.getDateTimeRangeLoText().addModifyListener(domainRangeListener);
			formViewer.getDateTimeRangeUpText().addModifyListener(domainRangeListener);
		}
	}
	
	public static void addRangeModifyListener(DomainFormViewer formViewer){
		formViewer.setDomainRangePageModifyListener(null);
		DomainRangePageModifyListener listener = new DomainRangePageModifyListener(formViewer);
		formViewer.setDomainRangePageModifyListener(listener);
		formViewer.getIntRangeLoText().addModifyListener(listener);
		formViewer.getIntRangeUpText().addModifyListener(listener);
		
		formViewer.getRealRangeLoText().addModifyListener(listener);
		formViewer.getRealRangeUpText().addModifyListener(listener);
		
		formViewer.getLongRangeLoText().addModifyListener(listener);
		formViewer.getLongRangeUpText().addModifyListener(listener);
		
		formViewer.getDateTimeRangeLoText().addModifyListener(listener);
		formViewer.getDateTimeRangeUpText().addModifyListener(listener);
	}
	
	public static void removeRangeModifyListener(DomainFormViewer formViewer){
		DomainRangePageModifyListener listener = formViewer.getDomainRangePageModifyListener();
		formViewer.getIntRangeLoText().removeModifyListener(listener);
		formViewer.getIntRangeUpText().removeModifyListener(listener);
		
		formViewer.getRealRangeLoText().removeModifyListener(listener);
		formViewer.getRealRangeUpText().removeModifyListener(listener);
		
		formViewer.getLongRangeLoText().removeModifyListener(listener);
		formViewer.getLongRangeUpText().removeModifyListener(listener);
		
		formViewer.getDateTimeRangeLoText().removeModifyListener(listener);
		formViewer.getDateTimeRangeUpText().removeModifyListener(listener);
	}
}
