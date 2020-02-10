package com.tibco.cep.decision.table.calendar;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDateTimeCalendarDialog extends Dialog implements IDateTimeConstants{

	protected DateTime calendar;
	protected DateTime time;
	protected Calendar firstcalendar;
	protected Calendar secondcalendar;
	protected Shell shell;
	protected Text valText;
	protected Spinner yrspinner;
	protected Scale monthsscale;
	protected Spinner monspinner;
	protected Scale weeksscale;
	protected Spinner weekspinner;
	protected Scale daysscale;
	protected Spinner dayspinner;
	protected Scale hrssscale;
	protected Spinner hrsspinner;
	protected Scale minsscale;
	protected Spinner minspinner;
	protected Scale secsscale;
	protected Spinner secspinner;
	protected Combo dateOpCombo;
	protected Composite enddateOpComposite;
	protected Label endDatelabel;
	protected Label startDatelabel;
	protected Text endDateValText;
	protected Group endDatecalendarGrp;
	protected Group calendarGrp;
	protected DateTime endDateCalendar;
	protected DateTime endDateTime;
	protected Button incbutton;
	protected Button decbutton;
	protected Control doubleCalendarButtonBar;
	protected Composite singleCalendarComposite;
	protected Composite doubleCalendarComposite;
	protected Combo betweenDateOpCombo;
	protected Shell newShell;
	protected Button resetbutton;
	protected Button showCalendarbutton;

	protected String orgdate;

	protected int years = 0;
	protected int months = 0; 
	protected int days = 0; 
	protected int weeks = 0; 
	protected int hours = 0;
	protected int mins = 0; 
	protected int secs = 0; 

	protected int endDateYears = 0;
	protected int endDateMonths = 0; 
	protected int endDateDays = 0; 
	protected int endDateWeeks = 0; 
	protected int endDateHours = 0;
	protected int endDateMins = 0; 
	protected int endDateSecs = 0;

	protected int startDateYears = 0;
	protected int startDateMonths = 0; 
	protected int startDateDays = 0; 
	protected int startDateWeeks = 0; 
	protected int startDateHours = 0;
	protected int startDateMins = 0; 
	protected int startDateSecs = 0; 

	protected boolean startDateCalendarSelected = true;
	protected boolean endDateCalendarSelected = false;
	protected String dateTime;

	protected String currentDateTime;
	
	public static final String BETWEEN_DATETIME_EXPR = "> {0} && < {1}";
	public static final String BEFORE_DATETIME_EXPR = "< {0}";
	public static final String AFTER_DATETIME_EXPR = "> {0}";
	
	/**
	 * @param dateTime
	 */
	protected abstract void setWidgetDateTime(String dateTime);
	
	/**
	 * @param calendar
	 * @param time
	 * @param gcalandar
	 * @param text
	 */
	protected void setCalendar(DateTime calendar, DateTime time, Calendar gcalandar,Text text){
		calendar.setDay(gcalandar.get(Calendar.DAY_OF_MONTH));
		calendar.setMonth(gcalandar.get(Calendar.MONTH));
		calendar.setYear(gcalandar.get(Calendar.YEAR));
		if (gcalandar.get(Calendar.AM_PM) == 1) {
			// add 12 to set the time to PM
			time.setHours(gcalandar.get(Calendar.HOUR)+12);
		} else {
			time.setHours(gcalandar.get(Calendar.HOUR));
		}
		time.setMinutes(gcalandar.get(Calendar.MINUTE));
		time.setSeconds(gcalandar.get(Calendar.SECOND));
		text.setText(sdf.format(gcalandar.getTime()));
	}

	/**
	 * @param calendar
	 * @param isBetweenOrRange
	 */
	public void resetConfiguration(Calendar calendar,boolean isBetweenOrRange){

		monspinner.setSelection(calendar.get(Calendar.MONTH)+1);
		monthsscale.setSelection(calendar.get(Calendar.MONTH)+1);
		dayspinner.setSelection(calendar.get(Calendar.DAY_OF_MONTH));
		daysscale.setSelection(calendar.get(Calendar.DAY_OF_MONTH));
		weekspinner.setSelection(calendar.get(Calendar.WEEK_OF_YEAR));
		weeksscale.setSelection(calendar.get(Calendar.WEEK_OF_YEAR));
		hrsspinner.setSelection(calendar.get(Calendar.HOUR_OF_DAY));
		hrssscale.setSelection(calendar.get(Calendar.HOUR_OF_DAY));
		minspinner.setSelection(calendar.get(Calendar.MINUTE));
		minsscale.setSelection(calendar.get(Calendar.MINUTE));
		secspinner.setSelection(calendar.get(Calendar.SECOND));
		secsscale.setSelection(calendar.get(Calendar.SECOND));

		if(isBetweenOrRange){
			if(startDateCalendarSelected){
				startDateMonths =  monthsscale.getSelection();
				startDateWeeks = weeksscale.getSelection();
				startDateDays = daysscale.getSelection();
				startDateHours = hrssscale.getSelection();
				startDateMins = minsscale.getSelection();
				startDateSecs = secsscale.getSelection();

			}else{
				endDateMonths =  monthsscale.getSelection();
				endDateWeeks = weeksscale.getSelection();
				endDateDays = daysscale.getSelection();
				endDateHours = hrssscale.getSelection();
				endDateMins = minsscale.getSelection();
				endDateSecs = secsscale.getSelection();
			}
		}else{
			months =  monthsscale.getSelection();
			weeks = weeksscale.getSelection();
			days = daysscale.getSelection();
			hours = hrssscale.getSelection();
			mins = minsscale.getSelection();
			secs = secsscale.getSelection();
		}
	}
	
	
	protected String getFormattedDateTime() {
		return sdf.format(new Date());
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setBetweenDateTime(String startDateTime, String endDateTime) {
		this.dateTime = MessageFormat.format(BETWEEN_DATETIME_EXPR, startDateTime, endDateTime);
	}

	public void setBeforeDateTime(String dateTime) {
		this.dateTime = MessageFormat.format(BEFORE_DATETIME_EXPR, dateTime);
	}

	public void setAfterDateTime(String dateTime) {
		this.dateTime = MessageFormat.format(AFTER_DATETIME_EXPR, dateTime);
	}
	
	public String getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(String currentDate) {
		this.currentDateTime = currentDate;
	}

	public DateTime getCalendar() {
		return calendar;
	}

	public void setCalendar(DateTime calendar) {
		this.calendar = calendar;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public Calendar getFirstcalendar() {
		return firstcalendar;
	}

	public void setFirstcalendar(Calendar firstcalendar) {
		this.firstcalendar = firstcalendar;
	}

	public Calendar getSecondcalendar() {
		return secondcalendar;
	}

	public void setSecondcalendar(Calendar secondcalendar) {
		this.secondcalendar = secondcalendar;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Text getValText() {
		return valText;
	}

	public void setValText(Text valText) {
		this.valText = valText;
	}

	public Spinner getYrspinner() {
		return yrspinner;
	}

	public void setYrspinner(Spinner yrspinner) {
		this.yrspinner = yrspinner;
	}

	public Scale getMonthsscale() {
		return monthsscale;
	}

	public void setMonthsscale(Scale monthsscale) {
		this.monthsscale = monthsscale;
	}

	public Spinner getMonspinner() {
		return monspinner;
	}

	public void setMonspinner(Spinner monspinner) {
		this.monspinner = monspinner;
	}

	public Scale getWeeksscale() {
		return weeksscale;
	}

	public void setWeeksscale(Scale weeksscale) {
		this.weeksscale = weeksscale;
	}

	public Spinner getWeekspinner() {
		return weekspinner;
	}

	public void setWeekspinner(Spinner weekspinner) {
		this.weekspinner = weekspinner;
	}

	public Scale getDaysscale() {
		return daysscale;
	}

	public void setDaysscale(Scale daysscale) {
		this.daysscale = daysscale;
	}

	public Spinner getDayspinner() {
		return dayspinner;
	}

	public void setDayspinner(Spinner dayspinner) {
		this.dayspinner = dayspinner;
	}

	public Scale getHrssscale() {
		return hrssscale;
	}

	public void setHrssscale(Scale hrssscale) {
		this.hrssscale = hrssscale;
	}

	public Spinner getHrsspinner() {
		return hrsspinner;
	}

	public void setHrsspinner(Spinner hrsspinner) {
		this.hrsspinner = hrsspinner;
	}

	public Scale getMinsscale() {
		return minsscale;
	}

	public void setMinsscale(Scale minsscale) {
		this.minsscale = minsscale;
	}

	public Spinner getMinspinner() {
		return minspinner;
	}

	public void setMinspinner(Spinner minspinner) {
		this.minspinner = minspinner;
	}

	public Scale getSecsscale() {
		return secsscale;
	}

	public void setSecsscale(Scale secsscale) {
		this.secsscale = secsscale;
	}

	public Spinner getSecspinner() {
		return secspinner;
	}

	public void setSecspinner(Spinner secspinner) {
		this.secspinner = secspinner;
	}

	public Combo getDateOpCombo() {
		return dateOpCombo;
	}

	public void setDateOpCombo(Combo dateOpCombo) {
		this.dateOpCombo = dateOpCombo;
	}

	public Composite getEnddateOpComposite() {
		return enddateOpComposite;
	}

	public void setEnddateOpComposite(Composite enddateOpComposite) {
		this.enddateOpComposite = enddateOpComposite;
	}

	public Label getEndDatelabel() {
		return endDatelabel;
	}

	public void setEndDatelabel(Label endDatelabel) {
		this.endDatelabel = endDatelabel;
	}

	public Label getStartDatelabel() {
		return startDatelabel;
	}

	public void setStartDatelabel(Label startDatelabel) {
		this.startDatelabel = startDatelabel;
	}

	public Text getEndDateValText() {
		return endDateValText;
	}

	public void setEndDateValText(Text endDateValText) {
		this.endDateValText = endDateValText;
	}

	public Group getEndDatecalendarGrp() {
		return endDatecalendarGrp;
	}

	public void setEndDatecalendarGrp(Group endDatecalendarGrp) {
		this.endDatecalendarGrp = endDatecalendarGrp;
	}

	public Group getCalendarGrp() {
		return calendarGrp;
	}

	public void setCalendarGrp(Group calendarGrp) {
		this.calendarGrp = calendarGrp;
	}

	public DateTime getEndDateCalendar() {
		return endDateCalendar;
	}

	public void setEndDateCalendar(DateTime endDateCalendar) {
		this.endDateCalendar = endDateCalendar;
	}

	public DateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Button getIncbutton() {
		return incbutton;
	}

	public void setIncbutton(Button incbutton) {
		this.incbutton = incbutton;
	}

	public Button getDecbutton() {
		return decbutton;
	}

	public void setDecbutton(Button decbutton) {
		this.decbutton = decbutton;
	}

	public Control getDoubleCalendarButtonBar() {
		return doubleCalendarButtonBar;
	}

	public void setDoubleCalendarButtonBar(Control doubleCalendarButtonBar) {
		this.doubleCalendarButtonBar = doubleCalendarButtonBar;
	}

	public Composite getSingleCalendarComposite() {
		return singleCalendarComposite;
	}

	public void setSingleCalendarComposite(Composite singleCalendarComposite) {
		this.singleCalendarComposite = singleCalendarComposite;
	}

	public Composite getDoubleCalendarComposite() {
		return doubleCalendarComposite;
	}

	public void setDoubleCalendarComposite(Composite doubleCalendarComposite) {
		this.doubleCalendarComposite = doubleCalendarComposite;
	}

	public Combo getBetweenDateOpCombo() {
		return betweenDateOpCombo;
	}

	public void setBetweenDateOpCombo(Combo betweenDateOpCombo) {
		this.betweenDateOpCombo = betweenDateOpCombo;
	}

	public Shell getNewShell() {
		return newShell;
	}

	public void setNewShell(Shell newShell) {
		this.newShell = newShell;
	}

	public String getOrgdate() {
		return orgdate;
	}

	public void setOrgdate(String orgdate) {
		this.orgdate = orgdate;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMins() {
		return mins;
	}

	public void setMins(int mins) {
		this.mins = mins;
	}

	public int getSecs() {
		return secs;
	}

	public void setSecs(int secs) {
		this.secs = secs;
	}

	public int getEndDateYears() {
		return endDateYears;
	}

	public void setEndDateYears(int endDateYears) {
		this.endDateYears = endDateYears;
	}

	public int getEndDateMonths() {
		return endDateMonths;
	}

	public void setEndDateMonths(int endDateMonths) {
		this.endDateMonths = endDateMonths;
	}

	public int getEndDateDays() {
		return endDateDays;
	}

	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}

	public int getEndDateWeeks() {
		return endDateWeeks;
	}

	public void setEndDateWeeks(int endDateWeeks) {
		this.endDateWeeks = endDateWeeks;
	}

	public int getEndDateHours() {
		return endDateHours;
	}

	public void setEndDateHours(int endDateHours) {
		this.endDateHours = endDateHours;
	}

	public int getEndDateMins() {
		return endDateMins;
	}

	public void setEndDateMins(int endDateMins) {
		this.endDateMins = endDateMins;
	}

	public int getEndDateSecs() {
		return endDateSecs;
	}

	public void setEndDateSecs(int endDateSecs) {
		this.endDateSecs = endDateSecs;
	}

	public int getStartDateYears() {
		return startDateYears;
	}

	public void setStartDateYears(int startDateYears) {
		this.startDateYears = startDateYears;
	}

	public int getStartDateMonths() {
		return startDateMonths;
	}

	public void setStartDateMonths(int startDateMonths) {
		this.startDateMonths = startDateMonths;
	}

	public int getStartDateDays() {
		return startDateDays;
	}

	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}

	public int getStartDateWeeks() {
		return startDateWeeks;
	}

	public void setStartDateWeeks(int startDateWeeks) {
		this.startDateWeeks = startDateWeeks;
	}

	public int getStartDateHours() {
		return startDateHours;
	}

	public void setStartDateHours(int startDateHours) {
		this.startDateHours = startDateHours;
	}

	public int getStartDateMins() {
		return startDateMins;
	}

	public void setStartDateMins(int startDateMins) {
		this.startDateMins = startDateMins;
	}

	public int getStartDateSecs() {
		return startDateSecs;
	}

	public void setStartDateSecs(int startDateSecs) {
		this.startDateSecs = startDateSecs;
	}

	public boolean isStartDateCalendarSelected() {
		return startDateCalendarSelected;
	}

	public void setStartDateCalendarSelected(boolean startDateCalendarSelected) {
		this.startDateCalendarSelected = startDateCalendarSelected;
	}

	public boolean isEndDateCalendarSelected() {
		return endDateCalendarSelected;
	}

	public void setEndDateCalendarSelected(boolean endDateCalendarSelected) {
		this.endDateCalendarSelected = endDateCalendarSelected;
	}
	protected AbstractDateTimeCalendarDialog(Shell parentShell) {
		super(parentShell);
	}

	public Button getResetbutton() {
		return resetbutton;
	}

	public void setResetbutton(Button resetbutton) {
		this.resetbutton = resetbutton;
	}

	public Button getShowCalendarbutton() {
		return showCalendarbutton;
	}

	public void setShowCalendarbutton(Button showCalendarbutton) {
		this.showCalendarbutton = showCalendarbutton;
	}
}
