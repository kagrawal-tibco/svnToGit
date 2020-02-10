package com.tibco.cep.studio.decision.table.calendar;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class CalendarOperations implements ModifyListener,IDateTimeConstants{

	private DateTimeCalendar dateTimeCalendar;
	
	/**
	 * @param dateTimecalendar
	 */
	public CalendarOperations(DateTimeCalendar dateTimecalendar){
		this.dateTimeCalendar = dateTimecalendar;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
//		if(e.getSource() == dateTimeCalendar.getDateOpCombo()){
//			if(isBetweenOrRange()){
//				dateTimeCalendar.getNewShell().setSize(875, 535);
//				dateTimeCalendar.getEnddateOpComposite().setVisible(true);
//				dateTimeCalendar.getEndDatecalendarGrp().setVisible(true);
//				dateTimeCalendar.getBetweenDateOpCombo().setVisible(true);
//				dateTimeCalendar.getStartDatelabel().setText("Start");
//				dateTimeCalendar.getCalendarGrp().setText("Start Date");
//				dateTimeCalendar.getDoubleCalendarButtonBar().setVisible(true);
//				dateTimeCalendar.buttonBar.setVisible(false);
//				dateTimeCalendar.getCalendarGrp().setBackground(COLOR_INFO_BACKGROUND);
//				dateTimeCalendar.resetConfiguration(dateTimeCalendar.getFirstcalendar(), true);
//				dateTimeCalendar.resetConfiguration(dateTimeCalendar.getSecondcalendar(), true);
//			}else{
//				dateTimeCalendar.getNewShell().setSize(627, 535);
//				dateTimeCalendar.getEnddateOpComposite().setVisible(false);
//				dateTimeCalendar.getEndDatecalendarGrp().setVisible(false);
//				dateTimeCalendar.getBetweenDateOpCombo().setVisible(false);
//				dateTimeCalendar.getStartDatelabel().setText("Date");
//				dateTimeCalendar.getCalendarGrp().setText("Date");
//				dateTimeCalendar.getDoubleCalendarButtonBar().setVisible(false);
//				dateTimeCalendar.buttonBar.setVisible(true);
//				dateTimeCalendar.getCalendarGrp().setBackground(COLOR_WHITE);
//				dateTimeCalendar.getEndDatecalendarGrp().setBackground(COLOR_WHITE);
//				dateTimeCalendar.resetConfiguration(dateTimeCalendar.getFirstcalendar(), false);
//			}
//		}
	}
	
	/**
	 * @return
	 */
	private boolean isBetweenOrRange(){
		return dateTimeCalendar.isBetweenOrRange();
	}
}
