package com.tibco.cep.decision.table.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DateTime;

/**
 * 
 * @author sasahoo
 *
 */
public class CalendarController implements SelectionListener,MouseListener,MouseTrackListener,IDateTimeConstants{

	private DateTimeCalendar dateTimeCalendar;
	
	/**
	 * @param dateTimeCalendar
	 */
	public CalendarController(DateTimeCalendar dateTimeCalendar){
		this.dateTimeCalendar = dateTimeCalendar;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == dateTimeCalendar.getCalendar() || 
				e.getSource() == dateTimeCalendar.getTime() || 
				e.getSource() == dateTimeCalendar.getCalendarGrp()){
			setFirstCalendar();
		}
		if(e.getSource() == dateTimeCalendar.getEndDateCalendar() || 
				e.getSource() == dateTimeCalendar.getEndDateTime() || 
				e.getSource() == dateTimeCalendar.getEndDatecalendarGrp()){
			setSecondCalendar();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {
		if(e.getSource() == dateTimeCalendar.getCalendar() || 
				e.getSource() == dateTimeCalendar.getTime()|| 
				e.getSource() == dateTimeCalendar.getCalendarGrp()){
			setFirstCalendar();
		}
		if(e.getSource() == dateTimeCalendar.getEndDateCalendar() || 
				e.getSource() == dateTimeCalendar.getEndDateTime() || 
				e.getSource() == dateTimeCalendar.getEndDatecalendarGrp()){
			setSecondCalendar();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseEnter(MouseEvent e) {
		if(e.getSource() == dateTimeCalendar.getCalendar() || 
				e.getSource() == dateTimeCalendar.getTime()|| 
				e.getSource() == dateTimeCalendar.getCalendarGrp()){
				calendarHoverEnter(true);
			
		}
		if(e.getSource() == dateTimeCalendar.getEndDateCalendar() || 
				e.getSource() == dateTimeCalendar.getEndDateTime() || 
				e.getSource() == dateTimeCalendar.getEndDatecalendarGrp()){
				calendarHoverEnter(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseExit(MouseEvent e) {
		if(e.getSource() == dateTimeCalendar.getCalendar() || 
				e.getSource() == dateTimeCalendar.getTime()|| 
				e.getSource() == dateTimeCalendar.getCalendarGrp()){
			calendarHoverExit(true);
		}
		if(e.getSource() == dateTimeCalendar.getEndDateCalendar() || 
				e.getSource() == dateTimeCalendar.getEndDateTime() || 
				e.getSource() == dateTimeCalendar.getEndDatecalendarGrp()){
			calendarHoverExit(false);
		}
	}

	/**
	 * @param first
	 */
	private void calendarHoverEnter(boolean first){
		if(isBetweenOrRange()){
			if(first){
				if (dateTimeCalendar.getCalendarGrp().getBackground().equals(COLOR_WHITE))
					dateTimeCalendar.getCalendarGrp().setBackground(COLOR_WIDGET_BACKGROUND);
			}else{
				if (dateTimeCalendar.getEndDatecalendarGrp().getBackground().equals(COLOR_WHITE))
					dateTimeCalendar.getEndDatecalendarGrp().setBackground(COLOR_WIDGET_BACKGROUND);
			}
		}
	}
	
	/**
	 * @param first
	 */
	private void calendarHoverExit(boolean first){
		if(isBetweenOrRange()){
			if(first){
				if (!dateTimeCalendar.getCalendarGrp().getBackground().equals(COLOR_INFO_BACKGROUND))
					dateTimeCalendar.getCalendarGrp().setBackground(COLOR_WHITE);
			}else{
				if (!dateTimeCalendar.getEndDatecalendarGrp().getBackground().equals(COLOR_INFO_BACKGROUND))
					dateTimeCalendar.getEndDatecalendarGrp().setBackground(COLOR_WHITE);
			}
		}
	}
	
	/**
	 * 
	 */
	private void setFirstCalendar(){
		Calendar firstcalendar = getCalendar(dateTimeCalendar.getCalendar(), dateTimeCalendar.getTime());
		dateTimeCalendar.setFirstcalendar(firstcalendar);
		dateTimeCalendar.getValText().setText(sdf.format(firstcalendar.getTime()));
		if(isBetweenOrRange()){
			dateTimeCalendar.setStartDateCalendarSelected(true);
			dateTimeCalendar.setEndDateCalendarSelected(false);
			dateTimeCalendar.getCalendarGrp().setBackground(COLOR_INFO_BACKGROUND);
			dateTimeCalendar.getEndDatecalendarGrp().setBackground(COLOR_WHITE);
			dateTimeCalendar.resetConfiguration(firstcalendar, true);
		}else{
			dateTimeCalendar.resetConfiguration(firstcalendar, false);
		}
	}

	/**
	 * 
	 */
	private void setSecondCalendar(){
		if(isBetweenOrRange()){
			dateTimeCalendar.setStartDateCalendarSelected(false);
			dateTimeCalendar.setEndDateCalendarSelected(true);
			dateTimeCalendar.getCalendarGrp().setBackground(COLOR_WHITE);
			dateTimeCalendar.getEndDatecalendarGrp().setBackground(COLOR_INFO_BACKGROUND);
			Calendar secondcalendar = getCalendar(dateTimeCalendar.getEndDateCalendar(), dateTimeCalendar.getEndDateTime());
			dateTimeCalendar.setSecondcalendar(secondcalendar);
			dateTimeCalendar.getEndDateValText().setText(sdf.format(secondcalendar.getTime()));
			dateTimeCalendar.resetConfiguration(secondcalendar, true);
		}
	}

	/**
	 * @param calendar
	 * @param time
	 * @return
	 */
	private Calendar getCalendar(DateTime calendar, DateTime time){
		return new GregorianCalendar(calendar.getYear(), 
									 calendar.getMonth(), 
									 calendar.getDay(), 
					                 time.getHours(), 
					                 time.getMinutes(), 
					                 time.getSeconds());
	}
	
	/**
	 * @return
	 */
	private boolean isBetweenOrRange(){
		return dateTimeCalendar.isBetweenOrRange();
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseHover(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
