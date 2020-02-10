package com.tibco.cep.decision.table.calendar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author sasahoo
 *
 */
public class CalendarConfigurationController implements SelectionListener,IDateTimeConstants{

	
	private DateTimeCalendar dateTimeCalendar;
	
	/**
	 * @param dateTimecalendar
	 */
	public CalendarConfigurationController(DateTimeCalendar dateTimecalendar){
		this.dateTimeCalendar = dateTimecalendar;
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == dateTimeCalendar.getResetbutton()){
			try {
				if(isBetweenOrRange()){
					if(isStartSelected()){
						Calendar firstcalendar = new GregorianCalendar();
						firstcalendar.setTime(sdf.parse(dateTimeCalendar.getOrgdate()));
						dateTimeCalendar.setCalendar(dateTimeCalendar.getCalendar(), 
								                     dateTimeCalendar.getTime(), 
								                     firstcalendar, 
								                     dateTimeCalendar.getValText());
						dateTimeCalendar.setFirstcalendar(firstcalendar);
						dateTimeCalendar.resetConfiguration(firstcalendar,true);
					}else{
						Calendar secondcalendar = new GregorianCalendar();
						secondcalendar.setTime(sdf.parse(dateTimeCalendar.getOrgdate()));
						dateTimeCalendar.setCalendar(dateTimeCalendar.getEndDateCalendar(),
								                     dateTimeCalendar.getEndDateTime(),
								                     secondcalendar,
								                     dateTimeCalendar.getEndDateValText());
						dateTimeCalendar.resetConfiguration(secondcalendar,true);
					}
				}else{
					Calendar firstcalendar = new GregorianCalendar();
					firstcalendar.setTime(sdf.parse(dateTimeCalendar.getOrgdate()));
					dateTimeCalendar.setCalendar(dateTimeCalendar.getCalendar(), 
							                     dateTimeCalendar.getTime(), 
							                     firstcalendar, 
							                     dateTimeCalendar.getValText());
					dateTimeCalendar.setFirstcalendar(firstcalendar);
					dateTimeCalendar.resetConfiguration(firstcalendar,false);
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == dateTimeCalendar.getShowCalendarbutton()){
			String dateOpComboText = dateTimeCalendar.getDateOpCombo().getText();
			if(isBetweenOrRange()){
				String text = dateTimeCalendar.getBetweenDateOpCombo().getText();
				if(isStartSelected()){
					if(text.equalsIgnoreCase("Matches")){
						setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
											dateTimeCalendar.getTime(), 
								            dateTimeCalendar.getFirstcalendar(), 
								            dateTimeCalendar.getValText(), 
								            dateTimeCalendar.getStartDateYears(), 
								            dateTimeCalendar.getStartDateMonths(), 
								            dateTimeCalendar.getStartDateWeeks(), 
								            dateTimeCalendar.getStartDateDays(), 
								            dateTimeCalendar.getStartDateHours(), 
								            dateTimeCalendar.getStartDateMins(), 
								            dateTimeCalendar.getStartDateSecs(),true,false,false);		
					}
					if(text.equalsIgnoreCase("After")){
						setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
											dateTimeCalendar.getTime(), 
								            dateTimeCalendar.getFirstcalendar(), 
								            dateTimeCalendar.getValText(), 
								            dateTimeCalendar.getStartDateYears(), 
								            dateTimeCalendar.getStartDateMonths(), 
								            dateTimeCalendar.getStartDateWeeks(), 
								            dateTimeCalendar.getStartDateDays(), 
								            dateTimeCalendar.getStartDateHours(), 
								            dateTimeCalendar.getStartDateMins(), 
								            dateTimeCalendar.getStartDateSecs(),false,true,false);	
					}
					if(text.equalsIgnoreCase("Before")){
						setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
											dateTimeCalendar.getTime(), 
								            dateTimeCalendar.getFirstcalendar(), 
								            dateTimeCalendar.getValText(), 
								            dateTimeCalendar.getStartDateYears(), 
								            dateTimeCalendar.getStartDateMonths(), 
								            dateTimeCalendar.getStartDateWeeks(), 
								            dateTimeCalendar.getStartDateDays(), 
								            dateTimeCalendar.getStartDateHours(), 
								            dateTimeCalendar.getStartDateMins(), 
								            dateTimeCalendar.getStartDateSecs(),false,false,true);	
					}
				}else{
					if(text.equalsIgnoreCase("Matches")){
						setCalanderWhenShow(dateTimeCalendar.getEndDateCalendar(), 
											dateTimeCalendar.getEndDateTime(), 
								            dateTimeCalendar.getSecondcalendar(), 
								            dateTimeCalendar.getEndDateValText(), 
								            dateTimeCalendar.getEndDateYears(), 
								            dateTimeCalendar.getEndDateMonths(), 
								            dateTimeCalendar.getEndDateWeeks(), 
								            dateTimeCalendar.getEndDateDays(), 
								            dateTimeCalendar.getEndDateHours(), 
								            dateTimeCalendar.getEndDateMins(), 
								            dateTimeCalendar.getEndDateSecs(),true,false,false);		
					}
					if(text.equalsIgnoreCase("After")){
						setCalanderWhenShow(dateTimeCalendar.getEndDateCalendar(), 
											dateTimeCalendar.getEndDateTime(), 
								            dateTimeCalendar.getSecondcalendar(), 
								            dateTimeCalendar.getEndDateValText(), 
								            dateTimeCalendar.getEndDateYears(), 
								            dateTimeCalendar.getEndDateMonths(), 
								            dateTimeCalendar.getEndDateWeeks(), 
								            dateTimeCalendar.getEndDateDays(), 
								            dateTimeCalendar.getEndDateHours(), 
								            dateTimeCalendar.getEndDateMins(), 
								            dateTimeCalendar.getEndDateSecs(),false,true,false);		
					}
					if(text.equalsIgnoreCase("Before")){
						setCalanderWhenShow(dateTimeCalendar.getEndDateCalendar(), 
											dateTimeCalendar.getEndDateTime(), 
								            dateTimeCalendar.getSecondcalendar(), 
								            dateTimeCalendar.getEndDateValText(), 
								            dateTimeCalendar.getEndDateYears(), 
								            dateTimeCalendar.getEndDateMonths(), 
								            dateTimeCalendar.getEndDateWeeks(), 
								            dateTimeCalendar.getEndDateDays(), 
								            dateTimeCalendar.getEndDateHours(), 
								            dateTimeCalendar.getEndDateMins(), 
								            dateTimeCalendar.getEndDateSecs(),false,false,true);		
					}
				}
			}else{
				if(dateOpComboText.equalsIgnoreCase("Matches")){
					setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
										dateTimeCalendar.getTime(), 
							            dateTimeCalendar.getFirstcalendar(), 
							            dateTimeCalendar.getValText(), 
							            dateTimeCalendar.getYears(), 
							            dateTimeCalendar.getMonths(), 
							            dateTimeCalendar.getWeeks(), 
							            dateTimeCalendar.getDays(), 
							            dateTimeCalendar.getHours(), 
							            dateTimeCalendar.getMins(), 
							            dateTimeCalendar.getSecs(),true,false,false);		
				}
				if(dateOpComboText.equalsIgnoreCase("After")){
					setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
										dateTimeCalendar.getTime(), 
							            dateTimeCalendar.getFirstcalendar(), 
							            dateTimeCalendar.getValText(), 
							            dateTimeCalendar.getYears(), 
							            dateTimeCalendar.getMonths(), 
							            dateTimeCalendar.getWeeks(), 
							            dateTimeCalendar.getDays(), 
							            dateTimeCalendar.getHours(), 
							            dateTimeCalendar.getMins(), 
							            dateTimeCalendar.getSecs(),false,true,false);				}
				if(dateOpComboText.equalsIgnoreCase("Before")){
					setCalanderWhenShow(dateTimeCalendar.getCalendar(), 
										dateTimeCalendar.getTime(), 
							            dateTimeCalendar.getFirstcalendar(), 
							            dateTimeCalendar.getValText(), 
							            dateTimeCalendar.getYears(), 
							            dateTimeCalendar.getMonths(), 
							            dateTimeCalendar.getWeeks(), 
							            dateTimeCalendar.getDays(), 
							            dateTimeCalendar.getHours(), 
							            dateTimeCalendar.getMins(), 
							            dateTimeCalendar.getSecs(),false,false,true);
				}
			}
		}
		if(e.getSource() == dateTimeCalendar.getYrspinner()){
           //TODO			
		}
		if(e.getSource() == dateTimeCalendar.getIncbutton()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getDecbutton()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getMonthsscale()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getMonspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getWeekspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getWeeksscale()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getDayspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getDaysscale()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getHrsspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getHrssscale()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getMinspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getMinsscale()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getSecspinner()){
			//TODO
		}
		if(e.getSource() == dateTimeCalendar.getSecsscale()){
			//TODO
		}
	}

	/**
	 * @param calendar
	 * @param time
	 * @param gCalenader
	 * @param text
	 * @param years
	 * @param months
	 * @param weeks
	 * @param days
	 * @param hours
	 * @param mins
	 * @param secs
	 * @param matches
	 * @param after
	 * @param before
	 */
	private void setCalanderWhenShow(DateTime calendar, 
									 DateTime time, 
									 Calendar gCalenader,
									 Text text,
			                         int years,
			                         int months, 
			                         int weeks, 
			                         int days,
			                         int hours, 
			                         int mins, 
			                         int secs, boolean matches,boolean after,boolean before){
		if(matches){
			gCalenader.set(Calendar.MONTH, months-1);
			gCalenader.set(Calendar.WEEK_OF_MONTH, weeks);
			gCalenader.set(Calendar.DAY_OF_MONTH, days);
			gCalenader.set(Calendar.HOUR, hours);
			gCalenader.set(Calendar.MINUTE, mins);
			gCalenader.set(Calendar.SECOND, secs);
			dateTimeCalendar.setCalendar(calendar,time,gCalenader,text);
		}
		if(after){
			gCalenader.add(Calendar.YEAR, years);
			gCalenader.add(Calendar.MONTH, months);
			gCalenader.add(Calendar.WEEK_OF_YEAR, weeks);
			gCalenader.add(Calendar.DAY_OF_MONTH, days);
			gCalenader.add(Calendar.HOUR, hours);
			gCalenader.add(Calendar.MINUTE, mins);
			gCalenader.add(Calendar.SECOND, secs);
			dateTimeCalendar.setCalendar(calendar,time,gCalenader,text);
		}
		if(before){
			gCalenader.add(Calendar.YEAR, -years);
			gCalenader.add(Calendar.MONTH, -months);
			gCalenader.add(Calendar.WEEK_OF_YEAR, -weeks);
			gCalenader.add(Calendar.DAY_OF_MONTH, -days);
			gCalenader.add(Calendar.HOUR, -hours);
			gCalenader.add(Calendar.MINUTE, -mins);
			gCalenader.add(Calendar.SECOND, -secs);
			dateTimeCalendar.setCalendar(calendar,time,gCalenader,text);
		}
	}

	/**
	 * @return
	 */
	private boolean isBetweenOrRange(){
		return dateTimeCalendar.isBetweenOrRange();
	}
	
	/**
	 * @return
	 */
	private boolean isStartSelected(){
		return dateTimeCalendar.isStartDateCalendarSelected();
	}
}

